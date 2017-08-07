package com.cnki.ksp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.CaptureRecord;
import com.cnki.ksp.core.BaseDao;

@Repository("captureRecordDao")
public class CaptureRecordDao extends BaseDao<CaptureRecord> {

	@Override
	public CaptureRecord saveOrUpdate(CaptureRecord cr) {
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.saveOrUpdate(cr);
			session.getTransaction().commit();
			return cr;
		} finally {
			closeSession();
		}
	}

	@Override
	public List<CaptureRecord> getAll() {
		try {
			session = sessionFactory.getCurrentSession();
			// return sqlSessionTemplate.selectList("CaptureRecord.getAll");
			return session.createQuery("from CaptureRecord", CaptureRecord.class).list();
		} finally {
			closeSession();
		}
	}

}
