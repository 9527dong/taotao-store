package com.taotao.manage.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 查询商品类目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tree",method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value="id", defaultValue="0") Long pid){
		try {
			ItemCat record = new ItemCat();
			record.setParentId(pid);
			List<ItemCat> list = this.itemCatService.queryListByWhere(record);
			
			if(null == list || list.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 查询商品类目的描述
	 *
	 * @return
	 */
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<ItemCat> queryItemCatById(@PathVariable("id") Long id){
		try {
			ItemCat record = new ItemCat();
			record = this.itemCatService.queryById(id);

			if(StringUtils.isEmpty(record.getName())){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(record);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
