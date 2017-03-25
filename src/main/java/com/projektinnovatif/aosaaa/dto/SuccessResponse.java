package com.projektinnovatif.aosaaa.dto;

import java.io.Serializable;

public class SuccessResponse extends Response implements Serializable {

	private static final long serialVersionUID = 1L;

	Object data;
	
	SuccessResponse() {
		super(true);
		// TODO Auto-generated constructor stub
	}

	public SuccessResponse(Object data) {
		super(true);
		this.data = data;
	}
	
	public Object getData () {
		return data;
	}
	
	public void setData (Object data) {
		this.data = data;
	}

}
