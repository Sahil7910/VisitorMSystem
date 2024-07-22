package com.distna.service.visitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.leaves.LeaveType;
import com.distna.domain.visitor.Visitors;

public class VisitorValidator implements Validator {

	private Pattern emailPattern;
	
	private Matcher emailMatcher;
	
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return Visitors.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		Visitors visitors=(Visitors) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "visitorName", "visitorName.required");
		if(visitors.getVisitorType().equals("0"))
		{
			errors.rejectValue("visitorType", "visitorType.required", "VisitorType is required");
		}
		if(visitors.getLocation()==0)
		{
			errors.rejectValue("location", "location.required", "Location is required");
		}
		String mobile=visitors.getMobileNo();
		String phoneNo=visitors.getPhoneNo();
		if((mobile.length()!=10 || checkIfNumber(mobile)) && !mobile.equals(""))
		{
			errors.rejectValue("mobileNo", "mobileNo.required", "Please Enter a Valid Mobile Number");
		}
		if((phoneNo.length()!=10 || checkIfNumber(phoneNo)) && !phoneNo.equals(""))
		{
			errors.rejectValue("phoneNo", "phoneNo.required", "Please Enter a Valid Phone Number");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "emailId.required");
		
		emailPattern=Pattern.compile(EMAIL_PATTERN);
		
		emailMatcher=emailPattern.matcher(visitors.getEmailId());
		
		if(!emailMatcher.matches())
		{
			errors.rejectValue("emailId", "emailId.required", "Please Enter a Valid e-mail Id");
		}
		if(visitors.getAddress().contains(","))
		{
			errors.rejectValue("address", "address.required", "No characters allowed in address");
		}
		if(visitors.getRemarks().contains(","))
		{
			errors.rejectValue("remarks", "remarks.required", "No characters allowed in remarks");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address1.required");
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
