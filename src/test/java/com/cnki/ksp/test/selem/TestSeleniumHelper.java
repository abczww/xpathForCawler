package com.cnki.ksp.test.selem;

import org.junit.Test;

import com.cnki.ksp.helper.SeleniumHelper;

public class TestSeleniumHelper {

	@Test
	public void test1() {
		for (int i = 0; i < 10; i++) {
			String url = "https://h5.weidian.com/m/weidian-buyer/item/index.html?itemId=190000000" + i;
			try {
				Thread d = new Thread(new SeleniumHelper(url));
				d.start();
				Thread.sleep(2000);
			} catch (Exception e) {

			}
		}
		
		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
