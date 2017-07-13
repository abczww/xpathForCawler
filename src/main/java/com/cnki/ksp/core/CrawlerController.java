package com.cnki.ksp.core;

import java.io.IOException;

/**
 * the controller of the crawler. funciton list: it creates a new crawler and
 * put it to thread pool and run it. it has a flag, could tell the invoker if it
 * starts success or not. it could list a url and get all the farword urls.
 * 
 * 
 * @author william
 * @version 1.0
 *
 */
public interface CrawlerController {

	public void init(KspObserver observer) throws Exception;

	/**
	 * get the id of the KScorption.
	 * 
	 * @return the id of the KScorption, default value is 0.
	 * 
	 */
	public int getKspId();

	/**
	 * get the name of the KScorption.
	 * 
	 * @return the name of the KScorpion, default value is "".
	 * 
	 * 
	 */
	public String getKspName();

	/**
	 * get the topic or message of the KScorpion.
	 * 
	 * @return the topic or message.
	 * 
	 */
	public String getTopic();

	/**
	 * the core method of Controller, the method should analysis the url and put
	 * the processor to thread pool.
	 * 
	 * @throws IOException
	 */
	public void run();
	
	public void execute() throws Exception;

}
