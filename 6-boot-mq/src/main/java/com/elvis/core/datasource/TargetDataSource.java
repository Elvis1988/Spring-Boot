package com.elvis.core.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  自定义一个annotation, 
 *  dsName ： 数据源名称
 *  配置的时候 ： @TargetDataSource(dsName = "mysql")
 * @author meng.wang.rd
 * 
 * @Target({ ElementType.METHOD, ElementType.TYPE })  目标类 方法 类型
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
	String dsName() default "h2";   // 默认H2的数据源
}
