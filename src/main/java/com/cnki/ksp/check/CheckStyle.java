package com.cnki.ksp.check;

import java.util.Properties;

import com.cnki.ksp.controller.autohome.AutohomeBBSController;
import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.StyleChangedException;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class CheckStyle {

	private CheckStyle() throws Exception {
		//check_AutohomeBBSController();
		//check_AutohomeBBSProcessor();
		check_people();
		System.out.println("Check Complete");
	}

	private void check_AutohomeBBSController() throws Exception {
		AutohomeBBSController coller = AppContext.getBean("autohomeController_bj40", AutohomeBBSController.class);
		Properties prop = coller.getControllerProperties();
		String entranceUrl = String.valueOf(prop.getProperty("entranceUrl"));
		
		JXDocument xdoc;
		xdoc = JSoupConnectionHelper.getXDocumentFromUrl(entranceUrl, 5000);
		System.out.println(xdoc);
		XPathUtilTools xpTools = new XPathUtilTools(xdoc);

		checkPath(xpTools, entranceUrl, "xTitle", String.valueOf(prop.getProperty("xTitle")));
		checkPath(xpTools, entranceUrl, "xAuthor", String.valueOf(prop.getProperty("xAuthor")));
		checkPath(xpTools, entranceUrl, "xDate", String.valueOf(prop.getProperty("xDate")));
		checkPath(xpTools, entranceUrl, "xForward", String.valueOf(prop.getProperty("xForward")));

	}
	
	
	private void check_AutohomeBBSProcessor() throws Exception {
		AutohomeBBSController coller = AppContext.getBean("autohomeController_bj40", AutohomeBBSController.class);
		Properties prop = coller.getProcessorProperties();
		String entranceUrl = "http://club.autohome.com.cn/bbs/thread-c-3800-64948092-1.html";
		
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(entranceUrl, 5000);
		System.out.println(xdoc);
		XPathUtilTools xpTools = new XPathUtilTools(xdoc);

		checkPath(xpTools, entranceUrl, "xTitle", String.valueOf(prop.getProperty("xTitle")));
		checkPath(xpTools, entranceUrl, "xAuthor", String.valueOf(prop.getProperty("xAuthor")));
		// //*[@id='F0']/div[2]/div[2]/div[1]/div/div[2]
		checkPath(xpTools, entranceUrl, "xContent", String.valueOf(prop.getProperty("xContent")));
		//checkPath(xpTools, entranceUrl, "xReply", String.valueOf(prop.getProperty("xReply")));
		checkPath(xpTools, entranceUrl, "xHits", String.valueOf(prop.getProperty("xHits")));
		
	}
	
	private void check_people() throws Exception{
		String url = "http://auto.people.com.cn/GB/1051/index#pageNum.html";
		url = url.replaceAll("#pageNum", "1");
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000);
		XPathUtilTools xpTools = new XPathUtilTools(xdoc);
		
		String xpath = "//html/body/div[6]/div[1]/div/a[text()='下一页']/@href";
		String nextUrl = xpTools.getContentByXPath(xpath);
		
		
		
		System.out.println(nextUrl);
	}

	private boolean checkPath(XPathUtilTools xpathTools, String url, String element, String xPath) {
		boolean isChanged = true;
		try {
			String content = xpathTools.getContentByXPath(xPath);
			if (null != content) {
				isChanged = false;
			}

		} catch (XpathSyntaxErrorException e) {
			e.printStackTrace();
		} catch (StyleChangedException e) {
			isChanged = true;
		}

		if (isChanged) {
			String changedStr = "Changed: " + url + ", " + element + ", " + xPath;
			System.out.println(changedStr);
			info.append(changedStr + "\n");

		} else {
			System.out.println("OK: " + element + " : " + url);
		}
		return isChanged;
	}

	private StringBuilder info = new StringBuilder(512);

	public String getChangedStyle() {
		return info.toString();
	}

	public static void main(String[] args) {
		try {
			new CheckStyle();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
