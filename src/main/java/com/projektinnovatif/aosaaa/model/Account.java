package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashMap;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements BaseModel, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String accounttype;
	
	private String accounttier;
	
	private String accountrole;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activateddt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activatehashsentdt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordresethashsentdt;

	private String activationhash;

	private String passwordresethash;
	
	private String currentauthtoken;

	private String hashsalt;

	private byte isactivated;
	
	private byte ispasswordresetrequested;

	public Date getPasswordresethashsentdt() {
		return passwordresethashsentdt;
	}

	public void setPasswordresethashsentdt(Date passwordresethashsentdt) {
		this.passwordresethashsentdt = passwordresethashsentdt;
	}

	public String getPasswordresethash() {
		return passwordresethash;
	}

	public void setPasswordresethash(String passwordresethash) {
		this.passwordresethash = passwordresethash;
	}

	public byte getIspasswordresetrequested() {
		return ispasswordresetrequested;
	}

	public void setIspasswordresetrequested(byte ispasswordresetrequested) {
		this.ispasswordresetrequested = ispasswordresetrequested;
	}

	private byte isactive;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastactiondt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastlogindt;

	private String mobilenumber;

	private String passwordhash;

	private int pbdkfiterations;

	private String username;

	@Column(insertable=false, updatable=false)
	@JsonIgnore
	private Timestamp lastupdateddt;
	
	@Column(insertable=false, updatable=false)
	@JsonIgnore
	private Timestamp createddt;
	
	public Account() {
	}

	public Account(String accounttype, Date activatehashsentdt, String activationhash, String hashsalt, byte isactive,
			String mobilenumber, String passwordhash, int pbdkfiterations, String username) {
		super();
		this.accounttype = accounttype;
		this.activatehashsentdt = activatehashsentdt;
		this.activationhash = activationhash;
		this.hashsalt = hashsalt;
		this.isactive = isactive;
		this.mobilenumber = mobilenumber;
		this.passwordhash = passwordhash;
		this.pbdkfiterations = pbdkfiterations;
		this.username = username;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccounttype() {
		return this.accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	
	public String getAccounttier() {
		return accounttier;
	}

	public void setAccounttier(String accounttier) {
		this.accounttier = accounttier;
	}
	
	public String getAccountrole() {
		return accountrole;
	}

	public void setAccountrole(String accountrole) {
		this.accountrole = accountrole;
	}

	public Date getActivateddt() {
		return this.activateddt;
	}

	public void setActivateddt(Date activateddt) {
		this.activateddt = activateddt;
	}

	public Date getActivatehashsentdt() {
		return this.activatehashsentdt;
	}

	public void setActivatehashsentdt(Date activatehashsentdt) {
		this.activatehashsentdt = activatehashsentdt;
	}

	public String getActivationhash() {
		return this.activationhash;
	}

	public void setActivationhash(String activationhash) {
		this.activationhash = activationhash;
	}

	public String getCurrentauthtoken() {
		return this.currentauthtoken;
	}

	public void setCurrentauthtoken(String currentauthtoken) {
		this.currentauthtoken = currentauthtoken;
	}

	public String getHashsalt() {
		return this.hashsalt;
	}

	public void setHashsalt(String hashsalt) {
		this.hashsalt = hashsalt;
	}

	public byte getIsactivated() {
		return this.isactivated;
	}

	public void setIsactivated(byte isactivated) {
		this.isactivated = isactivated;
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

	public Date getLastlogindt() {
		return this.lastlogindt;
	}

	public void setLastlogindt(Date lastlogindt) {
		this.lastlogindt = lastlogindt;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getPasswordhash() {
		return this.passwordhash;
	}

	public void setPasswordhash(String passwordhash) {
		this.passwordhash = passwordhash;
	}

	public int getPbdkfiterations() {
		return this.pbdkfiterations;
	}

	public void setPbdkfiterations(int pbdkfiterations) {
		this.pbdkfiterations = pbdkfiterations;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getLastupdateddt() {
		return lastupdateddt;
	}

	public void setLastupdateddt(Timestamp lastupdateddt) {
		this.lastupdateddt = lastupdateddt;
	}

	public Timestamp getCreateddt() {
		return createddt;
	}

	public void setCreateddt(Timestamp createddt) {
		this.createddt = createddt;
	}

	@Override
	public HashMap<String, String> checkEntries() {
		// TODO Auto-generated method stub
		return null;
	}
}