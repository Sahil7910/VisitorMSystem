package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Division;

public class DivisionValidator implements Validator {

	@Override
	public boolean supports(Class<?> validator) {
		// TODO Auto-generated method stub
		return Division.class.isAssignableFrom(validator);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		Division division=(Division) object;
		
		ValidationUtils.rejectIfEmpty(errors, "divisionName", "divisionName.required", "Please Enter Contractor/Sub Company");
		
		ValidationUtils.rejectIfEmpty(errors, "divisionCode", "divisionCode.required", "Please Enter Code");
		
		if(division.getLocationId()==0)
		{
			errors.rejectValue("locationId", "locationId.required", "Please Select Location");
		}

	}

}
