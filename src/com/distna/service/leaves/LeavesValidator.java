package com.distna.service.leaves;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.distna.domain.leaves.Breaks;
import com.distna.utility.DateTime;
public class LeavesValidator implements Validator {

	
	
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return Breaks.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	Breaks breaks=(Breaks) target;
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "breakDate.required");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String dateFormat = "dd-MM-yyyy";

	String currentdate = DateTime.CurrentDate(dateFormat);
	/*System.out.println(currentdate);*/
	
	if(breaks.getEmployee_no()==0)
	{
		errors.rejectValue("employee_no", "breaksEmployee.required");
	}
	
	if(breaks.getDuration()==0)
		errors.rejectValue("duration", "breakDuration.required");
	
	if(breaks.getStatus()==0)
		errors.rejectValue("status", "breakStatus.required");
	
	try {
		if(sdf.parse(breaks.getDate()).before(sdf.parse(currentdate)))
		{
			errors.rejectValue("date", "date.required");
		}
	} catch (ParseException e) {
		e.printStackTrace();
	}

	}
	 private boolean isNumber(String str){
	        for (int i = 0; i < str.length(); i++) {
	            //If we find a non-digit character we return false.
	            if (!Character.isDigit(str.charAt(i)))
	            return false;
      }
	        return true;
	    }

}
