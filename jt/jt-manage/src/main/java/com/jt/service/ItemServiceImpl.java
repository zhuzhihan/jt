package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable findItemByPage(Integer page, Integer pageSize) {

		Integer total = itemMapper.selectCount(null);
		int indexStart = pageSize * (page - 1);
		List<Item> itemList = itemMapper.findItemByPage(indexStart, pageSize);
		return new EasyUITable(total, itemList);
	}

	@Transactional
	@Override
	public void deleteByids(Long... ids) {
		int rows = itemMapper.deleteBatchIds(Arrays.asList(ids));
		int row = itemDescMapper.deleteBatchIds(Arrays.asList(ids));
	}

	@Transactional
	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		// 完成商品详情入库
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		System.out.println(itemDesc);
	}

	@Transactional
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {

		item.setUpdated(new Date());
		itemMapper.updateById(item);

		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Transactional
	@Override
	public void statusItem(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
		List<Long> asList = Arrays.asList(ids);
		queryWrapper.in("id", asList);
		itemMapper.update(item, queryWrapper);
	}

	@Override
	public ItemDesc selectItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {

		return itemMapper.selectById(itemId);
	}
}
