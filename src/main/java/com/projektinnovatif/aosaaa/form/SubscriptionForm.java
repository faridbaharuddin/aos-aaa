package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class SubscriptionForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String role;
	private String subscriptiontype;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSubscriptiontype() {
		return subscriptiontype;
	}

	public void setSubscriptiontype(String subscriptiontype) {
		this.subscriptiontype = subscriptiontype;
	}


	/**
	 * Check the validity of the entries in the SubscriptionForm
	 * 1. Is role valid?
	 * 2. Is subscription type valid for role?
	 * 
	 * @return A HashMap of errormessages
	 */
	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		// role
		// TODO: Map roles to subscription
		
		return errorMessages;
	}

}
