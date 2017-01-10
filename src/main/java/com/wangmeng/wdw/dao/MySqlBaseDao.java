package com.wangmeng.wdw.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.elvis.core.datasource.TargetDataSource;

@ComponentScan
@Service
public class MySqlBaseDao implements InitializingBean {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@TargetDataSource(dsName="mysql")
	public void create() {
		jdbcTemplate.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(jdbcTemplate);
	}
	
}
