package xpath.jx;

import java.io.IOException;

import org.junit.Test;

import xpath.abczww.bjcar.crawler.core.AppContext;
import xpath.abczww.bjcar.crawler.core.CrawlerController;
import xpath.abczww.bjcar.crawler.core.Processor;

public class AutoTest1 {
	
	@Test
	public void testLoadApp(){
		CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);
		try {
			cc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
