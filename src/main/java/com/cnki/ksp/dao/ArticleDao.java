package com.cnki.ksp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.BaseDao;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> {

	public List<Article> checkDuplicated(Article art) {
		Session session = sessionFactory.openSession();
		StringBuilder hlsql = new StringBuilder(512);
		hlsql.append(" from Article art where art.url=:url ");
		hlsql.append(" and art.time = :time ");
		hlsql.append(" and art.title = :title ");
		hlsql.append(" and art.kspId = :kspId ");
		Query<Article> query = session.createQuery(hlsql.toString(), Article.class);
		query.setParameter("url", art.getUrl());
		query.setParameter("time", art.getTime());
		query.setParameter("title", art.getTitle());
		query.setParameter("kspId", art.getKspId());
		List<Article> arts = query.list();
		return arts;
	}

}
