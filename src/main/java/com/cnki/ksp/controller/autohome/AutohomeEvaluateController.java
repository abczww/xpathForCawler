package com.cnki.ksp.controller.autohome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsController;
import com.cnki.ksp.core.ArticlePool;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.Processor;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;
import com.cnki.ksp.processor.AutohomeProcessor;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class AutohomeEvaluateController extends AbsController implements Runnable {

	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	private JXDocument xdoc;

	// private String entranceUrl = null;
	private String webSite = null;
	private int timeout = 5000;
	private String xUrl = null;
	private boolean isNeedForward = false;
	private String xForward = null;
	private int kspId = -1;

	@Override
	public void init(Observer observer) {
		this.observer = observer;
		// entranceUrl = controllerProperties.getProperty("entranceUrl");
		webSite = controllerProperties.getProperty("webSite");
		timeout = Integer.parseInt(String.valueOf(controllerProperties.getProperty("timeout")));
		xUrl = controllerProperties.getProperty("xUrl");
		isNeedForward = String.valueOf(controllerProperties.get("isNeedForward")).equals("true") ? true : false;
		xForward = controllerProperties.getProperty("xForward");
		articleUrls = new ArrayList<String>();
		kspId = Integer.parseInt(String.valueOf(controllerProperties.get("kspId")));
	}

	@Override
	public String getTopic() {
		return processorProperties.getProperty("carModel");
	}

	/**
	 * get all article urls from the entrance; and then analyze the article url
	 * and get the article; and the save the articles to db.
	 * 
	 * @throws Exception,
	 *             if any exception happened, throw it to run method of thread.
	 */
	@Override
	public void execute() throws Exception {
		try {
			if (isNeedForward) {
				// if(false){
				getAllUrlsByTemplate();
				observer.appendInfo("get %d urls in all.", articleUrls.size());

				for (int i = 0; i < articleUrls.size(); i++) {
					String url = articleUrls.get(i);
					Processor processor = new AutohomeProcessor(webSite + "" + url, processorProperties, observer);
					processor.execute();
					observer.appendInfo("getting articles from url progress: " + (i + 1) + "/" + articleUrls.size());
					// save 100 articles in one time.
					if ((i + 1) % 100 == 0) {
						saveArticlesAndClean();
					}
				}
			} else {
				String url = "http://club.autohome.com.cn/bbs/thread-c-623-64211415-1.html";
				// String url =
				// "http://club.autohome.com.cn/bbs/thread-c-623-63549997-1.html";
				Processor processor = new AutohomeProcessor(url, processorProperties, observer);
				processor.execute();
			}

			saveArticlesAndClean();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private List<String> getAllUrlsByTemplate() throws Exception {
		String entranceTemplate = controllerProperties.getProperty("entranceTemplate");
		int pageStart = 1;
		articleUrls.clear();

		while (true) {
			String url = entranceTemplate.replaceAll("pageNum", pageStart + "");
			if (url.startsWith("###")) {
				break;
			}
			analyseUrls(url);
			observer.appendInfo("analyse page: %d, get %d articles in all.", pageStart, articleUrls.size());
			pageStart++;
		}
		return articleUrls;
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
		xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, timeout);
		System.out.println(xdoc.sel("//dev"));
		List<JXNode> rs = xdoc.selN(xUrl);
		int type = Integer.parseInt(controllerProperties.getProperty("type"));
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, controllerProperties.getProperty("xTitleUrl"), "href");
			turl = turl.startsWith("http") ? turl : (this.webSite + turl);
			String tauthor = xpathTool.getContentByXPath(node, controllerProperties.getProperty("xAuthor"), null);
			String tdate = xpathTool.getContentByXPath(node, controllerProperties.getProperty("xDate"), null);
			String ttitle = xpathTool.getContentByXPath(node, controllerProperties.getProperty("xTitle"), null);
			Article art = new Article(kspId, turl, type);
			art.setAuthor(tauthor);
			art.setDate(tdate);
			art.setTitle(ttitle);
			if (!checkAndFindArticle(art)) {
				articleUrls.add(turl);
			} else {
				ArticlePool.getInstance().pushDuplicated(turl);
			}
			//// *[@id="subcontent"]/dl[24]/dt/a
			//// *[@id="subcontent"]/dl[8]/dd[1]/span
			//// *[@id="subcontent"]/dl[8]/dd[1]/a
			//// *[@id="subcontent"]/dl[9]/dt/a
			//// *[@id="subcontent"]/dl[24]/dd[1]/a

			// to do
			// need to check the db, if we need to analyze the articl url.
			// if the article exists in db, don't add it.

		}
	}

	@Override
	protected boolean ifContinue(String url) throws Exception {
		analyseUrls(url);

		// if we need to forward and
		if (isNeedForward) {
			String forwardUrl = getForwardUrl(url);
			if (null == forwardUrl) {
				return false;
			}
			observer.appendInfo(forwardUrl);
			ifContinue(forwardUrl);
		}

		return true;
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

	@Override
	public void run() {
		try {
			this.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
