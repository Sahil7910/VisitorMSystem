package com.distna.service.calendar;

import java.util.List;

import com.distna.domain.calendar.ShiftAllocation;

public interface ShiftAllocationDAO {
	public void saveShiftDefinitions(ShiftAllocation shiftAllocation);
	public List<Integer> getShiftAllocatedEmployeeList();
	
	public List<ShiftAllocation> getShiftAllocatedEmployeeList(int employeeId);
	
	public List<ShiftAllocation> getShiftAllocatedByShiftId(int shiftId);
	
	public List<ShiftAllocation> getShiftAllocatedList();
	
	public void clearAttRecords();
	
	public String [] getShiftStartEndTime(int employeeId);
	/*public void setTime(long time);
	public long getTime();*/
	
}
