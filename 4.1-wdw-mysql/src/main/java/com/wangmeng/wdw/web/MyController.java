package com.wangmeng.wdw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangmeng.wdw.h2.UserDao;
import com.wangmeng.wdw.mysql.MySqlBaseDao;

@RestController
@RequestMapping("/my")
public class MyController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MySqlBaseDao mySqlBaseDao;
	
	@RequestMapping("/users")
	public String hello() {
		return userDao.getUsers().toString();
	}
	
	@RequestMapping("/mysql")
	public String mysql()  {
		mySqlBaseDao.create();
		return "mysql create"; 
	}

}
