package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

// 请求地址:"http://manage.jt.com/web/item/findItemById?itenId=564321";
@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/findItemById")
	@Cache_Find(seconds = 7 * 24 * 3600)
	public Item findItemById(Long itemId) {

		return itemService.findItemById(itemId);
	}

	@RequestMapping("/findItemDescById")
	@Cache_Find(seconds = 7 * 24 * 3600)
	public ItemDesc findItemDescById(Long itemId) {

		return itemService.selectItemDescById(itemId);
	}

}
