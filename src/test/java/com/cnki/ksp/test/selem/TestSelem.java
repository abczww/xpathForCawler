package com.cnki.ksp.test.selem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelem {

	// @Test
	public void testBaidu() {
		System.setProperty("webdriver.chrome.driver", "/Users/windhut/local/selenium/chromedriver");
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.get("http://www.baidu.com");
		WebElement kw = webDriver.findElement(By.id("kw"));
		kw.sendKeys("暗算");
		WebElement su = webDriver.findElement(By.id("su"));
		su.click();
		webDriver.close();
		System.out.println("Hello World!");

	}

	@Test
	public void testWeixin() throws InterruptedException {
		// String url =
		// "https://h5.weidian.com/m/weidian-buyer/item/index.html?itemId=1800000020";
		String url = "https://h5.weidian.com/m/weidian-buyer/item/index.html?itemId=1874562273";
		System.setProperty("webdriver.chrome.driver", "/Users/windhut/local/selenium/chromedriver");
		WebDriver webDriver = new ChromeDriver();
		// webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		webDriver.get(url);

		String title = "";
		String shop = "";
		WebElement e = webDriver.findElement(By.xpath("//*[@id='container']/section[3]/div[2]"));
		title = e.getText();
		e = webDriver.findElement(By.xpath("//*[@id='container']/section[4]/div/div[1]/div[1]"));
		shop = e.getText();
		System.out.println("shop: " + shop);
		System.out.println("title: " + title);

		e = webDriver.findElement(By.xpath("//*[@id='container']/section[5]/a"));
		e.click();
		Thread.sleep(5000);

		System.out.println("comments:");
		List<WebElement> wes = webDriver.findElements(By.className("comment"));
		for (WebElement we : wes) {
			System.out.println("--->" + we.getText());
		}

		webDriver.close();
	}

}
