package com.wsy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wsy.domain.po.UserEntity;
import com.wsy.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	protected Logger printSQL = LoggerFactory.getLogger("UserController: ");
	
	@RequestMapping("/userView")
	public String userView(String uuid, Model model){
		
		UserEntity user = userService.findUserById(uuid);
		model.addAttribute("user", user);
		
		return "/user/userView";
	}
	
	@RequestMapping(value = "/userLook/{uuid}")
	public String userLook(@PathVariable String uuid, Model model){
		//UserEntity user = userService.findUserById(uuid);
		
		UserEntity user = userService.getUserById(uuid);
		
		model.addAttribute("user", user);
		
		return "/user/userView";
	}

}
