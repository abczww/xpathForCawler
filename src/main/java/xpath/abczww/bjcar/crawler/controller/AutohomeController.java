package xpath.abczww.bjcar.crawler.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import xpath.abczww.bjcar.crawler.core.AbsController;
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
public class AutohomeController extends AbsController {

	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	private JXDocument xdoc;

	public void execute() throws Exception {
		initDoc();
		if (isNeedForward) {
			getAllUrlsByXPath(entranceUrl);
			for (String url : urls) {
				// Processor processor = new AutohomeProcessor(url);
				// putProcessorToPool(processor);
				// for test.
				break;
			}
		} else {
			Processor processor = new AutohomeProcessor(entranceUrl);
			putProcessorToPool(processor);
		}
	}

	private void initDoc() throws IOException {
		Document doc = Jsoup.connect(entranceUrl).timeout(timeout)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();

		xdoc = new JXDocument(doc);
		urls = new ArrayList<String>();
	}

	private void getAllUrlsByXPath(String url) throws XpathSyntaxErrorException, IOException, InterruptedException {
		Document doc = Jsoup.connect(url).timeout(10000)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
		xdoc = new JXDocument(doc);
		System.out.println(url);
		List<Object> rs = xdoc.sel(xUrl);
		
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
		List<Object> rs = xdoc.sel(xForward);
		String reValue = null;
		for (Object o : rs) {
			if (o instanceof Element) {
				Element ele = (Element) o;
				reValue = webSite + ele.attr("href");
			}
		}
		return reValue;
	}

	protected boolean isContinue(Document doc) {
		if (urls.size() >= 2) {
			return false;
		}

		return true;
	}

	public boolean isLoadPageSuccess() {
		return true;
	}
}
