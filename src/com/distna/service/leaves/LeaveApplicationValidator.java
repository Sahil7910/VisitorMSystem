package com.distna.service.leaves;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.leaves.LeaveApplication;

public class LeaveApplicationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return LeaveApplication.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		LeaveApplication leaveApplication= (LeaveApplication) target;
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"from_date", "applicationFromDate.required","From Date Field Mandatory");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"to_date", "applicationToDate.required","To Date Field Mandatory");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"duration", "applicationDuration.required","Duration Field Mandatory");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"subject", "subject.required","Subject Field Mandatory");
		
		try {
			if(dateFormat.parse(leaveApplication.getFrom_date()).after(dateFormat.parse(leaveApplication.getTo_date())))
			{
				errors.rejectValue("to_date", "toDate.required","From Date And To Date Not Valid");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(Integer.toString(leaveApplication.getEmployee_id()).equals("0"))
		{
		
			errors.rejectValue("employee_id", "leaveApplicationEmpId.required");
		}
		if(leaveApplication.getLeavetype()==0)
			errors.rejectValue("leavetype", "leavetype.required");
		/*
		 * if(leaveApplication.getPriority()==0) {
		 * 
		 * errors.rejectValue("priority", "priority.required"); }
		 */
		if(leaveApplication.getDepartment_id()==0)
			errors.rejectValue("department_id", "leaveApplicationDepartment.required");
		
		if(leaveApplication.getDuration()<0)
			errors.rejectValue("duration", "negativeDuration.required");
		
		
	}

}
