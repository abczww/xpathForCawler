package com.cnki.ksp.processor;

import java.util.Properties;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsProcessor;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.HTMLCleanHelper;
import com.cnki.ksp.helper.JSoupConnectionHelper;
import com.cnki.ksp.helper.UtilHelper;

import cn.wanghaomiao.xpath.model.JXDocument;

public class AutohomeProcessor extends AbsProcessor {

	public AutohomeProcessor(String url, Properties prop, Observer observer) {
		super(url, prop, observer);
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
	@Override
	public Article getArticleFromUrl(String url) throws Exception {
		Article artl = null;
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000);

		XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
		String title = xpathTools.getContentByXPath(prop.getProperty("xTitle"));
		String content = getConent(xpathTools, prop.getProperty("xContent"));
		String author = xpathTools.getContentByXPath(prop.getProperty("xAuthor"));
		String time = xpathTools.getContentByXPath(prop.getProperty("xTime"));
		String date = UtilHelper.getDateFromTimeByFormat(time, "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd");
		String hits = xpathTools.getContentByXPath(prop.getProperty("xHits"));
		String replies = xpathTools.getContentByXPath(prop.getProperty("xReplies"));
		int type = Integer.parseInt(prop.getProperty("type"));

		if (null != content && title != null && author != null && date != null) {
			artl = new Article(kspId, url, type);
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

}
