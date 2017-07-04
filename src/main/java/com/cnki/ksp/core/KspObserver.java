package com.cnki.ksp.core;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * an observer of crawler. it could save log and print console.
 * 
 * @author william
 * @version 2017.6
 *
 */
public class KspObserver {

	private JTextArea jta_defaultInfoHandler = new JTextArea();
	private static Map<String, KspObserver> kspObserverMap = null;
	private static Logger logger = LoggerFactory.getLogger(KspObserver.class);

	private KspObserver() {

	}

	public static synchronized KspObserver getIntance(String className) {
		if (kspObserverMap == null) {
			kspObserverMap = new HashMap<>();
		}

		if (kspObserverMap.get(className) == null) {
			KspObserver kspObserver = new KspObserver();
			kspObserverMap.put(className, kspObserver);
		}
		return kspObserverMap.get(className);
	}

	public void setHandler(JTextArea info) {
		this.jta_defaultInfoHandler = info;
	}

	public void appendInfo(String info) {
		System.out.println(info);
		jta_defaultInfoHandler.append(info + "\n");
		logger.info(info);
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}

	public void appendInfo(String format, Object... args) {
		String info = String.format(format, args);
		System.out.println(info);
		logger.info(info);
		jta_defaultInfoHandler.append(info + "\n");
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}

}
