package com.distna.service.calendar;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.CalNumbers;
import com.distna.domain.calendar.CalTimes;

public class CalTimesDAOImpl implements CalTimesDAO {
	
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void saveTime() {
	
		/*for (int y = 5; y <= 11; y++) {
			for (int i = 0; i < 60; i++) {
				CalTimes calTimes=new CalTimes();
				if (i % 5 == 0) {
					if(y==0)
					{
						if(i>=0&i<10)
						{
							calTimes.setTime("12:0"+i+":00 AM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time=12:0"+i+":00 AM");
						}
						else{				
							calTimes.setTime("12:"+i+":00 AM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time=12:"+i+":00 AM");
						}
					}
					else
					{
						if(i>=0&i<10)
						{
							calTimes.setTime(y+":0"+i+":00 AM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time="+y+":0"+i+":00 AM");
						}
						else{	
							calTimes.setTime(y+":"+i+":00 AM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time="+y+":"+i+":00 AM");
						}
					}
					
					
					
				}

			}
		}
		
		System.out.println("*****************************************");
		for (int y = 0; y <= 11; y++) {
			for (int i = 0; i < 60; i++) {
				CalTimes calTimes=new CalTimes();
				if (i % 5 == 0) {
					if(y==0)
					{
						if(i>=0&i<10)
						{
							calTimes.setTime("12:0"+i+":00 PM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time=12:0"+i+":00 PM");
						}
						else{		
							calTimes.setTime("12:"+i+":00 PM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time=12:"+i+":00 PM");
						}
					}
					else
					{
						if(i>=0&i<10)
						{
							calTimes.setTime(y+":0"+i+":00 PM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time="+y+":0"+i+":00 PM");
						}
						else{	
							calTimes.setTime(y+":"+i+":00 PM");
							hibernateTemplate.save(calTimes);
							//System.out.println("time="+y+":"+i+":00 PM");
						}
					}
				}

			}
		}
		
		
		System.out.println("hey");*/
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CalTimes> getCalTimes() {

		return hibernateTemplate.find("from CalTimes");	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CalNumbers> getCalNumbers(int days) {
		return hibernateTemplate.find("from CalNumbers where n<="+days+" and n <> 0");
	}
	

}
