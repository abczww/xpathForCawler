package xpath.abczww.temp;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import cn.wanghaomiao.xpath.model.JXDocument;
import junit.framework.Assert;
import xpath.abczww.Seeds;

public class JXDocumentTest {

    private JXDocument underTest;

    private JXDocument doubanTest;

    private JXDocument custom;

    @Before
    public void before() throws Exception {
        String html = "<html><body><script>console.log('aaaaa')</script><div class='test'>搜易贷致力于普惠金融，专注于互联网投资理财与小额贷款，搭建中国最大、用户体验最好的个人及中小企业的互联网信贷平台</div><div class='xiao'>Two</div></body></html>";
        underTest = new JXDocument(html);
        if (doubanTest == null) {
            Document doc = Jsoup.connect(Seeds.URL1).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
            doubanTest = new JXDocument(doc);
        }
        custom = new JXDocument("<li><b>性别：</b>男</li>");
    }
    
    @Test
    public void testSeeds() throws Exception{
    	String xpath = "//*[@id='subcontent']/dl[8]/dt/a";
    	List<Object> res = doubanTest.sel(xpath);
    	Assert.assertTrue(res.size() >0);
    }

    

}