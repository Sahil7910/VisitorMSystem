package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.EmployeeExperiences;

public interface EmployeeExperiencesDAO {
	
	public int saveAndGetId(EmployeeExperiences employeeExperiences);
	
	public void saveExperience(EmployeeExperiences employeeExperiences);
	
	public List<EmployeeExperiences> getExperienceList();
	
	public void deleteExperience(int id);
	
	public EmployeeExperiences getExterience(int id);

}
