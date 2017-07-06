package com.cnki.ksp.test.autohome;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.KspObserver;

public class TestCase03 {
	
	@Test
	public void testLoadApp() {
		CrawlerController cc = AppContext.getBean("autohomeController_bj20_evalute", CrawlerController.class);
		try {
			cc.init(KspObserver.getIntance(this.getClass().getName()));
			cc.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
