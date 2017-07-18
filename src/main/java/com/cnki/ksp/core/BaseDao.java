package com.cnki.ksp.core;

import java.util.List;

import com.cnki.ksp.beans.BaseBean;

public abstract class BaseDao<T extends BaseBean> {

	public abstract void save(T t);
	
	public abstract List<T> getAll();


}
