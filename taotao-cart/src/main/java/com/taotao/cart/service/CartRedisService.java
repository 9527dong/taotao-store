package com.taotao.cart.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.cart.controller.CartController;
import com.taotao.cart.pojo.Cart;
import com.taotao.common.service.RedisServicePro;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Dong on 2017/8/5.
 * 将购物车中的商品数据保存到Redis中
 */
@Service
public class CartRedisService {

    @Autowired
    private RedisServicePro redisService;
    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String KEY_STR = "CART_";

    private Logger logger = LoggerFactory.getLogger(CartRedisService.class);

    public void addItemToCart(Long itemId, String cartKey) {
        String key = KEY_STR + cartKey;
        String itemIdStr = String.valueOf(itemId);
        //判断商品是否存在购物车中
        String value = this.redisService.hget(key, String.valueOf(itemId));
        Cart cart = null;
        if (value == null){
            //商品不存在
            //写入数据
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
        }else{
            //存在，数量增加
            try {
                cart = MAPPER.readValue(value, Cart.class);
                cart.setNum(cart.getNum() + 1);
                cart.setUpdated(new Date());
            } catch (IOException e) {
                logger.error("读取购物车商品失败 cart = {} ", value);
                e.printStackTrace();
            }

        }
        try {
            this.redisService.hset(cartKey, itemIdStr, MAPPER.writeValueAsString(cart), CartController.COOKIE_TIME);
        } catch (JsonProcessingException e) {
            logger.error("加入 itemId = {} 到购物车失败", itemId);
            e.printStackTrace();
        }
    }

    public List<Cart> getCartList(String cartKey){
        String key = KEY_STR + cartKey;
        this.redisService.hgetAll(key);
    }
}
