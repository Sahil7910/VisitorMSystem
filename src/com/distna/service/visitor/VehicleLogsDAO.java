package com.distna.service.visitor;

import java.util.List;
import com.distna.domain.visitor.VehicleLogs;

public interface VehicleLogsDAO
{
	public void saveVehicleLogs(VehicleLogs vehicleLogs);
	public void updateVehicleLogs(VehicleLogs vehicleLogs);
	public void deleteVehicleLogs(int logId);
	public VehicleLogs getVehicleLogs(int logId);
	public List<VehicleLogs> getVehicleLogsList();
}
