package com.distna.service.devicemanagement;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.devicemanagement.AllowedDevices;

public class AllowedDevicesDAOImpl implements AllowedDevicesDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}

		@Override
		public List<AllowedDevices> getAllowedDevices() {
			
			return hibernateTemplate.find("from AllowedDevices");
		}

}
