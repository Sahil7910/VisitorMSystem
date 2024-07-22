package com.distna.service.leaves;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.leaves.Status;

public class StatusDAOImpl implements StatusDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getStatusByCategory(String category1) {
		
		//String category1="leaves";
		
		return hibernateTemplate.find("from Status where category='"+category1+"'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getLeaveStatus(int statusId) {
		// TODO Auto-generated method stub
		return  hibernateTemplate.find("from Status where id="+statusId);
	}

	
}
