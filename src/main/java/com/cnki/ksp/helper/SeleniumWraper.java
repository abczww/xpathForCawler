package com.cnki.ksp.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumWraper {
	{
		System.setProperty("webdriver.chrome.driver", "/Users/windhut/local/selenium/chromedriver");
	}

	private WebDriver webDriver;

	SeleniumWraper(String url) {
		webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		webDriver.get(url);
	}
	
	public String getElementTextById(String id){
		String txt = null;
		try {
			WebElement ele = webDriver.findElement(By.id(id));
			txt = ele.getText();
		} catch (NoSuchElementException nse) {
			System.out.printf("Can't find element by className: %s from %s\n", id, webDriver.getCurrentUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	public String getElementTextByXPath(String xPath) {
		String txt = null;
		try {
			WebElement ele = webDriver.findElement(By.xpath(xPath));
			txt = ele.getText();
		} catch (NoSuchElementException nse) {
			System.out.printf("Can't find element by className: %s from %s\n", xPath, webDriver.getCurrentUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	public String getElementsTextByXPath(String xPath) {
		StringBuilder txt = new StringBuilder(512);
		try {
			List<WebElement> eles = webDriver.findElements(By.xpath(xPath));
			for (WebElement ele : eles) {
				txt.append(ele.getText());
			}
		} catch (NoSuchElementException nse) {
			System.out.printf("Can't find element by className: %s from %s\n", xPath, webDriver.getCurrentUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "".equals(txt.toString()) ? null : txt.toString();
	}

	public String getElementTextByClassName(String className) {
		String txt = null;
		try {
			WebElement ele = webDriver.findElement(By.className(className));
			txt = ele.getText();
		} catch (NoSuchElementException nse) {
			System.out.printf("Can't find element by className: %s from %s\n", className, webDriver.getCurrentUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	public String getElementsTextByClassName(String className) {
		StringBuilder txt = new StringBuilder(512);
		try {
			List<WebElement> eles = webDriver.findElements(By.className(className));
			for (WebElement ele : eles) {
				txt.append(ele.getText());
			}
		} catch (NoSuchElementException nse) {
			System.out.printf("Can't find element by className: %s from %s\n", className, webDriver.getCurrentUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "".equals(txt.toString()) ? null : txt.toString();
	}

	public void forwardByXPath(String xPath, int timeout) {
		WebElement e = webDriver.findElement(By.xpath(xPath));
		e.click();
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void close() {
		webDriver.close();
	}

}
