package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController // 不执行视图解析器
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/queryItemName")
	public String findName(Long itemCatId) {

		return itemCatService.findName(itemCatId);
	}

	@RequestMapping("/list")
	@Cache_Find()
	public List<EasyUITree> findListName(
			@RequestParam(defaultValue = "0", name = "id", required = false) Long parentId) {
		// 查询一级商品信息
		return itemCatService.findListName(parentId);
	}
}
