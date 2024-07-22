package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Designation;

public class DesignationValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Designation.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		Designation designation=(Designation) object;
		
		ValidationUtils.rejectIfEmpty(errors, "designation", "designation.required", "Please Enter Designation");
		
		if(designation.getDepartment_id()==0)
		{
			errors.rejectValue("department_id", "department_id.required", "Please Select Department");
		}
		
		/*if(designation.getParent_designation()==0)
		{
			errors.rejectValue("parent_designation", "parent_designation.required", "Please Select Supervisor Designation ");
		}*/

	}

}
