package com.cnki.ksp.test.conn;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.beans.CaptureRecord;
import com.cnki.ksp.dao.CaptureRecordDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/applicationContext.xml" })
public class TestCaptureRecord {

	Logger logger = LoggerFactory.getLogger(TestDBConnection.class);

	@Autowired
	private CaptureRecordDao crDao;
	
	@Test
	public void insertCaputreRecord(){
		CaptureRecord cr = this.getCaptureRecord();
		crDao.saveOrUpdate(cr);
	}
	
	@Test
	public void getAll(){
		List<CaptureRecord> crs = crDao.getAll();
		assertTrue(crs.size()>=0);
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
	
	private CaptureRecord getCaptureRecord(){
		CaptureRecord cr = new CaptureRecord();
		cr.setKspId(1101);
		cr.setKspName("A test name");
		cr.setUrl("a test url");
		cr.setCaptureStartTime(new Timestamp(System.currentTimeMillis()));
		return cr;
	}



}
