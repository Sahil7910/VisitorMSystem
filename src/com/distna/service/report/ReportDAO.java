
package com.distna.service.report;
import java.sql.ResultSet;


public interface ReportDAO 
{
	public ResultSet getAttendanceLogsBulkEntryList(int workId,String dateFrom,String dateTo,String empReportType,String selectedcheckbox);
	
	public ResultSet getDepartmentWiseEmployeeList(int workId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox,int departmentId);
	
	public ResultSet getDailyAttendanceReport(int employeeId, String dateFrom, String dateTo, String shiftStartTime, String shiftEndTime);
	
	public ResultSet getEmployeePersonalDetailsReport();
	public String getShiftName(int shiftid);
	
	public ResultSet getLeaveReport(int workId, int departmentId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox);
	
	public ResultSet getShiftWiseEmployeeList(int shiftId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox,int workId,String reportType,String allowedTime);
	
	public ResultSet getMonthlyAttendanceOfEmployee(int employeeId,String month,String year,String empReportType,String selectedcheckbox);
	
	public ResultSet getmusterReportOfEmployees(int shiftId,String month,String year);
	
	public ResultSet getAttendanceLogsBulkEntryExceptionList(int workId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox);
	
	public ResultSet getLeaveStatusReport(int workId, int departmentId, String empReportType, String selectedcheckbox, int leaveType);
	
	public ResultSet getEmployeeWiseAllList(int workId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox);
	
	public ResultSet getEmployeeTableForExport();

	public boolean importEmployeeTableData(String filePath);

	public ResultSet getDepartmentSummaryReport(int department_id, String date);

	public ResultSet getMaharastraMusterRollReport(int shiftId,int workId,String empReportType,String selectedcheckbox,String month,String year);

	public ResultSet getDepartmentWiseOutForWork(int workId, String dateFrom,String dateTo, String empReportType, String selectedcheckbox,int departmentId);
	
	public ResultSet getBasicWorkReport(int shiftId, String month,String year);
	
	public ResultSet getBasicWorkDetailReport(int shiftId, String month,String year,String grace_time );
	
	public ResultSet getMemoReport(int employeeId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox,int departmentId);
	
	public ResultSet getExtraWorkReport(int employeeId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox,int departmentId);
	
	
	public ResultSet getShortWorkReport(int employeeId, String dateFrom, String dateTo,String empReportType,String selectedcheckbox,int departmentId);
	
	
	public ResultSet getCanteenItemsReport();
	
	public ResultSet getCanteenTimingsReport();
	
	public ResultSet getVisitorDailyReport(String reportType, int visitorId, int employeeId, String fromDate, String toDate);
	
	public ResultSet getVisitorFrequencyReport(int visitorId, String selectedcheckbox, String visitorReportType);
	
	public ResultSet getVehicleLogsWoPhotoReport(String dateFrom,String dateTo,String concernPerson,String status,String filter,String vehicleNumber,String vehicleType);
	
	public ResultSet getVehicleFrequencyLogs(String dateFrom, String dateTo,String concernPerson, String status, String filter,String vehicleNumber, String vehicleType);
	
	
	public ResultSet getTableForExport(String tableName);
	
	public boolean importTableData(String filePath);

	//ResultSet getVisitorFrequencyReport(int visitorId, String selectedcheckbox, String visitorReportType,	);
	
	

	
}

