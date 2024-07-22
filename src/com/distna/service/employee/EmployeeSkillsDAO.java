package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.EmployeeSkills;

public interface EmployeeSkillsDAO {
	
	
	public int saveAndGetId(EmployeeSkills employeeSkills);
	
	public void saveEmployeeSkills(EmployeeSkills employeeSkills);
	
	public List<EmployeeSkills> getEmployeeSkillsList();
	
	public void deleteEmployeeSkills(int id);
	
	public EmployeeSkills getEmployeeSkills(int id);
	
	public void updateEmployeeSkills(EmployeeSkills employeeSkills);

}
