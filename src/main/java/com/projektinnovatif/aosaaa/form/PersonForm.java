package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class PersonForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String fullname;
	private String firstname;
	private String lastname;
	private String dateofbirth;
	private String addressstreet;
	private String addresscity;
	private String addresscountry;
	private String addresspostal;
	private String email;
	private String homecontactnumber;
	private String mobilecontactnumber;
	private String foodallergies;
	private String drugallergies;
	private String medicalconditions;
	private String emergencynumber;
	private String emergencyperson;
	private String emergencyrelationship;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getAddressstreet() {
		return addressstreet;
	}
	public void setAddressstreet(String addressstreet) {
		this.addressstreet = addressstreet;
	}
	public String getAddresscity() {
		return addresscity;
	}
	public void setAddresscity(String addresscity) {
		this.addresscity = addresscity;
	}
	public String getAddresscountry() {
		return addresscountry;
	}
	public void setAddresscountry(String addresscountry) {
		this.addresscountry = addresscountry;
	}
	public String getAddresspostal() {
		return addresspostal;
	}
	public void setAddresspostal(String addresspostal) {
		this.addresspostal = addresspostal;
	}
	public String getHomecontactnumber() {
		return homecontactnumber;
	}
	public void setHomecontactnumber(String homecontactnumber) {
		this.homecontactnumber = homecontactnumber;
	}
	public String getMobilecontactnumber() {
		return mobilecontactnumber;
	}
	public void setMobilecontactnumber(String mobilecontactnumber) {
		this.mobilecontactnumber = mobilecontactnumber;
	}
	public String getFoodallergies() {
		return foodallergies;
	}
	public void setFoodallergies(String foodallergies) {
		this.foodallergies = foodallergies;
	}
	public String getDrugallergies() {
		return drugallergies;
	}
	public void setDrugallergies(String drugallergies) {
		this.drugallergies = drugallergies;
	}
	public String getMedicalconditions() {
		return medicalconditions;
	}
	public void setMedicalconditions(String medicalconditions) {
		this.medicalconditions = medicalconditions;
	}
	public String getEmergencynumber() {
		return emergencynumber;
	}
	public void setEmergencynumber(String emergencynumber) {
		this.emergencynumber = emergencynumber;
	}
	public String getEmergencyperson() {
		return emergencyperson;
	}
	public void setEmergencyperson(String emergencyperson) {
		this.emergencyperson = emergencyperson;
	}
	public String getEmergencyrelationship() {
		return emergencyrelationship;
	}
	public void setEmergencyrelationship(String emergencyrelationship) {
		this.emergencyrelationship = emergencyrelationship;
	}

	
	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (firstname == null || firstname.length() == 0) {
			errorMessages.put("firstname", "First name is required.");
		}
		if (lastname == null || lastname.length() == 0) {
			errorMessages.put("lastname", "Last name is required.");
		}
		if (email == null || !Validator.isEmail(email)) {
			errorMessages.put("email", "Valid email is required.");
		}
		return errorMessages;
	}
	

	
	
}
