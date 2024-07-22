package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Designation;

public interface DesignationDAO {
	
	public void saveDesignation(Designation designation);
	public List<Designation> getDesignation();
	public void deleteDesignation(int id);
	public List<Designation> getDesignationById(int parentid);
	

}
