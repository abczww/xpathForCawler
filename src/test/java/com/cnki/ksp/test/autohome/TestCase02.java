package com.cnki.ksp.test.autohome;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.KspObserver;

public class TestCase02 {

	@Test
	public void testLoadApp() {
		CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);
		try {
			cc.init(KspObserver.getIntance());
			cc.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
