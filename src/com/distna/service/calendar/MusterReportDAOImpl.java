package com.distna.service.calendar;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.MusterReport;

public class MusterReportDAOImpl implements MusterReportDAO {

	
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void saveMusterReport(MusterReport musterReport) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(musterReport);
	}
	

}
