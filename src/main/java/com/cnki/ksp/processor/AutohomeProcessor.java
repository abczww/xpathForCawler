package com.cnki.ksp.processor;

import java.util.Properties;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsProcessor;
import com.cnki.ksp.core.ArticlePool;
import com.cnki.ksp.core.KspObserver;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.HTMLCleanHelper;
import com.cnki.ksp.helper.JSoupConnectionHelper;
import com.cnki.ksp.helper.UtilHelper;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class AutohomeProcessor extends AbsProcessor {

	private JXDocument xdoc;

	private String entranceUrl;
	private Properties prop;
	private KspObserver observer;

	public AutohomeProcessor(String url, Properties prop, KspObserver observer) {
		this.entranceUrl = url;
		this.prop = prop;
		this.observer = observer;
	}

	public void execute() {
		try {
			Article art = this.getArticleFromUrl(this.entranceUrl);
			if (null != art) {
				ArticlePool.getInstance().pushArticle(art);
			} else {
				observer.appendInfo("Can't get the article from the url: " + entranceUrl);
			}
		} catch (Exception e) {
			// if any exceptions happened, record the url and try again after
			// all processers completed
			ArticlePool.getInstance().pushFialUrl(entranceUrl);
			e.printStackTrace();
		} finally {
			// CompleteHelper.complete(this);
		}
	}

	/**
	 * get an article from a url.
	 * 
	 * @param url,
	 *            thr url we want to analysis.
	 * @return an article.
	 * @throws Exception,
	 *             if any exception happened, throws it.
	 */
	private Article getArticleFromUrl(String url) throws Exception {
		Article artl = null;
		xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000);

		XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
		String title = xpathTools.getContentByXPath(prop.getProperty("xTitle"));
		String content = getConent(xpathTools, prop.getProperty("xContent"));
		String author = xpathTools.getContentByXPath(prop.getProperty("xAuthor"));
		String time = xpathTools.getContentByXPath(prop.getProperty("xTime"));
		String date = UtilHelper.getDateFromTimeByFormat(time, "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd");
		String hits = xpathTools.getContentByXPath(prop.getProperty("xHits"));
		String replies = xpathTools.getContentByXPath(prop.getProperty("xReplies"));

		if (null != content && title != null && author != null && date != null) {
			artl = new Article();
			artl.setCarModel(prop.getProperty("carModel"));
			artl.setCarFirm(prop.getProperty("carFirm"));
			artl.setWebsite(prop.getProperty("webSite"));
			artl.setUrl(this.entranceUrl);
			artl.setAuthor(author);
			artl.setTime(time);
			artl.setDate(date);
			artl.setTitle(title);
			artl.setContent(HTMLCleanHelper.removeSytleAndScript(content));
			artl.setContent2(HTMLCleanHelper.removeHTML(content));
			artl.setHits(Integer.parseInt(hits));
			artl.setReplies(Integer.parseInt(replies));
			artl.setCreatedBy("william");
			artl.setUpdatedBy("william");
		}
		return artl;
	}

	/**
	 * get the article content from the xpathTools. because a url may have
	 * different content, so we need different xpath to analyze the content.
	 * 
	 * @param xpathTools
	 * @param defaultPath,
	 *            if we could get the content from the default xpath.
	 * @return the content.
	 * @throws XpathSyntaxErrorException
	 */
	private String getConent(XPathUtilTools xpathTools, String defaultPath) throws XpathSyntaxErrorException {
		String content = xpathTools.getContentByXPath(defaultPath);
		if (null != content) {
			return content;
		}
		String key = "xContent";
		for (int i = 1; i < 10; i++) {
			if (null == prop.getProperty(key + i)) {
				break;
			}

			content = xpathTools.getContentByXPath(prop.getProperty(key + i));
			if (null != content) {
				return content;
			}
		}

		return content;
	}

}
