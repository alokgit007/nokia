package model;

import org.springframework.stereotype.Component;

@Component
public class EmployeeAlertDto {
	

	private String employee_name;
	private String emplyee_satus ;
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmplyee_satus() {
		return emplyee_satus;
	}
	public void setEmplyee_satus(String emplyee_satus) {
		this.emplyee_satus = emplyee_satus;
	}
	
	
	

}
