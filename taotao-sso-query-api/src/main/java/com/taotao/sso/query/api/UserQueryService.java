package com.taotao.sso.query.api;

import com.taotao.sso.query.bean.User;

public interface UserQueryService {

    /**
     * 根据token查询User对象
     * 
     * @return
     */
    public User queryUserByToken(String token);

}
