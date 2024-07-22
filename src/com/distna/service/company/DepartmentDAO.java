package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Departments;

public interface DepartmentDAO {

	public void saveDepartments(Departments departments);
	public List<Departments> getDepartment();
	public void deleteDepartments(int id);
	public List<Departments> getDepartmentById(int id);
	public List<Departments> getDepartmentWithoutCurrentDept(int id);
	public String getDepartmentNameById(int departmentId);
	
}
