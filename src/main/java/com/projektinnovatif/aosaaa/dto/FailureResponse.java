package com.projektinnovatif.aosaaa.dto;

import java.io.Serializable;
import java.util.HashMap;

public class FailureResponse extends Response implements Serializable{

	private static final long serialVersionUID = 1L;

	HashMap<String, String> errormessages;

	public FailureResponse () {
		super(false);
	}
	
	public FailureResponse (HashMap<String, String> errormessages) {
		super(false);
		this.errormessages = errormessages;
	}

	public FailureResponse (String key, String message) {
		super(false);
		this.errormessages = new HashMap<String, String>();
		errormessages.put(key, message);
	}
	
	public HashMap<String, String> getErrormessages() {
		return errormessages;
	}

	public void setErrormessages(HashMap<String, String> errormessages) {
		this.errormessages = errormessages;
	}

	
	public void addErrorMessage (String key, String message) {
		errormessages.put(key, message);
	}
}
