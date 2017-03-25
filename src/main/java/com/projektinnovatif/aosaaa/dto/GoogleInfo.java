package com.projektinnovatif.aosaaa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfo extends OauthInfo {

	private String iss;
	private String iat;
	private String exp;
	private String at_hash;
	private String aud;
	private String sub;
	private String email_verified;
	private String azp;
	private String locale;
	private String alg;
	private String kid;
	
	public GoogleInfo () {
		super();
		this.setOauthType("google");
	}
	
	public String getIss() {
		return iss;
	}
	
	public void setIss(String iss) {
		this.iss = iss;
	}
	
	public String getIat() {
		return iat;
	}
	
	public void setIat(String iat) {
		this.iat = iat;
	}
	
	public String getExp() {
		return exp;
	}
	
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	public String getAt_hash() {
		return at_hash;
	}
	
	public void setAt_hash(String at_hash) {
		this.at_hash = at_hash;
	}
	
	public String getAud() {
		return aud;
	}
	
	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
		this.setId(sub);
	}

	public String getEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(String email_verified) {
		this.email_verified = email_verified;
	}

	public String getAzp() {
		return azp;
	}

	public void setAzp(String azp) {
		this.azp = azp;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}
	
	@Override
	protected boolean checkProfile () {
		if (this.getGiven_name() == null
				|| this.getFamily_name () == null
				|| this.getName() == null
				|| this.getPicture() == null) return false;
		else return true;
	}

}
