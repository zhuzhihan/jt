package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITree;

public interface ItemCatService {

	String findName(Long itemCatId);

	List<EasyUITree> findListName(Long parentId);

	//List<EasyUITree> findRedisListName(Long parentId);

}
