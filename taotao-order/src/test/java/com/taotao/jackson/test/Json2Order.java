package com.taotao.jackson.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.store.order.pojo.Order;

public class Json2Order {
	
	/**
	 * {
		    "picPath": "", //商品图片绝对途径
		    "payment": 200, //实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
		    "postFee": 18, //邮费。
		    "userId": "13", //用户ID
		    "buyerMessage": "buyerMessage", //买家留言
		    "buyerNick": "buyerNick", //用户昵称
		    "orderItems": [
		        {
		            "itemId": "2424", //商品id
		            "num": 2, //购买数量
		            "title": "title",//标题
		            "price": 23, //单价
		            "totalFee": 233 //总金额
		        }
		    ]
		}
	 * 
	 * @return
	 */
	private String getJson(){
		try {
			return FileUtils.readFileToString(new File("C:\\tmp\\order.json"), "UTF-8");
		} catch (IOException e) {
		}
		return null;
	}
	
	@Test
	public void json2Order() throws Exception{
		String json = getJson();
		ObjectMapper objectMapper = new ObjectMapper();
		Order order =  objectMapper.readValue(json, Order.class);
		System.out.println(order);
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}

}
