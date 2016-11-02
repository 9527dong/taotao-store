package com.taotao.common.bean;

import java.util.List;

public class EasyUIResult {
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public EasyUIResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public EasyUIResult() {
		super();
	}
	
	
}
