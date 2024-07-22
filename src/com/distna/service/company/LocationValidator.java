package com.distna.service.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Location;

public class LocationValidator implements Validator {
	
	private Pattern emailPattern;
	
	private Matcher emailMatcher;
	
	private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Location.class.isAssignableFrom(validate);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		Location location=(Location) object;
		
		String locationWebsite="http://"+location.getWebsite();
		
		UrlValidator urlValidator=new UrlValidator();
		
		if(!urlValidator.isValid(locationWebsite) && !locationWebsite.equals("http://"))
		{
			errors.rejectValue("website", "locationWebsite.required", "Please Enter the Correct Website");
		}
		
		
		emailPattern=Pattern.compile(EMAIL_PATTERN);
		
		emailMatcher=emailPattern.matcher(location.getEmail());
		
		if(!emailMatcher.matches() && !location.getEmail().equals(""))
		{
			errors.rejectValue("email", "locationEmail.required", "Please Enter a Valid e-mail Id");
		}
		
		String locationPhone=location.getPhone();
		
		if((locationPhone.length()!=10 || checkIfNumber(locationPhone)) && !locationPhone.equals(""))
		{
			errors.rejectValue("phone", "locationPhone.required", "Please Enter a Valid Phone Number");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "location", "location.required", "Please Enter Location Name");
		
		ValidationUtils.rejectIfEmpty(errors, "address1", "address1.required", "Please Enter Address 1");
		
		/*ValidationUtils.rejectIfEmpty(errors, "address2", "address2.required", "Please Enter Address 2");*/
		
		if(location.getCountry()==0)
		{
			errors.rejectValue("country", "country.required", "Please Select Country");
		}
		
		if(location.getState()==0)
		{
			errors.rejectValue("state", "state.required", "Please Select State");
		}
		
		/*if(location.getCity()==0)
		{
			errors.rejectValue("city", "city.required", "Please Select City");
		}*/
		
		if(location.getPostal_code().length()!=6 || checkIfNumber(location.getPostal_code()))
		{
			errors.rejectValue("postal_code", "postal_code.required", "Please Enter a Valid Postal Code");
		}

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
