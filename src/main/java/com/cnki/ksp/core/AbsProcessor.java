package com.cnki.ksp.core;

import java.util.Set;

import com.cnki.ksp.beans.Article;

public abstract class AbsProcessor implements Processor {

	public Set<Article> getArticles() {
		return null;
	}

	public String getEarliestDate() {
		return null;
	}

	public String getLastDate() {
		return null;
	}

	public void setObserver(KspObserver observer) {
	}

}
