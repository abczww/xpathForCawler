package com.cnki.ksp.processor;

import java.io.IOException;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsProcessor;
import com.cnki.ksp.core.ArticlePool;
import com.cnki.ksp.core.CompleteHelper;
import com.cnki.ksp.core.XPathUtilTools;

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
			// if any exceptions happened, record the url and try again after all processers completed
			CompleteHelper.pushFialUrl(entranceUrl);
			e.printStackTrace();
		} finally {
			CompleteHelper.complete(this);
		}
	}

	private Article getArticleFromUrl(String url) throws IOException, XpathSyntaxErrorException {
		Article artl = null;
		Document doc = null;

		doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
				.get();

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
			artl.setArticleContent(content);
			artl.setCreatedBy("william");
			artl.setUpdatedBy("william");
		}
		return artl;
	}

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
