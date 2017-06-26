package com.cnki.ksp.core;

import java.util.ArrayList;
import java.util.List;

public class CompleteHelper {

	private static int threadCount = 0;
	private static int counter = 0;
	private static List<String> failUrls = new ArrayList<String>();

	public static synchronized void initThreadCount(int count) {
		CompleteHelper.threadCount = count;
		failUrls.clear();
	}

	public static synchronized boolean isCompleted() {
		return counter == threadCount;
	}

	public static synchronized void complete(Runnable r) {
		counter++;
	}

	public static void pushFialUrl(String url) {
		failUrls.add(url);
	}

	public static List<String> getFailUrls() {
		return failUrls;
	}

}
