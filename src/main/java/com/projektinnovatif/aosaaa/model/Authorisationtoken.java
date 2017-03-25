package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashMap;


/**
 * The persistent class for the authorisationtokens database table.
 * 
 */
@Entity
@Table(name="authorisationtoken")
@NamedQuery(name="Authorisationtoken.findAll", query="SELECT a FROM Authorisationtoken a")
public class Authorisationtoken implements Serializable, BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String token;
	
	private Long accountid;

	@Column(insertable=false, updatable=false)
	@JsonIgnore
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createddt;

	private String devicedetails;

	private byte isactive;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastactiondt;

	private String sourceaddress;

	public Authorisationtoken() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getAccountid() {
		return this.accountid;
	}
	
	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}
	
	public Timestamp getCreateddt() {
		return this.createddt;
	}

	public void setCreateddt(Timestamp createddt) {
		this.createddt = createddt;
	}

	public String getDevicedetails() {
		return this.devicedetails;
	}

	public void setDevicedetails(String devicedetails) {
		this.devicedetails = devicedetails;
	}

	public byte getIsactive() {
		return this.isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public Date getLastactiondt() {
		return this.lastactiondt;
	}

	public void setLastactiondt(Date lastactiondt) {
		this.lastactiondt = lastactiondt;
	}

	public String getSourceaddress() {
		return this.sourceaddress;
	}

	public void setSourceaddress(String sourceaddress) {
		this.sourceaddress = sourceaddress;
	}

	@Override
	public HashMap<String, String> checkEntries() {
		// System-generated item. 
		// No checks required.
		return null;
	}
	
}