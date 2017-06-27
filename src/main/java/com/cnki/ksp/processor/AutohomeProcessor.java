package com.cnki.ksp.processor;

import java.util.Properties;

import org.jsoup.nodes.Document;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsProcessor;
import com.cnki.ksp.core.ArticlePool;
import com.cnki.ksp.core.CompleteHelper;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.HTMLCleanHelper;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class AutohomeProcessor extends AbsProcessor {

	private JXDocument xdoc;

	private String entranceUrl;
	private Properties prop;

	public AutohomeProcessor(String url, Properties prop) {
		this.entranceUrl = url;
		this.prop = prop;
	}

	public void run() {
		try {
			Article art = this.getArticleFromUrl(this.entranceUrl);
			if (null != art) {
				ArticlePool.pushArticle(art);
			} else {
				System.out.println("Can't get the article from the url: " + entranceUrl);
			}
		} catch (Exception e) {
			// if any exceptions happened, record the url and try again after
			// all processers completed
			CompleteHelper.pushFialUrl(entranceUrl);
			e.printStackTrace();
		} finally {
			// CompleteHelper.complete(this);
		}
	}

	/**
	 * get an article from a url.
	 * 
	 * @param url, thr url we want to analysis.
	 * @return an article.
	 * @throws Exception, if any exception happened, throws it.
	 */
	private Article getArticleFromUrl(String url) throws Exception {
		Article artl = null;
		Document doc = XPathUtilTools.getDocFromUrl(url, 5000);
		xdoc = new JXDocument(doc);

		XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
		String title = xpathTools.getContentByXPath(prop.getProperty("xTitle"));
		String content = getConent(xpathTools, prop.getProperty("xContent"));
		String author = xpathTools.getContentByXPath(prop.getProperty("xAuthor"));
		String date = xpathTools.getContentByXPath(prop.getProperty("xDate"));

		if (null != content && title != null && author != null && date != null) {
			artl = new Article();
			artl.setCarModel(prop.getProperty("carModel"));
			artl.setCarFirm(prop.getProperty("carFirm"));
			artl.setArticleWebsite(prop.getProperty("webSite"));
			artl.setarticleUrl(this.entranceUrl);
			artl.setArticleAuthor(author);
			artl.setArticleTime(date);
			artl.setArticleTitle(title);
			artl.setArticleContent(HTMLCleanHelper.removeSytleAndScript(content));
			artl.setArticleContent2(HTMLCleanHelper.removeHTML(content));
			artl.setCreatedBy("william");
			artl.setUpdatedBy("william");
		}
		return artl;
	}

	/**
	 * get the article content from the xpathTools. because a url may have different content, 
	 * so we need different xpath to analyze the content.
	 * @param xpathTools
	 * @param defaultPath, if we could get the content from the default xpath.
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
