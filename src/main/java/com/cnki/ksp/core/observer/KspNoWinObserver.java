package com.cnki.ksp.core.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnki.ksp.core.Observer;

public class KspNoWinObserver implements Observer {
	private static Logger logger = LoggerFactory.getLogger(KspNoWinObserver.class);

	private KspNoWinObserver() {

	}

	static synchronized KspNoWinObserver getIntance() {
		KspNoWinObserver kspObserver = new KspNoWinObserver();
		return kspObserver;
	}

	public void appendInfo(String info) {
		System.out.println(info);

		logger.info(info);
	}

	public void appendInfo(String format, Object... args) {
		String info = String.format(format, args);
		System.out.println(info);
		logger.info(info);
	}
}
