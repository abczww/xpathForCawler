package com.cnki.ksp.core;

import java.util.Properties;
import java.util.Set;

import com.cnki.ksp.beans.Article;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;

public abstract class AbsProcessor implements Processor {

	protected String entranceUrl;
	protected Properties prop;
	protected Observer observer;
	protected int kspId = -1;

	public AbsProcessor(String url, Properties prop, Observer observer) {
		this.entranceUrl = url;
		this.prop = prop;
		this.observer = observer;
		this.kspId = Integer.parseInt(String.valueOf(prop.get("kspId")));
	}

	@Override
	public void execute() {
		try {
			Article art = this.getArticleFromUrl(this.entranceUrl);
			if (null != art) {
				ArticlePool.getInstance().pushArticle(art);
			} else {
				observer.appendInfo("Can't get the article from the url: " + entranceUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// if any exceptions happened, record the url and try again after
			// all processers completed
			ArticlePool.getInstance().pushFialUrl(entranceUrl);
			observer.appendInfo(e.getMessage()  + ", " + entranceUrl);
		} finally {
			// CompleteHelper.complete(this);
		}
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
	 * @throws StyleChangedException
	 */
	protected String getConent(XPathUtilTools xpathTools, String defaultPath)
			throws XpathSyntaxErrorException, StyleChangedException {
		String content = null;
		try {
			content = xpathTools.getContentByXPath(defaultPath);
		} catch (StyleChangedException e) {
			// do nothing.
		}
		if (null != content) {
			return content;
		}
		String key = "xContent";
		for (int i = 1; i < 10; i++) {
			if (null == prop.getProperty(key + i)) {
				break;
			}

			try {
				content = xpathTools.getContentByXPath(prop.getProperty(key + i));
			} catch (StyleChangedException e) {
				// do nothing.
			}
			if (null != content) {
				return content;
			}
		}
		throw new StyleChangedException("xContent changed.");
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
	public abstract Article getArticleFromUrl(String url) throws Exception;

	public Set<Article> getArticles() {
		return null;
	}

}
