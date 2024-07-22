package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Projects;

public interface ProjectsDAO {
	public void saveEmployeeProjects(Projects projects);
	public List<Projects> getEmployeeProjects();
	public Projects getEmployeeProjects(int id);
	public void deleteEmployeeProjects(int id);

}
