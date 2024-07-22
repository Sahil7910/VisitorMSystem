package com.distna.service.privileges;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.privileges.MasterPrivileges;

public class MasterPrivilegesDAOImpl implements MasterPrivilegesDAO {

	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	@Override
	public List<MasterPrivileges> getMasterPrivileges() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from MasterPrivileges");
	}
	@Override
	public List<MasterPrivileges> getMasterPrivilegesByPrivilege(
			String privilegeName) {
		return hibernateTemplate.find("from MasterPrivileges where privilegeName='"+privilegeName+"'");
	}
	@Override
	public void savaList(List<MasterPrivileges> masterPrivileges) {
		 hibernateTemplate.saveOrUpdateAll(masterPrivileges);
	}
	
	
}
