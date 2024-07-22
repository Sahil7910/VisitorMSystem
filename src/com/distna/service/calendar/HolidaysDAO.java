package com.distna.service.calendar;

import java.util.List;

import com.distna.domain.calendar.Holidays;

public interface HolidaysDAO {
	
	public void saveHoliday(Holidays holidays);
	
	public List<Holidays> getHolidays();
	
	public void deleteHoliday(int id);
	
	public Holidays getHoliday(int id);
	
	public void updateHoliday(Holidays holidays);

}
