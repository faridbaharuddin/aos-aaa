package com.projektinnovatif.aosaaa.dao;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.model.BaseModel;

@Repository
public class BaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	private Class<T> entityClass;

	public BaseDao() {
	}

	public BaseDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<T> getAll() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<T> allT = session.createQuery("from " + entityClass.getSimpleName() + " where isactive=:isactive")
				.setByte("isactive", (byte) 1).list();
		return allT;
	}
	
	public T getById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		T t = (T) session.createQuery("from " + entityClass.getSimpleName() + " where id=:id and isactive=:isactive")
				.setLong("id", id)
				.setByte("isactive", (byte) 1)
				.uniqueResult();
		return t;
	}

	public List<T> getByIds (List<Long> ids) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<T> allTByIds = session.createQuery("from " + entityClass.getSimpleName() + " where id in :idlist and isactive=:isactive")
		.setByte("isactive", (byte)1)
		.setParameterList("idlist", ids)
		.list();
		session.setFlushMode(FlushMode.MANUAL);
		return allTByIds;
	}
	
	public T add(T t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(t);
		return t;
	}

	public void update(T t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(t);
	}

	public void expire(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		T t = (T) session.load(entityClass, id);
		if (t != null) {
			((BaseModel) t).setIsactive((byte) 0);
			session.update(t);
		}
	}

	public void delete(T t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(t);
	}
}