package com.distna.service.leaves;

import java.util.List;

import com.distna.domain.leaves.Breaks;

public interface BreakDAO {
	
	public void saveBreaks(Breaks breaks);
	public List<Breaks> getBreaks();
	public void deleteBreak(int id);
	public List<Breaks> getBreakById(int id);

}
