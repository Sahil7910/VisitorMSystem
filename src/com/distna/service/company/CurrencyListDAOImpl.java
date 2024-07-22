package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.CurrencyList;


public class CurrencyListDAOImpl implements CurrencyListDAO {
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyList> getCurrencyList() {
		
		return hibernateTemplate.find("from CurrencyList");
	}
	
	
}
