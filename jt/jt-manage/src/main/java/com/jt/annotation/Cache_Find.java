package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache_Find {
	// 如果用户不写 默认是空串 表示自动生成key
	String key() default "";

	// 如果等于0则表示不需要设置超时时间 不等于0则用户自定义超时时间
	int seconds() default 0;

}
