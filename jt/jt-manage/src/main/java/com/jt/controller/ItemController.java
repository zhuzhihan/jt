package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

//@Controller
@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据分页查询商品信息
	 */
	@RequestMapping("/query")
	public EasyUITable findItemBypage(Integer page, Integer rows) {
		return itemService.findItemByPage(page, rows);
	}

	/**
	 * 根据id删除商品信息
	 */
	@RequestMapping("/delete")
	public SysResult deleteByIds(Long... ids) {
		itemService.deleteByids(ids);
		return SysResult.success();
	}

	/**
	 * 新增商品信息
	 * 
	 * 和新增商品描述信息
	 */

	@RequestMapping("/save")
	public SysResult saveObject(Item item, ItemDesc itemDesc) {
		itemService.saveItem(item, itemDesc);
		return SysResult.success();
	}

	/**
	 * 修改商品信息
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		itemService.updateItem(item, itemDesc);
		return SysResult.success();
	}

	/**
	 * 商品下架
	 */
	@RequestMapping("/instock")
	public SysResult instockItem(Long... ids) {
		int status = 2; // 表示下架
		itemService.statusItem(ids, status);
		return SysResult.success();
	}

	/**
	 * 商品上架
	 */
	@RequestMapping("/reshelf")
	public SysResult reshelfItem(Long... ids) {
		int status = 1; // 表示上架
		itemService.statusItem(ids, status);
		return SysResult.success();
	}

	/**
	 * 根据商品详情信息获取商品描述
	 */

	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult selectItemDescById(@PathVariable Long itemId) {
		ItemDesc itemDesc = itemService.selectItemDescById(itemId);
		return SysResult.success(itemDesc);
	}

}
