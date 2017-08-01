package com.taotao.web.threadlocal;

import com.taotao.sso.query.bean.User;
/**
 * 通过线程来存储user对象，防止2次查询
 * @author Dong
 *
 */
public class UserThreadLocal {
	private static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();
	
	private UserThreadLocal(){
		
	}
	
	public static void set(User user){
		LOCAL.set(user);
	}
	
	public static User get(){
		return LOCAL.get();
	}
}
