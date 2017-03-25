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
	
	private String addresscity;

	private String addresscountry;

	private String addresspostal;

	private String addressstreet;

	private String email;
	
	private String homecontactnumber;
	
	private String mobilecontactnumber;

	@Lob
	private String drugallergies;

	private String emergencynumber;

	private String emergencyperson;

	private String emergencyrelationship;

	private String firstname;

	@Lob
	private String foodallergies;

	@JsonIgnore
	private Byte isactive;

	private String lastname;

	@Lob
	private String medicalconditions;
	
	private String fullname;

	private Byte profilecompletenessscore;

	private String profileimageurl;

	@Transient
	private String authtoken;
	
	@Transient
	private String role;
	
	@Transient
	private String subscriptiontype;
	
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

	public String getAddresscity() {
		return this.addresscity;
	}

	public void setAddresscity(String addresscity) {
		this.addresscity = addresscity;
	}

	public String getAddresscountry() {
		return this.addresscountry;
	}

	public void setAddresscountry(String addresscountry) {
		this.addresscountry = addresscountry;
	}

	public String getAddresspostal() {
		return this.addresspostal;
	}

	public void setAddresspostal(String addresspostal) {
		this.addresspostal = addresspostal;
	}

	public String getAddressstreet() {
		return this.addressstreet;
	}

	public void setAddressstreet(String addressstreet) {
		this.addressstreet = addressstreet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomecontactnumber() {
		return this.homecontactnumber;
	}

	public void setHomecontactnumber(String contactnumber) {
		this.homecontactnumber = contactnumber;
	}
	
	public String getMobilecontactnumber() {
		return mobilecontactnumber;
	}

	public void setMobilecontactnumber(String mobilecontactnumber) {
		this.mobilecontactnumber = mobilecontactnumber;
	}

	public String getDrugallergies() {
		return this.drugallergies;
	}

	public void setDrugallergies(String drugallergies) {
		this.drugallergies = drugallergies;
	}

	public String getEmergencynumber() {
		return this.emergencynumber;
	}

	public void setEmergencynumber(String emergencynumber) {
		this.emergencynumber = emergencynumber;
	}

	public String getEmergencyperson() {
		return this.emergencyperson;
	}

	public void setEmergencyperson(String emergencyperson) {
		this.emergencyperson = emergencyperson;
	}

	public String getEmergencyrelationship() {
		return this.emergencyrelationship;
	}

	public void setEmergencyrelationship(String emergencyrelationship) {
		this.emergencyrelationship = emergencyrelationship;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFoodallergies() {
		return this.foodallergies;
	}

	public void setFoodallergies(String foodallergies) {
		this.foodallergies = foodallergies;
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

	public String getMedicalconditions() {
		return this.medicalconditions;
	}

	public void setMedicalconditions(String medicalconditions) {
		this.medicalconditions = medicalconditions;
	}

	public Byte getProfilecompletenessscore() {
		return this.profilecompletenessscore;
	}

	public void setProfilecompletenessscore(Byte profilecompletenessscore) {
		this.profilecompletenessscore = profilecompletenessscore;
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

	public String getAuthtoken() {
		return authtoken;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}
	
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

	@Override
	public HashMap<String, String> checkEntries() {
		// TODO Auto-generated method stub
		return null;
	}

}