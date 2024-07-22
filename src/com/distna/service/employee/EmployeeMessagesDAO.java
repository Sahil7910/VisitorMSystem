package com.distna.service.employee;

import java.util.List;

import com.distna.domain.employee.EmployeeMessages;

public interface EmployeeMessagesDAO {
	
	public void saveEmployeeMessages(EmployeeMessages employeeMessages);
	public List<EmployeeMessages> getEmployeeMessagesByEmployeeId(int employeeId);
	public EmployeeMessages getEmployeeMessagesById(int employeeId);
	public void changeEmployeeMessagesStatus(int messageId);
	public int getUnreadEmployeeMessagesCount(int employeeId);
}
