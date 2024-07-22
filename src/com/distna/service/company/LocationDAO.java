package com.distna.service.company;


import java.util.List;

import com.distna.domain.company.Location;

public interface LocationDAO {
	
	public void saveLocation(Location location);
	public List<Location> getLocation();
	public void deleteLocation(int id);
	public List<Location> getLocationById(int id);
	public Location getLocationObject(int id);
}
