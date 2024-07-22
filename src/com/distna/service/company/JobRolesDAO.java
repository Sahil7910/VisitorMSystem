package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.JobRoles;

public interface JobRolesDAO {
	public void saveOrUpdateJobRoles(JobRoles jobRoles);
	public List<JobRoles> getJobRolesList();
	public void deleteJobRoles(int id);
	public JobRoles getJobRolesListById(int id);
	

}
