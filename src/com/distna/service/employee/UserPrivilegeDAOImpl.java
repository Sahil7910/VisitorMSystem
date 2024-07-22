package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.UserPrivilege;

public class UserPrivilegeDAOImpl implements UserPrivilegeDAO
{
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPrivilege> getUserPrivilegeList() {
		return hibernateTemplate.find("from UserPrivilege");
	}

}
