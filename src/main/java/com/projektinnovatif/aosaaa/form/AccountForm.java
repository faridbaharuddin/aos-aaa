package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class AccountForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String passwordrepeat;
	private String firstname;
	private String lastname;
	private String mobilenumber;
	private String invitationcode;
	
	public String getPasswordrepeat() {
		return passwordrepeat;
	}
	
	public void setPasswordrepeat(String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}
	
	public String getMobilenumber() {
		return mobilenumber;
	}
	
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	/**
	 * Check the validity of the entries in the AccountForm
	 * 1. Is username blank?
	 * 2. If not, is format of username correct?
	 * 3. Is password blank?
	 * 4. If not, do the passwords match?
	 * 5. If so, are the passwords strong?
	 * 6. Is firstname blank?
	 * 7. Is lastname blank?
	 * @return A HashMap of errormessages
	 */
	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		// username
		if (username == null || username.length() == 0) {
			errorMessages.put("username", "Username is required.");
		} else if (!Validator.isEmail(username)) {
			errorMessages.put("username", "Please enter a valid email as the username.");
		} 
		
		// password, passwordrepeat
		if (password == null || password.length() == 0) errorMessages.put("password", "New password is required.");
		else if (password.compareTo(passwordrepeat) != 0) errorMessages.put("passwordrepeat", "New password entries do not match.");
		else if (!Validator.isStrongPassword(password)) 
			errorMessages.put("password",
			"Password should be at least eight characters in length and contain at least three of the following: uppercase letter (A-Z), lowercase letter (a-z), numbers (0-9), special characters (@, $, !, %, *, #, ?, &)");
		
		// firstname, lastname
		if (firstname == null || !Validator.isName(firstname)) {
			errorMessages.put("firstname", "A valid First Name is required.");
		}
		
		if (lastname == null || !Validator.isName(lastname)) {
			errorMessages.put("lastname", "A valid Last Name is required.");
		}
		
		return errorMessages;
	}

}
