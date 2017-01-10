package com.elvis.core.datasource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切面类，针对
 *
 */
@Aspect        	// 切面类
@Order(-1)		// 执行顺序 
@Component
public class DynamicDataSourceAspect {
	
	private static final Logger logger = LogManager.getLogger(DynamicDataSourceAspect.class);

	/**
	 * 切面：TargetDataSource注解的方法调用前执行；
	 * @param joinpoint
	 * @param ds ： @TargetDataSource(dsName="mysql"), ds.dsName="mysql";
	 */
	@Before("@annotation(tds))")
	public void changeDataSource( JoinPoint joinPoint, TargetDataSource tds) throws Throwable {
		logger.info("before" + tds.dsName());
		logger.info(DataSourceContextHolder.getCurrentThread() + ", TargetDataSource changed: ");
//		Assert.isNull(joinpoint, "Joinpoint is null");
//		Assert.isNull(ds, "TargetDataSource is null");
		
		logger.debug(DataSourceContextHolder.getCurrentThread() + ", TargetDataSource = " +  tds.dsName());
		
		// 设置当前线程能访问的数据为 annotation配置的值
		DataSourceContextHolder.setDataSourceType(tds.dsName());
	}
	
	@After("@annotation(com.elvis.core.datasource.TargetDataSource)")
	public void removeDataSource( JoinPoint joinPoint) {
		logger.info("after" + joinPoint.getArgs());
		logger.info(DataSourceContextHolder.getCurrentThread() + ", TargetDataSource cleared: ");
		
		DataSourceContextHolder.clearDataSourceTypes();
	}
}
