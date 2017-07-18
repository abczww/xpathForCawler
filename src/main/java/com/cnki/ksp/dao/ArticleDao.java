package com.cnki.ksp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.BaseDao;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> {
	
	@Resource(name="sqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<Article> getAll() {
		List<Article> arts = sqlSessionTemplate.selectList("Article.getAll");
		return arts;
	}

	@Override
	public void save(Article art) {
		sqlSessionTemplate.insert("Article.save", art);
	}

	public List<Integer> getDuplicatedArticles(Article art) {
		List<Integer> ids = sqlSessionTemplate.selectList("Article.findDuplicated", art);
		return ids;
	}

}
