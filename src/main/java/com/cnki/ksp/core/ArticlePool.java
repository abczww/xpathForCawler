package com.cnki.ksp.core;

import java.util.ArrayList;
import java.util.List;

import com.cnki.ksp.beans.Article;

public class ArticlePool {

	private static List<Article> articles = new ArrayList<Article>();
	private static List<String> failUrls = new ArrayList<String>();

	private static ArticlePool articlePool;

	private ArticlePool() {

	}

	public static synchronized ArticlePool getInstance() {
		if (articlePool == null) {
			articlePool = new ArticlePool();
		}

		return articlePool;
	}

	public synchronized void pushArticle(Article art) {
		articles.add(art);
	}

	public List<Article> getAllArticles() {
		return articles;
	}

	public void clear() {
		articles.clear();
		failUrls.clear();
	}
	
	public void pushFialUrl(String url) {
		failUrls.add(url);
	}

	public List<String> getFailUrls() {
		return failUrls;
	}

}
