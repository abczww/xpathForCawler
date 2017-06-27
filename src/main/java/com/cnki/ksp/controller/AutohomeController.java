package com.cnki.ksp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cnki.ksp.core.AbsController;
import com.cnki.ksp.core.CompleteHelper;
import com.cnki.ksp.core.Processor;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.processor.AutohomeProcessor;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

/**
 * The crawler for Autohome(汽车之家). it could list all articles of autohome and
 * save it to db.
 * 
 * @author william
 * @version 1.0
 *
 */
public class AutohomeController extends AbsController {

	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	private JXDocument xdoc;

	// private String entranceUrl = null;
	private String webSite = null;
	private int timeout = 5000;
	private String xUrl = null;
	private boolean isNeedForward = false;
	private String xForward = null;

	public void init() {
		// entranceUrl = controllerProperties.getProperty("entranceUrl");
		webSite = controllerProperties.getProperty("webSite");
		timeout = Integer.parseInt(String.valueOf(controllerProperties.getProperty("timeout")));
		xUrl = controllerProperties.getProperty("xUrl");
		isNeedForward = String.valueOf(controllerProperties.get("isNeedForward")).equals("true") ? true : false;
		xForward = controllerProperties.getProperty("xForward");
		urls = new ArrayList<String>();
	}

	public void execute() throws Exception {
		try {
			if (isNeedForward) {
				// if(false){
				getAllUrlsByTemplate();
				System.out.println(urls.size());

				for (int i = 0; i < urls.size(); i++) {
					String url = urls.get(i);
					Processor processor = new AutohomeProcessor(webSite + "" + url, processorProperties);
					processor.run();
					System.out.println((i + 1) + "/" + urls.size());
				}
			} else {
				CompleteHelper.initThreadCount(1);

				String url = "http://club.autohome.com.cn/bbs/thread-c-623-64211415-1.html";
				// String url =
				// "http://club.autohome.com.cn/bbs/thread-c-623-63549997-1.html";
				Processor processor = new AutohomeProcessor(url, processorProperties);
				processor.run();
			}

			saveArticlesAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private List<String> getAllUrlsByTemplate() throws Exception {
		String entranceTemplate = controllerProperties.getProperty("entranceTemplate");
		int pageStart = Integer.parseInt(controllerProperties.getProperty("pageStart"));
		int pageEnd = Integer.parseInt(controllerProperties.getProperty("pageEnd"));
		urls.clear();

		for (int i = pageStart; i < pageEnd; i++) {
			String url = entranceTemplate.replaceAll("pageNum", i + "");
			analyseUrls(url);
			if (urls.size() > 100) {
				return urls;
			}
		}
		return urls;
	}

	/**
	 * this method could get all the article urls in a page. if got the socket
	 * exception, the program will try again after 30s, and it will try 3 times
	 * in all.
	 * 
	 * @param url,
	 *            the page's ur.
	 * @throws Exception
	 * @throws Exception,
	 *             if any exceptions happened, throw it.
	 */
	private void analyseUrls(String url) throws Exception {
		Thread.sleep(3000);
		Document doc = XPathUtilTools.getDocFromUrl(url, timeout);

		xdoc = new JXDocument(doc);
		List<JXNode> rs = xdoc.selN(xUrl);
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, "//dt/a", "href");
			urls.add(turl);
			if (urls.size() % 100 == 0) {
				System.out.println(urls.size());
			}
		}
	}

	@Override
	protected void getAllUrlsByXPath(String url) throws Exception {
		analyseUrls(url);

		// if we need to forward and
		if (isNeedForward) {
			String forwardUrl = getForwardUrl(url);
			if (null == forwardUrl) {
				return;
			}
			System.out.println(forwardUrl);
			getAllUrlsByXPath(forwardUrl);
		}
	}

	private String getForwardUrl(String url) throws XpathSyntaxErrorException, IOException {
		List<Object> rs = xdoc.sel(xForward);
		String reValue = null;
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				reValue = webSite + ele.attr("href");
			}
		}
		return reValue;
	}

}
