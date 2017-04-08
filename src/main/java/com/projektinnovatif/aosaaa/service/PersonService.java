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
	
		personDao.update(personInDb);
		return true;
	}
	
	@Transactional
	public void changePersonImage(Long personid, String imageFileName) {
		Person person = personDao.getById(personid.longValue());
		person.setProfileimageurl(imageFileName);
		personDao.update(person);
	}
	
}