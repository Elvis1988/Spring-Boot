package com.wangmeng.wdw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangmeng.wdw.h2.UserDao;

@RestController
@RequestMapping("/my")
public class MyController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/users")
	public String hello() {
		
		
		return userDao.getUsers().toString();
		
	}

}
