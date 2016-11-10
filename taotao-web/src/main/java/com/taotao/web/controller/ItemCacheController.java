package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

@RequestMapping("item/cache")
@Controller
public class ItemCacheController {
	
	@Autowired
	private RedisService redisService;
	/**
	 * 接收商品id，删除对应的缓存数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteCache(@PathVariable("itemId") Long itemId){
		try {
			String key = ItemService.REDIS_EKY + itemId;
			this.redisService.del(key);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
