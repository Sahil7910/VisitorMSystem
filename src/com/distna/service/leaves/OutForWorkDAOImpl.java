package com.distna.service.leaves;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.leaves.OutForWork;

public class OutForWorkDAOImpl implements OutForWorkDAO {
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void saveOutForWorkObject(OutForWork outForWork) {
		hibernateTemplate.saveOrUpdate(outForWork);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutForWork> getOutForWorkRequestList() {
		 return hibernateTemplate.find("from OutForWork where status='Pending'");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OutForWork> getOutForWorkApprovedList() {
		 return hibernateTemplate.find("from OutForWork where status='Approved'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutForWork> getOutForWorkRequestList(int id) {
		// TODO Auto-generated method stub
		 return hibernateTemplate.find("from OutForWork where id="+id);
	}

}
