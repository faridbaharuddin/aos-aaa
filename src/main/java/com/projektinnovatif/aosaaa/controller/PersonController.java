package com.projektinnovatif.aosaaa.controller;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.projektinnovatif.aosaaa.dto.Response;
import com.projektinnovatif.aosaaa.dto.ResponseBuilder;
import com.projektinnovatif.aosaaa.form.PersonForm;
import com.projektinnovatif.aosaaa.model.Account;
import com.projektinnovatif.aosaaa.model.Person;
import com.projektinnovatif.aosaaa.service.AccountService;
import com.projektinnovatif.aosaaa.service.AuthNAuthService;
import com.projektinnovatif.aosaaa.service.FileUploadService;
import com.projektinnovatif.aosaaa.service.PersonService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	@Autowired
	AuthNAuthService authService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	/*
	// NOTFORPROD: FOR DEBUGGING ONLY, DISABLE FOR PRODUCTION
	@RequestMapping(value = "/persons", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Response> getPersons() {
		return ResponseBuilder.OK(personService.getAllPersons());
	}
	*/
	
	
	/*
	// NOTFORPROD: FOR DEBUGGING ONLY, DISABLE FOR PRODUCTION
	@RequestMapping(value = "/person/{personid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Response> getPersonById(@PathVariable long personid) {
		return ResponseBuilder.OK(personService.getPerson(personid));
	}
	*/
	
	
	/** 
	 * Get a Trainee's profile
	 * @param personid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/person/{personid}", method = RequestMethod.GET)
	public ResponseEntity<Response> getPersonDetails(@PathVariable long personid, HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (authToken != null && 
			authService.checkIfAuthorised(authToken, "person:view-trainee::" + personid)) {
			Person person = personService.getPerson(authService.getPersonIdFromAuthToken(authToken));
			if (person != null) {
				return ResponseBuilder.OK(person);
			} else {
				return ResponseBuilder.NOT_FOUND();
			}
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/** 
	 * Edit a Trainee's profile. POST is used instead of PUT to minimise the 
	 * types of HTTP methods used. Some organisations do not allow PUT and 
	 * DELETE requests to be called from within their premises, only allowing
	 * POST or GET methods to be called.
	 * @param personid
	 * @param personform
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/person/{personid}", method = RequestMethod.POST)
	public ResponseEntity<Response> editPerson(@PathVariable long personid, @RequestBody PersonForm personform, HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		HashMap<String, String> responseMessages = personform.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		}
		if (authToken != null && 
			authService.checkIfAuthorised(authToken, "person:edit-trainee::" + personid)) {
			if (personService.editPerson(new Long(personid), personform)) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.SERVICE_UNAVAILABLE();
			}
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/** 
	 * Retrieve one's own profile.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ResponseEntity<Response> getMyDetails(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (authToken != null && 
			authService.checkIfAuthorised(authToken, "person:view-self")) {
			Person person = personService.getPerson(authService.getPersonIdFromAuthToken(authToken));
			Account account = accountService.getAccount(person.getAccountid());
			person.setAuthtoken(authToken);
			person.setRole(account.getAccountrole());
			person.setSubscriptiontype(account.getAccounttier());
			return ResponseBuilder.OK(person);
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/**
	 * Edit one's own profile. POST is used instead of PUT to minimise the 
	 * types of HTTP methods used. Some organisations do not allow PUT and 
	 * DELETE requests to be called from within their premises, only allowing 
	 * POST or GET methods to be called.
	 * @param personform
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/me", method = RequestMethod.POST)
	public ResponseEntity<Response> editPerson(@RequestBody PersonForm personform, HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		HashMap<String, String> responseMessages = personform.checkFormEntries();
		if (responseMessages.size() > 0) {
			return ResponseBuilder.BAD_REQUEST(responseMessages);
		}
		if (authToken != null && 
			authService.checkIfAuthorised(authToken, "person:edit-self")) {
			Long personid = authService.getPersonIdFromAuthToken(authToken);
			if (personService.editPerson(personid, personform)) {
				return ResponseBuilder.OK(null);
			} else {
				return ResponseBuilder.SERVICE_UNAVAILABLE();
			}
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/**
	 * Change a Trainee's profile image.
	 * @param personid
	 * @param multipartFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/person/{personid}/uploadimage", method = RequestMethod.POST)
	public ResponseEntity<Response> uploadImage(@PathVariable long personid, @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (authToken != null && 
				authService.checkIfAuthorised(authToken, "person:edit-trainee::" + personid)) {
			String imageFileName = fileUploadService.uploadImage(multipartFile, "profile", new Long(personid));
			personService.changePersonImage(personid, imageFileName);
			return ResponseBuilder.OK(imageFileName);
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	
	
	/**
	 * Change one's own profile image. POST is used instead of PUT to minimise
	 * the types of HTTP methods used. Some organisations do not allow PUT and 
	 * DELETE requests to be called from within their premises, only allowing 
	 * POST or GET methods to be called.
	 * @param multipartFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/me/uploadimage", method = RequestMethod.POST)
	public ResponseEntity<Response> uploadMyImage(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (authToken != null && 
				authService.checkIfAuthorised(authToken, "person:edit-self")) {
			Long personid = authService.getPersonIdFromAuthToken(authToken);
			String imageFileName = fileUploadService.uploadImage(multipartFile, "profile", personid);
			personService.changePersonImage(personid, imageFileName);
			return ResponseBuilder.OK(imageFileName);
		} else {
			return ResponseBuilder.UNAUTHORIZED();
		}
	}
	

}
