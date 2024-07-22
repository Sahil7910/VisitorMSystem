package com.distna.service.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.DesignationLevel;

public class DesignationLevelValidator implements Validator {

	@Override
	public boolean supports(Class<?> validator) {
		// TODO Auto-generated method stub
		return DesignationLevel.class.isAssignableFrom(validator);
	}

	public void validate(Object object, Errors errors,DesignationLevelDAO designationLevelDAO) {
		// TODO Auto-generated method stub
		
		DesignationLevel designationLevel=(DesignationLevel) object;
		List<DesignationLevel> designationLevelList=new ArrayList<DesignationLevel>();
		if(designationLevel.getId()==0)
		{
			designationLevelList=designationLevelDAO.getDesignationLevelforRank(designationLevel.getRank());
			if(designationLevelList.size()>0)
			{
				errors.rejectValue("rank", "rankDuplicate.required", "Rank already exists");
			}
		}
		else
		{
			boolean errorFlag=designationLevelDAO.getDesignationLevelforRank(designationLevel.getRank(),designationLevel.getId());
				if(!errorFlag)
				{
					errors.rejectValue("rank", "rankDuplicate.required", "Rank already exists");
				}
		}
		
		ValidationUtils.rejectIfEmpty(errors, "levelName", "levelName.required", "Please Enter Designation Level");
		
		
		if(designationLevel.getRank()==0)
		{
			errors.rejectValue("rank", "rank.required", "Please Enter a Valid Designation Rank");
		}
		

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

}
