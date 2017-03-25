package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@Table(name="subscription")
@NamedQuery(name="Subscription.findAll", query="SELECT s FROM Subscription s")
public class Subscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long accountid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddt;

	@JsonIgnore
	private byte isactive;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdt;

	private String subscriptiontype;

	public Subscription() {
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

	public Date getEnddt() {
		return this.enddt;
	}

	public void setEnddt(Date enddt) {
		this.enddt = enddt;
	}

	public byte getIsactive() {
		return this.isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public Date getStartdt() {
		return this.startdt;
	}

	public void setStartdt(Date startdt) {
		this.startdt = startdt;
	}

	public String getSubscriptiontype() {
		return this.subscriptiontype;
	}

	public void setSubscriptiontype(String subscriptiontype) {
		this.subscriptiontype = subscriptiontype;
	}

}