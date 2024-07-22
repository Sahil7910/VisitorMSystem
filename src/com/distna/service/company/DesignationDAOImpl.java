package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Departments;
import com.distna.domain.company.Designation;
import com.distna.domain.company.DesignationLevel;
import com.distna.domain.company.JobPositions;
import com.distna.domain.company.Workspaces;

public class DesignationDAOImpl implements DesignationDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public void saveDesignation(Designation designation) {
		hibernateTemplate.saveOrUpdate(designation);
	}


	@Override
	public List<Designation> getDesignation() {
		
		return hibernateTemplate.find("from Designation");
	}


	@Override
	public void deleteDesignation(int id) {
		List<Designation> designations=hibernateTemplate.find("from Designation where id="+id);
		hibernateTemplate.deleteAll(designations);
		
		List<JobPositions> jobPositionList=hibernateTemplate.find("from JobPositions where designation="+id);
		if(jobPositionList.size()!=0)
		{
			for(JobPositions jobPositions:jobPositionList)
			{
				jobPositions.setDesignation(0);
			}
			hibernateTemplate.saveOrUpdateAll(jobPositionList);
		}
		
		List<Workspaces> workspaceList=hibernateTemplate.find("from Workspaces where designation="+id);
		if(workspaceList.size()!=0)
		{
			for(Workspaces workspaces:workspaceList)
			{
				workspaces.setDesignation(0);
			}
			hibernateTemplate.saveOrUpdateAll(workspaceList);
		}
		
		
	}


	@Override
	public List<Designation> getDesignationById(int parentid) {
		
		return hibernateTemplate.find("from Designation where id="+parentid);
	}
	
	
	
	
	
	
	
}
