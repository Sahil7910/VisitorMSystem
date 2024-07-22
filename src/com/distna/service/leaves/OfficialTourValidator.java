package com.distna.service.leaves;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import com.distna.domain.leaves.OfficialTours;


public class OfficialTourValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return OfficialTours.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		OfficialTours officialTours=(OfficialTours)target;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "from_date", "fromDateTours.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to_date", "toDateTours.required");
		
		if(officialTours.getWorkID()==0)
		{
			errors.rejectValue("workID", "workID.required");
		}
		
		try {
			if(dateFormat.parse(officialTours.getFrom_date()).after(dateFormat.parse(officialTours.getTo_date())))
			{
				errors.rejectValue("to_date", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
