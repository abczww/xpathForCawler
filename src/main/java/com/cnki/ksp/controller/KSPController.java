package com.cnki.ksp.controller;

import java.util.List;

import com.cnki.ksp.core.CrawlerController;

public class KSPController {
	
	List<CrawlerController> crawlers;

	public List<CrawlerController> getCrawlers() {
		return crawlers;
	}

	public void setCrawlers(List<CrawlerController> crawlers) {
		this.crawlers = crawlers;
	}

}
