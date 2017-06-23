package com.cnki.ksp.dao;

import java.util.List;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.BaseDao;

public class ArticleDao extends BaseDao {

	public List<Article> getAllArticle() {
		List<Article> arts = sqlSessionTemplate.selectList("Article.getAllArticles");
		return arts;
	}

	public void saveArticle(Article art) {
		sqlSessionTemplate.insert("Article.saveArticle", art);
	}

}
