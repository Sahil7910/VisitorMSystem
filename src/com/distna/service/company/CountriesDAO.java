package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Countries;



public interface CountriesDAO {
	public List<Countries> getCountriesList();
	//public void saveCountries();

	public List<Countries> getCountryById(int countryid);
}
