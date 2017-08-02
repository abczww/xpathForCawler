package com.cnki.ksp.core;

import java.util.Set;

import com.cnki.ksp.beans.Article;

public interface Processor  {
	public Set<Article> getArticles();
	
	public void execute() ;

}
