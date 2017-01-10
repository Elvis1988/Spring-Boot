package com.elvis.core.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * MyBatisMapperScanner 自定扫描basePackage目录下的DAO接口，加载XML的SQL语句。生成MapperFactoryBean
 * 
 * 其实就是下述配置的批量自定注入
 * <bean id="mcsStlOrderDao" class="org.mybatis.spring.mapper.MapperFactoryBean">
        <property name="mapperInterface" value="com.bill99.mcs.process.order.dao.IStlOrderDao"/>
        <property name="sqlSessionFactory" ref="mcsQueryFactory"/>
    </bean>
 *
 * SqlSessionFactoryBeanName ： 
 * basePackage ：
 */
@Configuration
// SqlSessionFactory 要先配置
@AutoConfigureAfter(MyBatisSqlSessionFactoryConfig.class)
public class MyBatisMapperScannerConfig implements ApplicationContextAware{

	private static final String mybatis_mapperScanner_path_key = "mybatis.scanner.base.package";
	
	private static final String mybatis_sqlsessionFactory_beanName_key="mybatis.sql.session.factory.bean.name";

	private String basePackage;
	
	private String sqlSessionFactoryBeanName;
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		this.basePackage = this.applicationContext.getEnvironment().getProperty(mybatis_mapperScanner_path_key);
		this.sqlSessionFactoryBeanName = this.applicationContext.getEnvironment().getProperty(mybatis_sqlsessionFactory_beanName_key);
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName(this.sqlSessionFactoryBeanName);
		mapperScannerConfigurer.setBasePackage(this.basePackage);
		return mapperScannerConfigurer;
	}

}
