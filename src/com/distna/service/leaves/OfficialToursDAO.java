package com.distna.service.leaves;

import java.util.List;
import java.util.Map;
import com.distna.domain.leaves.OfficialTours;

public interface OfficialToursDAO 
{
	public void saveOfficialTour(OfficialTours officialTours);
	public List<OfficialTours> getOfficialToursList();
	public void deleteOfficialTour(int id);
	public List<OfficialTours> getOfficialToursList(int id);
	public void saveOrUpdateOfficialTour(OfficialTours officialTours);
}
