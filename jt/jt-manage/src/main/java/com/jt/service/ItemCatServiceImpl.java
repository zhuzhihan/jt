package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired(required = false)
	private ItemCatMapper itemCatMapper;
	
//	@Autowired(required = false)
//	@Lazy
//	private Jedis jedis;

	@Override
	public String findName(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}

	public List<ItemCat> findItemCat(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> list = itemCatMapper.selectList(queryWrapper);
		return list;
	}

	@Override
	public List<EasyUITree> findListName(Long parentId) {
		List<ItemCat> itemCats = findItemCat(parentId);
		ArrayList<EasyUITree> easyUITreeList = new ArrayList<EasyUITree>();
		for (ItemCat itemCat : itemCats) {
			String state = itemCat.getIsParent() ? "closed" : "open";
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId())
					  .setText(itemCat.getName())
					  .setState(state);
			easyUITreeList.add(easyUITree);
		}
		return easyUITreeList;
	}

//	@Override
//	public List<EasyUITree> findRedisListName(Long parentId) {
//		List<EasyUITree> redisTrees = new ArrayList<>();
//		String key = "item_cat_" + parentId;
//		String result = jedis.get(key);
//		if (StringUtils.isEmpty(result)) {
//			redisTrees = findListName(parentId);
//			String json = ObjectMapperUtil.toJSON(redisTrees);
//			jedis.set(key, json);
//			System.out.println("从数据库取!!");
//		} else {
//			redisTrees = 
//					ObjectMapperUtil.toObject(result, redisTrees.getClass());
//			System.out.println("从redis缓存中取!!");
//		}
//		return redisTrees;
//	}
}
