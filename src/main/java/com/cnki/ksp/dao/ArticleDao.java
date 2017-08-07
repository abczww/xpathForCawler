package com.cnki.ksp.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.BaseDao;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> {

	public List<Integer> checkDuplicated(Article art) {
		try {
			session = sessionFactory.openSession();
			StringBuilder hlsql = new StringBuilder(512);
			hlsql.append("select art.id from Article art where art.url=:url ");
			if (art.getTime() != null) {
				hlsql.append(" and art.time = :time ");
			}
			hlsql.append(" and art.title = :title ");
			hlsql.append(" and art.kspId = :kspId ");
			Query<Integer> query = session.createQuery(hlsql.toString(), Integer.class);
			query.setParameter("url", art.getUrl());
			if (art.getTime() != null) {
				query.setParameter("time", art.getTime());
			}
			query.setParameter("title", art.getTitle());
			query.setParameter("kspId", art.getKspId());
			List<Integer> arts = query.list();
			session.close();
			return arts;
		} finally {
			closeSession();
		}
	}

}
