package com.cnki.ksp.core;

import java.util.List;

/**
 * the dao operations, including: insert, update, get, getAll, delete, and check
 * methods.
 * 
 * @version 2017.7.20
 * @author windhut
 *
 * @param <T>
 */
public interface DaoInterface<T> {

	/**
	 * save or update a dao object.
	 * 
	 * @param t,
	 *            the object we want to save.
	 * @return t, the object after saved.
	 */
	public T saveOrUpdate(T t);

	/**
	 * get all objects.
	 * 
	 * @return, all object.
	 */
	public List<T> getAll();

	/**
	 * get a object by id.
	 * 
	 * @param id
	 * @return
	 */
	public T get(Integer id);

	/**
	 * remove object.
	 * 
	 * @param t
	 */
	public void delete(T t);

	/**
	 * check the duplicated records by object.
	 * 
	 * @param t
	 * @return the object list, if the list's size is greater than 0, that means
	 *         got duplicated records.
	 */
	public List<T> checkDuplicated(T t);
}
