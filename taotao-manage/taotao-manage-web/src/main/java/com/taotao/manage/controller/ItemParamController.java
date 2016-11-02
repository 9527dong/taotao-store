package com.taotao.manage.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@RequestMapping("item/param")
@Controller
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId){
		try {
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCatId);
			ItemParam itemParam = this.itemParamService.queryOne(record);
			if(null == itemParam){
				//404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 新增规格参数模版
	 * @param itemCatId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId, @RequestParam("paramData") String paramData){
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setId(null);
			itemParam.setItemCatId(itemCatId);
			itemParam.setParamData(paramData);
			this.itemParamService.save(itemParam);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
