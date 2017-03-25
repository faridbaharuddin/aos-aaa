package com.projektinnovatif.aosaaa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookInfo extends OauthInfo {

	public FacebookInfo () {
		super();
		this.setOauthType("facebook");
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		this.setPicture("http://graph.facebook.com/" + id + "/picture?type=normal");
	}
	
	public String getFirst_name() {
		return this.getGiven_name();
	}

	public void setFirst_name(String first_name) {
		this.setGiven_name(first_name);
	}

	public String getLast_name() {
		return this.getFamily_name();
	}

	public void setLast_name(String last_name) {
		this.setFamily_name(last_name);
	}

	protected boolean checkProfile () {
		if (this.getName() == null) return false;
		else return true;
	}

}
