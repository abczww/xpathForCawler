package com.cnki.ksp.test.conn;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resourse/applicationContext.xml" })
public class TestDBConnection {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void testConenction() {

		List<Article> arts = sqlSessionTemplate.selectList("Article.getAllArticles");
		Assert.assertTrue(arts.size() > 0);
	}

	@Test
	public void insertArticle() {
		Article art = getTheArticle();
		//sqlSessionTemplate.insert("Article.saveArticle", art);
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
