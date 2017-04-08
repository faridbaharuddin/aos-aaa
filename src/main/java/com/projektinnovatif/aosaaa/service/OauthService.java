package com.projektinnovatif.aosaaa.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.projektinnovatif.aosaaa.dao.AccountDao;
import com.projektinnovatif.aosaaa.dao.AuthorisationtokenDao;
import com.projektinnovatif.aosaaa.dao.PermissionDao;
import com.projektinnovatif.aosaaa.dao.PersonDao;
import com.projektinnovatif.aosaaa.dto.FacebookAccessToken;
import com.projektinnovatif.aosaaa.dto.FacebookInfo;
import com.projektinnovatif.aosaaa.dto.GoogleInfo;
import com.projektinnovatif.aosaaa.dto.OauthInfo;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.model.Authorisationtoken;
import com.projektinnovatif.aosaaa.model.Person;


@Service("oauthService")
public class OauthService {

	@Autowired
	AccountDao accountDao;

	@Autowired
	PersonDao personDao;
	
	@Autowired
	PermissionDao permissionDao;

	@Autowired
	AuthorisationtokenDao authorisationtokenDao;

	@Autowired
	JavaMailSender mailSender;
	
	@Value("${googleservicesclientid}")
	String googleServicesClientId;
	
	@Value("${facebookservicesclientid}")
	String facebookServicesClientId;
	
	@Value("${facebooksecret}")
	String facebookSecret;

	@Value("${facebookredirecturl}")
	String facebookRedirectUrl;
	
	private final String googleVerifyTokenUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
	private final String facebookGetAccessCodeUrl = "https://graph.facebook.com/v2.8/oauth/access_token?client_id={0}&redirect_uri={1}&client_secret={2}&code={3}";
	private final String facebookGraphMeApi = "https://graph.facebook.com/me/?fields=first_name,last_name,id,email,name&access_token=";
	
	

	@Transactional
	public GoogleInfo getGoogleUserInfoFromIdToken(String googleTokenId) {
		RestTemplate restTemplate = new RestTemplate();
		String queryUrl = googleVerifyTokenUrl + googleTokenId;
		try {
			GoogleInfo googleReply = restTemplate.getForObject(queryUrl, GoogleInfo.class);
			if (googleReply.getAud().compareTo(googleServicesClientId) == 0)
				return googleReply;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	
	@Transactional
	public FacebookInfo getFacebookUserInfoFromCode(String facebookCode) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			String queryUrl = MessageFormat.format(facebookGetAccessCodeUrl, facebookServicesClientId, facebookRedirectUrl, facebookSecret, facebookCode);
			System.out.println(queryUrl);
			FacebookAccessToken facebookAccessToken = restTemplate.getForObject(queryUrl, FacebookAccessToken.class);
			// TODO: Store access token
			System.out.println(facebookAccessToken);
			String graphRequestUrl = facebookGraphMeApi + facebookAccessToken.getAccess_token();
			System.out.println(graphRequestUrl);
			FacebookInfo facebookInfo = restTemplate.getForObject(graphRequestUrl, FacebookInfo.class);
			return facebookInfo;
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getResponseBodyAsString());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Account signInOrSignUpWithOauth (OauthInfo oauthInfo, HttpServletRequest request) {
		Account account;
		Person person;
		try {
			account = accountDao.getAccountByUsername(oauthInfo.getId());
			if (account == null) {
				account = new Account();
				account.setAccounttype(oauthInfo.getOauthType());
				account.setUsername(oauthInfo.getId());
				account.setAccountrole("");
				account.setAccounttier("");
				account.setPbdkfiterations(0);
				account.setIsactivated((byte) 1);
				account.setIspasswordresetrequested((byte) 0);
				account.setIsactive((byte) 1);
				account = accountDao.add(account);

				person = new Person();
				person.setAccountid(account.getId());
				person.setFullname(oauthInfo.getName());
				person.setFirstname(oauthInfo.getGiven_name());
				person.setLastname(oauthInfo.getFamily_name());
				person.setProfileimageurl(oauthInfo.getPicture());
				person.setEmail(oauthInfo.getEmail());
				person.setIsactive((byte) 1);
				person = personDao.add(person);
				
				permissionDao.addRolePermissions(account.getId(), 1L);

			} else {
				person = personDao.getPersonByAccountID(account.getId().longValue());
				
				// Update details
				person.setFullname(oauthInfo.getName());
				person.setFirstname(oauthInfo.getGiven_name());
				person.setLastname(oauthInfo.getFamily_name());
				person.setEmail(oauthInfo.getEmail());
				person.setProfileimageurl(oauthInfo.getPicture());
				
				personDao.update(person);
				
			}

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

			return account;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
