package org.iss.dao;

import org.iss.common.dao.BaseDao;
import org.iss.domain.User;

// 持久层接口 继承 BaseDao
public interface UserDao extends BaseDao{
	/**
	 * 根据loginname和password查询User
	 * @param loginname
	 * @param password
	 * @return 如果用户存在，返回包含用户所有信息的User对象
	 * 		如果用户不存在，返回null
	 * */
	User findParam(String loginname,String password);
		//↑bookDao不用写方法，但userDao这里要，因为它是个性化方法
}
