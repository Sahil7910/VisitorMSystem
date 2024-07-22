package com.distna.service.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.Projects;

public class EmployeeProjectsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Projects.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Projects projects=(Projects)target;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");

		ValidationUtils.rejectIfEmpty(errors, "fromDate", "projectFromDate.required");
		ValidationUtils.rejectIfEmpty(errors, "toDate", "projectToDate.required");
		ValidationUtils.rejectIfEmpty(errors, "link", "projectLink.required");
		ValidationUtils.rejectIfEmpty(errors, "client", "projectClient.required");
		ValidationUtils.rejectIfEmpty(errors, "role", "projectRole.required");
		ValidationUtils.rejectIfEmpty(errors, "projectName", "projectName.required");
		ValidationUtils.rejectIfEmpty(errors, "technology", "projectTechnology.required");
		try {
			if(dateFormat.parse(projects.getFromDate()).after(dateFormat.parse(projects.getToDate())))
			{
				errors.rejectValue("toDate", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(projects.getEmployeeId()==0)
			errors.rejectValue("employeeId", "projectEmployeeId.required");
		
	}

}
