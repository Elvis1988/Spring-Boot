package com.wangmeng.wdw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.wangmeng.wdw.datasource.DynamicDataSourceRegister;


@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
@EnableAspectJAutoProxy
public class Application { 
	
	public static void main( String[] args ) {
		SpringApplication.run(Application.class, args);
		
		System.out.println(String.format("thread name = %s, num = %s", Thread.currentThread().getName(), Thread.currentThread().getId()));
	}
	
}
