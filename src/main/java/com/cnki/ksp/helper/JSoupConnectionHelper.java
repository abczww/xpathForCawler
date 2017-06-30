package com.cnki.ksp.helper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.wanghaomiao.xpath.model.JXDocument;

public class JSoupConnectionHelper {
	
	private static long magicSleep = 1600;

	public static JXDocument getXDocumentFromUrl(String url, int timeout) throws Exception {
		JXDocument xdoc = null;

		int tryTimes = 0;
		Thread.sleep(magicSleep);
		while (true) {
			try {
				Document doc = Jsoup.connect(url).timeout(timeout)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
				xdoc = new JXDocument(doc);
				return xdoc;
			} catch (IOException e) {
				tryTimes++;
				if (tryTimes == 3) {
					throw e;
				}
				System.out.println("Socket error, try " + tryTimes + " times, try again after 10s.");
				Thread.sleep(15000);
			}
		}

	}

}
