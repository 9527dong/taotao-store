package com.taotao.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {
	
	private static final String COOKIE_NAME = "TT_TOKEN";
	
	@Autowired
	private UserService userService;
	/**
	 * 注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(User user,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String token = userService.doLogin(user.getUsername(), user.getPassword());
			if(StringUtils.isEmpty(token)){
				//登录失败
				result.put("status", 500);
				return result;
			}

			//登录成功，保存token 到 cookie
			result.put("status", 200);
			
			CookieUtils.setCookie(request, response, COOKIE_NAME, token);
		} catch (Exception e) {
			e.printStackTrace();
			//登录失败
			result.put("status", 500);
		}
		return result;
	}
	/**
	 * 检查数据是否可用
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> check(@PathVariable("param") String param,
			@PathVariable("type") Integer type) {
		try {
			Boolean bool = this.userService.check(param, type);
			if(bool == null){
				//参数有误
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			//为了兼容前端的逻辑，做出妥协
			return ResponseEntity.ok(!bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="doRegister", method = RequestMethod.POST)
	public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult){
		Map<String, Object> result = new HashMap<String, Object>();
		if(bindingResult.hasErrors()){
			//没有通过校验
			result.put("status", "400");
			
			//获取错误信息
			List<String> msgs = new ArrayList<String>();
			List<ObjectError> allError = bindingResult.getAllErrors();
			for (ObjectError objectError : allError) {
				String msg = objectError.getDefaultMessage();
				msgs.add(msg);
			}
			result.put("data", "参数有误!" + StringUtils.join(msgs, '|'));
			
			return result;
		}
		
		try {
			Boolean bool = this.userService.doRegister(user);
			if(bool){
				result.put("status", "200");
			}else{
				result.put("status", "500");
				result.put("data", "注册失败！请重新注册！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "{token}", method = RequestMethod.GET)
	public ResponseEntity<User> queryUserByToken(@PathVariable("token") String token){
//		try {
//			User user = this.userService.queryUserByToken(token);
//			if(null == user){
//				//资源不存在
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//			}
//			return ResponseEntity.ok(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		User user = new User();
		user.setUsername("该服务没有了，以后别调用了！请访问ssoquery.taotao.com或者是dubbo中的服务");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public Map<String, Object> deleteUserByToken(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
		String loginUrl = "http://sso.taotao.com/user/login.html";
		
		try {
			if(StringUtils.isEmpty(token)){
				//退出登录失败
				result.put("status", 500);
				return result;
			}
			this.userService.dologout(token);
			//登录成功，保存token 到 cookie
			//删除cookie
			CookieUtils.deleteCookie(request, response, COOKIE_NAME);
			//未登录，需要跳转到登录页面
			response.sendRedirect(loginUrl);
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			//登录失败
			result.put("status", 500);
		}
		return result;
	} 
}
