package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.LeaveAllocation;
import com.distna.domain.leaves.LeaveApplication;

public interface LeaveAllocationDAO {
	
	public void saveLeaveAllocation(LeaveAllocation leaveAllocation);
	public List<LeaveAllocation> getLeaveAllocation();
	public List<LeaveAllocation> getLeaveAllocationById(int id);
	public void deleteLeaveAllocation(int id);
	public void updateLeaveAllocation(LeaveAllocation leaveAllocation);
//	List<LeaveApplication> getApplicationsByIdandStatus(int id);
	

}
