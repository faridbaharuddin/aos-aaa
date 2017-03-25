package com.projektinnovatif.aosaaa.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.projektinnovatif.aosaaa.dao.AccountDao;
import com.projektinnovatif.aosaaa.dao.PermissionDao;
import com.projektinnovatif.aosaaa.dao.PersonDao;
import com.projektinnovatif.aosaaa.dao.SubscriptionDao;
import com.projektinnovatif.aosaaa.form.AccountForm;
import com.projektinnovatif.aosaaa.form.SubscriptionForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.model.Person;

@Service("accountService")
public class AccountService {
	
	@Autowired
	AccountDao accountDao;

	@Autowired
	PersonDao personDao;
	
	@Autowired
	PermissionDao permissionDao;
	
	@Autowired
	SubscriptionDao subscriptionDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${siteurl}")
	private String siteUrl;
	
	
	@Transactional
	public List<Account> getAllAccounts() {
		return accountDao.getAll();
	}

	@Transactional
	public Account getAccount(Long id) {
		return accountDao.getById(id);
	}
	
	
	// NOTFORPROD: FOR TESTING ONLY, REMOVE FOR PRODUCTION
	@Transactional
	public boolean deleteAccount(Long accountid) {
		try {
			Account account = accountDao.getById(accountid);
			if (account != null) {
				permissionDao.removeAllPermissionsForAccount(accountid);
				Person person = personDao.getPersonByAccountID(accountid);
				personDao.delete(person);
				// User user = userDao.getUserByAccountID(accountid);
				// if (user != null) userDao.delete(user);
				accountDao.delete(account);
				return true;
			} else return false;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	/**
	 * Check if a username already exists
	 * @param username
	 * @return true if a username exists, false otherwise
	 */
	@Transactional
	public boolean usernameExists (String username) {
		if (accountDao.getAccountByUsername(username) != null) 	return true;
		else return false;
	}
	
	
	/**
	 * Add an account
	 * @param accountForm
	 * @return Account object created
	 */
	@Transactional
	public Account addAccount (AccountForm accountForm) {
		try {
			Account account = createAccount(accountForm);
	        
			// Create person object
			Person person = new Person();
			person.setAccountid(account.getId());
			person.setFullname(accountForm.getFirstname() + " " + accountForm.getLastname());
			person.setFirstname(accountForm.getFirstname());
			person.setLastname(accountForm.getLastname());
			person.setEmail(accountForm.getUsername().toLowerCase());
			person.setMobilecontactnumber(accountForm.getMobilenumber());
			person.setIsactive((byte)1);
			person = personDao.add(person);
			
			// Add basic account permissions
			permissionDao.addRolePermissions(account.getId(), "personall");
			permissionDao.addRolePermissions(account.getId(), "persontrainee");
			
			/*
			// TODO: Coexistence strategy. For removal later on.
			User user = new User();
			user.setFirstName(accountForm.getFirstname());
			user.setLastName(accountForm.getLastname());
			user.setEmail(accountForm.getUsername());
			user.setAccountid(account.getId());
			user.setContactNumberMobile(accountForm.getMobilenumber());
			user = userDao.addUser(user);
			*/
			
			// Send activation as a last step after all the above have occured.
			if (!this.sendActivationEmail(account.getUsername(), account.getActivationhash())) {
				return null;
			} else {
				account.setActivatehashsentdt(new Date());
		        account.setIsactive((byte)1);
		        accountDao.update(account);
		        return account;
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		} catch (InvalidKeySpecException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	/**
	 * Add an account
	 * @param accountForm
	 * @return Account object created
	 */
	@Transactional
	public Account addAccountWithExistingPerson (AccountForm accountForm, Long personid) {
		try {
			byte[] salt = Security.getSalt(64);
			String passwordhash = Security.generatePasswordHash(accountForm.getPassword(), salt, Security.PBKDF_ITERATIONS);
			
			Account account = new Account ();
			account.setAccountrole("");
			account.setAccounttier("");
			account.setUsername(accountForm.getUsername());
			account.setMobilenumber(accountForm.getMobilenumber());
			account.setPbdkfiterations(Security.PBKDF_ITERATIONS);
			account.setHashsalt(Security.toHex(salt));
			account.setPasswordhash(passwordhash);
			account.setIsactivated((byte)0);
			account.setIspasswordresetrequested((byte)0);
			account.setActivationhash(Security.toHex(Security.getSalt(16)));
			account.setIsactive((byte)0);
			account = accountDao.add(account);
			
			if (!this.sendActivationEmail(account.getUsername(), account.getActivationhash())) {
				return null;
			};
	    
			// If the email was queued or sent successfully, set the account to active 
			// and create a user profile
			account.setActivatehashsentdt(new Date());
	        account.setIsactive((byte)1);
	        accountDao.update(account);
	        
			Person person = personDao.getById(personid.longValue());
			person.setAccountid(account.getId());
			person.setFirstname(accountForm.getFirstname());
			person.setLastname(accountForm.getLastname());
			person.setMobilecontactnumber(accountForm.getMobilenumber());
			person.setIsactive((byte)1);
			personDao.update(person);
			
			return account;
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		} catch (InvalidKeySpecException ex) {
			ex.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean activateAccountByEmailToken (String emailVerificationToken) {
		Account account = accountDao.getAccountByEmailVerificationToken(emailVerificationToken);
		if (account != null) {
			account.setActivateddt(new Date());
			account.setIsactivated((byte)1);
			accountDao.update(account);
			return true;
		}
		return false;
	}

	
	@Transactional
	public boolean resetPasswordByEmail (String emailAddress) {
		Account account = accountDao.getAccountByUsername(emailAddress);
		if (account != null) {
			try {
				account.setPasswordresethash(Security.toHex(Security.getSalt(16)));
				if (!this.sendPasswordResetEmail(account.getUsername(), account.getPasswordresethash())) {
					return false;
				} else {
					account.setPasswordresethashsentdt(new Date());
					account.setIspasswordresetrequested((byte)1);
					accountDao.update(account);
					return true;
				}
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
				return false;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		} else return false;
	}
	
	@Transactional
	public String updateSubscriptionDetails (SubscriptionForm subscriptionForm, String authToken) {
		Account account = accountDao.getAccountByAuthToken(authToken);
		if (account != null) {
			account.setAccountrole(subscriptionForm.getRole());
			account.setAccounttier(subscriptionForm.getSubscriptiontype());
			accountDao.update(account);
			
		}
		return "Account does not exist";
	}
	
	
	@Transactional
	public Long getAccountIdByPasswordResetToken (String passwordResetToken) {
		Account account = accountDao.getAccountByPasswordResetToken(passwordResetToken);
		if (account != null) {
			return account.getId();
		} else return null;
	}
	
	@Transactional
	public void	removePasswordResetFlag (Long accountid) {
		Account account = accountDao.getById(accountid);
		account.setPasswordresethash(null);
		account.setPasswordresethashsentdt(null);
		account.setIspasswordresetrequested((byte)0);
		accountDao.update(account);
	}
	
	
	// SUPPORT: Supporting Methods
	
	/**
	 * Sends an activation email with the verification token
	 * @param emailAddress
	 * @param verificationToken
	 * @return 
	 * true if the process succeeded
	 * false if the process failed
	 */
	private boolean sendActivationEmail (String emailAddress, String verificationToken) {
		try {
			// TODO: Send to a queue to process
			String subject = "Registration Confirmation";
			String confirmationUrl = siteUrl + "/#/confirm/" + verificationToken;
			String message = "Please visit the following link to verify your email and proceed with account registration\n\n";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject(subject);
			email.setText(message + confirmationUrl);
			mailSender.send(email);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	/**
	 * Sends a password reset email with the reset token
	 * @param emailAddress
	 * @param resetToken
	 * @return 
	 * true if the process succeeded
	 * false if the process failed
	 */
	private boolean sendPasswordResetEmail (String emailAddress, String resetToken) {
		try {
			// TODO: Send to a queue to process
			String subject = "Password Reset Request";
			String passwordResetUrl = siteUrl + "/#/resetpassword/" + resetToken;
			String message = "Please visit the following link to reset your password.\n\n";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject(subject);
			email.setText(message + passwordResetUrl);
			mailSender.send(email);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	/**
	 * Account creation from Account Form.
	 * @param accountForm
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private Account createAccount(AccountForm accountForm) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = Security.getSalt(64);
		String passwordhash = Security.generatePasswordHash(accountForm.getPassword(), salt, Security.PBKDF_ITERATIONS);
		
		Account account = new Account ();

		account.setAccountrole("");
		account.setAccounttier("");
		account.setAccounttype("aosaaa");
		account.setUsername(accountForm.getUsername().toLowerCase()); // For username, important to be in lowercase
		account.setMobilenumber(accountForm.getMobilenumber());
		account.setPbdkfiterations(Security.PBKDF_ITERATIONS);
		account.setHashsalt(Security.toHex(salt));
		account.setPasswordhash(passwordhash);
		account.setIsactivated((byte)0);
		account.setIspasswordresetrequested((byte)0);
		account.setActivationhash(Security.toHex(Security.getSalt(16)));
		account.setIsactive((byte)0);
		account = accountDao.add(account);
		
		return account;
	}
	
}