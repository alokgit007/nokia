package com.example.emmiter.service;

import org.springframework.stereotype.Component;

@Component
public class EmailServiceException extends RuntimeException {

	public EmailServiceException(Throwable e) {
		super(e);
	}
}
