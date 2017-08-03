package com.cnki.ksp.test.weixin;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;
import com.cnki.ksp.test.Seeds;

import cn.wanghaomiao.xpath.model.JXDocument;

public class JXDocumentTest {

	private JXDocument testDocx;

	@Test
	public void testLoad() throws Exception{
		String url = "https://h5.weidian.com/m/weidian-buyer/item/index.html?itemId=1800000020";
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000, 1000);
		XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
		
		String txt = xpathTool.getContentByXPath("//*[@id='container']/section[3]/div[2]/text()");
		System.out.println(txt);
	}

}