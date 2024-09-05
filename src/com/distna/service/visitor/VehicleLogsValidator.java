package com.distna.service.visitor;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.distna.domain.visitor.VehicleLogs;


public class VehicleLogsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) 
	{
		return VehicleLogs.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		VehicleLogs vehicleLogs=(VehicleLogs)target;
		if(vehicleLogs.getVehicleId()==0)
		{
			errors.rejectValue("vehicleId", "vehicleId.required", "Vehicle Selection is mandatory");
		}
		if(vehicleLogs.getStatus().equals("0"))
		{
			errors.rejectValue("status", "status.required", "Status Selection is mandatory");
		}
		if(vehicleLogs.getPurpose().equals("0"))
		{
			errors.rejectValue("purpose", "purpose.required", "Select Purpose");
		}
		if(vehicleLogs.getInTime().equals("0"))
		{
			errors.rejectValue("inTime", "inTime.required", "Select In Time");
		}
		if(vehicleLogs.getOutTime().equals("0"))
		{
			errors.rejectValue("outTime", "outTime.required", "Select Out Time");
		}
		if(vehicleLogs.getConcernPerson()==0)
		{
			errors.rejectValue("concernPerson", "concernPerson.required", "Select Concerned Person");
		}
		
	}

}
