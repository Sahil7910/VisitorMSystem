package com.distna.service.company;

import java.util.Scanner;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.company.Company;

public class CompanyValidator implements Validator {
	
	
	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return Company.class.isAssignableFrom(validate);
	}


	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		

		Company company=(Company) object;
		
		String companyWebsite="http://"+company.getWebsite();
		
		UrlValidator urlValidator = new UrlValidator();
		
		if(!urlValidator.isValid(companyWebsite))
		{
			errors.rejectValue("website", "companywebsite.required", "Please Enter the Correct Website");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "companyName", "companyName.required","Please Enter Company Name");
		
		ValidationUtils.rejectIfEmpty(errors, "companyAddress", "companyAddress.required","Please Enter Company Address");
		
		if(company.getAllowedLeaves()==0)
		{
			errors.rejectValue("allowedLeaves", "allowedLeaves.required", "Please Enter the No. of allowed leaves");
		}
		
		Scanner scanner=new Scanner(company.getAllowedLeaves()+"");
		if(!scanner.hasNextInt())
		{
			errors.rejectValue("allowedLeaves", "allowedLeaves.required", "Please Enter only numbers for allowed leaves");
		}
		
	}

}
