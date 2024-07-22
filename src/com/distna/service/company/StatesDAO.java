package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.States;

public interface StatesDAO {
	
	public List<States> getStatesById(int id);

	public List<States> getStatesByStateId(int stateid);

	public List<States> getStates();

	
}
