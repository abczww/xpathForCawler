package com.cnki.ksp.core;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao {

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

}
