package com.projektinnovatif.aosaaa.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projektinnovatif.aosaaa.dao.PersonDao;
import com.projektinnovatif.aosaaa.form.PersonForm;
import com.projektinnovatif.aosaaa.form.Validator;
import com.projektinnovatif.aosaaa.model.Person;

@Service("personService")
public class PersonService {

	@Autowired
	PersonDao personDao;

	@Value("${defaultpersonimage}")
	private String defaultPersonImage;
	
	@Transactional
	public List<Person> getAllPersons() {
		return personDao.getAll();
	}

	@Transactional
	public Person getPerson(long id) {
		return personDao.getById(id);
	}
	
	@Transactional
	public Person addPerson (Long accountId, String firstName, String lastName, String email) {
		Person person = new Person();
		person.setAccountid(accountId);
		person.setFirstname(firstName);
		person.setLastname(lastName);
		person.setEmail(email);
		person.setProfileimageurl(defaultPersonImage);
		person.setIsactive((byte)1);
		return personDao.add(person);
	}
	
	@Transactional
	public Person getPersonByAccountId (Long accountid) {
		return personDao.getPersonByAccountID(accountid);
	}
	
	@Transactional
	public Person addPerson (Person person) {
		person.setProfilecompletenessscore(this.calculateCompletenessScore(person));
		person.setIsactive((byte)1);
		return personDao.add(person);
	}
	
	@Transactional
	public boolean editPerson (Long personid, PersonForm person) {
		Person personInDb = personDao.getById(personid.longValue());
		// TODO: Handle email change
		// 1. Change account login?
		// 2. Store as secondary email?
		// 2. Send verification email?
		if (personInDb.getEmail().compareTo(person.getEmail()) != 0) {
			
		}
		
		// Date of Birth conversion
		if (person.getDateofbirth() != null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			try {
				Date dateOfBirth = (Date)formatter.parse(person.getDateofbirth());
				personInDb.setDateofbirth(dateOfBirth);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} else personInDb.setDateofbirth(null);
		
		// Name fields
		personInDb.setFirstname(person.getFirstname());
		personInDb.setLastname(person.getLastname());
		personInDb.setFullname(person.getFirstname() + " " + person.getLastname());
		
		personInDb.setAddressstreet(!Validator.isNullOrEmpty(person.getAddressstreet()) ? person.getAddressstreet() : null);
		personInDb.setAddresscity(!Validator.isNullOrEmpty(person.getAddresscity()) ? person.getAddresscity() : null);
		personInDb.setAddresscountry(!Validator.isNullOrEmpty(person.getAddresscountry()) ? person.getAddresscountry() : null);
		personInDb.setAddresspostal(!Validator.isNullOrEmpty(person.getAddresspostal()) ? person.getAddresspostal() : null);
		personInDb.setHomecontactnumber(!Validator.isNullOrEmpty(person.getHomecontactnumber()) ? person.getHomecontactnumber() : null);
		personInDb.setMobilecontactnumber(!Validator.isNullOrEmpty(person.getMobilecontactnumber()) ? person.getMobilecontactnumber() : null);
		
		// Medical condition fields
		personInDb.setFoodallergies(!Validator.isNullOrEmpty(person.getFoodallergies()) ? person.getFoodallergies() : null);
		personInDb.setDrugallergies(!Validator.isNullOrEmpty(person.getDrugallergies()) ? person.getDrugallergies() : null);
		personInDb.setMedicalconditions(!Validator.isNullOrEmpty(person.getMedicalconditions()) ? person.getMedicalconditions() : null);
		personInDb.setEmergencyperson(!Validator.isNullOrEmpty(person.getEmergencyperson()) ? person.getEmergencyperson() : null);
		personInDb.setEmergencyrelationship(!Validator.isNullOrEmpty(person.getEmergencyrelationship()) ? person.getEmergencyrelationship() : null);
		personInDb.setEmergencynumber(!Validator.isNullOrEmpty(person.getEmergencynumber()) ? person.getEmergencynumber() : null);
		
		// Calculate completeness
		personInDb.setProfilecompletenessscore(this.calculateCompletenessScore(personInDb));
		
		personDao.update(personInDb);
		return true;
	}
	
	private byte calculateCompletenessScore(Person person) {
		return (byte) ((!Validator.isNullOrEmpty(person.getFirstname()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getLastname()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getProfileimageurl()) ? 10 : 0)
						+ (!Validator.isNullOrEmpty(person.getAddressstreet()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getAddresscity()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getAddresscountry()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getAddresspostal()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getHomecontactnumber()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getMobilecontactnumber()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getFoodallergies()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getDrugallergies()) ? 10 : 0)
						+ (!Validator.isNullOrEmpty(person.getMedicalconditions()) ? 10 : 0)
						+ (!Validator.isNullOrEmpty(person.getEmergencyperson()) ? 10 : 0)
						+ (!Validator.isNullOrEmpty(person.getEmergencyrelationship()) ? 5 : 0)
						+ (!Validator.isNullOrEmpty(person.getEmergencynumber()) ? 10 : 0));
	}
	
	@Transactional
	public void changePersonImage(Long personid, String imageFileName) {
		Person person = personDao.getById(personid.longValue());
		person.setProfileimageurl(imageFileName);
		personDao.update(person);
	}
	
}