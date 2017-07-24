package com.cnki.ksp.core.observer;

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnki.ksp.core.Observer;

/**
 * an observer of crawler. it could save log and print console.
 * 
 * @author william
 * @version 2017.6
 *
 */
public class KspObserver implements Observer {

	private JTextArea jta_defaultInfoHandler = new JTextArea();
	private static Logger logger = LoggerFactory.getLogger(KspObserver.class);

	private KspObserver() {

	}

	static synchronized KspObserver getIntance() {
		KspObserver kspObserver = new KspObserver();

		return kspObserver;
	}

	public void setHandler(JTextArea info) {
		this.jta_defaultInfoHandler = info;
	}

	public void appendInfo(String info) {
		System.out.println(info);
		if (jta_defaultInfoHandler.getRows() >= 26) {
			jta_defaultInfoHandler.setText("");
		}
		jta_defaultInfoHandler.append(info + "\n");
		logger.info(info);
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}

	public void appendInfo(String format, Object... args) {
		String info = String.format(format, args);
		System.out.println(info);
		logger.info(info);
		if (jta_defaultInfoHandler.getRows() >= 26) {
			jta_defaultInfoHandler.setText("");
		}
		jta_defaultInfoHandler.append(info + "\n");
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}

}
