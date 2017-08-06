package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.cart.service.CartRedisService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.query.bean.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.web.threadlocal.UserThreadLocal;

@RequestMapping("cart")
@Controller
public class CartController {
	private static final String CART_COOKIE_KEY = "TT_CART";
	public static Integer COOKIE_TIME = 60 * 60 * 24 * 30 * 12;
	@Autowired
	private CartService cartService;
	
//	@Autowired
	private CartCookieService cartCookieService;

	@Autowired
	private CartRedisService cartRedisService;
	/**
	 * 2. 查询购物车列表
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.GET)
	public ModelAndView cartList(HttpServletRequest request){
		ModelAndView mv  = new ModelAndView("cart");

		User user = UserThreadLocal.get();
		List<Cart> cartList = null;
		if(null == user){
			//非登陆状态
			try {
				cartList =this.cartCookieService.querycartList(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//登录
			cartList = this.cartService.queryCartList();
		}
		mv.addObject("cartList", cartList);
		return mv;
	}
	/**
	 * 1. 加入商品到购物车
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "add/{itemId}", method = RequestMethod.GET)
	public String add(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response, @CookieValue(value = CART_COOKIE_KEY, required = false)String cartKey){
		
		User user = UserThreadLocal.get();
		if(null == user){
		    if(cartKey == null){
		        //第一次加入购物车，生成key
                cartKey = DigestUtils.md5Hex(itemId+""+System.currentTimeMillis());
                //将key保存到Cookie中
				CookieUtils.setCookie(request,response,CART_COOKIE_KEY, cartKey, COOKIE_TIME);
            }
			//非登陆状态
			try {
				this.cartRedisService.addItemToCart(itemId, cartKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//登录
			this.cartService.addItemToCart(itemId);
		}
		
		//重定向到购物车列表页面
		return "redirect:/cart/list.html";
		
	}
	/**
	 * 更新购物车商品的数量
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateNum(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response){
		
		User user = UserThreadLocal.get();
		
		if(null == user){
			//未登录状态
			try {
				this.cartCookieService.updateNum(itemId,num,request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//登录状态
			this.cartService.updateNum(itemId, num);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	/**
	 * 删除购物车的商品
	 * @return
	 */
	@RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
	public String delete(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response){
		User user = UserThreadLocal.get();
		if(null == user){
			//非登陆状态
			try {
				this.cartCookieService.delete(itemId, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//登录
			this.cartService.delete(itemId);
		}
		
		//重定向到购物车列表页面
		return "redirect:/cart/list.html";
	}
}
