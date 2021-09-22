package com.example.emmiter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmitterRepository extends CrudRepository<Employee, String> {


}
