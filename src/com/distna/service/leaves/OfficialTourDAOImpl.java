package com.distna.service.leaves;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.distna.domain.leaves.OfficialTours;

public class OfficialTourDAOImpl implements OfficialToursDAO
{
	
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveOfficialTour(OfficialTours officialTours) 
	{
		hibernateTemplate.save(officialTours);
	}

	@Override
	public List<OfficialTours> getOfficialToursList() 
	{
		return hibernateTemplate.find("from OfficialTours");
	}

	@Override
	public void deleteOfficialTour(int id) 
	{
		List<OfficialTours> officialToursList=getOfficialToursList(id);
		hibernateTemplate.deleteAll(officialToursList);
	}

	@Override
	public List<OfficialTours> getOfficialToursList(int id) 
	{
		return hibernateTemplate.find("from OfficialTours where id="+id);
	}

	@Override
	public void saveOrUpdateOfficialTour(OfficialTours officialTours) 
	{
		hibernateTemplate.saveOrUpdate(officialTours);
	}


}
