package org.iss.servlet.impl;

import java.util.List;

import org.iss.domain.Book;
import org.iss.domain.User;

public interface AppService {

	//登陆
	User login(String loginname,String password);
	
	//查询所有书
	List<Book>findAllbook();
	
}
