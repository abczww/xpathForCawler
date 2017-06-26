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

public class TestDouban2 {

	private JXDocument doubanTest;

	@Before
	public void before() throws Exception {
		if (doubanTest == null) {
			Document doc = Jsoup.connect("http://club.autohome.com.cn/bbs/thread-c-623-64162945-1.html")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
			doubanTest = new JXDocument(doc);
		}
	}

	@Test
	public void listAllUrls() throws NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException {
		String xTitle = "//*[@id='consnav']/span[4]/text()";
		////*[@id="consnav"]/span[4]
		String xDate = "//*[@xname='date']/text()";
		String xContent = "//*[@id='F0']/div[2]/div[2]/div[1]/div";
		////*[@id="F0"]/div[2]/div[2]
		String xReply = "//*[@id='F$$']/div[2]/div/div[2]/div";
		//// *[@id="F2"]/div[2]/div/div[2]/div/div[2]
		//// *[@id="F16"]/div[2]/div/div[2]/div/div[2]
		//// *[@id="F22"]/div[2]/div/div[2]/div/div[2]
		
		String xAuthor = "//*[@id='F0']/div[1]/ul[1]/li[1]";
		////*[@id="F0"]/div[1]/ul[1]/li[1]/a
		// //*[@id="F0"]/div[1]/ul[1]/li[1]/a[1]

		String title = this.getContentByXPath(xTitle);
		String date = this.getContentByXPath(xDate);
		String content = this.getContentByXPath(xContent);
		String author = this.getContentByXPath(xAuthor);
		
		System.out.println(author);
		//getReplysByXPath(xReply);

		System.out.println(title);
		//System.out.println(date);
		//System.out.println(content);
		// System.out.println(reply);
	}

	private void getReplysByXPath(String xpath) throws XpathSyntaxErrorException {
		int count = 1;
		String tempPath = "";
		while (true) {
			tempPath = xpath.replace("$$", count + "");
			String reply = this.getContentByXPath(tempPath);
			System.out.println(reply);
			if (reply == null) {
				break;
			}
			count++;
		}
	}

	private String getContentByXPath(String xpath) throws XpathSyntaxErrorException {
		List<String> urls = new ArrayList<String>();
		List<Object> rs = doubanTest.sel(xpath);
		String reValue = null;
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				urls.add(ele.attr("href"));
				reValue = ele.toString();
			} else {
				reValue = null == o ? "" : o.toString();
			}
		}
		return reValue;
	}
}