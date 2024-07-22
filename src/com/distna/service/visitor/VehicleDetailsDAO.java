package com.distna.service.visitor;

import java.util.List;

import com.distna.domain.visitor.VehicleDetails;

public interface VehicleDetailsDAO 
{
	public void saveVehicleDetails(VehicleDetails vehicleDetails);
	public void updateVehicleDetails(VehicleDetails vehicleDetails);
	public void deleteVehicleDetails(int vehicleId);
	public VehicleDetails getVehicleDetails(int vehicleId);
	public List<VehicleDetails> getVehicleDetailsList();
	public List<String> getUniqueTypeVehicleList();
}
