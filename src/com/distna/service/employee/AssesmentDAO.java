package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Assesment;

public interface AssesmentDAO {
	
	
	public void saveEmployeeAssesment(Assesment assesment);
	public List<Assesment> getEmployeeAssesment();
	public List<Assesment> getEmployeeAssesmentById(int id);
	public Assesment getAssesmentByemployeeId(int emp_id);
	public void deleteAssesment(int id);
	

}
