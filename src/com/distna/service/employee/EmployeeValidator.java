package com.distna.service.employee;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.Employee;


public class EmployeeValidator implements Validator{
	
	private Pattern emailPattern;
	
	private Matcher emailMatcher;
	
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(Class<?> clazz) {
	
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
	
	
	
	public void validate(Object target, Errors errors,HttpServletRequest request)
	{
		Employee employee=(Employee)target;
		
		String workPhone=employee.getWorkPhone();
		
		String currentPostalCode=employee.getCurrentPostalCode();
		
		String mobile=employee.getMobile();
		
		if((mobile.length()!=10 || checkIfNumber(mobile)) && !mobile.equals(""))
		{
			errors.rejectValue("mobile", "empMobile.required", "Please Enter a Valid Mobile Number");
		}
		
		if((currentPostalCode.length()!=6 || checkIfNumber(currentPostalCode)) && !currentPostalCode.equals(""))
		{
			errors.rejectValue("currentPostalCode", "empCurrentPostalCode.required", "Please Enter a Valid Postal Code");
		}
		
		if(checkIfNumber(workPhone) && !workPhone.equals(""))
		{
			errors.rejectValue("workPhone", "empWorkPhone.required", "Please Enter a Valid Work Phone No");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "employeeFirstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "employeeLastName.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deviceEmployeeNo", "deviceEmployeeNo.required", "Please Enter Device Id");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId", "employeeId.required", "Please Enter Device Id");
		
		
		
		emailPattern=Pattern.compile(EMAIL_PATTERN);
		
		emailMatcher=emailPattern.matcher(employee.getEmail());
		
		if(!emailMatcher.matches())
		{
			errors.rejectValue("email", "email.required", "Please Enter a Valid e-mail Id");
		}
		/*if(employee.getWorkspace()==0)
			errors.rejectValue("workspace", "workspace.required","Please Select Workspace");*/
		if(employee.getDepartmentId()==0)
			errors.rejectValue("departmentId", "employeeDepartmentId.required");
		if(employee.getDivision()==0)
			errors.rejectValue("division", "employeeDivision.required");
		if(employee.getDesignation()==0)
			errors.rejectValue("designation", "employeeDesignation.required");
		String loginUser=(String)request.getSession().getAttribute("loginUser");
		if(!loginUser.equals("admin"))
		{
			if(employee.getSupervisor()==0)
			errors.rejectValue("supervisor", "employeeSupervisor.required");
		}
		
		if(employee.getLocation()==0)
			errors.rejectValue("location", "employeeLocation.required");
		if(employee.getWorkspace()==0)
			errors.rejectValue("workspace", "employeeWorkspace.required");
		if(employee.getCurrentCountry()==0)
			errors.rejectValue("currentCountry", "Country.required");
		if(employee.getCurrentState()==0)
		{
			if(employee.getCurrentCountry()!=0)
			{
			employee.setCurrentCountry(0);
			errors.rejectValue("currentCountry", "Country.required");
			errors.rejectValue("currentState", "State.required");
			}
			else
			{
			errors.reject("currentState", "");
			}
		}
		if(employee.getCurrentCity()==0)
		{
			if(employee.getCurrentState()!=0)
			{
			employee.setCurrentCountry(0);
			errors.rejectValue("currentCountry", "Country.required");
			errors.rejectValue("currentCity", "City.required");
			}
			else
			errors.reject("currentCity", "");
		}
	}
	
	public boolean checkIfNumber(String number)
	{
		try
		{
			Long.parseLong(number);
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		
	}

}
