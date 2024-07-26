    package com.distna.service.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.DateRange;
import com.distna.domain.calendar.MusterReport;
import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;
import com.distna.domain.calendar.ShiftMaster;
import com.distna.domain.employee.AttendanceLogsBulkEntry;
import com.distna.domain.employee.Employee;
import com.distna.domain.leaves.LeaveApplication;

public class ReportDAOImpl implements ReportDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ResultSet getAttendanceLogsBulkEntryList(int workId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox) {
		ResultSet rs = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		DateFormat dateFormatNormalFormat = new SimpleDateFormat("d-M-yyyy");
		Date currentDate = Calendar.getInstance().getTime();
		String currentDateString = dateFormatNormalFormat.format(currentDate);
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();

			ps = connection.prepareStatement("TRUNCATE TABLE attendance_logs_bulk_entry_report");
			ps.executeUpdate();

			ps = connection.prepareStatement(
					"INSERT INTO attendance_logs_bulk_entry_report SELECT * FROM attendance_logs_bulk_entry");
			ps.executeUpdate();

			if (empReportType.equals("single")) {
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate
						.find("from ShiftAllocation where employee_id=" + workId);
				int shiftId = 0;
				String weekends = "";

				if (shiftAllocationList.size() != 0) {
					shiftId = shiftAllocationList.get(0).getShiftid();
					List<ShiftDefinition> definitionsList = hibernateTemplate
							.find("from ShiftDefinition where shiftid=" + shiftId);
					for (ShiftDefinition shiftDefinition : definitionsList) {
						if (shiftDefinition.isWeeklyOff1()) {
							weekends += shiftDefinition.getWeeklyOff1Day();
						}
						if (shiftDefinition.isWeeklyOff2()) {
							weekends += "," + shiftDefinition.getWeeklyOff2Day();
						}
					}
					List<LeaveApplication> leaveApplicationsList = hibernateTemplate
							.find("from LeaveApplication where leaveStatus=3 and employee_id =" + workId);
					for (LeaveApplication leaveApplication : leaveApplicationsList) {
						if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate()).getTime() < currentDate
								.getTime()) {
							Date leaveDateFromObj = dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate());
							String leaveDateFromString = dateFormat.format(leaveDateFromObj);
							Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
							String leaveDateToString = dateFormat.format(leaveDateToObj);
							ps = connection.prepareStatement("call datespopulate('" + leaveDateFromString + "','"
									+ leaveDateToString + "', " + workId + "," + shiftId + ",'" + weekends + "')");
							ps.executeQuery();
						}
					}

					Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
					String dateFromString = dateFormat.format(dateFromObj);
					Date dateToObj = dateFormatNormalFormat.parse(dateTo);
					String dateToString = dateFormat.format(dateToObj);
					ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
							+ dateToString + "', " + workId + "," + shiftId + ",'" + weekends + "')");
					ps.executeQuery();
				}
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
						+ workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
				/*
				 * else {
				 * query="select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
				 * +workId+" and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
				 * +dateFrom+"','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
				 * +dateTo+"','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')"
				 * ; }
				 */
			} else if (empReportType.equals("multiple")) {

				String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate
						.find("from ShiftAllocation where employee_id in (" + selectedEmployees + ")");
				int shiftId = 0;
				int currentEmployeeId = 0;
				if (shiftAllocationList.size() != 0) {
					for (ShiftAllocation shiftAllocation : shiftAllocationList) {
						String weekends = "";
						shiftId = shiftAllocation.getShiftid();
						currentEmployeeId = shiftAllocation.getEmployee_id();
						List<ShiftDefinition> definitionsList = hibernateTemplate
								.find("from ShiftDefinition where shiftid=" + shiftId);
						for (ShiftDefinition shiftDefinition : definitionsList) {
							if (shiftDefinition.isWeeklyOff1()) {
								weekends += shiftDefinition.getWeeklyOff1Day();
							}
							if (shiftDefinition.isWeeklyOff2()) {
								weekends += "," + shiftDefinition.getWeeklyOff2Day();
							}
						}
						List<LeaveApplication> leaveApplicationsList = hibernateTemplate.find(
								"from LeaveApplication where leaveStatus=3 and employee_id =" + currentEmployeeId);
						for (LeaveApplication leaveApplication : leaveApplicationsList) {
							if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate())
									.getTime() > currentDate.getTime()) {
								Date leaveDateFromObj = dateFormatNormalFormat
										.parse(leaveApplication.getApproveFromdate());
								String leaveDateFromString = dateFormat.format(leaveDateFromObj);
								Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
								String leaveDateToString = dateFormat.format(leaveDateToObj);
								ps = connection.prepareStatement(
										"call datespopulate('" + leaveDateFromString + "','" + leaveDateToString + "', "
												+ currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
								ps.executeQuery();
							}
						}
						Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
						String dateFromString = dateFormat.format(dateFromObj);
						Date dateToObj = dateFormatNormalFormat.parse(dateTo);
						String dateToString = dateFormat.format(dateToObj);
						ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
								+ dateToString + "', " + currentEmployeeId + "," + shiftId + " ,'" + weekends + "')");
						ps.executeQuery();
					}
				}
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID in("
						+ selectedEmployees + ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			} else {
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate.find("from ShiftAllocation");
				int shiftId = 0;
				int currentEmployeeId = 0;
				if (shiftAllocationList.size() != 0) {
					for (ShiftAllocation shiftAllocation : shiftAllocationList) {
						String weekends = "";
						shiftId = shiftAllocation.getShiftid();
						currentEmployeeId = shiftAllocation.getEmployee_id();
						List<ShiftDefinition> definitionsList = hibernateTemplate
								.find("from ShiftDefinition where shiftid=" + shiftId);
						for (ShiftDefinition shiftDefinition : definitionsList) {
							if (shiftDefinition.isWeeklyOff1()) {
								weekends += shiftDefinition.getWeeklyOff1Day();
							}
							if (shiftDefinition.isWeeklyOff2()) {
								weekends += "," + shiftDefinition.getWeeklyOff2Day();
							}
						}
						List<LeaveApplication> leaveApplicationsList = hibernateTemplate.find(
								"from LeaveApplication where leaveStatus=3 and employee_id =" + currentEmployeeId);
						for (LeaveApplication leaveApplication : leaveApplicationsList) {
							if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate())
									.getTime() > currentDate.getTime()) {
								Date leaveDateFromObj = dateFormatNormalFormat
										.parse(leaveApplication.getApproveFromdate());
								String leaveDateFromString = dateFormat.format(leaveDateFromObj);
								Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
								String leaveDateToString = dateFormat.format(leaveDateToObj);
								ps = connection.prepareStatement(
										"call datespopulate('" + leaveDateFromString + "','" + leaveDateToString + "', "
												+ currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
								ps.executeQuery();
							}
						}
						Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
						String dateFromString = dateFormat.format(dateFromObj);
						Date dateToObj = dateFormatNormalFormat.parse(dateTo);
						String dateToString = dateFormat.format(dateToObj);
						ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
								+ dateToString + "', " + currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
						ps.executeQuery();
					}
				}
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
						+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			}
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			// rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	@Override
	public ResultSet getEmployeeWiseAllList(int workId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox) {
		ResultSet rs = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		DateFormat dateFormatNormalFormat = new SimpleDateFormat("d-M-yyyy");
		Date currentDate = Calendar.getInstance().getTime();
		String currentDateString = dateFormatNormalFormat.format(currentDate);
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();

			ps = connection.prepareStatement("TRUNCATE TABLE attendance_logs_bulk_entry_report");
			ps.executeUpdate();

			ps = connection.prepareStatement(
					"INSERT INTO attendance_logs_bulk_entry_report SELECT * FROM attendance_logs_bulk_entry");
			ps.executeUpdate();

			if (empReportType.equals("single")) {
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate
						.find("from ShiftAllocation where employee_id=" + workId);
				int shiftId = 0;
				String weekends = "";
				if (shiftAllocationList.size() != 0) {
					shiftId = shiftAllocationList.get(0).getShiftid();
					List<ShiftDefinition> definitionsList = hibernateTemplate
							.find("from ShiftDefinition where shiftid=" + shiftId);
					for (ShiftDefinition shiftDefinition : definitionsList) {
						if (shiftDefinition.isWeeklyOff1()) {
							weekends += shiftDefinition.getWeeklyOff1Day();
						}
						if (shiftDefinition.isWeeklyOff2()) {
							weekends += "," + shiftDefinition.getWeeklyOff2Day();
						}
					}
					List<LeaveApplication> leaveApplicationsList = hibernateTemplate
							.find("from LeaveApplication where leaveStatus=3 and employee_id =" + workId);
					for (LeaveApplication leaveApplication : leaveApplicationsList) {
						if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate()).getTime() < currentDate
								.getTime()) {
							Date leaveDateFromObj = dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate());
							String leaveDateFromString = dateFormat.format(leaveDateFromObj);
							Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
							String leaveDateToString = dateFormat.format(leaveDateToObj);
							ps = connection.prepareStatement("call datespopulate('" + leaveDateFromString + "','"
									+ leaveDateToString + "', " + workId + "," + shiftId + ",'" + weekends + "')");
							ps.executeQuery();
						}
					}

					Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
					String dateFromString = dateFormat.format(dateFromObj);
					Date dateToObj = dateFormatNormalFormat.parse(dateTo);
					String dateToString = dateFormat.format(dateToObj);
					ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
							+ dateToString + "', " + workId + "," + shiftId + ",'" + weekends + "')");
					ps.executeQuery();
				}
				query = "select A.WorkID,A.Recorddate,REPLACE(A.timeAsPerShftTimings, '~', '      ') as Punches,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
						+ workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			} else if (empReportType.equals("multiple")) {

				String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate
						.find("from ShiftAllocation where employee_id in (" + selectedEmployees + ")");
				int shiftId = 0;
				int currentEmployeeId = 0;
				if (shiftAllocationList.size() != 0) {
					for (ShiftAllocation shiftAllocation : shiftAllocationList) {
						String weekends = "";
						shiftId = shiftAllocation.getShiftid();
						currentEmployeeId = shiftAllocation.getEmployee_id();
						List<ShiftDefinition> definitionsList = hibernateTemplate
								.find("from ShiftDefinition where shiftid=" + shiftId);
						for (ShiftDefinition shiftDefinition : definitionsList) {
							if (shiftDefinition.isWeeklyOff1()) {
								weekends += shiftDefinition.getWeeklyOff1Day();
							}
							if (shiftDefinition.isWeeklyOff2()) {
								weekends += "," + shiftDefinition.getWeeklyOff2Day();
							}
						}
						List<LeaveApplication> leaveApplicationsList = hibernateTemplate.find(
								"from LeaveApplication where leaveStatus=3 and employee_id =" + currentEmployeeId);
						for (LeaveApplication leaveApplication : leaveApplicationsList) {
							if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate())
									.getTime() > currentDate.getTime()) {
								Date leaveDateFromObj = dateFormatNormalFormat
										.parse(leaveApplication.getApproveFromdate());
								String leaveDateFromString = dateFormat.format(leaveDateFromObj);
								Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
								String leaveDateToString = dateFormat.format(leaveDateToObj);
								ps = connection.prepareStatement(
										"call datespopulate('" + leaveDateFromString + "','" + leaveDateToString + "', "
												+ currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
								ps.executeQuery();
							}
						}
						Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
						String dateFromString = dateFormat.format(dateFromObj);
						Date dateToObj = dateFormatNormalFormat.parse(dateTo);
						String dateToString = dateFormat.format(dateToObj);
						ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
								+ dateToString + "', " + currentEmployeeId + "," + shiftId + " ,'" + weekends + "')");
						ps.executeQuery();
					}
				}
				query = "select A.WorkID,A.Recorddate,REPLACE(A.timeAsPerShftTimings, '~', '      ') as Punches,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID in("
						+ selectedEmployees + ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			} else {
				List<ShiftAllocation> shiftAllocationList = hibernateTemplate.find("from ShiftAllocation");
				int shiftId = 0;
				int currentEmployeeId = 0;
				if (shiftAllocationList.size() != 0) {
					for (ShiftAllocation shiftAllocation : shiftAllocationList) {
						String weekends = "";
						shiftId = shiftAllocation.getShiftid();
						currentEmployeeId = shiftAllocation.getEmployee_id();
						List<ShiftDefinition> definitionsList = hibernateTemplate
								.find("from ShiftDefinition where shiftid=" + shiftId);
						for (ShiftDefinition shiftDefinition : definitionsList) {
							if (shiftDefinition.isWeeklyOff1()) {
								weekends += shiftDefinition.getWeeklyOff1Day();
							}
							if (shiftDefinition.isWeeklyOff2()) {
								weekends += "," + shiftDefinition.getWeeklyOff2Day();
							}
						}
						List<LeaveApplication> leaveApplicationsList = hibernateTemplate.find(
								"from LeaveApplication where leaveStatus=3 and employee_id =" + currentEmployeeId);
						for (LeaveApplication leaveApplication : leaveApplicationsList) {
							if (dateFormatNormalFormat.parse(leaveApplication.getApproveFromdate())
									.getTime() > currentDate.getTime()) {
								Date leaveDateFromObj = dateFormatNormalFormat
										.parse(leaveApplication.getApproveFromdate());
								String leaveDateFromString = dateFormat.format(leaveDateFromObj);
								Date leaveDateToObj = dateFormatNormalFormat.parse(leaveApplication.getApproveTodate());
								String leaveDateToString = dateFormat.format(leaveDateToObj);
								ps = connection.prepareStatement(
										"call datespopulate('" + leaveDateFromString + "','" + leaveDateToString + "', "
												+ currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
								ps.executeQuery();
							}
						}
						Date dateFromObj = dateFormatNormalFormat.parse(dateFrom);
						String dateFromString = dateFormat.format(dateFromObj);
						Date dateToObj = dateFormatNormalFormat.parse(dateTo);
						String dateToString = dateFormat.format(dateToObj);
						ps = connection.prepareStatement("call datespopulateAbsent('" + dateFromString + "', '"
								+ dateToString + "', " + currentEmployeeId + "," + shiftId + ",'" + weekends + "')");
						ps.executeQuery();
					}
				}
				query = "select A.WorkID,A.Recorddate,REPLACE(A.timeAsPerShftTimings, '~', '      ') as Punches,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry_report A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
						+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			}
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			// rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	@Override
	public ResultSet getAttendanceLogsBulkEntryExceptionList(int workId, String dateFrom, String dateTo,
			String empReportType, String selectedcheckbox) {
		ResultSet rs = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			if (empReportType.equals("single")) {
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
						+ workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') and mod(LENGTH(A.timeAsPerShftTimings)-LENGTH(REPLACE(A.timeAsPerShftTimings,'~','')),2)=0 order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			} else if (empReportType.equals("multiple")) {
				String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID in("
						+ selectedEmployees + ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
						+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') and mod(LENGTH(A.timeAsPerShftTimings)-LENGTH(REPLACE(A.timeAsPerShftTimings,'~','')),2)=0 order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			} else {
				query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,IF(SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1) = SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1), 'A',SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
						+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
						+ "','%d-%m-%Y') and mod(LENGTH(A.timeAsPerShftTimings)-LENGTH(REPLACE(A.timeAsPerShftTimings,'~','')),2)=0 order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
			}
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			// rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	@Override
	public ResultSet getDepartmentWiseEmployeeList(int workId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int departmentId) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
			query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn, if(substring_index(A.timeAsPerShftTimings,'~',1) = substring_index(A.timeAsPerShftTimings,'~',-1),'A', substring_index(A.timeAsPerShftTimings,'~',-1) ) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
					+ workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn, if(substring_index(A.timeAsPerShftTimings,'~',1) = substring_index(A.timeAsPerShftTimings,'~',-1),'A', substring_index(A.timeAsPerShftTimings,'~',-1) ) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID in("
					+ selectedEmployees + ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		} else {
			query = "select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn, if(substring_index(A.timeAsPerShftTimings,'~',1) = substring_index(A.timeAsPerShftTimings,'~',-1),'A', substring_index(A.timeAsPerShftTimings,'~',-1) ) as CheckOut,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') and E.department_id=" + departmentId
					+ " order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		}

		// query="select first_name, last_name, email, date_of_birth from employees
		// where department_id="+departmentId;
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getDepartmentWiseOutForWork(int workId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int departmentId) {
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
			query = "select A.WorkID,A.Recorddate,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_outdoor_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID="
					+ workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "select A.WorkID,A.Recorddate,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_outdoor_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where A.WorkID in("
					+ selectedEmployees + ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		} else {
			query = "select A.WorkID,A.Recorddate,S.shiftname,E.first_name as FirstName,E.last_name as LastName from attendance_outdoor_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y') and E.department_id=" + departmentId
					+ " order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		}

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	
	
	@Override
	public String getShiftName(int shifti) {
		String shiftn = null;
		;
		List<ShiftMaster> shift = hibernateTemplate.find("from ShiftMaster where shiftid=" + shifti);
		for (ShiftMaster sm : shift) {
			shiftn = sm.getShiftname();

		}

		return shiftn;
	}

	@Override
	public ResultSet getDepartmentSummaryReport(int department_id, String date) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		query = "select(select count(timeAsPerShftTimings) from attendance_logs_bulk_entry where  STR_TO_DATE(Recorddate,'%d-%m-%Y')= STR_TO_DATE('"
				+ date + "','%d-%m-%Y') and WorkID in(select employee_id from employees where department_id='"
				+ department_id + "'))as P,(select count(employee_id)  from employees where department_id='"
				+ department_id + "' )as TotalEmployees,"

				+ "((select count(employee_id)  from employees where department_id='" + department_id
				+ "')-(select count(timeAsPerShftTimings) from attendance_logs_bulk_entry where STR_TO_DATE(Recorddate,'%d-%m-%Y')= STR_TO_DATE('"
				+ date + "','%d-%m-%Y') and WorkID in(select employee_id from employees where department_id='"
				+ department_id + "'))) as A,"

				+ "(select count(employee_id) from allowed_leaves where STR_TO_DATE('" + date
				+ "','%d-%m-%Y') between STR_TO_DATE(approved_fromdate,'%d-%m-%Y') and STR_TO_DATE(approved_todate,'%d-%m-%Y')  and employee_id in(select employee_id from employees where department_id='"
				+ department_id + "'))as noLeaves,"

				+ "(select count(WorkID) from attendance_logs_bulk_entry where  (SUBSTRING_INDEX(timeAsPerShftTimings,'~',1) > SUBSTRING_INDEX(startDateAndTime,' ',-1) )  and STR_TO_DATE(Recorddate,'%d-%m-%Y')=STR_TO_DATE('"
				+ date + "','%d-%m-%Y') and WorkID in(select employee_id from employees where department_id='"
				+ department_id + "')) as LateComing," + ""

//				+ "(select count (earlyGoing) from attendance_logs_bulk_entry where  STR_TO_DATE(Recorddate,'%d-%m-%Y')= STR_TO_DATE('"
//				+ date + "','%d-%m-%Y')" + " and WorkID in(select employee_id from employees where department_id='"
//				+ department_id + "'))";

			+ "(select COUNT(workID) from attendance_logs_bulk_entry where (SUBSTRING_INDEX(timeAsPerShftTimings,'~',-1) < SUBSTRING_INDEX(endDateAndTime,' ',-1) ) and STR_TO_DATE(Recorddate,'%d-%m-%Y')=STR_TO_DATE('"
		+ date + "','%d-%m-%Y') and WorkID in(select employee_id from employees where department_id='"+ department_id + "')) as EarlyGoing,(select name from departments where id='" + department_id+ "') as departmentName";

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getDailyAttendanceReport(int employeeId, String dateFrom, String dateTo, String shiftStartTime,
			String shiftEndTime) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();
		/*
		 * query="SELECT emp.employee_id,emp.first_name, emp.last_name, att.Recorddate, att.statusInString, att.Recordtime from attendance_logs att inner join employees emp "
		 * +
		 * "on att.WorkID=emp.employee_id where STR_TO_DATE(att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
		 * +dateFrom+"','%d-%m-%Y') and "+
		 * "STR_TO_DATE(att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
		 * +dateTo+"','%d-%m-%Y') and att.WorkID="
		 * +employeeId+" order by STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		 */
		query = "select Recorddate, str_to_date(inTime,'%H:%i:%s') as inTime, if(str_to_date(inTime,'%H:%i:%s') = str_to_date(outTime,'%H:%i:%s'),'A', str_to_date(outTime,'%H:%i:%s') ) as outTime , TIMEDIFF(str_to_date(substring_index(timeAsPerShftTimings,'~',-1),'%H:%i:%s'),str_to_date(substring_index(timeAsPerShftTimings,'~',1),'%H:%i:%s')) as Total_Duration, if(TIMEDIFF(TIMEDIFF(str_to_date(outTime,'%H:%i:%s'),str_to_date(inTime,'%H:%i:%s')), TIMEDIFF(str_to_date('"
				+ shiftEndTime + "','%H:%i:%s'),str_to_date('" + shiftStartTime
				+ "','%H:%i:%s')))>'0' ,TIMEDIFF(TIMEDIFF(str_to_date(outTime,'%H:%i:%s'),str_to_date(inTime,'%H:%i:%s')), TIMEDIFF(str_to_date('"+ shiftEndTime + "','%H:%i:%s'),str_to_date('" + shiftStartTime	+ "','%H:%i:%s'))), 'N/A' ) as Over_Time, TIMEDIFF(str_to_date('" + shiftEndTime
				+ "','%H:%i:%s'),str_to_date('" + shiftStartTime
				+ "','%H:%i:%s')) as Work_Duration from attendance_logs_bulk_entry where str_to_date(Recorddate,'%d-%m-%Y') between str_to_date('"
				+ dateFrom + "','%d-%m-%Y') and str_to_date('" + dateTo
				+ "','%d-%m-%Y') and WorkID= " + employeeId + " order by str_to_date(Recorddate,'%d-%m-%Y')";
				//+ "and WorkID=2 order by str_to_date(Recorddate,'%d-%m-%Y')";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}
	
	
	@Override
	public ResultSet getEmployeePersonalDetailsReport() {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();
		query = "SELECT e.employee_id, e.first_name,e.last_name, dp.name, ds.designation, e.joining_date, e.date_of_birth, e.current_address FROM employees e inner join departments dp on e.department_id=dp.id inner join designation ds on e.designation=ds.id";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getLeaveReport(int workId, int departmentId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
			query = "SELECT emp.first_name, emp.last_name, dp.name, lv.leave_subject, lv.from_date, lv.to_date, lv.approveFromdate, lv.approveTodate, ss.status_name as Status "
					+ "FROM employee_leaves as lv inner join employees as emp on emp.employee_id=lv.single_employee_id inner join departments as dp on lv.department_id=dp.id inner join statuses as ss on lv.leaveStatus=ss.id "
					+ "WHERE lv.single_employee_id=" + workId
					+ " and STR_TO_DATE(lv.from_date,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(lv.from_date,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y')";

		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "SELECT emp.first_name, emp.last_name, dp.name, lv.leave_subject, lv.from_date, lv.to_date, lv.approveFromdate, lv.approveTodate,ss.status_name as Status "
					+ "FROM employee_leaves as lv inner join employees as emp on emp.employee_id=lv.single_employee_id inner join departments as dp on lv.department_id=dp.id inner join statuses as ss on lv.leaveStatus=ss.id "
					+ "WHERE lv.single_employee_id in(" + selectedEmployees
					+ ") and STR_TO_DATE(lv.from_date,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(lv.from_date,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y')";

		} else {
			query = "SELECT emp.first_name, emp.last_name, dp.name, lv.leave_subject, lv.from_date, lv.to_date, lv.approveFromdate, lv.approveTodate,ss.status_name as Status "
					+ "FROM employee_leaves as lv inner join employees as emp on emp.employee_id=lv.single_employee_id inner join departments as dp on lv.department_id=dp.id  inner join statuses as ss on lv.leaveStatus=ss.id "
					+ "WHERE lv.department_id=" + departmentId
					+ " AND STR_TO_DATE(lv.from_date,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
					+ "','%d-%m-%Y') and STR_TO_DATE(lv.from_date,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
					+ "','%d-%m-%Y')";
		}

		// query="select first_name, last_name, email, date_of_birth from employees
		// where department_id="+departmentId;
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	public ResultSet getLeaveStatusReport(int workId, int departmentId, String empReportType, String selectedcheckbox,
			int leaveType) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
			// query="SELECT dep.name,emp.first_name, emp.last_name, cmp.allowedLeaves as
			// TotalLeaves, emp.leavesUsed ,(cmp.allowedLeaves-emp.leavesUsed) as
			// LeavesRemaining from company cmp,employees emp inner join departments dep on
			// dep.id=emp.department_id where dep.id="+departmentId+" and
			// emp.employee_id="+workId;
			query = "select emp.first_name, emp.last_name, lt.yearlyLimit  as Total_Leaves, sum(al.value) as Leaves_Taken, if(lt.yearlyLimit-sum(al.value)>=0 ,lt.yearlyLimit-sum(al.value) ,0) as Leaves_Remaining, if(lt.yearlyLimit-sum(al.value)>=0 ,0 ,sum(al.value)-lt.yearlyLimit) as Extra_Leaves from allowed_leaves as al inner join leave_type as lt on al.leavetype=lt.id inner join employees as emp on emp.employee_id=al.employee_id where al.employee_id ="
					+ workId + " and al.leavetype= " + leaveType + " group by emp.employee_id";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			// query="SELECT dep.name,emp.first_name, emp.last_name, cmp.allowedLeaves as
			// TotalLeaves, emp.leavesUsed ,(cmp.allowedLeaves-emp.leavesUsed) as
			// LeavesRemaining from company cmp,employees emp inner join departments dep on
			// dep.id=emp.department_id where dep.id="+departmentId+" and emp.employee_id
			// in("+selectedEmployees+")";
			query = "select emp.first_name, emp.last_name, lt.yearlyLimit  as Total_Leaves, sum(al.value) as Leaves_Taken, if(lt.yearlyLimit-sum(al.value)>=0 ,lt.yearlyLimit-sum(al.value) ,0) as Leaves_Remaining, if(lt.yearlyLimit-sum(al.value)>=0 ,0 ,sum(al.value)-lt.yearlyLimit) as Extra_Leaves from allowed_leaves as al inner join leave_type as lt on al.leavetype=lt.id inner join employees as emp on emp.employee_id=al.employee_id where al.employee_id in( "
					+ selectedEmployees + " ) and al.leavetype=  " + leaveType + " group by emp.employee_id";
		} else {
			// query="SELECT dep.name,emp.first_name, emp.last_name, cmp.allowedLeaves as
			// TotalLeaves, emp.leavesUsed ,(cmp.allowedLeaves-emp.leavesUsed) as
			// LeavesRemaining from company cmp,employees emp inner join departments dep on
			// dep.id=emp.department_id where dep.id="+departmentId;
			query = "select emp.first_name, emp.last_name, lt.yearlyLimit  as Total_Leaves, sum(al.value) as Leaves_Taken, if(lt.yearlyLimit-sum(al.value)>=0 ,lt.yearlyLimit-sum(al.value) ,0) as Leaves_Remaining, if(lt.yearlyLimit-sum(al.value)>=0 ,0 ,sum(al.value)-lt.yearlyLimit) as Extra_Leaves from allowed_leaves as al inner join leave_type as lt on al.leavetype=lt.id inner join employees as emp on emp.employee_id=al.employee_id where al.employee_department_id="
					+ departmentId + " and al.leavetype= " + leaveType + " group by emp.employee_id";

		}

		// query="select first_name, last_name, email, date_of_birth from employees
		// where department_id="+departmentId;
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getShiftWiseEmployeeList(int shiftId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int workId, String reportType, String allowedTime) {

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		// Query queryres = session.createQuery(query).setMaxResults(10);
		try {
			
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			if (reportType.equals("InOut")) {
				if (empReportType.equals("single")) {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,STR_TO_DATE(Att.inTime,'%H:%i:%s')as CheckIn, if(substring_index(Att.timeAsPerShftTimings,'~',1) = substring_index(Att.timeAsPerShftTimings,'~',-1),'A', STR_TO_DATE(att.outTime,'%H:%i:%s') ) as CheckOut,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId + " and Att.WorkID=" + workId
							+ " order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
					
					//substring_index(timeAsPerShftTimings,'~',1) as inTime, if(substring_index(timeAsPerShftTimings,'~',1) = substring_index(timeAsPerShftTimings,'~',-1),'A', substring_index(timeAsPerShftTimings,'~',-1) ) as outTime

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,STR_TO_DATE(Att.inTime,'%H:%i:%s')as CheckIn, if(substring_index(Att.timeAsPerShftTimings,'~',1) = substring_index(Att.timeAsPerShftTimings,'~',-1),'A', STR_TO_DATE(att.outTime,'%H:%i:%s') ) as CheckOut,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId + " and Att.WorkID in(" + selectedEmployees
							+ ") order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,STR_TO_DATE(Att.inTime,'%H:%i:%s')as CheckIn, if(substring_index(Att.timeAsPerShftTimings,'~',1) = substring_index(Att.timeAsPerShftTimings,'~',-1),'A', STR_TO_DATE(att.outTime,'%H:%i:%s') ) as CheckOut,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
			}

			else if (reportType.equals("BreakInOut")) {
				if (empReportType.equals("single")) {
					query="select Emp.employee_id,Emp.first_name,Emp.last_name,shift.shiftcode,shift.shiftname,STR_TO_DATE(Att.breakIn,'%H:%i:%s')as BreakIn, STR_TO_DATE(Att.breakOut,'%H:%i:%s')as BreakOut,STR_TO_DATE(Att.breakIn2,'%H:%i:%s')as BreakIn2,STR_TO_DATE(Att.breakOut2,'%H:%i:%s')as BreakOut2 , Att.Recorddate from shift_mast shift" +
							" inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no " +
							" where     STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFrom+"','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"+dateTo+"','%d-%m-%Y') and shift.shiftid="+shiftId+" and Att.WorkID=" +workId+
							" order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
				
				else if(empReportType.equals("multiple"))
				{
					String selectedEmployees=selectedcheckbox.substring(0,selectedcheckbox.length()-1);
					query="select Emp.employee_id,Emp.first_name,Emp.last_name,shift.shiftcode,shift.shiftname,STR_TO_DATE(Att.breakIn,'%H:%i:%s')as BreakIn, STR_TO_DATE(Att.breakOut,'%H:%i:%s')as BreakOut,STR_TO_DATE(Att.breakIn2,'%H:%i:%s')as BreakIn2,STR_TO_DATE(Att.breakOut2,'%H:%i:%s')as BreakOut2 , Att.Recorddate from shift_mast shift" +
							" inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no " +
							" where  STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFrom+"','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"+dateTo+"','%d-%m-%Y') and shift.shiftid="+shiftId+" and  Att.WorkID in("+selectedEmployees+") " +
							" order by Emp.last_name, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}

				else {
//					query = "select Emp.employee_id,Emp.first_name,Emp.last_name,shift.shiftcode,shift.shiftname,SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(timeAsPerShftTimings,'~',-3),'~',2),'~',1) as BreakIn, SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(timeAsPerShftTimings,'~',-3),'~',2),'~',-1) as BreakOut  , Att.Recorddate from shift_mast shift"
//							+ " inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
//							+ " where  (mod(LENGTH(Att.timeAsPerShftTimings)-LENGTH(REPLACE(Att.timeAsPerShftTimings,'~','')),5)=0 and LENGTH(Att.timeAsPerShftTimings)>20)    and   STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
//							+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
//							+ dateTo + "','%d-%m-%Y') and shift.shiftid=" + shiftId
//							+ " order by Emp.last_name, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
					
					query = "select Emp.employee_id,Emp.first_name,Emp.last_name,shift.shiftcode,shift.shiftname,STR_TO_DATE(Att.breakIn,'%H:%i:%s')as BreakIn, STR_TO_DATE(Att.breakOut,'%H:%i:%s')as BreakOut, STR_TO_DATE(Att.breakIn2,'%H:%i:%s')as BreakIn2,STR_TO_DATE(Att.breakOut2,'%H:%i:%s')as BreakOut2 ,Att.Recorddate from shift_mast shift"
							+ " inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ " where      STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
							+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
							+ dateTo + "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by Emp.last_name, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				}

			}

			else if (reportType.equals("EarlyComing")) {
				if (empReportType.equals("single")) {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,concat(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)),'') as earlyComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)))=8 and Att.WorkID="
							+ workId + " order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
					
				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,concat(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)),'') as earlyComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)))=8 and Att.WorkID in("
							+ selectedEmployees + ") order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,concat(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)),'') as earlyComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBSTRING_INDEX(Att.startDateAndTime,' ',-1),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1)))=8 order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
			} else if (reportType.equals("LateComing")) {

				if (empReportType.equals("single")) {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)) as lateComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),'"
							+ allowedTime + "'),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)))=8 and Att.WorkID="
							+ workId + " order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)) as lateComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),'"
							+ allowedTime + "'),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)))=8 and Att.WorkID in("
							+ selectedEmployees + ") order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)) as lateComing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1),'"
							+ allowedTime
							+ "'),SUBSTRING_INDEX(Att.startDateAndTime,' ',-1)))=8 order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
			} else if (reportType.equals("EarlyGoing")) {
				if (empReportType.equals("single")) {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1), str_to_date(outTime,'%H:%i:%s')) as earlyGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1),'" + allowedTime
							+ "'),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)))=8 and Att.WorkID=" + workId
							+ " order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1), str_to_date(outTime,'%H:%i:%s')) as earlyGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1),'" + allowedTime
							+ "'),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)))=8 and Att.WorkID in("
							+ selectedEmployees + ") order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name, TIMEDIFF(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1), str_to_date(outTime,'%H:%i:%s')) as earlyGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBTIME(SUBSTRING_INDEX(Att.endDateAndTime,' ',-1),'" + allowedTime
							+ "'),SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)))=8 order by Emp.employee_id, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
			} else if (reportType.equals("LateGoing")) {
				if (empReportType.equals("single")) {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1),SUBSTRING_INDEX(Att.endDateAndTime,' ',-1)) as lateGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1),SUBSTRING_INDEX(Att.endDateAndTime,' ',-1)))=8 and Att.WorkID="
							+ workId + " order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1),SUBSTRING_INDEX(Att.endDateAndTime,' ',-1)) as lateGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and LENGTH(TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1),SUBSTRING_INDEX(Att.endDateAndTime,' ',-1)))=8 and Att.WorkID in("
							+ selectedEmployees + ") order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select Emp.employee_id,Emp.last_name,shift.shiftcode,shift.shiftname,Emp.first_name,TIMEDIFF(SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1),SUBSTRING_INDEX(Att.endDateAndTime,' ',-1)) as lateGoing,Att.Recorddate "
							+ "from shift_mast shift inner join attendance_logs_bulk_entry Att on Att.shift=shift.shiftid inner join employees Emp on Att.WorkID=Emp.employee_no "
							+ "where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " and SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)>SUBSTRING_INDEX(Att.endDateAndTime,' ',-1) order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
				}
			}

			else if (reportType.equals("Absent")) {
				SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
				Date fromDate;
				List<Date> dates = new ArrayList<Date>();
				List<Employee> employeesList = hibernateTemplate.find("from Employee");
				@SuppressWarnings("unchecked")
				// List<AttendanceLogsBulkEntry>
				// attendanceLogsBulkEntries=session.createQuery("").list();
				PreparedStatement psStmt = connection.prepareStatement("truncate daterange");
				psStmt.executeQuery();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"select A.WorkID,E.first_name,A.Recorddate from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
								+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
								+ dateTo + "','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')");
				resultSet = preparedStatement.executeQuery();
				List<ShiftDefinition> shiftDefinitionsList = hibernateTemplate
						.find("from ShiftDefinition where shiftid=" + shiftId);
				try {
					fromDate = format.parse(dateFrom);
					Date toDate = format.parse(dateTo);
					Calendar calendarFrom = new GregorianCalendar();
					calendarFrom.setTime(fromDate);
					
					/*
					 * calendarFrom.clear();
					 * calendarFrom.set(Integer.parseInt(dateFrom.split("-")[2]),Integer.parseInt(
					 * dateFrom.split("-")[1])-1,Integer.parseInt(dateFrom.split("-")[0]), 0, 0);
					 * Calendar calendarTo = new GregorianCalendar(); calendarTo.clear();
					 * calendarTo.set(Integer.parseInt(dateTo.split("-")[2]),Integer.parseInt(dateTo
					 * .split("-")[1])-1,Integer.parseInt(dateTo.split("-")[0]), 0, 0);
					 */
					
					while (calendarFrom.getTime().before(toDate)) {
						Date resultado = calendarFrom.getTime();
						dates.add(resultado);
						calendarFrom.add(Calendar.DATE, 1);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean checkAbsentEmployee = false;
				DateRange dateRange = new DateRange();
				for (int i = 0; i < dates.size(); i++) {
					for (Employee employeesObj : employeesList) {
						checkAbsentEmployee = false;
						resultSet.beforeFirst();
						while (resultSet.next()) {
							int empNo = resultSet.getInt(1);
							int empNo1 = employeesObj.getEmployeeNo();
							String date1 = resultSet.getString(3);
							String date2 = format.format(dates.get(i));
							if (empNo == employeesObj.getEmployeeNo() && date1.equals(format.format(dates.get(i)))) {
								checkAbsentEmployee = true;
								break;
							}
						}
						if (checkAbsentEmployee == false) {
							if (shiftDefinitionsList.get(0).getWeeklyOff1Day().equals((dates.get(i).getDay() + 1) + "")
									|| shiftDefinitionsList.get(0).getWeeklyOff2Day()
											.equals((dates.get(i).getDay() + 1) + "")) {

							} else {
								dateRange.setDate(format.format(dates.get(i)));
								dateRange.setEmployee_no(employeesObj.getEmployeeNo());
								hibernateTemplate.save(dateRange);
							}
						}
					}
					/* dateRange.setDate(format.format(dates.get(i))); */
				}

				if (empReportType.equals("single")) {
					query = "select d.dateValue,d.employee_no,emp.first_name,emp.last_name,shift.shiftname "
							+ "from daterange d inner join employees emp on d.employee_no=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where  d.employee_no=" + workId
							+ " and STR_TO_DATE(d.datevalue,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(d.datevalue,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by d.employee_no,STR_TO_DATE(d.datevalue,'%d-%m-%Y')";

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select d.dateValue,d.employee_no,emp.first_name,emp.last_name,shift.shiftname"
							+ " from daterange d inner join employees emp on d.employee_no=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where d.employee_no IN (" + selectedEmployees
							+ ") and STR_TO_DATE(d.datevalue,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(d.datevalue,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by d.employee_no,STR_TO_DATE(d.datevalue,'%d-%m-%Y')";

				} else {
					query = "select d.dateValue,d.employee_no,emp.first_name,emp.last_name,shift.shiftname from daterange d inner join employees emp on d.employee_no=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where STR_TO_DATE(d.datevalue,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(d.datevalue,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by d.employee_no,STR_TO_DATE(d.datevalue,'%d-%m-%Y')";

				}
			} else if (reportType.equals("Present")) {
				if (empReportType.equals("single")) {
					query = "select A.WorkID,A.Recorddate,emp.first_name,emp.last_name,shift.shiftname "
							+ "from attendance_logs_bulk_entry A inner join employees emp on A.WorkID=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where A.WorkID=" + workId + " and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
							+ dateFrom + "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
							+ dateTo + "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";

				} else if (empReportType.equals("multiple")) {
					String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
					query = "select A.WorkID,A.Recorddate,emp.first_name,emp.last_name,shift.shiftname "
							+ "from attendance_logs_bulk_entry A inner join employees emp on A.WorkID=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where A.WorkID IN(" + selectedEmployees
							+ ") and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";

				} else {
					query = "select A.WorkID,A.Recorddate,emp.first_name,emp.last_name,shift.shiftname "
							+ "from attendance_logs_bulk_entry A inner join employees emp on A.WorkID=emp.employee_no inner join cal_employee_shifts calEmp on emp.employee_id=calEmp.employee_id inner join shift_mast shift on calEmp.shiftid=shift.shiftid "
							+ "where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + dateFrom
							+ "','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + dateTo
							+ "','%d-%m-%Y') and shift.shiftid=" + shiftId
							+ " order by A.WorkID,STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
				}
			} else {

			}
			ps = connection.prepareStatement(query);
			resultSet = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public ResultSet getMonthlyAttendanceOfEmployee(int employeeNo, String month, String year, String empReportType,
			String selectedcheckbox) {

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			String campareDate = null;
			if (Integer.parseInt(month) != 12) {
				campareDate = (Integer.parseInt(month) + 1) + "-" + year;
			} else {
				campareDate = (Integer.parseInt(month) + 1) + "-" + 1;
			}

			if (empReportType.equals("single")) {
				query = "select emp.employee_no,emp.first_name,emp.last_name,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1) as CheckIn,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)as CheckOut,Att.Recorddate from employees emp inner join attendance_logs_bulk_entry Att on emp.employee_no=Att.WorkID where emp.employee_no="
						+ employeeNo + " and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + month + "-"
						+ year + "','%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('" + campareDate
						+ "','%m-%Y')  order by STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
			} else if (empReportType.equals("multiple")) {
				String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
				query = "select emp.employee_no,emp.first_name,emp.last_name,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1) as CheckIn,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)as CheckOut,Att.Recorddate from employees emp inner join attendance_logs_bulk_entry Att on emp.employee_no=Att.WorkID where emp.employee_no in("
						+ selectedEmployees + ") and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('" + month
						+ "-" + year + "','%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
						+ campareDate + "','%m-%Y')  order by emp.employee_no, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
			} else {
				query = "select emp.employee_no,emp.first_name,emp.last_name,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',1) as CheckIn,SUBSTRING_INDEX(Att.timeAsPerShftTimings,'~',-1)as CheckOut,Att.Recorddate from employees emp inner join attendance_logs_bulk_entry Att on emp.employee_no=Att.WorkID where STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
						+ month + "-" + year + "','%m-%Y') and STR_TO_DATE(Att.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
						+ campareDate + "','%m-%Y')  order by emp.employee_no, STR_TO_DATE(Att.Recorddate,'%d-%m-%Y')";
			}
			ps = connection.prepareStatement(query);
			resultSet = ps.executeQuery();
			int i = 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

		
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultSet getmusterReportOfEmployees(int shiftId, String month,
			String year) {
		ResultSet resultSet=null;
		Session session = null;
		String query=null;
		java.sql.PreparedStatement ps=null;		
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection=session.connection();
			String campareDate=null;
			PreparedStatement pstmt=connection.prepareStatement("truncate musterreport");
			pstmt.execute();
			if(Integer.parseInt(month)!=12)
			{
				campareDate=(Integer.parseInt(month)+1)+"-"+year;
			}
			else
			{
				campareDate=(Integer.parseInt(month)+1)+"-"+1;
			}
			
			SimpleDateFormat format=new SimpleDateFormat("d-M-yyyy");
			Date fromDate;
			List<Date> dates=new ArrayList<Date>();
			List<Employee> employeesList=hibernateTemplate.find("from Employee");
			/*List<AttendanceLogsBulkEntry> attendanceLogsBulkEntries=session.createQuery("select * AttendanceLogsBulkEntry where recordDate="+).list();*/
			/*PreparedStatement preparedStatement =connection.prepareStatement("select A.WorkID,E.first_name,A.Recorddate from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFrom+"','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"+dateTo+"','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')");
			resultSet=preparedStatement.executeQuery();*/
		
			try {
				fromDate = format.parse("1-"+month+"-"+year);
				Date toDate=format.parse("1-"+campareDate);
				Calendar calendar = new GregorianCalendar();
			    calendar.setTime(fromDate);
			    while (calendar.getTime().before(toDate))
			    {
			        Date resultado = calendar.getTime();
			        dates.add(resultado);
			        calendar.add(Calendar.DATE, 1);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			boolean checkAbsentEmployee=false;
			MusterReport musterReport=new MusterReport();
			for(Employee employeesObj:employeesList)
			{
				String employeeQuery=null;
				for (int i = 0; i < dates.size(); i++) 
					{
					String tempMonth=format.format(dates.get(i)).split("-")[1];
					String tempDate=format.format(dates.get(i)).split("-")[0];
					String tempYear=format.format(dates.get(i)).split("-")[2];
					List<ShiftDefinition> shiftDefinitionsList=hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftId);
					List<AttendanceLogsBulkEntry> attendanceLogsBulkEntriesList=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+employeesObj.getEmployeeNo()+" and recordDate Like '"+format.format(dates.get(i))+"'");
					List<MusterReport> alreadyPresentRecord=hibernateTemplate.find("from MusterReport where employeeNo="+employeesObj.getEmployeeNo()+" and month='"+tempMonth+"' and year='"+tempYear+"'");
					
					System.out.println("emp id "+employeesObj.getEmployeeNo());
					
					if(shiftDefinitionsList.get(0).getWeeklyOff1Day().equals((dates.get(i).getDay()+1)+"")||shiftDefinitionsList.get(0).getWeeklyOff2Day().equals((dates.get(i).getDay()+1)+""))
					{
						if(alreadyPresentRecord.size()!=0)
						{
							int id=alreadyPresentRecord.get(0).getId();
							System.out.println("id "+id);
							employeeQuery="UPDATE MusterReport SET day"+tempDate+"='W' where id="+id;
						}
						else
						{
							employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+")  VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','WO')";						
							
						}
					}
					else
					{
						if(attendanceLogsBulkEntriesList.size()!=0)
						{
							if(alreadyPresentRecord.size()!=0)
							{
								int preCount=alreadyPresentRecord.get(0).getPresentCount()+1;
								int id=alreadyPresentRecord.get(0).getId();
								employeeQuery="UPDATE MusterReport SET day"+tempDate+"='P',presentCount="+preCount+" where id="+id;
						        System.out.println(employeeQuery);   
							}
							else
							{
								employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+",presentCount)  VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','P',1)";						
							}
						}
						else
						{
							if(alreadyPresentRecord.size()!=0)
							{
								int abCount=alreadyPresentRecord.get(0).getAbsentCount()+1;
								int id=alreadyPresentRecord.get(0).getId();
								employeeQuery="UPDATE MusterReport SET day"+tempDate+"='A',absentCount="+abCount+" where id="+id;
							}
							else
							{
								employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+",absentCount) VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','A',1)";
							}
						}	
					}
					
					if(!employeeQuery.equals(null))
					{
						PreparedStatement preparedStatement=session.connection().prepareStatement(employeeQuery);
						preparedStatement.executeUpdate();
					}
				}
			}
			
			query="select m.empNo,emp.first_name,emp.last_name,m.day1,m.day2,m.day3,m.day4,m.day5,m.day6,m.day7,m.day8,m.day9,m.day10,m.day11,m.day12,m.day13,m.day14,m.day15, m.day16, m.day17, m.day18, m.day19, m.day20, m.day21, m.day22, m.day23, m.day24, m.day25, m.day26, m.day27, m.day28,IF(m.day29 = '0', 'N',m.day29) as day29, IF(m.day30 = '0', 'N',m.day30) as day30,IF(m.day31 = '0', 'N',m.day31) as day31,m.absentCount, m.presentCount from musterreport m inner join employees emp on m.empNo=emp.employee_no where m.empNo in (select employee_id from cal_employee_shifts where shiftid="+shiftId+")";
			ps=connection.prepareStatement(query);
			resultSet=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
//=============================================================	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultSet getBasicWorkReport(int shiftId, String month,String year) {
		ResultSet resultSet=null;
		Session session = null;
		String query=null;
		java.sql.PreparedStatement ps=null;		
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection=session.connection();
			String campareDate=null;
			PreparedStatement pstmt=connection.prepareStatement("truncate musterreport");
			pstmt.execute();
			if(Integer.parseInt(month)!=12)
			{
				campareDate=(Integer.parseInt(month)+1)+"-"+year;
			}
			else
			{
				campareDate=(Integer.parseInt(month)+1)+"-"+1;
			}
			
			
			SimpleDateFormat format=new SimpleDateFormat("d-M-yyyy");
			Date fromDate;
			List<Date> dates=new ArrayList<Date>();
			List<Employee> employeesList=hibernateTemplate.find("from Employee");
			List<AttendanceLogsBulkEntry>attendanceList=hibernateTemplate.find("From AttendanceLogsBulkEntry");
			/*List<AttendanceLogsBulkEntry> attendanceLogsBulkEntries=session.createQuery("select * AttendanceLogsBulkEntry where recordDate="+).list();*/
			/*PreparedStatement preparedStatement =connection.prepareStatement("select A.WorkID,E.first_name,A.Recorddate from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFrom+"','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"+dateTo+"','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')");
			resultSet=preparedStatement.executeQuery();*/
		
			try {
				fromDate = format.parse("1-"+month+"-"+year);
				Date toDate=format.parse("1-"+campareDate);
				Calendar calendar = new GregorianCalendar();
			    calendar.setTime(fromDate);
			    while (calendar.getTime().before(toDate))
			    {
			        Date resultado = calendar.getTime();
			        dates.add(resultado);
			        calendar.add(Calendar.DATE, 1);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
			boolean checkAbsentEmployee=false;
			MusterReport musterReport=new MusterReport();
			for(Employee employeesObj:employeesList)
			{
				for(AttendanceLogsBulkEntry attendance:attendanceList) {
				
				String employeeQuery=null;
				for (int i = 0; i < dates.size(); i++) 
					{
					String tempMonth=format.format(dates.get(i)).split("-")[1];
					String tempDate=format.format(dates.get(i)).split("-")[0];
					String tempYear=format.format(dates.get(i)).split("-")[2];
					List<ShiftDefinition> shiftDefinitionsList=hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftId);
					List<AttendanceLogsBulkEntry> attendanceLogsBulkEntriesList=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+employeesObj.getEmployeeNo()+" and recordDate Like '"+format.format(dates.get(i))+"'");
					List<MusterReport> alreadyPresentRecord=hibernateTemplate.find("from MusterReport where employeeNo="+employeesObj.getEmployeeNo()+" and month='"+tempMonth+"' and year='"+tempYear+"'");
					if(shiftDefinitionsList.get(0).getWeeklyOff1Day().equals((dates.get(i).getDay()+1)+"")||shiftDefinitionsList.get(0).getWeeklyOff2Day().equals((dates.get(i).getDay()+1)+""))
					{
						if(alreadyPresentRecord.size()!=0)
						{
							int id=alreadyPresentRecord.get(0).getId();
							employeeQuery="UPDATE MusterReport SET day"+tempDate+"='W' where id="+id;
						}
						else
						{
							employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+")  VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','WO')";						
						}
					}
					else
					{
						if(attendanceLogsBulkEntriesList.size()!=0)
						{
							if(alreadyPresentRecord.size()!=0)
							{
								int preCount=alreadyPresentRecord.get(0).getPresentCount()+1;
								int id=alreadyPresentRecord.get(0).getId();
								employeeQuery="UPDATE MusterReport SET day"+tempDate+"='"+attendanceLogsBulkEntriesList.get(0).getTimeAsPerShftTimings()+"',presentCount="+preCount+" where id="+id;
							}
							else
							{
								employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+",presentCount)  VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','"+attendanceLogsBulkEntriesList.get(0).getTimeAsPerShftTimings()+"',1)";						
							}
						}
						else
						{
							if(alreadyPresentRecord.size()!=0)
							{
								int abCount=alreadyPresentRecord.get(0).getAbsentCount()+1;
								int id=alreadyPresentRecord.get(0).getId();
								employeeQuery="UPDATE MusterReport SET day"+tempDate+"='A',absentCount="+abCount+" where id="+id;
							}
							else
							{
								employeeQuery="INSERT INTO MusterReport (empNo,musterMonth,musterYear,day"+tempDate+",absentCount) VALUES ("+employeesObj.getEmployeeNo()+",'"+tempMonth+"','"+tempYear+"','A',1)";
							}
						}	
					}
					
					if(!employeeQuery.equals(null))
					{
						PreparedStatement preparedStatement=session.connection().prepareStatement(employeeQuery);
						preparedStatement.executeUpdate();
					}
				}
			}
		}	
			
			query="select emp.employee_no as empNo,emp.first_name,emp.last_name,"+
					"if(m.day1!='W' and m.day1!='A','P',m.day1) as day1, if(m.day1!='W' and m.day1!='A',SUBSTRING_INDEX(m.day1,'~',1),m.day1) as d1In , if(m.day1!='W' and m.day1!='A',IF(SUBSTRING_INDEX(m.day1,'~',1) = SUBSTRING_INDEX(m.day1,'~',-1), 'A',SUBSTRING_INDEX(m.day1,'~',-1)),m.day1) as d1Out, if(m.day1!='W' and m.day1!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s')),'N'),'N') as TD1,"+
					"if(m.day2!='W' and m.day2!='A','P',m.day2) as day2, if(m.day2!='W' and m.day2!='A',SUBSTRING_INDEX(m.day2,'~',1),m.day2) as d2In , if(m.day2!='W' and m.day2!='A',IF(SUBSTRING_INDEX(m.day2,'~',1) = SUBSTRING_INDEX(m.day2,'~',-1), 'A',SUBSTRING_INDEX(m.day2,'~',-1)),m.day2) as d2Out, if(m.day2!='W' and m.day2!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s')),'N'),'N') as TD2,"+
					"if(m.day3!='W' and m.day3!='A','P',m.day3) as day3, if(m.day3!='W' and m.day3!='A',SUBSTRING_INDEX(m.day3,'~',1),m.day3) as d3In , if(m.day3!='W' and m.day3!='A',IF(SUBSTRING_INDEX(m.day3,'~',1) = SUBSTRING_INDEX(m.day3,'~',-1), 'A',SUBSTRING_INDEX(m.day3,'~',-1)),m.day3) as d3Out, if(m.day3!='W' and m.day3!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s')),'N'),'N') as TD3,"+
					"if(m.day4!='W' and m.day4!='A','P',m.day4) as day4, if(m.day4!='W' and m.day4!='A',SUBSTRING_INDEX(m.day4,'~',1),m.day4) as d4In , if(m.day4!='W' and m.day4!='A',IF(SUBSTRING_INDEX(m.day4,'~',1) = SUBSTRING_INDEX(m.day4,'~',-1), 'A',SUBSTRING_INDEX(m.day4,'~',-1)),m.day4) as d4Out, if(m.day4!='W' and m.day4!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s')),'N'),'N') as TD4,"+
					"if(m.day5!='W' and m.day5!='A','P',m.day5) as day5, if(m.day5!='W' and m.day5!='A',SUBSTRING_INDEX(m.day5,'~',1),m.day5) as d5In , if(m.day5!='W' and m.day5!='A',IF(SUBSTRING_INDEX(m.day5,'~',1) = SUBSTRING_INDEX(m.day5,'~',-1), 'A',SUBSTRING_INDEX(m.day5,'~',-1)),m.day5) as d5Out, if(m.day5!='W' and m.day5!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s')),'N'),'N') as TD5,"+
					"if(m.day6!='W' and m.day6!='A','P',m.day6) as day6, if(m.day6!='W' and m.day6!='A',SUBSTRING_INDEX(m.day6,'~',1),m.day6) as d6In , if(m.day6!='W' and m.day6!='A',IF(SUBSTRING_INDEX(m.day6,'~',1) = SUBSTRING_INDEX(m.day6,'~',-1), 'A',SUBSTRING_INDEX(m.day6,'~',-1)),m.day6) as d6Out, if(m.day6!='W' and m.day6!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s')),'N'),'N') as TD6,"+
					"if(m.day7!='W' and m.day7!='A','P',m.day7) as day7, if(m.day7!='W' and m.day7!='A',SUBSTRING_INDEX(m.day7,'~',1),m.day7) as d7In , if(m.day7!='W' and m.day7!='A',IF(SUBSTRING_INDEX(m.day7,'~',1) = SUBSTRING_INDEX(m.day7,'~',-1), 'A',SUBSTRING_INDEX(m.day7,'~',-1)),m.day7) as d7Out, if(m.day7!='W' and m.day7!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s')),'N'),'N') as TD7,"+
					"if(m.day8!='W' and m.day8!='A','P',m.day8) as day8, if(m.day8!='W' and m.day8!='A',SUBSTRING_INDEX(m.day8,'~',1),m.day8) as d8In , if(m.day8!='W' and m.day8!='A',IF(SUBSTRING_INDEX(m.day8,'~',1) = SUBSTRING_INDEX(m.day8,'~',-1), 'A',SUBSTRING_INDEX(m.day8,'~',-1)),m.day8) as d8Out, if(m.day8!='W' and m.day8!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s')),'N'),'N') as TD8,"+					                                                                                                            
					"if(m.day9!='W' and m.day9!='A','P',m.day9) as day9, if(m.day9!='W' and m.day9!='A',SUBSTRING_INDEX(m.day9,'~',1),m.day9) as d9In , if(m.day9!='W' and m.day9!='A',IF(SUBSTRING_INDEX(m.day9,'~',1) = SUBSTRING_INDEX(m.day9,'~',-1), 'A',SUBSTRING_INDEX(m.day9,'~',-1)),m.day9) as d9Out, if(m.day9!='W' and m.day9!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s')),'N'),'N') as TD9,"+
					"if(m.day10!='W' and m.day10!='A','P',m.day10) as day10, if(m.day10!='W' and m.day10!='A',SUBSTRING_INDEX(m.day10,'~',1),m.day10) as d10In , if(m.day10!='W' and m.day10!='A',IF(SUBSTRING_INDEX(m.day10,'~',1) = SUBSTRING_INDEX(m.day10,'~',-1), 'A',SUBSTRING_INDEX(m.day10,'~',-1)),m.day10) as d10Out, if(m.day10!='W' and m.day10!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s')),'N'),'N') as TD10,"+
					"if(m.day11!='W' and m.day11!='A','P',m.day11) as day11, if(m.day11!='W' and m.day11!='A',SUBSTRING_INDEX(m.day11,'~',1),m.day11) as d11In , if(m.day11!='W' and m.day11!='A',IF(SUBSTRING_INDEX(m.day11,'~',1) = SUBSTRING_INDEX(m.day11,'~',-1), 'A',SUBSTRING_INDEX(m.day11,'~',-1)),m.day11) as d11Out, if(m.day11!='W' and m.day11!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s')),'N'),'N') as TD11,"+
					"if(m.day12!='W' and m.day12!='A','P',m.day12) as day12, if(m.day12!='W' and m.day12!='A',SUBSTRING_INDEX(m.day12,'~',1),m.day12) as d12In , if(m.day12!='W' and m.day12!='A',IF(SUBSTRING_INDEX(m.day12,'~',1) = SUBSTRING_INDEX(m.day12,'~',-1), 'A',SUBSTRING_INDEX(m.day12,'~',-1)),m.day12) as d12Out, if(m.day12!='W' and m.day12!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s')),'N'),'N') as TD12,"+
					"if(m.day13!='W' and m.day13!='A','P',m.day13) as day13, if(m.day13!='W' and m.day13!='A',SUBSTRING_INDEX(m.day13,'~',1),m.day13) as d13In , if(m.day13!='W' and m.day13!='A',IF(SUBSTRING_INDEX(m.day13,'~',1) = SUBSTRING_INDEX(m.day13,'~',-1), 'A',SUBSTRING_INDEX(m.day13,'~',-1)),m.day13) as d13Out, if(m.day13!='W' and m.day13!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s')),'N'),'N') as TD13,"+
					"if(m.day14!='W' and m.day14!='A','P',m.day14) as day14, if(m.day14!='W' and m.day14!='A',SUBSTRING_INDEX(m.day14,'~',1),m.day14) as d14In , if(m.day14!='W' and m.day14!='A',IF(SUBSTRING_INDEX(m.day14,'~',1) = SUBSTRING_INDEX(m.day14,'~',-1), 'A',SUBSTRING_INDEX(m.day14,'~',-1)),m.day14) as d14Out, if(m.day14!='W' and m.day14!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s')),'N'),'N') as TD14,"+
					"if(m.day15!='W' and m.day15!='A','P',m.day15) as day15, if(m.day15!='W' and m.day15!='A',SUBSTRING_INDEX(m.day15,'~',1),m.day15) as d15In , if(m.day15!='W' and m.day15!='A',IF(SUBSTRING_INDEX(m.day15,'~',1) = SUBSTRING_INDEX(m.day15,'~',-1), 'A',SUBSTRING_INDEX(m.day15,'~',-1)),m.day15) as d15Out, if(m.day15!='W' and m.day15!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s')),'N'),'N') as TD15,"+
					"if(m.day16!='W' and m.day16!='A','P',m.day16) as day16, if(m.day16!='W' and m.day16!='A',SUBSTRING_INDEX(m.day16,'~',1),m.day16) as d16In , if(m.day16!='W' and m.day16!='A',IF(SUBSTRING_INDEX(m.day16,'~',1) = SUBSTRING_INDEX(m.day16,'~',-1), 'A',SUBSTRING_INDEX(m.day16,'~',-1)),m.day16) as d16Out, if(m.day16!='W' and m.day16!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s')),'N'),'N') as TD16,"+
					"if(m.day17!='W' and m.day17!='A','P',m.day17) as day17, if(m.day17!='W' and m.day17!='A',SUBSTRING_INDEX(m.day17,'~',1),m.day17) as d17In , if(m.day17!='W' and m.day17!='A',IF(SUBSTRING_INDEX(m.day17,'~',1) = SUBSTRING_INDEX(m.day17,'~',-1), 'A',SUBSTRING_INDEX(m.day17,'~',-1)),m.day17) as d17Out, if(m.day17!='W' and m.day17!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s')),'N'),'N') as TD17,"+
					"if(m.day18!='W' and m.day18!='A','P',m.day18) as day18, if(m.day18!='W' and m.day18!='A',SUBSTRING_INDEX(m.day18,'~',1),m.day18) as d18In , if(m.day18!='W' and m.day18!='A',IF(SUBSTRING_INDEX(m.day18,'~',1) = SUBSTRING_INDEX(m.day18,'~',-1), 'A',SUBSTRING_INDEX(m.day18,'~',-1)),m.day18) as d18Out, if(m.day18!='W' and m.day18!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s')),'N'),'N') as TD18,"+
					"if(m.day19!='W' and m.day19!='A','P',m.day19) as day19, if(m.day19!='W' and m.day19!='A',SUBSTRING_INDEX(m.day19,'~',1),m.day19) as d19In , if(m.day19!='W' and m.day19!='A',IF(SUBSTRING_INDEX(m.day19,'~',1) = SUBSTRING_INDEX(m.day19,'~',-1), 'A',SUBSTRING_INDEX(m.day19,'~',-1)),m.day19) as d19Out, if(m.day19!='W' and m.day19!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s')),'N'),'N') as TD19,"+
					"if(m.day20!='W' and m.day20!='A','P',m.day20) as day20, if(m.day20!='W' and m.day20!='A',SUBSTRING_INDEX(m.day20,'~',1),m.day20) as d20In , if(m.day20!='W' and m.day20!='A',IF(SUBSTRING_INDEX(m.day20,'~',1) = SUBSTRING_INDEX(m.day20,'~',-1), 'A',SUBSTRING_INDEX(m.day20,'~',-1)),m.day20) as d20Out, if(m.day20!='W' and m.day20!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s')),'N'),'N') as TD20,"+
					"if(m.day21!='W' and m.day21!='A','P',m.day21) as day21, if(m.day21!='W' and m.day21!='A',SUBSTRING_INDEX(m.day21,'~',1),m.day21) as d21In , if(m.day21!='W' and m.day21!='A',IF(SUBSTRING_INDEX(m.day21,'~',1) = SUBSTRING_INDEX(m.day21,'~',-1), 'A',SUBSTRING_INDEX(m.day21,'~',-1)),m.day21) as d21Out, if(m.day21!='W' and m.day21!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s')),'N'),'N') as TD21,"+
					"if(m.day22!='W' and m.day22!='A','P',m.day22) as day22, if(m.day22!='W' and m.day22!='A',SUBSTRING_INDEX(m.day22,'~',1),m.day22) as d22In , if(m.day22!='W' and m.day22!='A',IF(SUBSTRING_INDEX(m.day22,'~',1) = SUBSTRING_INDEX(m.day22,'~',-1), 'A',SUBSTRING_INDEX(m.day22,'~',-1)),m.day22) as d22Out, if(m.day22!='W' and m.day22!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s')),'N'),'N') as TD22,"+
					"if(m.day23!='W' and m.day23!='A','P',m.day23) as day23, if(m.day23!='W' and m.day23!='A',SUBSTRING_INDEX(m.day23,'~',1),m.day23) as d23In , if(m.day23!='W' and m.day23!='A',IF(SUBSTRING_INDEX(m.day23,'~',1) = SUBSTRING_INDEX(m.day23,'~',-1), 'A',SUBSTRING_INDEX(m.day23,'~',-1)),m.day23) as d23Out, if(m.day23!='W' and m.day23!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s')),'N'),'N') as TD23,"+
					"if(m.day24!='W' and m.day24!='A','P',m.day24) as day24, if(m.day24!='W' and m.day24!='A',SUBSTRING_INDEX(m.day24,'~',1),m.day24) as d24In , if(m.day24!='W' and m.day24!='A',IF(SUBSTRING_INDEX(m.day24,'~',1) = SUBSTRING_INDEX(m.day24,'~',-1), 'A',SUBSTRING_INDEX(m.day24,'~',-1)),m.day24) as d24Out, if(m.day24!='W' and m.day24!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s')),'N'),'N') as TD24,"+
					"if(m.day25!='W' and m.day25!='A','P',m.day25) as day25, if(m.day25!='W' and m.day25!='A',SUBSTRING_INDEX(m.day25,'~',1),m.day25) as d25In , if(m.day25!='W' and m.day25!='A',IF(SUBSTRING_INDEX(m.day25,'~',1) = SUBSTRING_INDEX(m.day25,'~',-1), 'A',SUBSTRING_INDEX(m.day25,'~',-1)),m.day25) as d25Out, if(m.day25!='W' and m.day25!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s')),'N'),'N') as TD25,"+
					"if(m.day26!='W' and m.day26!='A','P',m.day26) as day26, if(m.day26!='W' and m.day26!='A',SUBSTRING_INDEX(m.day26,'~',1),m.day26) as d26In , if(m.day26!='W' and m.day26!='A',IF(SUBSTRING_INDEX(m.day26,'~',1) = SUBSTRING_INDEX(m.day26,'~',-1), 'A',SUBSTRING_INDEX(m.day26,'~',-1)),m.day26) as d26Out, if(m.day26!='W' and m.day26!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s')),'N'),'N') as TD26,"+
					"if(m.day27!='W' and m.day27!='A','P',m.day27) as day27, if(m.day27!='W' and m.day27!='A',SUBSTRING_INDEX(m.day27,'~',1),m.day27) as d27In , if(m.day27!='W' and m.day27!='A',IF(SUBSTRING_INDEX(m.day27,'~',1) = SUBSTRING_INDEX(m.day27,'~',-1), 'A',SUBSTRING_INDEX(m.day27,'~',-1)),m.day27) as d27Out, if(m.day27!='W' and m.day27!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s')),'N'),'N') as TD27,"+
					"if(m.day28!='W' and m.day28!='A','P',m.day28) as day28, if(m.day28!='W' and m.day28!='A',SUBSTRING_INDEX(m.day28,'~',1),m.day28) as d28In , if(m.day28!='W' and m.day28!='A',IF(SUBSTRING_INDEX(m.day28,'~',1) = SUBSTRING_INDEX(m.day28,'~',-1), 'A',SUBSTRING_INDEX(m.day28,'~',-1)),m.day28) as d28Out, if(m.day28!='W' and m.day28!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s')),'N'),'N') as TD28,"+
					"IF(if(m.day29!='W' and m.day29!='A' and m.day29!=-0,'P',m.day29)= '0','N',if(m.day29!='W' and m.day29!='A','P',m.day29)) as day29, IF(if(m.day29!='W' and m.day29!='A',SUBSTRING_INDEX(m.day29,'~',1),m.day29)= '0','N',if(m.day29!='W' and m.day29!='A',SUBSTRING_INDEX(m.day29,'~',1),m.day29)) as d29In , IF(if(m.day29!='W' and m.day29!='A',IF(SUBSTRING_INDEX(m.day29,'~',1) = SUBSTRING_INDEX(m.day29,'~',-1), 'A',SUBSTRING_INDEX(m.day29,'~',-1)),m.day29)= '0','N',if(m.day29!='W' and m.day29!='A',IF(SUBSTRING_INDEX(m.day29,'~',1) = SUBSTRING_INDEX(m.day29,'~',-1), 'A',SUBSTRING_INDEX(m.day29,'~',-1)),m.day29)) as d29Out ,if(m.day29!='W' and m.day29!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s')),'N'),'N') as TD29,"+
					"IF(if(m.day30!='W' and m.day30!='A' and m.day30!=0,'P',m.day30)= '0','N',if(m.day30!='W' and m.day30!='A','P',m.day30)) as day30, IF(if(m.day30!='W' and m.day30!='A',SUBSTRING_INDEX(m.day30,'~',1),m.day30)= '0','N',if(m.day30!='W' and m.day30!='A',SUBSTRING_INDEX(m.day30,'~',1),m.day30)) as d30In , IF(if(m.day30!='W' and m.day30!='A',IF(SUBSTRING_INDEX(m.day30,'~',1) = SUBSTRING_INDEX(m.day30,'~',-1), 'A',SUBSTRING_INDEX(m.day30,'~',-1)),m.day30)= '0','N',if(m.day30!='W' and m.day30!='A',IF(SUBSTRING_INDEX(m.day30,'~',1) = SUBSTRING_INDEX(m.day30,'~',-1), 'A',SUBSTRING_INDEX(m.day30,'~',-1)),m.day30)) as d30Out ,if(m.day30!='W' and m.day30!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s')),'N'),'N') as TD30,"+
					"IF(if(m.day31!='W' and m.day31!='A' and m.day31!=0,'P',m.day31)= '0','N',if(m.day31!='W' and m.day31!='A','P',m.day31)) as day31, IF(if(m.day31!='W' and m.day31!='A',SUBSTRING_INDEX(m.day31,'~',1),m.day31)= '0','N',if(m.day31!='W' and m.day31!='A',SUBSTRING_INDEX(m.day31,'~',1),m.day31)) as d31In, IF(if(m.day31!='W' and m.day31!='A',IF(SUBSTRING_INDEX(m.day31,'~',1) = SUBSTRING_INDEX(m.day31,'~',-1), 'A',SUBSTRING_INDEX(m.day31,'~',-1)),m.day31)= '0','N',if(m.day31!='W' and m.day31!='A',IF(SUBSTRING_INDEX(m.day31,'~',1) = SUBSTRING_INDEX(m.day31,'~',-1), 'A',SUBSTRING_INDEX(m.day31,'~',-1)),m.day31)) as d31Out  , if(m.day31!='W' and m.day31!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s')),'N'),'N') as TD31,m.musterMonth,m.musterYear,m.absentCount,m.presentCount from musterreport m inner join employees emp on m.empNo=emp.employee_no where m.empNo in (select employee_id from cal_employee_shifts where shiftid="+shiftId+")";
			ps=connection.prepareStatement(query);
			resultSet=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultSet getBasicWorkDetailReport(int shiftId, String month, String year, String grace_time) {
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			String campareDate = null;
			PreparedStatement pstmt = connection.prepareStatement("truncate musterreport");
			pstmt.execute();
			if (Integer.parseInt(month) != 12) {
				campareDate = (Integer.parseInt(month) + 1) + "-" + year;
			} else {
				campareDate = (Integer.parseInt(month) + 1) + "-" + 1;
			}

			SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
			Date fromDate;
			List<Date> dates = new ArrayList<Date>();
			List<Employee> employeesList = hibernateTemplate.find("from Employee");
			/*
			 * List<AttendanceLogsBulkEntry> attendanceLogsBulkEntries=session.
			 * createQuery("select * AttendanceLogsBulkEntry where recordDate="+).list();
			 */
			/*
			 * PreparedStatement preparedStatement =connection.
			 * prepareStatement("select A.WorkID,E.first_name,A.Recorddate from attendance_logs_bulk_entry A inner join shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"
			 * +dateFrom+"','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"
			 * +dateTo+"','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')");
			 * resultSet=preparedStatement.executeQuery();
			 */

			try {
				fromDate = format.parse("1-" + month + "-" + year);
				Date toDate = format.parse("1-" + campareDate);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(fromDate);
				while (calendar.getTime().before(toDate)) {
					Date resultado = calendar.getTime();
					dates.add(resultado);
					calendar.add(Calendar.DATE, 1);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String shiftStartTime = null, shiftEndTime = null;
			String query1 = "select  TimeField, EndTime from calcalendar where shiftid=" + shiftId;
			String gt = "00:30:00";
			Statement stmt = connection.createStatement();
			ResultSet rsResultSet = stmt.executeQuery(query1);
			if (rsResultSet.next()) {
				shiftStartTime = rsResultSet.getString("TimeField");
				shiftEndTime = rsResultSet.getString("EndTime");
				System.out.println("From Calcalender In time" + shiftStartTime + "\nOut Time" + shiftEndTime);
			}

			boolean checkAbsentEmployee = false;
			MusterReport musterReport = new MusterReport();
			for (Employee employeesObj : employeesList) {
				//for(AttendanceLogsBulkEntry attendance:attendanceList) {
				String employeeQuery = null;
				for (int i = 0; i < dates.size(); i++) {
					String tempMonth=format.format(dates.get(i)).split("-")[1];
					String tempDate=format.format(dates.get(i)).split("-")[0];
					String tempYear=format.format(dates.get(i)).split("-")[2];
					List<ShiftDefinition> shiftDefinitionsList=hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftId);
					List<AttendanceLogsBulkEntry> attendanceLogsBulkEntriesList=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+employeesObj.getEmployeeNo()+" and recordDate Like '"+format.format(dates.get(i))+"'");
					List<MusterReport> alreadyPresentRecord=hibernateTemplate.find("from MusterReport where employeeNo="+employeesObj.getEmployeeNo()+" and month='"+tempMonth+"' and year='"+tempYear+"'");
				
					if (shiftDefinitionsList.get(0).getWeeklyOff1Day().equals((dates.get(i).getDay() + 1) + "")
							|| shiftDefinitionsList.get(0).getWeeklyOff2Day()
									.equals((dates.get(i).getDay() + 1) + "")) {
						if (alreadyPresentRecord.size() != 0) {
							int id = alreadyPresentRecord.get(0).getId();
							employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='W' where id=" + id;
						} else {
							employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
									+ ")  VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth + "','" + tempYear
									+ "','WO')";
						}
					} else {
						if (attendanceLogsBulkEntriesList.size() != 0) {
							if (alreadyPresentRecord.size() != 0) {
								int preCount = alreadyPresentRecord.get(0).getPresentCount() + 1;
								int id = alreadyPresentRecord.get(0).getId();
								employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='"
										+ attendanceLogsBulkEntriesList.get(0).getTimeAsPerShftTimings()
										+ "',presentCount=" + preCount + " where id=" + id;
							} else {
								employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
										+ ",presentCount)  VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth
										+ "','" + tempYear + "','"
										+ attendanceLogsBulkEntriesList.get(0).getTimeAsPerShftTimings() + "',1)";
							}
						} else {
							if (alreadyPresentRecord.size() != 0) {
								int abCount = alreadyPresentRecord.get(0).getAbsentCount() + 1;
								int id = alreadyPresentRecord.get(0).getId();
								employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='A',absentCount=" + abCount
										+ " where id=" + id;
							} else {
								employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
										+ ",absentCount) VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth
										+ "','" + tempYear + "','A',1)";
							}
						}
					}

					if (!employeeQuery.equals(null)) {
						PreparedStatement preparedStatement = session.connection().prepareStatement(employeeQuery);
						preparedStatement.executeUpdate();
						}
					}
				}
			

			query = "select emp.employee_no as empNo,emp.first_name,emp.last_name,"
					+"if(m.day1!='W' and m.day1!='A','P',m.day1) as day1, if(m.day1!='W' and m.day1!='A',SUBSTRING_INDEX(m.day1,'~',1),m.day1) as d1In , if(m.day1!='W' and m.day1!='A',IF(SUBSTRING_INDEX(m.day1,'~',1) = SUBSTRING_INDEX(m.day1,'~',-1), 'A',SUBSTRING_INDEX(m.day1,'~',-1)),m.day1) as d1Out, if(m.day1!='W' and m.day1!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s')),'N'),'N') as TD1,if(m.day1!='W' and m.day1!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT1,if(m.day1!='W' and m.day1!='A',if(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT1,if(m.day1!='W' and m.day1!='A',if(str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day1,'~',-1),'%H:%i:%s')),'N'),'N') as ET1,"+
					"if(m.day2!='W' and m.day2!='A','P',m.day2) as day2, if(m.day2!='W' and m.day2!='A',SUBSTRING_INDEX(m.day2,'~',1),m.day2) as d2In , if(m.day2!='W' and m.day2!='A',IF(SUBSTRING_INDEX(m.day2,'~',1) = SUBSTRING_INDEX(m.day2,'~',-1), 'A',SUBSTRING_INDEX(m.day2,'~',-1)),m.day2) as d2Out, if(m.day2!='W' and m.day2!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s')),'N'),'N') as TD2,if(m.day2!='W' and m.day2!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT2,if(m.day2!='W' and m.day2!='A',if(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT2,if(m.day2!='W' and m.day2!='A',if(str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day2,'~',-1),'%H:%i:%s')),'N'),'N') as ET2,"+					
					"if(m.day3!='W' and m.day3!='A','P',m.day3) as day3, if(m.day3!='W' and m.day3!='A',SUBSTRING_INDEX(m.day3,'~',1),m.day3) as d3In , if(m.day3!='W' and m.day3!='A',IF(SUBSTRING_INDEX(m.day3,'~',1) = SUBSTRING_INDEX(m.day3,'~',-1), 'A',SUBSTRING_INDEX(m.day3,'~',-1)),m.day3) as d3Out, if(m.day3!='W' and m.day3!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s')),'N'),'N') as TD3,if(m.day3!='W' and m.day3!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT3,if(m.day3!='W' and m.day3!='A',if(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT3,if(m.day3!='W' and m.day3!='A',if(str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day3,'~',-1),'%H:%i:%s')),'N'),'N') as ET3,"+
					"if(m.day4!='W' and m.day4!='A','P',m.day4) as day4, if(m.day4!='W' and m.day4!='A',SUBSTRING_INDEX(m.day4,'~',1),m.day4) as d4In , if(m.day4!='W' and m.day4!='A',IF(SUBSTRING_INDEX(m.day4,'~',1) = SUBSTRING_INDEX(m.day4,'~',-1), 'A',SUBSTRING_INDEX(m.day4,'~',-1)),m.day4) as d4Out, if(m.day4!='W' and m.day4!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s')),'N'),'N') as TD4,if(m.day4!='W' and m.day4!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT4,if(m.day4!='W' and m.day4!='A',if(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT4,if(m.day4!='W' and m.day4!='A',if(str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day4,'~',-1),'%H:%i:%s')),'N'),'N') as ET4,"+
					"if(m.day5!='W' and m.day5!='A','P',m.day5) as day5, if(m.day5!='W' and m.day5!='A',SUBSTRING_INDEX(m.day5,'~',1),m.day5) as d5In , if(m.day5!='W' and m.day5!='A',IF(SUBSTRING_INDEX(m.day5,'~',1) = SUBSTRING_INDEX(m.day5,'~',-1), 'A',SUBSTRING_INDEX(m.day5,'~',-1)),m.day5) as d5Out, if(m.day5!='W' and m.day5!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s')),'N'),'N') as TD5,if(m.day5!='W' and m.day5!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT5,if(m.day5!='W' and m.day5!='A',if(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT5,if(m.day5!='W' and m.day5!='A',if(str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day5,'~',-1),'%H:%i:%s')),'N'),'N') as ET5,"+
					"if(m.day6!='W' and m.day6!='A','P',m.day6) as day6, if(m.day6!='W' and m.day6!='A',SUBSTRING_INDEX(m.day6,'~',1),m.day6) as d6In , if(m.day6!='W' and m.day6!='A',IF(SUBSTRING_INDEX(m.day6,'~',1) = SUBSTRING_INDEX(m.day6,'~',-1), 'A',SUBSTRING_INDEX(m.day6,'~',-1)),m.day6) as d6Out, if(m.day6!='W' and m.day6!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s')),'N'),'N') as TD6,if(m.day6!='W' and m.day6!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT6,if(m.day6!='W' and m.day6!='A',if(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT6,if(m.day6!='W' and m.day6!='A',if(str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day6,'~',-1),'%H:%i:%s')),'N'),'N') as ET6,"+
					"if(m.day7!='W' and m.day7!='A','P',m.day7) as day7, if(m.day7!='W' and m.day7!='A',SUBSTRING_INDEX(m.day7,'~',1),m.day7) as d7In , if(m.day7!='W' and m.day7!='A',IF(SUBSTRING_INDEX(m.day7,'~',1) = SUBSTRING_INDEX(m.day7,'~',-1), 'A',SUBSTRING_INDEX(m.day7,'~',-1)),m.day7) as d7Out, if(m.day7!='W' and m.day7!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s')),'N'),'N') as TD7,if(m.day7!='W' and m.day7!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT7,if(m.day7!='W' and m.day7!='A',if(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT7,if(m.day7!='W' and m.day7!='A',if(str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day7,'~',-1),'%H:%i:%s')),'N'),'N') as ET7,"+
					"if(m.day8!='W' and m.day8!='A','P',m.day8) as day8, if(m.day8!='W' and m.day8!='A',SUBSTRING_INDEX(m.day8,'~',1),m.day8) as d8In , if(m.day8!='W' and m.day8!='A',IF(SUBSTRING_INDEX(m.day8,'~',1) = SUBSTRING_INDEX(m.day8,'~',-1), 'A',SUBSTRING_INDEX(m.day8,'~',-1)),m.day8) as d8Out, if(m.day8!='W' and m.day8!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s')),'N'),'N') as TD8,if(m.day8!='W' and m.day8!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT8,if(m.day8!='W' and m.day8!='A',if(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT8,if(m.day8!='W' and m.day8!='A',if(str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day8,'~',-1),'%H:%i:%s')),'N'),'N') as ET8,"+					                                                                                                            
					"if(m.day9!='W' and m.day9!='A','P',m.day9) as day9, if(m.day9!='W' and m.day9!='A',SUBSTRING_INDEX(m.day9,'~',1),m.day9) as d9In , if(m.day9!='W' and m.day9!='A',IF(SUBSTRING_INDEX(m.day9,'~',1) = SUBSTRING_INDEX(m.day9,'~',-1), 'A',SUBSTRING_INDEX(m.day9,'~',-1)),m.day9) as d9Out, if(m.day9!='W' and m.day9!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s')),'N'),'N') as TD9,if(m.day9!='W' and m.day9!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT9,if(m.day9!='W' and m.day9!='A',if(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT9,if(m.day9!='W' and m.day9!='A',if(str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day9,'~',-1),'%H:%i:%s')),'N'),'N') as ET9,"+
					"if(m.day10!='W' and m.day10!='A','P',m.day10) as day10, if(m.day10!='W' and m.day10!='A',SUBSTRING_INDEX(m.day10,'~',1),m.day10) as d10In , if(m.day10!='W' and m.day10!='A',IF(SUBSTRING_INDEX(m.day10,'~',1) = SUBSTRING_INDEX(m.day10,'~',-1), 'A',SUBSTRING_INDEX(m.day10,'~',-1)),m.day10) as d10Out, if(m.day10!='W' and m.day10!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s')),'N'),'N') as TD10,if(m.day10!='W' and m.day10!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT10,if(m.day10!='W' and m.day10!='A',if(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT10,if(m.day10!='W' and m.day10!='A',if(str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day10,'~',-1),'%H:%i:%s')),'N'),'N') as ET10,"+
					"if(m.day11!='W' and m.day11!='A','P',m.day11) as day11, if(m.day11!='W' and m.day11!='A',SUBSTRING_INDEX(m.day11,'~',1),m.day11) as d11In , if(m.day11!='W' and m.day11!='A',IF(SUBSTRING_INDEX(m.day11,'~',1) = SUBSTRING_INDEX(m.day11,'~',-1), 'A',SUBSTRING_INDEX(m.day11,'~',-1)),m.day11) as d11Out, if(m.day11!='W' and m.day11!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s')),'N'),'N') as TD11,if(m.day11!='W' and m.day11!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT11,if(m.day11!='W' and m.day11!='A',if(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT11,if(m.day11!='W' and m.day11!='A',if(str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day11,'~',-1),'%H:%i:%s')),'N'),'N') as ET11,"+
					"if(m.day12!='W' and m.day12!='A','P',m.day12) as day12, if(m.day12!='W' and m.day12!='A',SUBSTRING_INDEX(m.day12,'~',1),m.day12) as d12In , if(m.day12!='W' and m.day12!='A',IF(SUBSTRING_INDEX(m.day12,'~',1) = SUBSTRING_INDEX(m.day12,'~',-1), 'A',SUBSTRING_INDEX(m.day12,'~',-1)),m.day12) as d12Out, if(m.day12!='W' and m.day12!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s')),'N'),'N') as TD12,if(m.day12!='W' and m.day12!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT12,if(m.day12!='W' and m.day12!='A',if(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT12,if(m.day12!='W' and m.day12!='A',if(str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day12,'~',-1),'%H:%i:%s')),'N'),'N') as ET12,"+
					"if(m.day13!='W' and m.day13!='A','P',m.day13) as day13, if(m.day13!='W' and m.day13!='A',SUBSTRING_INDEX(m.day13,'~',1),m.day13) as d13In , if(m.day13!='W' and m.day13!='A',IF(SUBSTRING_INDEX(m.day13,'~',1) = SUBSTRING_INDEX(m.day13,'~',-1), 'A',SUBSTRING_INDEX(m.day13,'~',-1)),m.day13) as d13Out, if(m.day13!='W' and m.day13!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s')),'N'),'N') as TD13,if(m.day13!='W' and m.day13!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT13,if(m.day13!='W' and m.day13!='A',if(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT13,if(m.day13!='W' and m.day13!='A',if(str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day13,'~',-1),'%H:%i:%s')),'N'),'N') as ET13,"+
					"if(m.day14!='W' and m.day14!='A','P',m.day14) as day14, if(m.day14!='W' and m.day14!='A',SUBSTRING_INDEX(m.day14,'~',1),m.day14) as d14In , if(m.day14!='W' and m.day14!='A',IF(SUBSTRING_INDEX(m.day14,'~',1) = SUBSTRING_INDEX(m.day14,'~',-1), 'A',SUBSTRING_INDEX(m.day14,'~',-1)),m.day14) as d14Out, if(m.day14!='W' and m.day14!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s')),'N'),'N') as TD14,if(m.day14!='W' and m.day14!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT14,if(m.day14!='W' and m.day14!='A',if(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT14,if(m.day14!='W' and m.day14!='A',if(str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day14,'~',-1),'%H:%i:%s')),'N'),'N') as ET14,"+
					"if(m.day15!='W' and m.day15!='A','P',m.day15) as day15, if(m.day15!='W' and m.day15!='A',SUBSTRING_INDEX(m.day15,'~',1),m.day15) as d15In , if(m.day15!='W' and m.day15!='A',IF(SUBSTRING_INDEX(m.day15,'~',1) = SUBSTRING_INDEX(m.day15,'~',-1), 'A',SUBSTRING_INDEX(m.day15,'~',-1)),m.day15) as d15Out, if(m.day15!='W' and m.day15!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s')),'N'),'N') as TD15,if(m.day15!='W' and m.day15!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT15,if(m.day15!='W' and m.day15!='A',if(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT15,if(m.day15!='W' and m.day15!='A',if(str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day15,'~',-1),'%H:%i:%s')),'N'),'N') as ET15,"+
					"if(m.day16!='W' and m.day16!='A','P',m.day16) as day16, if(m.day16!='W' and m.day16!='A',SUBSTRING_INDEX(m.day16,'~',1),m.day16) as d16In , if(m.day16!='W' and m.day16!='A',IF(SUBSTRING_INDEX(m.day16,'~',1) = SUBSTRING_INDEX(m.day16,'~',-1), 'A',SUBSTRING_INDEX(m.day16,'~',-1)),m.day16) as d16Out, if(m.day16!='W' and m.day16!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s')),'N'),'N') as TD16,if(m.day16!='W' and m.day16!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT16,if(m.day16!='W' and m.day16!='A',if(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT16,if(m.day16!='W' and m.day16!='A',if(str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day16,'~',-1),'%H:%i:%s')),'N'),'N') as ET16,"+
					"if(m.day17!='W' and m.day17!='A','P',m.day17) as day17, if(m.day17!='W' and m.day17!='A',SUBSTRING_INDEX(m.day17,'~',1),m.day17) as d17In , if(m.day17!='W' and m.day17!='A',IF(SUBSTRING_INDEX(m.day17,'~',1) = SUBSTRING_INDEX(m.day17,'~',-1), 'A',SUBSTRING_INDEX(m.day17,'~',-1)),m.day17) as d17Out, if(m.day17!='W' and m.day17!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s')),'N'),'N') as TD17,if(m.day17!='W' and m.day17!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT17,if(m.day17!='W' and m.day17!='A',if(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT17,if(m.day17!='W' and m.day17!='A',if(str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day17,'~',-1),'%H:%i:%s')),'N'),'N') as ET17,"+
					"if(m.day18!='W' and m.day18!='A','P',m.day18) as day18, if(m.day18!='W' and m.day18!='A',SUBSTRING_INDEX(m.day18,'~',1),m.day18) as d18In , if(m.day18!='W' and m.day18!='A',IF(SUBSTRING_INDEX(m.day18,'~',1) = SUBSTRING_INDEX(m.day18,'~',-1), 'A',SUBSTRING_INDEX(m.day18,'~',-1)),m.day18) as d18Out, if(m.day18!='W' and m.day18!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s')),'N'),'N') as TD18,if(m.day18!='W' and m.day18!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT18,if(m.day18!='W' and m.day18!='A',if(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT18,if(m.day18!='W' and m.day18!='A',if(str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day18,'~',-1),'%H:%i:%s')),'N'),'N') as ET18,"+
					"if(m.day19!='W' and m.day19!='A','P',m.day19) as day19, if(m.day19!='W' and m.day19!='A',SUBSTRING_INDEX(m.day19,'~',1),m.day19) as d19In , if(m.day19!='W' and m.day19!='A',IF(SUBSTRING_INDEX(m.day19,'~',1) = SUBSTRING_INDEX(m.day19,'~',-1), 'A',SUBSTRING_INDEX(m.day19,'~',-1)),m.day19) as d19Out, if(m.day19!='W' and m.day19!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s')),'N'),'N') as TD19,if(m.day19!='W' and m.day19!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT19,if(m.day19!='W' and m.day19!='A',if(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT19,if(m.day19!='W' and m.day19!='A',if(str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day19,'~',-1),'%H:%i:%s')),'N'),'N') as ET19,"+
					"if(m.day20!='W' and m.day20!='A','P',m.day20) as day20, if(m.day20!='W' and m.day20!='A',SUBSTRING_INDEX(m.day20,'~',1),m.day20) as d20In , if(m.day20!='W' and m.day20!='A',IF(SUBSTRING_INDEX(m.day20,'~',1) = SUBSTRING_INDEX(m.day20,'~',-1), 'A',SUBSTRING_INDEX(m.day20,'~',-1)),m.day20) as d20Out, if(m.day20!='W' and m.day20!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s')),'N'),'N') as TD20,if(m.day20!='W' and m.day20!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT20,if(m.day20!='W' and m.day20!='A',if(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT20,if(m.day20!='W' and m.day20!='A',if(str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day20,'~',-1),'%H:%i:%s')),'N'),'N') as ET20,"+
					"if(m.day21!='W' and m.day21!='A','P',m.day21) as day21, if(m.day21!='W' and m.day21!='A',SUBSTRING_INDEX(m.day21,'~',1),m.day21) as d21In , if(m.day21!='W' and m.day21!='A',IF(SUBSTRING_INDEX(m.day21,'~',1) = SUBSTRING_INDEX(m.day21,'~',-1), 'A',SUBSTRING_INDEX(m.day21,'~',-1)),m.day21) as d21Out, if(m.day21!='W' and m.day21!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s')),'N'),'N') as TD21,if(m.day21!='W' and m.day21!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT21,if(m.day21!='W' and m.day21!='A',if(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT21,if(m.day21!='W' and m.day21!='A',if(str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day21,'~',-1),'%H:%i:%s')),'N'),'N') as ET21,"+
					"if(m.day22!='W' and m.day22!='A','P',m.day22) as day22, if(m.day22!='W' and m.day22!='A',SUBSTRING_INDEX(m.day22,'~',1),m.day22) as d22In , if(m.day22!='W' and m.day22!='A',IF(SUBSTRING_INDEX(m.day22,'~',1) = SUBSTRING_INDEX(m.day22,'~',-1), 'A',SUBSTRING_INDEX(m.day22,'~',-1)),m.day22) as d22Out, if(m.day22!='W' and m.day22!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s')),'N'),'N') as TD22,if(m.day22!='W' and m.day22!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT22,if(m.day22!='W' and m.day22!='A',if(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT22,if(m.day22!='W' and m.day22!='A',if(str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day22,'~',-1),'%H:%i:%s')),'N'),'N') as ET22,"+
					"if(m.day23!='W' and m.day23!='A','P',m.day23) as day23, if(m.day23!='W' and m.day23!='A',SUBSTRING_INDEX(m.day23,'~',1),m.day23) as d23In , if(m.day23!='W' and m.day23!='A',IF(SUBSTRING_INDEX(m.day23,'~',1) = SUBSTRING_INDEX(m.day23,'~',-1), 'A',SUBSTRING_INDEX(m.day23,'~',-1)),m.day23) as d23Out, if(m.day23!='W' and m.day23!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s')),'N'),'N') as TD23,if(m.day23!='W' and m.day23!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT23,if(m.day23!='W' and m.day23!='A',if(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT23,if(m.day23!='W' and m.day23!='A',if(str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day23,'~',-1),'%H:%i:%s')),'N'),'N') as ET23,"+
					"if(m.day24!='W' and m.day24!='A','P',m.day24) as day24, if(m.day24!='W' and m.day24!='A',SUBSTRING_INDEX(m.day24,'~',1),m.day24) as d24In , if(m.day24!='W' and m.day24!='A',IF(SUBSTRING_INDEX(m.day24,'~',1) = SUBSTRING_INDEX(m.day24,'~',-1), 'A',SUBSTRING_INDEX(m.day24,'~',-1)),m.day24) as d24Out, if(m.day24!='W' and m.day24!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s')),'N'),'N') as TD24,if(m.day24!='W' and m.day24!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT24,if(m.day24!='W' and m.day24!='A',if(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT24,if(m.day24!='W' and m.day24!='A',if(str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day24,'~',-1),'%H:%i:%s')),'N'),'N') as ET24,"+
					"if(m.day25!='W' and m.day25!='A','P',m.day25) as day25, if(m.day25!='W' and m.day25!='A',SUBSTRING_INDEX(m.day25,'~',1),m.day25) as d25In , if(m.day25!='W' and m.day25!='A',IF(SUBSTRING_INDEX(m.day25,'~',1) = SUBSTRING_INDEX(m.day25,'~',-1), 'A',SUBSTRING_INDEX(m.day25,'~',-1)),m.day25) as d25Out, if(m.day25!='W' and m.day25!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s')),'N'),'N') as TD25,if(m.day25!='W' and m.day25!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT25,if(m.day25!='W' and m.day25!='A',if(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT25,if(m.day25!='W' and m.day25!='A',if(str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day25,'~',-1),'%H:%i:%s')),'N'),'N') as ET25,"+
					"if(m.day26!='W' and m.day26!='A','P',m.day26) as day26, if(m.day26!='W' and m.day26!='A',SUBSTRING_INDEX(m.day26,'~',1),m.day26) as d26In , if(m.day26!='W' and m.day26!='A',IF(SUBSTRING_INDEX(m.day26,'~',1) = SUBSTRING_INDEX(m.day26,'~',-1), 'A',SUBSTRING_INDEX(m.day26,'~',-1)),m.day26) as d26Out, if(m.day26!='W' and m.day26!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s')),'N'),'N') as TD26,if(m.day26!='W' and m.day26!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT26,if(m.day26!='W' and m.day26!='A',if(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT26,if(m.day26!='W' and m.day26!='A',if(str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day26,'~',-1),'%H:%i:%s')),'N'),'N') as ET26,"+
					"if(m.day27!='W' and m.day27!='A','P',m.day27) as day27, if(m.day27!='W' and m.day27!='A',SUBSTRING_INDEX(m.day27,'~',1),m.day27) as d27In , if(m.day27!='W' and m.day27!='A',IF(SUBSTRING_INDEX(m.day27,'~',1) = SUBSTRING_INDEX(m.day27,'~',-1), 'A',SUBSTRING_INDEX(m.day27,'~',-1)),m.day27) as d27Out, if(m.day27!='W' and m.day27!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s')),'N'),'N') as TD27,if(m.day27!='W' and m.day27!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT27,if(m.day27!='W' and m.day27!='A',if(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT27,if(m.day27!='W' and m.day27!='A',if(str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day27,'~',-1),'%H:%i:%s')),'N'),'N') as ET27,"+
					"if(m.day28!='W' and m.day28!='A','P',m.day28) as day28, if(m.day28!='W' and m.day28!='A',SUBSTRING_INDEX(m.day28,'~',1),m.day28) as d28In , if(m.day28!='W' and m.day28!='A',IF(SUBSTRING_INDEX(m.day28,'~',1) = SUBSTRING_INDEX(m.day28,'~',-1), 'A',SUBSTRING_INDEX(m.day28,'~',-1)),m.day28) as d28Out, if(m.day28!='W' and m.day28!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s')),'N'),'N') as TD28,if(m.day28!='W' and m.day28!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT28,if(m.day28!='W' and m.day28!='A',if(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT28,if(m.day28!='W' and m.day28!='A',if(str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day28,'~',-1),'%H:%i:%s')),'N'),'N') as ET28,"+
					"IF(if(m.day29!='W' and m.day29!='A' and m.day29!=-0,'P',m.day29)= '0','N',if(m.day29!='W' and m.day29!='A','P',m.day29)) as day29, IF(if(m.day29!='W' and m.day29!='A',SUBSTRING_INDEX(m.day29,'~',1),m.day29)= '0','N',if(m.day29!='W' and m.day29!='A',SUBSTRING_INDEX(m.day29,'~',1),m.day29)) as d29In , IF(if(m.day29!='W' and m.day29!='A',IF(SUBSTRING_INDEX(m.day29,'~',1) = SUBSTRING_INDEX(m.day29,'~',-1), 'A',SUBSTRING_INDEX(m.day29,'~',-1)),m.day29)= '0','N',if(m.day29!='W' and m.day29!='A',IF(SUBSTRING_INDEX(m.day29,'~',1) = SUBSTRING_INDEX(m.day29,'~',-1), 'A',SUBSTRING_INDEX(m.day29,'~',-1)),m.day29)) as d29Out ,if(m.day29!='W' and m.day29!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s')),'N'),'N') as TD29,if(m.day29!='W' and m.day29!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT29,if(m.day29!='W' and m.day29!='A',if(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT29,if(m.day29!='W' and m.day29!='A',if(str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day29,'~',-1),'%H:%i:%s')),'N'),'N') as ET29,"+
					"IF(if(m.day30!='W' and m.day30!='A' and m.day30!=0,'P',m.day30)= '0','N',if(m.day30!='W' and m.day30!='A','P',m.day30)) as day30, IF(if(m.day30!='W' and m.day30!='A',SUBSTRING_INDEX(m.day30,'~',1),m.day30)= '0','N',if(m.day30!='W' and m.day30!='A',SUBSTRING_INDEX(m.day30,'~',1),m.day30)) as d30In , IF(if(m.day30!='W' and m.day30!='A',IF(SUBSTRING_INDEX(m.day30,'~',1) = SUBSTRING_INDEX(m.day30,'~',-1), 'A',SUBSTRING_INDEX(m.day30,'~',-1)),m.day30)= '0','N',if(m.day30!='W' and m.day30!='A',IF(SUBSTRING_INDEX(m.day30,'~',1) = SUBSTRING_INDEX(m.day30,'~',-1), 'A',SUBSTRING_INDEX(m.day30,'~',-1)),m.day30)) as d30Out ,if(m.day30!='W' and m.day30!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s')),'N'),'N') as TD30,if(m.day30!='W' and m.day30!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT30,if(m.day30!='W' and m.day30!='A',if(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT30,if(m.day30!='W' and m.day30!='A',if(str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day30,'~',-1),'%H:%i:%s')),'N'),'N') as ET30,"+
					"IF(if(m.day31!='W' and m.day31!='A' and m.day31!=0,'P',m.day31)= '0','N',if(m.day31!='W' and m.day31!='A','P',m.day31)) as day31, IF(if(m.day31!='W' and m.day31!='A',SUBSTRING_INDEX(m.day31,'~',1),m.day31)= '0','N',if(m.day31!='W' and m.day31!='A',SUBSTRING_INDEX(m.day31,'~',1),m.day31)) as d31In, IF(if(m.day31!='W' and m.day31!='A',IF(SUBSTRING_INDEX(m.day31,'~',1) = SUBSTRING_INDEX(m.day31,'~',-1), 'A',SUBSTRING_INDEX(m.day31,'~',-1)),m.day31)= '0','N',if(m.day31!='W' and m.day31!='A',IF(SUBSTRING_INDEX(m.day31,'~',1) = SUBSTRING_INDEX(m.day31,'~',-1), 'A',SUBSTRING_INDEX(m.day31,'~',-1)),m.day31)) as d31Out  , if(m.day31!='W' and m.day31!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s')))<=8,TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s')),'N'),'N') as TD31,if(m.day31!='W' and m.day31!='A',if(LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')))<=8,if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s'))>str_to_date('"+grace_time+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',1),'%H:%i:%s'),str_to_date('"+shiftStartTime+"','%H:%i:%s')),'N'),'N'),'N') as LT31,if(m.day31!='W' and m.day31!='A',if(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s')>str_to_date('"+shiftEndTime+"','%H:%i:%s'),if(TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s'))>str_to_date('"+gt+"','%H:%i:%s'),TIMEDIFF(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s'),str_to_date('"+shiftEndTime+"','%H:%i:%s')),'N'),'N'),'N') as OT31,if(m.day31!='W' and m.day31!='A',if(str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s')<str_to_date('"+shiftEndTime+"','%H:%i:%s'),TIMEDIFF(str_to_date('"+shiftEndTime+"','%H:%i:%s'),str_to_date(SUBSTRING_INDEX(m.day31,'~',-1),'%H:%i:%s')),'N'),'N') as ET31,m.musterMonth,m.musterYear,m.absentCount,m.presentCount from musterreport m inner join employees emp on m.empNo=emp.employee_no where m.empNo in (select employee_id from cal_employee_shifts where shiftid="+shiftId+")";
			ps=connection.prepareStatement(query);
			resultSet=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultSet getEmployeeTableForExport() {
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			query = "select * from employees";
			ps = connection.prepareStatement(query);
			resultSet = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importEmployeeTableData(String filePath) {
		String backSl = "////";

		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(filePath);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {

			if (character == '\\') {
				result.append("/");
			} else {
				result.append(character);
			}

			character = iterator.next();
		}

		System.out.println("----------" + result);
		/*
		 * String my_new_str = filePath.replaceAll("/\","//");
		 */ String filep = "E://DISTNA(Final20thAugust)//.metadata//.plugins//org.eclipse.wst.server.core//tmp0//wtpwebapps//DISTNA//EmployeeCsvFiles//employee.csv";
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			query = "LOAD DATA INFILE '" + result
					+ "' REPLACE INTO TABLE employees FIELDS TERMINATED BY ',' Lines terminated by '\r\n' IGNORE 1 LINES (employee_id, first_name,last_name, email, department_id, password, active, employee_no, introduction, photo, designation, date_of_birth, joining_date, allowed_leaves, leaves_start_date, supervisor, gender, marital_status, identification_card, permanent_address, current_address, emergency_contact, relatives_friends, company_friends, yrs_experience, education, languages, location, skills, children_count, personal_emails, all_phones, identification_no, resume, all_chat_ids, employment_status, privledge_by, mobile, document1, document2, document3, document4, home_distance, oneway_time, travel_mode, home_gpslocation, pan_no, workspace, job_code, shift_default, current_city, current_country, permanent_city, permanent_country, current_state, permanent_state, current_address2, permanent_address2, created_on, created_by, updated_on, updated_by, password_reset_date, middle_name, father_husband_name, current_postal_code, permanent_postal_code, work_phone, work_phone_ext, home_phone, fax_phone, home_email, termination_date, notes, currency, timezone, time_format, group_id, probation, grade_level, income_tax_id, driving_license_id, passport_no, civil_id, insurance_id, state_fund_id, state_insurance_id, social_security_id, health_insurance_id, other_id, bank_name, bank_branch_name, branch_full_address, account_no, other_bank_details, highest_qualifications, passing_year, college_name, university_name, signature_scan, finger_scan1, finger_scan2, photo2, photo3, secret_question1, secret_answer1, secret_question2, secret_answer2, division, adminFlag, rediffChatId, gtalkChatId, msnChatId, yahooChatId, skypeChatId, leavesUsed, confirmation_date);";
			ps = connection.prepareStatement(query);
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importTableData(String filePath) {
		String backSl = "////";
		File file = new File(filePath);
		List<String> lines = new ArrayList<String>();
		String columnNames = "";
		String tableName = file.getName().split(" ")[0];
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			columnNames = lines.get(0).substring(0, lines.get(0).lastIndexOf(","));
			System.out.println(columnNames);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(filePath);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {

			if (character == '\\') {
				result.append("/");
			} else {
				result.append(character);
			}

			character = iterator.next();
		}
		System.out.println("----------" + result);
		/*
		 * String my_new_str = filePath.replaceAll("/\","//");
		 */ String filep = "E://DISTNA(Final20thAugust)//.metadata//.plugins//org.eclipse.wst.server.core//tmp0//wtpwebapps//DISTNA//EmployeeCsvFiles//employee.csv";
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			query = "LOAD DATA INFILE '" + result + "' REPLACE INTO TABLE " + tableName
					+ " FIELDS TERMINATED BY ',' Lines terminated by '\r\n' IGNORE 1 LINES (" + columnNames + ");";
			ps = connection.prepareStatement(query);
			ps.executeQuery();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public ResultSet getMaharastraMusterRollReport(int shiftId, int workId, String empReportType,
			String selectedcheckbox, String month, String year) {
		// TODO Auto-generated method stub
		
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		SimpleDateFormat Date12 = new SimpleDateFormat("d-M-yyyy");
		String date1 = Date12.format(new Date());
		
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();
			String campareDate = null;
			PreparedStatement pstmt = connection.prepareStatement("truncate musterreport");
			pstmt.execute();
			if (Integer.parseInt(month) != 12) {
				campareDate = (Integer.parseInt(month) + 1) + "-" + year;
			} else {
				campareDate = (Integer.parseInt(month) + 1) + "-" + 1;
			}

			SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
			Date fromDate;
			List<Date> dates = new ArrayList<Date>();
			List<Employee> employeesList = hibernateTemplate.find("from Employee");

			try {
				fromDate = format.parse("1-" + month + "-" + year);
				Date toDate = format.parse("1-" + campareDate);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(fromDate);
				while (calendar.getTime().before(toDate)) {
					Date resultado = calendar.getTime();
					dates.add(resultado);
					calendar.add(Calendar.DATE, 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			boolean checkAbsentEmployee = false;
			MusterReport musterReport = new MusterReport();
			for (Employee employeesObj : employeesList) {
				String employeeQuery = null;
				for (int i = 0; i < dates.size(); i++) {
					String tempMonth = format.format(dates.get(i)).split("-")[1];
					String tempDate = format.format(dates.get(i)).split("-")[0];
					String tempYear = format.format(dates.get(i)).split("-")[2];
					List<ShiftDefinition> shiftDefinitionsList = hibernateTemplate
							.find("from ShiftDefinition where shiftid=" + shiftId);
					List<AttendanceLogsBulkEntry> attendanceLogsBulkEntriesList = hibernateTemplate
							.find("from AttendanceLogsBulkEntry where workID=" + employeesObj.getEmployeeNo()
									+ " and recordDate Like '" + format.format(dates.get(i)) + "'");
					List<MusterReport> alreadyPresentRecord = hibernateTemplate
							.find("from MusterReport where employeeNo=" + employeesObj.getEmployeeNo() + " and month='"
									+ tempMonth + "' and year='" + tempYear + "'");

					if (shiftDefinitionsList.get(0).getWeeklyOff1Day().equals((dates.get(i).getDay() + 1) + "")
							|| shiftDefinitionsList.get(0).getWeeklyOff2Day()
									.equals((dates.get(i).getDay() + 1) + "")) {
						if (alreadyPresentRecord.size() != 0) {
							int id = alreadyPresentRecord.get(0).getId();
							employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='W' where id=" + id;
						} else {
							employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
									+ ")  VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth + "','" + tempYear
									+ "','WO')";
						}
					} else {
						if (attendanceLogsBulkEntriesList.size() != 0) {
							if (alreadyPresentRecord.size() != 0) {
								int preCount = alreadyPresentRecord.get(0).getPresentCount() + 1;
								int id = alreadyPresentRecord.get(0).getId();
								employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='P',presentCount="
										+ preCount + " where id=" + id;
							} else {
								employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
										+ ",presentCount)  VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth
										+ "','" + tempYear + "','P',1)";
							}
						} else {
							if (alreadyPresentRecord.size() != 0) {
								int abCount = alreadyPresentRecord.get(0).getAbsentCount() + 1;
								int id = alreadyPresentRecord.get(0).getId();
								employeeQuery = "UPDATE MusterReport SET day" + tempDate + "='A',absentCount=" + abCount
										+ " where id=" + id;
							} else {
								employeeQuery = "INSERT INTO MusterReport (empNo,musterMonth,musterYear,day" + tempDate
										+ ",absentCount) VALUES (" + employeesObj.getEmployeeNo() + ",'" + tempMonth
										+ "','" + tempYear + "','A',1)";
							}
						}
					}

					if (!employeeQuery.equals(null)) {
						PreparedStatement preparedStatement = session.connection().prepareStatement(employeeQuery);
						preparedStatement.executeUpdate();
					}
				}
			}

			if (empReportType.equals("single")) {
				query = "SELECT e.employee_id as empNo,e.first_name,e.last_name,e.gender as gender,Floor(abs(DATEDIFF(STR_TO_DATE(e.date_of_birth,'%d-%m-%Y'),STR_TO_DATE(att.Recorddate,'%d-%m-%Y')) / 365.25 ))as age,e.joining_date,s.TimeField,s.EndTime,s.break1StartTime as lunch1StartTime,s.break1EndTime as lunch1EndTime,s.break2StartTime as lunch2StartTime,s.break2EndTime as lunch2EndTime,sum(l.yearlyLimit) as totalLeaves,sum(al.value) as enjoyedleaves,(sum(l.yearlyLimit)-sum(al.value)) as balanceleaves,m.day1,m.day2,m.day3,m.day4,m.day5,m.day6,m.day7,m.day8,m.day9,m.day10,m.day11,m.day12,m.day13,m.day14,m.day15, m.day16, m.day17, m.day18, m.day19, m.day20, m.day21, m.day22, m.day23, m.day24, m.day25, m.day26, m.day27, m.day28,IF(m.day29 = '0', 'N',m.day29) as day29, IF(m.day30 = '0', 'N',m.day30) as day30,IF(m.day31 = '0', 'N',m.day31) as day31,m.absentCount, m.presentCount FROM employees e inner join cal_employee_shifts c on e.employee_id=c.employee_id inner join calcalendar s on c.shiftid=s.shiftid inner join musterreport m on e.employee_id=m.empNo left join allowed_leaves al on e.employee_id=al.employee_id left join leave_type l on al.leavetype=l.id where e.employee_id="
						+ workId + " group by e.employee_id";

			} else if (empReportType.equals("multiple")) {
				String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
				query = "SELECT e.employee_id as empNo,e.first_name,e.last_name,e.gender as gender,Floor(abs(DATEDIFF(STR_TO_DATE(e.date_of_birth,'%d-%m-%Y'),STR_TO_DATE(att.Recorddate,'%d-%m-%Y')) / 365.25 ))as age,e.joining_date,s.TimeField,s.EndTime,s.break1StartTime as lunch1StartTime,s.break1EndTime as lunch1EndTime,s.break2StartTime as lunch2StartTime,s.break2EndTime as lunch2EndTime,sum(l.yearlyLimit) as totalLeaves,sum(al.value) as enjoyedleaves,(sum(l.yearlyLimit)-sum(al.value)) as balanceleaves,m.day1,m.day2,m.day3,m.day4,m.day5,m.day6,m.day7,m.day8,m.day9,m.day10,m.day11,m.day12,m.day13,m.day14,m.day15, m.day16, m.day17, m.day18, m.day19, m.day20, m.day21, m.day22, m.day23, m.day24, m.day25, m.day26, m.day27, m.day28,IF(m.day29 = '0', 'N',m.day29) as day29, IF(m.day30 = '0', 'N',m.day30) as day30,IF(m.day31 = '0', 'N',m.day31) as day31,m.absentCount, m.presentCount FROM employees e inner join cal_employee_shifts c on e.employee_id=c.employee_id inner join calcalendar s on c.shiftid=s.shiftid inner join musterreport m on e.employee_id=m.empNo left join allowed_leaves al on e.employee_id=al.employee_id left join leave_type l on al.leavetype=l.id where e.employee_id IN ("
						+ selectedEmployees + ") group by e.employee_id";
			} else {
				query = "SELECT e.employee_id as empNo,e.first_name,e.last_name,e.gender as gender,Floor(abs(DATEDIFF(STR_TO_DATE(e.date_of_birth,'%d-%m-%Y'),STR_TO_DATE(att.Recorddate,'%d-%m-%Y')) / 365.25 ))as age,e.joining_date as joining_date ,s.TimeField,s.EndTime,s.break1StartTime as lunch1StartTime,s.break1EndTime as lunch1EndTime,s.break2StartTime as lunch2StartTime,s.break2EndTime as lunch2EndTime,sum(l.yearlyLimit) as totalLeaves,sum(al.value) as enjoyedleaves,(sum(l.yearlyLimit)-sum(al.value)) as balanceleaves,m.day1,m.day2,m.day3,m.day4,m.day5,m.day6,m.day7,m.day8,m.day9,m.day10,m.day11,m.day12,m.day13,m.day14,m.day15, m.day16, m.day17, m.day18, m.day19, m.day20, m.day21, m.day22, m.day23, m.day24, m.day25, m.day26, m.day27, m.day28,IF(m.day29 = '0', 'N',m.day29) as day29, IF(m.day30 = '0', 'N',m.day30) as day30,IF(m.day31 = '0', 'N',m.day31) as day31,m.absentCount, m.presentCount FROM attendance_logs_bulk_entry as att, employees e inner join cal_employee_shifts c on e.employee_id=c.employee_id inner join calcalendar s on c.shiftid=s.shiftid inner join musterreport m on e.employee_id=m.empNo left join allowed_leaves al on e.employee_id=al.employee_id left join leave_type l on al.leavetype=l.id where c.shiftid="
						+ shiftId + " group by e.employee_id";
			}

			// query="select first_name, last_name, email, date_of_birth from employees
			// where department_id="+departmentId;
			try {
				ps = connection.prepareStatement(query);
				resultSet = ps.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public ResultSet getMemoReport(int employeeId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int departmentId) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
			query = "SELECT emp.first_name, emp.last_name, msg.messageDate, msg.messageSubject FROM employee_messages as msg inner join employees as emp on msg.employeeId=emp.employee_id where msg.isMemo=1 and msg.employeeId="
					+ employeeId + " and (str_to_date(msg.messageDate, '%d-%m-%Y') between str_to_date('" + dateFrom
					+ "', '%d-%m-%Y') and str_to_date('" + dateTo
					+ "', '%d-%m-%Y')) order by  emp.last_name, str_to_date(msg.messageDate, '%d-%m-%Y')";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "SELECT emp.first_name, emp.last_name, msg.messageDate, msg.messageSubject FROM employee_messages as msg inner join employees as emp on msg.employeeId=emp.employee_id where msg.isMemo=1 and msg.employeeId in("
					+ selectedEmployees
					+ ") and (str_to_date(msg.messageDate, '%d-%m-%Y') between str_to_date('"+dateFrom
					+"', '%d-%m-%Y') and str_to_date('"+dateTo
					+"', '%d-%m-%Y')) order by  emp.last_name, str_to_date(msg.messageDate, '%d-%m-%Y')";
		} else {
			query = "SELECT emp.first_name, emp.last_name, msg.messageDate, msg.messageSubject FROM employee_messages as msg inner join employees as emp on msg.employeeDepartmentId=emp.department_id where msg.isMemo=1  and (str_to_date(msg.messageDate, '%d-%m-%Y') between str_to_date('01-09-2013', '%d-%m-%Y') and str_to_date('08-09-2013', '%d-%m-%Y')) order by  emp.last_name, str_to_date(msg.messageDate, '%d-%m-%Y')";
		}

		// query="select first_name, last_name, email, date_of_birth from employees
		// where department_id="+departmentId;
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;

	}

	@Override
	public ResultSet getExtraWorkReport(int employeeId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int departmentId) {
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (empReportType.equals("single")) {
//					query="SELECT emp.first_name, emp.last_name, msg.messageDate, msg.messageSubject FROM employee_messages as msg inner join employees as emp on msg.employeeId=emp.employee_id where msg.isMemo=1 and msg.employeeId="+employeeId+" and (str_to_date(msg.messageDate, '%d-%m-%Y') between str_to_date('"+dateFrom+"', '%d-%m-%Y') and str_to_date('"+dateTo+"', '%d-%m-%Y')) order by  emp.last_name, str_to_date(msg.messageDate, '%d-%m-%Y')";
			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1), 'A', SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)) as OutTime, IF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00' ,TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'))) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1) , '00:00:00' , IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00:', TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s')), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) )) as ExtraWork  "
					+ "FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
					+ "where emp.employee_id=" + employeeId
					+ " and IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00') AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
					+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1), 'A', SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)) as OutTime, IF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00' ,TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'))) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1) , '00:00:00' , IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00:', TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s')), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) )) as ExtraWork  "
					+ "FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
					+ "where emp.employee_id in (" + selectedEmployees
					+ ") and IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00') AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
					+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		} else {
			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1), 'A', SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)) as OutTime, IF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00' ,TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'))) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1) , '00:00:00' , IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00:', TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s')), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) )) as ExtraWork  "
					+ "FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
					+ "where emp.department_id=" + departmentId
					+ " and IF(TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00' , TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s' )), TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' ))) > '00:00:00') AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
					+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		}


		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getCanteenItemsReport() {
		// TODO Auto-generated method stub

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		query = "SELECT itemName,itemShortName,employeeContribution,employerContribution FROM canteen_items";

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getCanteenTimingsReport() {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		query = "SELECT ct.timingName, ct.shortName,ct.startTime,ct.endTime,ci.itemName FROM canteen_timings as ct inner join canteen_items as ci on ct.defaultItem=ci.itemsId order by STR_TO_DATE(ct.startTime,'%H:%i:%s');";

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getVisitorDailyReport(String reportType, int visitorId, int employeeId, String fromDate,
			String toDate) {
		// TODO Auto-generated method stub

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();

		if (reportType.equals("VisitorWise")) {
			query = "SELECT emp.first_name, emp.last_name, vis.status, vis.purpose, vis.inTime,vis.outTime,vis.materialsCarried,vid.visitorPhoto  "
					+ "FROM visitor_logs as vis inner join visitors as vid on vis.visitorId=vid.visitorId inner join employees as emp on vis.employeeId=emp.employee_id "
					+ "where vis.visitorId=" + visitorId
					+ " and STR_TO_DATE(vis.inTime,'%d-%m-%Y %H:%i:%s') between STR_TO_DATE('" + fromDate
					+ "','%d-%m-%Y') and STR_TO_DATE('" + toDate
					+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vis.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('"
					+ fromDate
					+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vis.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('"
					+ toDate + "','%d-%m-%Y') "
					+ "order by emp.last_name, emp.first_name,STR_TO_DATE(vis.inTime,'%d-%m-%Y %H:%i:%s')";
		}

		if (reportType.equals("EmployeeWise")) {
			query = "SELECT vis.visitorName, vil.status, vil.purpose, vil.inTime, vil.outTime,vil.materialsCarried,vis.visitorPhoto  "
					+ "from visitor_logs as vil inner join visitors as vis on vis.visitorId=vil.visitorId "
					+ "where vil.employeeId=" + employeeId
					+ " and STR_TO_DATE(vil.inTime,'%d-%m-%Y %H:%i:%s') between STR_TO_DATE('" + fromDate
					+ "','%d-%m-%Y') and STR_TO_DATE('" + toDate
					+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vil.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('"
					+ fromDate
					+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vil.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('"
					+ toDate + "','%d-%m-%Y') "
					+ "order by vis.visitorName, STR_TO_DATE(vil.inTime,'%d-%m-%Y %H:%i:%s')";
		}

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}
	
	

//	@Override
//	public ResultSet getVisitorFrequencyReport(int visitorId, String selectedcheckbox, String visitorReportType) {
//		// TODO Auto-generated method stub
//		ResultSet resultSet = null;
//		Session session = null;
//		String query = null;
//		PreparedStatement preparedStatement = null;
//
//		session = sessionFactory.openSession();
//		Connection connection = session.connection();
//
//		if (visitorReportType.equals("multiple")) {
//			String selectedVisitors = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
//			query = "SELECT  visitorName,visitorType,  company,  location,  mobileNo, emailId, address, visitCount FROM visitors where visitorId in ("
//					+ selectedVisitors + ") order by visitorName";
//		}
//
//		else if (visitorReportType.equals("single")) {
//			query = "SELECT  visitorName,visitorType,  company,  location,  mobileNo, emailId, address, visitCount FROM visitors where visitorId="
//					+ visitorId;
//		}
//
//		else {
//			query = "SELECT visitorName ,visitorType , company ,  location , mobileNo , emailId ,address , visitCount  FROM visitors   order by visitorName";
//		}
//
//		try {
//			preparedStatement = connection.prepareStatement(query);
//			resultSet = preparedStatement.executeQuery();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return resultSet;
//	}
	
	@Override
	public ResultSet getVisitorFrequencyReport(int visitorId,String selectedcheckbox, String visitorReportType) {
		// TODO Auto-generated method stub
		ResultSet resultSet=null;
		Session session=null;
		String query=null;
		PreparedStatement preparedStatement=null;
		
		session=sessionFactory.openSession();
		Connection connection=session.connection();
		
		if(visitorReportType.equals("multiple"))
		{
			String selectedVisitors=selectedcheckbox.substring(0,selectedcheckbox.length()-1);
			query="SELECT  visitorName,visitorType,  company,  location,  mobileNo, emailId, address, visitCount FROM visitors where visitorId in ("+selectedVisitors+") order by visitorName";
		}
		
		else if(visitorReportType.equals("single"))
		{
			query="SELECT  visitorName,visitorType,  company,  location,  mobileNo, emailId, address, visitCount FROM visitors where visitorId="+visitorId;
		}
		
		else
		{
			query="SELECT  visitorName,visitorType,  company,  location,  mobileNo, emailId, address, visitCount FROM visitors order by visitorName";
		}
		
		try {
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	
	
	

	@Override
	public ResultSet getVehicleLogsWoPhotoReport(String dateFrom, String dateTo, String concernPerson, String status,
			String filter, String vehicleNumber, String vehicleType) {

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();
		query = "select vd.vehicleNumber, vd.vehicleBrand, vd.vehicleModel, vd.vehicleEdition, vd.vehicleType, vd.ownerName, vd.ownerPhoneNo,vl.status, vl.inTime, vl.outTime,E.first_name,E.last_name,vl.materialsCarried "
				+ " from vehicle_details as vd inner join vehicle_logs as vl on vd.vehicleId=vl.vehicleId inner join employees as E on vl.concernPerson=E.employee_Id"
				+ " where";
		int length = 0;
		int lastoccurrance = 0;

		if (!concernPerson.equals("0")) {
			length = query.length();
			lastoccurrance = query.lastIndexOf("where");
			if (length - lastoccurrance != 5)
				query = query + " and";
			query = query + " vl.concernPerson='" + concernPerson + "'";
		}

		if (!status.equals("0")) {
			length = query.length();
			lastoccurrance = query.lastIndexOf("where");
			if (length - lastoccurrance != 5)
				query = query + " and";
			query = query + " vl.status='" + status + "'";
		}

		if (null != filter) {
			/*
			 * length=query.length(); lastoccurrance=query.lastIndexOf("where");
			 * if(length-lastoccurrance!=5) query=query+" and";
			 * query=query+" vd.vehicleNumber='"+vehicleNumber+"' and vd.vehicleType='"
			 * +vehicleType+"'";
			 */

			if (!vehicleNumber.equals("0")) {
				length = query.length();
				lastoccurrance = query.lastIndexOf("where");
				if (length - lastoccurrance != 5)
					query = query + " and";
				query = query + " vd.vehicleNumber='" + vehicleNumber + "'";
			}

			if (vehicleType.equals("")) {
				length = query.length();
				lastoccurrance = query.lastIndexOf("where");
				if (length - lastoccurrance != 5)
					query = query + " and";
				query = query + " vd.vehicleType='" + vehicleType + "'";
			}
		}

		length = query.length();
		lastoccurrance = query.lastIndexOf("where");
		if (length - lastoccurrance != 5)
			query = query + " and";
		query = query + " STR_TO_DATE(vl.inTime,'%d-%m-%Y %H:%i:%s') between STR_TO_DATE('" + dateFrom
				+ "','%d-%m-%Y') and STR_TO_DATE('" + dateTo
				+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vl.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('" + dateFrom
				+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vl.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('" + dateTo
				+ "','%d-%m-%Y')  order by STR_TO_DATE(vl.inTime,'%d-%m-%Y %H:%i:%s') ;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	@Override
	public ResultSet getVehicleFrequencyLogs(String dateFrom, String dateTo, String concernPerson, String status,
			String filter, String vehicleNumber, String vehicleType) {

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();
		query = "select vd.vehicleNumber, vd.vehicleBrand, vd.vehicleModel, vd.vehicleEdition, vd.vehicleType, vd.ownerName, vd.ownerPhoneNo, count(vl.vehicleId) as vehiclefrequency ,vl.status,E.first_name,E.last_name"
				+ " from vehicle_details as vd inner join vehicle_logs as vl on vd.vehicleId=vl.vehicleId inner join employees as E on vl.concernPerson=E.employee_Id"
				+ " where";
		int length = 0;
		int lastoccurrance = 0;

		if (!concernPerson.equals("0")) {
			length = query.length();
			lastoccurrance = query.lastIndexOf("where");
			if (length - lastoccurrance != 5)
				query = query + " and";
			query = query + " vl.concernPerson='" + concernPerson + "'";
		}

		if (!status.equals("0")) {
			length = query.length();
			lastoccurrance = query.lastIndexOf("where");
			if (length - lastoccurrance != 5)
				query = query + " and";
			query = query + " vl.status='" + status + "'";
		}

		if (null != filter) {
			if (!vehicleNumber.equals("0")) {
				length = query.length();
				lastoccurrance = query.lastIndexOf("where");
				if (length - lastoccurrance != 5)
					query = query + " and";
				query = query + " vd.vehicleNumber='" + vehicleNumber + "'";
			}

			if (vehicleType.equals("")) {
				length = query.length();
				lastoccurrance = query.lastIndexOf("where");
				if (length - lastoccurrance != 5)
					query = query + " and";
				query = query + " vd.vehicleType='" + vehicleType + "'";
			}
		}

		length = query.length();
		lastoccurrance = query.lastIndexOf("where");
		if (length - lastoccurrance != 5)
			query = query + " and";
		query = query + " STR_TO_DATE(vl.inTime,'%d-%m-%Y %H:%i:%s') between STR_TO_DATE('" + dateFrom
				+ "','%d-%m-%Y') and STR_TO_DATE('" + dateTo
				+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vl.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('" + dateFrom
				+ "','%d-%m-%Y') or STR_TO_DATE(substring_index(vl.inTime,' ',1),'%d-%m-%Y')=STR_TO_DATE('" + dateTo
				+ "','%d-%m-%Y') group by vl.vehicleId order by STR_TO_DATE(vl.inTime,'%d-%m-%Y %H:%i:%s') ;";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ResultSet getTableForExport(String tableName) {

		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		java.sql.PreparedStatement ps = null;
		try {
			session = sessionFactory.openSession();
			java.sql.Connection connection = session.connection();

			/*
			 * DatabaseMetaData databaseMetaData = connection.getMetaData(); String[] types
			 * = {"TABLE"}; ResultSet rs = databaseMetaData.getTables(null, null, "%",
			 * types); while (rs.next()) { System.out.println(rs.getString("TABLE_NAME")); }
			 */

			query = "select * from " + tableName;
			ps = connection.prepareStatement(query);
			resultSet = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ResultSet getShortWorkReport(int employeeId, String dateFrom, String dateTo, String empReportType,
			String selectedcheckbox, int departmentId) {
		ResultSet resultSet = null;
		Session session = null;
		String query = null;
		PreparedStatement preparedStatement = null;

		session = sessionFactory.openSession();
		Connection connection = session.connection();



		

		if (empReportType.equals("single")) {
//					query="SELECT emp.first_name, emp.last_name, msg.messageDate, msg.messageSubject FROM employee_messages as msg inner join employees as emp on msg.employeeId=emp.employee_id where msg.isMemo=1 and msg.employeeId="+employeeId+" and (str_to_date(msg.messageDate, '%d-%m-%Y') between str_to_date('"+dateFrom+"', '%d-%m-%Y') and str_to_date('"+dateTo+"', '%d-%m-%Y')) order by  emp.last_name, str_to_date(msg.messageDate, '%d-%m-%Y')";
			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1), 'A', SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)) as OutTime, TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00', TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')))as ShortWork "
					
					+ "FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
					+ "where emp.employee_id=" + employeeId
					+ " AND  SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)<SUBSTRING_INDEX(att.endDateAndTime,' ',-1) AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
					+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		} else if (empReportType.equals("multiple")) {
			String selectedEmployees = selectedcheckbox.substring(0, selectedcheckbox.length() - 1);
			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1), 'A', SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)) as OutTime, TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00', TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')))as ShortWork "
					+ "FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
					+ "where emp.employee_id in (" + selectedEmployees
					+ ")  AND  SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)<SUBSTRING_INDEX(att.endDateAndTime,' ',-1) AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
					+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
					+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		} else {
//			query = "SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime , IF(STR_TO_DATE(att.inTime,'%H:%i:%s')=STR_TO_DATE(att.outTime,'%H:%i:%s'), 'A', STR_TO_DATE(att.outTime,'%H:%i:%s')) as OutTime, TIMEDIFF(STR_TO_DATE(att.outTime,'%H:%i:%s') , STR_TO_DATE(att.inTime,'%H:%i:%s')) > '00:00:00', TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) as WorkDuration , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration "
//					+ "TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%h:%i:%s')),TIMEDIFF(STR_TO_DATE(att.inTime,'%h:%i:%s'), STR_TO_DATE(att.outTime,'%H:%i:%s'))) as ShortWork FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
//			   
//			    + "where emp.department_id=" + departmentId
//			    + " AND  SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1)<SUBSTRING_INDEX(att.endDateAndTime,' ',-1) AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"
//				+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y')  "
//				+ "ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
			
			query="SELECT  emp.employee_id, emp.first_name, emp.last_name, att.Recorddate, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1) as InTime ,	IF(STR_TO_DATE(att.inTime,'%h:%i:%s')=STR_TO_DATE(att.outTime,'%H:%i:%s'), 'A', STR_TO_DATE(att.outTime,'%H:%i:%s')) as OutTime, IF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) > '00:00:00' ,TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')) , TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'))) as WorkDuration,	TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')) as ShiftDuration, SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1)=SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s' )),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s' )))> '00:00:00',TIMEDIFF(TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'), STR_TO_DATE(SUBSTRING_INDEX(att.startDateAndTime,' ',-1),'%H:%i:%s')),TIMEDIFF(STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'),STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',1),'%H:%i:%s')))as ShortWork	FROM attendance_logs_bulk_entry as att inner join employees as emp on emp.employee_id=att.WorkID "
				+"where emp.department_id=" + departmentId 
				+" AND LENGTH(TIMEDIFF(str_to_date(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s'),str_to_date(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s')))<=8  AND STR_TO_DATE(att.Recorddate,'%d-%m-%Y') BETWEEN STR_TO_DATE('"+ dateFrom + "','%d-%m-%Y') AND STR_TO_DATE('" + dateTo + "' , '%d-%m-%Y') ORDER BY emp.last_name , STR_TO_DATE(att.Recorddate,'%d-%m-%Y')";
		}
			
	
				//(STR_TO_DATE(SUBSTRING_INDEX(att.endDateAndTime,' ',-1),'%H:%i:%s') >= STR_TO_DATE(SUBSTRING_INDEX(att.timeAsPerShftTimings,'~',-1),'%H:%i:%s'))
				try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

}
