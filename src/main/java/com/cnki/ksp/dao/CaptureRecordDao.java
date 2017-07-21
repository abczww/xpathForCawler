package com.cnki.ksp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.CaptureRecord;
import com.cnki.ksp.core.BaseDao;

@Repository("captureRecordDao")
public class CaptureRecordDao extends BaseDao<CaptureRecord> {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public CaptureRecord saveOrUpdate(CaptureRecord cr) {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.saveOrUpdate(cr);
		session.getTransaction().commit();
		return cr;
	}

	@Override
	public List<CaptureRecord> getAll() {
		//return sqlSessionTemplate.selectList("CaptureRecord.getAll");
		return sessionFactory.openSession().createQuery("from CaptureRecord", CaptureRecord.class).list();
	}

}
