package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class ResetPasswordForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String passwordresettoken;
	private String newpassword;
	private String newpasswordrepeat;
		
	public String getNewpassword() {
		return newpassword;
	}
	
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	public String getNewpasswordrepeat() {
		return newpasswordrepeat;
	}
	
	public void setNewpasswordrepeat(String newpasswordrepeat) {
		this.newpasswordrepeat = newpasswordrepeat;
	}
	
	public String getPasswordresettoken() {
		return passwordresettoken;
	}

	public void setPasswordresettoken(String passwordresettoken) {
		this.passwordresettoken = passwordresettoken;
	}

	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (passwordresettoken == null || !Validator.checkHashFormat(passwordresettoken, 32))
			errorMessages.put("passwordresettoken", "Invalid password reset token.");
		else if (newpassword == null || newpassword.length() == 0) 
			errorMessages.put("newpassword", 
			"New password is required.");
		else if (newpasswordrepeat == null || newpassword.compareTo(newpasswordrepeat) != 0) 
			errorMessages.put("newpasswordrepeat", 
			"New password entries do not match.");
		else if (!Validator.isStrongPassword(newpassword)) 
			errorMessages.put("password",
			"Password should be at least eight characters in length and contain at least three of the following: uppercase letter (A-Z), lowercase letter (a-z), numbers (0-9), special characters (@, $, !, %, *, #, ?, &)");
		return errorMessages;
	}
	
	
}
