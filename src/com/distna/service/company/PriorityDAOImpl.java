package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Priority;

public class PriorityDAOImpl implements PriorityDAO {

	
	HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Priority> getPriorityList() {
		
		return hibernateTemplate.find("from Priority");
	}

	@Override
	public void savePriority(Priority priority) {
		hibernateTemplate.saveOrUpdate(priority);
		
	}

	

}
