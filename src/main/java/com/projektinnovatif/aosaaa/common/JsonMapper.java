package com.projektinnovatif.aosaaa.common;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

	public static JsonNode mapJson (String sourceString) {
		ObjectMapper mapper = new ObjectMapper();	
		try {
			return mapper.readTree(sourceString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
