package com.distna.service.calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.calendar.ShiftMaster;

public class ShiftMasterValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		
		return ShiftMaster.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		ShiftMaster shiftMaster=(ShiftMaster)object;
			
			ValidationUtils.rejectIfEmpty(errors, "shiftcode", "shiftcode.required","Please Enter Shift Code.");
			
			ValidationUtils.rejectIfEmpty(errors, "shiftname", "shiftname.required","Please Enter Shift Name.");
			
			if(shiftMaster.getShiftcode().length()>5)
			{
				errors.rejectValue("shiftcode", "shiftcode.required", "Shift Code Should Be Less Than 5 Characters.");
			}

	}

}
