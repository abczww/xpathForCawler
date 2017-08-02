package com.cnki.ksp.test.conn;

import java.util.Properties;

import org.junit.Test;

import com.cnki.ksp.core.observer.ObserverFactory;
import com.cnki.ksp.processor.PeopleCnProcessor;

public class TestProcessor {

	private Properties getProperties() {
		Properties prop = new Properties();
		prop.setProperty("kspId", 1501 + "");
		prop.setProperty("xTitle", "//html/body/div[4]/h1");
		prop.setProperty("xAuthor", "//*[@id='rwb_navpath']/a[2]");
		prop.setProperty("xTime", "//html/body/div[4]/div/div[1]");
		prop.setProperty("xContent", "//*[@id='rwb_zw']");

		return prop;
	}

	@Test
	public void testProcessor() {
		String url = "http://auto.people.com.cn/n1/2017/0801/c1005-29441001.html";
		PeopleCnProcessor pro = new PeopleCnProcessor(url, this.getProperties(),
				ObserverFactory.getObserverByName(ObserverFactory.KSP_CMD_OBSERVER));
		pro.execute();
	}

}
