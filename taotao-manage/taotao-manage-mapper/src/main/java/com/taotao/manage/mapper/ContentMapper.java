package com.taotao.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;

public interface ContentMapper extends Mapper<Content> {
	/**
	 * 根据categoryId查询内容列表，并且按照更新时间排序
	 */
	public List<Content> queryContentList(Long categoryId);
}
