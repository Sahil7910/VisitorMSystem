package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.JobPositions;

public class JobPositionValidator implements Validator {

	@Override
	public boolean supports(Class<?> validator) {
		// TODO Auto-generated method stub
		return JobPositions.class.isAssignableFrom(validator);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		JobPositions jobPositions=(JobPositions) object;
		
		ValidationUtils.rejectIfEmpty(errors, "job_code", "job_code.required", "Please Enter Job Code");
		
		ValidationUtils.rejectIfEmpty(errors, "position_name", "position_name.required", "Please Enter Position");
		
		if(jobPositions.getDesignation()==0)
		{
			errors.rejectValue("designation", "designation2.required", "Please Select Designation");
		}

	}

}
