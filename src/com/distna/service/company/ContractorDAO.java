package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Contractor;

public interface ContractorDAO {
	public void saveContractor(Contractor contractor);
	public List<Contractor> getContractorList();
	public void deleteContractor(int id);
	public List<Contractor> getContractorList(int id);

}
