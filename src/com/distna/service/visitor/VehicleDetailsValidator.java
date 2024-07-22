package com.distna.service.visitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.visitor.VehicleDetails;


public class VehicleDetailsValidator implements Validator {

	
	private Pattern numberPlatePattern;
	
	private Matcher numberPlateMatcher;
	
	private static final String NUMBER_PATTERN="^[A-Z,a-z]{2} [\\s]{1} [0-9]{2} [\\s]{1} [A_Z,a-z]{2}[\\s]{1} [0-9]{1,4}"; 
	
	
	@Override
	public boolean supports(Class<?> validate)
	{
		return VehicleDetails.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		VehicleDetails vehicleDetails=(VehicleDetails) object;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicleBrand", "vehicleBrand.required","Please enter vehicle brand");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicleModel", "vehicleModel.required","Please enter vehicle model");
		
		if(vehicleDetails.getVehicleType().equals("0"))
		{
			errors.rejectValue("vehicleType", "vehicleType.required", "Please select vehicle type");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ownerName", "ownerName.required","Please enter owner's name");
		
		if(checkIfNumber(vehicleDetails.getOwnerPhoneNo()))
		{
			errors.rejectValue("ownerPhoneNo", "ownerPhoneNo.required", "Please enter phone number");
		}
		
		
		numberPlatePattern=Pattern.compile(NUMBER_PATTERN);
		numberPlateMatcher=numberPlatePattern.matcher(vehicleDetails.getVehicleNumber());
//		
//		if(!numberPlateMatcher.matches())
//		{
//			errors.rejectValue("vehicleNumber", "vehicleNumber.required", "Please enter a valid vehicle number");
//		}
//		
		
		
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
