package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.AttendanceLogs;

public interface AttendanceLogsDAO {
	public void saveAttendanceLogs(AttendanceLogs attendanceLogs);
	public List<AttendanceLogs> getAttendanceLogsByMachineId(int machineId);

}
