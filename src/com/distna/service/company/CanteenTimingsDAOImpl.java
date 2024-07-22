package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.CanteenTimings;

public class CanteenTimingsDAOImpl implements CanteenTimingsDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}

	@Override
	public void saveCanteenTimings(CanteenTimings canteenTimings) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(canteenTimings);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenTimings> getCanteenTimingsList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from CanteenTimings");
	}

	@SuppressWarnings("unchecked")
	@Override
	public CanteenTimings getCanteenTiming(int timingId) {
		// TODO Auto-generated method stub
		List<CanteenTimings> canteenTimingsList=hibernateTemplate.find("from CanteenTimings where timingId="+timingId);
		if(canteenTimingsList.size()>0)
		{
			return canteenTimingsList.get(0);
		}
		
		return null;
	}

	@Override
	public void updateCanteenTimings(CanteenTimings canteenTimings) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(canteenTimings);
	}

	@Override
	public void deleteCanteenTimings(int timingId) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from CanteenTimings where timingId="+timingId));
		
	}

}
