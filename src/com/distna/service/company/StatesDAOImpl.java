package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.States;

public class StatesDAOImpl implements StatesDAO {


	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public List<States> getStatesById(int id) {
		return hibernateTemplate.find("from States where countryId="+id) ;
	}

	@Override
	public List<States> getStatesByStateId(int stateid) {
		
		return hibernateTemplate.find("from States where stateId="+stateid) ;
	}

	@Override
	public List<States> getStates() {
		
		return hibernateTemplate.find("from States");
	}
	
	

}
