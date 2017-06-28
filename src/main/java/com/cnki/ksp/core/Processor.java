package com.cnki.ksp.core;

import java.util.Properties;
import java.util.Set;

import com.cnki.ksp.beans.Article;

public interface Processor  {
	public Set<Article> getArticles();

	public String getEarliestDate();

	public String getLastDate();

	public void setObserver(KspObserver observer);
	
	public void execute() ;

}
