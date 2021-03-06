package com.cnki.ksp.test.conn;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.core.ArticleType;
import com.cnki.ksp.dao.ArticleDao;
import com.cnki.ksp.test.RunTests;

/**
 * Creat
 * ed by windhut on 7/18/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/applicationContext.xml" })
public class TestArticle {

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void insertArticle() {
        Article art = getTheArticle();
        articleDao.saveOrUpdate(art);
    }
    
    @Test
    public void getAllArticle(){
    	List<Article> arts = articleDao.getAll();
    	System.out.println(arts.size());
    	assertTrue(arts.size()>=0);
    }

    private Article getTheArticle() {
        Article artl = new Article(1101, "http://www.autohome.com.cn", ArticleType.PROBLEM.getType());
        artl.setGatherTime(null);
        artl.setCarModel("bj40");
        artl.setCarFirm("beijing car");
        artl.setWebsite("www.autohome.com.cn");
        artl.setAuthor("NotMe");
        artl.setDate("2017-07-20");
        artl.setTime("2017-06-21");
        artl.setTitle("This is a test");
        artl.setContent("添加包的依赖，编辑pom.xml文件添加如下依赖");
        artl.setContent2("添加包的依赖，编辑pom.xml文件添加如下依赖");
        artl.setCreatedBy("zww");
        artl.setUpdatedBy("zww");
        artl.setDeleted(RunTests.TEST_DELETE);

        return artl;
    }
}
