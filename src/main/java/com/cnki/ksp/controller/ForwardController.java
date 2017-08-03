package com.cnki.ksp.controller;

import java.util.ArrayList;
import java.util.List;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsController;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.Processor;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;
import com.cnki.ksp.processor.AutohomeProcessor;
import com.cnki.ksp.processor.PeopleCnProcessor;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class ForwardController extends AbsController {

	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	// private String entranceUrl = null;
	private String webSite = null;
	private int timeout = 5000;
	private String xUrl = null;
	private boolean isNeedForward = false;
	private int kspId = -1;
	private String kspName = "";
	private int saveFrequency = 20;

	@Override
	public void init(Observer observer) {
		this.observer = observer;
		// entranceUrl = controllerProperties.getProperty("entranceUrl");
		webSite = controllerProperties.getProperty("webSite");
		timeout = Integer.parseInt(String.valueOf(controllerProperties.getProperty("timeout")));
		xUrl = controllerProperties.getProperty("xUrl");
		isNeedForward = String.valueOf(controllerProperties.get("isNeedForward")).equals("true") ? true : false;
		articleUrls = new ArrayList<String>();
		kspId = Integer.parseInt(String.valueOf(controllerProperties.get("kspId")));
		kspName = String.valueOf(controllerProperties.get("kspName"));
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
			getAllArticleUrlsInPage();
			observer.appendInfo("get %d urls in all.", articleUrls.size());

			for (int i = 0; i < articleUrls.size(); i++) {
				String url = articleUrls.get(i);
				Processor processor = new PeopleCnProcessor(url, processorProperties, observer);
				processor.execute();
				observer.appendInfo("getting articles from url progress: " + (i + 1) + "/" + articleUrls.size());
				// save 100 articles in one time.
				if ((i + 1) % saveFrequency == 0) {
					saveArticlesAndClean();
				}
			}
		} else {
			Processor processor = new AutohomeProcessor(String.valueOf(controllerProperties.getProperty("entranceUrl")),
					processorProperties, observer);
			processor.execute();
		}

		saveArticlesAndClean();
	}

	public List<String> getAllArticleUrlsInPage() throws Exception {
		String entranceTemplate = controllerProperties.getProperty("entranceTemplate");
		int pageStart = 1;
		articleUrls.clear();

		while (true) {
			String currentPageUrl = entranceTemplate.replaceAll("#pageNum", pageStart + "");
			duplicatedCount = 0;
			if (!ifContinue(currentPageUrl) || !isNeedForward) {
				break;
			}
			observer.appendInfo(kspName + " is analysing, gets %d articles in.", articleUrls.size());
			pageStart++;
		}
		return articleUrls;
	}

	private final int MAX_DUPLICATED_COUNT = 10;
	private int duplicatedCount = 0;

	/**
	 * this method could get all articles' url in current page. Add it get all
	 * articles, it MUST get and check article one by one. so the program could
	 * know if it need to continue.
	 * 
	 */
	@Override
	protected boolean ifContinue(String url) throws Exception {
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, timeout, 1000);

		List<JXNode> rs = xdoc.selN(xUrl);
		// it's the end of the last page.
		if (rs == null || rs.size() == 0) {
			return false;
		}
		int type = Integer.parseInt(controllerProperties.getProperty("type"));
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, String.valueOf(controllerProperties.get("xTitleUrl")),
					null);
			turl = formatUrl(turl);
			String ttitle = xpathTool.getContentByXPath(node, String.valueOf(controllerProperties.get("xTitle")), null);
			Article art = new Article(kspId, turl, type);
			art.setTitle(ttitle);

			if (!this.checkAndFindArticle(art)) {
				articleUrls.add(turl);
			} else {
				duplicatedCount++;
				// if program found some duplicated records, it would think they
				// are history data,
				// and don't need to capture any more.
				if (duplicatedCount == MAX_DUPLICATED_COUNT) {
					return false;
				}
			}
		}

		return true;
	}

	public String formatUrl(String url) {
		if (url.startsWith("/")) {
			return webSite + url;
		}
		return url;
	}

}
