package com.wangmeng.wdw.web;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elvis.core.datasource.TargetDataSource;
import com.wangmeng.wdw.dao.H2BaseDao;
import com.wangmeng.wdw.dao.MySqlBaseDao;
import com.wangmeng.wdw.dao.OracleBaseDao;
import com.wangmeng.wdw.dao.user.UserInfoDao;

@RestController
@RequestMapping("/my")
public class MyController {
	
	@Autowired
	private H2BaseDao userDao;
	
	@Autowired
	private MySqlBaseDao mySqlBaseDao;
	
	@Autowired
	private OracleBaseDao oracleBaseDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@RequestMapping("/users")
	public String hello() {
		return userDao.getUsers().toString();
	}
	
	@RequestMapping("/mysql")
	@TargetDataSource(dsName="mysql")
	public String mysql()  {
		return this.userInfoDao.getUsers(null).toString();
	}
	
	@RequestMapping("/oracle")
	public String oracle()  {
		oracleBaseDao.create();
		return "oracle create"; 
	}
	
	@TargetDataSource(dsName="h2")
	@RequestMapping("/userInfo")
	public String userInfo()  {
		System.out.println(this.userInfoDao);
		return this.userInfoDao.getUsers(null).toString();
	}

}
