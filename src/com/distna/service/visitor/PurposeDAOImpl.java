package com.distna.service.visitor;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.Purpose;

public class PurposeDAOImpl implements PurposeDAO {

	private HibernateTemplate hibernateTemplate;
	
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public List<Purpose> getPurposeList() {
		return hibernateTemplate.find("from Purpose");
	}

	@Override
	public void savePurposeList(Purpose purpose) {
		hibernateTemplate.saveOrUpdate(purpose);
	}

}
