package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name="permission")
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Long accountid;
	
	private Long subscriptionid;
	
	@Column(insertable=false, updatable=false)
	@JsonIgnore
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createddt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expireddt;

	private byte isactive;

	private String permissionstring;

	private Long resourceid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdt;

	public Permission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public Long getSubscriptionid() {
		return subscriptionid;
	}

	public void setSubscriptionid(Long subscriptionid) {
		this.subscriptionid = subscriptionid;
	}

	public Timestamp getCreateddt() {
		return this.createddt;
	}

	public void setCreateddt(Timestamp createddt) {
		this.createddt = createddt;
	}

	public Date getExpireddt() {
		return this.expireddt;
	}

	public void setExpireddt(Date expireddt) {
		this.expireddt = expireddt;
	}

	public byte getIsactive() {
		return this.isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public String getPermissionstring() {
		return this.permissionstring;
	}

	public void setPermissionstring(String permissionstring) {
		this.permissionstring = permissionstring;
	}

	public Long getResourceid() {
		return this.resourceid;
	}

	public void setResourceid(Long resourceid) {
		this.resourceid = resourceid;
	}

	public Date getStartdt() {
		return this.startdt;
	}

	public void setStartdt(Date startdt) {
		this.startdt = startdt;
	}

}