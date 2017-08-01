package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.search.bean.SearchResult;
import com.taotao.search.service.ItemSearchService;

@Controller
public class SearchController {
	public static final Integer ROWS = 32;
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam("q") String keyWords,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		ModelAndView mv = new ModelAndView("search");
		
		try {
			keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			keyWords = "";
		}
		//搜索关键字
		mv.addObject("query", keyWords);
		SearchResult searchResult = null;
		try {
			searchResult = this.itemSearchService.search(keyWords, page, ROWS);
		} catch (SolrServerException e) {
			e.printStackTrace();
			searchResult = new SearchResult(0L, null);
		};
		//搜索结果数据
		mv.addObject("itemList", searchResult.getList());
		
		//页数
		mv.addObject("page", page);
		
		int total = searchResult.getTotal().intValue();
		int pages = total % ROWS == 0 ? total / ROWS : total / ROWS + 1;
		//总页数
		mv.addObject("pages", pages);
		
		return mv;
	}
}
