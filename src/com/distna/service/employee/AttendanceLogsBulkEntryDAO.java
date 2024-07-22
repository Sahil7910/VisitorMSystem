package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.AttendanceLogsBulkEntry;

public interface AttendanceLogsBulkEntryDAO {
	public void saveAttendanceLogsBulkEntrys(AttendanceLogsBulkEntry attendanceLogsBulkEntry);
	//public void saveupdateAttendanceLogsBulkEntrys(AttendanceLogsBulkEntry attendanceLogsBulkEntry);
	public void saveAttendanceLogsBulkEntrysReports();
	public List<AttendanceLogsBulkEntry> getAttendanceLogsBulkEntrys(int employeeNo);
	
	public void saveAttendanceLogsBulkEntrysDefault(AttendanceLogsBulkEntry attendanceLogsBulkEntry);
	public boolean isRecordAvailable(String recorddate,int employeeId);
	public void saveAttendanceBulkEntryForOutdoor(AttendanceLogsBulkEntry attendanceLogsBulkEntry);

}
