package com.wangmeng.wdw.dao.user;

import java.util.List;

import com.wangmeng.wdw.datasource.TargetDataSource;

public interface UserInfoDao {
	@TargetDataSource(dsName="mysql")
	UserInfo get(Long id);
	List<UserInfo> getUsers(UserInfo cond);
}
