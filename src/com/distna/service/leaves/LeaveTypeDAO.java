package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.LeaveType;

public interface LeaveTypeDAO {
	
	public void saveLeaveType(LeaveType leaveType);
	public List<LeaveType> getleavetypes();
	public List<LeaveType> getLeaveTypeById(int id);
	public void deleteLeaveType(int id);
	public List<LeaveType> getLeaveTypeforRank(int rank);
	public boolean getLeaveTypeforRank(int rank,int id);
	public String getLeaveTypeNameById(int leaveTypeId);
	

}
