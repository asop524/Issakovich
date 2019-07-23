package org.iss.dao.impl;

import org.iss.common.dao.impl.BaseDaoImpl;
import org.iss.dao.BookDao;
import org.springframework.stereotype.Repository;
//<bean id ="bookDao" class="org.iss.dao.impl.BookDaoImpl" parent="baseDao"/>

@Repository("bookDao")
public class BookDaoImpl extends BaseDaoImpl implements BookDao {

}
