package com.distna.service.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.Education;
import com.distna.domain.employee.Employee;

public class EmployeeEducationValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return Employee.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
		Education education =(Education)target;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		/*ValidationUtils.rejectIfEmpty(errors, "firstName", "employeeFirstName.required", "Please Enter First Name");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "employeeLastName.required", "Please Enter Last Name");*/
		if(education.getEmp_education().equals("0"))
		{
			errors.rejectValue("emp_education", "empEducation.required","Qualification Not Selected");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "course_name", "courseName.required", "Please Enter Course Name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialisation", "specialisation.required", "Please Enter Specialisation");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "board_university", "boardUniversity.required", "Please Enter Board/University Name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "college", "college.required", "Please Enter College Name");
		if(education.getEdu_country()==0)
			errors.rejectValue("edu_country", "eduCountry.required","Country Not Selected");
		if(education.getEdu_state()==0)
		{
			if(education.getEdu_country()!=0)
			{
			education.setEdu_country(0);
			errors.rejectValue("edu_country", "eduCountry.required","Country Not Selected");
			errors.rejectValue("edu_state", "eduState.required","State Not Selected");
			}
			else
			{
				errors.rejectValue("edu_state", "eduState.required","State Not Selected Blank");
			}
		}
		if(education.getEdu_city()==0)
		{
			if(education.getEdu_city()!=0)
			{
				education.setEdu_country(0);
			errors.rejectValue("edu_country", "eduCountry.required","Country Not Selected");
			errors.rejectValue("edu_city", "eduCity.required","City Not Selected");
			}
			else
			errors.rejectValue("edu_country","eduCountry.required","Country Not Selected Blank");
		}
		if(education.getDuration()==0)
		{
			errors.rejectValue("duration", "Duration.required","Duration Not Valid");
		}
		if(education.getDuration()==0)
		{
			errors.rejectValue("percentage", "Percentage.required","Percentage Not Valid");
		}
		
		try {
			if(dateFormat.parse(education.getFrom_date()).after(dateFormat.parse(education.getTo_date())))
			{
				errors.rejectValue("to_date", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String companyWebsite="http://"+education.getWebsite();
		
		UrlValidator urlValidator = new UrlValidator();
		
		if(!urlValidator.isValid(companyWebsite) && !companyWebsite.equals("http://"))
		{
			errors.rejectValue("website", "educationWebsite.required", "Please Enter Correct Website");
		}

	}

}
