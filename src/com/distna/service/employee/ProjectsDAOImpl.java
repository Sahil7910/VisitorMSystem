package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Projects;

public class ProjectsDAOImpl implements ProjectsDAO {

	
	
@SuppressWarnings("unused")
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void saveEmployeeProjects(Projects projects) {
		hibernateTemplate.saveOrUpdate(projects);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Projects> getEmployeeProjects() {
		return hibernateTemplate.find("from Projects");
	}
	@Override
	public Projects getEmployeeProjects(int id) {
		List<Projects> projectsList=hibernateTemplate.find("from Projects where id="+id);
		if(projectsList.size()!=0)
		{
			return  projectsList.get(0);
		}
		else
			return null;
	}
	@Override
	public void deleteEmployeeProjects(int id) {
		@SuppressWarnings("unchecked")
		List<Projects> projectsList=hibernateTemplate.find("from Projects where id="+id);
		hibernateTemplate.deleteAll(projectsList);
		
	}

}
