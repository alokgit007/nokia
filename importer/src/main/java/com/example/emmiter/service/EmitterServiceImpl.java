package com.example.emmiter.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.emmiter.repository.EmitterRepository;
import com.example.emmiter.repository.Employee;


@Service
public class EmitterServiceImpl implements EmitterService {


    EmitterRepository emitterRepository;
    
    @Autowired
	public EmitterServiceImpl(EmitterRepository emitterRepository) {
		this.emitterRepository = emitterRepository;
	}

    @Override
	public Employee saveData(Employee employee) {
		return emitterRepository.save(employee);
	}
	
    @Override
	public Employee updateData(Employee employee) {
		return emitterRepository.save(employee);
	}
		
	

}
