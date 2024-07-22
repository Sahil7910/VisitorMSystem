package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Zones;

public class ZonesDAOImpl implements ZonesDAO {

	
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public List<Zones> getZonesList() {
		return hibernateTemplate.find("from Zones");
	}

}
