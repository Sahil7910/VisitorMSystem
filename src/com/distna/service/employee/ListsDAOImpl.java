package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Lists;

public class ListsDAOImpl implements ListsDAO {
	
@SuppressWarnings("unused")
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lists> getListsByCategory(String category) {
		
		return hibernateTemplate.find("from Lists where category='"+category+"'");
	}

}
