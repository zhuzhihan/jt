package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean findCheckUser(String param, Integer type) {
		String column = 
				type == 1 ? "username" : (type == 2 ? "phone" : "email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, param);
		User user = userMapper.selectOne(queryWrapper);
		//手写的sql: select * from tb_user where ${column}=#{param};		
		return user == null ? false : true;
	}
}
