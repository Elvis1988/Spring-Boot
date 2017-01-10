package com.wangmeng.wdw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * 纯jar包启动方式，
 * 
 * 使用boot-start-web内置的Tomcat
 * 不需要继承SpringBootServletInitializer
 * <packaging>jar</packaging>
 * 
 * @author meng.wang.rd
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
