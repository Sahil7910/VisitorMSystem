package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Company;
import com.distna.domain.company.JobRoles;

public class JobRolesDAOImpl implements JobRolesDAO {
	public HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory)
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveOrUpdateJobRoles(JobRoles jobRoles) {
		hibernateTemplate.saveOrUpdate(jobRoles);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobRoles> getJobRolesList() {
		return hibernateTemplate.find("from JobRoles");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteJobRoles(int id) {
		List<JobRoles> jobRolesList=hibernateTemplate.find("from JobRoles where id="+id);
		if(jobRolesList.size()!=0)
		{
			hibernateTemplate.delete(jobRolesList.get(0));
		}	
		
	}

	@Override
	public JobRoles getJobRolesListById(int id) {
		List<JobRoles> jobRolesList=hibernateTemplate.find("from JobRoles where id="+id);
		if(jobRolesList.size()!=0)
		{
			return jobRolesList.get(0);
		}	
		else
		{
			return null;
		}
	}
	
	

}
