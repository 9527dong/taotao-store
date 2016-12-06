package com.taotao.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.sso.query.api.UserQueryService;
import com.taotao.sso.query.bean.User;

@Service
public class UserService {
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private UserQueryService userQueryService;
	
	@Value("${TAOTAO_SSO_URL}")
	public String TAOTAO_SSO_URL;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//使用httpclient的方式来调用接口，启用
//	public User queryByToken(String token){
//		try {
//			String url = TAOTAO_SSO_URL + "/service/user/" + token;
//			String jsonData = this.apiService.doGet(url);
//			if(StringUtils.isNotEmpty(jsonData)){
//				return MAPPER.readValue(jsonData, User.class);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public User queryByToken(String token){
		return this.userQueryService.queryUserByToken(token);
	}
}
