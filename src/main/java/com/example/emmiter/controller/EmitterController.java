package com.example.emmiter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emmiter.repository.Employee;
import com.example.emmiter.service.EmitterService;




@RestController
public class EmitterController {

	EmitterService emitterService;
	
	@Autowired
	public EmitterController(EmitterService emitterService) {
		super();
		this.emitterService = emitterService;
	}
	
	@PostMapping("/import")
	public  Employee saveData(@RequestBody Employee employee){
		return employee=emitterService.saveData(employee);
	}

	@PutMapping("/modify")
	public  Employee  updateData(@RequestBody Employee employee){
		return employee=emitterService.updateData(employee);
	}

}
