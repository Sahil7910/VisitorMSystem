package com.distna.service.calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.distna.domain.calendar.ShiftDefinition;

public class ShiftDefinitionValidator implements Validator{

	@Override
	public boolean supports(Class<?> validate) {
	
		return ShiftDefinition.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		ShiftDefinition shiftDefinition=(ShiftDefinition)object;
		ValidationUtils.rejectIfEmpty(errors, "dateField", "dateField.required","Please Enter Start Date");
		ValidationUtils.rejectIfEmpty(errors, "endDate", "endDate.required","Please Enter End Date");
		
		if(shiftDefinition.getShiftid()==0)
		{
			errors.rejectValue("shiftid", "shiftid.required","Please Select Shift");
		}
		if(shiftDefinition.getLocation_id()==0)
		{
			errors.rejectValue("location_id", "location_id.required","Please Select Location");
		}
		/*if(shiftDefinition.getCategory()==0)
		{
			errors.rejectValue("category", "category.required","Please Select Category");
		}*/
		if(shiftDefinition.getTimeField().equals("0"))
		{
			errors.rejectValue("timeField", "timeField.required","Please Select Start Time");
		}
		if(shiftDefinition.getEndTime().equals("0"))
		{
			errors.rejectValue("endTime", "endTime.required","Please Select End Time");
		}
	}
}
