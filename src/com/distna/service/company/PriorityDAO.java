package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Priority;

public interface PriorityDAO {
	public List<Priority> getPriorityList();
	public void savePriority(Priority priority);

}
