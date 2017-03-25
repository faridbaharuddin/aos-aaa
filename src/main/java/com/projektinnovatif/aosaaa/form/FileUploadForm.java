package com.projektinnovatif.aosaaa.form;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements IForm {

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public HashMap<String, String> checkFormEntries() {
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		if (file == null) {
			errorMessages.put("file", "A valid file is required.");
		}
		return errorMessages;
	}
	
	
	
}