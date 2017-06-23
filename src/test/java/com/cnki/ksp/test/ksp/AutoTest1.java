package com.cnki.ksp.test.ksp;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;

public class AutoTest1 {

	@Test
	public void testLoadApp() {
		CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);
		try {
			cc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
