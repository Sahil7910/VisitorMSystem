package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Workspaces;

public class WorkspacesValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Workspaces.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		Workspaces workspaces=(Workspaces)object;
		
		ValidationUtils.rejectIfEmpty(errors, "workspaceName", "workspaceName", "Please Enter Workspace Name");
		
		
		if(workspaces.getDepartment()==0)
		{
			errors.rejectValue("department", "department.required", "Please Select Department");
		}
		

	}

}
