package com.distna.service.visitor;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.VisitorGates;

public class VisitorGatesDAOImpl implements VisitorGatesDAO {

private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public List<VisitorGates> getVisitorGatesList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from VisitorGates");
	}

	@Override
	public void saveVisitorGatesList(VisitorGates visitorGates) {
		
		hibernateTemplate.saveOrUpdate(visitorGates);
	}

}
