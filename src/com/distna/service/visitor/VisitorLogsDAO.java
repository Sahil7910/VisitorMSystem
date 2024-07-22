package com.distna.service.visitor;

import java.util.List;

import com.distna.domain.visitor.VisitorLogs;

public interface VisitorLogsDAO 
{
	public void saveVisitorLogs(VisitorLogs visitorLogs);
	public List<VisitorLogs> getVisitorLogsList();
	public List<VisitorLogs> getVisitorLogsList(int logId);
	public void updateVisitorLogs(VisitorLogs visitorLogs);
	public void deleteVisitorLogs(int logId);
	public void updateVisitorLogs(int logId);
	public List<VisitorLogs> getVisitorLogsListAccEmployee(int employeeId);
	public List<VisitorLogs> getVisitorLogsApprovedEmployeeList();
	public List<VisitorLogs> getVisitorLogsByVisitorName(String visitorName);
	
}
