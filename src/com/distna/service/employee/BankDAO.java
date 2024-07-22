package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Employee;
import com.distna.domain.employee.EmployeeBank;

public interface BankDAO {

	
public int saveAndGetId(EmployeeBank employeeBank);
	
	public void saveEmployeeBank(EmployeeBank employeeBank);
	
	public List<EmployeeBank> getEmployeeBankList();
	
	public void deleteEmployeeBank(int id);
	
	public EmployeeBank getEmployeeBank(int id);
	
	public void updateEmployeeBank(EmployeeBank employeeBank);

	EmployeeBank getEmployeeBankbyNo(Employee employee);

	
}
