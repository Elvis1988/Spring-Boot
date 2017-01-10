package com.wangmeng.wdw.datasource;

/**
 * 多线程下，保存当前线下的数据源一致；ThreadLocal
 * 
 * @author meng.wang.rd
 */
public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> context = new ThreadLocal<String>();
	
	public static String getDataSourceType() {
		return context.get();
	}
	
	
	public static void setDataSourceType(String dsType) {
		context.set(dsType);
	}
	
	public static void clearDataSourceTypes() {
		context.remove();
	}
	
	public static String getCurrentThread() {
		return String.format("thread name = %s, num = %s", Thread.currentThread().getName(), Thread.currentThread().getId());
	}
	
}
