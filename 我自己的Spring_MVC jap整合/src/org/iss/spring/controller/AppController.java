package org.iss.spring.controller;

import java.util.List;

import org.iss.domain.Book;
import org.iss.domain.User;
import org.iss.servlet.impl.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

//处理用户请求的控制器
@Controller	
public class AppController {
	
	@Autowired
	@Qualifier("appService")
	private AppService appService;
	
	@PostMapping("/login")
	public String login(String loginname,String password,WebRequest webRequest) {
		//去数据库查询
		User user=appService.login(loginname, password);
		
		if(user != null) {
			//保存到session
			webRequest.setAttribute("user", user, WebRequest.SCOPE_SESSION);
			//跳转到另一个控制器
			return "redirect:/findBooks";
		}else {
			//不成功就保存提示信息
			webRequest.setAttribute("message", "登录名或密码错误！请重新输入！", WebRequest.SCOPE_SESSION);
			
			//跳转
			return "index";
		}
		
	}
	
	
	
	@RequestMapping("/findBooks")
	public String main(Model model) {
		//查找所有图书信息
		List<Book>books = appService.findAllbook();
		//保存到model
		model.addAttribute("books", books);
		//返回
		return "main";		
	}
}
