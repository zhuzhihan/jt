package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service(timeout = 3000)
public class DubboOrderServiceImpl implements DubboOrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper itemMapper;

	@Autowired
	private OrderShippingMapper shippingMapper;

	@Override
	@Transactional
	public String saveOrder(Order order) {
		// 生成orderId
		String orderId 
				= "" + order.getUserId() + System.currentTimeMillis();
		Date date = new Date();

		// 实现订单入库
		order.setOrderId(orderId)
			 .setStatus(1)
			 .setCreated(date)
			 .setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功!!!!!!!!!!!!!!!");

		// 实现订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
			itemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功!!!!!!!!!!!!");
		// 实现订单物流入库
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
		shippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功!!!!!");
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = shippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id", id);
		List<OrderItem> list = itemMapper.selectList(queryWrapper);
		order.setOrderShipping(orderShipping)
			 .setOrderItems(list);
		return order;
	}
}
