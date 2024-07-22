package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Cities;

public interface CitiesDAO {
	
	public void saveCities(Cities cities);

	public List<Cities> getCitiesById(int id);

	public List<Cities> getCitiesByCityId(int cityid);

	public List<Cities> getCities();

}
