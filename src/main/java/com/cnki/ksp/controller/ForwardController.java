package com.cnki.ksp.controller;

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
import com.cnki.ksp.helper.UtilHelper;
import com.cnki.ksp.processor.AutohomeProcessor;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class ForwardController extends AbsController{

	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	private JXDocument xdoc;

	// private String entranceUrl = null;
	private String webSite = null;
	private int timeout = 5000;
	private String xUrl = null;
	private boolean isNeedForward = false;
	private String xForward = null;

	private final int MAX_PREVIOUS_DATE_ARTICLE = 10;
	/** 100 records per day, so half year would have 20000 records in all */
	private final int MAX_HISTORY_RECORD = 20000;
	private int oldRecordCount = 0;

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
		if (isNeedForward) {
			// if(false){
			getAllUrlsByTemplate();
			observer.appendInfo("get %d urls in all.", articleUrls.size());

			for (int i = 0; i < articleUrls.size(); i++) {
				String url = articleUrls.get(i);
				Processor processor = new AutohomeProcessor(webSite + "" + url, processorProperties, observer);
				processor.execute();
				observer.appendInfo(controllerProperties.getProperty("kspName") + " : getting articles, progress: "
						+ (i + 1) + "/" + articleUrls.size());
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
	}

	private List<String> getAllUrlsByTemplate() throws Exception {
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(controllerProperties.getProperty("entranceUrl"),
				timeout);
		XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
		String endPageStr = xpathTools.getContentByXPath(controllerProperties.getProperty("pageEnd"));
		String entranceTemplate = controllerProperties.getProperty("entranceTemplate");
		int pageStart = 1;
		int pageEnd = Integer.parseInt(UtilHelper.getLastPageNumber(endPageStr));
		articleUrls.clear();

		for (int i = pageStart; i <= pageEnd; i++) {
			String url = entranceTemplate.replaceAll("#pageNum", i + "");
			if (!analyseUrls(url)) {
				// got 10 old data, and don't need to analysis any more.
				break;
			}
			observer.appendInfo("analyse all pages: %d/%d, get %d articles in all.", i, pageEnd, articleUrls.size());
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
	private boolean analyseUrls(String url) throws Exception {
		xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, timeout);
		List<JXNode> rs = xdoc.selN(xUrl);
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, "//dt/a", "href");
			String tauthor = xpathTool.getContentByXPath(node, "//dd[1]/a", null);
			String tdate = xpathTool.getContentByXPath(node, "//dd[1]/span", null);
			String ttitle = xpathTool.getContentByXPath(node, "//dt/a", null);

			// we don't need to analysis the old data.
			if (!ifForward(tdate)) {
				return false;
			}

			Article art = new Article();
			art.setUrl(this.webSite + turl);
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

		return true;
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
			observer.appendInfo(forwardUrl);
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

	/**
	 * if the ksp needs to forward or not. if the ksp never captureed the topic,
	 * it would get all articles.
	 * 
	 * @param articleDate,
	 *            the article publish time.
	 * @return
	 */
	private boolean ifForward(String articleDate) {
		// if the crawler never get this topic, get all articles.
		// or if user wants to get all articles
		if (null == lastCapRecord
				|| "true".equals(controllerProperties.getProperty("forwardAll").toString().toLowerCase())) {
			// we don't need to get too many history articles.
			if (oldRecordCount > MAX_HISTORY_RECORD) {
				return false;
			}
			return true;
		}
		String lastDate = UtilHelper.getFormataedDate(lastCapRecord.getCaptureStartTime().getTime(),
				UtilHelper.YYYY_MM_DD);

		if (!articleDate.equals(lastDate)) {
			if (oldRecordCount < MAX_PREVIOUS_DATE_ARTICLE) {
				oldRecordCount++;
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
