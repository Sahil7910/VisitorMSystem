package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.Employee;
import com.distna.domain.employee.Lists;

public interface ListsDAO {
	public List<Lists> getListsByCategory(String category);

}
