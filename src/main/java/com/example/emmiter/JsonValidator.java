package com.example.emmiter;

import java.io.InputStream;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class JsonValidator {

	private static InputStream inputStreamFromClasspath(String path) {
	    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	 
	public static void main(String[] args) throws Exception {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
	 
	    try (
	            InputStream jsonStream = inputStreamFromClasspath("employee.json");
	            InputStream schemaStream = inputStreamFromClasspath("schema.json")
	    ) {
	        JsonNode json = objectMapper.readTree(jsonStream);
	        JsonSchema schema = schemaFactory.getSchema(schemaStream);
	        Set<ValidationMessage> validationResult = schema.validate(json);
	 
	        // print validation errors
	        if (validationResult.isEmpty()) {
	            System.out.println("no validation errors :-)");
	        } else {
	            for(ValidationMessage vm:validationResult) {
	            	System.out.println(vm.getMessage());
	            }
	        }
	    }
	}

}
