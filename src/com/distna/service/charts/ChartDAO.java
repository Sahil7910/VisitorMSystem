package com.distna.service.charts;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface ChartDAO {
	
	public Map<Integer, Integer> getLeavesPerLeaveType();

}
