package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.CanteenItems;

public class CanteenItemsValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return CanteenItems.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		CanteenItems canteenItems=(CanteenItems) object;
		
		ValidationUtils.rejectIfEmpty(errors, "itemName", "canteenitemName.required","Please enter name");
		
		if(canteenItems.getEmployeeContribution()<0)
		{
			errors.rejectValue("employeeContribution", "employeeContribution.required", "Please enter a positive value");
		}
		
		if(canteenItems.getEmployerContribution()<0)
		{
			errors.rejectValue("employerContribution", "employerContribution.required", "Please enter a positive value");
		}
		
	}

}
