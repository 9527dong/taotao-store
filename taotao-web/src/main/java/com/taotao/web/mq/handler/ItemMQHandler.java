package com.taotao.web.mq.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

public class ItemMQHandler {
	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 删除缓存中的商品数据，完成数据同步
	 * @param msg
	 */
	public void execute(String msg){
		try {
			JsonNode jsonNode = MAPPER.readTree(msg);
			
			Long itemId  = jsonNode.get("itemId").asLong();
			String key = ItemService.REDIS_EKY + itemId;
			this.redisService.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
