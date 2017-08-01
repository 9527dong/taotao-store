package com.taotao.sso.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.query.api.UserQueryService;
import com.taotao.sso.query.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dong on 2017/7/29.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    @Autowired
    private RedisService redisService;

    private static final Integer REDIS_TIME = 60*30;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    public User queryUserByToken(String token) {

        String key = "TOKEN_" + token;
        String jsonData = this.redisService.get(key);

        if(StringUtils.isEmpty(jsonData)){
            //登录超时
            return null;
        }


        //重新设置redis中的生存时间
        this.redisService.expire(key, REDIS_TIME);


        try {
            return MAPPER.readValue(jsonData, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
