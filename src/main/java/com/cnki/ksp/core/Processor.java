package com.cnki.ksp.core;

import java.util.Set;

import com.cnki.ksp.beans.Article;

public interface Processor extends Runnable {
	public Set<Article> getArticles();

	public String getEarliestDate();

	public String getLastDate();

	public void setObserver(Observer observer);

}
