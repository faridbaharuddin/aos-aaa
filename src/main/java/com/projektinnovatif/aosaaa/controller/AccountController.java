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
import com.projektinnovatif.aosaaa.form.AccountForm;
import com.projektinnovatif.aosaaa.form.ChangePasswordForm;
import com.projektinnovatif.aosaaa.form.EmailVerificationForm;
import com.projektinnovatif.aosaaa.form.ResetPasswordForm;
import com.projektinnovatif.aosaaa.form.ResetPasswordRequestForm;
import com.projektinnovatif.aosaaa.form.SubscriptionForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.service.AccountService;
import com.projektinnovatif.aosaaa.service.AuthNAuthService;
import com.projektinnovatif.aosaaa.service.PersonService;

@CrossOrigin
@RestController
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AuthNAuthService authService;
	
	@Autowired
	PersonService personService;
	
	
	/**
	 * Create an account from Account Form
	 * @param accountForm
	 * @param request
	 * @return
	 * Account object
	 */
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public ResponseEntity<Response> addAccount(@RequestBody AccountForm accountForm, HttpServletRequest request) {
		HashMap<String, String> responseMessages = accountForm.checkFormEntries();
		// Check if username already exists
		if (accountService.usernameExists(accountForm.getUsername())) {
			responseMessages.put("username", "Username already exists.");
		}
		
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			Account account = accountService.addAccount(accountForm);
			if (account != null) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.SERVICE_UNAVAILABLE();
			}
		}
	}
	
	
	/**
	 * Verify the email submitted for account creation
	 * @param emailVerificationForm
	 * @return
	 * True is successful, false otherwise
	 */
	@RequestMapping(value = "/account/verify", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyEmail(@RequestBody EmailVerificationForm emailVerificationForm) {
		HashMap<String, String> responseMessages = emailVerificationForm.checkFormEntries();
		
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		}
		
		if (accountService.activateAccountByEmailToken (emailVerificationForm.getVerificationtoken())) {
			return ResponseBuilder.OK(null);
		} else {
			return ResponseBuilder.NOT_FOUND();
		} 
	}
	

	/**
	 * Requests for a change in subscription status.
	 * Usually for free trial accounts
	 * @param subscriptionForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account/subscription", method = RequestMethod.POST)
	public ResponseEntity<Response> setSubscriptionDetails(@RequestBody SubscriptionForm subscriptionForm, HttpServletRequest request) {
		
		HashMap<String, String> responseMessages = subscriptionForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		}
		
		String authToken = request.getHeader("Authorization");
		Long accountid = authService.getAccountIdFromAuthToken(authToken);
		if (accountid != null) {
			String result = accountService.updateSubscriptionDetails(subscriptionForm, authToken);
			if (result == null) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.BAD_REQUEST("subscription",result);
			}
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/**
	 * Request for password reset token to be sent to the user
	 * @param resetPasswordRequestForm
	 * @param request
	 * @return
	 * True if successful
	 */
	@RequestMapping(value = "/account/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<Response> requestPasswordReset(@RequestBody ResetPasswordRequestForm resetPasswordRequestForm, HttpServletRequest request) {
		HashMap<String, String> responseMessages = resetPasswordRequestForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			if (accountService.resetPasswordByEmail(resetPasswordRequestForm.getEmail())) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.BAD_REQUEST("email", "There is no account associated with that email address."); 
			}
		}
	}
	
	
	/**
	 * Effects a change in password for an account linked to a password reset token
	 * @param resetPasswordForm
	 * @return
	 * True if successful
	 */
	@RequestMapping(value = "/account/resetpassword/changepassword", method = RequestMethod.POST)
	public ResponseEntity<Response> passwordReset(@RequestBody ResetPasswordForm resetPasswordForm) {
		HashMap<String, String> responseMessages = resetPasswordForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			Long accountId = accountService.getAccountIdByPasswordResetToken(resetPasswordForm.getPasswordresettoken());
			if (accountId != null) {
				if (authService.changePassword(accountId, resetPasswordForm.getNewpassword())) {
					accountService.removePasswordResetFlag(accountId);
					return ResponseBuilder.OK(null);
				} else {
					return ResponseBuilder.SERVICE_UNAVAILABLE();
				}
			} else {
				return ResponseBuilder.BAD_REQUEST("passwordresettoken", "Invalid or expired password reset token.");
			}
		}
	}
	
	
	/**
	 * Effects a change in password for a user
	 * @param changePasswordForm
	 * @param request
	 * @return
	 * True if successful
	 */
	@RequestMapping(value = "/account/changepassword", method = RequestMethod.POST)
	public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordForm changePasswordForm,  HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		HashMap<String, String> responseMessages = changePasswordForm.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		} else {
			if (authToken != null && authService.checkIfAuthorised(authToken, "person:edit-self")) {
				Long accountId = authService.checkOldPassword (authToken, changePasswordForm.getOldpassword());
				if (accountId != null && authService.changePassword(accountId, changePasswordForm.getNewpassword())) {
					return ResponseBuilder.OK(null);
				} else {
					return ResponseBuilder.SERVICE_UNAVAILABLE();
				}
			} else {
				return ResponseBuilder.UNAUTHORIZED();
			}
		} 
	}
	
}
