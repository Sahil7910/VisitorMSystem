package com.distna.service.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.CanteenTimings;

public class CanteenTimingsValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return CanteenTimings.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		CanteenTimings canteenTimings=(CanteenTimings) object;
		
		DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
		
		ValidationUtils.rejectIfEmpty(errors, "timingName", "canteenTimingName.required", "Please enter name");
		
		if(canteenTimings.getStartTime().equals("0"))
		{
			errors.rejectValue("startTime", "canteenStartTime.required", "Please select start time");
		}
		
		if(canteenTimings.getEndTime().equals("0"))
		{
			errors.rejectValue("endTime", "canteenEndTime.required", "Please select end time");
		}
		
		if(!canteenTimings.getStartTime().equals("0") && !canteenTimings.getEndTime().equals("0"))
		{
			try {
				if(dateFormat.parse(canteenTimings.getEndTime()).before(dateFormat.parse(canteenTimings.getStartTime())))
				{
					errors.rejectValue("endTime", "canteenEndTime.wrong", "End time should be after start time");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(canteenTimings.getDefaultItem()==0)
		{
			errors.rejectValue("defaultItem", "canteenDefaultItem.required", "Please select default item");
		}


	}

}
