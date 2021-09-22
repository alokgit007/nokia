package com.example.emmiter.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.emmiter.repository.Employee;
import com.example.emmiter.service.AlertService;
import com.example.emmiter.service.EmitterService;

@RestController
public class EmitterController {

	EmitterService emitterService;
	
    AlertService alertService;

	
	private final Logger log = LoggerFactory.getLogger(AlertService.class);


	
	@Autowired
	public EmitterController(EmitterService emitterService,AlertService alertService) {
		super();
		this.emitterService = emitterService;
		this.alertService = alertService;
	}
	
	@PostMapping("/import")
	public  ResponseEntity saveData(@RequestBody Employee employee){
		 emitterService.saveData(employee);
		 return new ResponseEntity<>("employee data saved successfully",HttpStatus.CREATED);
	}

	@PutMapping("/modify")
	public  ResponseEntity  updateData(@RequestBody Employee employee){
		Employee result=emitterService.updateData(employee);
		log.debug("SEND store alert for Store: {}", employee);
		alertService.alertStoreStatus(result);
		
		return new ResponseEntity<>("employee data updated successfully",HttpStatus.OK);
	}

}
