package xpath.abczww.bjcar.crawler.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xpath.abczww.bjcar.crawler.core.CrawlerController;
import xpath.abczww.bjcar.crawler.core.ExecutorPool;
import xpath.abczww.bjcar.crawler.core.Processor;
import xpath.abczww.bjcar.crawler.processor.AutohomeProcessor;

/**
 * The crawler for Autohome(汽车之家). it could list all articles of autohome and
 * save it to db.
 * 
 * @author william
 * @version 1.0
 *
 */
public class AutohomeController implements CrawlerController {
	/** the forward urls. */
	protected List<String> urls;
	/** the entrance of the website. */
	protected String entranceUrl;
	/** the base url of the website. */
	protected String webSite;
	/** if the page need to analysis the forward pages. */
	protected boolean isNeedForward;
	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	public void execute() throws IOException {
		if (isNeedForward) {
			generateForwardUrls();
			for (String url : urls) {
				Processor processor = new AutohomeProcessor(url);
				putProcessorToPool(processor);
				// for test.
				break;
			}
		} else {
			Processor processor = new AutohomeProcessor(entranceUrl);
			putProcessorToPool(processor);
		}
	}

	private void generateForwardUrls() throws IOException {
		urls = new ArrayList<String>();
		findForwardPage(entranceUrl);
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
		if (urls.size() >= 2) {
			return false;
		}

		return true;
	}

	public void setEntranceUrl(String webSite, String url, boolean isNeedForward) {
		this.webSite = webSite;
		this.entranceUrl = url;
		this.isNeedForward = isNeedForward;
	}

	public boolean isLoadPageSuccess() {
		return true;
	}
}
