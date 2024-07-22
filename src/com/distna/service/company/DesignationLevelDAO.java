package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Company;
import com.distna.domain.company.DesignationLevel;

public interface DesignationLevelDAO {
	public int getDesignationLevelCurrentId();
	public void saveDesignationLevel(DesignationLevel designationLevel);
	public List<DesignationLevel> getDesignationLevelList();
	public void deleteDesignationLevel(int id);
	public DesignationLevel getDesignationLevelById(int id);
	public void saveOrUpdateDesignationLevel(DesignationLevel designationLevel);
	public List<DesignationLevel> getDesignationLevel();
	public boolean getDesignationLevelforRank(int rank,int id);
	public List<DesignationLevel> getDesignationLevelforRank(int rank);
}
