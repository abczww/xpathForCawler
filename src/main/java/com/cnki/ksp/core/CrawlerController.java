package com.cnki.ksp.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * the controller of the crawler. funciton list:
 * it creates a new crawler and put it to thread pool and run it.
 * it has a flag, could tell the invoker if it starts success or not.
 * it could list a url and get all the farword urls.
 * 
 * 
 * @author william
 * @version 1.0
 *
 */
public interface CrawlerController {

	/**
	 * the core method of Controller, the method should analysis the url and put
	 * the processor to thread pool.
	 * 
	 * @throws IOException
	 */
	public void execute() throws Exception;

	/**
	 * if load page success.
	 * 
	 * @return loadPageSuccessFlag, if load page success return true or return
	 *         false;
	 */
	public boolean isLoadPageSuccess() ;
}
