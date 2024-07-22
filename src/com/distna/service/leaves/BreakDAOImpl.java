package com.distna.service.leaves;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.leaves.Breaks;

public class BreakDAOImpl implements BreakDAO{

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	
	@Override
	public void saveBreaks(Breaks breaks) {
		
		hibernateTemplate.saveOrUpdate(breaks);
	}

	@Override
	public List<Breaks> getBreaks() {
		
		return hibernateTemplate.find("from Breaks");
	}


	public void deleteBreak(int id) {
		List<Breaks> breakList=hibernateTemplate.find("from Breaks where id="+id);
		hibernateTemplate.deleteAll(breakList);
		
	}


	public List<Breaks> getBreakById(int id) {
		
	
		return hibernateTemplate.find("from Breaks where id="+id);
	}

	
	
}
