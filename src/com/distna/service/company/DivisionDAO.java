package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Division;

public interface DivisionDAO {
	
	public void addDivision(Division division);
	
	public List<Division> getDivisionList();
	
	public void deleteDivision(int id);
	
	public Division getDivision(int id);
	
	public void updateDivision(Division division);
	

}
