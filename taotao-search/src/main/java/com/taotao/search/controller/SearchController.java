package com.taotao.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Dong on 2017/7/13.
 */
@Controller
public class SearchController {
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(){
        return null;
    }
}
