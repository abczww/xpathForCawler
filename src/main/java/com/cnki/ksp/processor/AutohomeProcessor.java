package com.cnki.ksp.processor;

import java.io.IOException;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.Observer;
import com.cnki.ksp.core.Processor;

public class AutohomeProcessor implements Processor {

	private static String HOME = "http://club.autohome.com.cn";
	private String entranceUrl;

	public AutohomeProcessor(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}

	public void run() {
		try {
			Document doc = Jsoup.connect(entranceUrl).get();
			Element subcontent = doc.getElementById("subcontent");
			Elements listdl = subcontent.getElementsByAttributeValue("class", "list_dl");
			assert listdl.size() > 0;
			for (Element elemt : listdl) {
				Article art = new Article();
				art.setArticleTitle(elemt.getElementsByAttributeValue("class", "a_topic").get(0).html());
				art.setGatherTime(elemt.getElementsByAttributeValue("class", "tdate").get(0).html());
				art.setArticleAuthor(elemt.getElementsByAttributeValue("class", "linkblack").get(0).html());
				art.setCarType("bj40");
				System.out.println(art);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setEntranceURL(String url) {
		this.entranceUrl = url;
	}

	public Set<Article> getArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEarliestDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObserver(Observer observer) {
		// TODO Auto-generated method stub

	}

}
