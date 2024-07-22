package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.CompanyPolicies;

public class CompanyPoliciesValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		CompanyPolicies companyPolicies=(CompanyPolicies) object;
		
		ValidationUtils.rejectIfEmpty(errors, "section", "section.required", "Please Enter Section");
		
		ValidationUtils.rejectIfEmpty(errors, "title", "title.required", "Please Enter Title");
		
		if(companyPolicies.getDepartmentId()==0)
		{
			errors.rejectValue("departmentId", "departmentId.required", "Please Select Department");
		}
		
		if(companyPolicies.getPriorityId()==0)
		{
			errors.rejectValue("priorityId", "priorityId.required", "Please Select Priority");
		}
		
		if(companyPolicies.getLocationId()==0)
		{
			errors.rejectValue("locationId", "locationId.required", "Please Select Location");
		}

	}

}
