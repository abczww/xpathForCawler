package com.cnki.ksp.test.autohome;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.cnki.ksp.core.XPathUtilTools;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class TestCase01 {
	String url = "http://club.autohome.com.cn/bbs/thread-c-623-64162945-1.html";

	@Test
	public void analyseArticle() throws Exception {
		Document doc = Jsoup.connect(url).timeout(10000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
		JXDocument xdoc = new JXDocument(doc);
		List<JXNode> authorRs = xdoc.selN("//*[@id='F0']/div[1]/ul[1]/li[1]/a[1]");
		List<JXNode> titleRs = xdoc.selN("//*[@id='F0']/div[2]/div[2]/h1/div");
		List<JXNode> dateRs = xdoc.selN("//*[@id='F0']/div[2]/div[1]/span[2]");
		List<JXNode> contentRs = xdoc.selN("//*[@id='F0']/div[2]/div[2]/div[1]/div/div[2]");

		XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
		String content = xpathTool.getContentByXPath("//*[@id='F0']/div[2]/div[2]/div[1]/div/div[2]");
		System.out.println(content);
	}

}
