package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.JobPositions;

public interface JobPositionDAO {
	
	public void saveJobPosition(JobPositions jobPositions);
	public List<JobPositions> getJobPositions();
	public void deleteJobPosition(int id);
	public List<JobPositions> getJobPositionsById(int id);

}
