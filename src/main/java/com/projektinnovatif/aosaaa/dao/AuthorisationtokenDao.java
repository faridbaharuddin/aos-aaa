package com.projektinnovatif.aosaaa.dao;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.model.Authorisationtoken;

@Repository
public class AuthorisationtokenDao extends BaseDao<Authorisationtoken>{

	public AuthorisationtokenDao() {
		super(Authorisationtoken.class);
	}
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public Authorisationtoken getAuthorisationtoken (String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Authorisationtoken authorisationtoken = (Authorisationtoken) session.createQuery("from Authorisationtoken a where a.token=:authtoken and a.isactive=:isactive")
				.setString("authtoken", token)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return authorisationtoken;		
	}
	
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public Long getAccountIdFromAuthorisationtoken (String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Long accountid = (Long) session.createQuery("select a.accountid from Authorisationtoken a where a.token=:authtoken and a.isactive=:isactive")
				.setString("authtoken", token)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return accountid;
	}
	
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public Long getPersonIdFromAuthorisationtoken (String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Long personid = new Long(((BigInteger) (session.createSQLQuery("select p.id from person p right join authorisationtoken a on a.accountid = p.accountid where a.token=:token and a.isactive=:isactivea and p.isactive=:isactivep")
				.setString("token", token)
				.setByte("isactivea", (byte)1)
				.setByte("isactivep", (byte)1)
				.uniqueResult())).longValue());
		return personid;
	}
	
	
}