package com.distna.service.employee;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.distna.domain.employee.EmployeePrivilege;
import com.distna.domain.employee.EmployeeSkills;

public class EmployeePrivilegeValidator implements Validator
{

	@Override
	public boolean supports(Class<?> validate) {
		return EmployeePrivilege.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		EmployeePrivilege employeePrivilege=(EmployeePrivilege)target;
		
		
	}

}
