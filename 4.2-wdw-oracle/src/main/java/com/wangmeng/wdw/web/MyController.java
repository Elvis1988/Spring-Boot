package com.wangmeng.wdw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangmeng.wdw.dao.H2BaseDao;
import com.wangmeng.wdw.dao.MySqlBaseDao;
import com.wangmeng.wdw.dao.OracleBaseDao;

@RestController
@RequestMapping("/my")
public class MyController {
	
	@Autowired
	private H2BaseDao userDao;
	
	@Autowired
	private MySqlBaseDao mySqlBaseDao;
	
	@Autowired
	private OracleBaseDao oracleBaseDao;
	
	@RequestMapping("/users")
	public String hello() {
		return userDao.getUsers().toString();
	}
	
	@RequestMapping("/mysql")
	public String mysql()  {
		mySqlBaseDao.create();
		return "mysql create"; 
	}
	
	@RequestMapping("/oracle")
	public String oracle()  {
		oracleBaseDao.create();
		return "oracle create"; 
	}

}
