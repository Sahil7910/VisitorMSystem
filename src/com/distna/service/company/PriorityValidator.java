package com.distna.service.company;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Priority;

public class PriorityValidator implements Validator{

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Priority.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		Priority priority=(Priority) object;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "priorityName.required", "Please Enter Priority Name");
		
		if(priority.getRank()==0 || checkIfNumber(priority.getRank()))
		{
			errors.rejectValue("rank", "priorityRank.required", "Please Enter Non-zero Number");
		}
		
	}
	
	public boolean checkIfNumber(int number)
	{
		String strNumber=number+"";
		try
		{
		Long.parseLong(strNumber);
		return false;
		}
		catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		
	}

}
