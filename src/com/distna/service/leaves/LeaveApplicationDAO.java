package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.LeaveApplication;

public interface LeaveApplicationDAO {
	
	public void saveLeaveApplication(LeaveApplication leaveApplication);
	public List<LeaveApplication> getLeaveApplications();
	public List<LeaveApplication> getLeaveApplicationsById(int id);
	public void deleteLeaveApplications(int id);
	public List<LeaveApplication> getApplicationsByIdandStatus(int id);
	public List<LeaveApplication> getApplicationsByStatus(int statusid);
	public List<LeaveApplication> getLeaveApplicationsByEmployeeId(int empId);

}
