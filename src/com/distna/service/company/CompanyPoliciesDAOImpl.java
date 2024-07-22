package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.CompanyPolicies;
import com.distna.domain.company.JobRoles;

public class CompanyPoliciesDAOImpl implements CompanyPoliciesDAO {
@SuppressWarnings("unused")
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void saveOrUpdateCompanyPolicies(CompanyPolicies companyPolicies) {
		hibernateTemplate.saveOrUpdate(companyPolicies);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPolicies> getCompanyPolicies() {
	 return	hibernateTemplate.find("from CompanyPolicies");
	}
	@SuppressWarnings("unchecked")
	@Override
	public void deleteCompanyPolicies(int id) {
		List<CompanyPolicies> companyPoliciesList=hibernateTemplate.find("from CompanyPolicies where policyId="+id);
		hibernateTemplate.deleteAll(companyPoliciesList);
	}
	@SuppressWarnings("unchecked")
	@Override
	public CompanyPolicies getCompanyPoliciesById(int id) {
		List<CompanyPolicies> companyPoliciesList=hibernateTemplate.find("from CompanyPolicies where policyId="+id);
		if(companyPoliciesList.size()!=0)
		{
			return companyPoliciesList.get(0);
		}	
		else
		{
			return null;
		}
		
	}
	
}
