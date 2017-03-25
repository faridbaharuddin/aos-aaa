package com.projektinnovatif.aosaaa.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projektinnovatif.aosaaa.dto.FacebookInfo;
import com.projektinnovatif.aosaaa.dto.GoogleInfo;
import com.projektinnovatif.aosaaa.dto.Response;
import com.projektinnovatif.aosaaa.dto.ResponseBuilder;
import com.projektinnovatif.aosaaa.form.FacebookOauthForm;
import com.projektinnovatif.aosaaa.form.GoogleOauthForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.service.OauthService;

@CrossOrigin
@RestController
public class OauthController {

	@Autowired
	OauthService oauthService;
	
	
	/**
	 * After receiving the required data regarding a user from Google, this is
	 * called to check directly with Google on the particulars of a User and to
	 * associate his details and Google ID with an AOS-AAA account.
	 * @param googleOauthForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oauth/google/signup", method = RequestMethod.POST)
	public ResponseEntity<Response> addGoogleSignup(@RequestBody GoogleOauthForm googleOauthForm, HttpServletRequest request) {
		HashMap<String, String> responseMessages = googleOauthForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			GoogleInfo googleInfo = oauthService.getGoogleUserInfoFromIdToken(googleOauthForm.getIdtoken());

			// If token info returned is null, then id token provided was
			// invalid
			if (googleInfo == null) {
				return ResponseBuilder.BAD_REQUEST("signin", "Invalid Google token provided.");
			}

			// Check correctness and completeness of information from Google
			String[] scopeItems = { "profile", "email" };
			if (!googleInfo.checkScope(scopeItems)) {
				return ResponseBuilder.BAD_REQUEST("signin", "Invalid Google token provided.");
			}

			// If information from Google is okay
			else {
				Account account = oauthService.signInOrSignUpWithOauth(googleInfo, request);
				if (account != null) {
					HashMap<String, String> dataReturn = new HashMap<String, String>();
					dataReturn.put("authtoken", account.getCurrentauthtoken());
					dataReturn.put("subscriptiontype", account.getAccounttier());
					dataReturn.put("role", account.getAccountrole());
					return ResponseBuilder.OK(dataReturn);
				} else {
					return ResponseBuilder.SERVICE_UNAVAILABLE();
				}
			}
		}
	}

	
	/**
	 * After receiving the required data regarding a user from Facebook, this 
	 * is called to check directly with Google on the particulars of a User and
	 * to associate his details and Google ID with an AOS-AAA account.
	 * @param facebookOauthForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oauth/facebook/signup", method = RequestMethod.POST)
	public ResponseEntity<Response> addFacebookSignup(@RequestBody FacebookOauthForm facebookOauthForm, HttpServletRequest request) {
		HashMap<String, String> responseMessages = facebookOauthForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			FacebookInfo facebookInfo = oauthService.getFacebookUserInfoFromCode(facebookOauthForm.getCode());
			

			// If token info returned is null, then code provided was
			// invalid
			if (facebookInfo == null) {
				return ResponseBuilder.BAD_REQUEST("signin", "Invalid Facebook code provided.");
			}

			// Check correctness and completeness of information from Facebook
			String[] scopeItems = { "profile", "email" };
			if (!facebookInfo.checkScope(scopeItems)) {
				return ResponseBuilder.BAD_REQUEST("signin", "Invalid Facebook code provided.");
			}

			// If information from Facebook is okay
			else {
				Account account = oauthService.signInOrSignUpWithOauth(facebookInfo, request);
				if (account != null) {
					HashMap<String, String> dataReturn = new HashMap<String, String>();
					dataReturn.put("authtoken", account.getCurrentauthtoken());
					dataReturn.put("subscriptiontype", account.getAccounttier());
					dataReturn.put("role", account.getAccountrole());
					return ResponseBuilder.OK(dataReturn);
				} else {
					return ResponseBuilder.SERVICE_UNAVAILABLE();
				}
			}
		}
	}

}
