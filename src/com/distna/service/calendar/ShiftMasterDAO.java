package com.distna.service.calendar;

import java.util.List;

import com.distna.domain.calendar.ShiftMaster;

public interface ShiftMasterDAO 
{
	public void saveShiftMaster(ShiftMaster shiftMaster);
	public List<ShiftMaster> getShiftMasterList();
	public List<ShiftMaster> getShiftMasterList(int id);
	public void deleteShiftMaster(int id);
	public void saveOrUpdateShiftMaster(ShiftMaster shiftMaster);
}
