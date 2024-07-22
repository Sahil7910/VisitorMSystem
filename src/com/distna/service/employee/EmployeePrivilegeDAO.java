package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.EmployeePrivilege;

public interface EmployeePrivilegeDAO
{
	public List<EmployeePrivilege> getEmployeePrivilegeList();
	public void saveEmployeePrivilege(EmployeePrivilege employeePrivilege);
	public void updateEmployeePrivilege(EmployeePrivilege employeePrivilege);
	public List<EmployeePrivilege> getEmployeePrivilegeListByEmployeeId(int employeeId);
	public List<EmployeePrivilege> getEmployeePrivilegeListById(int id);
	public void deleteEmployeePrivilegeListById(int id);
	
	
}
