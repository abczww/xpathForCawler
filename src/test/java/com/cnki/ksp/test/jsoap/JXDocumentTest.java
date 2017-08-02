package com.cnki.ksp.test.jsoap;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.cnki.ksp.test.Seeds;

import cn.wanghaomiao.xpath.model.JXDocument;

public class JXDocumentTest {

	private JXDocument testDocx;

	@Test
	public void testIterator() throws Exception {
		String html = "<html><body><script>console.log('aaaaa')</script><div class='test'>搜易贷致力于普惠金融，专注于互联网投资理财与小额贷款，搭建中国最大、用户体验最好的个人及中小企业的互联网信贷平台</div><div class='xiao'>Two</div></body></html>";
		if (testDocx == null) {
			Document doc = Jsoup.parse(html);
			Element elem = doc.body();
			iteratorBody(elem);
		}
	}

	private void iteratorBody(Element elem) {
		Elements eles = elem.children();
		Iterator<Element> it = eles.iterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			if (element.childNodeSize() > 1) {
				iteratorBody(element);
			} else {
				String text = element.text();
				if (text.length() > 10) {
					System.out.println(element.text());
				}
			}
		}

	}


	@Test
	public void testBody() throws Exception {
		Document doc = Jsoup.connect(Seeds.URL4)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
		testDocx = new JXDocument(doc);
		Element elem = doc.body();
		iteratorBody(elem);
	}

}