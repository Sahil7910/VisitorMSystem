package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.Status;

public interface StatusDAO {
	


   public List<Status> getStatusByCategory(String category1);
   
   public List<Status> getLeaveStatus(int statusId);
}
