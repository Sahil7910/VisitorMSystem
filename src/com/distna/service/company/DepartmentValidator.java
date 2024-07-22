package com.distna.service.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Departments;

public class DepartmentValidator implements Validator {

	private Pattern emailPattern;
	
	private Matcher emailMatcher;
	
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	@Override
	public boolean supports(Class<?> validator) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		Departments departments=(Departments) object;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "name.required", "Please Enter Department Name");
		
		/*if(departments.getHead()==0)
		{
			errors.rejectValue("head", "head", "Please Select Department Head");
		}*/
		
		emailPattern=Pattern.compile(EMAIL_PATTERN);
		
		emailMatcher=emailPattern.matcher(departments.getEmail());
		
		if(!emailMatcher.matches() && !departments.getEmail().equals(""))
		{
			errors.rejectValue("email", "departmentEmail.required", "Please Enter a Valid e-mail Id");
		}
		
		if(departments.getDivision()==0)
		{
			errors.rejectValue("division", "division.required", "Please Enter Division");
		}

	}

}
