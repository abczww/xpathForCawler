package com.cnki.ksp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.BaseDao;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> {
	
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public List<Article> getAll() {
		Session session = sessionFactory.openSession();
		List<Article> arts = session.createQuery("from Article", Article.class).getResultList();
		return arts;
	}

	@Override
	public void save(Article art) {
		if(art.getId() < 1) {
			//sessionFactory.insert(art);
		}
	}

	public List<Integer> getDuplicatedArticles(Article art) {
		//List<Integer> ids = sessionFactory.selectList("Article.findDuplicated", art);
		return null;
	}

}
