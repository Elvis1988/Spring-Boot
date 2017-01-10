package com.wangmeng.wdw.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源切换，集成Spring的类AbstractRoutingDataSource；
 * 重写determineCurrentLookupKey()方法，动态返回每次sessionConnection前所要连接dataSource的Key
 * 
 * AbstractRoutingDataSource。targetDataSources<key,datasource>;存放所有的ds的值
 * 
 * DynamicDataSource类的初始化很重要，要注入很多数据源；否则无法自动切换
 * 
 * @author meng.wang.rd
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// 返回当前线程的数据源类型
		return DataSourceContextHolder.getDataSourceType();
	}

}
