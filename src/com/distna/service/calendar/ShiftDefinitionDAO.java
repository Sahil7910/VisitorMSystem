package com.distna.service.calendar;

import java.util.List;

import com.distna.domain.calendar.CalCategory;
import com.distna.domain.calendar.ShiftDefinition;

public interface ShiftDefinitionDAO 
{
	public List<CalCategory> getCategoryList();
	public List<ShiftDefinition> getShiftDefinitions() ;
	public List<ShiftDefinition> getShiftDefinitionsByShiftId(int shiftId);
	public void saveShiftDefinitions(ShiftDefinition shiftDefinition);
	public ShiftDefinition getShiftDefinitionObjByShiftId(int shiftId);
	
	public List getShiftBreakTime(int employeeNo);
	
	public List<ShiftDefinition>getshftbreakList(int shiftid);
}
