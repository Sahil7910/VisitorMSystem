package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Contractor;

public class ContractorDAOImpl implements ContractorDAO {

	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}
	@Override
	public void saveContractor(Contractor contractor) {
		hibernateTemplate.saveOrUpdate(contractor);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Contractor> getContractorList() {
		return hibernateTemplate.find("from Contractor");
	}
	@Override
	public void deleteContractor(int id) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from Contractor where contractorId="+id));
		
	}
	@Override
	public List<Contractor> getContractorList(int id) {
		return hibernateTemplate.find("from Contractor where contractorId="+id);
	}

}
