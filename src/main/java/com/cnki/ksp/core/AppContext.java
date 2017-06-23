package com.cnki.ksp.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
	final static ApplicationContext ctx;
	static {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public static <T> T getBean(Class<T> cls) {
		return ctx.getBean(cls);
	}

	public static <T> T getBean(String name, Class<T> cls) {
		return ctx.getBean(name, cls);
	}

}
