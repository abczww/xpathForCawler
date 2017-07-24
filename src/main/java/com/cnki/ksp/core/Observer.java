package com.cnki.ksp.core;

public interface Observer {
	
	public void appendInfo(String info);
	
	public void appendInfo(String format, Object... args);

}
