package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.JobRoles;

public class JobRolesValidator implements Validator {

	@Override
	public boolean supports(Class<?> validator) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		JobRoles jobRoles=(JobRoles) object;
		
		if(jobRoles.getLevel()<1)
		{
			errors.rejectValue("level", "level.required", "Please Enter a Valid Level");
		}
		
		if(jobRoles.getPriority()==0)
		{
			errors.rejectValue("priority", "priority.required", "Please Select Priority");
		}
		
		
		ValidationUtils.rejectIfEmpty(errors, "roleName", "roleName.required", "Please Enter Job Role name");

	}

}
