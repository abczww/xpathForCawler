package com.cnki.ksp.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cnki.ksp.beans.BaseBean;

public abstract class BaseDao<T extends BaseBean> implements DaoInterface<T> {

	private Class<T> entityClass;

	protected Session session;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
	}

	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cnki.ksp.core.DaoInterface#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public T saveOrUpdate(T t) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(t);
			session.getTransaction().commit();
			return t;
		} finally {
			closeSession();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cnki.ksp.core.DaoInterface#delete(java.lang.Object)
	 */
	@Override
	public void delete(T t) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.remove(t);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cnki.ksp.core.DaoInterface#getAll()
	 */
	@Override
	public List<T> getAll() {
		try {
			session = sessionFactory.openSession();
			List<T> arts = session.createQuery("from " + entityClass.getName(), entityClass).getResultList();
			return arts;
		} finally {
			closeSession();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cnki.ksp.core.DaoInterface#get(java.lang.Integer)
	 */
	@Override
	public T get(Integer id) {
		try {
			session = sessionFactory.getCurrentSession();
			return session.find(entityClass, id);
		} finally {
			session.close();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.cnki.ksp.core.DaoInterface#checkDuplicated(java.lang.Object)
	 */
	@Override
	public List<Integer> checkDuplicated(T t) {
		return null;
	}

	protected void closeSession() {
		if (null != session && session.isOpen()) {
			session.close();
		}
	}
}
