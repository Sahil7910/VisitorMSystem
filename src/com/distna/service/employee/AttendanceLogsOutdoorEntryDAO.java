package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.AttendanceLogsOutdoorEntry;

public interface AttendanceLogsOutdoorEntryDAO
{
	public void saveOutdoorEntry(AttendanceLogsOutdoorEntry attendanceLogsOutdoorEntry);
	public List<AttendanceLogsOutdoorEntry> getAttendanceOutEntryList(int employeeId);
	public boolean isRecordAvailable(String recorddate,int employeeId);
	public List<AttendanceLogsOutdoorEntry> getAttendanceOutEntryList();
}
