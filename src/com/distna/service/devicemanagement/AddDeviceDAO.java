package com.distna.service.devicemanagement;

import java.util.List;

import com.distna.domain.devicemanagement.AddDevice;

public interface AddDeviceDAO {
	public void saveOrUpdateDevice(AddDevice addDevice);
	public List<AddDevice> getDeviceList();
	public AddDevice getDeviceInfoByIp(String ipAddress);
	public List<AddDevice> getDeviceInfoListByIp(String ipAddress);
	public void deleteAttendanceDevice(String ipAddress);

}
