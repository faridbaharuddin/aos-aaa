package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class GoogleOauthForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String idtoken;
	private String invitationcode;

	public String getIdtoken() {
		return idtoken;
	}

	public void setIdtoken(String idtoken) {
		this.idtoken = idtoken;
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
		
		// idtoken
		if (idtoken.length() == 0) {
			errorMessages.put("idtoken", "ID Token required.");
		} else if (!Validator.isGoogleIdToken(idtoken)) {
			errorMessages.put("idtoken", "Invalid Google ID Token.");
		} 
		
		return errorMessages;
	}

}
