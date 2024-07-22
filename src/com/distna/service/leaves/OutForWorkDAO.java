package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.OutForWork;

public interface OutForWorkDAO {
	
	public void saveOutForWorkObject(OutForWork outForWork );
	public List<OutForWork> getOutForWorkRequestList();
	public List<OutForWork> getOutForWorkApprovedList() ;
	public List<OutForWork> getOutForWorkRequestList(int id);

}
