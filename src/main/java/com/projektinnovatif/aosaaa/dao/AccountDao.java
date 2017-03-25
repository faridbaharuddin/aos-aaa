package com.projektinnovatif.aosaaa.dao;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.service.Security;

@Repository
public class AccountDao extends BaseDao<Account>{
	
	public AccountDao() {
		super(Account.class);
	}
	
	/**
	 * Search for unique active accounts by username
	 * @param username
	 * @return Account
	 */
	public Account getAccountByUsername (String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Account account = (Account) session
				.createQuery("from Account a where a.username=:username and a.isactive=:isactive")
				.setString("username", username)
				.setByte("isactive", (byte)1).uniqueResult();
		return account;		
	}
	
	
	public Account getAccountByEmailVerificationToken (String emailVerificationToken) {
		Session session = this.sessionFactory.getCurrentSession();
		Account account = (Account) session
				.createQuery("from Account a where a.activationhash=:emailtoken and a.isactivated=:isactivated and a.isactive=:isactive")
				.setString("emailtoken", emailVerificationToken)
				.setByte("isactivated", (byte)0)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		return account;		
	}
	
	
	public Account getAccountByAuthToken (String authToken) {
		Session session = this.sessionFactory.getCurrentSession();
		Account account = (Account) session.createQuery("from Account a where a.currentauthtoken=:authtoken and a.isactivated=:isactivated and a.isactive=:isactive")
			.setString("authtoken", authToken)
			.setByte("isactivated", (byte)1)
			.setByte("isactive", (byte)1)
			.uniqueResult();
		return account;		
	}
	
	
	public Account getAccountByPasswordResetToken (String passwordResetToken) {
		Session session = this.sessionFactory.getCurrentSession();
		Account account = (Account) session.createQuery("from Account a where a.passwordresethash=:resethash and a.ispasswordresetrequested=:resetrequested and a.isactive=:isactive")
			.setString("resethash", passwordResetToken)
			.setByte("resetrequested", (byte)1)
			.setByte("isactive", (byte)1)
			.uniqueResult();
		if (account != null && account.getPasswordresethashsentdt().after(new Date(System.currentTimeMillis() - (Security.PASSWORD_RESET_EXPIRY_MINUTES * 60 * 1000)))) {
			return account;
		}
		return null;		
	}

}