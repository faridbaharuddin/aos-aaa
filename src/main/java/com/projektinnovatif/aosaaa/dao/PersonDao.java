package com.projektinnovatif.aosaaa.dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.dto.BasicPersonInfo;
import com.projektinnovatif.aosaaa.model.Person;

@Repository
public class PersonDao extends BaseDao<Person> {

	public PersonDao() {
		super(Person.class);
	}
	
	/**
	 * Retrieve unique person by Account ID
	 * @param id
	 * @return
	 */
	public Person getPersonByAccountID (Long accountid) {
		Session session = this.sessionFactory.getCurrentSession();
		Person person = (Person) session.createQuery("from Person p where p.accountid=:accountid and p.isactive=:isactive")
				.setLong("accountid", accountid)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return person;		
	}
	
	
	/**
	 * Retrieve unique person by Account ID
	 * @param id
	 * @return
	 */
	public Long getPersonIdByAccountId (Long accountid) {
		Session session = this.sessionFactory.getCurrentSession();
		Long personid = (Long) session.createQuery("select p.id from Person p where p.accountid=:accountid and p.isactive=:isactive")
				.setLong("accountid", accountid)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return personid;		
	}
	
	/**
	 * Retrieve unique person by Email address
	 * @param email
	 * @return
	 */
	public Person getPersonByEmail (String email) {
		Session session = this.sessionFactory.getCurrentSession();
		Person person = (Person) session.createQuery("from Person p where p.email=:email and p.isactive=:isactive")
				.setString("email", email)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return person;		
	}
	
	
	public BasicPersonInfo getBasicPersonInfo (Long personid) {
		Session session = this.sessionFactory.getCurrentSession();
		Object[] personObject = (Object[]) session
				.createSQLQuery("select p.id, p.fullname, p.profileimageurl from person p where p.id=:personid and p.isactive=:isactive")
				.setLong("personid", personid)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		BasicPersonInfo bpi = new BasicPersonInfo(((BigInteger)personObject[0]).longValue(), (String)personObject[1], (String)personObject[2], null);
		return bpi;
	}
	
	
	/**
	 * Get persons info
	 * @param List of personids
	 * @return
	 * List of basic info (id, fullname, profileimageurl) of qlass members
	 */
	public List<BasicPersonInfo> getPersons (List<Long> personIds) {
		Session session = this.sessionFactory.getCurrentSession();
		List<BasicPersonInfo> memberList = new ArrayList<BasicPersonInfo>();
		@SuppressWarnings("unchecked")
		List<Object[]> memberObjectList = session
				.createSQLQuery("select p.id, p.fullname, p.profileimageurl from person p where p.id in :idlist and p.isactive=:isactive")
				.setParameterList("idlist", personIds)
				.setByte("isactive", (byte)1)
				.list();
		for (Object[] mo: memberObjectList) {
			memberList.add(new BasicPersonInfo(((BigInteger)mo[0]).longValue(), (String)mo[1], (String)mo[2], null));
		}
		return memberList;		
	}
	
}