package com.wsy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pageController")
public class PageController {
	
	@RequestMapping("/page")
	public String page(String pageName){
		return pageName;
	}
}
