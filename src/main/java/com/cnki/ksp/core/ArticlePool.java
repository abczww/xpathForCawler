package com.cnki.ksp.core;

import java.util.ArrayList;
import java.util.List;

import com.cnki.ksp.beans.Article;

public class ArticlePool {

	private static List<Article> articles = new ArrayList<Article>();

	public static synchronized void pushArticle(Article art) {
		articles.add(art);
	}

	public static List<Article> getAllArticles() {
		return articles;
	}

}
