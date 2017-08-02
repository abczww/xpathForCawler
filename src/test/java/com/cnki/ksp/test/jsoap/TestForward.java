package com.cnki.ksp.test.jsoap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.ArticleType;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/applicationContext.xml" })
public class TestForward {

	protected Properties controllerProperties;
	protected Properties processorProperties;
	protected List<String> articleUrls;
	private JXDocument xdoc;

	// private String entranceUrl = null;
	private String webSite = "http://auto.people.com.cn";
	private int timeout = 5000;

	@Before
	public void init() {
		articleUrls = new ArrayList<String>();
	}

	@Test
	public void testForward() throws Exception {
		this.getAllUrlsByTemplate();
	}

	public List<String> getAllUrlsByTemplate() throws Exception {
		String entranceTemplate = "http://auto.people.com.cn/GB/1051/index#pageNum.html";
		int pageStart = 1;
		articleUrls.clear();

		while (true) {
			String url = entranceTemplate.replaceAll("#pageNum", pageStart + "");
			if (!analyseUrlsAndContinue(url)) {
				break;
			}
			pageStart++;
		}
		return articleUrls;
	}

	public boolean analyseUrlsAndContinue(String url) throws Exception {
		JXDocument xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, timeout, 5000);

		List<JXNode> rs = xdoc.selN("//html/body/div[6]/div[1]/dl/dt");
		if (rs == null || rs.size() == 0) {
			return false;
		}
		for (JXNode node : rs) {
			XPathUtilTools xpathTool = new XPathUtilTools(xdoc);
			String turl = xpathTool.getContentByXPath(node, "//a", "href");
			String ttitle = xpathTool.getContentByXPath(node, "//a", null);
			Article art = new Article(1501, turl,ArticleType.PILICY.getType());
			turl = turl.toLowerCase().startsWith("http") ? turl : (webSite + turl);
			System.out.println(turl + " : " + ttitle);
			art.setTitle(ttitle);
		}

		return true;
	}
}
