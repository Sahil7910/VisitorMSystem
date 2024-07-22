package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Company;

public interface CompanyDAO {
	public int getCompanyCurrentId();
	public void saveOrUpdateCompany(Company company);
	public void saveCompany(Company company);
	public List<Company> getCompanies();
	public void deleteCompany(int Id);
	public Company getCompanyById(int id);
	public int getCompanyCount();
}
