package com.cnki.ksp.core;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class XPathUtilTools {

	private static long magicSleep = 1700;
	private JXDocument xdoc;

	public XPathUtilTools(JXDocument xdoc) {
		this.xdoc = xdoc;
	}

	public static Document getDocFromUrl(String url, int timeout) throws Exception {
		Document doc = null;
		int tryTimes = 0;
		Thread.sleep(magicSleep);
		while (true) {
			try {
				doc = Jsoup.connect(url).timeout(timeout)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
			} catch (IOException e) {
				tryTimes++;
				if (tryTimes == 3) {
					throw e;
				}
				System.out.println("Socket error, try " + tryTimes + " times, try again after 10s.");
				Thread.sleep(30000);
				//magicSleep = magicSleep + 200;
			}

			if (null != doc) {
				//magicSleep = magicSleep - 2;
				return doc;
			}
		}

	}

	public String getContentByXPath(JXNode node, String xPath, String attr) throws XpathSyntaxErrorException {
		String reValue = "";
		List<JXNode> eles = node.sel(xPath);
		if (eles.size() > 0) {
			if (attr != null && !attr.trim().equals("") && attr.trim().length() > 1) {
				reValue = eles.get(0).getElement().attr("href");
			} else {
				reValue = eles.get(0).getElement().text();
			}
		}
		return reValue;
	}

	public String getContentByXPath(String xPath) throws XpathSyntaxErrorException {
		List<JXNode> rs = xdoc.selN(xPath);
		return getContentByNodeList(rs);
	}

	private String getContentByNodeList(List<JXNode> rs) {
		if (rs.size() > 0) {
			JXNode node = rs.get(0);
			String content = null;
			if (null == node.getElement()) {
				content = node.getTextVal();
			} else {
				content = node.getElement().html();
			}
			return content;
		}
		return null;
	}

}
