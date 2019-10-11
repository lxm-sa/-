package com.yb.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenForm {
	//1.标记映射方法需要创建Token
	boolean create() default false;
	//2.标记映射方法需要移除token
	boolean remove() default false;

}
