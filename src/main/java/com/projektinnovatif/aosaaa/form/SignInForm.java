package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class SignInForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String invitationcode;
	
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

	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (username == null || !Validator.isEmail(username)) {
			errorMessages.put("signin", "Invalid username and/or password.");
		}
		if (password == null) {
			errorMessages.put("signin", "Invalid username and/or password.");
		}
		
		return errorMessages;
	}
	
}
