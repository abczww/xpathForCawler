package com.cnki.ksp.core;

import java.util.List;

public abstract class BaseDao<T> {

	public abstract void save(T art);
	
	public abstract List<T> getAll();


}
