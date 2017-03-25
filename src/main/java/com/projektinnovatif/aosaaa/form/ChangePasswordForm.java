package com.projektinnovatif.aosaaa.form;

import java.io.Serializable;
import java.util.HashMap;

public class ChangePasswordForm implements Serializable, IForm {
	private static final long serialVersionUID = 1L;

	private String oldpassword;
	private String newpassword;
	private String newpasswordrepeat;
	
	public String getOldpassword() {
		return oldpassword;
	}
	
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	
	public String getNewpassword() {
		return newpassword;
	}
	
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	public String getNewpasswordrepeat() {
		return newpasswordrepeat;
	}
	
	public void setNewpasswordrepeat(String newpasswordrepeat) {
		this.newpasswordrepeat = newpasswordrepeat;
	}
	
	/**
	 * Check the validity of the entries in the ChangePasswordForm
	 * 1. Is old password blank?
	 * 2. Is new password blank?
	 * 3. If not, do the passwords match?
	 * 4. If so, are the passwords strong?
	 * 5. Are the old and new passwords the same?
	 * @return A HashMap of errormessages
	 */
	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (oldpassword == null || oldpassword.length() == 0) {
			errorMessages.put("oldpassword", "Old password is required.");
		}
		if (newpassword == null || newpassword.length() == 0) {
			errorMessages.put("newpassword", "New password is required.");
		} else if (newpasswordrepeat == null || newpassword.compareTo(newpasswordrepeat) != 0) {
			errorMessages.put("newpasswordrepeat", "New password entries do not match.");
		} else if (!Validator.isStrongPassword(newpassword)) {
			errorMessages.put("password",
					"Password should be at least eight characters in length and contain at least three of the following: uppercase letter (A-Z), lowercase letter (a-z), numbers (0-9), special characters (@, $, !, %, *, #, ?, &)");
		} else if (oldpassword.compareTo(newpassword) == 0) {
			errorMessages.put("newpassword", 
			"Please enter a new password that is different from the current one.");
		}
		return errorMessages;
	}

}
