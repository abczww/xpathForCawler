package com.cnki.ksp.test.autohome;

import org.junit.Test;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.observer.ObserverFactory;

public class TestCase02 {

	@Test
	public void testLoadApp() {
		CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);
		try {
			cc.init(ObserverFactory.getObserverByName(ObserverFactory.KSP_CMD_OBSERVER));
			cc.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
