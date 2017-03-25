package com.projektinnovatif.aosaaa.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.model.Subscription;

@Repository
public class SubscriptionDao extends BaseDao<Subscription> {

	public SubscriptionDao() {
		super(Subscription.class);
	}

	
	/**
	 * Retrieve active Subscription by Account ID
	 * @param id
	 * @return
	 * 
	 */
	public Subscription getSubscriptionByAccountId (Long accountid) {
		Session session = this.sessionFactory.getCurrentSession();
		Subscription subscription = (Subscription) session
				.createQuery("from Subscription s where s.accountid=:accountid and s.startdt <= NOW() and s.enddt >= NOW() and s.isactive=:isactive")
				.setLong("accountid", accountid)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return subscription;		
	}
	
	
	/**
	 * Retrieve past Subscription by Account ID and type
	 * @param id
	 * @return
	 * 
	 */
	public Subscription getPastSubscriptionByAccountId (Long accountid, String subscriptiontype) {
		Session session = this.sessionFactory.getCurrentSession();
		Subscription subscription = (Subscription) session
				.createQuery("from Subscription s where s.accountid=:accountid and s.subscriptiontype=:subscriptiontype and s.enddt < NOW() and s.isactive=:isactive")
				.setLong("accountid", accountid)
				.setString("subscriptiontype", subscriptiontype)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return subscription;		
	}
	
}