package org.iss.dao.impl;

import org.iss.common.dao.impl.BaseDaoImpl;
import org.iss.dao.UserDao;
import org.iss.domain.User;
import org.springframework.stereotype.Repository;

//<bean id ="userDao"	class="org.iss.dao.impl.UserDaoImpl" 	parent="baseDao"/>
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User findParam(String loginname, String password) {
		//创建JPQL
		String jsql = " select u from User u where u.loginname = ?0 and u.password = ?1 ";
		//调用BaseDao的findUniqueEntity方法查询
		return this.findUniqueEntity(jsql, loginname,password);
		
		
	}

}
