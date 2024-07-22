package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.DesignationLevel;
import com.distna.domain.company.Workspaces;

public class WorkspacesDAOImpl implements WorkspacesDAO {

private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveWorkspace(Workspaces workspaces) {
		hibernateTemplate.save(workspaces);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Workspaces> getAllWorkspaces() {
		return hibernateTemplate.find("from Workspaces");
	}

	@Override
	public void deleteWorkspace(int id) {
		List<DesignationLevel> designationLevelList=hibernateTemplate.find("from Workspaces where id="+id);
		if(designationLevelList.size()!=0)
		{
			hibernateTemplate.delete(designationLevelList.get(0));
		}	
		
	}

	@Override
	public Workspaces getWorkspaceById(int id) {
		List<Workspaces> workspacesList=hibernateTemplate.find("from Workspaces where id="+id);
		if(workspacesList.size()!=0)
		{
			return workspacesList.get(0);
		}	
		else
		{
			return null;
		}
	}

	@Override
	public void saveOrUpdateWorkspace(Workspaces workspaces) {
		hibernateTemplate.saveOrUpdate(workspaces);
		
	}
}
