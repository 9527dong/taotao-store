package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.taotao.manage.pojo.Item{
	public String[] getImages(){
		//使用StringUtils.split 效率更高
		return StringUtils.split(super.getImage(), ',');
	}
}
