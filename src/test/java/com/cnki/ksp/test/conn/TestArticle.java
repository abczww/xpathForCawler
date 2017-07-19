package com.cnki.ksp.test.conn;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.dao.ArticleDao;

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
        articleDao.save(art);
    }
    
    @Test
    public void getAllArticle(){
    	List<Article> arts = articleDao.getAll();
    	assertTrue(arts.size()>0);
    }

    private Article getTheArticle() {
        Article artl = new Article();
        artl.setKspId(1101);
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
