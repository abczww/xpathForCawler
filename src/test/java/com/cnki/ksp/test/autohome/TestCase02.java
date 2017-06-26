package com.cnki.ksp.test.autohome;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;

public class TestCase02 {

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
