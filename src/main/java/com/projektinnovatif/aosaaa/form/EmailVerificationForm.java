package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class EmailVerificationForm implements Serializable, IForm{
	private static final long serialVersionUID = 1L;

	private String verificationtoken;

	public String getVerificationtoken() {
		return verificationtoken;
	}

	public void setVerificationtoken(String verificationtoken) {
		this.verificationtoken = verificationtoken;
	}

	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (verificationtoken == null || !Validator.checkHashFormat(verificationtoken, 32)) {
			errorMessages.put("verificationtoken", "Invalid verification token.");
		}
		return errorMessages;
	}
	
	
	
}
