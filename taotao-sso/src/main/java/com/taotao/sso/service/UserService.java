package com.taotao.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisService redisService;
	
	private static final Integer REDIS_TIME = 60*30;
	
	private static final ObjectMapper MAPPER = new ObjectMapper(); 
	
	public Boolean check(String param, Integer type) {
		User record = new User();

		switch (type) {
		case 1:
			record.setUsername(param);
			break;
		case 2:
			record.setPhone(param);
			break;
		case 3:
			record.setEmail(param);
			break;
		default:
			// 参数有误
			return null;
		}
		return this.userMapper.selectOne(record) == null;
	}

	
	public Boolean doRegister(User user) {
		//初始化的处理
		user.setId(null);
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		return this.userMapper.insert(user) == 1;
	}

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String doLogin(String username, String password) throws Exception {
		
		User record = new User();
		record.setUsername(username);
		User user = this.userMapper.selectOne(record);
		if(null == user){
			//用户不存在
			return null;
		}
		
		if(!StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())){
			//密码错误
			return null;
		}
		//登录成功，将用户的信息保存到redis中
		String key = DigestUtils.md5Hex(username);
		this.redisService.set("TOKEN_" + key , MAPPER.writeValueAsString(user), REDIS_TIME);
		return key;
	}
	/**
	 * 登出
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Boolean dologout(String token) throws Exception {
		return this.redisService.del("TOKEN_" + token) == 1 ? true : false;
	}
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
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
