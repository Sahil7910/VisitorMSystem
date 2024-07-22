package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.MasterSettings;

public class MasterSettingsDAOImpl implements MasterSettingsDAO
{
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveOrUpdateMasterSettings(MasterSettings masterSettings)
	{
		hibernateTemplate.saveOrUpdate(masterSettings);
	}

	@Override
	public List<MasterSettings> getMasterSettings() {
		return hibernateTemplate.find("from MasterSettings");
	}
	
}
