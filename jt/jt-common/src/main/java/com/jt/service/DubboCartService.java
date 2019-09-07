package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;

public interface DubboCartService {

	List<Cart> findCartByUserId(Long userId);

	void updateNum(Cart cart);

	void deleteCart(Cart cart);

	void addCart(Cart cart);

}
