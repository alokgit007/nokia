package com.example.emitter.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{

	  public EmployeeNotFoundException(String exception) {
		    super(exception);
		  }
}
