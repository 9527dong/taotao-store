package com.taotao.cart.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.cart.pojo.Cart;
import com.taotao.common.utils.CookieUtils;

@Service
public class CartCookieService {

	private static String CART_COOKIE_KEY = "TT_CART";
	private static Integer COOKIE_TIME = 60 * 60 * 24 * 30 * 12;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private ItemService itemService;

	/**
	 * 添加商品到购物车
	 * 
	 * 逻辑：判断加入的商品在原有购物车中是否存在，如果存在数量相加，如果不存在，直接写入即可
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public void addItemToCart(Long itemId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Cart> carts = this.querycartList(request);
		Cart cart = null;
		for (Cart c : carts) {
			if (cart.getItemId().longValue() == itemId.longValue()) {
				// 该商品已经存在购物车中
				cart = c;
				break;
			}
		}

		if (null == cart) {
			// 不存在
			cart = new Cart();
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cart.setItemId(itemId);
			// 商品的基本数据应该通过后台系统查询
			Item item = this.itemService.queryById(itemId);
			cart.setItemTitle(item.getTitle());
			cart.setItemPrice(item.getPrice());
			cart.setItemImage(StringUtils.split(item.getImage(), ',')[0]);
			cart.setNum(1);// TODO 商品数量默认为1，需要修改

			// 将商品加入购物车列表中
			carts.add(cart);
		} else {
			// 存在
			cart.setNum(cart.getNum() + 1); // TODO 默认为1
			cart.setUpdated(new Date());
		}

		// 将购物车列表数据写入到Cookie 中
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY,
				MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
	}

	public List<Cart> querycartList(HttpServletRequest request)
			throws Exception {
		String jsonData = CookieUtils
				.getCookieValue(request, CART_COOKIE_KEY, true);
		List<Cart> carts = null;
		if (StringUtils.isEmpty(jsonData)) {
			carts = new ArrayList<Cart>();
		} else {
			// 反序列化
			carts = MAPPER.readValue(jsonData, MAPPER.getTypeFactory()
					.constructCollectionType(List.class, Cart.class));
		}
		return carts;
	}

	public void updateNum(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Cart> carts = this.querycartList(request);
		Cart cart = null;
		for (Cart c : carts) {
			if (cart.getItemId().longValue() == itemId.longValue()) {
				// 该商品已经存在购物车中
				cart = c;
				break;
			}
		}
		if (cart != null) {
			cart.setNum(num);
			cart.setUpdated(new Date());
		} else {
			// 参数非法
			return;
		}
		// 将购物车列表数据写入到Cookie 中
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY,
				MAPPER.writeValueAsString(carts), COOKIE_TIME, true);

	}

	public void delete(Long itemId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Cart> carts = this.querycartList(request);
		Cart cart = null;
		for (Cart c : carts) {
			if (cart.getItemId().longValue() == itemId.longValue()) {
				cart = c;
				// 该商品已经存在购物车中
				carts.remove(c);
				break;
			}
		}
		
		if(cart == null){
			return;
		}
		// 将购物车列表数据写入到Cookie 中
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY,
				MAPPER.writeValueAsString(carts), COOKIE_TIME, true);

	}

}
