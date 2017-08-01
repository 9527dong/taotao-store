package com.taotao.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.sso.query.api.UserQueryService;
import com.taotao.sso.query.bean.User;

@Service
public class UserService {
	@Autowired
	private UserQueryService userQueryService;

	@Value("${TAOTAO_SSO_URL}")
	public String TAOTAO_SSO_URL;
	
	public User queryByToken(String token){
		
		return this.userQueryService.queryUserByToken(token);
	}
}
