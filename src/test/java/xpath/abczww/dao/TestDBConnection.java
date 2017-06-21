package xpath.abczww.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xpath.abczww.bjcar.beans.Article;
import xpath.abczww.bjcar.crawler.core.AppContext;
import xpath.abczww.bjcar.crawler.core.CrawlerController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resourse/applicationContext.xml" })
public class TestDBConnection {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void testConenction() {
		CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);

		SqlSessionFactory factory = sqlSessionTemplate.getSqlSessionFactory();
		SqlSession sqlSession = factory.openSession(ExecutorType.BATCH.BATCH, false);

		sqlSessionTemplate.selectList("Article.getAllArticles");
	}

	@Test
	public void insertArticle() {
		Article art = getTheArticle();
		sqlSessionTemplate.insert("Article.saveArticle", art);
	}

	private Article getTheArticle() {
		Article artl = new Article();
		artl.setGatherTime(null);
		artl.setCarType("bj40");
		artl.setCarFirm("beijing car");
		artl.setArticleWebsite("www.autohome.com.cn");
		artl.setarticleUrl("http://www.autohome.com.cn");
		artl.setArticleAuthor("NotMe");
		artl.setArticleTime("2017-06-21");
		artl.setArticleTitle("This is a test");
		artl.setArticleContent("添加包的依赖，编辑pom.xml文件添加如下依赖");
		artl.setCreatedBy("zww");
		artl.setUpdatedBy("zww");

		return artl;
	}

}
