package com.cnki.ksp.test.autohome;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.observer.ObserverFactory;

public class TestCase03 {
	
	@Test
	public void testLoadApp() {
		CrawlerController cc = AppContext.getBean("autohomeController_bj20_evalute", CrawlerController.class);
		try {
			cc.init(ObserverFactory.getObserverByName(ObserverFactory.KSP_CMD_OBSERVER));
			cc.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
