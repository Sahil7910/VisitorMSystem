package com.distna.service.employee;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.EmployeeMessages;

public class EmployeeMessagesValidator implements Validator{

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return EmployeeMessages.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		EmployeeMessages employeeMessages=(EmployeeMessages) object;
		
		ValidationUtils.rejectIfEmpty(errors, "messageSubject", "messageSubject.required","Please Enter Subject");
		
		ValidationUtils.rejectIfEmpty(errors, "messageBody", "messageBody.required","Please Enter Message");
		
		if(employeeMessages.getEmployeeId()==0)
		{
			errors.rejectValue("employeeId", "messagesEmployeeId.required", "Please Select Employee");
		}
	}

}
