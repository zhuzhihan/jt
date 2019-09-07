package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Transactional
	@Override
	public void updateNum(Cart cart) {
//		Cart cartTempCart = new Cart();
//		cartTempCart.setNum(cart.getNum())
//					.setUpdated(new Date());
		cart.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
					 .eq("item_id", cart.getItemId());
		cartMapper.update(cart/*TempCart*/, updateWrapper);
	}

	@Transactional
	@Override
	public void deleteCart(Cart cart) {
		System.out.println(cart);
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
	}
	
	/**
	 * 在入库操作之前必须先查询数据库(user_id,item_id)
	 * null则新增入库
	 * 如果不为空则只更新数量
	 */
	@Transactional
	@Override
	public void addCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId())
					.eq("item_id", cart.getItemId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if (cartDB == null) {
			cart.setCreated(new Date())
				.setUpdated(cart.getCreated());		
			cartMapper.insert(cart);
		}else {
			int num = cart.getNum() + cartDB.getNum();
			Cart newCart = new Cart();
			newCart.setNum(num)
				   .setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", cartDB.getId());					 
			cartMapper.update(newCart, updateWrapper);
		}		
	}
	
}
