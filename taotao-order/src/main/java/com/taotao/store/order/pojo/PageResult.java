package com.taotao.store.order.pojo;

import java.util.List;

public class PageResult<T> {

	private Integer totle;

	private List<T> data;

	public PageResult() {
	}

	public PageResult(Integer totle, List<T> data) {
		this.totle = totle;
		this.data = data;
	}

	public Integer getTotle() {
		return totle;
	}

	public void setTotle(Integer totle) {
		this.totle = totle;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
