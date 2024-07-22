package com.distna.service.company;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.distna.domain.company.Cities;
import com.distna.domain.company.States;

public class CitiesDAOImpl implements CitiesDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public void saveCities(Cities cities) {
		
		hibernateTemplate.saveOrUpdate(cities);
	}


	@Override
	public List<Cities> getCitiesById(int id) {
		
		
		
		return hibernateTemplate.find("from Cities where state_id="+id);
	}


	@Override
	public List<Cities> getCitiesByCityId(int cityid) {
		
		return hibernateTemplate.find("from Cities where CityId="+cityid);
	}


	@Override
	public List<Cities> getCities() {
		
		return hibernateTemplate.find("from Cities");
	}
	
	
	

	

}
