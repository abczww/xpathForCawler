package com.cnki.ksp.processor;

import java.util.Properties;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.AbsProcessor;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.DateHelper;
import com.cnki.ksp.helper.HTMLCleanHelper;
import com.cnki.ksp.helper.JSoupConnectionHelper;

import cn.wanghaomiao.xpath.model.JXDocument;

public class PeopleCnProcessor extends AbsProcessor{
	
	public PeopleCnProcessor(String url, Properties prop, Observer observer) {
		super(url, prop, observer);
	}

	/**
	 * this method should tell the program how to get the article from the web page.
	 */
	@Override
	public Article getArticleFromUrl(String url) throws Exception {
		Article artl = null;
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000, 500);

		XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
		String title = xpathTools.getContentByXPath(prop.getProperty("xTitle"));
		String content = getConent(xpathTools, prop.getProperty("xContent"));
		String author = xpathTools.getContentByXPath(prop.getProperty("xAuthor"));
		String time = xpathTools.getContentByXPath(prop.getProperty("xTime"));
		String date = DateHelper.getInstance().getFormatedDate(time);
		int kspId = Integer.parseInt(prop.getProperty("kspId"));
		int type  = Integer.parseInt(prop.getProperty("type"));
		String website = prop.getProperty("webSite");
		
		artl = new Article(kspId, url, type);
		artl.setWebsite(website);
		artl.setAuthor(author);
		artl.setTime(time);
		artl.setDate(date);
		artl.setTitle(title);
		artl.setContent(content);
		artl.setContent2(HTMLCleanHelper.removeHTML(content));
		
		return artl;
	}

}
