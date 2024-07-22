package com.distna.service.leaves;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.leaves.Breaks;

public class BreaksValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Breaks.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		Breaks breaks=(Breaks) object;
		
		ValidationUtils.rejectIfEmpty(errors, "title", "breakTitle.required", "Please Enter Title");
		
		if(breaks.getEmployee_no()==0)
		{
			errors.rejectValue("employee_no", "breaksEmployeeNo.required", "Please Select Employee");
		}
		
		if(breaks.getDuration()==0)
		{
			errors.rejectValue("duration", "breaksDuration.required", "Please Select Duration");
		}
		
		if(breaks.getStatus()==0)
		{
			errors.rejectValue("status", "breaksStatus.required", "Please Select Status");
		}
	}

}
