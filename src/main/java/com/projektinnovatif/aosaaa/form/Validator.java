package com.projektinnovatif.aosaaa.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Validator {

	// Email
	private static final String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
	
	// Name
	private static final String nameRegex = "^[\\p{L}\\s'\\.\\-]+$";
	
	// Password strength
	private static final String passwordStrengthRegexCombo1 = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%^&+=])(?=\\S+$).{8,}$";
	private static final String passwordStrengthRegexCombo2 = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$!%^&+=])(?=\\S+$).{8,}$";
	private static final String passwordStrengthRegexCombo3 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$!%^&+=])(?=\\S+$).{8,}$";
	private static final String passwordStrengthRegexCombo4 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
	private static final String passwordStrengthRegexCombo5 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%^&+=])(?=\\S+$).{8,}$";

	// UUID
	private static final String uuidRegex = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
	
	// Google ID Token
	private static final String googleIdTokenRegex = "^[a-zA-Z0-9\\.\\-_]+$";
	
	// Facebook Code
	private static final String facebookCodeRegex = "^[a-zA-Z0-9\\-_#=]+$";
		
	// Currency Codes
	// TODO: Add entire list of currencies
	private static final Set<String> acceptedCurrencies = new HashSet<String>(Arrays.asList("SGD","USD","EUR","MYR"));
	
	/**
	 * Checks if a submitted value is a valid name.
	 * @param name
	 * @return
	 * true if valid name
	 * false if not valid name
	 */
	public static boolean isName (String name) {
		return name.matches(nameRegex);
	}
	
	
	/**
	 * Checks if a submitted value is a valid email address.
	 * @param email
	 * @return
	 * true if valid email
	 * false if not valid email
	 */
	public static boolean isEmail (String email) {
		return email.matches(emailRegex);
	}
	
	
	/**
	 * Checks the strength of a password by looking for a mix of
	 * - Capital letters
	 * - Small letters
	 * - Numbers
	 * - Special characters
	 * @param password
	 * @return
	 * false if at least three of the four types of characters are not included
	 * true otherwise
	 */
	public static boolean isStrongPassword (String password) {
		if (!(password.matches(passwordStrengthRegexCombo1)
			|| password.matches(passwordStrengthRegexCombo2)
			|| password.matches(passwordStrengthRegexCombo3)
			|| password.matches(passwordStrengthRegexCombo4)
			|| password.matches(passwordStrengthRegexCombo5))) return false;
		else return true;
	}
	
	
	/**
	 * Checks if a submitted value matches the required hash pattern
	 * @param submittedHash
	 * @param noOfCharacters
	 * @return
	 * true if a valid hash of stated length
	 * false if not a valid hash of stated length
	 */
	public static boolean checkHashFormat (String submittedHash, int noOfCharacters) {
		String hashRegex = "^[0-9a-f]{" + noOfCharacters + "}$";
		if (submittedHash.matches(hashRegex)) return true;
		else return false;
	}
	
	
	/**
	 * Checks if a submitted value is a valid UUID
	 * @param uuid
	 * @return
	 * true if valid UUID
	 * false if not valid UUID
	 */
	public static boolean isUuid (String uuid) {
		return uuid.matches(uuidRegex);
	}
	
	
	/**
	 * Checks if a submitted value is a valid Google ID Token
	 * @param idtoken
	 * @return
	 * true if valid Google ID Token
	 * false if not valid Google ID Token
	 */
	public static boolean isGoogleIdToken (String idtoken) {
		return idtoken.matches(googleIdTokenRegex);
	}
	
	
	/**
	 * Checks if a submitted value is a valid Facebook Code
	 * @param code
	 * @return
	 * true if valid Facebook Code
	 * false if not valid Facebook Code
	 */
	public static boolean isFacebookCode (String code) {
		return code.matches(facebookCodeRegex);
	}
	
	
	/**
	 * Checks if a submitted value is null or empty
	 * @param entry
	 * @return
	 */
	public static boolean isNullOrEmpty (String entry) {
		return entry == null || entry.isEmpty();
	}
	
	
	/** 
	 * Checks if a submitted String represents a valid date
	 * @param dateIn
	 * @return
	 */
	public static boolean isValidDate (String dateIn) {
		
		if(dateIn == null){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateIn);
		sdf.setLenient(false);

		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateIn);
			System.out.println(date);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}
	
	
	/**
	 * Checks if a submitted String represents a valid accepted currency
	 * @param currency
	 * @return
	 */
	public static boolean isValidCurrency(String currency) {
		return acceptedCurrencies.contains(currency);
	}
	
}
