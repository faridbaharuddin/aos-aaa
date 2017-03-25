package com.projektinnovatif.aosaaa.dto;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
	
	public static ResponseEntity<Response> OK (Object object) {
		return ResponseEntity.ok(new SuccessResponse(object));
	}
	
	public static ResponseEntity<Response> UNAUTHORIZED () {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FailureResponse ("system", "You do not have access to the requested resource."));
	}
	
	public static ResponseEntity<Response> BAD_REQUEST (String title, String description) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailureResponse (title, description));
	}
	
	public static ResponseEntity<Response> BAD_REQUEST (HashMap<String, String> responseMessages) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailureResponse (responseMessages));
	}
	
	public static ResponseEntity<Response> SERVICE_UNAVAILABLE() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new FailureResponse ("system", "Service is unavailable at this time. Please try again later."));
	}
	
	public static ResponseEntity<Response> NOT_FOUND() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailureResponse ("system", "Resource not found."));
	}
}
