package com.distna.service.calendar;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.Holidays;

public class HolidaysDAOImpl implements HolidaysDAO{

	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public void saveHoliday(Holidays holidays) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(holidays);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Holidays> getHolidays() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Holidays");
	}


	@Override
	public void deleteHoliday(int id) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from Holidays where id="+id));
	}


	@Override
	public Holidays getHoliday(int id) {
		// TODO Auto-generated method stub
		Holidays holidays=(Holidays) hibernateTemplate.find("from Holidays where id="+id).get(0);
		return holidays;
	}


	@Override
	public void updateHoliday(Holidays holidays) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(holidays);
	}

}
