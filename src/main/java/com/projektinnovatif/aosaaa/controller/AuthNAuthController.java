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

import com.projektinnovatif.aosaaa.dto.Response;
import com.projektinnovatif.aosaaa.dto.ResponseBuilder;
import com.projektinnovatif.aosaaa.form.SignInForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.service.AuthNAuthService;
import com.projektinnovatif.aosaaa.service.PersonService;

@CrossOrigin
@RestController
public class AuthNAuthController {

	@Autowired
	AuthNAuthService authNAuthService;
	
	@Autowired
	PersonService personService;
	
	
	/**
	 * Sign in with a Username and Password. This creates a user token that
	 * will be used during the session.
	 * @param signInForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> signIn(@RequestBody SignInForm signInForm, HttpServletRequest request) {
		if (signInForm.checkFormEntries().size() == 0)
		{			
			Account account = authNAuthService.signInWithUsernamePassword(signInForm, request);
			if (account != null) {
				if (account.getIsactivated() == (byte)1 && account.getCurrentauthtoken() != null) {
					HashMap<String, String> dataReturn = new HashMap<String, String>();
					dataReturn.put("authtoken", account.getCurrentauthtoken());
					dataReturn.put("subscriptiontype", account.getAccounttier());
					dataReturn.put("role", account.getAccountrole());
					return ResponseBuilder.OK(dataReturn);
				} else {
					return ResponseBuilder.BAD_REQUEST("signin", "Account email has not been verified. Please check your email for the verification link to activate your account.");					
				}
			}
		} 
		return ResponseBuilder.BAD_REQUEST("signin", "Invalid username and/or password.");
	}
	
	
	/**
	 * Sign out of an account. This will invalidate the account token currently used.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> signOut(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (authToken != null) {
			if (authNAuthService.signOut(authToken)) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.NOT_FOUND();
			}
		} else {
			return ResponseBuilder.BAD_REQUEST("signout","Session information missing.");
		}
	}
	
	
}
