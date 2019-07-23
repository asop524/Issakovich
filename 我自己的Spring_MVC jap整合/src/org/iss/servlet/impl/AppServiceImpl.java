package org.iss.servlet.impl;

import java.util.List;

import org.iss.dao.BookDao;
import org.iss.dao.UserDao;
import org.iss.domain.Book;
import org.iss.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


//<bean id ="baseDao" abstract="true" 
//p:entityManagerFactory-ref="entityManagerFactory"/>  	 	

@Service("appService")//声明成一个bean，并告诉它是一个服务层的bean
public class AppServiceImpl implements AppService {

	//依赖注入Dao层接口
	@Autowired
	@Qualifier("userDao") //就是userDaoImpl@Repository("userDao")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("bookDao")//就是BookDaoImpl@Repository("bookDao")
	private BookDao bookDao;
		
	



	@Override
	public User login(String loginname, String password) {
		return userDao.findParam(loginname, password);
	}

	@Override
	public List<Book> findAllbook() {
		
		return bookDao.find(Book.class);
	}

}
