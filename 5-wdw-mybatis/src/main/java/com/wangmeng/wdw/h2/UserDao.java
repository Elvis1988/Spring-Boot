package com.wangmeng.wdw.h2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@ComponentScan
@Service
public class UserDao implements InitializingBean {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Map<String, Object>> getUsers() {
		return jdbcTemplate.queryForList("SELECT * FROM USER_INFO ");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(jdbcTemplate);
		System.out.println(jdbcTemplate.queryForList("SELECT * FROM USER_INFO "));
	}
	
}
