package com.distna.service.calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.calendar.Holidays;

public class HolidaysValidator implements Validator{

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Holidays.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		ValidationUtils.rejectIfEmpty(errors, "holidayName", "holidayName.required" ,"Please Enter Holiday Name");
		
	}
	
	

}
