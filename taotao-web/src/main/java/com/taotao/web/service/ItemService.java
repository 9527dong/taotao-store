package com.taotao.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.bean.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ItemService {

    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisService redisServices;

    public static final String REDIS_EKY = "TAOTAO_WEB_ITEM_DETAIL_";
    private static final Integer REDIS_TIME = 60 * 60 * 24;

    /**
     * 根据商品id查询商品数据
     * 通过后台系统提供的接口服务进行查询
     *
     * @param itemId
     * @return
     */
    public Item queryById(Long itemId) {
        String key = REDIS_EKY + itemId;
        try {
            String cacheData = this.redisServices.get(key);
            if (StringUtils.isNotEmpty(cacheData)) {
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String url = TAOTAO_MANAGE_URL + "/rest/api/item/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);

            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }

            try {
                this.redisServices.set(key, jsonData, REDIS_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //将json数据反序列化为item对象
            return MAPPER.readValue(jsonData, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询商品描述数据
     *
     * @param itemId
     * @return
     */
    public ItemDesc queryDescById(Long itemId) {
        String url = TAOTAO_MANAGE_URL + "/rest/api/item/desc/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);

            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            //将json数据反序列化为item对象
            return MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询商品参数信息
     * TODO 未做，需要拷贝html代码
     *
     * @param itemId
     * @return
     */
    public String queryItemParamItemByItemId(Long itemId) {
        String url = TAOTAO_MANAGE_URL + "/rest/api/item/param/item/" + itemId;
        try {
            String jsonData = this.apiService.doGet(url);

            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            //将json数据反序列化
            ItemParamItem itemParamItem = MAPPER.readValue(jsonData, ItemParamItem.class);
            String paramData = itemParamItem.getParamData();
            //
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
