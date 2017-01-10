package com.wangmeng.wdw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * War包部署方式 ： web项目的静态文件加载，jar包加载不进来问题；
 * 
 * <packaging>war</packaging>
 * 添加Tomcat配置spring-boot-starter-tomcat
 * 继承SpringBootServletInitializer： 默认servlet3.0的容器加载web应用
 * 
 * @see http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html 
 * @author meng.wang.rd
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer { 
	public static void main( String[] args ) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
}
