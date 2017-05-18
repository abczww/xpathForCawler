package xpath.abczww.bjcar.crawler.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xpath.abczww.bjcar.crawler.core.CawlerController;
import xpath.abczww.bjcar.crawler.core.ExecutorPool;
import xpath.abczww.bjcar.crawler.core.Processor;
import xpath.abczww.bjcar.crawler.processor.AutohomeProcessor;

public class AutohomeController extends CawlerController {

	@Override
	protected void generateForwardUrls() throws IOException {
		urls = new ArrayList<String>();
		findForwardPage(entranceUrl);
	}
	
	public void execute() throws IOException {
		if (isNeedForward) {
			generateForwardUrls();
			for (String url : urls) {
				Processor processor = new AutohomeProcessor(url);
				putProcessorToPool(processor);
			}
		} else {
			Processor processor = new AutohomeProcessor(entranceUrl);
			putProcessorToPool(processor);
		}
	}

	private void putProcessorToPool(Processor processor) {
		ExecutorPool.put(processor);
	}

	private void findForwardPage(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements pages = doc.getElementsByAttributeValue("class", "pages");
		if (pages.size() < 1) {
			System.out.println("Can't load autohome");
			return;
		}

		Element pageAre = pages.get(0);
		Elements afpage = pageAre.getElementsByAttributeValue("class", "afpage");
		if (afpage.size() < 1) {
			return;
		}

		Element afUrl = afpage.get(0);
		if (null != afUrl) {
			String forwardUrl = webSite + afUrl.attr("href");
			System.out.println(forwardUrl);
			urls.add(forwardUrl);
			if (!isContinue(doc)) {
				return;
			}
			findForwardPage(forwardUrl);
		}
		
		loadPageSuccessFlag = true;
	}

	protected boolean isContinue(Document doc) {
		if (urls.size() >= 10) {
			return false;
		}

		return true;
	}
}
