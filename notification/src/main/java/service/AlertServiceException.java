package service;

import org.springframework.stereotype.Component;

@Component
public class AlertServiceException extends RuntimeException {
	
	public AlertServiceException(Throwable e) {
		super(e);
	}

}
