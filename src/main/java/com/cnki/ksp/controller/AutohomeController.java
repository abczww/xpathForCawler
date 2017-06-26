package com.cnki.ksp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
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

	public void execute() throws Exception {
		initDoc();
		if (this.isNeedForward()) {
		//if(false){
			getAllUrlsByXPath(entranceUrl);
			System.out.println(urls.size());
			CompleteHelper.initThreadCount(urls.size());
			for (int i = 0; i < urls.size(); i++) {
				String url = urls.get(i);
				Processor processor = new AutohomeProcessor(webSite + "" + url, processorProperties);
				processor.run();
				System.out.println((i + 1) + ":" + urls.size());
				Thread.sleep(2000);
			}
		} else {
			CompleteHelper.initThreadCount(1);
			
			String url = "http://club.autohome.com.cn/bbs/thread-c-623-64211415-1.html";
			//String url = "http://club.autohome.com.cn/bbs/thread-c-623-63549997-1.html";
			Processor processor = new AutohomeProcessor(url, processorProperties);
			processor.run();
		}

		waitAndClose();
	}

	private void initDoc() throws IOException {
		Document doc = Jsoup.connect(entranceUrl).timeout(timeout)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();

		xdoc = new JXDocument(doc);
		urls = new ArrayList<String>();
	}

	private void getAllUrlsByXPath(String url) throws XpathSyntaxErrorException, IOException, InterruptedException {
		Document doc = Jsoup.connect(url).timeout(10000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
		xdoc = new JXDocument(doc);
		List<JXNode> rs = xdoc.selN("//*[@id='subcontent']/dl[@class='list_dl']");
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, "//dt/a", "href");
			urls.add(turl);
		}

		if (!isContinue()) {
			return;
		}
		isNeedForward = false;
		if (isNeedForward) {
			String forwardUrl = getForwardUrl(url);
			if (null == forwardUrl) {
				return;
			}

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

	protected boolean isContinue() {
		if (urls.size() >= 10) {
			return false;
		}

		return true;
	}

	public boolean isLoadPageSuccess() {
		return true;
	}

}
