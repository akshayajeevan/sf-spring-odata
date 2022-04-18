package com.akshaya.sf.spring.odata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestService {

	public static void main(String[] args) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String carJson = "[{ \"message\" : \"\\nSELECT Id,Username,Name,Email,ABC FROM USER LIMIT 200\\n                              ^\\nERROR at Row:1:Column:31\\nNo such column 'ABC' on entity 'User'. If you are attempting to use a custom field, be sure to append the '__c' after the custom field name. Please reference your WSDL or the describe call for the appropriate names.\", \"errorCode\" : \"INVALID_FIELD\" }]";
		//String carJson = 
		try {
			JsonNode carJsonNode = objectMapper.readTree(carJson);
			System.out.println(carJsonNode.isArray());
			System.out.println(carJsonNode.isObject());
			System.out.println(carJsonNode.get(0).has("message"));
			System.out.println(carJsonNode.get(0).get("errorCode").asText());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
