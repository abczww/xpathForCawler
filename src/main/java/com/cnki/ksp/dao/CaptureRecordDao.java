package com.cnki.ksp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cnki.ksp.beans.CaptureRecord;
import com.cnki.ksp.core.BaseDao;

@Repository("captureRecordDao")
public class CaptureRecordDao extends BaseDao<CaptureRecord>{
	
	@Resource(name="sqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public void save(CaptureRecord cr) {
		sqlSessionTemplate.insert("CaptureRecord.save", cr);
	}

	@Override
	public List<CaptureRecord> getAll() {
		return sqlSessionTemplate.selectList("CaptureRecord.getAll");
	}

}
