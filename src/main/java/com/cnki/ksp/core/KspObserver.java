package com.cnki.ksp.core;

import javax.swing.JTextArea;

public class KspObserver {
	
	private JTextArea jta_defaultInfoHandler = new JTextArea();
	private static KspObserver kspObserver;
	private KspObserver(){
		
	}
	
	public static synchronized KspObserver getIntance(){
		if(kspObserver == null){
			kspObserver = new KspObserver();
		}
		return kspObserver;
	}
	
	public void setHandler(JTextArea info){
		this.jta_defaultInfoHandler = info;
	}
	
	public void appendInfo(String info){
		System.out.println(info);
		jta_defaultInfoHandler.append(info + "\n");
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}
	
	public void appendInfo(String format, Object... args){
		String info = String.format(format, args);
		System.out.println(info);
		jta_defaultInfoHandler.append(info);
		jta_defaultInfoHandler.setCaretPosition(jta_defaultInfoHandler.getDocument().getLength());
	}

}
