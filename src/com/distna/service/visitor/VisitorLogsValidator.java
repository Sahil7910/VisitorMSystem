package com.distna.service.visitor;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.distna.domain.visitor.VisitorLogs;

public class VisitorLogsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) 
	{
		return VisitorLogs.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		VisitorLogs visitorLogs=(VisitorLogs)target;
		if(visitorLogs.getEmployeeId()==0)
		{
			errors.rejectValue("employeeId", "employeeId.required", "Select Person to meet");
		}
		if(visitorLogs.getVisitorId()==0)
		{
			errors.rejectValue("visitorId", "visitorId.required", "Select the Visitor");
		}
		if(visitorLogs.getStatus().equals("0"))
		{
			errors.rejectValue("status", "status.required", "Select Status");
		}
		if(visitorLogs.getGateNo()==0)
		{
			errors.rejectValue("gateNo", "gateNo.required", "Select Gate No");
		}
		if(visitorLogs.getPurpose().equals("0"))
		{
			errors.rejectValue("purpose", "purpose.required", "Select Purpose");
		}
		if(visitorLogs.getInTime().equals("0"))
		{
			errors.rejectValue("inTime", "inTime.required", "Select In Time");
		}
		if(visitorLogs.getBadgeRemarks().contains(","))
		{
			errors.rejectValue("badgeRemarks", "badgeRemarks.required", "No characters allowed in badgeRemarks");
		}
		if(visitorLogs.getMaterialsCarried().contains(","))
		{
			errors.rejectValue("materialsCarried", "materialsCarried.required", "No characters allowed in materialsCarried");
		}
		if(visitorLogs.getBadgeValidity().contains(","))
		{
			errors.rejectValue("badgeValidity", "badgeValidity.required", "No characters allowed in badgeValidity");
		}
	}
}
