package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.CanteenTimings;

public interface CanteenTimingsDAO {
	
	public void saveCanteenTimings(CanteenTimings canteenTimings);
	
	public List<CanteenTimings> getCanteenTimingsList();
	
	public CanteenTimings getCanteenTiming(int timingId);
	
	public void updateCanteenTimings(CanteenTimings canteenTimings);
	
	public void deleteCanteenTimings(int timingId);

}
