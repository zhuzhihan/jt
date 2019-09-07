package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.ThreadLocalUtils;

import redis.clients.jedis.JedisCluster;

@Component
public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 重写perhandler校验用户是否登录
	 * 
	 * boolean: true: 请求放行 false: 请求拦截 一般会有重定向执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if (cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(ticket)) {
			String json = jedisCluster.get(ticket);
			if (!StringUtils.isEmpty(json)) {
				User user = ObjectMapperUtil.toObject(json, User.class);
				ThreadLocalUtils.set(user);
				//request.setAttribute("JT_USER", user);
				return true;
			}
		}
		// 重定向到用户登录页面
		response.sendRedirect("/user/login.html");
		return false; // 表示请求拦截
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
