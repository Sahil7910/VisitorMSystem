package com.distna.service.leaves;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.leaves.LeaveAllocation;

public class LeaveAllocationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return LeaveAllocation.class.isAssignableFrom(clazz) ;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		LeaveAllocation leaveAllocation=(LeaveAllocation)target;
		
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"approved_fromdate", "applicationFromDate.required","From Date Field Mandatory");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"approved_todate", "applicationToDate.required","To Date Field Mandatory");
		
		try {
			if(dateFormat.parse(leaveAllocation.getApproved_fromdate()).after(dateFormat.parse(leaveAllocation.getApproved_todate())))
			{
				errors.rejectValue("approved_todate", "toDate.required","From Date And To Date Not Valid");
			}
			if(dateFormat.parse(leaveAllocation.getApproved_fromdate()).before(dateFormat.parse(leaveAllocation.getFromdate())))
			{
				errors.rejectValue("approved_fromdate", "approvedfromDate.required","Approved FromDate must be after requested FromDate");
			}
			if(dateFormat.parse(leaveAllocation.getApproved_todate()).after(dateFormat.parse(leaveAllocation.getTodate())))
			{
				errors.rejectValue("approved_todate", "approvedtoDate.required","Approved ToDate must be before requested ToDate");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"value", "value.required","Value Field Mandatory");
		
		if( checkIfNumber(leaveAllocation.getValue()))
		{
			errors.rejectValue("value", "valueint.required", "Please Enter No.of allocated days");
		}
		if(leaveAllocation.getTypeofleave()==0)
			errors.rejectValue("typeofleave", "typeofleave.required");
		if(leaveAllocation.getAllocationStatus()==0)
			errors.rejectValue("allocationStatus", "allocationStatus.required","Please Select valid status");
		
	}
	
	public boolean checkIfNumber(String number)
	{
		try
		{
			Long.parseLong(number);
			return false;
		}
		catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		
	}

}
