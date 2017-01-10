package com.elvis.core.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.h2.util.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * @import(class):ImportBeanDefinitionRegistrar
 * 此类的重点是初始化 DynamicDataSource类；初始化多个数据源的值，注入进去；
 * @author meng.wang.rd
 *
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private static final Logger logger = LogManager.getLogger(DynamicDataSourceRegister.class);

	// 默认DataSource类型
	private static final Object DEFAULT_DATASOURCE_TYPE = "org.apache.tomcat.jdbc.pool.DataSource";

	private static final String DF_DS_POPS_PREFIXS = "spring.datasource";

	private ConversionService conversionService = new DefaultConversionService();

	// 默认数据源
	private DataSource defaultDataSource;

	// 配置的其他数据源，key='dsType'
	private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();


	/**
	 * 系统启动的时候 会被执行。所以可以添加自己定义的方法；在启动的是执行
	 */
	@Override
	public void setEnvironment(Environment env) {
		// 1. build defaultDataSurce
		initDefaultDataSource(env);
		// 2. init customDataSources
		initCustomDataSource(env);
	}

	/**
	 * 
	 * 生成一个DynamicDataSourceDefinition,然后register到Spring中；
	 * 重写这个方法是在DynamicDataSourceRegister加载的时候，顺带register DynamicDataSourceDefinition
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotation, BeanDefinitionRegistry registry) {
		// 支持数据源集合，DynamicDataSource的父类属性值，每次切换determineCurrentLookupKey()方法返回需要的数据源
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 将主数据源添加到更多数据源中
		targetDataSources.put("dataSource", defaultDataSource);
		// H2是默认的
		targetDataSources.put("h2", defaultDataSource);
		// 将自定义配置的数据源加进来
		targetDataSources.putAll(customDataSources);

		// 创建DynamicDataSource
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		beanDefinition.setSynthetic(true);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		registry.registerBeanDefinition("dataSource", beanDefinition);

	}

	private DataSource buildDataSource(Map<String, Object> dsMap) {

		try {
			Object dsType = dsMap.get("type");
			if(dsType == null) {
				dsType = this.DEFAULT_DATASOURCE_TYPE;
			}

			Class<? extends DataSource> dataSourceClass;
			dataSourceClass = (Class<? extends DataSource>) Class.forName((String) dsType);

			String driverClassName = dsMap.get("driver-class-name").toString();
			String url = dsMap.get("url").toString();
			String username = dsMap.get("username").toString();
			String password = dsMap.get("password").toString();

			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
					.driverClassName(driverClassName)
					.url(url)
					.username(username)
					.password(password)
					.type(dataSourceClass);
			return dataSourceBuilder.build();
		} catch (ClassNotFoundException e) {
			logger.error("buildDataSource", e);
		}		
		return null;
	}

	private void initDefaultDataSource(Environment env) {
		
		Assert.notNull(env, "environment is null");

		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, DF_DS_POPS_PREFIXS);

		if (defaultDataSource == null) {
			Map<String, Object> dsMap = propertyResolver.getSubProperties(".");
			defaultDataSource = buildDataSource(dsMap);

			Map<String, Object> values = new HashMap<>(dsMap);
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			PropertyValues propertyValues = new MutablePropertyValues(values);

			dataSourceBinder(defaultDataSource, propertyValues);
		}

	}

	private void initCustomDataSource(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, DF_DS_POPS_PREFIXS);
		String customs = propertyResolver.getProperty(".customs");
		String [] arrCustomDsKey = null;
		if(!StringUtils.isNullOrEmpty(customs)) {
			arrCustomDsKey = customs.split(",");
		}

		if (arrCustomDsKey != null ) {
			customDataSources = new HashMap<String, DataSource>();
			
			for (String dsCusKey : arrCustomDsKey) {
				Map<String, Object> dsMap = propertyResolver.getSubProperties("." + dsCusKey + ".");
				DataSource cusDs = buildDataSource(dsMap);
				customDataSources.put(dsCusKey, cusDs);
				
				Map<String, Object> values = new HashMap<>(dsMap);
				// 排除已经设置的属性
				values.remove("type");
				values.remove("driver-class-name");
				values.remove("url");
				values.remove("username");
				values.remove("password");
				PropertyValues propertyValues = new MutablePropertyValues(values);
				dataSourceBinder(cusDs, propertyValues);
			}
		}
	}


	private void dataSourceBinder(DataSource dataSource, PropertyValues propertyValues ) {
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		//dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);//false
		dataBinder.setIgnoreInvalidFields(false);//false
		dataBinder.setIgnoreUnknownFields(true);//true
		dataBinder.bind(propertyValues);
	}

}
