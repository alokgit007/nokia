package model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAlertRepository extends JpaRepository<EmployeeAlert, Long>{

}
