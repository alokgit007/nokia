package model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import com.sun.istack.NotNull;

@Entity
@Table(name = "employee_alert")
public class EmployeeAlert implements Serializable{
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotNull
	 @Column(name = "employee_name", nullable = false)
	 private String employeeName;

	 @NotNull
	 @Column(name = "employee_status", nullable = false)
	 private String employeeStatus;

	 @NotNull
	 @Column(name = "timestamp", nullable = false)
	 private Instant timestamp;
	
	 public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}	

}
