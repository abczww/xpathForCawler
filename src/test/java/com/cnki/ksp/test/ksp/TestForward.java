package com.cnki.ksp.test.ksp;

import java.io.IOException;
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

public class TestForward {

	private JXDocument xdoc;
	String webSite = "http://club.autohome.com.cn";
	String entranceUrl = "http://club.autohome.com.cn/bbs/forum-c-623-1.html?orderby=dateline&qaType=-1";
	boolean isNeedForward = true;

	List<String> urls = new ArrayList<String>();

	@Before
	public void before() throws Exception {
	}

	@Test
	public void listAllUrls()
			throws NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException, IOException, InterruptedException {
		getAllUrlsByXPath(entranceUrl);
	}

	private void getAllUrlsByXPath(String url) throws XpathSyntaxErrorException, IOException, InterruptedException {
		Document doc = Jsoup.connect(url).timeout(10000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
		xdoc = new JXDocument(doc);
		System.out.println(url);
		List<Object> rs = xdoc.sel("//*[@id='subcontent']/dl/dt/a");
		
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				String turl = webSite + ele.attr("href");
				urls.add(turl);
				System.out.println(turl + " : " + urls.size());
			}
		}

		if (isNeedForward) {
			String forwardUrl = getForwardUrl(url);
			if (null == forwardUrl) {
				return;
			}
			
			getAllUrlsByXPath(forwardUrl);
		}
	}

	private String getForwardUrl(String url) throws XpathSyntaxErrorException, IOException {
		//  //*[@id="subcontent"]/div[1]/div[2]/a[6]
		List<Object> rs = xdoc.sel("//*[@id='subcontent']/div/div/a[@class='afpage']");
		String reValue = null;
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				reValue = webSite + ele.attr("href");
			}
		}
		return reValue;
	}
}