package com.example.emmiter.controller;


import org.apache.tomcat.util.http.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emmiter.repository.Employee;
import com.example.emmiter.service.AlertService;
import com.example.emmiter.service.EmitterService;




@RestController
public class EmitterController {

	EmitterService emitterService;
	
    AlertService alertService;
    
    private String applicationName;

	
	private final Logger log = LoggerFactory.getLogger(AlertService.class);
	private static final String ENTITY_NAME = "employee";

	
	@Autowired
	public EmitterController(EmitterService emitterService,AlertService alertService) {
		super();
		this.emitterService = emitterService;
		this.alertService = alertService;
	}
	
	@PostMapping("/import")
	public  ResponseEntity<Employee> saveData(@RequestBody Employee employee){
		Employee result = emitterService.saveData(employee);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
	}

	@PutMapping("/modify")
	public  ResponseEntity<Employee>  updateData(@RequestBody Employee employee){
		Employee result=emitterService.updateData(employee);
		log.debug("SEND store alert for Store: {}", employee);
		alertService.alertStoreStatus(result);
		
		return ResponseEntity.ok()
				            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId()))
				            .body(result);
	}

}
