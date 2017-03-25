package com.projektinnovatif.aosaaa.dto;

import com.projektinnovatif.aosaaa.form.Validator;

public abstract class OauthInfo {
	
	private String oauthType;
	private String id;
	private String email;
	private String name;
	private String picture;
	private String given_name;
	private String family_name;
	
	public String getOauthType() {
		return oauthType;
	}

	public void setOauthType(String oauthType) {
		this.oauthType = oauthType;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	/**
	 * Check whether the reply belongs to this application and whether
	 * all the fields requested for in the scope are present
	 * @param scopeItems
	 * @return
	 * false if a condition is not met
	 * true if all conditions are met
	 */
	public boolean checkScope (String[] scopeItems) {
		for (String scopeItem : scopeItems) {
			switch (scopeItem) {
			case "profile":
				if (!this.checkProfile()) return false;
				break;
			case "email":
				if (!this.checkEmail()) return false;
				break;
			default:
			}		
		}
		return true;
	}
	
	protected abstract boolean checkProfile();
	
	protected boolean checkEmail () {
		if (this.email == null || !Validator.isEmail(this.email)) return false;
		else return true;
	}

	
}
