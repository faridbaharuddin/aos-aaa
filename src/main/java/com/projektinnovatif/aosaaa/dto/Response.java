package com.projektinnovatif.aosaaa.dto;

import java.io.Serializable;

public class Response implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	
	Response (boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}	
	
}
