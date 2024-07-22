package com.distna.service.employee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.Employee;

public class EmployeePersonalValidator implements Validator {

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
		
		Employee employee=(Employee)target;
		String personalPhone=employee.getHomePhone();
		String personalEmail=employee.getPersonalEmails();
		String homeEmail=employee.getHomeEmail();
		
		emailPattern=Pattern.compile(EMAIL_PATTERN);
		
		emailMatcher=emailPattern.matcher(homeEmail);
		
		if(!emailMatcher.matches() && !homeEmail.equals(""))
		{
			errors.rejectValue("homeEmail", "homeEmail.required", "Please Enter a Valid e-mail Id");
		}
		
		emailMatcher=emailPattern.matcher(personalEmail);
		
		if(!emailMatcher.matches() && !personalEmail.equals(""))
		{
			errors.rejectValue("personalEmails", "personalEmail.required", "Please Enter a Valid e-mail Id");
		}
		
		if(!personalPhone.equals("")&&(personalPhone.length()!=10 || checkIfNumber(personalPhone)))
		{
			errors.rejectValue("homePhone", "homePhone.required", "Please Enter a Valid Phone Number");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "employeeFirstName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "employeeLastName.required");
		if(employee.getPermanentCountry()==0)
			errors.rejectValue("permanentCountry", "Country.required");
		if(employee.getPermanentState()==0)
		{
			if(employee.getPermanentCountry()!=0)
			{
			employee.setPermanentCountry(0);
			errors.rejectValue("permanentCountry", "Country.required");
			errors.rejectValue("permanentState", "State.required");
			}
			else
			{
			errors.reject("permanentState", "");
			}
		}
		if(employee.getPermanentCity()==0)
		{
			if(employee.getPermanentState()!=0)
			{
			employee.setPermanentCountry(0);
			errors.rejectValue("permanentCountry", "Country.required");
			errors.rejectValue("permanentCity", "City.required");
			}
			else
			errors.reject("permanentCity", "");
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
