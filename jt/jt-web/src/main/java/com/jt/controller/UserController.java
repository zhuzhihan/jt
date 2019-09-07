package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserIService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Reference(timeout = 3000, check = true)
	private DubboUserIService userIService;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 登录和注册页面跳转实现
	 * 
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/{moduleName}")
	public String login(@PathVariable String moduleName) {
		return moduleName;
	}

	/**
	 * 实现用户新增
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		userIService.insertUser(user);
		return SysResult.success();
	}

	/**
	 * 实现用户登录
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user, HttpServletResponse response) {
		// 获取服务器加密的,秘钥
		String ticket = userIService.doLogin(user);
		if (StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		Cookie cookie = new Cookie("JT_TICKET", ticket);
		cookie.setMaxAge(7 * 24 * 3600);
		// 设定cookie的使用权限 比如user/表示只有 www.jt.com/user的目录下允许访问
		cookie.setPath("/");
		// 设置cookie为共享(重要) 默认cookie是私有的 只允许当前域名使用
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		return SysResult.success();
	}

	/**
	 * 实现退出登录
	 */
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response) {
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

		// 删除redis缓存
		if (!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
		}

		// 删除cookie -1指的是当回话关闭后删除cookie
		Cookie cookie = new Cookie("JT_TICKET", "");
		cookie.setMaxAge(0);// 删除cookie
		// 设定cookie的使用权限 比如user/表示只有 www.jt.com/user的目录下允许访问
		cookie.setPath("/");
		// 设置cookie为共享(重要) 默认cookie是私有的 只允许当前域名使用
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		// 表示重定向到系统首页
		return "redirect:/";
	}
}
