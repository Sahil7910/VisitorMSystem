package com.distna.service.employee;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.employee.EmployeeBank;




public class EmployeeBankValidator implements Validator  {

	@Override
	public boolean supports(Class<?> validate) {
		// TODO Auto-generated method stub
		return EmployeeBank.class.isAssignableFrom(validate);
	}

	
	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		EmployeeBank employeeBank=(EmployeeBank) object;
		
		if(employeeBank.getEmployeeId()==0)
		{
			errors.rejectValue("employeeId", "employeeBankId.required","Please Select Employee");
		}
		
		
		ValidationUtils.rejectIfEmpty(errors, "BankName", "employeeBankName.required","Bank name required");
		
		ValidationUtils.rejectIfEmpty(errors, "BankBranchName", "employeeBankBranchName.required","Bank Baranch name required");
		
		ValidationUtils.rejectIfEmpty(errors, "BankAddress", "employeeAddress.required","Bank address required");
		
		if(employeeBank.getAccountNo().equals("0"))
		{
			errors.rejectValue("AccountNo", "employeeBankAccountNo.required","Please enter Bank Account Number");
		}
		
		
		
//		if(employeeBank.getPan_no().equals("0"))
//		{
//			errors.rejectValue("PANNo", "employeeBankAccountNo.required","Please enter PAN Number");
//		}
//		
		if(employeeBank.getIFSC_CODE().equals("0"))
		{
			errors.rejectValue("IFSC_CODE", "employeeBankIFSC_CODE.required","Please enter IFSC Code");
		}
		
		
	}

	
}
