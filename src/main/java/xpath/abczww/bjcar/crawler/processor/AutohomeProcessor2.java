package xpath.abczww.bjcar.crawler.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import xpath.abczww.bjcar.crawler.core.AbsProcessor;

public class AutohomeProcessor2 extends AbsProcessor implements Runnable {

	private JXDocument doubanTest;

	public void run() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://club.autohome.com.cn/bbs/thread-c-623-63246963-1.html")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();

			doubanTest = new JXDocument(doc);
			String title = this.getContentByXPath(xTitle);
			String date = this.getContentByXPath(xDate);
			String content = this.getContentByXPath(xContent);

			System.out.println("Title: " + title);
			System.out.println("Date:" + date);
			System.out.println("Content" + content);
			//getReplysByXPath(xReply);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XpathSyntaxErrorException e) {
			e.printStackTrace();
		}

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
		System.out.println("current xpath:" + xpath);
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
