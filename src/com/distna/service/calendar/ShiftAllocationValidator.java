package com.distna.service.calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;

public class ShiftAllocationValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		return ShiftDefinition.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ShiftAllocation shiftAllocation=(ShiftAllocation)object;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		if(shiftAllocation.getDepartment()==0)
		{
			errors.rejectValue("department", "department.required","Department Not Selected");
		}
		if(shiftAllocation.getEmployee_id()==0)
		{
			errors.rejectValue("employee_id", "employee_id.required","Employee Not Selected");
		}
		if(shiftAllocation.getShiftid()==0)
		{
			errors.rejectValue("shiftid", "shiftid.required","Shift Not Selected");
		}

		try {
			if(dateFormat.parse(shiftAllocation.getStartdate()).after(dateFormat.parse(shiftAllocation.getEnddate())))
			{
				errors.rejectValue("enddate", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
