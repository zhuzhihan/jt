package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache_Find;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Component
@Aspect
public class CacheAspect {
	@Autowired
	private JedisCluster jedis;

	@Around("@annotation(cacheFind)")
	public Object around(ProceedingJoinPoint pj, Cache_Find cacheFind) {
		String key = getKey(pj, cacheFind);
		String json = jedis.get(key);
		Object object = null;
		if (StringUtils.isEmpty(json)) {
			try {
				Long a = System.currentTimeMillis();
				object = pj.proceed();
				Long b = System.currentTimeMillis();
				String json2 = ObjectMapperUtil.toJSON(object);
				if (cacheFind.seconds() > 0)
					jedis.setex(key, cacheFind.seconds(), json2);
				else
					jedis.set(key, json2);
				System.out.println("走的是数据库!!");
				System.out.println(b - a);
				return object;
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			object = ObjectMapperUtil.toObject(json, getType(pj));
			System.out.println("走的是缓存!!");
			// System.out.println(b - a);
			return object;
		}
	}

	private Class getType(ProceedingJoinPoint pj) {
		MethodSignature ms = (MethodSignature) pj.getSignature();
		return ms.getReturnType();
	}

	private String getKey(ProceedingJoinPoint pj, Cache_Find cacheFind) {
		String key = cacheFind.key();
		// getSignature()该方法封装了连接点的全部属性
		if (StringUtils.isEmpty(key)) {
			String methodName = pj.getSignature().getName();
			String className = pj.getSignature().getDeclaringTypeName();
			String arg1 = String.valueOf(pj.getArgs()[0]);
			return className + "." + methodName + "::" + arg1;
		} else {
			return key;
		}
	}
}
