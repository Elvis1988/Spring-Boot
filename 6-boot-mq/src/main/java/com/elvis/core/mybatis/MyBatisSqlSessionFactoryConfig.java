package com.elvis.core.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * 	sqlSession配置
 *  参考Spring-Mybatis的配置
 *  <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:context/mybatis-common-config.xml"/>
        <property name="mapperLocations" value="classpath*:ormapper/mybatis/*.xml"/>
    </bean>
 * 	
 * 	DataSource : 数据源
 * 	ConfigLocation : mybatis配置文件
 * 	MapperLocations : mapper.xml文件
 * 
 * 
 * @Configuration : 上述XML配置作用，不能改成@component方式，dataDource还没开始注入，而且要配合@componentScan
 * 
 * 为什么不用enventmentAware, 此类启动的时候，enventment为null
 * */
@Configuration
public class MyBatisSqlSessionFactoryConfig implements ApplicationContextAware {
	
	private static final String mybatis_configLocation_path_key = "mybatis.config.location.class.path";
	
	private static final String mybatis_mapperLocations_beanName_key="mybatis.mapper.locations.class.path";
	
	@Autowired
	private DataSource dataDource;
	
	private String configLocationPath;
	
	private String mapperLocationsPath;
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.configLocationPath = this.applicationContext.getEnvironment().getProperty(mybatis_configLocation_path_key);
		this.mapperLocationsPath = this.applicationContext.getEnvironment().getProperty(mybatis_mapperLocations_beanName_key);
		
	}
	
	@Bean(name = {"myBatisSqlSessionFactory"})
	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(new TransactionAwareDataSourceProxy(this.dataDource));
		
		bean.setConfigLocation(this.applicationContext.getResource(this.configLocationPath));
		bean.setMapperLocations(this.applicationContext.getResources(this.mapperLocationsPath));
		
		return bean.getObject();
	}

}
