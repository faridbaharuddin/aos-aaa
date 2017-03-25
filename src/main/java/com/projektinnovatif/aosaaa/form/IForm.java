package com.projektinnovatif.aosaaa.form;

/**
 * Created: 04 Dec 2016
 * 
 * Forms are Data Transfer Objects that are used to contain the fields
 * within the form. ALl these fields have entries that should be validated 
 * before being processed further.
 * 
 */
import java.util.HashMap;

public interface IForm {

	/**
	 * Checks that all form entries are valid.
	 * @return
	 * A HashMap containing field names with errors as keys and a
	 * description of the error in the value field.
	 */
	public HashMap<String, String> checkFormEntries();

}
