package com.taotao.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem>{

	@Autowired
	private ItemParamItemMapper itemParamItemMapper;
	
	public Integer updateItemParamItem(Long itemId, String paramData) {
		//更新数据
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setParamData(paramData);
		itemParamItem.setUpdated(new Date());
		
		//更新条件
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		
		return this.itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
	}

}
