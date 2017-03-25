package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class ResetPasswordRequestForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String email;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (email == null || !Validator.isEmail(email))
			errorMessages.put("email", "Invalid email.");
		return errorMessages;
	}
	
	
}
