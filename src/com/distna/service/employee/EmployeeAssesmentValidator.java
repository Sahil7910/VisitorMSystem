package com.distna.service.employee;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.Assesment;

public class EmployeeAssesmentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Assesment.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Assesment assesment=(Assesment)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"date1", "assesmentDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"remark1", "assesmentRemark.required");
		
		if(assesment.getEmpid()==0)
		{
		
			errors.rejectValue("empid", "assesmentEmpId.required");
		}
		if(assesment.getPosition()==0)
			errors.rejectValue("position", "position.required");
		if(assesment.getPersonality().equals("0"))
			errors.rejectValue("personality", "personality.required");
		if(assesment.getCommunication().equals("0"))
			errors.rejectValue("communication", "communication.required");
		if(assesment.getKnowledge().equals("0"))
			errors.rejectValue("knowledge", "knowledge.required");
		if(assesment.getInterviewer1()==0)
			errors.rejectValue("interviewer1", "assesmentInterviewr.required");
		
	}

}
