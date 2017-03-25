package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class FacebookOauthForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String code;
	private String invitationcode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	/**
	 * Check the validity of the entries in the GoogleOauthForm
	 * 1. Is ID token in the correct format?
	 * @return A HashMap of errormessages
	 */
	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		// code
		if (code.length() == 0) {
			errorMessages.put("code", "Facebook code required.");
		}
		
		return errorMessages;
	}

}
