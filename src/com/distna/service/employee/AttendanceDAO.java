package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Attendance;
import com.distna.domain.employee.AttendanceLogsBulkEntry;
import com.distna.domain.employee.AttendanceRecord;


public interface AttendanceDAO {
	
	public List<Attendance> getAttendanceList(int employeeNo);
	public void saveAttendance(Attendance attendance);
	public void updateattendance(Attendance attendance);
}
