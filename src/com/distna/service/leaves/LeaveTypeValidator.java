package com.distna.service.leaves;


import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.DesignationLevel;
import com.distna.domain.leaves.LeaveType;

public class LeaveTypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) 
	{
		return LeaveType.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors,LeaveTypeDAO leaveTypeDAO) 
	{
	LeaveType leaveType=(LeaveType) target;
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "leavetypeName.required");
	List<LeaveType> leaveTypeList=new ArrayList<LeaveType>();
	
	/*String rankInString=leaveType.getRank()+"";
	if(!rankInString.matches("^[0-9]+$"))
	{
		errors.rejectValue("rank", "stringRank.required","Rank Should Be A Number");
	}
	else
	{*/
		/*if(leaveType.getRank()==0 || leaveType.getRank()<0)
		{
			errors.rejectValue("rank", "leavetypeRank.required");
		}
		else
		{
			if(leaveType.getId()==0)
			{
				leaveTypeList=leaveTypeDAO.getLeaveTypeforRank(leaveType.getRank());
				if(leaveTypeList.size()>0)
				{
					errors.rejectValue("rank", "rankDuplicate.required", "Rank already exists");
				}
			}
			else
			{
				boolean errorFlag=leaveTypeDAO.getLeaveTypeforRank(leaveType.getRank(),leaveType.getId());
					if(!errorFlag)
					{
						errors.rejectValue("rank", "rankDuplicate.required", "Rank already exists");
					}
			}
		}
	}*/
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
}
