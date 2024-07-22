package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Employee;
import com.distna.domain.employee.UserType;
import com.distna.domain.leaves.LeaveApplication;

public interface EmployeeDAO {
	public List<Employee> getEmployeeList();
	public List<Employee> getEmployeeList(int id);
 	public List<Employee> getEmployeeListExcludingCurrentId(int employeeId);
 	
	public int saveEmployeeAndGetId(Employee employee);
	public void saveOrUpdateEmployeeAndGetId(Employee employee);
	
	public List<Employee> getEmployeeList(String username);
	
	public void saveUser(Employee employees);
	public boolean checkIfUserExist(String username,String password);
	
	public List<Employee> getEmployeeListById(int employee_id);
	
	public void deleteEmployee(int id);
	public Employee getEmployeeById(int id);
	public void updateUser(Employee employee);
	public List<Employee> getEmployeeListByEmployeeNo(int EmployeeNo);
	public void saveCalTimes();
	public int checkUserTypeById(int id);
	public List<Employee> getEmployeeListByDepartment(int departmentId);
	public List<UserType> getUserType();
	public int checkUserType(String username,String password);
	public Employee getEmployeeByUserName(String username,String encryptedPassword);
	public List<Employee> getEmployeesWithBirthdays();
	public int getDepartmentIdByEmployeeId(int employeeId);
	public List<Employee> getEmployeeListBySupervisor(int supervisor) ;
	public Employee getEmployeeByID(int id);
}
