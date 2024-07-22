package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Education;
import com.distna.domain.employee.Employee;

public interface EducationDAO {
	
	public List<Education> getEducationList();
	public void saveEducation(Education education);
	public Education getEducationByEmployeeId(int employee_id);
	public void deleteEducation(int id);
	public List<Employee> getEducationById(int id);
	public List<Education> getEducationListByEmployeeId(int employee_id);
	


}
