package com.example.emmiter.service.dto;

import org.springframework.stereotype.Component;

import com.example.emmiter.repository.Employee;

@Component
public class EmployeeAlertDTO {
	
	  private String employeeName;
	  private long employeeSalary;
	  
	  public EmployeeAlertDTO(Employee e) {
		  this.employeeName=e.getName();
		  this.employeeSalary=e.getSalary();
	  }
	  

}
