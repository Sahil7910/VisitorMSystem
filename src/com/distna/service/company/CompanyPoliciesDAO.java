package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.CompanyPolicies;

public interface CompanyPoliciesDAO {
	public void saveOrUpdateCompanyPolicies(CompanyPolicies companyPolicies);
	public  List<CompanyPolicies> getCompanyPolicies();
	public void deleteCompanyPolicies(int id);
	public CompanyPolicies getCompanyPoliciesById(int id);
}
