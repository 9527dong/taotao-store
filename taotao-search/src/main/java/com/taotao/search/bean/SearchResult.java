package com.taotao.search.bean;

import java.util.List;

/**
 * Created by Dong on 2017/7/15.
 */
public class SearchResult {
    private Long total;
    private List<?> list;

	public SearchResult(Long total, List<?> list) {
		super();
		this.total = total;
		this.list = list;
	}
	public SearchResult() {
		super();
	}
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
