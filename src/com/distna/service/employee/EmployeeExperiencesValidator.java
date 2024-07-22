package com.distna.service.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.EmployeeExperiences;

public class EmployeeExperiencesValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return EmployeeExperiences.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		EmployeeExperiences employeeExperiences=(EmployeeExperiences) object;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
		if(!employeeExperiences.getSalary().equals(""))
		{
			if(!employeeExperiences.getSalary().matches("^[0-9,]+$"))
			{
				errors.rejectValue("salary", "salary.required","Salary Not Valid");
			}
		}
		
		
		if(employeeExperiences.getEmployeeId()==0)
		{
			errors.rejectValue("employeeId", "employeeId.required");
		}
		
		String employeeExperiencesWebsite="http://"+employeeExperiences.getWebsite();
		
		UrlValidator urlValidator = new UrlValidator();
		
		if(!urlValidator.isValid(employeeExperiencesWebsite) && !employeeExperiencesWebsite.equals("http://"))
		{
			errors.rejectValue("website", "employeeExperiencesWebsite.required", "Please Enter the Correct Website");
		}
		
		
		ValidationUtils.rejectIfEmpty(errors, "designation", "designation.required");
		ValidationUtils.rejectIfEmpty(errors, "company", "company.required");
		try {
			if(dateFormat.parse(employeeExperiences.getFromDate()).after(dateFormat.parse(employeeExperiences.getToDate())))
			{
				errors.rejectValue("toDate", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
