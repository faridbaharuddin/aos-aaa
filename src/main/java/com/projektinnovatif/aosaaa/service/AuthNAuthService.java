package com.projektinnovatif.aosaaa.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projektinnovatif.aosaaa.dao.AccountDao;
import com.projektinnovatif.aosaaa.dao.AuthorisationtokenDao;
import com.projektinnovatif.aosaaa.dao.PermissionDao;
import com.projektinnovatif.aosaaa.dao.PersonDao;
import com.projektinnovatif.aosaaa.form.SignInForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.model.Authorisationtoken;
import com.projektinnovatif.aosaaa.model.Person;

@Service("authNAuthService")
public class AuthNAuthService {

	@Autowired
	AccountDao accountDao;

	@Autowired
	PersonDao personDao;

	@Autowired
	AuthorisationtokenDao authorisationtokenDao;

	@Autowired
	PermissionDao permissionDao;

	/**
	 * Signs in to an account
	 * 
	 * @param signInForm
	 * @return UUID as an authentication / authorisation token
	 */
	@Transactional
	public Account signInWithUsernamePassword(SignInForm signInForm, HttpServletRequest request) {
		Account account = accountDao.getAccountByUsername(signInForm.getUsername().toLowerCase()); 
		if (account != null) {
			if (account.getIsactivated() == (byte) 1) {
				String hashSaltString = account.getHashsalt();
				byte[] hashSalt = new byte[hashSaltString.length() / 2];
				for (int i = 0; i < account.getHashsalt().length(); i += 2) {
					hashSalt[i / 2] = (byte) ((Character.digit(hashSaltString.charAt(i), 16) << 4)
							+ Character.digit(hashSaltString.charAt(i + 1), 16));
				}
				try {
					String passwordHash = Security.generatePasswordHash(signInForm.getPassword(), hashSalt,
							account.getPbdkfiterations());
					if (passwordHash.compareTo(account.getPasswordhash()) == 0) {
						String uuid = UUID.randomUUID().toString();
						accountDao.update(account);

						Authorisationtoken authToken = new Authorisationtoken();
						authToken.setToken(uuid);
						authToken.setAccountid(account.getId());
						authToken.setDevicedetails(request.getHeader("User-Agent"));
						authToken.setLastactiondt(new Date());
						authToken.setSourceaddress(request.getRemoteAddr());
						authToken.setIsactive((byte) 1);
						authToken = authorisationtokenDao.add(authToken);

						// Coexistence strategy
						// User user = userDao.getUserByAccountID(account.getId().longValue());
						// user.setAuthToken(uuid);

						return account;
					}
				} catch (InvalidKeySpecException ex) {
					ex.printStackTrace();
					return null;
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			} else {
				return account;
			}
		}
		return null;
	}

	@Transactional
	public Long checkOldPassword(String authToken, String oldPassword) {
		Long accountId = authorisationtokenDao.getAccountIdFromAuthorisationtoken(authToken);
		if (accountId != null) {
			Account account = accountDao.getById(accountId.longValue());
			if (account != null) {
				String hashSaltString = account.getHashsalt();
				byte[] hashSalt = new byte[hashSaltString.length() / 2];
				for (int i = 0; i < account.getHashsalt().length(); i += 2) {
					hashSalt[i / 2] = (byte) ((Character.digit(hashSaltString.charAt(i), 16) << 4)
							+ Character.digit(hashSaltString.charAt(i + 1), 16));
				}
				try {
					String passwordHash = Security.generatePasswordHash(oldPassword, hashSalt,
							account.getPbdkfiterations());
					if (passwordHash.compareTo(account.getPasswordhash()) == 0) {
						return accountId;
					}
				} catch (InvalidKeySpecException ex) {
					ex.printStackTrace();
					return null;
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	@Transactional
	public boolean changePassword(Long accountId, String newPassword) {
		try {
			byte[] salt = Security.getSalt(64);
			String passwordhash = Security.generatePasswordHash(newPassword, salt, Security.PBKDF_ITERATIONS);
			Account account = accountDao.getById(accountId.longValue());
			account.setPbdkfiterations(Security.PBKDF_ITERATIONS);
			account.setHashsalt(Security.toHex(salt));
			account.setPasswordhash(passwordhash);
			accountDao.update(account);
			return true;
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return false;
		} catch (InvalidKeySpecException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean signOut(String authToken) {
		Account account = accountDao.getAccountByAuthToken(authToken);
		if (account != null) {
			Authorisationtoken authorisationtoken = authorisationtokenDao.getAuthorisationtoken(authToken);
			authorisationtoken.setIsactive((byte) 0);
			authorisationtoken.setLastactiondt(new Date());
			accountDao.update(account);
			authorisationtokenDao.update(authorisationtoken);
			return true;
		} else
			return false;
	}

	@Transactional
	public Long getAccountIdFromAuthToken(String authToken) {
		if (authToken == null) return null;
		return authorisationtokenDao.getAccountIdFromAuthorisationtoken(authToken);
	}

	@Transactional
	public Long getPersonIdFromAuthToken(String authToken) {
		if (authToken == null) return null;
		return authorisationtokenDao.getPersonIdFromAuthorisationtoken(authToken);
	}

	/**
	 * 
	 * @param authToken
	 * @param action
	 * @return
	 */
	@Transactional
	public boolean checkIfAuthorised(String authToken, String actionString) {
		Long accountid = authorisationtokenDao.getAccountIdFromAuthorisationtoken(authToken);
		if (accountid != null) {
			String[] actionParts = actionString.split("::");
			if (actionParts.length < 2) {
				return permissionDao.checkAction(accountid, actionParts[0]);
			} else if (actionParts.length == 2) {
				System.out.println(actionString);
				return permissionDao.checkActionOnResource(accountid, actionParts[0], Long.parseLong(actionParts[1]));
			}
		} else
			return false;
		return false;
	}

	@Transactional
	public boolean checkIfAuthTokenBelongsToPerson(String authToken, Long personid) {
		Long accountid = authorisationtokenDao.getAccountIdFromAuthorisationtoken(authToken);
		if (accountid != null) {
			Person person = personDao.getPersonByAccountID(accountid.longValue());
			if (person.getId().equals(personid)) {
				return true;
			} else
				return false;
		} else
			return false;
	}

}
