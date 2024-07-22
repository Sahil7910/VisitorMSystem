package com.distna.service.employee;

import java.util.List;


import com.distna.domain.employee.AttendanceRecord;

public interface AttendanceRecordDAO {
	public void saveAttendancerecord(AttendanceRecord attendanceRecord);
	public List<AttendanceRecord> getAttendanceRecordList();
	public List<AttendanceRecord> getEmployeeListByEmployeeNo(int EmployeeNo);
	public List<AttendanceRecord> getlastentryList(AttendanceRecord attendanceRecord);

}
