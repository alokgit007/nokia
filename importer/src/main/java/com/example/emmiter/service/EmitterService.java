package com.example.emmiter.service;


import org.springframework.stereotype.Service;

import com.example.emmiter.repository.Employee;


@Service
public interface EmitterService {



	public Employee saveData(Employee employee) ;
	

	public Employee updateData(Employee employee);
		
	

}
