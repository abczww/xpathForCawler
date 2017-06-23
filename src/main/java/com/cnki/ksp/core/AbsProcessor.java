package com.cnki.ksp.core;

import java.util.Set;

import com.cnki.ksp.beans.Article;

public abstract class AbsProcessor implements Processor {
	protected String webSite;
	protected String entranceUrl;
	protected String xTitle;
	protected String xDate;
	protected String xContent;
	protected String xReply;

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getEntranceUrl() {
		return entranceUrl;
	}

	public void setEntranceUrl(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getxDate() {
		return xDate;
	}

	public void setxDate(String xDate) {
		this.xDate = xDate;
	}

	public String getxContent() {
		return xContent;
	}

	public void setxContent(String xContent) {
		this.xContent = xContent;
	}

	public String getxReply() {
		return xReply;
	}

	public void setxReply(String xReply) {
		this.xReply = xReply;
	}

	public void run() {
		// TODO Auto-generated method stub

	}

	public void setEntranceURL(String url) {
		// TODO Auto-generated method stub

	}

	public Set<Article> getArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEarliestDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObserver(Observer observer) {
		// TODO Auto-generated method stub

	}

}
