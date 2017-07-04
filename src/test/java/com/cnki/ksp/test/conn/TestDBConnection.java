package com.cnki.ksp.test.conn;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/applicationContext.xml" })
public class TestDBConnection {
	Logger logger = LoggerFactory.getLogger(TestDBConnection.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void testConenction() {

		List<Article> arts = sqlSessionTemplate.selectList("Article.getAllArticles");
		logger.debug("Found " + arts.size() + " articles");
		System.out.println("Found " + arts.size() + " articles");
		Assert.assertTrue(arts.size() > 0);
	}

	@Test
	public void insertArticle() {
		Article art = getTheArticle();
		// sqlSessionTemplate.insert("Article.saveArticle", art);
	}

	private Article getTheArticle() {
		Article artl = new Article();
		artl.setGatherTime(null);
		artl.setCarModel("bj40");
		artl.setCarFirm("beijing car");
		artl.setWebsite("www.autohome.com.cn");
		artl.setUrl("http://www.autohome.com.cn");
		artl.setAuthor("NotMe");
		artl.setTime("2017-06-21");
		artl.setTitle("This is a test");
		artl.setContent("添加包的依赖，编辑pom.xml文件添加如下依赖");
		artl.setCreatedBy("zww");
		artl.setUpdatedBy("zww");

		return artl;
	}

}
