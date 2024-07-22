package com.distna.service.employee;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.EmployeeSkills;

public class EmployeeSkillsValidator implements Validator {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return EmployeeSkills.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		EmployeeSkills employeeSkills=(EmployeeSkills) object;
		
		if(employeeSkills.getEmployeeId()==0)
		{
			errors.rejectValue("employeeId", "employeeSkillsId.required","Please Select Employee");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "skillName", "employeeSkillsName.required","skill name required");
		
		if(employeeSkills.getSkillLevel().equals("0"))
		{
			errors.rejectValue("skillLevel", "employeeSkillsLevel.required","Please Select Skill Level");
		}
		
		if(employeeSkills.getExperienceYear()<0)
		{
			errors.rejectValue("experienceYear", "employeeSkillsExperienceYear.required","Please Enter a Valid Experience Year");
		}
		
		if(employeeSkills.getExperienceMonth()<0 || employeeSkills.getExperienceMonth()>12)
		{
			errors.rejectValue("experienceMonth", "employeeSkillsExperienceMonth.required","Please Enter a Valid Experience Month");
		}

	}
	

}
