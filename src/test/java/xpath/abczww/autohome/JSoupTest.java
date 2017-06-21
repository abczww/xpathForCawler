package xpath.abczww.autohome;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import xpath.abczww.Seeds;
import xpath.abczww.bjcar.beans.Article;

public class JSoupTest {

	// @Test
	public void testHTML() {
		String html = "<html><head><title> 开源中国社区 </title></head>" + "<body><p> 这里是 jsoup 项目的相关文章 </p></body></html>";
		Document doc = Jsoup.parse(html);
		System.out.println(doc.body());
		System.out.println(doc.title());
	}

	private Set<String> urls = new TreeSet<String>();
	private String webSite = "http://club.autohome.com.cn";;

	@Test
	public void getURLS() {
		try {
			Document doc = Jsoup.connect(Seeds.URL1).get();
			Element pageAre = doc.getElementsByAttributeValue("class", "pages").get(0);
			Elements pages = pageAre.getElementsByTag("a");
			for (Element elmt : pages) {
				urls.add(webSite + elmt.attr("href"));
			}
			
			findForwardPage(Seeds.URL1);

			for (String url : urls) {
				System.out.println(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listURLs() throws IOException{
		findForwardPage(Seeds.URL1);
	}

	public void findForwardPage(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Element pageAre = doc.getElementsByAttributeValue("class", "pages").get(0);
		Element afUrl = pageAre.getElementsByAttributeValue("class", "afpage").get(0);
		if (null != afUrl) {
			String forwardUrl = webSite + afUrl.attr("href");
			System.out.println(forwardUrl);
			urls.add(forwardUrl);
			findForwardPage(forwardUrl);
		}
	}

	@Test
	public void loadGetArticle() {
		try {
			Document doc = Jsoup.connect(Seeds.URL1).get();
			Element subcontent = doc.getElementById("subcontent");
			Elements listdl = subcontent.getElementsByAttributeValue("class", "list_dl");
			assert listdl.size() > 0;
			for (Element elemt : listdl) {
				Article art = new Article();
				art.setArticleTitle(elemt.getElementsByAttributeValue("class", "a_topic").get(0).html());
				art.setCreatedTime(elemt.getElementsByAttributeValue("class", "tdate").get(0).html());
				art.setArticleAuthor(elemt.getElementsByAttributeValue("class", "linkblack").get(0).html());
				art.setCarType("bj40");
				System.out.println(art);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
