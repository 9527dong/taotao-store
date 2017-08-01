package com.taotao.cart.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.common.service.ApiService;

@Service
public class ItemService {
	@Autowired
	private ApiService apiService;

	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public Item queryById(Long itemId) {
		try {
			String url = TAOTAO_MANAGE_URL + "/rest/api/item/" + itemId;
			String jsonData = this.apiService.doGet(url);
			if (StringUtils.isNotEmpty(jsonData)) {
				return MAPPER.readValue(jsonData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
