package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name="person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable, BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
	private Long id;

	@JsonIgnore
	private Long accountid;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateofbirth;
	
	private String email;
	
	private String firstname;

	@JsonIgnore
	private Byte isactive;

	private String lastname;

	private String fullname;

	private String profileimageurl;

	public Person() {
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

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getProfileimageurl() {
		return this.profileimageurl;
	}

	public void setProfileimageurl(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	@Override
	public byte getIsactive() {
		return this.isactive;
	}
	
	@Override
	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	@Override
	public HashMap<String, String> checkEntries() {
		// TODO Auto-generated method stub
		return null;
	}

}