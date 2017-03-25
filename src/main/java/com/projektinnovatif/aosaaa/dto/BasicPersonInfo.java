package com.projektinnovatif.aosaaa.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicPersonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fullname;
	private String profileimageurl;
	private String role;
	private String rsvp;
	private String rsvpremarks;
	
	public BasicPersonInfo (Long id, String fullname, String profileimageurl, String role) {
		this.id = id;
		this.fullname = fullname;
		this.profileimageurl = profileimageurl;
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFullname() {
		return fullname;	
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getProfileimageurl() {
		return profileimageurl;
	}
	
	public void setProfileimageurl(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRsvp() {
		return rsvp;
	}

	public void setRsvp(String rsvp) {
		this.rsvp = rsvp;
	}

	public String getRsvpremarks() {
		return rsvpremarks;
	}

	public void setRsvpremarks(String rsvpremarks) {
		this.rsvpremarks = rsvpremarks;
	}
	
}
