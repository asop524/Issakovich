package org.iss.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.iss.common.dao.BaseDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDao {
	
	// 依赖对象EntityManagerFactory
	@Autowired
	@Qualifier("entityManagerFactory") //把一个工厂bean叫entityManagerFactory注入到entityManagerFactory
	private EntityManagerFactory entityManagerFactory;
	
	
	
	
	
	
	/**
	 * 统计总条数
	 * @param hql 查询语句
	 */
	@Override public Integer count(final String hql) {
		if (StringUtils.isEmpty(hql)){
    		throw new IllegalArgumentException("hql is null");
    	}
		
		Object result = this.entityManagerFactory.createEntityManager().createQuery(hql).getSingleResult();
		return ((Long)result).intValue();
	}
	/**
     * 批量修改或删除
     * @param queryString
     * @param values
     */
     public int bulkUpdate(String queryString, Object[] values){

    	 Query query = this.entityManagerFactory.createEntityManager().createQuery(queryString);
    	 for(int i = 0; i < values.length; i++){
    		 query.setParameter(i, values[i]);
    	 }
    	 try{
    		 return query.executeUpdate();
    	 }catch(Exception ex){
    		 throw new RuntimeException(ex);
    	 }
    	 
     }
     /**
      * 批量删除
      * @param entities
      */
     public <T> void deleteAll(Collection<T> entities){
    	 for (T obj : entities){
    		 this.entityManagerFactory.createEntityManager().remove(obj);
    	 }
     }
	/**
	 * 按条件统计总条数
	 * @param hql
	 * @param obj
	 */
	@Override public Integer count(final String hql,final Object... obj) {
		if (ObjectUtils.isEmpty(obj)){
			return count(hql);
		}else{
			if (StringUtils.isEmpty(hql)){
	    		return this.count(hql);
	    	}
			Query query = this.entityManagerFactory.createEntityManager().createQuery(hql);
			for (int i = 0; i < obj.length; i++) {
				query.setParameter(i, obj[i]);
			}
			Object result = query.getSingleResult();
			return ((Long)result).intValue();
		}
	}
	/**
	 * 删除
	 * @param entities
	 */
	@Override public <T> void delete(T entity) {
		this.entityManagerFactory.createEntityManager().remove(entity);
	}
	/**
	 * 判断是否存在
	 * @param entities
	 */
	@Override public <T> boolean exist(Class<T> c, Serializable id) {
		if (get(c, id) != null)
			return true;
		return false;
	}
	/**
	 * 查询全部
	 * @param entities
	 */
	@Override public <T> List<T> find(String queryString) {
		return this.entityManagerFactory.createEntityManager().createQuery(queryString).getResultList();
	}
	/**
     *  Execute an HQL query. 
     * @param bean
     * @return
     */
	@Override public <T> List<T> find(Class<T> bean){
    	String hql = "FROM " + bean.getSimpleName();
    	return find(hql);
    }
	/**
	 * 按条件查询全部
	 * @param queryString
	 * @param values
	 */
	@Override public List<?> find(String queryString, Object[] values) {
		if (ObjectUtils.isEmpty(values)){
			return find(queryString);
		}else{
			Query query = this.entityManagerFactory.createEntityManager().createQuery(queryString);
			for (int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
			return query.getResultList();
		}
	}
	/**
     * 获取唯一实体
     * @param queryString HQL query string
     * @param params query object array params
     * @return unique object
     * @see org.hibernate#Session
     * @throws java.lang.IllegalArgumentException if queryString is null
     */
	@Override public <T> T findUniqueEntity(final String queryString, final Object ... params){
    	if (StringUtils.isEmpty(queryString)){
    		throw new IllegalArgumentException("queryString is null");
    	}
    	if (ObjectUtils.isEmpty(params)){
			return (T)this.entityManagerFactory.createEntityManager().createQuery(queryString).getSingleResult();
    	}else{
    		Query query = this.entityManagerFactory.createEntityManager().createQuery(queryString);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			return (T)query.getSingleResult();
    	}
    }
	/**
	 * 命名查询
	 * @param queryName
	 */
	@Override public <T> List<T> findByNamedQuery(String queryName) {
		if (StringUtils.isEmpty(queryName)){
    		throw new IllegalArgumentException("queryName is null");
    	}
		return this.entityManagerFactory.createEntityManager().createNamedQuery(queryName).getResultList();
	}
	/**
	 * 条件命名查询
	 * @param queryName
	 * @param values
	 */
	@Override public <T> List<T> findByNamedQuery(String queryName, Object... values) {
		if (ObjectUtils.isEmpty(values)){
			return this.findByNamedQuery(queryName);
		}
		Query query = this.entityManagerFactory.createEntityManager().createNamedQuery(queryName);
		for (int i = 0; i < values.length; i++){
			query.setParameter(i, values[i]);
		}
		return query.getResultList();
	}

	/**
	 * 多条件分页查询
	 * @param hql query string
	 * @param startRow begin row
	 * @param pageSize page number
	 * @param params query object params array
	 * @return the query list<?> result
	 * @see org.hibernate#Session
     * @throws java.lang.IllegalArgumentException if queryString is null
	 */
	@Override public <T> List<T> findByPage(String hql, Integer startRow,
			Integer pageSize, Object ... params) {
		if (StringUtils.isEmpty(hql)){
    		throw new IllegalArgumentException("hql is null");
    	}
		if (ObjectUtils.isEmpty(params)) {
			return this.entityManagerFactory.createEntityManager().createQuery(hql).setFirstResult(startRow)
					.setMaxResults(pageSize).getResultList();
		}else {
			Query query = this.entityManagerFactory.createEntityManager().createQuery(hql);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			return query.setFirstResult(startRow).setMaxResults(
					pageSize).getResultList();
		}
	}

	/**
	 * 获取一个实体
	 * @param entityClass
	 * @param id
	 */
	@Override public <T> T get(Class<T> entityClass, Serializable id) {
		return this.entityManagerFactory.createEntityManager().find(entityClass, id);
	}

	@Override public <T> Iterator<T> iterate(String queryString) {
		return this.entityManagerFactory.createEntityManager().createQuery(queryString).getResultList().iterator();
		
	}

	@Override public <T> Iterator<T> iterate(String queryString, Object... values) {
		Query query = this.entityManagerFactory.createEntityManager().createQuery(queryString);
		for (int i = 0; i < values.length; i++){
			query.setParameter(i, values[i]);
		}
		return (Iterator<T>)query.getResultList().iterator();
	}

	/**
	 * 加载一个实体
	 * @param entityClass
	 * @param id
	 */
	@Override public <T> T load(Class<T> entityClass, Serializable id) {
		return this.entityManagerFactory.createEntityManager().getReference(entityClass, id);
	}

	@Override public <T> void persist(T entity) {
		this.entityManagerFactory.createEntityManager().persist(entity);
	}
	
	@Override public <T> void refresh(T entity) {
		this.entityManagerFactory.createEntityManager().refresh(entity);
	}

	/**
	 * 保存
	 * @param entities
	 * @throws java.lang.IllegalArgumentException if entity is null
	 */
	@Override public <T> T save(T entity) {
		if (entity == null){
			throw new IllegalArgumentException("entity is null");
		}
		return this.entityManagerFactory.createEntityManager().merge(entity);
	}

	/**
	 * 保存与修改
	 * @param entities
	 */
	@Override public <T> void saveOrUpdate(T entity) {
		this.entityManagerFactory.createEntityManager().merge(entity);
	}

	/**
	 * 保存与修改全部
	 * @param entities
	 */
	@Override public <T> void saveOrUpdateAll(Collection<T> entities) {
		for (T obj : entities){
			this.entityManagerFactory.createEntityManager().merge(obj);
		}
	}
	/**
	 * 修改
	 * @param entity
	 */
	@Override public <T> void update(T entity) {
		this.entityManagerFactory.createEntityManager().merge(entity);
	}
	/**
	 * 修改所有的实体
	 * @param entities
	 * @throws java.lang.IllegalArgumentException if entities is null
	 */
	@Override public <T> void updateAll(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)){
			throw new IllegalArgumentException("entities is null");
		}
		int i = 0;
		for (Object obj : entities) {
			if (i % 30 == 0) {
				this.entityManagerFactory.createEntityManager().flush();
				this.entityManagerFactory.createEntityManager().clear();
			}
			this.entityManagerFactory.createEntityManager().merge(obj);
			i++;
		}
	}
	/**
	 * 保存所有的实体
	 * @param entities
	 * @throws java.lang.IllegalArgumentException if entities is null
	 */
	@Override public <T> void saveAll(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)){
			throw new IllegalArgumentException("entities is null");
		}
		int i = 0;
		for (T obj : entities) {
			if (i % 30 == 0) {
				this.entityManagerFactory.createEntityManager().flush();
				this.entityManagerFactory.createEntityManager().clear();
			}
			save(obj);
			i++;
		}
	}
	
	
}