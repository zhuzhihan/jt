package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(Integer page, Integer rows);

	void deleteByids(Long... ids);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	void statusItem(Long[] ids, int status);

	ItemDesc selectItemDescById(Long itemId);

	Item findItemById(Long itemId);
}
