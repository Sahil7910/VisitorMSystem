package com.distna.service.calendar;

import java.util.List;

import com.distna.domain.calendar.CalNumbers;
import com.distna.domain.calendar.CalTimes;


public interface CalTimesDAO {
	public void saveTime();
	public  List<CalTimes> getCalTimes();
	public List<CalNumbers> getCalNumbers(int days);

}
