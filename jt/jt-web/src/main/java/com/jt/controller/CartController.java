package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.ThreadLocalUtils;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Reference(timeout = 3000, check = false)
	private DubboCartService cartService;

	/**
	 * 根据用户信息实现购物车列表展现
	 * 
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model/* ,HttpServletRequest request */) {
		// Long userId = (Long) request.getAttribute("JT_USER");
		Long userId = ThreadLocalUtils.get().getId();
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	/**
	 * 修改购物车商品数量 当名称和属性一直时可以用对象接收] 不需要@PathVariable
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		Long userId = ThreadLocalUtils.get().getId();
		cart.setUserId(userId);
		cartService.updateNum(cart);
		return SysResult.success();
	}

	/**
	 * 删除订单
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId = ThreadLocalUtils.get().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}

	/**
	 * 添加商品
	 */
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		Long userId = ThreadLocalUtils.get().getId();
		cart.setUserId(userId);
		cartService.addCart(cart);
		return "redirect:/cart/show.html";
	}

}
