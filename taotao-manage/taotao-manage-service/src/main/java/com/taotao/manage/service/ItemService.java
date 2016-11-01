package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{
	@Autowired
	private ItemDescService itemDescService;
	
	public boolean saveItem(Item item, String desc) {
		// 初始值
		item.setStatus(1);
		item.setId(null);// 出于安全考虑，强制设置id为null，通过数据库自增长得到
		Integer count1 = super.save(item);
		
		//保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer count2 = this.itemDescService.save(itemDesc);
		
		return count1.intValue() == 1 && count2.intValue() == 1;
	}

}
