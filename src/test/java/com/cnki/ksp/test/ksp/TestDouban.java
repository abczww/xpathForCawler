package com.cnki.ksp.test.ksp;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class TestDouban {

	private JXDocument doubanTest;

	@Before
	public void before() throws Exception {
		if (doubanTest == null) {
			Document doc = Jsoup.connect("http://club.autohome.com.cn/bbs/forum-c-623-1.html?orderby=dateline&qaType=-1#pvareaid=101061")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
			doubanTest = new JXDocument(doc);
		}
	}

	@Test
	public void listAllUrls() throws NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException {
		String xpath = "//*[@id='subcontent']/dl/dt/a";
		
		System.out.println("current xpath:" + xpath);
		List<String> urls = new ArrayList<String>();
		List<Object> rs = doubanTest.sel(xpath);
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				urls.add(ele.attr("href"));
					
			}			
		}
		
		
	}
}