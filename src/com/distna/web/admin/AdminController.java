package com.distna.web.admin;

import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jawin.COMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.distna.domain.calendar.CalCategory;
import com.distna.domain.calendar.CalNumbers;
import com.distna.domain.calendar.CalTimes;
import com.distna.domain.calendar.Holidays;
import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;
import com.distna.domain.calendar.ShiftMaster;
import com.distna.domain.command.ClassCommand;
import com.distna.domain.command.Student;
import com.distna.domain.company.CanteenItems;
import com.distna.domain.company.CanteenTimings;
import com.distna.domain.company.Cities;
import com.distna.domain.company.Company;
import com.distna.domain.company.CompanyPolicies;
import com.distna.domain.company.Contractor;
import com.distna.domain.company.Countries;
import com.distna.domain.company.Departments;
import com.distna.domain.company.Designation;
import com.distna.domain.company.DesignationLevel;
import com.distna.domain.company.Division;
import com.distna.domain.company.JobPositions;
import com.distna.domain.company.JobRoles;
import com.distna.domain.company.Location;
import com.distna.domain.company.MasterSettings;
import com.distna.domain.company.Priority;
import com.distna.domain.company.States;
import com.distna.domain.company.Workspaces;
import com.distna.domain.devicemanagement.AddDevice;
import com.distna.domain.devicemanagement.AllowedDevices;
import com.distna.domain.devicemanagement.UserInfo;
import com.distna.domain.employee.Assesment;
import com.distna.domain.employee.Attendance;
import com.distna.domain.employee.AttendanceLogs;
import com.distna.domain.employee.AttendanceLogsBulkEntry;
import com.distna.domain.employee.AttendanceLogsOutdoorEntry;
import com.distna.domain.employee.AttendanceRecord;
import com.distna.domain.employee.Education;
import com.distna.domain.employee.Employee;
import com.distna.domain.employee.EmployeeBank;
import com.distna.domain.employee.EmployeeExperiences;
import com.distna.domain.employee.EmployeeMessages;
import com.distna.domain.employee.EmployeePrivilege;
import com.distna.domain.employee.EmployeeSkills;
import com.distna.domain.employee.Lists;
import com.distna.domain.employee.Projects;
import com.distna.domain.employee.UserPrivilege;
import com.distna.domain.leaves.Breaks;
import com.distna.domain.leaves.LeaveAllocation;
import com.distna.domain.leaves.LeaveApplication;
import com.distna.domain.leaves.LeaveType;
import com.distna.domain.leaves.OfficialTours;
import com.distna.domain.leaves.OutForWork;
import com.distna.domain.leaves.Status;
import com.distna.domain.privileges.MasterPrivileges;
import com.distna.domain.visitor.Purpose;
import com.distna.domain.visitor.VehicleDetails;
import com.distna.domain.visitor.VehicleLogs;
import com.distna.domain.visitor.VisitorGates;
import com.distna.domain.visitor.VisitorLogs;
import com.distna.domain.visitor.Visitors;
import com.distna.service.calendar.CalTimesDAO;
import com.distna.service.calendar.HolidaysDAO;
import com.distna.service.calendar.HolidaysValidator;
import com.distna.service.calendar.ShiftAllocationDAO;
import com.distna.service.calendar.ShiftAllocationValidator;
import com.distna.service.calendar.ShiftDefinitionDAO;
import com.distna.service.calendar.ShiftDefinitionValidator;
import com.distna.service.calendar.ShiftMasterDAO;
import com.distna.service.calendar.ShiftMasterValidator;
import com.distna.service.charts.ChartDAO;
import com.distna.service.charts.EmployeesPerDepartment;
import com.distna.service.charts.LeavesPerEmployee;
import com.distna.service.charts.LeavesPerLeaveType;
import com.distna.service.charts.PresenceAbscencePercentage;
import com.distna.service.charts.WeeklyTimecard;
import com.distna.service.company.CanteenItemsDAO;
import com.distna.service.company.CanteenItemsValidator;
import com.distna.service.company.CanteenTimingsDAO;
import com.distna.service.company.CanteenTimingsValidator;
import com.distna.service.company.CitiesDAO;
import com.distna.service.company.CompanyDAO;
import com.distna.service.company.CompanyPoliciesDAO;
import com.distna.service.company.CompanyPoliciesValidator;
import com.distna.service.company.CompanyValidator;
import com.distna.service.company.ContractorDAO;
import com.distna.service.company.CountriesDAO;
import com.distna.service.company.CurrencyListDAO;
import com.distna.service.company.DepartmentDAO;
import com.distna.service.company.DepartmentValidator;
import com.distna.service.company.DesignationDAO;
import com.distna.service.company.DesignationLevelDAO;
import com.distna.service.company.DesignationLevelValidator;
import com.distna.service.company.DesignationValidator;
import com.distna.service.company.DivisionDAO;
import com.distna.service.company.DivisionValidator;
import com.distna.service.company.JobPositionDAO;
import com.distna.service.company.JobPositionValidator;
import com.distna.service.company.JobRolesDAO;
import com.distna.service.company.JobRolesValidator;
import com.distna.service.company.LocationDAO;
import com.distna.service.company.LocationValidator;
import com.distna.service.company.MasterSettingsDAO;
import com.distna.service.company.PriorityDAO;
import com.distna.service.company.PriorityValidator;
import com.distna.service.company.StatesDAO;
import com.distna.service.company.WorkspacesDAO;
import com.distna.service.company.WorkspacesValidator;
import com.distna.service.company.ZonesDAO;
import com.distna.service.devicemanagement.AddDeviceDAO;
import com.distna.service.devicemanagement.AllowedDevicesDAO;
import com.distna.service.devicemanagement.UserInfoDAO;
import com.distna.service.employee.AssesmentDAO;
import com.distna.service.employee.AttendanceDAO;
import com.distna.service.employee.AttendanceLogsBulkEntryDAO;
import com.distna.service.employee.AttendanceLogsDAO;
import com.distna.service.employee.AttendanceLogsDAOImpl;
import com.distna.service.employee.AttendanceLogsOutdoorEntryDAO;
import com.distna.service.employee.AttendanceRecordDAO;
import com.distna.service.employee.BankDAO;
import com.distna.service.employee.EducationDAO;
import com.distna.service.employee.EmployeeAssesmentValidator;
import com.distna.service.employee.EmployeeBankValidator;
import com.distna.service.employee.EmployeeDAO;
import com.distna.service.employee.EmployeeEducationValidator;
import com.distna.service.employee.EmployeeExperiencesDAO;
import com.distna.service.employee.EmployeeExperiencesValidator;
import com.distna.service.employee.EmployeeMessagesDAO;
import com.distna.service.employee.EmployeeMessagesValidator;
import com.distna.service.employee.EmployeePersonalValidator;
import com.distna.service.employee.EmployeePrivilegeDAO;
import com.distna.service.employee.EmployeeProjectsValidator;
import com.distna.service.employee.EmployeeSkillsDAO;
import com.distna.service.employee.EmployeeSkillsValidator;
import com.distna.service.employee.EmployeeValidator;
import com.distna.service.employee.ListsDAO;
import com.distna.service.employee.ProjectsDAO;
import com.distna.service.employee.UserPrivilegeDAO;
import com.distna.service.leaves.BreakDAO;
import com.distna.service.leaves.BreaksValidator;
import com.distna.service.leaves.LeaveAllocationDAO;
import com.distna.service.leaves.LeaveAllocationValidator;
import com.distna.service.leaves.LeaveApplicationDAO;
import com.distna.service.leaves.LeaveApplicationValidator;
import com.distna.service.leaves.LeaveTypeDAO;
import com.distna.service.leaves.LeaveTypeValidator;
import com.distna.service.leaves.LeavesValidator;
import com.distna.service.leaves.OfficialTourValidator;
import com.distna.service.leaves.OfficialToursDAO;
import com.distna.service.leaves.OutForWorkDAO;
import com.distna.service.leaves.StatusDAO;
import com.distna.service.privileges.MasterPrivilegesDAO;
import com.distna.service.report.ReportDAO;
import com.distna.service.visitor.PurposeDAO;
import com.distna.service.visitor.VehicleDetailsDAO;
import com.distna.service.visitor.VehicleDetailsValidator;
import com.distna.service.visitor.VehicleLogsDAO;
import com.distna.service.visitor.VehicleLogsValidator;
import com.distna.service.visitor.VisitorBatchPojo;
import com.distna.service.visitor.VisitorGatesDAO;
import com.distna.service.visitor.VisitorLogsDAO;
import com.distna.service.visitor.VisitorLogsValidator;
import com.distna.service.visitor.VisitorValidator;
import com.distna.service.visitor.VisitorsDAO;
import com.distna.utility.DateTime;
import com.distna.utility.DbBackup;
import com.distna.utility.EncryptPassword;
import com.distna.utility.IAttApp;
import com.distna.utility.IDatFileRead;
import com.distna.utility.Imageload;
import com.distna.utility.SendMailTLS;
import com.distna.utility.WriteExcel;
import com.distna.web.login.ConnectionDao;
import com.distna.domain.devicemanagement.CardDetails;
import com.distna.utility.ResponseHandler;

import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import sun.misc.BASE64Decoder;

@Controller
public class AdminController {
	/*
	 * static { System.loadLibrary("AttAppClassLibrary");
	 * System.loadLibrary("RestoreDatFileDLL"); System.loadLibrary("jawin"); }
	 */
	static Logger log = Logger.getLogger(AdminController.class.getName());
	IAttApp iAttApp = null;

	private ModelMap modelMap = new ModelMap();
	private DesignationLevelDAO designationLevelDAO;
	private CompanyDAO companyDAO;
	private ZonesDAO zonesDAO;
	private CountriesDAO countriesDAO;
	private CurrencyListDAO currencyListDAO;
	private WorkspacesDAO workspacesDAO;
	private JobRolesDAO jobRolesDAO;
	private PriorityDAO priorityDAO;
	private DepartmentDAO departmentDAO;
	private DesignationDAO designationDAO;
	private LocationDAO locationDAO;
	private CompanyPoliciesDAO companyPoliciesDAO;
	private StatesDAO statesDAO;
	private CitiesDAO citiesDAO;
	private JobPositionDAO jobPositionDAO;
	private ListsDAO listsDAO;
	private AddDeviceDAO addDeviceDAO;
	private HolidaysDAO holidaysDAO;
	private int countryID = 0;
	private int countryIdForAddcity;

	private int locationId;
	private String redirectUrl;
	private int departmentIdAdd;

	private DivisionDAO divisionDAO;
	private EmployeeDAO employeeDAO;
	private EncryptPassword encryptPassword;
	private OfficialToursDAO officialToursDAO;
	private ShiftMasterDAO shiftMasterDAO;
	private ShiftDefinitionDAO shiftDefinitionDAO;
	private CalTimesDAO calTimesDAO;
	private ShiftAllocationDAO shiftAllocationDAO;
	private EmployeeExperiencesDAO employeeExperiencesDAO;
	private EmployeeSkillsDAO employeeSkillsDAO;
	private StatusDAO statusDAO;
	private ProjectsDAO projectsDAO;
	private EducationDAO educationDAO;
	private AssesmentDAO assesmentDAO;
	private BreakDAO breakDAO;
	private LeaveTypeDAO leaveTypeDAO;
	private LeaveAllocationDAO leaveAllocationDAO;
	private LeaveApplicationDAO leaveApplicationDAO;
	private EmployeeSkillsValidator employeeSkillsValidator;
	private EmployeeBankValidator employeeBankValidator;

	private JobRolesValidator jobRolesValidator;
	private AttendanceRecordDAO attendanceRecordDAO;

	private AttendanceLogsDAO attendanceLogsDAO;
	private AttendanceLogsBulkEntryDAO attendanceLogsBulkEntryDAO;
	private CompanyPoliciesValidator companyPoliciesValidator;
	private LocationValidator locationValidator;
	private DepartmentValidator departmentValidator;
	private DesignationValidator designationValidator;
	private JobPositionValidator jobPositionValidator;
	private DivisionValidator divisionValidator;
	private LeavesValidator leavesValidator;
	private OfficialTourValidator officialTourValidator;
	private LeaveTypeValidator leaveTypeValidator;
	private LeaveAllocationValidator leaveAllocationValidator;
	private LeaveApplicationValidator leaveApplicationValidator;
	private EmployeeAssesmentValidator employeeAssesmentValidator;
	private EmployeeProjectsValidator employeeProjectsValidator;
	private EmployeeValidator employeeValidator;
	private ShiftMasterValidator shiftMasterValidator;
	private ShiftDefinitionValidator shiftDefinitionValidator;
	private ShiftAllocationValidator shiftAllocationValidator;
	private ReportDAO reportDAO;
	private JasperDesign jasperDesign;
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;
	private ResultSet rs = null;
	private ChartDAO chartDAO;
	private LeavesPerLeaveType leavesPerLeaveType;
	private EmployeePersonalValidator employeePersonalValidator;
	private EmployeeEducationValidator employeeEducationValidator;
	private EmployeesPerDepartment employeesPerDepartment;
	private WeeklyTimecard weeklyTimecard;
	private PresenceAbscencePercentage presenceAbscencePercentage;
	private LeavesPerEmployee leavesPerEmployee;
	private EmployeeMessagesDAO employeeMessagesDAO;
	private UserPrivilegeDAO userPrivilegeDAO;
	private EmployeePrivilegeDAO employeePrivilegeDAO;
	private ContractorDAO contractorDAO;
	private EmployeeMessagesValidator employeeMessagesValidator;
	private BreaksValidator breaksValidator;
	private PriorityValidator priorityValidator;
	private OutForWorkDAO outForWorkDAO;
	private UserInfoDAO userInfoDAO;
	private MasterSettingsDAO masterSettingsDAO;
	private AllowedDevicesDAO allowedDevicesDAO;
	private AttendanceLogsOutdoorEntryDAO attendanceLogsOutdoorEntryDAO;
	private CanteenItemsDAO canteenItemsDAO;
	private CanteenItemsValidator canteenItemsValidator;
	private CanteenTimingsDAO canteenTimingsDAO;
	private CanteenTimingsValidator canteenTimingsValidator;
	private VisitorLogsDAO visitorLogsDAO;
	private VisitorsDAO visitorsDAO;
	private VehicleDetailsDAO vehicleDetailsDAO;
	private VehicleLogsDAO vehicleLogsDAO;
	private VisitorGatesDAO visitorGatesDAO;
	private PurposeDAO purposeDAO;
	private VisitorValidator visitorValidator;
	private VisitorLogsValidator visitorLogsValidator;
	private VehicleDetailsValidator vehicleDetailsValidator;
	private VehicleLogsValidator vehicleLogsValidator;
	private MasterPrivilegesDAO masterPrivilegesDAO;
	private BankDAO BankDAO;
	private AttendanceDAO attendanceDAO;

	@Autowired
	public AdminController(AttendanceDAO attendanceDAO, MasterPrivilegesDAO masterPrivilegesDAO,
			VehicleLogsValidator vehicleLogsValidator, VisitorLogsValidator visitorLogsValidator,
			VisitorValidator visitorValidator, PurposeDAO purposeDAO, VisitorGatesDAO visitorGatesDAO,
			AttendanceLogsOutdoorEntryDAO attendanceLogsOutdoorEntryDAO, MasterSettingsDAO masterSettingsDAO,
			UserInfoDAO userInfoDAO, AllowedDevicesDAO allowedDevicesDAO, OutForWorkDAO outForWorkDAO,
			ContractorDAO contractorDAO, PriorityValidator priorityValidator, BreaksValidator breaksValidator,
			EmployeeMessagesValidator employeeMessagesValidator, EmployeeMessagesDAO employeeMessagesDAO,
			EmployeeEducationValidator employeeEducationValidator, LeavesPerEmployee leavesPerEmployee,
			PresenceAbscencePercentage presenceAbscencePercentage, WeeklyTimecard weeklyTimecard,
			EmployeesPerDepartment employeesPerDepartment, EmployeePersonalValidator employeePersonalValidator,
			ShiftAllocationValidator shiftAllocationValidator, ShiftDefinitionValidator shiftDefinitionValidator,
			ShiftMasterValidator shiftMasterValidator, DivisionValidator divisionValidator,
			JobPositionValidator jobPositionValidator, DesignationValidator designationValidator,
			DepartmentValidator departmentValidator, LocationValidator locationValidator,
			CompanyPoliciesValidator companyPoliciesValidator, AttendanceLogsBulkEntryDAO attendanceLogsBulkEntryDAO,
			AttendanceRecordDAO attendanceRecordDAO, AttendanceLogsDAO attendanceLogsDAO,
			ShiftAllocationDAO shiftAllocationDAO, EmployeeSkillsDAO employeeSkillsDAO, BankDAO bankDAO,
			EmployeeExperiencesDAO employeeExperiencesDAO, HolidaysDAO holidaysDAO, CalTimesDAO calTimesDAO,
			AddDeviceDAO addDeviceDAO, ListsDAO listsDAO, JobPositionDAO jobPositionDAO,
			CompanyPoliciesDAO companyPoliciesDAO, LocationDAO locationDAO, DesignationDAO designationDAO,
			DepartmentDAO departmentDAO, PriorityDAO priorityDAO, JobRolesDAO jobRolesDAO, WorkspacesDAO workspacesDAO,
			CurrencyListDAO currencyListDAO, CountriesDAO countriesDAO, DesignationLevelDAO designationLevelDAO,
			CompanyDAO companyDAO, ZonesDAO zonesDAO, StatesDAO statesDAO, CitiesDAO citiesDAO, DivisionDAO divisionDAO,
			EmployeeDAO employeeDAO, OfficialToursDAO officialToursDAO, ShiftMasterDAO shiftMasterDAO,
			ShiftDefinitionDAO shiftDefinitionDAO, StatusDAO statusDAO, ProjectsDAO projectsDAO,
			EducationDAO educationDAO, AssesmentDAO assesmentDAO, BreakDAO breakDAO, LeaveTypeDAO leaveTypeDAO,
			LeaveAllocationDAO leaveAllocationDAO, LeaveApplicationDAO leaveApplicationDAO,
			EmployeeSkillsValidator employeeSkillsValidator, EmployeeBankValidator employeebankValidator,
			JobRolesValidator jobRolesValidator, LeavesValidator leavesValidator,
			OfficialTourValidator officialTourValidator, LeaveTypeValidator leaveTypeValidator,
			LeaveAllocationValidator leaveAllocationValidator, LeaveApplicationValidator leaveApplicationValidator,
			EmployeeAssesmentValidator employeeAssesmentValidator, EmployeeProjectsValidator employeeProjectsValidator,
			EmployeeValidator employeeValidator, ReportDAO reportDAO, ChartDAO chartDAO,
			LeavesPerLeaveType leavesPerLeaveType, UserPrivilegeDAO userPrivilegeDAO,
			EmployeePrivilegeDAO employeePrivilegeDAO, CanteenItemsDAO canteenItemsDAO,
			CanteenItemsValidator canteenItemsValidator, CanteenTimingsDAO canteenTimingsDAO,
			CanteenTimingsValidator canteenTimingsValidator, VisitorLogsDAO visitorLogsDAO, VisitorsDAO visitorsDAO,
			VehicleDetailsDAO vehicleDetailsDAO, VehicleLogsDAO vehicleLogsDAO,
			VehicleDetailsValidator vehicleDetailsValidator) {
		this.designationLevelDAO = designationLevelDAO;
		this.companyDAO = companyDAO;
		this.zonesDAO = zonesDAO;
		this.countriesDAO = countriesDAO;
		this.currencyListDAO = currencyListDAO;
		this.workspacesDAO = workspacesDAO;
		this.jobRolesDAO = jobRolesDAO;
		this.priorityDAO = priorityDAO;
		this.departmentDAO = departmentDAO;
		this.designationDAO = designationDAO;
		this.locationDAO = locationDAO;
		this.companyPoliciesDAO = companyPoliciesDAO;
		this.statesDAO = statesDAO;
		this.citiesDAO = citiesDAO;
		this.statusDAO = statusDAO;
		this.divisionDAO = divisionDAO;
		this.employeeDAO = employeeDAO;
		this.jobPositionDAO = jobPositionDAO;
		this.listsDAO = listsDAO;
		this.addDeviceDAO = addDeviceDAO;
		this.officialToursDAO = officialToursDAO;
		this.shiftMasterDAO = shiftMasterDAO;
		this.shiftDefinitionDAO = shiftDefinitionDAO;
		this.calTimesDAO = calTimesDAO;
		this.shiftAllocationDAO = shiftAllocationDAO;
		this.holidaysDAO = holidaysDAO;
		this.employeeExperiencesDAO = employeeExperiencesDAO;
		this.employeeSkillsDAO = employeeSkillsDAO;
		this.BankDAO = bankDAO;
		this.projectsDAO = projectsDAO;
		this.educationDAO = educationDAO;
		this.breakDAO = breakDAO;
		this.leaveTypeDAO = leaveTypeDAO;
		this.leaveAllocationDAO = leaveAllocationDAO;
		this.leaveApplicationDAO = leaveApplicationDAO;
		this.employeeSkillsValidator = employeeSkillsValidator;
		this.employeeBankValidator = employeeBankValidator;
		this.assesmentDAO = assesmentDAO;
		this.jobRolesValidator = jobRolesValidator;
		this.attendanceLogsDAO = attendanceLogsDAO;
		this.attendanceRecordDAO = attendanceRecordDAO;
		this.attendanceLogsBulkEntryDAO = attendanceLogsBulkEntryDAO;
		this.companyPoliciesValidator = companyPoliciesValidator;
		this.locationValidator = locationValidator;
		this.departmentValidator = departmentValidator;
		this.designationValidator = designationValidator;
		this.jobPositionValidator = jobPositionValidator;
		this.divisionValidator = divisionValidator;
		this.shiftMasterValidator = shiftMasterValidator;
		this.shiftDefinitionValidator = shiftDefinitionValidator;
		this.shiftAllocationValidator = shiftAllocationValidator;
		this.leavesValidator = leavesValidator;
		this.officialTourValidator = officialTourValidator;
		this.leaveTypeValidator = leaveTypeValidator;
		this.leaveAllocationValidator = leaveAllocationValidator;
		this.leaveApplicationValidator = leaveApplicationValidator;
		this.employeeAssesmentValidator = employeeAssesmentValidator;
		this.employeeProjectsValidator = employeeProjectsValidator;
		this.employeeValidator = employeeValidator;
		this.reportDAO = reportDAO;
		this.chartDAO = chartDAO;
		this.contractorDAO = contractorDAO;
		this.leavesPerLeaveType = leavesPerLeaveType;
		this.employeePersonalValidator = employeePersonalValidator;
		this.leavesPerEmployee = leavesPerEmployee;
		this.employeesPerDepartment = employeesPerDepartment;
		this.weeklyTimecard = weeklyTimecard;
		this.presenceAbscencePercentage = presenceAbscencePercentage;
		this.employeeEducationValidator = employeeEducationValidator;
		this.employeeMessagesDAO = employeeMessagesDAO;
		this.userPrivilegeDAO = userPrivilegeDAO;
		this.employeePrivilegeDAO = employeePrivilegeDAO;
		this.employeeMessagesValidator = employeeMessagesValidator;
		this.breaksValidator = breaksValidator;
		this.priorityValidator = priorityValidator;
		this.outForWorkDAO = outForWorkDAO;
		this.allowedDevicesDAO = allowedDevicesDAO;
		this.userInfoDAO = userInfoDAO;
		this.masterSettingsDAO = masterSettingsDAO;
		this.attendanceLogsOutdoorEntryDAO = attendanceLogsOutdoorEntryDAO;
		this.canteenItemsDAO = canteenItemsDAO;
		this.canteenItemsValidator = canteenItemsValidator;
		this.canteenTimingsDAO = canteenTimingsDAO;
		this.canteenTimingsValidator = canteenTimingsValidator;
		this.visitorLogsDAO = visitorLogsDAO;
		this.visitorsDAO = visitorsDAO;
		this.vehicleDetailsDAO = vehicleDetailsDAO;
		this.vehicleLogsDAO = vehicleLogsDAO;
		this.visitorGatesDAO = visitorGatesDAO;
		this.purposeDAO = purposeDAO;
		this.visitorValidator = visitorValidator;
		this.visitorLogsValidator = visitorLogsValidator;
		this.vehicleLogsValidator = vehicleLogsValidator;
		this.vehicleDetailsValidator = vehicleDetailsValidator;
		this.masterPrivilegesDAO = masterPrivilegesDAO;
		this.attendanceDAO = attendanceDAO;

		File dir = new File("D:\\Distna_Report");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	@RequestMapping(value = "loginpage.htm", method = RequestMethod.GET)
	public ModelAndView administratorLogin(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (null != session) {
			session.invalidate();
		}
		return new ModelAndView("loginPage");
	}

	@RequestMapping(value = "loginWhenSessionExpires.htm", method = RequestMethod.GET)
	public ModelAndView loginWhenSessionExpires(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (null != session) {
			session.invalidate();
		}
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("sessionExpired", "Your Session has Expired. Please Login Again.");
		return new ModelAndView("loginPage", modelMap);
	}

	@RequestMapping(value = "checkvaliduser.htm", method = RequestMethod.POST)
	public ModelAndView checkValidUser(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		Properties properties = new Properties();
		String destination = request.getSession().getServletContext().getRealPath("") + "\\reports";
		DateFormat dbDateFormat = new SimpleDateFormat("d-M-yyyy");

		File dir = new File(destination);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String databaseFolderPath = request.getSession().getServletContext().getRealPath("").split(":")[0]
				+ ":\\Distna database backup";

		File dbFolder = new File(databaseFolderPath);
		if (!dbFolder.exists()) {
			dbFolder.mkdir();
		}

		String dbFileName = dbFolder + "\\DISTNA_" + dbDateFormat.format(new Date().getTime()) + ".sql";

		// String
		// path=request.getSession().getServletContext().getRealPath("")+"/database.properties";
		// FileInputStream fileInputStream=new FileInputStream(path);
		// properties.load(fileInputStream);
		InputStream inputStream = AdminController.class.getClassLoader().getResourceAsStream("database.properties");
		properties.load(inputStream);
		String dbusername = properties.getProperty("database.username");
		String encryptdPasswd = properties.getProperty("database.password");
		encryptPassword = new EncryptPassword();
		String dbPasswd = encryptPassword.decrypt(encryptdPasswd);
		List<MasterSettings> masterSettingsList = masterSettingsDAO.getMasterSettings();
		String dateF = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = DateTime.CurrentDate(dateF);
		String lastBackupDate = null;
		int backupDays = 0;
		if (masterSettingsList.size() > 0) {
			for (MasterSettings masterSettings : masterSettingsList) {
				lastBackupDate = masterSettings.getLastBackupDate();
				backupDays = masterSettings.getDbBackupDays();
			}

			Date todayDate = sdf.parse(dateNow);
			Date lastDbDate = sdf.parse(lastBackupDate);
			long lastDbDateLong = lastDbDate.getTime();
			long todayDateLong = todayDate.getTime();
			long dateDiff = todayDateLong - lastDbDateLong;
			long noOfDays = dateDiff / (1000 * 60 * 60 * 24);
			if (noOfDays >= backupDays) {
				DbBackup dbBackup = new DbBackup();
				dbBackup.backupDB("distna", dbusername, dbPasswd, dbFileName);
				List<MasterSettings> masterList = masterSettingsDAO.getMasterSettings();
				if (masterList.size() > 0) {
					MasterSettings masterSettings = masterList.get(0);
					masterSettings.setLastBackupDate(sdf.format(todayDate));
					masterSettingsDAO.saveOrUpdateMasterSettings(masterSettings);
				}
			}
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<MasterPrivileges> masterPrivilegesList = masterPrivilegesDAO.getMasterPrivilegesByPrivilege("DISTNA");
		if (masterPrivilegesList.size() != 0 && masterPrivilegesList.get(0).getPrivilegeStatus().equals("1")) {
			if (null != session.getAttribute("masterPrivilege"))
				session.removeAttribute("masterPrivilege");

			List<MasterPrivileges> masterPrivileges = masterPrivilegesDAO.getMasterPrivilegesByPrivilege("VMS");
			if (masterPrivileges.size() != 0) {
				String decryptedStatus = "";
				try {
					decryptedStatus = encryptPassword.decrypt(masterPrivileges.get(0).getPrivilegeStatus());
					session.setAttribute("masterPrivilege", decryptedStatus.equals("1"));
				} catch (Throwable throwable) {
					modelMap.addAttribute("message", "Not an Authorized User!");
					return new ModelAndView("loginPage", modelMap);
				}
			}
			if (username.equals("admin") && password.equals("admin")) {
				session.setAttribute("loginUser", username);
				/*
				 * session.setAttribute("loginUsername",username);
				 * session.setAttribute("loginUserId",0);
				 */
				return new ModelAndView("admindashboard");
			} else {
				encryptPassword = new EncryptPassword();
				String encryptedPassword = encryptPassword.encrypt(password);
				boolean flag = employeeDAO.checkIfUserExist(username, encryptedPassword);
				if (flag == true) {
					Employee employee = employeeDAO.getEmployeeByUserName(username, encryptedPassword);
					session.setAttribute("loginUser", username);
					session.setAttribute("loginUserId", employee.getEmployeeId());
					session.setAttribute("loginUserNo", employee.getEmployeeNo());
					session.setAttribute("loginUsername", employee.getFirstName() + " " + employee.getLastName());
					List<EmployeePrivilege> employeePrivilegesList = employeePrivilegeDAO
							.getEmployeePrivilegeListByEmployeeId(employee.getEmployeeId());
					if (null != session.getAttribute("employeePrivilegesString"))
						session.removeAttribute("employeePrivilegesString");
					if (employeePrivilegesList.size() > 0) {
						session.setAttribute("employeePrivilegesString", employeePrivilegesList.get(0).getPrivilege());
						/*
						 * List<UserPrivilege>
						 * userPrivilegesList=userPrivilegeDAO.getUserPrivilegeList();
						 * session.setAttribute("userPrivilegesList",userPrivilegesList);
						 */
					}
					List<Employee> monthsBirthdayList = employeeDAO.getEmployeesWithBirthdays();
					Date currentDate = Calendar.getInstance().getTime();
					Calendar calendarStartOfWeek = Calendar.getInstance();
					Calendar calendarEndOfWeek = Calendar.getInstance();
					calendarEndOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
					String currentDateString = dateFormat.format(currentDate);
					int employeeMessagesCount = employeeMessagesDAO
							.getUnreadEmployeeMessagesCount(employee.getEmployeeId());
					modelMap.addAttribute("employeeMessagesListSize", employeeMessagesCount);
					modelMap.addAttribute("monthsBirthdayList", monthsBirthdayList);
					modelMap.addAttribute("currentDate", currentDateString);
					modelMap.addAttribute("loginUserName", employee.getFirstName());
					modelMap.addAttribute("loginUserEmployeeNo", employee.getEmployeeNo());
					modelMap.addAttribute("loginUserEmployeeId", employee.getEmployeeId());
					weeklyTimecard.createDemoPanel(request, employee.getFirstName(), employee.getEmployeeNo(),
							simpleDateFormat.format(calendarStartOfWeek.getTime()),
							simpleDateFormat.format(calendarEndOfWeek.getTime()));
					int userType = employeeDAO.checkUserType(username, encryptedPassword);
					if (userType == 1) {
						return new ModelAndView("userdashboard", modelMap);
					} else if (userType == 2) {
						return new ModelAndView("admindashboard");
					} else if (userType == 3) {
						return new ModelAndView("ManagerDashBoard", modelMap);
					} else {
						modelMap.addAttribute("message", "Invalid User!");
						return new ModelAndView("loginPage", modelMap);
					}
				} else {
					modelMap.addAttribute("message", "Invalid User!");
					return new ModelAndView("loginPage", modelMap);
				}
			}
		} else {
			modelMap.addAttribute("message", "Not an Authorized User!");
			return new ModelAndView("loginPage", modelMap);
		}

	}
//+++++++++++++++++++++++++++++++++++++++++++++++AttendanceLogs++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public ModelMap getModelMapForManagerDashBoard(Employee employee) {
		ModelMap modelMap = new ModelMap();
		Date currentDate = Calendar.getInstance().getTime();
		Calendar calendarStartOfWeek = Calendar.getInstance();
		Calendar calendarEndOfWeek = Calendar.getInstance();
		calendarEndOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
		String currentDateString = dateFormat.format(currentDate);
		List<Employee> monthsBirthdayList = employeeDAO.getEmployeesWithBirthdays();
		List<Location> locationList = locationDAO.getLocation();

		int employeeMessagesCount = employeeMessagesDAO.getUnreadEmployeeMessagesCount(employee.getEmployeeId());
		modelMap.addAttribute("employeeMessagesListSize", employeeMessagesCount);
		modelMap.addAttribute("monthsBirthdayList", monthsBirthdayList);
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("currentDate", currentDateString);
		modelMap.addAttribute("loginUserName", employee.getFirstName());
		modelMap.addAttribute("loginUserEmployeeNo", employee.getEmployeeNo());
		modelMap.addAttribute("loginUserEmployeeId", employee.getEmployeeId());
		return modelMap;
	}

	
	@RequestMapping(value = "ManagerDashBoard.htm", method = RequestMethod.POST)
	public ModelAndView ManagerDashBoard(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {


		SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String timeStamp = Date.format(new Date());

		SimpleDateFormat Date12 = new SimpleDateFormat("d-M-yyyy");
		String date1 = Date12.format(new Date());

		SimpleDateFormat Date1 = new SimpleDateFormat("HH:mm:ss");
		String time = Date1.format(new Date());

		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;

		String eid = request.getParameter("EmployeeID");
		request.getSession().setAttribute("EID", eid);
		System.out.println("Employee ID:" + eid);
		int employeeNo = Integer.parseInt(eid);
		Connection conn = null;
		ConnectionDao d = new ConnectionDao();
		conn = d.getConnection();
		PreparedStatement ps = null;

		// List<ShiftDefinition>shftdefList=shiftDefinitionDAO.getShiftDefinitionsByShiftId(employeeNo);
		// for(ShiftDefinition shftdef:shftdefList ) {

		try {// first try start
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM employees WHERE employee_id='" + eid + "'";// checking employee
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("employee existed");// process to checkin
				try {// second start
					String SQLCHECK = "select * from attrecord where empCode='" + eid + "'";
					Statement stm = conn.createStatement();
					ResultSet rs2 = stm.executeQuery(SQLCHECK);
					if (!rs2.next()) {
						AttendanceRecord attendanceRecord = new AttendanceRecord();
						Attendance attendance = new Attendance();
						attendanceRecord.setEmpCode(employeeNo);
						attendanceRecord.setCheckInOut("CheckIn");
						attendanceRecord.setStatus(0);
						attendanceRecord.setDate(date1);
						attendanceRecord.setTime(time);
						attendanceRecord.setDateTime(timeStamp);
						attendance.setEmpCode(employeeNo);
						attendance.setDate(date1);
						attendance.setIn_Time(time);
						attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
						attendanceDAO.saveAttendance(attendance);

						System.out.println("checkIn SuccessFully");
					} else {
						try {
							String sql1 = "select * from attrecord where empCode='" + eid
									+ "'ORDER BY dateTime DESC LIMIT 1";
							Statement stmt12 = conn.createStatement();
							ResultSet rs12 = stmt12.executeQuery(sql1);

							if (rs12.next()) {// check status
								int status = rs12.getInt(4);
								System.out.println("status:" + status);

								if (status == 1) {
									// checkinpart
									try {// In_Time
										AttendanceRecord attendanceRecord = new AttendanceRecord();
										Attendance attendance = new Attendance();
										attendanceRecord.setEmpCode(employeeNo);
										attendanceRecord.setCheckInOut("CheckIn");
										attendanceRecord.setStatus(0);
										attendanceRecord.setDate(date1);
										attendanceRecord.setTime(time);
										attendanceRecord.setDateTime(timeStamp);
										attendance.setEmpCode(employeeNo);
										attendance.setDate(date1);
										attendance.setIn_Time(time);
										attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
										attendanceDAO.saveAttendance(attendance);
										System.out.println("In SuccessFully");
										conn.close();
									} catch (Exception e) {// in_Time
										e.printStackTrace();
									}

								} else if (status == 0) {
									try {
										AttendanceRecord attendanceRecord = new AttendanceRecord();
										attendanceRecord.setEmpCode(employeeNo);
										attendanceRecord.setCheckInOut("CheckOut");
										attendanceRecord.setStatus(1);
										attendanceRecord.setDate(date1);
										attendanceRecord.setTime(time);
										attendanceRecord.setDateTime(timeStamp);
										attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
										System.out.println("Out SuccessFully");
										String SQL = "UPDATE attendance  SET Out_Time='" + time + "' WHERE empCode='"
												+ eid + "' and Date='" + date1 + "'";
										stmt = conn.createStatement();
										stmt.executeUpdate(SQL);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}

								/*
								 * }else if(status == 0) { try { AttendanceRecord attendanceRecord=new
								 * AttendanceRecord(); attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("BreakOut"); attendanceRecord.setStatus(2);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * 
								 * System.out.println("BreakOut SuccessFully"); conn.close(); }catch (Exception
								 * e) { e.printStackTrace(); }
								 */

								/*
								 * } else if(status==2) { try { //String INSERT_RECORD
								 * ="insert into attrecord(empCode, checkInOut, status, date, time,dateTime)values(?,?,?,?,?,?)"
								 * ;
								 * 
								 * AttendanceRecord attendanceRecord=new AttendanceRecord();
								 * attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("BreakIn"); attendanceRecord.setStatus(3);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * 
								 * System.out.println("BreakIn SuccessFully"); conn.close(); } catch (Exception
								 * e) { e.printStackTrace(); }
								 */

								/*
								 * } else if(status==3 || status==4 ) {
								 * 
								 * try { //Second Break
								 * 
								 * List<ShiftDefinition>shiftDefinitionList=shiftDefinitionDAO.getShiftBreakTime
								 * (employeeNo); Boolean BreakTime=shiftDefinitionList.get(0).isBreak2();
								 * 
								 * if(BreakTime==true) {
								 * 
								 * if(status==3) { try { AttendanceRecord attendanceRecord=new
								 * AttendanceRecord(); attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("BreakOut2"); attendanceRecord.setStatus(4);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * 
								 * System.out.println("BreakOut2 SuccessFully"); conn.close(); }catch (Exception
								 * e) { e.printStackTrace(); }
								 */
								/*
								 * }else if (status==4) { try { AttendanceRecord attendanceRecord=new
								 * AttendanceRecord(); attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("BreakIn2"); attendanceRecord.setStatus(5);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * 
								 * System.out.println("BreakIn2 SuccessFully"); conn.close();
								 * 
								 * } catch (Exception e) { e.printStackTrace(); } }
								 */

								/*
								 * }else { try { AttendanceRecord attendanceRecord=new AttendanceRecord();
								 * attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("CheckOut"); attendanceRecord.setStatus(1);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * System.out.println("Out SuccessFully"); } catch (Exception e) {
								 * e.printStackTrace(); } try { String SQL="UPDATE attendance  SET Out_Time='" +
								 * time + "' WHERE empCode='" + eid + "' and Date='"+date1+"'"; stmt=
								 * conn.createStatement(); stmt.executeUpdate(SQL); } catch (Exception e) { //
								 * TODO: handle exception e.printStackTrace(); }
								 * 
								 * }
								 * 
								 * }catch (Exception e) { // TODO: handle exception } }
								 */

								/*
								 * else if(status==3 || status==5) { try { AttendanceRecord attendanceRecord=new
								 * AttendanceRecord(); attendanceRecord.setEmpCode(employeeNo);
								 * attendanceRecord.setCheckInOut("CheckOut"); attendanceRecord.setStatus(1);
								 * attendanceRecord.setDate(date1); attendanceRecord.setTime(time);
								 * attendanceRecord.setDateTime(timeStamp);
								 * attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
								 * System.out.println("Out SuccessFully"); } catch (Exception e) {
								 * e.printStackTrace(); } try { String SQL="UPDATE attendance  SET Out_Time='" +
								 * time + "' WHERE empCode='" + eid + "' and Date='"+date1+"'"; stmt=
								 * conn.createStatement(); stmt.executeUpdate(SQL); } catch (Exception e) { //
								 * TODO: handle exception e.printStackTrace(); }
								 * 
								 * 
								 * }
								 */

							} // check status end

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					conn.close();
				} catch (Exception e) { // second end....
					e.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "employee id not found ");
			}
		} catch (Exception e) { // first try end...
			System.out.println("Error in getting employee:" + e.getMessage());
		}

		try {// start attenddancebulkentry

			DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy HH:mm:ss");

			List<AttendanceRecord> attendanceRecordList = attendanceRecordDAO.getAttendanceRecordList();
			List<Attendance> attendanceList = attendanceDAO.getAttendanceList(employeeNo);

			for (AttendanceRecord attendanceRecordObj : attendanceRecordList) {
				for (Attendance attendanceobj : attendanceList) {
					List<Employee> employeeForAttendance = employeeDAO
							.getEmployeeListByEmployeeNo(attendanceRecordObj.getEmpCode());

					if (employeeForAttendance.size() != 0) {

						Employee attendanceLogsEmployee = employeeForAttendance.get(0);

						int currentEmployeeId = attendanceLogsEmployee.getEmployeeId();
						List<ShiftAllocation> allocatedShiftsToEmployee = shiftAllocationDAO
								.getShiftAllocatedEmployeeList(currentEmployeeId);

						AttendanceLogs attendanceLogs = new AttendanceLogs();

						int currentLocation = attendanceLogsEmployee.getLocation();

						String currentDate = attendanceRecordObj.getDate();
						String currentTime = attendanceRecordObj.getTime();
						int currentStatus = attendanceRecordObj.getStatus();
						String currentStatusInString = attendanceRecordObj.getCheckInOut();
						int currentShiftId = 0;
						String currentDateActual = "0";
						int currentWorkId = attendanceRecordObj.getEmpCode();

						attendanceLogs.setLocation(currentLocation);
						attendanceLogs.setRecordDate(currentDate);
						attendanceLogs.setRecordTime(currentTime);
						attendanceLogs.setStatus(currentStatus);
						attendanceLogs.setStatusInString(currentStatusInString);

						if (allocatedShiftsToEmployee.size() != 0) {
							currentShiftId = allocatedShiftsToEmployee.get(0).getShiftid();
							attendanceLogs.setShift(currentShiftId);
						}

						attendanceLogs.setWorkID(currentWorkId);
						attendanceLogsDAO.saveAttendanceLogs(attendanceLogs);// save in attendace logs

						SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
						int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
						try {
							Date currentDateConverted = format.parse(currentDate);
							currentDateActual = format.format(currentDateConverted.getTime() + MILLIS_IN_DAY);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (currentShiftId != 0
								&& shiftDefinitionDAO.getShiftDefinitionsByShiftId(currentShiftId).size() != 0) {
							ShiftDefinition currentShiftDefinitionObj = shiftDefinitionDAO
									.getShiftDefinitionObjByShiftId(currentShiftId);

							String shiftStartTime = currentShiftDefinitionObj.getTimeField();
							String shiftEndTime = currentShiftDefinitionObj.getEndTime();

							String[] splitForStartTimeAMPM = shiftStartTime.split(":");
							String[] splitForEndTimeAMPM = shiftEndTime.split(":");

							AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();

							if (Integer.parseInt(splitForStartTimeAMPM[0]) > 12
									&& Integer.parseInt(splitForEndTimeAMPM[0]) < 12) {

								attendanceLogsBulkEntry.setOverNight(1);
								attendanceLogsBulkEntry.setEndDateAndTime(currentDateActual + " " + shiftEndTime);
							} else {
								attendanceLogsBulkEntry.setOverNight(0);
								attendanceLogsBulkEntry.setEndDateAndTime(currentDate + " " + shiftEndTime);
							}

							attendanceLogsBulkEntry.setRecordDate(currentDate);
							if (currentStatus == 0) {
								attendanceLogsBulkEntry.setInTime(currentTime);

							} else if (currentStatus == 1) {
								attendanceLogsBulkEntry.setOutTime(currentTime);

							} else if (currentStatus == 2) {
								attendanceLogsBulkEntry.setBreakOut(currentTime);

							} else if (currentStatus == 3) {
								attendanceLogsBulkEntry.setBreakIn(currentTime);

							} else if (currentStatus == 4) {
								attendanceLogsBulkEntry.setBreakOut2(currentTime);

							} else if (currentStatus == 5) {
								attendanceLogsBulkEntry.setBreakIn2(currentTime);
							}

							attendanceLogsBulkEntry.setLocation(currentLocation);
							attendanceLogsBulkEntry.setShift(currentShiftId);
							attendanceLogsBulkEntry.setTimeAsPerShftTimings(currentTime);
							attendanceLogsBulkEntry.setWorkID(currentWorkId);
							attendanceLogsBulkEntry.setStartDateAndTime(currentDate + " " + shiftStartTime);
							attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrys(attendanceLogsBulkEntry);

							try {
								String previouslySetStartDateTime = attendanceLogsBulkEntry.getStartDateAndTime();
								String previouslySetEndDateTime = attendanceLogsBulkEntry.getEndDateAndTime();
								Date startDateAndTimeDateFormat = dateFormat.parse(previouslySetStartDateTime);
								Date endDateAndTimeDateFormat = dateFormat.parse(previouslySetEndDateTime);

								DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
								if (!currentShiftDefinitionObj.getPartialDay().equals("0")) {
									if (startDateAndTimeDateFormat.getDay() == Integer
											.parseInt(currentShiftDefinitionObj.getPartialDay()) - 1) {
										int temp = startDateAndTimeDateFormat.getDay();
										attendanceLogsBulkEntry.setStartDateAndTime(
												currentDate + " " + currentShiftDefinitionObj.getPartialDayStartTime());
										String[] tempPreviousDate = attendanceLogsBulkEntry.getEndDateAndTime()
												.split(" ");
										attendanceLogsBulkEntry.setEndDateAndTime(tempPreviousDate[0] + " "
												+ currentShiftDefinitionObj.getPartialDayEndTime());
									}
								}

								previouslySetStartDateTime = attendanceLogsBulkEntry.getStartDateAndTime();
								previouslySetEndDateTime = attendanceLogsBulkEntry.getEndDateAndTime();

								startDateAndTimeDateFormat = dateFormat.parse(previouslySetStartDateTime);
								endDateAndTimeDateFormat = dateFormat.parse(previouslySetEndDateTime);

								if (!currentShiftDefinitionObj.getGraceTime().equals("0")) {
									Date startDateAndTimeDateFormatCopy = startDateAndTimeDateFormat;
									String graceTime = currentShiftDefinitionObj.getGraceTime();
									String[] graceTimeArray = graceTime.split(":");
									startDateAndTimeDateFormat.setHours(startDateAndTimeDateFormat.getHours()
											+ Integer.parseInt(graceTimeArray[0]));
									startDateAndTimeDateFormat.setMinutes(startDateAndTimeDateFormat.getMinutes()
											+ Integer.parseInt(graceTimeArray[1]));
									startDateAndTimeDateFormat.setSeconds(startDateAndTimeDateFormat.getSeconds()
											+ Integer.parseInt(graceTimeArray[2]));
									attendanceLogsBulkEntry
											.setStartDateAndTimeGrace(dateFormat.format(startDateAndTimeDateFormat));
									startDateAndTimeDateFormat = startDateAndTimeDateFormatCopy;
								}

							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrys(attendanceLogsBulkEntry);

						} else {
							if (currentShiftId == 0) {
								AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();
								attendanceLogsBulkEntry.setOverNight(0);
								attendanceLogsBulkEntry.setEndDateAndTime(currentDate + " 23:59:59");
								attendanceLogsBulkEntry.setRecordDate(currentDate);
								if (currentStatus == 0) {
									attendanceLogsBulkEntry.setInTime(currentTime);
								} else if (currentStatus == 1) {
									attendanceLogsBulkEntry.setOutTime(currentTime);

								} else if (currentStatus == 2) {
									attendanceLogsBulkEntry.setBreakOut(currentTime);
								} else if (currentStatus == 3) {
									attendanceLogsBulkEntry.setBreakIn(currentTime);
								} else if (currentStatus == 4) {
									attendanceLogsBulkEntry.setBreakOut2(currentTime);
								} else if (currentStatus == 5) {
									attendanceLogsBulkEntry.setBreakIn2(currentTime);
								}

								attendanceLogsBulkEntry.setLocation(currentLocation);
								attendanceLogsBulkEntry.setShift(currentShiftId);
								attendanceLogsBulkEntry.setTimeAsPerShftTimings(currentTime);
								attendanceLogsBulkEntry.setWorkID(currentWorkId);
								attendanceLogsBulkEntry.setStartDateAndTime(currentDate + " 0:0:0");
								attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrysDefault(attendanceLogsBulkEntry);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("ManagerDashBoard");
	}

//========================================================================================================================================//	
//		try {
//				
//				Date shftstarttime = timeFormat.parse(shiftStartTime);
//              Date ctime = timeFormat.parse(currentTime);
//              
//            if (ctime.after(shftstarttime)) {
//          	attendanceLogsBulkEntry.setLateComing(time);
//              
//          }
//
//			}catch (Exception e) {
//			// TODO: handle exception
//				System.out.println("error:"+e.getMessage());
//		}

//	@RequestMapping(value = "showviewattendance.htm", method = RequestMethod.POST)
//	public ModelAndView showviewattendance(@RequestParam(value = "employeeNo") int employeeNo,HttpServletRequest request, HttpServletResponse response, HttpSession session,
//			Attendance attendance) throws SQLException {
//
//	
//		Connection conn = null;
//		ConnectionDao d = new ConnectionDao();
//		conn = d.getConnection();
//		PreparedStatement ps = null;
//				
//		SimpleDateFormat Date12 = new SimpleDateFormat("yyyy.MM.dd");
//		String date1 = Date12.format(new Date());
//
//		
//		int empId = (Integer) session.getAttribute("loginUserId");
//		
//			
//			//String show="select * From attendance where empCode=? AND Date=? ";
//		
//			String show="select * From attendance where empCode=? AND Date=? ";
//			ps=conn.prepareStatement(show);
//			ps.setInt(1, empId);
//			ps.setString(2, date1);
//		
//			ResultSet rs4 = ps.executeQuery();
//			while (rs4.next()) {
//			
//				int empCode = rs4.getInt(2);
//				String Date=rs4.getString(3);
//				String checkin=rs4.getString(4);
//				String checkout=rs4.getString(5);
//				
//				System.out.println("WorkID:" + empId + " "+ "Date:" + Date+" "+"CheckIn:"+ checkin+" "+ "CheckOut:" + checkout);
//			}
//		
//		return new ModelAndView("viewemployeeattendance",modelMap);
//	}

	@RequestMapping(value = "setProjectView.htm", method = RequestMethod.GET)
	public ModelAndView setProjectView(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<MasterPrivileges> masterPrivilegesList = masterPrivilegesDAO.getMasterPrivileges();
		modelMap.addAttribute("masterPrivilegesList", masterPrivilegesList);
		EncryptPassword encryptPassword;
		try {
			encryptPassword = new EncryptPassword();
			modelMap.addAttribute("encryptedStatus", encryptPassword.encrypt("1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("setProjectView", modelMap);
	}

	@RequestMapping(value = "checkPasswordForProjectView.htm", method = RequestMethod.POST)
	public void checkPasswordForProjectView(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		PrintWriter out = response.getWriter();
		String validPassFlag = "false";
		if (username.equals("einzig") && password.equals("einzig")) {
			validPassFlag = "true";
		}
		out.write(validPassFlag);
	}

	@RequestMapping(value = "saveMasterPrivileges.htm", method = RequestMethod.POST)
	public ModelAndView saveMasterPrivileges(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String encryptedStatus = "";
		try {
			EncryptPassword encryptPassword = new EncryptPassword();
			encryptedStatus = encryptPassword.encrypt("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<MasterPrivileges> masterPrivilegesList = masterPrivilegesDAO.getMasterPrivileges();
		for (MasterPrivileges masterPrivilege : masterPrivilegesList) {
			if (masterPrivilege.getPrivilegeName().equals("DISTNA")) {
				if (null != request.getParameter("DISTNA"))
					masterPrivilege.setPrivilegeStatus("1");
				else
					masterPrivilege.setPrivilegeStatus("0");
			}

			if (masterPrivilege.getPrivilegeName().equals("VMS")) {
				if (null != request.getParameter("VMS"))
					masterPrivilege.setPrivilegeStatus(encryptedStatus);
				else
					masterPrivilege.setPrivilegeStatus("0");
			}
		}
		masterPrivilegesDAO.savaList(masterPrivilegesList);
		return new ModelAndView("addlocationdataselfclose");
	}

	public ModelMap getModelMapForUserDashboard(Employee employee) {
		ModelMap modelMap = new ModelMap();
		Date currentDate = Calendar.getInstance().getTime();
		Calendar calendarStartOfWeek = Calendar.getInstance();
		Calendar calendarEndOfWeek = Calendar.getInstance();
		calendarEndOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
		String currentDateString = dateFormat.format(currentDate);
		List<Employee> monthsBirthdayList = employeeDAO.getEmployeesWithBirthdays();
		int employeeMessagesCount = employeeMessagesDAO.getUnreadEmployeeMessagesCount(employee.getEmployeeId());
		modelMap.addAttribute("employeeMessagesListSize", employeeMessagesCount);
		modelMap.addAttribute("monthsBirthdayList", monthsBirthdayList);
		modelMap.addAttribute("currentDate", currentDateString);
		modelMap.addAttribute("loginUserName", employee.getFirstName());
		modelMap.addAttribute("loginUserEmployeeNo", employee.getEmployeeNo());
		modelMap.addAttribute("loginUserEmployeeId", employee.getEmployeeId());
		return modelMap;
	}

	@RequestMapping(value = "Back.htm", method = RequestMethod.GET)
	public ModelAndView back(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("admindashboard");
	}

	public ModelMap getDesignationLevelModelMap() {
		ModelMap modelMap = new ModelMap();
		int nextId = designationLevelDAO.getDesignationLevelCurrentId();
		modelMap.addAttribute("nextId", nextId);
		return modelMap;
	}

	@RequestMapping(value = "showDesignationLevel.htm", method = RequestMethod.GET)
	public ModelAndView showDesignationLevel(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, DesignationLevel designationLevel) {

		ModelMap modelMap = getDesignationLevelModelMap();
		return new ModelAndView("designationlevelpage", modelMap);
	}

	@RequestMapping(value = "addDesignationLevel.htm", method = RequestMethod.GET)
	public ModelAndView addDesignationLevel(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, DesignationLevel designationLevel, BindingResult bindingResult) {
		ModelMap modelMap = getDesignationLevelModelMap();
		DesignationLevelValidator designationLevelValidator = new DesignationLevelValidator();
		designationLevelValidator.validate(designationLevel, bindingResult, designationLevelDAO);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("designationlevelpage", modelMap);
		} else {
			designationLevelDAO.saveOrUpdateDesignationLevel(designationLevel);
			return new ModelAndView(new RedirectView("showDesignationLevel.htm"));
		}
	}

	@RequestMapping(value = "viewDesignationLevel.htm", method = RequestMethod.GET)
	public ModelAndView viewDesignationLevel(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, DesignationLevel designationLevel) {
		ModelMap modelMap = new ModelMap();
		List<DesignationLevel> designationLevelList = designationLevelDAO.getDesignationLevelList();
		modelMap.addAttribute("designationLevelList", designationLevelList);
		modelMap.addAttribute("designationLevelListSize", designationLevelList.size());
		return new ModelAndView("viewdesignationlevel", modelMap);
	}

	@RequestMapping(value = "deleteDesignationLevel.htm", method = RequestMethod.POST)
	public ModelAndView deleteDesignationLevel(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		designationLevelDAO.deleteDesignationLevel(id);
		return new ModelAndView(new RedirectView("viewDesignationLevel.htm"));
	}

	@RequestMapping(value = "updateDesignationLevel.htm", method = RequestMethod.POST)
	public ModelAndView updateDesignationLevel(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		DesignationLevel designationLevel = designationLevelDAO.getDesignationLevelById(id);
		if (designationLevel != null) {
			modelMap.addAttribute("designationLevel", designationLevel);
		}
		return new ModelAndView("updatedesignationlevel", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateDesignationLevel.htm", method = RequestMethod.GET)
	public ModelAndView saveOrUpdateDesignationLevel(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, DesignationLevel designationLevel, BindingResult bindingResult) {
		DesignationLevelValidator designationLevelValidator = new DesignationLevelValidator();
		designationLevelValidator.validate(designationLevel, bindingResult, designationLevelDAO);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updatedesignationlevel");
		} else {
			designationLevelDAO.saveOrUpdateDesignationLevel(designationLevel);
			return new ModelAndView(new RedirectView("viewDesignationLevel.htm"));
		}
	}

	public ModelMap getCompanyModelMap() {
		ModelMap modelMap = new ModelMap();
		int currentId = companyDAO.getCompanyCurrentId();
		modelMap.addAttribute("zonesList", zonesDAO.getZonesList());
		modelMap.addAttribute("currencyList", currencyListDAO.getCurrencyList());
		modelMap.addAttribute("currentId", currentId);
		modelMap.addAttribute("locationList", locationDAO.getLocation());

		return modelMap;
	}

	@RequestMapping(value = "showCompany.htm", method = RequestMethod.GET)
	public ModelAndView showCompany(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Company company) {
		ModelMap modelMap = getCompanyModelMap();
		if (companyDAO.getCompanyCount() > 0) {
			modelMap.addAttribute("errorMessageFlag", 0);
			modelMap.addAttribute("errorMessage", "You cannot add more companies.");
		} else {
			modelMap.addAttribute("errorMessageFlag", 1);
		}
		return new ModelAndView("companypage", modelMap);
	}

	@RequestMapping(value = "saveCompany.htm", method = RequestMethod.POST)
	public ModelAndView saveCompany(@RequestParam(value = "logoFile") MultipartFile logoFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Company company,
			BindingResult bindingResult) {
		ModelMap modelMap = getCompanyModelMap();
		CompanyValidator companyValidator = new CompanyValidator();
		/*
		 * if(companyDAO.getCompanyCount()>0) { modelMap.addAttribute("errorMessage",
		 * "You cannot add more companies."); return new ModelAndView(new
		 * RedirectView("showCompany.htm")); }
		 */

		companyValidator.validate(company, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("companypage", modelMap);
		}

		else {
			try {
				Imageload imageload = new Imageload();
				String path = imageload.uploadImage(logoFile, request, "ProfilePhotos", logoFile.getOriginalFilename());
				company.setLogo("ProfilePhotos/" + logoFile.getOriginalFilename());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			companyDAO.saveCompany(company);
			return new ModelAndView(new RedirectView("showCompany.htm"));
		}
	}

	@RequestMapping(value = "viewUpdateCompany.htm", method = RequestMethod.GET)
	public ModelAndView viewUpdateCompany(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getCompanyModelMap();
		List<Company> companyList = companyDAO.getCompanies();
		modelMap.addAttribute("companyList", companyList);
		modelMap.addAttribute("companyListSize", companyList.size());
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		String[] months = dateFormatSymbols.getMonths();
		List<String> monthsList = Arrays.asList(months);
		modelMap.addAttribute("monthsList", monthsList);
		return new ModelAndView("viewcompanylist", modelMap);
	}

	@RequestMapping(value = "updateCompany.htm", method = RequestMethod.POST)
	public ModelAndView updateCompany(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getCompanyModelMap();
		Company company = companyDAO.getCompanyById(id);
		// modelMap.addAttribute("currentId",currentId);
		if (company != null) {
			modelMap.addAttribute("company", company);
		}
		return new ModelAndView("updatecompany", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateCompany.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateCompany(@RequestParam(value = "logoFile") MultipartFile logoFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Company company,
			BindingResult bindingResult) {
		ModelMap modelMap = getCompanyModelMap();

		CompanyValidator companyValidator = new CompanyValidator();

		companyValidator.validate(company, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updatecompany", modelMap);
		}

		else {
			if (logoFile.getSize() != 0) {
				try {
					Imageload imageload = new Imageload();
					String path = imageload.uploadImage(logoFile, request, "ProfilePhotos",
							logoFile.getOriginalFilename());
					company.setLogo("ProfilePhotos/" + logoFile.getOriginalFilename());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			companyDAO.saveOrUpdateCompany(company);
			return new ModelAndView(new RedirectView("viewUpdateCompany.htm"), modelMap);
		}
	}

	@RequestMapping(value = "deleteCompany.htm", method = RequestMethod.POST)
	public ModelAndView deleteCompany(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		companyDAO.deleteCompany(id);
		/* return new ModelAndView(new RedirectView("viewUpdateCompany.htm")); */
		return new ModelAndView(new RedirectView("loginpage.htm"));

	}

	public ModelMap getWorkspaceModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("locationList", locationDAO.getLocation());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		return modelMap;
	}

	@RequestMapping(value = "showAddWorkspaces.htm", method = RequestMethod.GET)
	public ModelAndView showAddWorkspaces(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Workspaces workspaces) {
		ModelMap modelMap = getWorkspaceModelMap();
		return new ModelAndView("showaddworkspaces", modelMap);
	}

	@RequestMapping(value = "showAddDivisionDiv.htm", method = RequestMethod.POST)
	public ModelAndView addDivisionDiv(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division) {
		ModelMap modelMap = getDivisionModelMap();
		return new ModelAndView("adddivisioninternally", modelMap);
	}

	@RequestMapping(value = "addDivisionInternally.htm", method = RequestMethod.POST)
	public void addDivisionInternally(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division, BindingResult bindingResult) {
		division.setDivisionName(request.getParameter("divisionName"));
		division.setDivisionCode(request.getParameter("divisionCode"));
		division.setDivisionDescription(request.getParameter("divisionDescription"));
		division.setDivisionHead(Integer.parseInt(request.getParameter("divisionHead")));
		division.setLocationId(Integer.parseInt(request.getParameter("locationId")));
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		divisionValidator.validate(division, bindingResult);
		if (bindingResult.hasErrors()) {
			printWriter.write("false");
		} else {
			divisionDAO.addDivision(division);
			printWriter.write("true");
		}
	}

	@RequestMapping(value = "addWorkspaces.htm", method = RequestMethod.POST)
	public ModelAndView addWorkspaces(@RequestParam(value = "photoFile") MultipartFile photoFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Workspaces workspaces,
			BindingResult bindingResult) {
		ModelMap modelMap = getWorkspaceModelMap();

		WorkspacesValidator workspacesValidator = new WorkspacesValidator();
		int departmentId = workspaces.getDepartment();
		if (departmentId != 0) {
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			// Location location=locationDAO.getLocationObject(locationId);
			workspaces.setDivision(divisionId);
			workspaces.setLocation(locationId);
		}
		workspacesValidator.validate(workspaces, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("showaddworkspaces", modelMap);
		}

		else {

			try {
				Imageload imageload = new Imageload();
				String path = imageload.uploadImage(photoFile, request, "ProfilePhotos",
						photoFile.getOriginalFilename());
				workspaces.setPhoto("ProfilePhotos/" + photoFile.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workspacesDAO.saveWorkspace(workspaces);
			return new ModelAndView(new RedirectView("showAddWorkspaces.htm"));
		}
	}

	@RequestMapping(value = "viewWorkspaces.htm", method = RequestMethod.GET)
	public ModelAndView viewWorkspaces(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Workspaces workspaces) {
		ModelMap modelMap = getWorkspaceModelMap();
		List<Workspaces> workList = workspacesDAO.getAllWorkspaces();
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		modelMap.addAttribute("workspaceList", workList);
		modelMap.addAttribute("workspaceListSize", workList.size());

		return new ModelAndView("viewworkspaces", modelMap);
	}

	@RequestMapping(value = "deleteWorkspace.htm", method = RequestMethod.POST)
	public ModelAndView deleteWorkspace(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Workspaces workspaces) {
		workspacesDAO.deleteWorkspace(id);
		return new ModelAndView(new RedirectView("viewWorkspaces.htm"));
	}

	@RequestMapping(value = "updateWorkspace.htm", method = RequestMethod.POST)
	public ModelAndView updateWorkspace(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getWorkspaceModelMap();
		Workspaces workspaces = workspacesDAO.getWorkspaceById(id);
		if (workspaces != null) {
			modelMap.addAttribute("workspaces", workspaces);
		}

		int departmentId = workspaces.getDepartment();
		if (departmentId != 0) {
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			modelMap.addAttribute("division", division);
			modelMap.addAttribute("location", location);
		}
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		return new ModelAndView("updateworkspace", modelMap);
	}

	@RequestMapping(value = "updateWorkspaceForInternalDiv.htm", method = RequestMethod.GET)
	public ModelAndView updateWorkspaceForInternalDiv(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getWorkspaceModelMap();

		Workspaces workspaces = workspacesDAO.getWorkspaceById(id);
		if (workspaces != null) {
			modelMap.addAttribute("workspaces", workspaces);
		}
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		return new ModelAndView("updateworkspace", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateWorkspaces.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateWorkspaces(@RequestParam(value = "photoFile") MultipartFile photoFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Workspaces workspaces,
			BindingResult bindingResult) {
		ModelMap modelMap = getWorkspaceModelMap();

		WorkspacesValidator workspacesValidator = new WorkspacesValidator();

		int departmentId = workspaces.getDepartment();
		if (departmentId != 0) {
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			workspaces.setDivision(divisionId);
			workspaces.setLocation(locationId);
		}

		workspacesValidator.validate(workspaces, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateworkspace", modelMap);
		}

		else {
			if (photoFile.getSize() != 0) {
				try {
					Imageload imageload = new Imageload();
					String path = imageload.uploadImage(photoFile, request, "ProfilePhotos",
							photoFile.getOriginalFilename());
					workspaces.setPhoto("ProfilePhotos/" + photoFile.getOriginalFilename());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			workspacesDAO.saveOrUpdateWorkspace(workspaces);
			return new ModelAndView(new RedirectView("viewWorkspaces.htm"), modelMap);
		}
	}

	public ModelMap getJobRolesModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Priority> prioritiesList = priorityDAO.getPriorityList();
		modelMap.addAttribute("priorityList", prioritiesList);
		return modelMap;
	}

	@RequestMapping(value = "showAddJobRoles.htm", method = RequestMethod.GET)
	public ModelAndView showAddJobRoles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JobRoles jobRoles) {
		ModelMap modelMap = getJobRolesModelMap();
		return new ModelAndView("showaddjobroles", modelMap);
	}

	@RequestMapping(value = "addJobRoles.htm", method = RequestMethod.POST)
	public ModelAndView addJobRoles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JobRoles jobRoles, BindingResult bindingResult) {
		ModelMap modelMap = getJobRolesModelMap();

		jobRolesValidator.validate(jobRoles, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("showaddjobroles", modelMap);
		} else {
			jobRolesDAO.saveOrUpdateJobRoles(jobRoles);
			return new ModelAndView(new RedirectView("showAddJobRoles.htm"));
		}
	}

	@RequestMapping(value = "viewJobRoles.htm", method = RequestMethod.GET)
	public ModelAndView viewJobRoles(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getJobRolesModelMap();
		List<JobRoles> jobRolesList = jobRolesDAO.getJobRolesList();
		modelMap.addAttribute("jobRolesList", jobRolesList);
		modelMap.addAttribute("jobRolesListSize", jobRolesList.size());
		return new ModelAndView("viewjobroles", modelMap);
	}

	@RequestMapping(value = "deleteJobRoles.htm", method = RequestMethod.POST)
	public ModelAndView deleteJobRoles(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		jobRolesDAO.deleteJobRoles(id);
		return new ModelAndView(new RedirectView("viewJobRoles.htm"));
	}

	@RequestMapping(value = "updateJobRoles.htm", method = RequestMethod.POST)
	public ModelAndView updateJobRoles(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelMap modelMap = getJobRolesModelMap();
		JobRoles jobRoles = jobRolesDAO.getJobRolesListById(id);
		if (jobRoles != null) {
			modelMap.addAttribute("jobRoles", jobRoles);
		}

		/*
		 * List<JobRoles> jobRolesList=jobRolesDAO.getJobRolesList();
		 * modelMap.addAttribute("jobRolesList",jobRolesList);
		 */
		return new ModelAndView("updatejobroles", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateJobRoles.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateJobRoles(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, JobRoles jobRoles, BindingResult bindingResult) {
		ModelMap modelMap = getJobRolesModelMap();
		jobRolesValidator.validate(jobRoles, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updatejobroles", modelMap);
		} else {
			jobRolesDAO.saveOrUpdateJobRoles(jobRoles);
			return new ModelAndView(new RedirectView("viewJobRoles.htm"));
		}
	}

	public ModelMap getPriorityModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Priority> prioritiesList = priorityDAO.getPriorityList();
		modelMap.addAttribute("id", 0);
		modelMap.addAttribute("priorityList", prioritiesList);
		return modelMap;
	}

	@RequestMapping(value = "addPriorityDiv.htm", method = RequestMethod.POST)
	public ModelAndView addPriorityDiv(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Priority priority) {
		ModelMap modelMap = getPriorityModelMap();
		return new ModelAndView("addprioritydiv", modelMap);
	}

	@RequestMapping(value = "savePriorityDiv.htm", method = RequestMethod.POST)
	public ModelAndView savePriorityDiv(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Priority priority, BindingResult bindingResult) {

		ModelMap modelMap = getPriorityModelMap();
		int id = Integer.parseInt(request.getParameter("updateJobRolesId"));
		priorityValidator.validate(priority, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addprioritydiv", modelMap);
		} else {
			priorityDAO.savePriority(priority);
		}

		if (id == 0) {
			return new ModelAndView("addlocationdataselfclose");
		} else {
			/*
			 * JobRoles jobRoles=jobRolesDAO.getJobRolesListById(id); if(jobRoles!=null) {
			 * modelMap.addAttribute("jobRoles",jobRoles); } List<JobRoles>
			 * jobRolesList=jobRolesDAO.getJobRolesList();
			 * modelMap.addAttribute("jobRolesList",jobRolesList); List<Priority>
			 * prioritiesList=priorityDAO.getPriorityList();
			 * modelMap.addAttribute("priorityList",prioritiesList);
			 */
			return new ModelAndView("addlocationdataselfclose");
		}
	}

	@RequestMapping(value = "addUpdatePriorityDiv.htm", method = RequestMethod.POST)
	public ModelAndView addUpdatePriorityDiv(@RequestParam(value = "id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Priority priority) {
		ModelMap modelMap = getPriorityModelMap();
		modelMap.addAttribute("id", id);
		return new ModelAndView("addprioritydiv", modelMap);
	}

	public ModelMap getCompanyPolicyModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Priority> prioritiesList = priorityDAO.getPriorityList();
		List<Departments> departmentsList = departmentDAO.getDepartment();
		List<Location> locationsList = locationDAO.getLocation();
		modelMap.addAttribute("departmentsList", departmentsList);
		modelMap.addAttribute("priorityList", prioritiesList);
		modelMap.addAttribute("locationsList", locationsList);
		return modelMap;
	}

	@RequestMapping(value = "showAddCompanyPolicies.htm", method = RequestMethod.GET)
	public ModelAndView showAddCompanyPolicies(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CompanyPolicies companyPolicies) {
		ModelMap modelMap = getCompanyPolicyModelMap();
		return new ModelAndView("showaddcompanypolicies", modelMap);
	}

	@RequestMapping(value = "addCompanyPolicies.htm", method = RequestMethod.POST)
	public ModelAndView addCompanyPolicies(@RequestParam(value = "documentFile") MultipartFile documentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CompanyPolicies companyPolicies, BindingResult bindingResult) {

		ModelMap modelMap = getCompanyPolicyModelMap();

		companyPoliciesValidator.validate(companyPolicies, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("showaddcompanypolicies", modelMap);
		}

		else {

			if (documentFile.getSize() != 0) {
				try {
					Imageload imageload = new Imageload();
					String path = imageload.uploadImage(documentFile, request, "Documents",
							documentFile.getOriginalFilename());
					companyPolicies.setDocument("Documents/" + documentFile.getOriginalFilename());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			companyPoliciesDAO.saveOrUpdateCompanyPolicies(companyPolicies);
			return new ModelAndView(new RedirectView("showAddCompanyPolicies.htm"));
		}
	}

	@RequestMapping(value = "viewCompanyPolicies.htm", method = RequestMethod.GET)
	public ModelAndView viewCompanyPolicies(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getCompanyPolicyModelMap();
		List<CompanyPolicies> companyPoliciesList = companyPoliciesDAO.getCompanyPolicies();
		modelMap.addAttribute("companyPoliciesList", companyPoliciesList);
		modelMap.addAttribute("companyPoliciesListSize", companyPoliciesList.size());
		modelMap.addAttribute("prioritiesList", priorityDAO.getPriorityList());
		return new ModelAndView("viewcompanypolicies", modelMap);
	}

	@RequestMapping(value = "deleteCompanyPolicies.htm", method = RequestMethod.POST)
	public ModelAndView deleteCompanyPolicies(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		companyPoliciesDAO.deleteCompanyPolicies(id);
		return new ModelAndView(new RedirectView("viewCompanyPolicies.htm"));
	}

	@RequestMapping(value = "updateCompanyPolicies.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateCompanyPolicies(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getCompanyPolicyModelMap();

		CompanyPolicies companyPolicies = companyPoliciesDAO.getCompanyPoliciesById(id);
		if (companyPolicies != null) {
			modelMap.addAttribute("companyPolicies", companyPolicies);
			int departmentId = companyPolicies.getDepartmentId();
			if (departmentId != 0) {
				Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
				int divisionId = department.getDivision();
				Division division = divisionDAO.getDivision(divisionId);
				int locationId = division.getLocationId();
				Location location = locationDAO.getLocationObject(locationId);
				modelMap.addAttribute("division", division);
				modelMap.addAttribute("location", location);
			}
		}

		return new ModelAndView("updatecompanypolicies", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateCompanyPolicies.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateCompanyPolicies(@RequestParam(value = "documentFile") MultipartFile documentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CompanyPolicies companyPolicies, BindingResult bindingResult) {
		ModelMap modelMap = getCompanyPolicyModelMap();

		companyPoliciesValidator.validate(companyPolicies, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updatecompanypolicies", modelMap);
		}

		else

		{
			if (documentFile.getSize() != 0) {
				try {
					Imageload imageload = new Imageload();
					String path = imageload.uploadImage(documentFile, request, "Documents",
							documentFile.getOriginalFilename());
					companyPolicies.setDocument("Documents/" + documentFile.getOriginalFilename());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			companyPoliciesDAO.saveOrUpdateCompanyPolicies(companyPolicies);
			return new ModelAndView(new RedirectView("viewCompanyPolicies.htm"));
		}
	}

	/*
	 * @ModelAttribute("citiesListClass") public CitiesListClass
	 * getCitiesListClass() { CitiesListClass citiesListClass = new
	 * CitiesListClass(); AutoPopulatingList<Cities> autoPopulatingList=new
	 * AutoPopulatingList<Cities>(Cities.class); citiesDAO.getCities();
	 * citiesListClass.setAutoPopulatingListCities(autoPopulatingList); return
	 * citiesListClass; }
	 */

	@RequestMapping(value = "addlocation.htm", method = RequestMethod.GET)
	public ModelAndView addlocation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Location location) {
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		List<Location> locationList = locationDAO.getLocation();
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("internalLocationFlag", "0");
		modelMap.addAttribute("redirectUrl", "addlocation.htm");
		return new ModelAndView("addlocation", modelMap);
	}

	@RequestMapping(value = "addLocationInternally.htm", method = RequestMethod.GET)
	public ModelAndView addLocationInternally(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Location location) {
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		List<Location> locationList = locationDAO.getLocation();
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("internalLocationFlag", "1");
		modelMap.addAttribute("redirectUrl", "addLocationInternally.htm");
		return new ModelAndView("addlocationinternally", modelMap);
	}

	@RequestMapping(value = "showState.htm", method = RequestMethod.POST)
	public ModelAndView showState(@RequestParam("Id") int id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Employee employeeObj) {

		modelMap.addAttribute("States", statesDAO.getStatesById(id));
		/* modelMap.addAttribute("States",statesDAO.getStatesById(id)); */
		/*
		 * countryID=id; modelMap.addAttribute("countryID",id);
		 */
		return new ModelAndView("showStateDiv", modelMap);
	}

	@RequestMapping(value = "showStateLang.htm", method = RequestMethod.POST)
	public ModelAndView showStateLang(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		modelMap.addAttribute("States", statesDAO.getStatesById(id));
		countryID = id;
		modelMap.addAttribute("countryID", id);
		return new ModelAndView("showStateDivLang", modelMap);

	}

	@RequestMapping(value = "showAvalableCities.htm", method = RequestMethod.GET)
	public ModelAndView showAvalableCities(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<Cities> AvalableCities = citiesDAO.getCitiesById(id);
		modelMap.addAttribute("Cities", AvalableCities);
		countryID = id;
		modelMap.addAttribute("countryID", id);
		return new ModelAndView("showCitiesDiv", modelMap);

	}

	@RequestMapping(value = "showAvalableLanguages.htm", method = RequestMethod.POST)
	public ModelAndView showAvalableLanguages(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<States> AvalableLanguage = statesDAO.getStatesByStateId(id);
		System.out.println("AvalableLanguage: " + AvalableLanguage.size() + "id: " + id);
		modelMap.addAttribute("Languages", AvalableLanguage);

		return new ModelAndView("showLanguageDiv", modelMap);

	}

	@RequestMapping(value = "showCity.htm", method = RequestMethod.GET)
	public ModelAndView showCity(@RequestParam("redirectURL") String redirectURL,
			@RequestParam(value = "countryId") int countryIdCity, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Cities cities) {
		/*
		 * @RequestParam("location") int id, locationId=id;
		 */
		redirectUrl = redirectURL;
		countryIdForAddcity = countryIdCity;
		modelMap.addAttribute("States", statesDAO.getStatesById(countryIdCity));

		return new ModelAndView("showCityDiv", modelMap);

	}

	@RequestMapping(value = "addCity.htm", method = RequestMethod.POST)
	public ModelAndView addCity(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Cities cities) {

		cities.setCountryId(countryIdForAddcity);
		citiesDAO.saveCities(cities);
		return new ModelAndView(new RedirectView(redirectUrl));
	}

	@RequestMapping(value = "showDepartment.htm", method = RequestMethod.GET)
	public ModelAndView showDepartment(@RequestParam("deptid") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Departments departments) {
		departmentIdAdd = id;
		ModelMap map = new ModelMap();
		map.addAttribute("department", departmentDAO.getDepartment());
		List<Departments> departmentListForHead = departmentDAO.getDepartment();
		map.addAttribute("departmentListForHead", departmentListForHead);

		return new ModelAndView("showDepartmentDiv", map);

	}

	@RequestMapping(value = "addLocationData.htm", method = RequestMethod.POST)
	public ModelAndView addlocationdata(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Location location, BindingResult bindingResult) {
		locationValidator.validate(location, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addlocation", modelMap);
		}

		else {
			if (null != request.getParameter("state"))
				location.setState(Integer.parseInt(request.getParameter("state")));
			if (null != request.getParameter("city"))
				location.setCity(Integer.parseInt(request.getParameter("city")));
			locationDAO.saveLocation(location);
			return new ModelAndView(new RedirectView("addlocation.htm"));
		}
	}

	@RequestMapping(value = "addLocationDataInternally.htm", method = RequestMethod.POST)
	public ModelAndView addLocationDataInternally(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Location location, BindingResult bindingResult) {
		locationValidator.validate(location, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addlocationinternally", modelMap);
		}

		else {

			if (null != request.getParameter("state"))
				location.setState(Integer.parseInt(request.getParameter("state")));
			if (null != request.getParameter("city"))
				location.setCity(Integer.parseInt(request.getParameter("city")));
			locationDAO.saveLocation(location);
			return new ModelAndView("addlocationdataselfclose");
		}
	}

	@RequestMapping(value = "viewlocation.htm", method = RequestMethod.GET)
	public ModelAndView viewlocation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Location location) {
		/*
		 * ArrayList<Integer> parentLocationarr=new ArrayList<Integer>();
		 * ArrayList<Integer> countryarr=new ArrayList<Integer>(); ArrayList<Integer>
		 * statearr=new ArrayList<Integer>(); ArrayList<Integer> cityarr=new
		 * ArrayList<Integer>();
		 */
		/*
		 * int stateid = 0; int cityid = 0; for(int i=0;i<locationList.size();i++) {
		 * Location onelocation=locationList.get(i); int
		 * parentid=onelocation.getSub_locationof(); parentLocationarr.add(parentid);
		 * 
		 * int countryid=onelocation.getCountry(); countryarr.add(countryid);
		 * 
		 * stateid=onelocation.getState(); statearr.add(stateid);
		 * 
		 * cityid=onelocation.getCity(); cityarr.add(cityid);
		 * 
		 * }
		 */

		/*
		 * modelMap.addAttribute("parentLocationarr",parentLocationarr);
		 * modelMap.addAttribute("countryarr",countryarr);
		 * modelMap.addAttribute("statearr",statearr);
		 * modelMap.addAttribute("cityarr",cityarr);
		 */
		List<Location> locationList = locationDAO.getLocation();
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("parentLocations", locationDAO.getLocation());
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("states", statesDAO.getStates());
		modelMap.addAttribute("city", citiesDAO.getCities());
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("locationListSize", locationList.size());
		modelMap.addAttribute("zeroValue", 0);
		return new ModelAndView("viewlocation", modelMap);
	}

	@RequestMapping(value = "deletelocation.htm", method = RequestMethod.POST)
	public ModelAndView deleteLocat(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Location location) {

		locationDAO.deleteLocation(id);
		return new ModelAndView(new RedirectView("viewlocation.htm"));

	}

	@RequestMapping(value = "showupdatelocation.htm", method = RequestMethod.POST)
	public ModelAndView editLocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		List<Location> locationList = locationDAO.getLocationById(id);
		List<Location> allLocationList = locationDAO.getLocation();

		for (int i = 0; i < locationList.size(); i++) {
			Location locationobj = (Location) locationList.get(i);
			modelMap.addAttribute("Location", locationobj.getLocation());
			modelMap.addAttribute("ParentLocation", locationobj.getSub_locationof());
			modelMap.addAttribute("Address1", locationobj.getAddress1());
			modelMap.addAttribute("Address2", locationobj.getAddress2());
			modelMap.addAttribute("Details", locationobj.getDetails());
			modelMap.addAttribute("Email", locationobj.getEmail());
			modelMap.addAttribute("Fax", locationobj.getFax());
			modelMap.addAttribute("Gps", locationobj.getGps_location());
			modelMap.addAttribute("Phone", locationobj.getPhone());
			modelMap.addAttribute("Zipcode", locationobj.getPostal_code());
			modelMap.addAttribute("Website", locationobj.getWebsite());
			modelMap.addAttribute("Id", locationobj.getId());
			modelMap.addAttribute("AllowedIps", locationobj.getAllowed_ips());
			modelMap.addAttribute("Attention", locationobj.getAttention());
			modelMap.addAttribute("City", locationobj.getCity());
		}
		Location locationObj = locationDAO.getLocationObject(id);
		if (locationObj != null) {
			modelMap.addAttribute("locationObj", locationObj);
		}
		System.out.println("locationList: " + locationList.size());

		modelMap.addAttribute("allLocationList", allLocationList);
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("locationListSize", locationList.size());
		modelMap.addAttribute("redirectUrlupdate", "showupdatelocation.htm");
		modelMap.addAttribute("States", statesDAO.getStatesById(locationObj.getCountry()));
		List<Cities> AvalableCities = citiesDAO.getCitiesById(locationObj.getState());
		modelMap.addAttribute("Cities", AvalableCities);
		return new ModelAndView("editlocation", modelMap);

	}

	@RequestMapping(value = "showupdatelocationAddnew.htm", method = RequestMethod.GET)
	public ModelAndView showupdatelocationAddnew(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Location location) {

		List<Location> locationList = locationDAO.getLocationById(id);
		for (int i = 0; i < locationList.size(); i++) {
			Location locationobj = (Location) locationList.get(i);
			modelMap.addAttribute("Location", locationobj.getLocation());
			modelMap.addAttribute("ParentLocation", locationobj.getSub_locationof());
			modelMap.addAttribute("Address1", locationobj.getAddress1());
			modelMap.addAttribute("Address2", locationobj.getAddress2());
			modelMap.addAttribute("Details", locationobj.getDetails());
			modelMap.addAttribute("Email", locationobj.getEmail());
			modelMap.addAttribute("Fax", locationobj.getFax());
			modelMap.addAttribute("Gps", locationobj.getGps_location());
			modelMap.addAttribute("Phone", locationobj.getPhone());
			modelMap.addAttribute("Zipcode", locationobj.getPostal_code());
			modelMap.addAttribute("Website", locationobj.getWebsite());
			modelMap.addAttribute("Id", locationobj.getId());
			modelMap.addAttribute("AllowedIps", locationobj.getAllowed_ips());
			modelMap.addAttribute("Attention", locationobj.getAttention());
			modelMap.addAttribute("City", locationobj.getCity());
		}
		System.out.println("locationList: " + locationList.size());
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("locationListSize", locationList.size());
		return new ModelAndView("editlocation", modelMap);

	}

	@RequestMapping(value = "updatelocation.htm", method = RequestMethod.POST)
	public ModelAndView updateLocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Location locationObj, BindingResult bindingResult) {
		System.out.println("id: " + id);

		try {
			/*
			 * Location locationobj = null;
			 * 
			 * String location1=locationObj.getLocation(); int
			 * ParentLocation=locationObj.getSub_locationof(); String
			 * address1=locationObj.getAddress1(); String
			 * address2=locationObj.getAddress2(); String details= locationObj.getDetails();
			 * String email=locationObj.getEmail(); String fax=locationObj.getFax(); String
			 * gps=locationObj.getGps_location(); String phone=locationObj.getPhone();
			 * String zipcode=locationObj.getPostal_code(); String
			 * website=locationObj.getWebsite(); int Id=locationObj.getId(); String
			 * allowedips=locationObj.getAllowed_ips(); String attention=
			 * locationObj.getAttention(); int city=locationObj.getCity(); int
			 * state=locationObj.getState(); int country=locationObj.getCountry();
			 * 
			 * List<Location> locationList=locationDAO.getLocationById(id); for(int
			 * i=0;i<locationList.size();i++) { locationobj=(Location)locationList.get(i);
			 * locationobj.setLocation(location1);
			 * locationobj.setSub_locationof(ParentLocation);
			 * locationobj.setAddress1(address1); locationobj.setAddress2(address2);
			 * locationobj.setCity(city); locationobj.setDetails(details);
			 * locationobj.setEmail(email); locationobj.setFax(fax);
			 * locationobj.setGps_location(gps); locationobj.setPhone(phone);
			 * locationobj.setPostal_code(zipcode); locationobj.setWebsite(website);
			 * locationobj.setId(Id); locationobj.setAllowed_ips(allowedips);
			 * locationobj.setAttention(attention); locationobj.setCountry(country);
			 * locationobj.setState(state);
			 * 
			 * }
			 */
			if (null != request.getParameter("state") && locationObj.getState() == 0)
				locationObj.setState(Integer.parseInt(request.getParameter("state")));
			if (null != request.getParameter("city") && locationObj.getCity() == 0)
				locationObj.setCity(Integer.parseInt(request.getParameter("city")));
			locationValidator.validate(locationObj, bindingResult);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("editlocation", modelMap);
			} else {
				locationDAO.saveLocation(locationObj);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		;

		return new ModelAndView(new RedirectView("viewlocation.htm"));

	}

	public ModelMap getDepartmentModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("department", departmentDAO.getDepartment());
		List<Departments> departmentListForHead = departmentDAO.getDepartment();
		List<DesignationLevel> designationLevels = designationLevelDAO.getDesignationLevel();
		List<Designation> designations = designationDAO.getDesignation();
		List<Departments> departmentsList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentListForHead", departmentListForHead);
		modelMap.addAttribute("addDepartmentErrorMsg", "Department Not Added");
		modelMap.addAttribute("designations", designations);
		modelMap.addAttribute("designationLevels", designationLevels);
		modelMap.addAttribute("departments", departmentsList);
		return modelMap;
	}

	@RequestMapping(value = "addNewDepartment.htm", method = RequestMethod.POST)
	public ModelAndView addNewDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Designation designation, Departments departments, BindingResult bindingResult) {
		ModelMap modelMap = getDepartmentModelMap();

		departmentValidator.validate(departments, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("showaddworkspaces", modelMap);
		} else {
			departmentDAO.saveDepartments(departments);
		}

		if (departmentIdAdd == 0)
			return new ModelAndView("designation", modelMap);
		else if (departmentIdAdd == 1) {
			return new ModelAndView(new RedirectView("showAddWorkspaces.htm"));
		} else {
			return new ModelAndView(new RedirectView("showupdateDesiganationAddNew.htm?Id=" + departmentIdAdd));

		}

	}

	@RequestMapping(value = "department.htm", method = RequestMethod.GET)
	public ModelAndView department(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Departments departments) {
		ModelMap map = new ModelMap();
		map.addAttribute("departmentList", departmentDAO.getDepartment());
		map.addAttribute("employeeList", employeeDAO.getEmployeeList());
		map.addAttribute("divisionList", divisionDAO.getDivisionList());
		return new ModelAndView("departments", map);

	}

	@RequestMapping(value = "addDepartment.htm", method = RequestMethod.POST)
	public ModelAndView addDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Departments departments, BindingResult bindingResult) {
		departmentValidator.validate(departments, bindingResult);
		ModelMap map = new ModelMap();
		map.addAttribute("departmentList", departmentDAO.getDepartment());
		if (bindingResult.hasErrors()) {
			map.addAttribute("departmentList", departmentDAO.getDepartment());
			map.addAttribute("employeeList", employeeDAO.getEmployeeList());
			map.addAttribute("divisionList", divisionDAO.getDivisionList());
			return new ModelAndView("departments", map);
		} else {
			departmentDAO.saveDepartments(departments);
			return new ModelAndView(new RedirectView("department.htm"));
		}

	}

	@RequestMapping(value = "viewDepartment.htm", method = RequestMethod.GET)
	public ModelAndView viewDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Departments departments) {

		List<Departments> departmentList = departmentDAO.getDepartment();

		ArrayList<Integer> headarr = new ArrayList<Integer>();

		for (int i = 0; i < departmentList.size(); i++) {
			Departments onedepartment = departmentList.get(i);
			int deptid = onedepartment.getHead();
			headarr.add(deptid);
		}
		modelMap.addAttribute("headarr", headarr);
		modelMap.addAttribute("departmentList", departmentList);
		modelMap.addAttribute("departmentListSize", departmentList.size());
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		return new ModelAndView("viewdepartments", modelMap);

	}

	@RequestMapping(value = "deleteDepartment.htm", method = RequestMethod.POST)
	public ModelAndView deleteDepartment(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Departments departments) {
		departmentDAO.deleteDepartments(id);
		return new ModelAndView(new RedirectView("viewDepartment.htm"));
	}

	@RequestMapping(value = "showupdateDepartment.htm", method = RequestMethod.POST)
	public ModelAndView showupdateDepartment(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<Departments> departmentList = departmentDAO.getDepartmentById(id);
		List<Departments> departmentListForHead = departmentDAO.getDepartment();

		/*
		 * for(int i=0;i<departmentList.size();i++) { Departments
		 * deptobj=(Departments)departmentList.get(i);
		 * modelMap.addAttribute("id",deptobj.getId());
		 * modelMap.addAttribute("name",deptobj.getName());
		 * modelMap.addAttribute("head",deptobj.getHead());
		 * modelMap.addAttribute("emailId",deptobj.getEmail());
		 * 
		 * }
		 */
		if (departmentList.size() != 0)
			modelMap.addAttribute("id", departmentList.get(0).getId());
		modelMap.addAttribute("departments", departmentList.get(0));
		modelMap.addAttribute("departmentListForHead", departmentListForHead);
		modelMap.addAttribute("departmentList", departmentList);
		modelMap.addAttribute("departmentListSize", departmentList.size());
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		return new ModelAndView("editdepartment", modelMap);

	}

	/*
	 * @RequestMapping(value="showupdateDepartmentAddnew.htm",method=RequestMethod.
	 * GET) public ModelAndView showupdateDepartmentAddnew(@RequestParam("Id") int
	 * id, HttpServletRequest request,HttpServletResponse response, HttpSession
	 * session, Departments departments) {
	 * 
	 * 
	 * List<Departments> departmentList=departmentDAO.getDepartmentById(id);
	 * 
	 * for(int i=0;i<departmentList.size();i++) { Departments
	 * deptobj=(Departments)departmentList.get(i);
	 * modelMap.addAttribute("Id",deptobj.getId());
	 * modelMap.addAttribute("Name",deptobj.getName());
	 * modelMap.addAttribute("Head",deptobj.getHead());
	 * modelMap.addAttribute("EmailId",deptobj.getEmail());
	 * 
	 * }
	 * 
	 * 
	 * modelMap.addAttribute("departmentList",departmentList);
	 * modelMap.addAttribute("departmentListSize",departmentList.size()); return new
	 * ModelAndView("editdepartment",modelMap);
	 * 
	 * }
	 */

	@RequestMapping(value = "updatedepartment.htm", method = RequestMethod.POST)
	public ModelAndView updateDepartment(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Departments departments, BindingResult bindingResult) {
		try {
			/*
			 * Departments departmentobj = null;
			 * 
			 * String name=departments.getName(); int head=departments.getHead(); String
			 * email=departments.getEmail();
			 * 
			 * 
			 * List<Departments> departmentList=departmentDAO.getDepartmentById(id); for(int
			 * i=0;i<departmentList.size();i++) {
			 * departmentobj=(Departments)departmentList.get(i);
			 * departmentobj.setName(name); departmentobj.setHead(head);
			 * departmentobj.setEmail(email); departmentValidator.validate(departmentobj,
			 * bindingResult);
			 * 
			 * }
			 */

			departmentValidator.validate(departments, bindingResult);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("editdepartment", modelMap);
			} else {
				departmentDAO.saveDepartments(departments);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		;

		return new ModelAndView(new RedirectView("viewDepartment.htm"));

	}

	public ModelMap getDesignationModelMap() {
		ModelMap modelMap = new ModelMap();
		List<DesignationLevel> designationLevels = designationLevelDAO.getDesignationLevel();
		List<Designation> designations = designationDAO.getDesignation();
		List<Departments> departments = departmentDAO.getDepartment();
		modelMap.addAttribute("designations", designations);
		modelMap.addAttribute("designationLevels", designationLevels);
		modelMap.addAttribute("departments", departments);
		return modelMap;

	}

	@RequestMapping(value = "designation.htm", method = RequestMethod.GET)
	public ModelAndView designation(@RequestParam(value = "view") String view, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Designation designation) {
		ModelMap modelMap = getDesignationModelMap();
		if (view.equals("main")) {
			modelMap.addAttribute("addOrUpdateFlag", "0");
			return new ModelAndView("designation", modelMap);
		} else if (view.equals("addnew")) {
			modelMap.addAttribute("addOrUpdateFlag", "addnew");
			return new ModelAndView("designationAdd", modelMap);
		} else {
			modelMap.addAttribute("addOrUpdateFlag", view);
			return new ModelAndView("designationAdd", modelMap);
		}

	}

	@RequestMapping(value = "addDesignation.htm", method = RequestMethod.POST)
	public ModelAndView addDesignation(@RequestParam(value = "view") String view,
			@RequestParam(value = "addOrUpdateFlag") String addOrUpdateFlag, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Designation designation, BindingResult bindingResult) {
		ModelMap modelMap = getDesignationModelMap();

		designationValidator.validate(designation, bindingResult);

		if (bindingResult.hasErrors() && view.equals("main")) {
			return new ModelAndView("designation", modelMap);
		} else {
			designationDAO.saveDesignation(designation);
			if (view.equals("addWorkspace")) {
				if (addOrUpdateFlag.equals("addnew"))
					return new ModelAndView(new RedirectView("showAddWorkspaces.htm"));
				else
					return new ModelAndView(
							new RedirectView("updateWorkspaceForInternalDiv.htm?Id=" + addOrUpdateFlag));
			} else {
				/*
				 * List<DesignationLevel>
				 * designationLevels=designationLevelDAO.getDesignationLevel();
				 * List<Designation> designations=designationDAO.getDesignation();
				 * List<Departments> departments=departmentDAO.getDepartment();
				 * modelMap.addAttribute("designations",designations);
				 * modelMap.addAttribute("designationLevels",designationLevels);
				 * modelMap.addAttribute("departments",departments);
				 */
				return new ModelAndView(new RedirectView("designation.htm?view=main"));
			}
		}

	}
	/*
	 * @RequestMapping(value="addDesignationInternalDiv.htm",method=RequestMethod.
	 * POST) public void addDesignationInternalDiv(@RequestParam(value="view")
	 * String view,HttpServletRequest request,HttpServletResponse response,
	 * HttpSession session,Designation designation , BindingResult bindingResult) {
	 * ModelMap modelMap=getDesignationModelMap(); PrintWriter printWriter=null; try
	 * { printWriter = response.getWriter(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * if(bindingResult.hasErrors() && view.equals("main")) {
	 * printWriter.write("false");
	 * 
	 * } else { designationDAO.saveDesignation(designation);
	 * printWriter.write("true"); } }
	 */

	@RequestMapping(value = "viewDesignation.htm", method = RequestMethod.GET)
	public ModelAndView viewDesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Designation designation) {
		ModelMap map = new ModelMap();
		List<Designation> designationList = designationDAO.getDesignation();
		/*
		 * ArrayList<Integer> deptarr=new ArrayList<Integer>(); ArrayList<Integer>
		 * parentarr=new ArrayList<Integer>(); ArrayList<Integer> levelarr=new
		 * ArrayList<Integer>(); for(int i=0;i<designationList.size();i++) { Designation
		 * onedesination=designationList.get(i);
		 * 
		 * int deptid=onedesination.getDepartment_id(); deptarr.add(deptid);
		 * 
		 * int parentid=onedesination.getParent_designation(); parentarr.add(parentid);
		 * 
		 * int levelid=onedesination.getLevel(); levelarr.add(levelid);
		 * 
		 * }
		 */
		List<Departments> departmentList = departmentDAO.getDepartment();
		List<Designation> designations = designationDAO.getDesignation();
		List<DesignationLevel> levelList = designationLevelDAO.getDesignationLevel();

		map.addAttribute("departmentList", departmentList);
		map.addAttribute("designations", designations);
		/*
		 * map.addAttribute("deptarr", deptarr);
		 * map.addAttribute("parentarr",parentarr);
		 * map.addAttribute("levelarr",levelarr);
		 */
		map.addAttribute("levelList", levelList);
		map.addAttribute("designationList", designationList);
		map.addAttribute("designationListSize", designationList.size());
		return new ModelAndView("viewdesignation", map);

	}

	@RequestMapping(value = "deleteDesignation.htm", method = RequestMethod.POST)
	public ModelAndView deleteDesignation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Departments departments) {
		designationDAO.deleteDesignation(id);
		return new ModelAndView(new RedirectView("viewDesignation.htm"));

	}

	@RequestMapping(value = "showupdateDesiganation.htm", method = RequestMethod.POST)
	public ModelAndView showupdateDesiganation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Designation designation) {

		ModelMap modelMap = getDesignationModelMap();

		List<Designation> designationList = designationDAO.getDesignationById(id);
		for (int i = 0; i < designationList.size(); i++) {
			Designation designationobj = designationList.get(i);
			modelMap.addAttribute("id", designationobj.getId());
			modelMap.addAttribute("designation", designationobj);
			/*
			 * modelMap.addAttribute("Rank",designationobj.getRank());
			 * modelMap.addAttribute("ShortName",designationobj.getShortname());
			 * modelMap.addAttribute("Designation",designationobj.getDesignation());
			 */
		}

		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("designationListSize", designationList.size());
		return new ModelAndView("editdesignation", modelMap);

	}

	@RequestMapping(value = "showupdateDesiganationAddNew.htm", method = RequestMethod.GET)
	public ModelAndView showupdateDesiganationAddNew(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Designation designation) {

		ModelMap modelMap = getDesignationModelMap();
		List<Designation> designationList = designationDAO.getDesignationById(id);
		for (int i = 0; i < designationList.size(); i++) {
			Designation designationobj = (Designation) designationList.get(i);
			modelMap.addAttribute("Id", designationobj.getId());
			modelMap.addAttribute("Rank", designationobj.getRank());
			modelMap.addAttribute("ShortName", designationobj.getShortname());
			modelMap.addAttribute("Designation", designationobj.getDesignation());
		}
		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("designationListSize", designationList.size());
		return new ModelAndView("editdesignation", modelMap);

	}

	@RequestMapping(value = "updatedesignation.htm", method = RequestMethod.POST)
	public ModelAndView updatedesignation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Designation designation, BindingResult bindingResult) {
		ModelMap modelMap = getDesignationModelMap();

		try {

			designationValidator.validate(designation, bindingResult);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("editdesignation", modelMap);
			} else {
				designationDAO.saveDesignation(designation);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		;

		return new ModelAndView(new RedirectView("viewDesignation.htm"));

	}

	public ModelMap getDivisionModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Location> locationsList = locationDAO.getLocation();
		modelMap.addAttribute("locationsList", locationsList);
		return modelMap;
	}

	@RequestMapping(value = "showAddDivision.htm", method = RequestMethod.GET)
	public ModelAndView showAddDivision(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division) {
		ModelMap modelMap = getDivisionModelMap();
		return new ModelAndView("showadddivision", modelMap);
	}

	@RequestMapping(value = "addDivision.htm", method = RequestMethod.POST)
	public ModelAndView addDivision(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division, BindingResult bindingResult) {
		ModelMap modelMap = getDivisionModelMap();
		divisionValidator.validate(division, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("showadddivision", modelMap);
		} else {
			divisionDAO.addDivision(division);
			return new ModelAndView(new RedirectView("showAddDivision.htm"));
		}
	}

	@RequestMapping(value = "showViewDivision.htm", method = RequestMethod.GET)
	public ModelAndView viewDivision(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division) {
		ModelMap modelMap = getDivisionModelMap();
		List<Division> divisionList = divisionDAO.getDivisionList();
		modelMap.addAttribute("divisionList", divisionList);
		modelMap.addAttribute("divisionListSize", divisionList.size());
		List<Location> locationsList = locationDAO.getLocation();
		modelMap.addAttribute("locationsList", locationsList);
		return new ModelAndView("showviewdivision", modelMap);
	}

	@RequestMapping(value = "deleteDivision.htm", method = RequestMethod.POST)
	public ModelAndView deleteDivision(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Division division) {
		divisionDAO.deleteDivision(id);
		return new ModelAndView(new RedirectView("showViewDivision.htm"));
	}

	@RequestMapping(value = "	showUpdateDivision.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateDivision(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Division division) {
		ModelMap modelMap = getDivisionModelMap();
		division = divisionDAO.getDivision(id);
		List<Location> locationsList = locationDAO.getLocation();
		modelMap.addAttribute("locationsList", locationsList);
		modelMap.addAttribute("division", division);
		return new ModelAndView("updateDivision", modelMap);
	}

	@RequestMapping(value = "updateDivision.htm", method = RequestMethod.POST)
	public ModelAndView updateDivision(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Division division, BindingResult bindingResult) {

		ModelMap modelMap = getDivisionModelMap();

		divisionValidator.validate(division, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateDivision", modelMap);
		}

		else {
			divisionDAO.updateDivision(division);
			return new ModelAndView(new RedirectView("showViewDivision.htm"));
		}
	}

	public ModelMap getEmployeeModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Departments> departmentsList = departmentDAO.getDepartment();
		List<Division> divisionList = divisionDAO.getDivisionList();
		List<Designation> designationList = designationDAO.getDesignation();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		List<Location> locationsList = locationDAO.getLocation();
		List<Workspaces> workspacesList = workspacesDAO.getAllWorkspaces();
		List<Countries> countriesList = countriesDAO.getCountriesList();
		List<Cities> citiesList = citiesDAO.getCities();
		List<States> statesList = statesDAO.getStates();
		List<JobPositions> jobPositionsList = jobPositionDAO.getJobPositions();
		List<Lists> listsList = listsDAO.getListsByCategory("employment_status");
		modelMap.addAttribute("departmentsList", departmentsList);
		modelMap.addAttribute("divisionList", divisionList);
		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("employeeList", employeeList);
		modelMap.addAttribute("locationsList", locationsList);
		modelMap.addAttribute("workspacesList", workspacesList);
		modelMap.addAttribute("countriesList", countriesList);
		modelMap.addAttribute("citiesList", citiesList);
		modelMap.addAttribute("statesList", statesList);
		modelMap.addAttribute("jobPositionsList", jobPositionsList);
		modelMap.addAttribute("listsList", listsList);
		return modelMap;
	}

//	@RequestMapping(value = "showAddEmployees.htm", method = RequestMethod.GET)
//	public ModelAndView showAddEmployees(HttpServletRequest request, HttpServletResponse response, HttpSession session,
//			Employee employee) {
//		ModelMap modelMap = getEmployeeModelMap();
//		return new ModelAndView("showaddemployees", modelMap);
//	}
//
//	@RequestMapping(value = "addEmployee.htm", method = RequestMethod.POST)
//	public ModelAndView addEmployee(@RequestParam(value = "photoFile") MultipartFile photoFile,
//			HttpServletRequest request, HttpServletResponse response, HttpSession session, Employee employee) {
//		employeeDAO.saveEmployeeAndGetId(employee);
//
//		int currentId = employee.getEmployeeId();
//		String extension = "";
//		if (photoFile.getSize() > 0) {
//			extension = photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
//		}
//		String fileName = employee.getFirstName() + "_" + employee.getLastName() + "_" + currentId + extension;
//		try {
//			Imageload imageload = new Imageload();
//			String path = imageload.uploadImage(photoFile, request, "ProfilePhotos", fileName);
//			employee.setPhoto("ProfilePhotos/" + fileName);
//			employee.setEmployeeNo(currentId);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		employeeDAO.saveOrUpdateEmployeeAndGetId(employee);
//		return new ModelAndView(new RedirectView("showAddEmployees.htm"));
//	}

	public ModelMap getDeviceModelMap() {
		ModelMap modelMap = new ModelMap();
		List<AddDevice> addDeviceList = addDeviceDAO.getDeviceList();
		// if(addDeviceList.size()!=0)
		modelMap.addAttribute("addDeviceList", addDeviceList);
		modelMap.addAttribute("addDeviceListSize", addDeviceList.size());
		return modelMap;
	}

	@RequestMapping(value = "addAttendanceDevice.htm", method = RequestMethod.GET)
	public ModelAndView addAttendanceDevice(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, AddDevice addDevice) {

		ModelMap modelMap = getDeviceModelMap();
		return new ModelAndView("addattendancedevice", modelMap);
	}

	@RequestMapping(value = "testConnection.htm", method = RequestMethod.POST)
	public ModelAndView testConnection(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDeviceModelMap();
		String msgIP = "0";
		String testConnectionNativeString = "0";
		String deviceSerialNo = "0";
		String enterIP = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			enterIP = request.getParameter("ipAddress");
			log.info("Above IATTAPP testConnection.htm " + enterIP);

			// iAttApp=new IAttApp("Jawin.AttApp");

			log.info("Below IATTAPP testConnection.htm ");
			String s1 = iAttApp.TestMethod(enterIP);
			log.info("Ip return from dll" + s1);
			System.out.println("return ip " + s1);
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			log.info("view status method  " + testConnectionNativeString);
			System.out.println("View status" + testConnectionNativeString);
			deviceSerialNo = iAttApp.GetSerial(enterIP);
			// s1=iAttApp.DisConnect(enterIP);
			System.out.println("serial noooooooo= " + deviceSerialNo);

			System.out.println(testConnectionNativeString);
		} catch (COMException ex) {
			ex.printStackTrace();
			log.info("  COMException " + ex.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("   IOException " + e.getMessage());
		}
		String[] deviceIpAndSerialNoArray = deviceSerialNo.split("~");
		if (testConnectionNativeString.equals(enterIP) || !deviceIpAndSerialNoArray[1].equals("")) {
			modelMap.addAttribute("checkConnectionStatus", "successfull");
			modelMap.addAttribute("ipAddress", testConnectionNativeString);
		} else {
			modelMap.addAttribute("checkConnectionStatus", "failed");
		}
		modelMap.addAttribute("checkOnload", 1);
		return new ModelAndView("testconnection", modelMap);
	}

	@RequestMapping(value = "showTestConnection.htm", method = RequestMethod.GET)
	public void showTestConnection(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		try {
			PrintWriter out = response.getWriter();
			System.out.println();
			out.write(iAttApp.TestMethod("192.168.0.155"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelMap modelMap = getDeviceModelMap();
		modelMap.addAttribute("checkOnload", 0);
		// return new ModelAndView("testconnection",modelMap);
	}

	@RequestMapping(value = "addDevice.htm", method = RequestMethod.POST)
	public ModelAndView addDevice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			AddDevice addDevice) {
		String btnval = request.getParameter("save");
		System.out.println("Btn val" + btnval);
		if (btnval.equals("Save")) {
			if (addDeviceDAO.getDeviceInfoListByIp(addDevice.getIpAddress()).size() > 0) {
				ModelMap modelMap = getDeviceModelMap();
				modelMap.addAttribute("ipPresent", "Please select another IP Address");
				return new ModelAndView("addattendancedevice", modelMap);
			} else {
				addDeviceDAO.saveOrUpdateDevice(addDevice);
				return new ModelAndView(new RedirectView("addAttendanceDevice.htm"));
			}
		} else {
			String testConnectionNativeString = null;
			System.out.println("usb get");
			try {
				// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
				testConnectionNativeString = iAttApp.GetSerial((String) request.getParameter("ipAddress"));
				System.out.println("IP in get serial" + testConnectionNativeString);

				String[] deviceIpAndSerialNoArray = testConnectionNativeString.split("~");
				addDevice.setSerialNo(deviceIpAndSerialNoArray[1]);
				Connection conn = null;
				Statement stmt = null;
				Statement stmt1 = null;
				ConnectionDao d = new ConnectionDao();
				conn = d.getConnection();

				try {
					stmt = conn.createStatement();
					String sql1 = "insert into alloweddevices (serialNo) values('" + deviceIpAndSerialNoArray[1] + "')";
					stmt = conn.createStatement();

					String sql = "select * from alloweddevices where serialNo='" + deviceIpAndSerialNoArray[1] + "'";
					// System.out.println(sql);
					stmt1 = conn.createStatement();
					ResultSet rs = stmt1.executeQuery(sql);

					if (rs.next()) {
					} else {
						int i = stmt.executeUpdate(sql1);
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// modelMap.addAttribute("serialno", "Logs Download successfully!");
			} catch (Exception e) {
				// TODO: handle exception
			}
			ModelMap modelMap = getDeviceModelMap();
			return new ModelAndView("addattendancedevice", modelMap);
			// return new ModelAndView(new RedirectView("addAttendanceDevice.htm"));
		}
	}

	@RequestMapping(value = "deleteattendancedevice.htm", method = RequestMethod.GET)
	public ModelAndView deleteAttendanceDevice(@RequestParam("ipAddress") String ipAddress, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		addDeviceDAO.deleteAttendanceDevice(ipAddress);
		return new ModelAndView(new RedirectView("updateAttendanceDevice.htm"));
	}

	@RequestMapping(value = "updateAttendanceDevice.htm", method = RequestMethod.GET)
	public ModelAndView updateAttendanceDevice(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, AddDevice addDevice) {
		ModelMap modelMap = getDeviceModelMap();
		return new ModelAndView("updateattendancedevice", modelMap);
	}

	@RequestMapping(value = "showDeviceInfodiv.htm", method = RequestMethod.POST)
	public ModelAndView showDeviceInfodiv(@RequestParam(value = "ipAddress") String ipAddress,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDeviceModelMap();
		modelMap.addAttribute("addDevice", addDeviceDAO.getDeviceInfoByIp(ipAddress));
		return new ModelAndView("showdeviceinfodiv", modelMap);
	}

	@RequestMapping(value = "changeDeviceTimeDate.htm", method = RequestMethod.POST)
	public void changeDeviceTimeDate(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		PrintWriter out = null;
		String msgIP = "0";
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";

		try {
			out = response.getWriter();
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}
					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.SetSystemTime(enterIP);
					out.write(testConnectionNativeString);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}

		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "restartDevice.htm", method = RequestMethod.POST)
	public void restartDevice(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {

		String msgIP = "0";
		PrintWriter out = null;
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("validIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.RestartDevice(enterIP);
					out.write(testConnectionNativeString);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* return testConnectionNativeString; */
	}

	@RequestMapping(value = "clearLogsF.htm", method = RequestMethod.POST)
	public void clearLogsF(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {

		String msgIP = "0";
		PrintWriter out = null;
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("validIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.ClearLogs(enterIP);
					out.write(testConnectionNativeString);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* return testConnectionNativeString; */
	}

	@RequestMapping(value = "showUsersDiv.htm", method = RequestMethod.GET)
	public ModelAndView showUsersDiv(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		Properties properties = new Properties();
		InputStream inputStream = AdminController.class.getClassLoader().getResourceAsStream("database.properties");
		String databaseUserName = "";
		String dbEncryptedPassword = "";
		String dbdencryptedPassword = "";
		try {
			properties.load(inputStream);
			databaseUserName = properties.getProperty("database.username");
			dbEncryptedPassword = properties.getProperty("database.password");
			dbdencryptedPassword = encryptPassword.decrypt(dbEncryptedPassword);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ModelMap modelMap = new ModelMap();
		String msgIP = "0";
		PrintWriter out = null;
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("validIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}
					}
				}

				if (serialNoFlag) {
					userInfoDAO.deleteAllUserInfo();
					int recordsInserted = iAttApp.DownloadUserTmp(enterIP,
							"Server=localhost;port=3306;Database=distna;Uid=" + databaseUserName + ";Pwd="
									+ dbdencryptedPassword);
					if (recordsInserted == 0) {
						recordsInserted = iAttApp.downloadUserTmpVer9(enterIP,
								"Server=localhost;port=3306;Database=distna;Uid=" + databaseUserName + ";Pwd="
										+ dbdencryptedPassword);
					}
					if (recordsInserted != 0) {
						List<UserInfo> userInfoList = userInfoDAO.getUserInfo();
						modelMap.addAttribute("enterIP", enterIP);
						modelMap.addAttribute("userInfoList", userInfoList);
						modelMap.addAttribute("userInfoListSize", userInfoList.size());
						modelMap.addAttribute("connectionStatus", 0);
						modelMap.addAttribute("deviceSerial", 0);
					} else {
						modelMap.addAttribute("connectionStatus", "Users Not Present");
						modelMap.addAttribute("deviceSerial", 0);
					}
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						modelMap.addAttribute("connectionStatus", 0);
						modelMap.addAttribute("deviceSerial", "Device Serial Number Not Valid.");
					}
				}
			} else {
				modelMap.addAttribute("deviceSerial", 0);
				modelMap.addAttribute("connectionStatus", testConnectionNativeString);
			}
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("showusersdiv", modelMap);
	}

	@RequestMapping(value = "deleteUserFromDiv.htm", method = RequestMethod.POST)
	public ModelAndView deleteUserFromDiv(@RequestParam(value = "enterIP") String enterIP,
			@RequestParam(value = "id") String enrollmentNo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		/* List<UserInfo> userInfoList= userInfoDAO.getUserInfo(id); */
		String testConnectionNativeString = "0";
		List<UserInfo> userInfoList = userInfoDAO.getUserInfo(enrollmentNo);

		try {
			// iAttApp = new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				if (userInfoList.size() != 0) {
					testConnectionNativeString = iAttApp.BtnDeleteUser(enrollmentNo, enterIP,
							userInfoList.get(0).getFpIndex() + "");
					if (testConnectionNativeString.equals(enterIP)) {
						userInfoDAO.deleteUserInfo(enrollmentNo);
					}
				}
			}
		} catch (COMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView(new RedirectView("showUsersDiv.htm?validIp=" + testConnectionNativeString));
	}

	@RequestMapping(value = "deleteUsers.htm", method = RequestMethod.POST)
	public void deleteUsers(@RequestParam(value = "validIp") String enterIP, @RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		String msgIP = "0";
		PrintWriter out = null;
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("validIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.deleteUser(enterIP);
					/* testConnectionNativeString=iAttApp.BtnDeleteUser("1", enterIP); */
					out.write(testConnectionNativeString);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* return testConnectionNativeString; */
	}

	@RequestMapping(value = "changeDeviceIp.htm", method = RequestMethod.POST)
	public ModelAndView changeDeviceIp(@RequestParam(value = "ipAddress") String oldIp, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDeviceModelMap();
		modelMap.addAttribute("checkOnload", 0);
		modelMap.addAttribute("oldIp", oldIp);
		return new ModelAndView("changedeviceip", modelMap);
	}

	@RequestMapping(value = "deviceChangeIp.htm", method = RequestMethod.POST)
	public ModelAndView deviceChangeIp(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDeviceModelMap();
		PrintWriter out = null;
		String msgIP = "0";
		String testConnectionNativeString = "0";
		String enterIP = "";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("ipAddress");
			String oldIp = request.getParameter("oldIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			if (iAttApp.ViewStatus(oldIp).equals(oldIp)) {

				deviceIpAndSerialNo = iAttApp.GetSerial(oldIp);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(oldIp)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}
					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.SetNewIP(oldIp, enterIP);
				} else {

					if (deviceIpAndSerialNoArray[0].equals(oldIp)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			}

		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (testConnectionNativeString.equals(enterIP)) {
			modelMap.addAttribute("checkConnectionStatus", "successfull");
			modelMap.addAttribute("ipAddress", testConnectionNativeString);
		} else {
			modelMap.addAttribute("checkConnectionStatus", "failed");
		}
		modelMap.addAttribute("checkOnload", 1);
		return new ModelAndView("changedeviceip", modelMap);

	}

	@RequestMapping(value = "clearAdminPrivilege.htm", method = RequestMethod.POST)
	public void clearAdminPrivilege(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		PrintWriter out = null;
		String msgIP = "0";
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.ClearAdminPrivilege(enterIP);
					out.write(testConnectionNativeString);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "downloadToDatabase.htm", method = RequestMethod.POST)
	public void downloadToDatabase(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		Properties properties = new Properties();
		InputStream inputStream = AdminController.class.getClassLoader().getResourceAsStream("database.properties");
		String databaseUserName = "";
		String dbEncryptedPassword = "";
		String dbdencryptedPassword = "";
		try {
			properties.load(inputStream);
			databaseUserName = properties.getProperty("database.username");
			dbEncryptedPassword = properties.getProperty("database.password");
			dbdencryptedPassword = encryptPassword.decrypt(dbEncryptedPassword);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrintWriter out = null;
		String msgIP = "0";
		String testConnectionNativeString = "0";
		String deviceIpAndSerialNo = "0";
		try {
			out = response.getWriter();
			/*
			 * Server=localhost;port=3306;Database=distna;Uid=root;Pwd=admin Server =
			 * localhost; port=3306; Database = DISTNA; User Id = root; Password = admin
			 */
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			int recordsInserted = 0;
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			if (testConnectionNativeString.equals(enterIP)) {
				/*
				 * IDownloadUsers iDownloadUsers=new IDownloadUsers("Jawin.DownloadUsersToDB");
				 * iDownloadUsers.downloadToDB(enterIP,
				 * "Server=localhost;port=3306;Database=distna;Uid=root;Pwd=admin");
				 */

				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					recordsInserted = iAttApp.DownloadUserTmp(enterIP, "Server=localhost;port=3306;Database=distna;Uid="
							+ databaseUserName + ";Pwd=" + dbdencryptedPassword);
					if (recordsInserted != 0) {
						out.write(testConnectionNativeString);
					} else {
						out.write("Records Not Inserted");
					}
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			} else {
				out.write(testConnectionNativeString);
			}

		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "InitilizationDevice.htm", method = RequestMethod.POST)
	public void InitializeDevice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			AddDevice addDevice) {
		System.out.println("InitializeDevice controller");
		try {

			iAttApp = new IAttApp("Jawin.AttApp");
			iAttApp.InitMethod();
		} catch (COMException ex) {
			ex.printStackTrace();

		}
	}

	@RequestMapping(value = "viewDeviceStatus.htm", method = RequestMethod.POST)
	public ModelAndView viewDeviceStatus(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		System.out.println("viewDeviceStatus controller");
		ModelMap modelMap = getDeviceModelMap();
		PrintWriter out = null;
		String msgIP = "0";
		String testConnectionNativeString = "0";
		try {
			out = response.getWriter();
			enterIP = request.getParameter("validIp");
			// IAttApp iAttApp=new IAttApp("Jawin.AttApp");
			testConnectionNativeString = iAttApp.ViewStatus(enterIP);
			String deviceIpAndSerialNo = "0";
			System.out.println("macid + ip" + testConnectionNativeString);
			if (testConnectionNativeString.equals(enterIP)) {
				deviceIpAndSerialNo = iAttApp.GetSerial(enterIP);
				String[] deviceIpAndSerialNoArray = deviceIpAndSerialNo.split("~");
				List<AllowedDevices> allowedDevicesList = allowedDevicesDAO.getAllowedDevices();
				boolean serialNoFlag = false;
				if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
					for (AllowedDevices allowedDevices : allowedDevicesList) {
						if (allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
							serialNoFlag = true;
						}

					}
				} else {
					out.write(deviceIpAndSerialNo);
				}
				if (serialNoFlag) {
					testConnectionNativeString = iAttApp.GetDeviceStatus(enterIP);
					String[] statusNativeString = testConnectionNativeString.split(",");
					List<String> statusNativeStringValues = new ArrayList<String>();
					for (String statusNativeStringObj : statusNativeString) {
						String[] splitByEqual = statusNativeStringObj.split("=");
						statusNativeStringValues.add(splitByEqual[1]);
					}
					modelMap.addAttribute("statusNativeStringValues", statusNativeStringValues);
				} else {
					if (deviceIpAndSerialNoArray[0].equals(enterIP)) {
						out.write("Device Serial Number Not Valid.");
					}
				}
			}
			modelMap.addAttribute("testConnectionNativeString", testConnectionNativeString);
		} catch (COMException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("deviceinfodiv", modelMap);
	}

	public ModelMap getOfficialTourModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeesList", employeesList);
		return modelMap;
	}

	@RequestMapping(value = "showOfficialTour.htm", method = RequestMethod.GET)
	public ModelAndView showOfficialTour(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			OfficialTours officialTours, BindingResult bindingResult) {
		ModelMap modelMap = getOfficialTourModelMap();
		return new ModelAndView("showOfficialTour", modelMap);
	}

	@RequestMapping(value = "saveOfficialTours.htm", method = RequestMethod.POST)
	public ModelAndView saveOfficialTours(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			OfficialTours officialTours, BindingResult bindingResult) {
		ModelMap modelMap = getOfficialTourModelMap();
		officialTourValidator.validate(officialTours, bindingResult);
		if (bindingResult.hasErrors()) {

			return new ModelAndView("showOfficialTour", modelMap);

		} else {

			String dateFormat = "dd-MM-yyyy";
			String dateNow = DateTime.CurrentDate(dateFormat);
			String loginUser = (String) session.getAttribute("loginUser");
			officialTours.setCreated_by(loginUser);
			officialTours.setCreated_on(dateNow);
			officialToursDAO.saveOfficialTour(officialTours);
			return new ModelAndView(new RedirectView("showOfficialTour.htm"));
		}
	}

	@RequestMapping(value = "viewUpdateOfficialTour.htm", method = RequestMethod.GET)
	public ModelAndView viewUpdateOfficialTour(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, OfficialTours officialTours) {
		ModelMap modelMap = getOfficialTourModelMap();
		List<OfficialTours> officialToursList = officialToursDAO.getOfficialToursList();
		modelMap.addAttribute("officialToursList", officialToursList);
		modelMap.addAttribute("officialToursListSize", officialToursList.size());
		return new ModelAndView("viewUpdateOfficialTour", modelMap);

	}

	@RequestMapping(value = "deleteOfficialTour.htm", method = RequestMethod.POST)
	public ModelAndView deleteOfficialTour(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, OfficialTours officialTours) {
		officialToursDAO.deleteOfficialTour(id);
		return new ModelAndView(new RedirectView("viewUpdateOfficialTour.htm"));

	}

	@RequestMapping(value = "updateOfficialTour.htm", method = RequestMethod.POST)
	public ModelAndView updateOfficialTour(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getOfficialTourModelMap();
		List<OfficialTours> officialToursList = officialToursDAO.getOfficialToursList(id);
		for (OfficialTours officialToursObj : officialToursList) {
			modelMap.addAttribute("officialTours", officialToursObj);
		}
		return new ModelAndView("updateOfficialTour", modelMap);

	}

	@RequestMapping(value = "saveOrUpdateOfficialTour.htm", method = RequestMethod.GET)
	public ModelAndView saveOrUpdateOfficialTour(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, OfficialTours officialTours, BindingResult bindingResult) {
		ModelMap modelMap = getOfficialTourModelMap();
		officialTourValidator.validate(officialTours, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateOfficialTour", modelMap);
		} else {
			officialToursDAO.saveOrUpdateOfficialTour(officialTours);
			return new ModelAndView(new RedirectView("viewUpdateOfficialTour.htm"));
		}
	}

	public ModelMap getShiftMasterModelMap() {
		ModelMap modelMap = new ModelMap();
		return modelMap;
	}

	@RequestMapping(value = "showShiftMaster.htm", method = RequestMethod.GET)
	public ModelAndView showShiftMaster(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ShiftMaster shiftMaster) {
		ModelMap modelMap = getShiftMasterModelMap();
		return new ModelAndView("showShiftMaster", modelMap);
	}

	@RequestMapping(value = "saveShiftMaster.htm", method = RequestMethod.GET)
	public ModelAndView saveShiftMaster(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ShiftMaster shiftMaster, BindingResult bindingResult) {
		ModelMap modelMap = getShiftMasterModelMap();
		shiftMasterValidator.validate(shiftMaster, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("showShiftMaster", modelMap);
		}

		else {
			String loginUser = (String) session.getAttribute("loginUser");
			List<Employee> employeesList = employeeDAO.getEmployeeList(loginUser);
			java.net.InetAddress i;
			try {
				i = java.net.InetAddress.getLocalHost();
				shiftMaster.setFrom_ip(i.getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Employee employee : employeesList) {
				shiftMaster.setRecord_Created_By(employee.getEmployeeId());
			}
			shiftMaster.setActive(true);
			shiftMasterDAO.saveShiftMaster(shiftMaster);
			return new ModelAndView(new RedirectView("showShiftMaster.htm"));
		}
	}

	@RequestMapping(value = "viewUpdateShiftMaster.htm", method = RequestMethod.GET)
	public ModelAndView viewUpdateShiftMaster(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getShiftMasterModelMap();
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		modelMap.addAttribute("shiftMasterList", shiftMasterList);
		modelMap.addAttribute("shiftMasterListSize", shiftMasterList.size());
		return new ModelAndView("viewUpdateShiftMaster", modelMap);
	}

	@RequestMapping(value = "deleteShiftMaster.htm", method = RequestMethod.POST)
	public ModelAndView deleteShiftMaster(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ShiftMaster shiftMaster) {
		shiftMasterDAO.deleteShiftMaster(id);
		return new ModelAndView(new RedirectView("viewUpdateShiftMaster.htm"));
	}

	@RequestMapping(value = "updateShiftMaster.htm", method = RequestMethod.POST)
	public ModelAndView updateShiftMaster(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getShiftMasterModelMap();
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList(id);
		for (ShiftMaster shiftMasterObj : shiftMasterList) {
			modelMap.addAttribute("shiftMaster", shiftMasterObj);
		}
		return new ModelAndView("updateShiftMaster", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateShiftMaster.htm", method = RequestMethod.GET)
	public ModelAndView saveOrUpdateShiftMaster(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftMaster shiftMaster, BindingResult bindingResult) {
		ModelMap modelMap = getShiftMasterModelMap();
		shiftMasterValidator.validate(shiftMaster, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateShiftMaster", modelMap);

		} else {
			shiftMasterDAO.saveOrUpdateShiftMaster(shiftMaster);
			return new ModelAndView(new RedirectView("viewUpdateShiftMaster.htm"));
		}
	}

	@RequestMapping(value = "createShiftDefinition.htm", method = RequestMethod.GET)
	public ModelAndView createShiftDefinition(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftDefinition shiftDefinition) {
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		List<Location> locationList = locationDAO.getLocation();
		List<CalCategory> categoryList = shiftDefinitionDAO.getCategoryList();
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		modelMap.addAttribute("shiftMasterList", shiftMasterList);
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("categoryList", categoryList);
		return new ModelAndView("createShiftDefinition", modelMap);
	}

	@RequestMapping(value = "	saveShiftDefinition.htm", method = RequestMethod.POST)
	public ModelAndView saveShiftDefinition(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftDefinition shiftDefinition, BindingResult bindingResult) {

		shiftDefinitionValidator.validate(shiftDefinition, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("createShiftDefinition", modelMap);
		} else {
			shiftDefinitionDAO.saveShiftDefinitions(shiftDefinition);
			return new ModelAndView(new RedirectView("createShiftDefinition.htm"));
		}

	}

	public ModelMap getShowShiftDefinitionUpdateModelMap(int shiftId) {
		ModelMap modelMap = new ModelMap();
		List<ShiftDefinition> shiftDefinitionList = shiftDefinitionDAO.getShiftDefinitionsByShiftId(shiftId);
		if (shiftDefinitionList.size() > 0) {
			modelMap.addAttribute("shiftDefinition", shiftDefinitionList.get(0));
		} else {
			ShiftDefinition shiftDefinition = new ShiftDefinition();
			modelMap.addAttribute("shiftDefinition", shiftDefinition);
		}
		List<ShiftMaster> shiftMaster = shiftMasterDAO.getShiftMasterList(shiftId);
		modelMap.addAttribute("shiftMasterObj", shiftMaster.get(0));
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		modelMap.addAttribute("shiftMasterList", shiftMasterList);
		List<Location> locationList = locationDAO.getLocation();
		List<CalCategory> categoryList = shiftDefinitionDAO.getCategoryList();
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("locationList", locationList);
		modelMap.addAttribute("categoryList", categoryList);
		modelMap.addAttribute("calTimesList", calTimesList);
		return modelMap;
	}

	@RequestMapping(value = "showShiftDefinitionUpdate.htm", method = RequestMethod.POST)
	public ModelAndView showShiftDefinitionUpdate(@RequestParam(value = "id") int shiftId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("showshiftdefinitionupdate", getShowShiftDefinitionUpdateModelMap(shiftId));
	}

	@RequestMapping(value = "saveOrUpdateShiftDefinition.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateShiftDefinition(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftDefinition shiftDefinition, BindingResult bindingResult) {
		shiftDefinitionValidator.validate(shiftDefinition, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getShowShiftDefinitionUpdateModelMap(shiftDefinition.getShiftid());
			return new ModelAndView("showshiftdefinitionupdate", modelMap);
		} else {
			shiftDefinitionDAO.saveShiftDefinitions(shiftDefinition);
			System.out.println("shift updated");
			return new ModelAndView(new RedirectView("viewUpdateShiftMaster.htm"));
		}
	}

	public ModelMap shiftAllocationSingleEmployeeModelMap(int id, String idType) {
		ModelMap map = new ModelMap();
		if (idType.equals("shift")) {
			List<ShiftMaster> shiftMaster = shiftMasterDAO.getShiftMasterList(id);
			map.addAttribute("shiftMasterObj", shiftMaster.get(0));
			map.addAttribute("idType", "shift");

		} else if (idType.equals("employee")) {
			List<Employee> employeeList = employeeDAO.getEmployeeList(id);
			List<Departments> selectedEmployeesDepartment = departmentDAO
					.getDepartmentById(employeeList.get(0).getDepartmentId());
			if (employeeList.size() != 0)
				map.addAttribute("employeeObj", employeeList.get(0));
			if (selectedEmployeesDepartment.size() != 0)
				map.addAttribute("selectedEmployeesDepartment", selectedEmployeesDepartment.get(0));
			map.addAttribute("idType", "employee");
		}

		List<Departments> departmentsList = departmentDAO.getDepartment();
		map.addAttribute("departmentsList", departmentsList);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		map.addAttribute("employeeList", employeeList);
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		map.addAttribute("shiftMasterList", shiftMasterList);
		List<Company> companyList = companyDAO.getCompanies();
		map.addAttribute("companyList", companyList);
		List<Location> locationsList = locationDAO.getLocation();
		map.addAttribute("locationsList", locationsList);
		return map;
	}

	@RequestMapping(value = "shiftAllocationSingleEmployee.htm", method = RequestMethod.POST)
	public ModelAndView shiftAllocationSingleEmployee(@RequestParam(value = "id") int id,
			@RequestParam(value = "idType") String idType, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftAllocation shiftAllocation) {
		return new ModelAndView("shiftallocationsingleemployee", shiftAllocationSingleEmployeeModelMap(id, idType));
	}

	@RequestMapping(value = "allocateShiftSingleEmployee.htm", method = RequestMethod.GET)
	public ModelAndView allocateShiftSingleEmployee(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ShiftAllocation shiftAllocation) {

		List<Departments> departmentsList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentsList", departmentsList);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Company> companyList = companyDAO.getCompanies();
		modelMap.addAttribute("companyList", companyList);

		List<Location> locationsList = locationDAO.getLocation();
		modelMap.addAttribute("locationsList", locationsList);

		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		modelMap.addAttribute("shiftMasterList", shiftMasterList);
		modelMap.addAttribute("idType", "singleEmployeeAllocation");
		return new ModelAndView("shiftallocationsingleemployee", modelMap);
	}

	@RequestMapping(value = "saveShiftAllocation.htm", method = RequestMethod.POST)
	public ModelAndView saveShiftAllocation(@RequestParam(value = "idType") String idType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ShiftAllocation shiftAllocation,
			BindingResult bindingResult) {

		if (null != request.getParameter("deptEmployee") && shiftAllocation.getEmployee_id() == 0)
			shiftAllocation.setEmployee_id(Integer.parseInt(request.getParameter("deptEmployee")));
		shiftAllocationValidator.validate(shiftAllocation, bindingResult);

		if (bindingResult.hasErrors()) {
			if (idType.equals("singleEmployeeAllocation")) {

				return new ModelAndView("shiftallocationsingleemployee", modelMap);

			} else if (idType.equals("employee")) {
				int empId = 0;
				if (null != request.getParameter("employee_id")) {
					empId = Integer.parseInt(request.getParameter("employee_id"));
				}
				return new ModelAndView("shiftallocationsingleemployee",
						shiftAllocationSingleEmployeeModelMap(empId, idType));
			} else {
				int shiftId = 0;
				if (null != request.getParameter("shiftid")) {
					shiftId = Integer.parseInt(request.getParameter("shiftid"));
				}
				return new ModelAndView("shiftallocationsingleemployee",
						shiftAllocationSingleEmployeeModelMap(shiftId, idType));
			}

		} else {
			List<ShiftAllocation> shiftAllocationList = shiftAllocationDAO
					.getShiftAllocatedEmployeeList(shiftAllocation.getEmployee_id());
			if (shiftAllocationList.size() != 0) {
				shiftAllocation.setId(shiftAllocationList.get(0).getId());
			}
			shiftAllocationDAO.saveShiftDefinitions(shiftAllocation);

			if (idType.equals("singleEmployeeAllocation")) {
				return new ModelAndView(new RedirectView("allocateShiftSingleEmployee.htm"));
			} else if (idType.equals("employee")) {
				return new ModelAndView(new RedirectView("viewEmployeeCalendar.htm"));
			} else {
				return new ModelAndView(new RedirectView("viewUpdateShiftMaster.htm"));
			}
		}
	}
//=============================================end allocation===============================================================================================

	public ModelMap getEmployeeCalendarModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Departments> departmentsList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentsList", departmentsList);
		List<ShiftAllocation> allocatedShiftList = shiftAllocationDAO.getShiftAllocatedList();
		modelMap.addAttribute("allocatedShiftList", allocatedShiftList);
		List<ShiftMaster> shiftMasterList = shiftMasterDAO.getShiftMasterList();
		modelMap.addAttribute("shiftMasterList", shiftMasterList);
		return modelMap;
	}

	@RequestMapping(value = "viewEmployeeCalendar.htm", method = RequestMethod.GET)
	public ModelAndView viewEmployeeCalendar(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getEmployeeCalendarModelMap();
		return new ModelAndView("viewemployeecalendar", modelMap);
	}

	@RequestMapping(value = "showMonthCalender.htm", method = RequestMethod.GET)
	public ModelAndView showMonthCalender(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		return new ModelAndView("showmonthcalender", modelMap);
	}

	@RequestMapping(value = "showMonthCalenderDiv.htm", method = RequestMethod.POST)
	public ModelAndView showMonthCalenderDiv(@RequestParam(value = "employeeId") int employeeId,
			@RequestParam(value = "iYear") int iYear, @RequestParam(value = "iMonth") int iMonth,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		Calendar ca = new GregorianCalendar();
		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);

		if (iYear == 0) {
			iYear = iTYear;
			iMonth = iTMonth;
		}

		List<ShiftAllocation> shiftAllocatedEmployees = shiftAllocationDAO.getShiftAllocatedEmployeeList(employeeId);
		if (shiftAllocatedEmployees.size() == 0) {
			modelMap.addAttribute("errorMsg", "No shift is allocated to the employee.");
			return new ModelAndView("showmonthcalenderdiverrormsg", modelMap);
		}

		else {
			List<ShiftDefinition> allShiftsDefined = shiftDefinitionDAO.getShiftDefinitions();
			List<ShiftDefinition> shiftDefinitionListByShiftId = shiftDefinitionDAO
					.getShiftDefinitionsByShiftId(shiftAllocatedEmployees.get(0).getShiftid());
			if (shiftDefinitionListByShiftId.size() > 0) {
				ShiftDefinition allocatedShiftsDefinition = shiftDefinitionDAO
						.getShiftDefinitionObjByShiftId(shiftAllocatedEmployees.get(0).getShiftid());
				ShiftMaster shiftMaster = shiftMasterDAO.getShiftMasterList(allocatedShiftsDefinition.getShiftid())
						.get(0);
				modelMap.addAttribute("shiftAllocatedEmployees", shiftAllocatedEmployees);
				modelMap.addAttribute("allShiftsDefined", allShiftsDefined);
				modelMap.addAttribute("allocatedShiftsDefinition", allocatedShiftsDefinition);
				modelMap.addAttribute("shiftName", shiftMaster.getShiftname());
				modelMap.addAttribute("iYear", iYear);
				modelMap.addAttribute("iMonth", iMonth);
				modelMap.addAttribute("employeeId", employeeId);
				return new ModelAndView("showmonthcalenderdiv", modelMap);
			} else {
				modelMap.addAttribute("errorMsg", "Please Define the Shift Allocated to the Employee");
				return new ModelAndView("showmonthcalenderdiverrormsg", modelMap);
			}
		}

	}

	@RequestMapping(value = "showMonthCalenderInternalDiv.htm", method = RequestMethod.POST)
	public ModelAndView showMonthCalenderInternalDiv(@RequestParam(value = "employeeId") int employeeId,
			@RequestParam(value = "iYear") int iYear, @RequestParam(value = "iMonth") int iMonth,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		Calendar ca = new GregorianCalendar();
		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);

		if (iYear == 0) {
			iYear = iTYear;
			iMonth = iTMonth;
		}

		List<ShiftAllocation> shiftAllocatedEmployees = shiftAllocationDAO.getShiftAllocatedEmployeeList(employeeId);
		List<ShiftDefinition> allShiftsDefined = shiftDefinitionDAO.getShiftDefinitions();
		ShiftDefinition allocatedShiftsDefinition = shiftDefinitionDAO
				.getShiftDefinitionObjByShiftId(shiftAllocatedEmployees.get(0).getShiftid());

		modelMap.addAttribute("shiftAllocatedEmployees", shiftAllocatedEmployees);
		modelMap.addAttribute("allShiftsDefined", allShiftsDefined);
		modelMap.addAttribute("allocatedShiftsDefinition", allocatedShiftsDefinition);
		List<Holidays> monthlyHolidayList = holidaysDAO.getHolidays();
		modelMap.addAttribute("monthlyHolidayList", monthlyHolidayList);
		modelMap.addAttribute("iYear", iYear);
		modelMap.addAttribute("iMonth", iMonth);
		modelMap.addAttribute("employeeId", employeeId);
		return new ModelAndView("showmonthcalenderinternaldiv", modelMap);
	}

	@RequestMapping(value = "showMonthlyCalendar.htm", method = RequestMethod.GET)
	public ModelAndView showMonthlyCalendar(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		List<Integer> listOfUniqueEmployees = shiftAllocationDAO.getShiftAllocatedEmployeeList();
		List<Employee> listOfEmployeeList = new ArrayList<Employee>();
		for (Integer listOfUniqueEmployeesObj : listOfUniqueEmployees) {
			listOfEmployeeList.add(employeeDAO.getEmployeeList(listOfUniqueEmployeesObj).get(0));
		}
		modelMap.addAttribute("listOfEmployeeList", listOfEmployeeList);
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		List<Holidays> monthlyHolidayList = holidaysDAO.getHolidays();
		modelMap.addAttribute("monthlyHolidayList", monthlyHolidayList);
		return new ModelAndView("showmonthlycalendar", modelMap);
	}

	@RequestMapping(value = "displaydepartmentwiseemployeelistshift.htm", method = RequestMethod.POST)
	public ModelAndView displayDepartmentWiseEmployeeListShift(@RequestParam("departmentId") int departmentId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDepartmentWiseReportModelMap();

		List<Employee> employeeListDept = employeeDAO.getEmployeeListByDepartment(departmentId);
		modelMap.addAttribute("employeeListDept", employeeListDept);
		modelMap.addAttribute("departmentId", departmentId);
		return new ModelAndView("shiftcalendaremployees", modelMap);
	}

	@RequestMapping(value = "showaddholiday.htm", method = RequestMethod.GET)
	public ModelAndView showAddHoliday(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Holidays holidays) {
		return new ModelAndView("addholiday");
	}

	@RequestMapping(value = "addholiday.htm", method = RequestMethod.POST)
	public ModelAndView addHoliday(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Holidays holidays, BindingResult bindingResult) {
		HolidaysValidator holidaysValidator = new HolidaysValidator();
		holidaysValidator.validate(holidays, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addholiday", modelMap);
		}

		else {
			holidaysDAO.saveHoliday(holidays);
			return new ModelAndView(new RedirectView("showaddholiday.htm"));
		}
	}

	@RequestMapping(value = "showviewholiday.htm", method = RequestMethod.GET)
	public ModelAndView showViewHoliday(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Holidays holidays) {
		ModelMap modelMap = new ModelMap();
		List<Holidays> holidayList = holidaysDAO.getHolidays();
		modelMap.addAttribute("holidayList", holidayList);
		modelMap.addAttribute("holidayListSize", holidayList.size());
		return new ModelAndView("viewholiday", modelMap);
	}

	@RequestMapping(value = "deleteholiday.htm", method = RequestMethod.POST)
	public ModelAndView deleteHoliday(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Holidays holidays) {
		holidaysDAO.deleteHoliday(id);
		return new ModelAndView(new RedirectView("showviewholiday.htm"));
	}

	@RequestMapping(value = "showupdateholiday.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateHoliday(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Holidays holidays) {
		holidays = holidaysDAO.getHoliday(id);
		modelMap.addAttribute("holidays", holidays);
		return new ModelAndView("updateholiday", modelMap);
	}

	@RequestMapping(value = "updateholiday.htm", method = RequestMethod.POST)
	public ModelAndView updateHoliday(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Holidays holidays, BindingResult bindingResult) {
		HolidaysValidator holidaysValidator = new HolidaysValidator();
		holidaysValidator.validate(holidays, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateholiday", modelMap);
		}

		else {
			holidaysDAO.updateHoliday(holidays);
			return new ModelAndView(new RedirectView("showviewholiday.htm"));
		}
	}

	public ModelMap getExperienceModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showaddexperience.htm", method = RequestMethod.GET)
	public ModelAndView showAddExperience(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeExperiences employeeExperiences, BindingResult bindingResult) {
		ModelMap modelMap = getExperienceModelMap();
		return new ModelAndView("addexperience", modelMap);
	}

	@RequestMapping(value = "addexperience.htm", method = RequestMethod.POST)
	public ModelAndView addExperience(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeExperiences employeeExperiences, BindingResult bindingResult) {

		ModelMap modelMap = getExperienceModelMap();

		if (null != request.getParameter("state")) {
			int employeeState = Integer.parseInt(request.getParameter("state"));
			employeeExperiences.setState(employeeState);
		}
		if (null != request.getParameter("city")) {
			int employeeCity = Integer.parseInt(request.getParameter("city"));
			employeeExperiences.setCity(employeeCity);
		}
		EmployeeExperiencesValidator employeeExperiencesValidator = new EmployeeExperiencesValidator();
		employeeExperiencesValidator.validate(employeeExperiences, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addexperience", modelMap);
		} else {
			int experienceId = employeeExperiencesDAO.saveAndGetId(employeeExperiences);
			String extension = "";
			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}
			String filename = employeeExperiences.getEmployeeId() + "_" + experienceId + extension;

			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "Experiences", filename);
					employeeExperiences.setAttachment("Experiences/" + filename);
					employeeExperiences.setId(experienceId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeExperiencesDAO.saveExperience(employeeExperiences);
			return new ModelAndView(new RedirectView("showaddexperience.htm"));
		}
	}

	@RequestMapping(value = "showviewexperience.htm", method = RequestMethod.GET)
	public ModelAndView showViewExperience(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, EmployeeExperiences employeeExperiences) {
		ModelMap modelMap = getExperienceModelMap();
		List<EmployeeExperiences> experienceList = employeeExperiencesDAO.getExperienceList();
		modelMap.addAttribute("experienceList", experienceList);
		modelMap.addAttribute("experienceListSize", experienceList.size());
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		return new ModelAndView("viewexperience", modelMap);
	}

	@RequestMapping(value = "deleteExperience.htm", method = RequestMethod.POST)
	public ModelAndView deleteExperience(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeExperiences employeeExperiences) {
		employeeExperiencesDAO.deleteExperience(id);
		return new ModelAndView(new RedirectView("showviewexperience.htm"));
	}

	@RequestMapping(value = "showupdateExperience.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateExperience(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeExperiences employeeExperiences) {
		employeeExperiences = employeeExperiencesDAO.getExterience(id);
		int employeeCountry = employeeExperiences.getCountry();
		int employeeState = employeeExperiences.getState();
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("employeeState", statesDAO.getStatesById(employeeCountry));
		modelMap.addAttribute("employeeCity", citiesDAO.getCitiesById(employeeState));
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		employeeExperiences = employeeExperiencesDAO.getExterience(id);
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		modelMap.addAttribute("employeeExperiences", employeeExperiences);
		return new ModelAndView("updateexperience", modelMap);
	}

	@RequestMapping(value = "updateexperience.htm", method = RequestMethod.POST)
	public ModelAndView updateExperience(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeExperiences employeeExperiences, BindingResult bindingResult) {
		if (null != request.getParameter("state")) {
			int employeeState = Integer.parseInt(request.getParameter("state"));
			if (employeeState != 0) {
				employeeExperiences.setState(employeeState);
			}
		}
		if (null != request.getParameter("city")) {
			int employeeCity = Integer.parseInt(request.getParameter("city"));
			if (employeeCity != 0) {
				employeeExperiences.setCity(employeeCity);
			}
		}

		EmployeeExperiencesValidator employeeExperiencesValidator = new EmployeeExperiencesValidator();
		employeeExperiencesValidator.validate(employeeExperiences, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateexperience", modelMap);
		}

		else

		{
			int experienceId = employeeExperiences.getId();
			String extension = "";
			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}
			String filename = employeeExperiences.getEmployeeId() + "_" + experienceId + extension;
			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "Experiences", filename + extension);
					employeeExperiences.setAttachment("Experiences/" + filename + extension);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeExperiences.setId(experienceId);
			employeeExperiencesDAO.saveExperience(employeeExperiences);
			return new ModelAndView(new RedirectView("showviewexperience.htm"));

		}
	}

	// ############################################### SKILL
	// start##################################################### //

	public ModelMap getEmployeeSkillsModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showaddskills.htm", method = RequestMethod.GET)
	public ModelAndView showAddSkills(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeSkills employeeSkills, BindingResult bindingResult) {
		ModelMap modelMap = getEmployeeSkillsModelMap();
		modelMap.addAttribute("employeeSkills", employeeSkills);
		return new ModelAndView("addskills", modelMap);
	}

	@RequestMapping(value = "addskills.htm", method = RequestMethod.POST)
	public ModelAndView addSkills(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeSkills employeeSkills, BindingResult bindingResult) {
		ModelMap modelMap = getEmployeeSkillsModelMap();

		employeeSkillsValidator.validate(employeeSkills, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addskills", modelMap);
		}

		else {
			int skillId = employeeSkillsDAO.saveAndGetId(employeeSkills);
			String extension = "";
			String filename = employeeSkills.getEmployeeId() + "_" + skillId;

			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}

			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "EmployeeSkills", filename + extension);
					employeeSkills.setAttachment("EmployeeSkills/" + filename + extension);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeSkills.setId(skillId);
			employeeSkillsDAO.saveEmployeeSkills(employeeSkills);
			return new ModelAndView(new RedirectView("showaddskills.htm"));

		}
	}

	@RequestMapping(value = "showviewskills.htm", method = RequestMethod.GET)
	public ModelAndView showViewSkills(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeSkills employeeSkills) {
		ModelMap modelMap = getEmployeeSkillsModelMap();
		List<EmployeeSkills> skillList = employeeSkillsDAO.getEmployeeSkillsList();
		modelMap.addAttribute("skillList", skillList);
		modelMap.addAttribute("skillListSize", skillList.size());
		return new ModelAndView("viewemployeeskills", modelMap);
	}

	@RequestMapping(value = "deleteemployeeskills.htm", method = RequestMethod.POST)
	public ModelAndView deleteEmployeeSkills(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeSkills employeeSkills) {
		employeeSkillsDAO.deleteEmployeeSkills(id);
		return new ModelAndView(new RedirectView("showviewskills.htm"));
	}

	@RequestMapping(value = "showupdateemployeeskills.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateEmployeeSkills(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeSkills employeeSkills) {
		ModelMap modelMap = getEmployeeSkillsModelMap();
		employeeSkills = employeeSkillsDAO.getEmployeeSkills(id);
		modelMap.addAttribute("employeeSkills", employeeSkills);
		return new ModelAndView("updateemployeeskills", modelMap);
	}

	@RequestMapping(value = "updateskills.htm", method = RequestMethod.POST)
	public ModelAndView updateEmployeeSkills(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeSkills employeeSkills, BindingResult bindingResult) {
		ModelMap modelMap = getEmployeeSkillsModelMap();

		employeeSkillsValidator.validate(employeeSkills, bindingResult);

		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("employeeSkills", employeeSkillsDAO.getEmployeeSkills(employeeSkills.getId()));
			return new ModelAndView("updateemployeeskills", modelMap);
		}

		else {
			int skillId = employeeSkills.getId();
			String extension = "";
			String filename = employeeSkills.getEmployeeId() + "_" + skillId;

			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}

			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "EmployeeSkills", filename + extension);
					employeeSkills.setAttachment("EmployeeSkills/" + filename + extension);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeSkills.setId(skillId);
			employeeSkillsDAO.updateEmployeeSkills(employeeSkills);

			return new ModelAndView(new RedirectView("showviewskills.htm"));
		}
	}

	// ############################################### SKILL
	// END##################################################### //

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Bank
	// Start@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //

	public ModelMap getEmployeebankModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showaddbank.htm", method = RequestMethod.GET)
	public ModelAndView showaddbank(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeBank employeeBank, BindingResult bindingResult) {
		ModelMap modelMap = getEmployeebankModelMap();
		modelMap.addAttribute("employeeBank", employeeBank);
		return new ModelAndView("addbank", modelMap);
	}

	@RequestMapping(value = "addbank.htm", method = RequestMethod.POST)
	public ModelAndView addbank(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployeeBank employeeBank,
			BindingResult bindingResult) {
		ModelMap modelMap = getEmployeebankModelMap();
		// employeeSkillsValidator.validate(employeeSkills, bindingResult);
		// employeeBankValidator.validate(employeeBank, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addbank", modelMap);
		}

		else {
			int bankId = BankDAO.saveAndGetId(employeeBank);
			String extension = "";
			String filename = employeeBank.getEmployeeId() + "_" + bankId;

			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}

			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "EmployeeBank", filename + extension);
					employeeBank.setAttachment("EmployeeBank/" + filename + extension);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeBank.setId(bankId);
			BankDAO.saveEmployeeBank(employeeBank);
			return new ModelAndView(new RedirectView("showaddbank.htm"));

		}
	}

	@RequestMapping(value = "showviewbank.htm", method = RequestMethod.GET)
	public ModelAndView showViewBank(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			EmployeeBank employeeBank) {
		ModelMap modelMap = getEmployeebankModelMap();
		List<EmployeeBank> BankList = BankDAO.getEmployeeBankList();
		modelMap.addAttribute("BankList", BankList);
		modelMap.addAttribute("BankListSize", BankList.size());
		return new ModelAndView("viewemployeebank", modelMap);
	}

	@RequestMapping(value = "deleteemployeeBank.htm", method = RequestMethod.POST)
	public ModelAndView deleteEmployeeBank(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeBank employeeBank) {
		BankDAO.deleteEmployeeBank(id);
		return new ModelAndView(new RedirectView("showviewbank.htm"));
	}

	@RequestMapping(value = "showupdateemployeesbank.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateEmployeeBank(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeBank employeeBank) {
		ModelMap modelMap = getEmployeebankModelMap();
		employeeBank = BankDAO.getEmployeeBank(id);
		modelMap.addAttribute("employeeBank", employeeBank);
		return new ModelAndView("updateemployeebank", modelMap);
	}

	@RequestMapping(value = "updateBank.htm", method = RequestMethod.POST)
	public ModelAndView updateEmployeeBank(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, EmployeeBank employeeBank,
			BindingResult bindingResult) {
		ModelMap modelMap = getEmployeebankModelMap();
		// employeeSkillsValidator.validate(employeeSkills, bindingResult);
		// employeeBankValidator.validate(employeeBank, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addbank", modelMap);
		}

		else {
			int bankId = BankDAO.saveAndGetId(employeeBank);
			String extension = "";
			String filename = employeeBank.getEmployeeId() + "_" + bankId;

			if (attachmentFile.getSize() > 0) {
				extension = attachmentFile.getOriginalFilename()
						.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
			}

			if (extension != "") {
				Imageload imageload = new Imageload();
				try {
					imageload.uploadImage(attachmentFile, request, "EmployeeBank", filename + extension);
					employeeBank.setAttachment("EmployeeBank/" + filename + extension);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			employeeBank.setId(bankId);
			BankDAO.saveEmployeeBank(employeeBank);
			return new ModelAndView(new RedirectView("showviewbank.htm"));
		}
	}

	// ################################################# bank end//
	// #############################################################//
//	public ModelMap getEmpattendancerecordModelMap() {
//		ModelMap modelMap = new ModelMap();
//		List<AttendanceRecord>attendenceRecordList = attendanceRecordDAO.getAttendanceRecordList(empCode);
//		modelMap.addAttribute("attendenceRecordList", attendenceRecordList);
//		return modelMap;
//	}

	public ModelMap getEmployeeProjectsModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "updateEmployeeProjects.htm", method = RequestMethod.POST)
	public ModelAndView updateEmployeeProjects(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelMap modelMap = getEmployeeProjectsModelMap();
		Projects projectObj = projectsDAO.getEmployeeProjects(id);
		modelMap.addAttribute("projects", projectObj);

		return new ModelAndView("editprojects", modelMap);

	}

	@RequestMapping(value = "deleteEmployeeProjects.htm", method = RequestMethod.POST)
	public ModelAndView deleteEmployeeProjects(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Projects projects) {
		projectsDAO.deleteEmployeeProjects(id);
		return new ModelAndView(new RedirectView("viewEmployeeProject.htm"));
	}

	@RequestMapping(value = "viewEmployeeProject.htm", method = RequestMethod.GET)
	public ModelAndView viewEmployeeProject(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Projects projects) {
		ModelMap modelMap = getEmployeeProjectsModelMap();
		modelMap.addAttribute("employeeProjectList", projectsDAO.getEmployeeProjects());
		modelMap.addAttribute("employeeProjectListSize", projectsDAO.getEmployeeProjects().size());
		return new ModelAndView("viewemployeeproject", modelMap);
	}

	@RequestMapping(value = "saveOrUpdateEmployeeProject.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateEmployeeProject(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Projects projects, BindingResult bindingResult) {
		ModelMap modelMap = getEmployeeProjectsModelMap();
		employeeProjectsValidator.validate(projects, bindingResult);
		if (bindingResult.hasErrors()) {
			List<Employee> employeeList = employeeDAO.getEmployeeList();
			modelMap.addAttribute("employeeList", employeeList);
			return new ModelAndView("showaddemployeeproject", modelMap);
		}

		else {
			projectsDAO.saveEmployeeProjects(projects);
			return new ModelAndView(new RedirectView("viewEmployeeProject.htm"));
		}
	}

	@RequestMapping(value = "showAddEmployeeProject.htm", method = RequestMethod.GET)
	public ModelAndView showAddProject(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Projects projects) {
		ModelMap modelMap = getEmployeeProjectsModelMap();
		return new ModelAndView("showaddemployeeproject", modelMap);
	}

	public ModelMap getEducationModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		return modelMap;
	}

	@RequestMapping(value = "showEducationDetails.htm", method = RequestMethod.POST)
	public ModelAndView showEducationDetails(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getEducationModelMap();

		Employee employeeObj = employeeDAO.getEmployeeById(id);
		List<Education> educationsLists = educationDAO.getEducationListByEmployeeId(id);
		if (educationsLists.size() != 0) {
			modelMap.addAttribute("education", educationsLists.get(0));
		} else {
			modelMap.addAttribute("education", new Education());
		}

		modelMap.addAttribute("employeeObj", employeeObj);

		return new ModelAndView("education", modelMap);

	}

	@RequestMapping(value = "showAddEducationalDetails.htm", method = RequestMethod.GET)
	public ModelAndView showAddEducationalDetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Education education) {

		ModelMap modelMap = getEducationModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		List<Education> educationsList = educationDAO.getEducationList();
		List<Employee> employeeListWithoutUpdate = new ArrayList<Employee>();
		for (Employee employee : employeeList) {
			boolean employeePresent = false;
			for (Education educationObj : educationsList) {
				if (educationObj.getEmp_id() == employee.getEmployeeId()) {
					employeePresent = true;
				}
			}
			if (!employeePresent) {
				employeeListWithoutUpdate.add(employee);
			}
		}
		modelMap.addAttribute("employeeList", employeeListWithoutUpdate);
		return new ModelAndView("showaddeducationaldetails", modelMap);
	}

	@RequestMapping(value = "showEducationDetailsAddNew.htm", method = RequestMethod.GET)
	public ModelAndView showEducationDetailsAddNew(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Education education) {
		ModelMap modelMap = getEducationModelMap();
		Employee employeeObj = employeeDAO.getEmployeeById(id);
		modelMap.addAttribute("employeeObj", employeeObj);
		Education educationList = educationDAO.getEducationByEmployeeId(id);
		modelMap.addAttribute("educations", educationList);
		return new ModelAndView("education", modelMap);

	}

	@RequestMapping(value = "addEducationDetails.htm", method = RequestMethod.POST)
	public ModelAndView addEducationDetails(@RequestParam(value = "attachmentFile") MultipartFile attachmentFile,
			@RequestParam(value = "fromFlag") String fromFlag, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Education education, BindingResult bindingResult) {

		ModelMap modelmp = new ModelMap();
		if (null != request.getParameter("employeeid") && education.getEmp_id() == 0)
			education.setEmp_id(Integer.parseInt(request.getParameter("employeeid")));
		if (null != request.getParameter("state") && education.getEdu_state() == 0)
			education.setEdu_state(Integer.parseInt(request.getParameter("state")));
		if (null != request.getParameter("city") && education.getEdu_city() == 0)
			education.setEdu_city(Integer.parseInt(request.getParameter("city")));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int id = education.getEmp_id();
		String fileName = null;
		String extension = "";
		if (attachmentFile.getSize() > 0) {
			extension = attachmentFile.getOriginalFilename()
					.substring(attachmentFile.getOriginalFilename().lastIndexOf("."));
		}

		fileName = "edu_" + firstName + "_" + lastName + "_" + education.getEmp_id() + extension;

		try {
			Imageload imageload = new Imageload();
			String path = imageload.uploadImage(attachmentFile, request, "Attachments", fileName);
			education.setAttachment("Attachments/" + fileName);

		} catch (IOException e) {

			e.printStackTrace();
		}
		Employee employeeObj = employeeDAO.getEmployeeById(id);
		modelmp.addAttribute("employeeObj", employeeObj);
		modelmp.addAttribute("countries", countriesDAO.getCountriesList());
		modelmp.addAttribute("employeeList", employeeDAO.getEmployeeList());
		Education educationList = educationDAO.getEducationByEmployeeId(id);
		modelmp.addAttribute("educations", educationList);
		employeeEducationValidator.validate(education, bindingResult);
		if (bindingResult.hasErrors()) {
			if (fromFlag.equals("add"))
				return new ModelAndView("education", modelmp);
			else
				return new ModelAndView("editeducation", modelmp);

		} else {
			educationDAO.saveEducation(education);
			if (fromFlag.equals("add"))
				return new ModelAndView(new RedirectView("showAddEducationalDetails.htm"));
			else
				return new ModelAndView(new RedirectView("viewEducation.htm"));
			// return new ModelAndView(new RedirectView("showEducationDetailsAddNew.htm"));
		}

	}

	@RequestMapping(value = "viewEducation.htm", method = RequestMethod.GET)
	public ModelAndView viewEducation(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Education education) {
		ModelMap modelMap = getEducationModelMap();
		List<Education> educationList = educationDAO.getEducationList();
		modelMap.addAttribute("educationList", educationList);
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("educationListSize", educationList.size());
		return new ModelAndView("vieweducation", modelMap);
	}

	@RequestMapping(value = "deleteEducation.htm", method = RequestMethod.POST)
	public ModelAndView deleteEducation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Education education) {

		educationDAO.deleteEducation(id);
		return new ModelAndView(new RedirectView("viewEducation.htm"));

	}

	@RequestMapping(value = "showupdateEducation.htm", method = RequestMethod.POST)
	public ModelAndView showupdateEducation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getEducationModelMap();
		Education educations = educationDAO.getEducationByEmployeeId(id);
		modelMap.addAttribute("educations", educations);
		Employee employeeObj = employeeDAO.getEmployeeById(id);
		modelMap.addAttribute("employeeObj", employeeObj);
		modelMap.addAttribute("education", educations);
		modelMap.addAttribute("States", statesDAO.getStatesById(educations.getEdu_country()));
		List<Cities> AvalableCities = citiesDAO.getCitiesById(educations.getEdu_state());
		modelMap.addAttribute("Cities", AvalableCities);
		return new ModelAndView("editeducation", modelMap);

	}

	public ModelMap getShowAssesmentModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Designation> designationList = designationDAO.getDesignation();
		modelMap.addAttribute("designationList", designationList);
		return modelMap;
	}

	@RequestMapping(value = "showAssesment.htm", method = RequestMethod.GET)
	public ModelAndView showAssesment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Assesment assesment) {
		return new ModelAndView("showassesment", getShowAssesmentModelMap());

	}

	@RequestMapping(value = "addAssesment.htm", method = RequestMethod.POST)
	public ModelAndView addAssesment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Assesment assesment, BindingResult bindingResult) {
		employeeAssesmentValidator.validate(assesment, bindingResult);
		if (bindingResult.hasErrors()) {
			/*
			 * List<Employee> employeeList=employeeDAO.getEmployeeList();
			 * modelMap.addAttribute("employeeList",employeeList); List<Designation>
			 * designationList=designationDAO.getDesignation();
			 * modelMap.addAttribute("designationList", designationList);
			 */
			return new ModelAndView("showassesment", getShowAssesmentModelMap());

		} else {
			assesmentDAO.saveEmployeeAssesment(assesment);
			return new ModelAndView(new RedirectView("viewAssesment.htm"));
		}

	}

	@RequestMapping(value = "viewAssesment.htm", method = RequestMethod.GET)
	public ModelAndView viewAssesment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Assesment assesment) {
		ModelMap modelMap = new ModelMap();
		List<Assesment> assesmentList = assesmentDAO.getEmployeeAssesment();
		modelMap.addAttribute("assesmentList", assesmentList);
		modelMap.addAttribute("assesmentListSize", assesmentList.size());
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		List<Designation> designationList = designationDAO.getDesignation();
		modelMap.addAttribute("designationList", designationList);
		return new ModelAndView("viewassesment", modelMap);
	}

	@RequestMapping(value = "deleteAssesment.htm", method = RequestMethod.POST)
	public ModelAndView deleteAssesment(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Assesment assesment) {

		assesmentDAO.deleteAssesment(id);
		return new ModelAndView(new RedirectView("viewAssesment.htm"));
	}

	@RequestMapping(value = "showupdateAssesment.htm", method = RequestMethod.POST)
	public ModelAndView showupdateAssesment(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		Assesment assesment = assesmentDAO.getEmployeeAssesmentById(id).get(0);
		modelMap.addAttribute("assesment", assesment);
		Employee employeeObj = employeeDAO.getEmployeeById(id);
		modelMap.addAttribute("employeeObj", employeeObj);
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		List<Designation> designationList = designationDAO.getDesignation();
		modelMap.addAttribute("designationList", designationList);
		return new ModelAndView("editassesment", modelMap);

	}

	public ModelMap getJobPositionModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Designation> designations = designationDAO.getDesignation();
		modelMap.addAttribute("designations", designations);
		return modelMap;
	}

	@RequestMapping(value = "jobposition.htm", method = RequestMethod.GET)
	public ModelAndView jobposition(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JobPositions jobPositions) {

		/*
		 * List<Designation> designations=designationDAO.getDesignation();
		 * modelMap.addAttribute("designations",designations);
		 */
		return new ModelAndView("jobPositions", getJobPositionModelMap());

	}

	@RequestMapping(value = "addjobposition.htm", method = RequestMethod.POST)
	public ModelAndView addjobposition(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JobPositions jobPositions, BindingResult bindingResult) {
		jobPositionValidator.validate(jobPositions, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("jobPositions", getJobPositionModelMap());
		} else {
			jobPositionDAO.saveJobPosition(jobPositions);
			return new ModelAndView(new RedirectView("jobposition.htm"));
		}

	}

	@RequestMapping(value = "viewJobPosition.htm", method = RequestMethod.GET)
	public ModelAndView viewJobPosition(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JobPositions jobPositions) {
		ModelMap modelMap = new ModelMap();
		List<JobPositions> jobPositionList = jobPositionDAO.getJobPositions();
		ArrayList<Integer> designationarr = new ArrayList<Integer>();
		for (int i = 0; i < jobPositionList.size(); i++) {
			JobPositions oneposition = jobPositionList.get(i);
			int designationid = oneposition.getDesignation();
			designationarr.add(designationid);
		}
		List<Designation> designations = designationDAO.getDesignation();
		modelMap.addAttribute("designationarr", designationarr);
		modelMap.addAttribute("designations", designations);
		modelMap.addAttribute("jobPositionList", jobPositionList);
		modelMap.addAttribute("jobPositionListSize", jobPositionList.size());
		return new ModelAndView("viewjobposition", modelMap);
	}

	@RequestMapping(value = "deleteJobPosition.htm", method = RequestMethod.POST)
	public ModelAndView deleteJobPosition(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, JobPositions jobPositions) {
		designationDAO.deleteDesignation(id);
		jobPositionDAO.deleteJobPosition(id);
		return new ModelAndView(new RedirectView("viewJobPosition.htm"));

	}

	public ModelMap getShowupdateJobPositionModelMap(int id) {
		ModelMap modelMap = new ModelMap();
		List<JobPositions> jobPositionList = jobPositionDAO.getJobPositionsById(id);
		for (int i = 0; i < jobPositionList.size(); i++) {
			JobPositions jobPositions = (JobPositions) jobPositionList.get(i);
			modelMap.addAttribute("id", jobPositions.getId());
			modelMap.addAttribute("jobPositions", jobPositions);
			/*
			 * modelMap.addAttribute("Job_code",jobpositionobj.getJob_code());
			 * modelMap.addAttribute("Position_name",jobpositionobj.getPosition_name());
			 * modelMap.addAttribute("Description",jobpositionobj.getDescription());
			 */
		}
		List<Designation> designations = designationDAO.getDesignation();
		modelMap.addAttribute("designations", designations);
		modelMap.addAttribute("jobPositionList", jobPositionList);
		modelMap.addAttribute("jobPositionListSize", jobPositionList.size());
		return modelMap;
	}

	@RequestMapping(value = "showupdateJobPosition.htm", method = RequestMethod.POST)
	public ModelAndView showupdateJobPosition(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		return new ModelAndView("editjobPosition", getShowupdateJobPositionModelMap(id));

	}

	@RequestMapping(value = "updatejobposition.htm", method = RequestMethod.POST)
	public ModelAndView updatejobposition(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, JobPositions jobPositions, BindingResult bindingResult) {
		try {
			JobPositions jobpositionobj = null;
			int Id = jobPositions.getId();
			String jobCode = jobPositions.getJob_code();
			String positionName = jobPositions.getPosition_name();
			String description = jobPositions.getDescription();
			int designation = jobPositions.getDesignation();
			List<JobPositions> jobPositionList = jobPositionDAO.getJobPositionsById(id);
			for (int i = 0; i < jobPositionList.size(); i++) {
				jobpositionobj = (JobPositions) jobPositionList.get(i);
				jobpositionobj.setId(Id);
				jobpositionobj.setJob_code(jobCode);
				jobpositionobj.setPosition_name(positionName);
				jobpositionobj.setDescription(description);
				jobpositionobj.setDesignation(designation);
			}

			jobPositionValidator.validate(jobpositionobj, bindingResult);

			if (bindingResult.hasErrors()) {
				return new ModelAndView("editjobPosition", getShowupdateJobPositionModelMap(id));
			} else {
				jobPositionDAO.saveJobPosition(jobpositionobj);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		;

		return new ModelAndView(new RedirectView("viewJobPosition.htm"));

	}

	public ModelMap getAddBreaksModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "breaks.htm", method = RequestMethod.GET)
	public ModelAndView addBreaks(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Breaks breaks) {
		return new ModelAndView("breaks", getAddBreaksModelMap());

	}

	@RequestMapping(value = "addbreaks.htm", method = RequestMethod.POST)
	public ModelAndView addbreaks(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Breaks breaks, BindingResult bindingResult) {
		leavesValidator.validate(breaks, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("breaks", getAddBreaksModelMap());
		} else {
			breakDAO.saveBreaks(breaks);
			return new ModelAndView(new RedirectView("breaks.htm"));
		}

	}

	@RequestMapping(value = "viewBreaks.htm", method = RequestMethod.GET)
	public ModelAndView viewBreaks(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Breaks breaks) {
		ModelMap modelMap = new ModelMap();
		List<Breaks> breakList = breakDAO.getBreaks();
		ArrayList<Integer> statusarr = new ArrayList<Integer>();
		for (int i = 0; i < breakList.size(); i++) {
			Breaks onebreak = breakList.get(i);
			int statusid = onebreak.getStatus();
			statusarr.add(statusid);
		}
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusarr", statusarr);
		modelMap.addAttribute("statusList", statusList);
		modelMap.addAttribute("breakList", breakList);
		modelMap.addAttribute("breakListSize", breakList.size());
		return new ModelAndView("viewbreaks", modelMap);

	}

	@RequestMapping(value = "deleteBreaks.htm", method = RequestMethod.POST)
	public ModelAndView deleteBreaks(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Breaks breaks) {
		breakDAO.deleteBreak(id);
		return new ModelAndView(new RedirectView("viewBreaks.htm"));

	}

	public ModelMap getShowUpdateBreaks(int id) {
		ModelMap modelMap = new ModelMap();
		List<Breaks> breakList = breakDAO.getBreakById(id);
		for (Breaks breakObj : breakList) {
			modelMap.addAttribute("breaks", breakObj);
			modelMap.addAttribute("breakObj", breakObj);
			modelMap.addAttribute("id", breakObj.getId());
		}

		List<Employee> employeeList = employeeDAO.getEmployeeList();
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showupdateBreaks.htm", method = RequestMethod.POST)
	public ModelAndView showupdateBreaks(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("editBreak", getShowUpdateBreaks(id));

	}

	@RequestMapping(value = "saveOrUpdateBreak.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateBreak(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Breaks breaks, BindingResult bindingResult) {
		breaks.setId(id);
		breaksValidator.validate(breaks, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("editBreak", getShowUpdateBreaks(id));
		} else {
			breakDAO.saveBreaks(breaks);
			return new ModelAndView(new RedirectView("viewBreaks.htm"));
		}
	}

	@RequestMapping(value = "leaveType.htm", method = RequestMethod.GET)
	public ModelAndView leaveType(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveType leaveType) {
		return new ModelAndView("leavetype");
	}

	@RequestMapping(value = "addLeaveType.htm", method = RequestMethod.POST)
	public ModelAndView addLeaveType(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveType leaveType, BindingResult bindingResult) {
		leaveTypeValidator.validate(leaveType, bindingResult, leaveTypeDAO);
		// List<LeaveType> leaveTypeList=leaveTypeDAO.getleavetypes();
		if (bindingResult.hasErrors()) {
			return new ModelAndView("leavetype");
		} else {
			/*
			 * for (LeaveType leaveType2 : leaveTypeList) {
			 * if(leaveType2.getRank()!=leaveType.getRank()) {
			 */
			leaveTypeDAO.saveLeaveType(leaveType);
			/*
			 * } }
			 */

			return new ModelAndView(new RedirectView("leaveType.htm"));
		}
	}

	@RequestMapping(value = "viewleaveType.htm", method = RequestMethod.GET)
	public ModelAndView viewleaveType(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveType leaveType) {
		ModelMap modelMap = new ModelMap();
		List<LeaveType> leaveTypeList = leaveTypeDAO.getleavetypes();
		modelMap.addAttribute("leaveTypeList", leaveTypeList);
		modelMap.addAttribute("leaveTypeListSize", leaveTypeList.size());
		return new ModelAndView("viewleavetype", modelMap);

	}

	@RequestMapping(value = "deleteLeaveType.htm", method = RequestMethod.POST)
	public ModelAndView deleteLeaveType(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveType leaveType) {
		leaveTypeDAO.deleteLeaveType(id);
		return new ModelAndView(new RedirectView("viewleaveType.htm"));

	}

	public ModelMap getShowupdateLeaveTypeModelMap(int id) {
		ModelMap modelMap = new ModelMap();
		List<LeaveType> leaveTypeList = leaveTypeDAO.getLeaveTypeById(id);
		for (LeaveType leaveTypeObj : leaveTypeList) {
			modelMap.addAttribute("leaveType", leaveTypeObj);
		}
		return modelMap;
	}

	@RequestMapping(value = "showupdateLeaveType.htm", method = RequestMethod.POST)
	public ModelAndView showupdateLeaveType(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("editLeaveType", getShowupdateLeaveTypeModelMap(id));
	}

	@RequestMapping(value = "saveOrUpdateLeaveType.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateLeaveType(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveType leaveType, BindingResult bindingResult) {
		leaveType.setId(id);
		leaveTypeValidator.validate(leaveType, bindingResult, leaveTypeDAO);
		// List<LeaveType> leaveTypeList=leaveTypeDAO.getleavetypes();
		if (bindingResult.hasErrors()) {
			return new ModelAndView("editLeaveType", getShowupdateLeaveTypeModelMap(id));
		} else {
			leaveTypeDAO.saveLeaveType(leaveType);
			return new ModelAndView(new RedirectView("viewleaveType.htm"));
		}
	}

	public ModelMap getLeaveAllocation(int id) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("leaveList", leaveTypeDAO.getleavetypes());
		List<LeaveApplication> leaveApplicationObj = leaveApplicationDAO.getApplicationsByIdandStatus(id);
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);
		modelMap.addAttribute("Id", id);
		int duration = (int) leaveApplicationObj.get(0).getDuration();
		modelMap.addAttribute("duration", duration);
		/* modelMap.addAttribute("daysWithOutHolidays", leaveApplicationObj) */
		modelMap.addAttribute("leaveApplicationObj", leaveApplicationObj);
		return modelMap;
	}

	@RequestMapping(value = "leaveAllocation.htm", method = RequestMethod.GET)
	public ModelAndView leaveAllocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveAllocation leaveAllocation) {
		return new ModelAndView("leaveAllocation", getLeaveAllocation(id));
	}

	@RequestMapping(value = "AllocateLeave.htm", method = RequestMethod.GET)
	public ModelAndView AllocateLeave(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveApplication leaveApplication) {
		ModelMap modelMap = new ModelMap();
		List<LeaveApplication> leaveApplicationList = leaveApplicationDAO.getApplicationsByStatus(1);

		/*
		 * ArrayList<Integer> priorityarr=new ArrayList<Integer>(); ArrayList<Integer>
		 * leavearr=new ArrayList<Integer>(); ArrayList<Integer> statusarr=new
		 * ArrayList<Integer>(); ArrayList<Integer> employeearr=new
		 * ArrayList<Integer>(); ArrayList<Integer> deptarr=new ArrayList<Integer>();
		 * for(int i=0;i<leaveApplicationList.size();i++) { LeaveApplication
		 * oneleaveapplication=leaveApplicationList.get(i); int
		 * priority=oneleaveapplication.getPriority(); priorityarr.add(priority);
		 * 
		 * int leavetype=oneleaveapplication.getLeavetype(); leavearr.add(leavetype);
		 * 
		 * int status=oneleaveapplication.getStatus(); statusarr.add(status);
		 * 
		 * int employee=oneleaveapplication.getEmployee_id(); employeearr.add(employee);
		 * 
		 * int department=oneleaveapplication.getDepartment_id();
		 * deptarr.add(department);
		 * 
		 * }
		 */

		// modelMap.addAttribute("statusarr",statusarr);

		int sessionEmployeeId = 0;
		if (null != session.getAttribute("loginUserId"))
			sessionEmployeeId = (Integer) session.getAttribute("loginUserId");
		List<LeaveApplication> leaveApplicationsFinal = new ArrayList<LeaveApplication>();
		System.out.println("Login User Id" + sessionEmployeeId);
		List<Employee> EmployeeListBySupervisor = employeeDAO.getEmployeeListBySupervisor(sessionEmployeeId);
		for (LeaveApplication application : leaveApplicationList) {
			if (sessionEmployeeId != 0) {
				String singleEmployeeId = application.getSingleEmployeeId();
				System.out.println("Single Emp ID" + singleEmployeeId);
				for (Employee employee : EmployeeListBySupervisor) {
					if (!singleEmployeeId.equals("")
							&& Integer.parseInt(singleEmployeeId) == employee.getEmployeeId()) {
						System.out.println(" Emp ID" + employee.getEmployeeId());
						leaveApplicationsFinal.add(application);
					}

				}

			}
		}

		/*
		 * List<LeaveApplication> leaveApplicationsFinal=new
		 * ArrayList<LeaveApplication>(); for(LeaveApplication
		 * application:leaveApplicationList) { if(sessionEmployeeId!=0) { String
		 * singleEmployeeId= application.getSingleEmployeeId(); String
		 * multipleEmployeeId= application.getMultipleEmployeeIds();
		 * if(!singleEmployeeId.equals("")&&Integer.parseInt(singleEmployeeId)==
		 * sessionEmployeeId) { leaveApplicationsFinal.add(application); }else {
		 * if(!multipleEmployeeId.equals("")) { String[]
		 * multipleArray=multipleEmployeeId.split(","); for(String
		 * multipleArrayObj:multipleArray) {
		 * if(Integer.parseInt(multipleArrayObj)==sessionEmployeeId) {
		 * leaveApplicationsFinal.add(application); } }
		 * 
		 * }
		 * 
		 * } if(singleEmployeeId.equals("")&&multipleEmployeeId.equals("")) {
		 * leaveApplicationsFinal.add(application); }
		 * 
		 * } }
		 */

		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);

		// modelMap.addAttribute("priorityarr",priorityarr);
		List<Priority> Priorities = priorityDAO.getPriorityList();
		modelMap.addAttribute("prioritiesList", Priorities);

		// modelMap.addAttribute("leavearr",leavearr);
		List<LeaveType> leaveTypeList = leaveTypeDAO.getleavetypes();
		modelMap.addAttribute("leaveTypeList", leaveTypeList);

		// modelMap.addAttribute("employeearr",employeearr);
		List<Employee> EmployeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", EmployeeList);

		// modelMap.addAttribute("deptarr",deptarr);
		List<Departments> deptList = departmentDAO.getDepartment();
		modelMap.addAttribute("deptList", deptList);
		modelMap.addAttribute("zeroFlag", 0);

		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		/*
		 * modelMap.addAttribute("leaveApplicationList",leaveApplicationList);
		 * modelMap.addAttribute("leaveApplicationListSize",leaveApplicationList.size())
		 * ;
		 */
		modelMap.addAttribute("leaveApplicationList", leaveApplicationsFinal);
		modelMap.addAttribute("leaveApplicationListSize", leaveApplicationsFinal.size());
		return new ModelAndView("viewApplications", modelMap);

	}

	@RequestMapping(value = "AllocateLeaveApplication.htm", method = RequestMethod.POST)
	public ModelAndView AllocateLeaveApplication(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveAllocation leaveAllocation,
			BindingResult bindingResult) {
		return new ModelAndView(new RedirectView("leaveAllocation.htm?Id=" + id));
	}

	@RequestMapping(value = "addLeaveAllocation.htm", method = RequestMethod.POST)
	public ModelAndView addLeaveAllocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeMessages employeeMessages,
			LeaveAllocation leaveAllocation, BindingResult bindingResult) {
		List<LeaveApplication> leaveApplicationList = leaveApplicationDAO.getLeaveApplicationsById(id);
		LeaveApplication leaveApplicationObj = null;
		Employee employee = null;
		DateFormat messageDateFormat = new SimpleDateFormat("d-M-yyyy");
		leaveAllocationValidator.validate(leaveAllocation, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getLeaveAllocation(id);
			modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
			modelMap.addAttribute("leaveList", leaveTypeDAO.getleavetypes());
			return new ModelAndView("leaveAllocation", modelMap);

		} else {
			for (int i = 0; i < leaveApplicationList.size(); i++) {
				leaveApplicationObj = leaveApplicationList.get(i);

				leaveApplicationObj.setId(id);
				/*
				 * if(null!=request.getParameter("leaveStatus")){
				 * leaveApplicationObj.setStatus(Integer.parseInt(request.getParameter(
				 * "leaveStatus"))); }
				 */
				Employee employee2 = employeeDAO
						.getEmployeeByID(Integer.parseInt(session.getAttribute("loginUserId").toString()));
				leaveApplicationObj.setApproved_by(employee2.getEmail());
				leaveApplicationObj.setLeaveStatus(leaveAllocation.getAllocationStatus());
				leaveApplicationObj.setApproveFromdate(leaveAllocation.getApproved_fromdate());
				leaveApplicationObj.setApproveTodate(leaveAllocation.getApproved_todate());
				if (leaveAllocation.getAllocationStatus() == 3) {
					/*
					 * DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy"); Date
					 * approvedStartDate=new Date(); Date approvedEndDate=new Date(); try {
					 * approvedStartDate=dateFormat.parse(leaveAllocation.getApproved_fromdate());
					 * approvedEndDate=dateFormat.parse(leaveAllocation.getApproved_todate()); }
					 * catch (ParseException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 * 
					 * int
					 * days=(int)(approvedEndDate.getTime()-approvedStartDate.getTime())/(24*60*60*
					 * 1000);
					 * 
					 */

					int employeeId = leaveAllocation.getEmployee_id();
					leaveApplicationObj.setEmployeeDepartmentId(employeeDAO.getDepartmentIdByEmployeeId(employeeId));
					leaveAllocation.setEmployeeDepartmentId(employeeDAO.getDepartmentIdByEmployeeId(employeeId));
					List<Employee> employeesList = employeeDAO.getEmployeeList(employeeId);
					List<Company> companiesList = companyDAO.getCompanies();
					if (employeesList.size() != 0) {
						employee = employeesList.get(0);
						int previouslyUsedLeaves = employee.getLeavesUsed();
						employee.setLeavesUsed(previouslyUsedLeaves + Integer.parseInt(leaveAllocation.getValue()));
						employeeDAO.saveUser(employee);
					}
				}
				leaveApplicationDAO.saveLeaveApplication(leaveApplicationObj);

			}

			leaveAllocationDAO.saveLeaveAllocation(leaveAllocation);
			/* return new ModelAndView(new RedirectView("leaveAllocation.htm?Id="+id)); */

			employee = employeeDAO.getEmployeeById(leaveAllocation.getEmployee_id());
			employeeMessages.setEmployeeId(employee.getEmployeeId());
			employeeMessages.setEmployeeDepartmentId(employee.getDepartmentId());
			employeeMessages.setMemo(false);
			employeeMessages.setMessageBody(
					"Hello " + employee.getFirstName() + " " + employee.getLastName() + "," + "<br>Your leave is "
							+ statusDAO.getLeaveStatus(leaveAllocation.getAllocationStatus()).get(0).getStatus_name()
							+ "." + "<br>From date: " + leaveAllocation.getApproved_fromdate() + ". " + "To date: "
							+ leaveAllocation.getApproved_todate() + ".");
			employeeMessages.setMessageStatus(false);
			employeeMessages.setMessageDate(messageDateFormat.format(new Date()));
			employeeMessages.setMessageFrom(Integer.parseInt(session.getAttribute("loginUserId").toString()));
			employeeMessages.setMessageSubject("Regarding leave approval status");
			employeeMessagesDAO.saveEmployeeMessages(employeeMessages);
			return new ModelAndView(new RedirectView("AllocateLeave.htm"));

		}
	}

	@RequestMapping(value = "viewleaveAllocation.htm", method = RequestMethod.GET)
	public ModelAndView viewleaveAllocation(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, LeaveAllocation leaveAllocation) {
		ModelMap modelMap = new ModelMap();
		List<LeaveAllocation> leaveAllocationList = leaveAllocationDAO.getLeaveAllocation();

		ArrayList<Integer> employeearr = new ArrayList<Integer>();
		ArrayList<Integer> leavearr = new ArrayList<Integer>();
		ArrayList<Integer> statusarr = new ArrayList<Integer>();

		for (int i = 0; i < leaveAllocationList.size(); i++) {
			LeaveAllocation oneleave = leaveAllocationList.get(i);
			int employeeno = oneleave.getEmployee_id();
			employeearr.add(employeeno);

			int leavetype = oneleave.getTypeofleave();
			leavearr.add(leavetype);

			int status = oneleave.getAllocationStatus();
			statusarr.add(status);

		}

		modelMap.addAttribute("employeearr", employeearr);
		modelMap.addAttribute("EmployeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("statusarr", statusarr);
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);

		modelMap.addAttribute("leavearr", leavearr);
		modelMap.addAttribute("leaveTypeList", leaveTypeDAO.getleavetypes());
		modelMap.addAttribute("leaveAllocationList", leaveAllocationList);
		modelMap.addAttribute("leaveAllocationListSize", leaveAllocationList.size());
		return new ModelAndView("viewleaveallocation", modelMap);

	}

	@RequestMapping(value = "deleteleaveAllocation.htm", method = RequestMethod.POST)
	public ModelAndView deleteleaveAllocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveAllocation leaveAllocation) {
		leaveAllocationDAO.deleteLeaveAllocation(id);
		return new ModelAndView(new RedirectView("viewleaveAllocation.htm"));

	}

	public ModelMap getShowUpdateLeaveAllocation(int id) {
		ModelMap modelMap = new ModelMap();
		List<LeaveAllocation> leaveAllocationList = leaveAllocationDAO.getLeaveAllocationById(id);
		for (LeaveAllocation leaveAllocationObj : leaveAllocationList) {
			modelMap.addAttribute("leaveAllocationObj", leaveAllocationObj);
		}
		modelMap.addAttribute("statusList", statusDAO.getStatusByCategory("leaves"));
		modelMap.addAttribute("leaveList", leaveTypeDAO.getleavetypes());
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("leavetypeList", leaveTypeDAO.getleavetypes());
		return modelMap;
	}

	@RequestMapping(value = "showupdateleaveAllocation.htm", method = RequestMethod.POST)
	public ModelAndView showupdateleaveAllocation(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveAllocation leaveAllocation) {
		return new ModelAndView("editLeaveAllocation", getShowUpdateLeaveAllocation(id));
	}

	@RequestMapping(value = "saveOrUpdateLeaveAllocation.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateLeaveType(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveAllocation leaveAllocation,
			BindingResult bindingResult) {

		List<LeaveApplication> leaveApplicationList = leaveApplicationDAO.getLeaveApplicationsById(id);
		LeaveApplication leaveApplicationObj = null;

		leaveAllocationValidator.validate(leaveAllocation, bindingResult);
		if (bindingResult.hasErrors()) {
			/*
			 * List<LeaveAllocation>
			 * leaveAllocationList=leaveAllocationDAO.getLeaveAllocationById(id);
			 * for(LeaveAllocation leaveAllocationObj:leaveAllocationList) {
			 * modelMap.addAttribute("leaveAllocationObj", leaveAllocationObj); }
			 * modelMap.addAttribute("statusList",statusDAO.getStatusByCategory("leaves"));
			 * modelMap.addAttribute("leaveList",leaveTypeDAO.getleavetypes());
			 * 
			 * modelMap.addAttribute("employeeList",employeeDAO.getEmployeeList());
			 * modelMap.addAttribute("leavetypeList",leaveTypeDAO.getleavetypes());
			 */

			return new ModelAndView("editLeaveAllocation", getShowUpdateLeaveAllocation(id));

		} else {
			for (int i = 0; i < leaveApplicationList.size(); i++) {
				leaveApplicationObj = leaveApplicationList.get(i);

				leaveApplicationObj.setId(id);
				/*
				 * if(null!=request.getParameter("leaveStatus")){
				 * leaveApplicationObj.setStatus(Integer.parseInt(request.getParameter(
				 * "leaveStatus"))); }
				 */

				leaveApplicationObj.setLeaveStatus(leaveAllocation.getAllocationStatus());
				leaveApplicationObj.setApproveFromdate(leaveAllocation.getApproved_fromdate());
				leaveApplicationObj.setApproveTodate(leaveAllocation.getApproved_todate());
				leaveApplicationDAO.saveLeaveApplication(leaveApplicationObj);

			}

			leaveAllocation.setId(id);
			leaveAllocationDAO.updateLeaveAllocation(leaveAllocation);
			return new ModelAndView(new RedirectView("viewleaveAllocation.htm"));

		}

	}

	public ModelMap getLeaveApplicationModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("leavetypeList", leaveTypeDAO.getleavetypes());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("priorities", priorityDAO.getPriorityList());
		return modelMap;
	}

	@RequestMapping(value = "leaveApplication.htm", method = RequestMethod.GET)
	public ModelAndView leaveApplication(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveApplication leaveApplication) {
		return new ModelAndView("leaveApplication", getLeaveApplicationModelMap());

	}

	@RequestMapping(value = "addLeaveApplication.htm", method = RequestMethod.POST)
	public ModelAndView addLeaveApplication(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, LeaveApplication leaveApplication, BindingResult bindingResult) {

		if (null != request.getParameter("deptEmployee")) {
			leaveApplication.setEmployee_id(Integer.parseInt(request.getParameter("deptEmployee")));
			leaveApplication.setEmployee_no(Integer.parseInt(request.getParameter("deptEmployee")));
		}
		leaveApplicationValidator.validate(leaveApplication, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("leaveApplication", getLeaveApplicationModelMap());

		} else {
			if (request.getParameter("half_day_session") != null) {
				leaveApplication.setHalf_day_session(request.getParameter("half_day_session"));

				if (request.getParameter("half_day_session").equals("First Session")) {
					leaveApplication.setFirst_half_day("true");
				} else if (request.getParameter("half_day_session").equals("Second Session")) {
					leaveApplication.setLast_half_day("true");
				}
			} else
				leaveApplication.setHalf_day_session("None");

			leaveApplication.setLeaveStatus(1);
			System.out.println("EmployeeID" + leaveApplication.getEmployee_id());

			List<Employee> employeeList = employeeDAO.getEmployeeListById(leaveApplication.getEmployee_id());

			for (int i = 0; i < employeeList.size(); i++) {
				Employee employeeObj = employeeList.get(i);
				leaveApplication.setEmployee_no(employeeObj.getEmployeeNo());
				System.out.println("EmployeeNO" + employeeObj.getEmployeeNo());
			}

			leaveApplicationDAO.saveLeaveApplication(leaveApplication);
			return new ModelAndView(new RedirectView("leaveApplication.htm"));
		}
	}

	@RequestMapping(value = "showHalfDaySession.htm", method = RequestMethod.GET)
	public ModelAndView showHalfDaySession(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, LeaveApplication leaveApplication) {

		return new ModelAndView("showHalfDaySession");

	}

	@RequestMapping(value = "viewleaveApplication.htm", method = RequestMethod.GET)
	public ModelAndView viewleaveApplication(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, LeaveApplication leaveApplication) {
		ModelMap modelMap = new ModelMap();
		int empId = (Integer) session.getAttribute("loginUserId");
		List<LeaveApplication> leaveApplicationList = leaveApplicationDAO.getLeaveApplicationsByEmployeeId(empId);
		ArrayList<Integer> priorityarr = new ArrayList<Integer>();
		ArrayList<Integer> leavearr = new ArrayList<Integer>();
		ArrayList<Integer> statusarr = new ArrayList<Integer>();
		ArrayList<Integer> employeearr = new ArrayList<Integer>();
		ArrayList<Integer> deptarr = new ArrayList<Integer>();
		for (int i = 0; i < leaveApplicationList.size(); i++) {
			LeaveApplication oneleaveapplication = leaveApplicationList.get(i);
			/*
			 * int priority = oneleaveapplication.getPriority(); priorityarr.add(priority);
			 */

			int leavetype = oneleaveapplication.getLeavetype();
			leavearr.add(leavetype);

			int status = oneleaveapplication.getLeaveStatus();
			statusarr.add(status);

			int employee = oneleaveapplication.getEmployee_id();
			employeearr.add(employee);

			int department = oneleaveapplication.getDepartment_id();
			deptarr.add(department);

		}

		modelMap.addAttribute("statusarr", statusarr);
		List<Status> statusList = statusDAO.getStatusByCategory("leaves");
		modelMap.addAttribute("statusList", statusList);

		modelMap.addAttribute("priorityarr", priorityarr);
		List<Priority> Priorities = priorityDAO.getPriorityList();
		modelMap.addAttribute("Priorities", Priorities);

		modelMap.addAttribute("leavearr", leavearr);
		List<LeaveType> leaveTypeList = leaveTypeDAO.getleavetypes();
		modelMap.addAttribute("leaveTypeList", leaveTypeList);

		modelMap.addAttribute("employeearr", employeearr);
		List<Employee> EmployeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("EmployeeList", EmployeeList);

		modelMap.addAttribute("deptarr", deptarr);
		List<Departments> deptList = departmentDAO.getDepartment();
		modelMap.addAttribute("deptList", deptList);
		modelMap.addAttribute("zeroFlag", 0);

		modelMap.addAttribute("leaveApplicationList", leaveApplicationList);
		modelMap.addAttribute("leaveApplicationListSize", leaveApplicationList.size());
		return new ModelAndView("viewleaveApplication", modelMap);

	}

	@RequestMapping(value = "deleteleaveApplication.htm", method = RequestMethod.POST)
	public ModelAndView deleteleaveApplication(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveApplication leaveApplication) {
		/* leaveAllocationDAO.deleteLeaveAllocation(id); */
		leaveApplicationDAO.deleteLeaveApplications(id);
		return new ModelAndView(new RedirectView("viewleaveApplication.htm"));

	}

	@RequestMapping(value = "showupdateleaveApplication.htm", method = RequestMethod.POST)
	public ModelAndView showupdateleaveApplication(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<LeaveApplication> leaveApplicationList = leaveApplicationDAO.getLeaveApplications();
		for (LeaveApplication leaveApplicationObj : leaveApplicationList) {
			modelMap.addAttribute("leaveApplication", leaveApplicationObj);
		}
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("leavetypeList", leaveTypeDAO.getleavetypes());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("priorities", priorityDAO.getPriorityList());
		modelMap.addAttribute("id", id);
		return new ModelAndView("editLeaveApplication", modelMap);

	}

	@RequestMapping(value = "saveOrUpdateLeaveApplication.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateLeaveApplication(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LeaveApplication leaveApplication) {
		leaveApplication.setId(id);

		if (request.getParameter("half_day_session") != null) {
			leaveApplication.setHalf_day_session(request.getParameter("half_day_session"));

			if (request.getParameter("half_day_session").equals("First Session")) {
				leaveApplication.setFirst_half_day("true");
			} else if (request.getParameter("half_day_session").equals("Second Session")) {
				leaveApplication.setLast_half_day("true");
			}
		} else
			leaveApplication.setHalf_day_session("None");

		leaveApplication.setLeaveStatus(1);
		leaveApplication.setEmployee_no(leaveApplication.getEmployee_id());
		leaveApplicationDAO.saveLeaveApplication(leaveApplication);
		return new ModelAndView(new RedirectView("viewleaveApplication.htm"));
	}

	public ModelMap getShowPersonalDetailsModelMap(int id) {
		ModelMap modelMap = new ModelMap();
		Employee employeeObj = employeeDAO.getEmployeeById(id);
		if (employeeObj != null) {
			modelMap.addAttribute("employeeObj", employeeObj);
		}
		modelMap.addAttribute("countries", countriesDAO.getCountriesList());
		modelMap.addAttribute("employeeList", employeeDAO.getEmployeeList());
		modelMap.addAttribute("States", statesDAO.getStatesById(employeeObj.getPermanentCountry()));
		List<Cities> AvalableCities = citiesDAO.getCitiesById(employeeObj.getPermanentState());
		modelMap.addAttribute("Cities", AvalableCities);
		return modelMap;
	}

	@RequestMapping(value = "showPersonalDetails.htm", method = RequestMethod.POST)
	public ModelAndView showPersonalDetails(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getShowPersonalDetailsModelMap(id);
		modelMap.addAttribute("dynamicActionFlag", 0);

		return new ModelAndView("personaldetails", modelMap);

	}

	@RequestMapping(value = "showPersonalDetailsFromAddTab.htm", method = RequestMethod.POST)
	public ModelAndView showPersonalDetailsFromAddTab(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getShowPersonalDetailsModelMap(id);
		modelMap.addAttribute("dynamicActionFlag", 1);

		return new ModelAndView("personaldetails", modelMap);

	}

	@RequestMapping(value = "showPersonalDetailsAddNew.htm", method = RequestMethod.GET)
	public ModelAndView showPersonalDetailsAddNew(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("personaldetails", getShowPersonalDetailsModelMap(id));

	}

	@RequestMapping(value = "showAddPrivileges.htm", method = RequestMethod.GET)
	public ModelAndView showAddPrivileges(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<UserPrivilege> userPrivilegeList = userPrivilegeDAO.getUserPrivilegeList();
		modelMap.addAttribute("userPrivilegeList", userPrivilegeList);
		modelMap.addAttribute("userPrivilegeListSize", userPrivilegeList.size());
		return new ModelAndView("showAddPrivileges", modelMap);
	}

	@RequestMapping(value = "viewPrivilegesDetails.htm", method = RequestMethod.GET)
	public ModelAndView viewPrivilegesDetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<UserPrivilege> userPrivilegeList = userPrivilegeDAO.getUserPrivilegeList();
		modelMap.addAttribute("userPrivilegeList", userPrivilegeList);
		modelMap.addAttribute("userPrivilegeListSize", userPrivilegeList.size());

		List<EmployeePrivilege> employeePrivilegesList = employeePrivilegeDAO.getEmployeePrivilegeList();
		modelMap.addAttribute("employeePrivilegesList", employeePrivilegesList);
		modelMap.addAttribute("employeePrivilegesListSize", employeePrivilegesList.size());
		return new ModelAndView("viewprivilegesdetails", modelMap);
	}

	public ModelMap getEmployeePrivileges(int employeeId) {
		ModelMap modelMap = new ModelMap();
		List<EmployeePrivilege> employeePrivilegeList = employeePrivilegeDAO
				.getEmployeePrivilegeListByEmployeeId(employeeId);
		if (employeePrivilegeList.size() != 0) {
			EmployeePrivilege employeePrivilege = employeePrivilegeList.get(0);
			modelMap.addAttribute("employeePrivilege", employeePrivilege);
			String[] employeePrivilegesArray = employeePrivilege.getPrivilege().split(",");
			List<String> employeePrivilegeSplitList = Arrays.asList(employeePrivilegesArray);
			modelMap.addAttribute("employeePrivilegeSplitList", employeePrivilegeSplitList);
		} else
			modelMap.addAttribute("employeePrivilege", new EmployeePrivilege());

		List<UserPrivilege> userPrivilegeList = userPrivilegeDAO.getUserPrivilegeList();
		modelMap.addAttribute("userPrivilegeList", userPrivilegeList);
		modelMap.addAttribute("userPrivilegeListSize", userPrivilegeList.size());
		return modelMap;
	}

	@RequestMapping(value = "showUpdatePrivilegesDetails.htm", method = RequestMethod.POST)
	public ModelAndView showUpdatePrivilegesDetails(@RequestParam(value = "Id") int priviledgeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<EmployeePrivilege> employeePrivilegesList = employeePrivilegeDAO
				.getEmployeePrivilegeListById(priviledgeId);
		int employeeId = employeePrivilegesList.get(0).getEmployeeId();
		return new ModelAndView("showupdateprivilegesdetails", getEmployeePrivileges(employeeId));
	}

	@RequestMapping(value = "showAddPrivilegesInternalDiv.htm", method = RequestMethod.POST)
	public ModelAndView showAddPrivilegesInternalDiv(@RequestParam(value = "id") int employeeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("showaddprivilegesinternaldiv", getEmployeePrivileges(employeeId));
	}

	@RequestMapping(value = "addPrivileges.htm", method = RequestMethod.POST)
	public ModelAndView addPrivileges(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		List<EmployeePrivilege> employeePrivilegeList = employeePrivilegeDAO.getEmployeePrivilegeList();
		boolean flag = false;
		EmployeePrivilege employeePrivilege = new EmployeePrivilege();
		if (employeePrivilegeList.size() != 0) {
			for (EmployeePrivilege employeePrivilege1 : employeePrivilegeList) {
				employeePrivilege1.setPrivilege(selectedcheckbox);
				if (employeePrivilege1.getEmployeeId() == employeeId) {
					employeePrivilegeDAO.updateEmployeePrivilege(employeePrivilege1);
					flag = true;
					break;
				}
				/*
				 * else { employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege); }
				 */
			}
			if (flag == false) {
				employeePrivilege.setEmployeeId(employeeId);
				employeePrivilege.setPrivilege(selectedcheckbox);
				employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege);
			}
		} else {

			employeePrivilege.setEmployeeId(employeeId);
			employeePrivilege.setPrivilege(selectedcheckbox);
			employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege);
		}
		return new ModelAndView(new RedirectView("showAddPrivileges.htm"));
	}

	@RequestMapping(value = "updatePrivileges.htm", method = RequestMethod.POST)
	public ModelAndView updatePrivileges(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		List<EmployeePrivilege> employeePrivilegeList = employeePrivilegeDAO.getEmployeePrivilegeList();
		boolean flag = false;
		EmployeePrivilege employeePrivilege = new EmployeePrivilege();
		if (employeePrivilegeList.size() != 0) {
			for (EmployeePrivilege employeePrivilege1 : employeePrivilegeList) {
				employeePrivilege1.setPrivilege(selectedcheckbox);
				if (employeePrivilege1.getEmployeeId() == employeeId) {
					employeePrivilegeDAO.updateEmployeePrivilege(employeePrivilege1);
					flag = true;
					break;
				}
				/*
				 * else { employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege); }
				 */
			}
			if (flag == false) {
				employeePrivilege.setEmployeeId(employeeId);
				employeePrivilege.setPrivilege(selectedcheckbox);
				employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege);
			}
		} else {

			employeePrivilege.setEmployeeId(employeeId);
			employeePrivilege.setPrivilege(selectedcheckbox);
			employeePrivilegeDAO.saveEmployeePrivilege(employeePrivilege);
		}
		return new ModelAndView(new RedirectView("viewPrivilegesDetails.htm"));
	}

	@RequestMapping(value = "deletePrivilegesDetails.htm", method = RequestMethod.POST)
	public ModelAndView deletePrivilegesDetails(@RequestParam(value = "Id") int priviledgeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		employeePrivilegeDAO.deleteEmployeePrivilegeListById(priviledgeId);
		return new ModelAndView(new RedirectView("viewPrivilegesDetails.htm"));
	}

	@RequestMapping(value = "showAddPersonalDetails.htm", method = RequestMethod.GET)
	public ModelAndView showAddPersonalDetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Employee employeeObj) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return new ModelAndView("showaddpersonaldetails", modelMap);
	}

	@RequestMapping(value = "addPersonalDetails.htm", method = RequestMethod.POST)
	public ModelAndView addPersonalDetails(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Employee employeeObj, BindingResult bindingResult) {
		String languagesSelected = null;
		if (null != request.getParameter("state") && employeeObj.getPermanentState() == 0)
			employeeObj.setPermanentState(Integer.parseInt(request.getParameter("state")));
		if (null != request.getParameter("city") && employeeObj.getPermanentCity() == 0)
			employeeObj.setPermanentCity(Integer.parseInt(request.getParameter("city")));

		employeePersonalValidator.validate(employeeObj, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("personaldetails", getShowPersonalDetailsModelMap(id));

		} else {
			if (null != request.getParameter("language")) {
				languagesSelected = employeeObj.getLanguages() + "," + request.getParameter("language");
				employeeObj.setLanguages(languagesSelected);
			}
			employeeDAO.updateUser(employeeObj);
			return new ModelAndView(new RedirectView("viewPersonalDetails.htm"));
		}

	}

	@RequestMapping(value = "addPersonalDetailsFromPersonalAdd.htm", method = RequestMethod.POST)
	public ModelAndView addPersonalDetailsFromPersonalAdd(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Employee employeeObj, BindingResult bindingResult) {
		if (null != request.getParameter("state") && employeeObj.getPermanentState() == 0)
			employeeObj.setPermanentState(Integer.parseInt(request.getParameter("state")));
		if (null != request.getParameter("city") && employeeObj.getPermanentCity() == 0)
			employeeObj.setPermanentCity(Integer.parseInt(request.getParameter("city")));
		employeePersonalValidator.validate(employeeObj, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("personaldetails", getShowPersonalDetailsModelMap(id));
		} else {

			employeeDAO.updateUser(employeeObj);
			return new ModelAndView(new RedirectView("showAddPersonalDetails.htm"));

		}

	}

	@RequestMapping(value = "viewPersonalDetails.htm", method = RequestMethod.GET)
	public ModelAndView viewPersonalDetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Employee employee) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		Map<Integer, String> countryMap = new HashMap<Integer, String>();
		Map<Integer, String> stateMap = new HashMap<Integer, String>();
		Map<Integer, String> cityMap = new HashMap<Integer, String>();

		int stateId = 0;
		int cityId = 0;
		int countryId = 0;
		for (int i = 0; i < employeeList.size(); i++) {
			Employee oneemplEmployee = employeeList.get(i);

			countryId = oneemplEmployee.getPermanentCountry();
			List<Countries> countriesList = countriesDAO.getCountryById(countryId);
			if (countriesList.size() != 0)
				countryMap.put(countryId, countriesList.get(0).getCountry());

			stateId = oneemplEmployee.getPermanentState();
			List<States> statesList = statesDAO.getStatesByStateId(stateId);
			if (statesList.size() != 0)
				stateMap.put(stateId, statesList.get(0).getStateName());

			cityId = oneemplEmployee.getPermanentCity();
			List<Cities> citiesList = citiesDAO.getCitiesByCityId(cityId);
			if (citiesList.size() != 0)
				cityMap.put(cityId, citiesList.get(0).getCity());
		}

		modelMap.addAttribute("countryMap", countryMap);
		modelMap.addAttribute("stateMap", stateMap);
		modelMap.addAttribute("cityMap", cityMap);

		modelMap.addAttribute("employeeList", employeeList);
		modelMap.addAttribute("employeeListSize", employeeList.size());
		return new ModelAndView("viewpersonal", modelMap);
	}

	@RequestMapping(value = "viewEmployees.htm", method = RequestMethod.GET)
	public ModelAndView viewEmployees(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Employee employee) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		/*
		 * ArrayList<Integer> countryarr=new ArrayList<Integer>(); ArrayList<Integer>
		 * statearr=new ArrayList<Integer>(); ArrayList<Integer> cityarr=new
		 * ArrayList<Integer>(); ArrayList<Integer> deptarr=new ArrayList<Integer>();
		 * ArrayList<Integer> designationarr=new ArrayList<Integer>(); int stateid = 0;
		 * int cityid = 0; for(int i=0;i<employeeList.size();i++) { Employee
		 * oneemplEmployee=employeeList.get(i);
		 * 
		 * int countryid=oneemplEmployee.getCurrentCountry(); countryarr.add(countryid);
		 * 
		 * stateid=oneemplEmployee.getCurrentState(); statearr.add(stateid);
		 * 
		 * cityid=oneemplEmployee.getCurrentCity(); cityarr.add(cityid);
		 * 
		 * int deptid=oneemplEmployee.getDepartmentId(); deptarr.add(deptid);
		 * 
		 * int designation=oneemplEmployee.getDesignation();
		 * designationarr.add(designation);
		 * 
		 * } modelMap.addAttribute("countryarr",countryarr);
		 * modelMap.addAttribute("statearr",statearr);
		 * modelMap.addAttribute("cityarr",cityarr);
		 * modelMap.addAttribute("deptarr",deptarr);
		 * modelMap.addAttribute("designationarr",designationarr);
		 */

		/*
		 * modelMap.addAttribute("countriesList",countriesDAO.getCountriesList());
		 * modelMap.addAttribute("statesList",statesDAO.getStates());
		 * modelMap.addAttribute("cityList",citiesDAO.getCities());
		 */
		modelMap.addAttribute("departmentsList", departmentDAO.getDepartment());
		modelMap.addAttribute("designationList", designationDAO.getDesignation());
		modelMap.addAttribute("locationList", locationDAO.getLocation());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		modelMap.addAttribute("workspaceList", workspacesDAO.getAllWorkspaces());
		modelMap.addAttribute("employeeList", employeeList);
		modelMap.addAttribute("employeeListSize", employeeList.size());
		return new ModelAndView("viewemployee", modelMap);
	}

	@RequestMapping(value = "deleteEmployee.htm", method = RequestMethod.POST)
	public ModelAndView deleteEmployee(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Employee employee) {

		employeeDAO.deleteEmployee(id);
		return new ModelAndView(new RedirectView("viewEmployees.htm"));

	}

	public ModelMap getShowUpdateEmployeeModelMap(int id) {
		ModelMap modelMap = new ModelMap();
		Employee employee = employeeDAO.getEmployeeById(id);
		String decryptedPassword = null;
		try {
			encryptPassword = new EncryptPassword();
			decryptedPassword = encryptPassword.decrypt(employee.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (employee != null) {
			modelMap.addAttribute("employeeObj", employee);
			modelMap.addAttribute("empPassword", decryptedPassword);
		}
		modelMap.addAttribute("Id", id);
		List<Departments> departmentsList = departmentDAO.getDepartment();
		List<Division> divisionList = divisionDAO.getDivisionList();
		List<Designation> designationList = designationDAO.getDesignation();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		List<Location> locationsList = locationDAO.getLocation();
		List<Workspaces> workspacesList = workspacesDAO.getAllWorkspaces();
		List<Countries> countriesList = countriesDAO.getCountriesList();
		List<Cities> citiesList = citiesDAO.getCities();
		List<States> statesList = statesDAO.getStates();
		List<JobPositions> jobPositionsList = jobPositionDAO.getJobPositions();
		List<Status> listsList = statusDAO.getStatusByCategory("employment_status");
		int workspaceId = employee.getWorkspace();
		if (workspaceId != 0) {
			Workspaces workspaces = workspacesDAO.getWorkspaceById(workspaceId);
			int departmentId = workspaces.getDepartment();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			modelMap.addAttribute("location", location);
			modelMap.addAttribute("department", department);
			modelMap.addAttribute("division", division);
		}
		modelMap.addAttribute("departmentsList", departmentsList);
		modelMap.addAttribute("divisionList", divisionList);
		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("employeeList", employeesList);
		modelMap.addAttribute("locationsList", locationsList);
		modelMap.addAttribute("workspacesList", workspacesList);
		modelMap.addAttribute("countriesList", countriesList);
		modelMap.addAttribute("citiesList", citiesList);
		modelMap.addAttribute("statesList", statesList);
		modelMap.addAttribute("jobPositionsList", jobPositionsList);
		modelMap.addAttribute("statusList", listsList);
		List<Cities> AvalableCities = citiesDAO.getCitiesById(employee.getCurrentState());
		modelMap.addAttribute("Cities", AvalableCities);
		modelMap.addAttribute("States", statesDAO.getStatesById(employee.getCurrentCountry()));
		countryID = employee.getCurrentCountry();
		modelMap.addAttribute("countryID", countryID);
		modelMap.addAttribute("userList", employeeDAO.getUserType());
		return modelMap;
	}

	@RequestMapping(value = "showupdateEmployee.htm", method = RequestMethod.POST)
	public ModelAndView showupdateEmployee(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("editEmployees", getShowUpdateEmployeeModelMap(id));

	}

	@RequestMapping(value = "showupdateEmployeeAddNew.htm", method = RequestMethod.GET)
	public ModelAndView showupdateEmployeeAddNew(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		/*
		 * Employee employee=employeeDAO.getEmployeeById(id); String
		 * decryptedPassword=null; try { encryptPassword=new EncryptPassword();
		 * decryptedPassword=encryptPassword.decrypt(employee.getPassword()); } catch
		 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * if(employee!=null) { modelMap.addAttribute("employeeObj", employee);
		 * modelMap.addAttribute("empPassword", decryptedPassword); }
		 * modelMap.addAttribute("Id", id); List<Departments>
		 * departmentsList=departmentDAO.getDepartment(); List<Division>
		 * divisionList=divisionDAO.getDivisionList(); List<Designation>
		 * designationList=designationDAO.getDesignation(); List<Employee>
		 * employeesList=employeeDAO.getEmployeeList(); List<Location>
		 * locationsList=locationDAO.getLocation(); List<Workspaces>
		 * workspacesList=workspacesDAO.getAllWorkspaces(); List<Countries>
		 * countriesList=countriesDAO.getCountriesList(); List<Cities>
		 * citiesList=citiesDAO.getCities(); List<States>
		 * statesList=statesDAO.getStates(); List<JobPositions>
		 * jobPositionsList=jobPositionDAO.getJobPositions(); List<Status>
		 * listsList=statusDAO.getStatusByCategory("employment_status");
		 * modelMap.addAttribute("departmentsList", departmentsList);
		 * modelMap.addAttribute("divisionList",divisionList );
		 * modelMap.addAttribute("designationList", designationList);
		 * modelMap.addAttribute("employeeList", employeesList);
		 * modelMap.addAttribute("locationsList",locationsList );
		 * modelMap.addAttribute("workspacesList",workspacesList );
		 * modelMap.addAttribute("countriesList", countriesList);
		 * modelMap.addAttribute("citiesList", citiesList);
		 * modelMap.addAttribute("statesList", statesList);
		 * modelMap.addAttribute("jobPositionsList", jobPositionsList);
		 * modelMap.addAttribute("statusList", listsList); List<Cities>
		 * AvalableCities=citiesDAO.getCitiesById(employee.getCurrentState());
		 * modelMap.addAttribute("Cities",AvalableCities);
		 * modelMap.addAttribute("States",statesDAO.getStatesById(employee.
		 * getCurrentCountry())); countryID=employee.getCurrentCountry();
		 * modelMap.addAttribute("countryID",countryID);
		 */
		return new ModelAndView("editEmployees", getShowUpdateEmployeeModelMap(id));

	}

	@RequestMapping(value = "saveOrUpdateEmployee.htm", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateEmployee(@RequestParam(value = "photoFile") MultipartFile photoFile,
			@RequestParam("Id") int id, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Employee employeeObj, BindingResult bindingResult) {
		String encryptedPassword = null;
		// employee.setEmployeeId(id);
		/*
		 * String extention=photoFile.getOriginalFilename().substring(photoFile.
		 * getOriginalFilename().indexOf("."));
		 */
		String extension = "";
		if (photoFile.getSize() > 0) {
			extension = photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
			String fileName = employeeObj.getFirstName() + "_" + employeeObj.getLastName() + "_" + id + extension;
			try {
				Imageload imageload = new Imageload();
				String path = imageload.uploadImage(photoFile, request, "ProfilePhotos", fileName);
				employeeObj.setPhoto("ProfilePhotos/" + fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		employeeObj.setEmployeeNo(id);
		if (request.getParameter("newPassword").equals("") && request.getParameter("oldPassword").equals("")) {
			String password = request.getParameter("newPassword");
			try {
				encryptPassword = new EncryptPassword();
				encryptedPassword = encryptPassword.encrypt(password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			employeeObj.setPassword(encryptedPassword);
		}
		if (null != request.getParameter("state"))
			employeeObj.setCurrentState(Integer.parseInt(request.getParameter("state")));
		if (null != request.getParameter("city"))
			employeeObj.setCurrentCity(Integer.parseInt(request.getParameter("city")));

		int workspaceId = employeeObj.getWorkspace();
		if (workspaceId != 0) {
			Workspaces workspaces = workspacesDAO.getWorkspaceById(workspaceId);
			int departmentId = workspaces.getDepartment();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			employeeObj.setLocation(locationId);
			employeeObj.setDivision(divisionId);
			employeeObj.setDepartmentId(departmentId);
		}

		EmployeeValidator employeeValidator = new EmployeeValidator();

		employeeValidator.validate(employeeObj, bindingResult, request);

		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getShowUpdateEmployeeModelMap(id);
			modelMap.addAttribute("EmployeeAddMsg", "Update Failed.");
			return new ModelAndView("editEmployees", modelMap);
		} else {
			employeeDAO.saveUser(employeeObj);
			return new ModelAndView(new RedirectView("viewEmployees.htm"));
		}
	}

	@RequestMapping(value = "saveEmpPassword.htm", method = RequestMethod.POST)
	public ModelAndView saveEmpPassword(@RequestParam("Id") int id, @RequestParam("conPass") String password,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		ModelMap modelMap = new ModelMap();
		Employee employee = employeeDAO.getEmployeeById(id);
		encryptPassword = new EncryptPassword();
		// String password=request.getParameter("confirmPassword");
		String encryptedPassword = encryptPassword.encrypt(password);
		employee.setPassword(encryptedPassword);
		employeeDAO.saveOrUpdateEmployeeAndGetId(employee);

		SendMailTLS sendMailTLS = new SendMailTLS();
		if (null != session.getAttribute("loginUserId")) {
			int senderEmployeeId = (Integer) session.getAttribute("loginUserId");
			Employee senderEmployee = employeeDAO.getEmployeeById(senderEmployeeId);
			String senderEmail = senderEmployee.getEmail();
			String decryptedSenderPasssword = encryptPassword.decrypt(senderEmployee.getPassword());
			String decryptedRecieverPasssword = encryptPassword.decrypt(employee.getPassword());
			String smtpHost = "";
			if (senderEmail.contains("yahoo")) {
				smtpHost = "smtp.mail.yahoo.com";
			} else {
				smtpHost = "smtp." + senderEmail.split("@")[1];
			}
			sendMailTLS.sendMail(senderEmail, decryptedSenderPasssword, smtpHost, "587", employee.getEmail(),
					employee.getFirstName() + " " + employee.getLastName(), decryptedRecieverPasssword);
		}

		if (employee != null) {
			modelMap.addAttribute("employeeObj", employee);
			modelMap.addAttribute("empPassword", password);
		}
		modelMap.addAttribute("Id", id);

		int workspaceId = employee.getWorkspace();
		if (workspaceId != 0) {
			Workspaces workspaces = workspacesDAO.getWorkspaceById(workspaceId);
			int departmentId = workspaces.getDepartment();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			modelMap.addAttribute("location", location);
			modelMap.addAttribute("department", department);
			modelMap.addAttribute("division", division);
		}

		List<Departments> departmentsList = departmentDAO.getDepartment();
		List<Division> divisionList = divisionDAO.getDivisionList();
		List<Designation> designationList = designationDAO.getDesignation();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		List<Location> locationsList = locationDAO.getLocation();
		List<Workspaces> workspacesList = workspacesDAO.getAllWorkspaces();
		List<Countries> countriesList = countriesDAO.getCountriesList();
		List<Cities> citiesList = citiesDAO.getCities();
		List<States> statesList = statesDAO.getStates();
		List<JobPositions> jobPositionsList = jobPositionDAO.getJobPositions();
		List<Status> listsList = statusDAO.getStatusByCategory("employment_status");
		modelMap.addAttribute("departmentsList", departmentsList);
		modelMap.addAttribute("divisionList", divisionList);
		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("employeeList", employeesList);
		modelMap.addAttribute("locationsList", locationsList);
		modelMap.addAttribute("workspacesList", workspacesList);
		modelMap.addAttribute("countriesList", countriesList);
		modelMap.addAttribute("citiesList", citiesList);
		modelMap.addAttribute("statesList", statesList);
		modelMap.addAttribute("jobPositionsList", jobPositionsList);
		modelMap.addAttribute("statusList", listsList);
		List<Cities> AvalableCities = citiesDAO.getCitiesById(employee.getCurrentState());
		modelMap.addAttribute("Cities", AvalableCities);
		modelMap.addAttribute("States", statesDAO.getStatesById(employee.getCurrentCountry()));
		countryID = employee.getCurrentCountry();
		modelMap.addAttribute("countryID", countryID);
		modelMap.addAttribute("userList", employeeDAO.getUserType());
		return new ModelAndView("editEmployees", modelMap);

	}

	public ModelMap getShowEmployeeModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Departments> departmentsList = departmentDAO.getDepartment();
		List<Division> divisionList = divisionDAO.getDivisionList();
		List<Designation> designationList = designationDAO.getDesignation();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		List<Location> locationsList = locationDAO.getLocation();
		List<Workspaces> workspacesList = workspacesDAO.getAllWorkspaces();
		List<Countries> countries = countriesDAO.getCountriesList();
		List<Cities> citiesList = citiesDAO.getCities();
		List<States> statesList = statesDAO.getStates();
		List<JobPositions> jobPositionsList = jobPositionDAO.getJobPositions();
		List<Status> listsList = statusDAO.getStatusByCategory("employment_status");
		modelMap.addAttribute("departmentsList", departmentsList);
		modelMap.addAttribute("divisionList", divisionList);
		modelMap.addAttribute("designationList", designationList);
		modelMap.addAttribute("employeeList", employeesList);
		modelMap.addAttribute("locationsList", locationsList);
		modelMap.addAttribute("workspacesList", workspacesList);
		modelMap.addAttribute("countries", countries);
		modelMap.addAttribute("citiesList", citiesList);
		modelMap.addAttribute("statesList", statesList);
		modelMap.addAttribute("jobPositionsList", jobPositionsList);
		modelMap.addAttribute("statusList", listsList);
		modelMap.addAttribute("userList", employeeDAO.getUserType());
		return modelMap;
	}

	@RequestMapping(value = "showEmployees.htm", method = RequestMethod.GET)
	public ModelAndView showEmployees(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Employee employee) {
		return new ModelAndView("showEmployees", getShowEmployeeModelMap());
	}

	@RequestMapping(value = "addEmployees.htm", method = RequestMethod.POST)
	public ModelAndView addEmployees(@RequestParam(value = "photoFile") MultipartFile photoFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Employee employee,
			BindingResult bindingResult) {
		
		int eid=employee.getEmployeeId();
		
		//int lastFiveDigits =num  % 100000;

		//System.out.println("Last 5 digits: " + lastFiveDigits);
		
		String encryptedPassword = null;
		if (null != request.getParameter("state"))
			employee.setCurrentState(Integer.parseInt(request.getParameter("state")));
		if (null != request.getParameter("city"))
			employee.setCurrentCity(Integer.parseInt(request.getParameter("city")));

		int workspaceId = employee.getWorkspace();
		if (workspaceId != 0) {
			Workspaces workspaces = workspacesDAO.getWorkspaceById(workspaceId);
			int departmentId = workspaces.getDepartment();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);

			employee.setLocation(locationId);
			employee.setDivision(divisionId);
			employee.setDepartmentId(departmentId);
		}
		employeeValidator.validate(employee, bindingResult, request);

		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getShowEmployeeModelMap();
			modelMap.addAttribute("EmployeeAddMsg", "Employee Not Added.");
			return new ModelAndView("showEmployees", modelMap);
		} else {
			String password = request.getParameter("password");
			try {
				encryptPassword = new EncryptPassword();
				encryptedPassword = encryptPassword.encrypt(password);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			employee.setPassword(encryptedPassword);
			employeeDAO.saveEmployeeAndGetId(employee);
			int currentId = employee.getEmployeeId();
			String extension = "";
			if (photoFile.getSize() > 0) {
				extension = photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
			}

			String fileName = employee.getFirstName() + "_" + employee.getLastName() + "_" + currentId + extension;
			try {
				Imageload imageload = new Imageload();
				String path = imageload.uploadImage(photoFile, request, "ProfilePhotos", fileName);
				employee.setPhoto("ProfilePhotos/" + fileName);
				employee.setEmployeeNo(currentId);

				if (null != request.getParameter("state"))
					employee.setCurrentState(Integer.parseInt(request.getParameter("state")));
				if (null != request.getParameter("city"))
					employee.setCurrentCity(Integer.parseInt(request.getParameter("city")));

			} catch (IOException e) {

				e.printStackTrace();
			}
			employeeDAO.saveOrUpdateEmployeeAndGetId(employee);
			SendMailTLS sendMailTLS = new SendMailTLS();

			if (null != session.getAttribute("loginUserId")) {
				int senderEmployeeId = (Integer) session.getAttribute("loginUserId");
				Employee senderEmployee = employeeDAO.getEmployeeById(senderEmployeeId);
				String senderEmail = senderEmployee.getEmail();
				String decryptedSenderPasssword = encryptPassword.decrypt(senderEmployee.getPassword());
				String decryptedRecieverPasssword = encryptPassword.decrypt(employee.getPassword());
				String smtpHost = "";
				if (senderEmail.contains("google")) {
					smtpHost = "smtp.mail.google.com";
				} else {
					smtpHost = "smtp." + senderEmail.split("@")[1];
				}
				sendMailTLS.sendMail(senderEmail, decryptedSenderPasssword, smtpHost, "587", employee.getEmail(),
						employee.getFirstName() + " " + employee.getLastName(), decryptedRecieverPasssword);
			}
			return new ModelAndView(new RedirectView("showEmployees.htm"));
		}
	}

	@RequestMapping(value = "additionalEmployeeInfo.htm", method = RequestMethod.POST)
	public ModelAndView additionalEmployeeInfo(@RequestParam("Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("EmpId", id);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeListSize", employeeList.size());
		return new ModelAndView("addEmployeeInfo", modelMap);

	}

	public void saveDownloadedLogsToDatabase() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		/*
		 * startDateAndTimeDateFormat.setHours(startDateAndTimeDateFormat.getHours()-2);
		 * endDateAndTimeDateFormat.setHours(endDateAndTimeDateFormat.getHours()+3);
		 */

		List<AttendanceRecord> attendanceRecordList = attendanceRecordDAO.getAttendanceRecordList();
		for (AttendanceRecord attendanceRecordObj : attendanceRecordList) {
			List<Employee> employeeForAttendance = employeeDAO
					.getEmployeeListByEmployeeNo(attendanceRecordObj.getEmpCode());

			if (employeeForAttendance.size() != 0) {

				Employee attendanceLogsEmployee = employeeForAttendance.get(0);

				int currentEmployeeId = attendanceLogsEmployee.getEmployeeId();
				List<ShiftAllocation> allocatedShiftsToEmployee = shiftAllocationDAO
						.getShiftAllocatedEmployeeList(currentEmployeeId);

				AttendanceLogs attendanceLogs = new AttendanceLogs();

				// int currentMachineId = attendanceRecordObj.getEmpCode();
				// String currentDeviceSerialNo = attendanceRecordObj.getDeviceSerialNo();
				// String currentMachineIp = attendanceRecordObj.getDeviceIP();

				int currentLocation = attendanceLogsEmployee.getLocation();

				String currentDate = attendanceRecordObj.getDate();
				String currentTime = attendanceRecordObj.getTime();
				int currentStatus = attendanceRecordObj.getStatus();
				String currentStatusInString = attendanceRecordObj.getCheckInOut();
				int currentShiftId = 0;
				String currentDateActual = "0";
				int currentWorkId = attendanceLogsEmployee.getEmployeeNo();
				// attendanceLogs.setMachineWorkId(currentMachineId);
				// attendanceLogs.setEquipmentID(currentDeviceSerialNo);
				// attendanceLogs.setFromIp(currentMachineIp);
				attendanceLogs.setLocation(currentLocation);
				attendanceLogs.setRecordDate(currentDate);
				attendanceLogs.setRecordTime(currentTime);
				attendanceLogs.setStatus(currentStatus);
				attendanceLogs.setStatusInString(currentStatusInString);
				if (allocatedShiftsToEmployee.size() != 0) {
					currentShiftId = allocatedShiftsToEmployee.get(0).getShiftid();
					attendanceLogs.setShift(currentShiftId);
				}
				attendanceLogs.setWorkID(currentWorkId);
				attendanceLogsDAO.saveAttendanceLogs(attendanceLogs);// save in attendace logs

				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
				try {
					Date currentDateConverted = format.parse(currentDate);
					currentDateActual = format.format(currentDateConverted.getTime() + MILLIS_IN_DAY);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (currentShiftId != 0
						&& shiftDefinitionDAO.getShiftDefinitionsByShiftId(currentShiftId).size() != 0) {
					ShiftDefinition currentShiftDefinitionObj = shiftDefinitionDAO
							.getShiftDefinitionObjByShiftId(currentShiftId);
					String shiftStartTime = currentShiftDefinitionObj.getTimeField();
					String shiftEndTime = currentShiftDefinitionObj.getEndTime();

					String[] splitForStartTimeAMPM = shiftStartTime.split(":");
					String[] splitForEndTimeAMPM = shiftEndTime.split(":");
					AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();

					if (Integer.parseInt(splitForStartTimeAMPM[0]) > 12
							&& Integer.parseInt(splitForEndTimeAMPM[0]) < 12) {
						attendanceLogsBulkEntry.setOverNight(1);
						attendanceLogsBulkEntry.setEndDateAndTime(currentDateActual + " " + shiftEndTime);
					} else {
						attendanceLogsBulkEntry.setOverNight(0);
						attendanceLogsBulkEntry.setEndDateAndTime(currentDate + " " + shiftEndTime);
					}
					attendanceLogsBulkEntry.setRecordDate(currentDate);
					if (currentStatus == 0) {
						attendanceLogsBulkEntry.setInTime(currentTime);

					} else if (currentStatus == 1) {
						attendanceLogsBulkEntry.setOutTime(currentTime);

					} else if (currentStatus == 2) {
						attendanceLogsBulkEntry.setBreakOut(currentTime);

					} else if (currentStatus == 3) {
						attendanceLogsBulkEntry.setBreakIn(currentTime);
					}

					attendanceLogsBulkEntry.setLocation(currentLocation);
					attendanceLogsBulkEntry.setShift(currentShiftId);
					attendanceLogsBulkEntry.setTimeAsPerShftTimings(currentTime);
					attendanceLogsBulkEntry.setWorkID(currentWorkId);
					attendanceLogsBulkEntry.setStartDateAndTime(currentDate + " " + shiftStartTime);

					try {

						String previouslySetStartDateTime = attendanceLogsBulkEntry.getStartDateAndTime();
						String previouslySetEndDateTime = attendanceLogsBulkEntry.getEndDateAndTime();

						Date startDateAndTimeDateFormat = dateFormat.parse(previouslySetStartDateTime);
						Date endDateAndTimeDateFormat = dateFormat.parse(previouslySetEndDateTime);

						DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
						if (!currentShiftDefinitionObj.getPartialDay().equals("0")) {
							if (startDateAndTimeDateFormat
									.getDay() == Integer.parseInt(currentShiftDefinitionObj.getPartialDay()) - 1) {
								int temp = startDateAndTimeDateFormat.getDay();
								attendanceLogsBulkEntry.setStartDateAndTime(
										currentDate + " " + currentShiftDefinitionObj.getPartialDayStartTime());
								String[] tempPreviousDate = attendanceLogsBulkEntry.getEndDateAndTime().split(" ");
								attendanceLogsBulkEntry.setEndDateAndTime(
										tempPreviousDate[0] + " " + currentShiftDefinitionObj.getPartialDayEndTime());
							}
						}

						previouslySetStartDateTime = attendanceLogsBulkEntry.getStartDateAndTime();
						previouslySetEndDateTime = attendanceLogsBulkEntry.getEndDateAndTime();

						startDateAndTimeDateFormat = dateFormat.parse(previouslySetStartDateTime);
						endDateAndTimeDateFormat = dateFormat.parse(previouslySetEndDateTime);

						if (!currentShiftDefinitionObj.getGraceTime().equals("0")) {
							Date startDateAndTimeDateFormatCopy = startDateAndTimeDateFormat;
							String graceTime = currentShiftDefinitionObj.getGraceTime();
							String[] graceTimeArray = graceTime.split(":");
							startDateAndTimeDateFormat.setHours(
									startDateAndTimeDateFormat.getHours() + Integer.parseInt(graceTimeArray[0]));
							startDateAndTimeDateFormat.setMinutes(
									startDateAndTimeDateFormat.getMinutes() + Integer.parseInt(graceTimeArray[1]));
							startDateAndTimeDateFormat.setSeconds(
									startDateAndTimeDateFormat.getSeconds() + Integer.parseInt(graceTimeArray[2]));
							attendanceLogsBulkEntry
									.setStartDateAndTimeGrace(dateFormat.format(startDateAndTimeDateFormat));
							startDateAndTimeDateFormat = startDateAndTimeDateFormatCopy;
						}
						/*
						 * else { startDateAndTimeDateFormat.setHours(0);
						 * startDateAndTimeDateFormat.setMinutes(0);
						 * startDateAndTimeDateFormat.setSeconds(0);
						 * attendanceLogsBulkEntry.setStartDateAndTimeGrace(dateFormat.format(
						 * startDateAndTimeDateFormat)); }
						 */

						/*
						 * previouslySetStartDateTime=attendanceLogsBulkEntry.getStartDateAndTime();
						 * previouslySetEndDateTime=attendanceLogsBulkEntry.getEndDateAndTime();
						 * 
						 * startDateAndTimeDateFormat = dateFormat.parse(previouslySetStartDateTime);
						 * endDateAndTimeDateFormat = dateFormat.parse(previouslySetEndDateTime);
						 * 
						 * if(!currentShiftDefinitionObj.getPunchBeginBefore().equals("0")) {
						 * 
						 * String punchBeginBefore=currentShiftDefinitionObj.getPunchBeginBefore();
						 * String []punchBeginBeforeArray=punchBeginBefore.split(":");
						 * startDateAndTimeDateFormat.setHours(startDateAndTimeDateFormat.getHours()-
						 * Integer.parseInt(punchBeginBeforeArray[0]));
						 * startDateAndTimeDateFormat.setMinutes(startDateAndTimeDateFormat.getMinutes()
						 * -Integer.parseInt(punchBeginBeforeArray[1]));
						 * startDateAndTimeDateFormat.setSeconds(startDateAndTimeDateFormat.getSeconds()
						 * -Integer.parseInt(punchBeginBeforeArray[2]));
						 * attendanceLogsBulkEntry.setStartDateAndTime(dateFormat.format(
						 * startDateAndTimeDateFormat)); }
						 * if(!currentShiftDefinitionObj.getPunchEndAfter().equals("0")) { String
						 * punchEndAfter=currentShiftDefinitionObj.getPunchEndAfter(); String
						 * []punchEndAfterArray=punchEndAfter.split(":");
						 * endDateAndTimeDateFormat.setHours(endDateAndTimeDateFormat.getHours()+Integer
						 * .parseInt(punchEndAfterArray[0]));
						 * endDateAndTimeDateFormat.setMinutes(endDateAndTimeDateFormat.getMinutes()+
						 * Integer.parseInt(punchEndAfterArray[1]));
						 * endDateAndTimeDateFormat.setSeconds(endDateAndTimeDateFormat.getSeconds()+
						 * Integer.parseInt(punchEndAfterArray[2]));
						 * attendanceLogsBulkEntry.setEndDateAndTime(dateFormat.format(
						 * endDateAndTimeDateFormat)); }
						 */

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrys(attendanceLogsBulkEntry);
					/* attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrysReports(); */
				} else {
					if (currentShiftId == 0) {
						AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();
						attendanceLogsBulkEntry.setOverNight(0);
						attendanceLogsBulkEntry.setEndDateAndTime(currentDate + " 23:59:59");
						attendanceLogsBulkEntry.setRecordDate(currentDate);
						if (currentStatus == 0) {
							attendanceLogsBulkEntry.setInTime(currentTime);
						} else if (currentStatus == 1) {
							attendanceLogsBulkEntry.setOutTime(currentTime);

						} else if (currentStatus == 2) {
							attendanceLogsBulkEntry.setBreakOut(currentTime);
						} else if (currentStatus == 3) {
							attendanceLogsBulkEntry.setBreakIn(currentTime);
						}
						// attendanceLogsBulkEntry.setEquipmentId(currentDeviceSerialNo);
						// attendanceLogsBulkEntry.setFromIp(currentMachineIp);
						attendanceLogsBulkEntry.setLocation(currentLocation);
						attendanceLogsBulkEntry.setShift(currentShiftId);
						attendanceLogsBulkEntry.setTimeAsPerShftTimings(currentTime);
						attendanceLogsBulkEntry.setWorkID(currentWorkId);
						attendanceLogsBulkEntry.setStartDateAndTime(currentDate + " 0:0:0");
						attendanceLogsBulkEntryDAO.saveAttendanceLogsBulkEntrysDefault(attendanceLogsBulkEntry);
					}
				}

				/* } */
			}
		}

	}

	@RequestMapping(value = "downloadLogsToDatebase.htm", method = RequestMethod.POST)
	public void downloadLogsToDatebases(@RequestParam(value = "validIp") String enterIP, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		try {
			System.out.println("Enter Ip" + enterIP);
			int noOfemployees = iAttApp.downloadLogs(enterIP,
					"Server=localhost;port=3306;Database=distna;Uid=root;Pwd=admin");
			System.out.println("No of Employee" + noOfemployees);
			/*
			 * if(noOfemployees==0){
			 * 
			 * } else {
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * System.out.println("In downloadLogs  "); Properties properties=new
		 * Properties(); InputStream
		 * inputStream=AdminController.class.getClassLoader().getResourceAsStream(
		 * "database.properties"); String databaseUserName=""; String
		 * dbEncryptedPassword=""; String dbdencryptedPassword=""; try {
		 * properties.load(inputStream);
		 * databaseUserName=properties.getProperty("database.username");
		 * dbEncryptedPassword=properties.getProperty("database.password");
		 * dbdencryptedPassword=encryptPassword.decrypt(dbEncryptedPassword); } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * PrintWriter out = null; String msgIP="0"; String
		 * testConnectionNativeString="0"; String deviceIpAndSerialNo="0"; try {
		 * out=response.getWriter(); System.out.println("Enter Ip is="+enterIP);
		 * log.info("Before IATTAPP downloadLogsToDatebase"); //IAttApp iAttApp=new
		 * IAttApp("Jawin.AttApp"); log.info("After IATTAPP  downloadLogsToDatebase");
		 * testConnectionNativeString=iAttApp.ViewStatus(enterIP);
		 * log.info("testConnectionNativeString in downloadLogsToDatebase"
		 * +testConnectionNativeString);
		 * System.out.println("Test Connection Native String "
		 * +testConnectionNativeString); if(testConnectionNativeString.equals(enterIP))
		 * { deviceIpAndSerialNo=iAttApp.GetSerial(enterIP);
		 * log.info("deviceIpAndSerialNo in downloadLogsToDatebase"+deviceIpAndSerialNo)
		 * ; System.out.println("Device Ip and serial number="+deviceIpAndSerialNo);
		 * String[] deviceIpAndSerialNoArray=deviceIpAndSerialNo.split("~");
		 * System.out.println("Device Serial no array="+deviceIpAndSerialNoArray[1]);
		 * List<AllowedDevices> allowedDevicesList=
		 * allowedDevicesDAO.getAllowedDevices(); boolean serialNoFlag=false;
		 * if(deviceIpAndSerialNoArray[0].equals(enterIP)) { for(AllowedDevices
		 * allowedDevices:allowedDevicesList) {
		 * System.out.println("Allowed devices="+allowedDevices);
		 * System.out.println("Serial No="+allowedDevices.getSerialNo()
		 * +" and deviceIPandserial[1]"+deviceIpAndSerialNoArray[1]);
		 * if(allowedDevices.getSerialNo().equals(deviceIpAndSerialNoArray[1])) {
		 * serialNoFlag=true; }
		 * 
		 * } } else { out.write(deviceIpAndSerialNo); System.out.println("else part"); }
		 * System.out.println("SerialNOFlag"+serialNoFlag); if(serialNoFlag) {
		 * System.out.println( "Dpownload Enter IP"+enterIP); int
		 * noOfemployees=iAttApp.downloadLogs(enterIP,
		 * "Server=localhost;port=3306;Database=distna;Uid="+databaseUserName+";Pwd="+
		 * dbdencryptedPassword); System.out.println( "No of Employee"+noOfemployees);
		 * 
		 * if(noOfemployees==0) { noOfemployees=iAttApp.downloadLogsVer9(enterIP,
		 * "Server=localhost;port=3306;Database=distna;Uid="+databaseUserName+";Pwd="+
		 * dbdencryptedPassword); }
		 * 
		 * 
		 * if(noOfemployees!=0) { saveDownloadedLogsToDatabase(); //
		 * testConnectionNativeString=iAttApp.DisConnect(enterIP);
		 * System.out.println("after disconnect");
		 * out.write(testConnectionNativeString); } else {
		 * out.write("Operation not successfull"); } } else {
		 * if(deviceIpAndSerialNoArray[0].equals(enterIP)) {
		 * out.write("Device Serial Number Not Valid."); } } } else {
		 * out.write(testConnectionNativeString); } } catch (COMException ex) {
		 * ex.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
	}

	@RequestMapping(value = "showRestoreLogs.htm", method = RequestMethod.GET)
	public ModelAndView showRestoreLogs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			AddDevice addDevice) {
		return new ModelAndView("showrestorelogs");
	}

	@RequestMapping(value = "showInnerRestore.htm", method = RequestMethod.POST)
	public ModelAndView showInnerRestore(@RequestParam(value = "restoreType") String restoreType,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, AddDevice addDevice) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("restoreType", restoreType);
		return new ModelAndView("showinnerrestore", modelMap);
	}

	@RequestMapping(value = "restoreLogs.htm", method = RequestMethod.POST)
	public ModelAndView restoreLogs(@RequestParam(value = "selectFile") MultipartFile selectFile,
			@RequestParam(value = "restoreType") String restoreType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, AddDevice addDevice) {

		Properties properties = new Properties();
		InputStream inputStream = AdminController.class.getClassLoader().getResourceAsStream("database.properties");
		String databaseUserName = "";
		String dbEncryptedPassword = "";
		String dbdencryptedPassword = "";
		try {
			properties.load(inputStream);
			databaseUserName = properties.getProperty("database.username");
			dbEncryptedPassword = properties.getProperty("database.password");
			dbdencryptedPassword = encryptPassword.decrypt(dbEncryptedPassword);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		shiftAllocationDAO.clearAttRecords();
		String status = "";
		String extention = null;
		ModelMap modelMap = new ModelMap();
		extention = selectFile.getOriginalFilename().substring(selectFile.getOriginalFilename().indexOf("."));
		if (selectFile.getSize() > 0 && extention.equals(".dat")) {
			String destination = request.getSession().getServletContext().getRealPath("") + "\\DatFiles";
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(destination + "\\" + selectFile.getOriginalFilename());
			try {
				if (!file.exists()) {
					file.createNewFile();
				}

				selectFile.transferTo(file);
				IDatFileRead iDatFileRead = new IDatFileRead("Jawin.DatFileRead");
				if (restoreType.equals("users"))
					status = iDatFileRead.ReadUserLogFromDatFile(file.getAbsolutePath(),
							"Server=localhost;port=3306;Database=distna;Uid=" + databaseUserName + ";Pwd="
									+ dbdencryptedPassword);
				else if (restoreType.equals("attLogs")) {
					status = iDatFileRead.ReadAttLogFromDatFile(file.getAbsolutePath(),
							"Server=localhost;port=3306;Database=distna;Uid=" + databaseUserName + ";Pwd="
									+ dbdencryptedPassword);
					if (status.split("~")[0].trim().equalsIgnoreCase("success")) {

						saveDownloadedLogsToDatabase();
					}
				}
				if (status.split("~")[0].trim().equalsIgnoreCase("success")) {
					modelMap.addAttribute("status", "Operation Successfull");
				}
			} catch (COMException e) {
				// TODO Auto-generated catch block
				modelMap.addAttribute("status", "Operation Not Successfull.Please Select a Proper Dat File.");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				modelMap.addAttribute("status", "Operation Not Successfull.Please Select a Proper Dat File.");
			}
		} else {
			modelMap.addAttribute("status", "Operation Not Successfull.Please Select a Proper Dat File.");
		}
		return new ModelAndView("showrestorelogs", modelMap);
	}

	public ModelMap getEmployeeWiseReportModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("EmployeeList", employeesList);
		modelMap.addAttribute("ErrorMsg", " ");
		return modelMap;
	}

	@RequestMapping(value = "showEmployeeWiseReport.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeeWiseReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showEmployeeWiseReport", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "showEmployeeWiseAllPunchesReport.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeeWiseAllPunchesReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showemployeewiseallpunchesreport", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "generateleavereport.htm", method = RequestMethod.POST)
	public ModelAndView generateLeaveReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		int workId = 0;
		modelMap.addAttribute("ErrorMsg", " ");
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		int departmentId = Integer.parseInt(request.getParameter("hiddenDepartmentId"));
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		try {
			if (dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) {
				List<Departments> departmentList = departmentDAO.getDepartment();
				modelMap.addAttribute("departmentList", departmentList);
				modelMap.addAttribute("ErrorMsg", "Start Date and End Date not Valid");
				return new ModelAndView("leavereport", modelMap);

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
				jasperPath = path + "LeaveReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "LeaveReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "LeaveReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
				rs.next();
				System.out.println(rs.getString(3));
				jasperPath = path + "LeaveReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "LeaveReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "LeaveReportSingle.xls"));
			} else {
				rs = reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
				rs.next();
				System.out.println(rs.getString(3));
				jasperPath = path + "LeaveReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\" + "LeaveReport.pdf";
				exportPathXLS = new FileOutputStream(new File(
						request.getSession().getServletContext().getRealPath("reportsXLS") + "\\" + "LeaveReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Leave Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();
			System.out.println("Generate Leave success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		return new ModelAndView(new RedirectView("showleavereport.htm"));
	}

	@RequestMapping(value = "generateEmployeeWiseReport.htm", method = RequestMethod.POST)
	public void generateEmployeeWiseReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("ErrorMsg", " ");
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		/*
		 * try { if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) {
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("EmployeeList",employeeList);
		 * modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid"); return
		 * new ModelAndView("showEmployeeWiseReport",modelMap);
		 * 
		 * } } catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseReportMultiple.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseReportSingle.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseReportSingle.xls"));
			} else {
				rs = reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Employee Wise report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());
			// JasperViewer.viewReport(jasperPrint, false);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}

	@RequestMapping(value = "generateEmployeeWiseaAllPunchesReport.htm", method = RequestMethod.POST)
	public void generateEmployeeWiseaAllPunchesReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("ErrorMsg", " ");
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo, empReportType, selectedcheckbox);
				jasperPath = path + "EmployeeWiseAll.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseAllPunchesReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseAllPunchesReportMultiple.xls"));
			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo, empReportType, selectedcheckbox);
				jasperPath = path + "EmployeeWiseAll.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseAllPunchesReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseAllPunchesReportSingle.xls"));
			} else {
				rs = reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo, empReportType, selectedcheckbox);
				jasperPath = path + "EmployeeWiseAll.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseAllPunchesReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseAllPunchesReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Employee Wise report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}

	@RequestMapping(value = "showEmployeeWiseReportsPdf.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeeWiseReportsPdf(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showEmployeeWiseReportsPdf", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "showEmployeeTypeView.htm", method = RequestMethod.POST)
	public ModelAndView showEmployeeTypeView(@RequestParam(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		/*
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("EmployeeList",employeeList);
		 */
		if (type.equals("multiple")) {
			return new ModelAndView("showEmployeeTypeViewMultiple", getEmployeeWiseReportModelMap());
		} else if (type.equals("single")) {
			return new ModelAndView("showEmployeeTypeViewSingle", getEmployeeWiseReportModelMap());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "showDepartmentTypeView.htm", method = RequestMethod.POST)
	public ModelAndView showDepartmentTypeView(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "type") String type, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		List<Employee> employeeList = employeeDAO.getEmployeeListByDepartment(departmentId);
		modelMap.addAttribute("EmployeeList", employeeList);
		if (type.equals("multiple")) {
			return new ModelAndView("showEmployeeTypeViewMultiple", modelMap);
		} else if (type.equals("single")) {
			return new ModelAndView("showEmployeeTypeViewSingle", modelMap);
		} else {
			return null;
		}
	}

	public ModelMap getDepartmentWiseReportModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeesList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("EmployeeList", employeesList);

		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);

		modelMap.addAttribute("ErrorMsg", " ");
		return modelMap;
	}

	@RequestMapping(value = "showdepartmentwisereport.htm", method = RequestMethod.GET)
	public ModelAndView showDepartmentWiseReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("departmentwisereport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "showDepartmentOutForWorkReport.htm", method = RequestMethod.GET)
	public ModelAndView showDepartmentOutForWorkReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showDepartmentOutForWorkReport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "showDepartmentSummaryReport.htm", method = RequestMethod.GET)
	public ModelAndView showDepartmentSummaryReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("departmentsummaryreport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "generateDepartmentSummaryReport.htm", method = RequestMethod.GET)
	public void generateDepartmentSummaryReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws SQLException {
		int department_id = Integer.parseInt(request.getParameter("department"));
		String date = request.getParameter("datefrom");
		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			rs = reportDAO.getDepartmentSummaryReport(department_id, date);
			jasperPath = path + "DepartmentSummaryReport.jrxml";
			exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
					+ "DepartmentSummaryReport.pdf";
			exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "DepartmentSummaryReport.xls"));
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Department Summary report");
			parameters.put("date", date);
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}
		// return new ModelAndView(new RedirectView("showDepartmentSummaryReport.htm"));
	}

	@RequestMapping(value = "totalWorkingHoursReport.htm", method = RequestMethod.GET)
	public ModelAndView totalWorkingHoursReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("totalworkinghoursreport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "displaydepartmentwiseemployeelist.htm", method = RequestMethod.POST)
	public ModelAndView displayDepartmentWiseEmployeeList(@RequestParam("departmentId") int departmentId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDepartmentWiseReportModelMap();

		List<Employee> employeeListDept = employeeDAO.getEmployeeListByDepartment(departmentId);
		modelMap.addAttribute("employeeListDept", employeeListDept);
		modelMap.addAttribute("departmentId", departmentId);

		return new ModelAndView("departmentwiseemployeelist", modelMap);
	}

	@RequestMapping(value = "getdepartmentwiseemployeelist.htm", method = RequestMethod.POST)
	public ModelAndView getDepartmentWiseEmployeeList(@RequestParam("departmentId") String departmentId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDepartmentWiseReportModelMap();

		List<Employee> employeeListDept = employeeDAO.getEmployeeListByDepartment(Integer.parseInt(departmentId));
		modelMap.addAttribute("employeeListDept", employeeListDept);
		modelMap.addAttribute("departmentId", departmentId);

		return new ModelAndView("departmentwiseemployeelistleave", modelMap);
	}

	@RequestMapping(value = "getdepartmentwisesingleemployeelist.htm", method = RequestMethod.POST)
	public ModelAndView getDepartmentWiseSingleEmployeeList(@RequestParam("departmentId") String departmentId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDepartmentWiseReportModelMap();

		List<Employee> employeeListDept = employeeDAO.getEmployeeListByDepartment(Integer.parseInt(departmentId));
		modelMap.addAttribute("EmployeeList", employeeListDept);
		modelMap.addAttribute("departmentId", departmentId);

		return new ModelAndView("showEmployeeTypeViewSingle", modelMap);
	}

	@RequestMapping(value = "generatedepartmentwisereport.htm", method = RequestMethod.POST)
	public ModelAndView generateDepartmentWiseReport(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReportSingle.xls"));
			} else {
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Department Wise Logs report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}

	@RequestMapping(value = "generatedepartmentwiseOutForWorkreport.htm", method = RequestMethod.POST)
	public void generatedepartmentwiseOutForWorkreport(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "OutForWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "OutForWorkReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "OutForWorkReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "OutForWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "OutForWorkReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "OutForWorkReportSingle.xls"));
			} else {
				rs = reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);
				jasperPath = path + "OutForWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "OutForWorkReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "OutForWorkReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Department Wise Out For Work report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}
///*
//	@RequestMapping(value = "departmentwiseshortworkreport.htm", method = RequestMethod.GET)
//	public ModelAndView departmentWiseShortWorkReport(HttpServletRequest request, HttpServletResponse response,
//			HttpSession session) {
//		return new ModelAndView("shortworkreport", getDepartmentWiseReportModelMap());
//	}
//
//	@RequestMapping(value = "generatedepartmentwiseshortworkreport.htm", method = RequestMethod.POST)
//	public void generateDepartmentWiseShortWorkReport(@RequestParam(value = "departmentId") int departmentId,
//			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
//			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
//			HttpServletResponse response, HttpSession session) throws SQLException {
//		ModelMap modelMap = getDepartmentWiseReportModelMap();
//		int  employeeId= 0;
//		String dateFrom = request.getParameter("datefrom");
//		String dateTo = request.getParameter("dateTo");
//		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
//
//		ServletOutputStream servletOutputStream = null;
//		String jasperPath = null;
//		String exportPath = null;
//		OutputStream exportPathXLS = null;
//		try {
//			servletOutputStream = response.getOutputStream();
//			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
//
//			if (empReportType.equals("multiple")) {
//				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
//						0);
//				jasperPath = path + "DepartmentWiseReport.jrxml";
//				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
//						+ "DepartmentWiseReportMultiple.pdf";
//				exportPathXLS = new FileOutputStream(
//						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
//								+ "DepartmentWiseReportMultiple.xls"));
//
//			} else if (empReportType.equals("single")) {
//				employeeId = Integer.parseInt(request.getParameter("employeeNo"));
//				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
//						0);
//				jasperPath = path + "DepartmentWiseReport.jrxml";
//				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
//						+ "DepartmentWiseReportSingle.pdf";
//				exportPathXLS = new FileOutputStream(
//						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
//								+ "DepartmentWiseReportSingle.xls"));
//			} else {
//				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
//						departmentId);
//				jasperPath = path + "DepartmentWiseReport.jrxml";
//				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
//						+ "DepartmentWiseReport.pdf";
//				exportPathXLS = new FileOutputStream(
//						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
//								+ "DepartmentWiseReport.xls"));
//			}
//
//			rs.beforeFirst();
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("Report_Title", "Department Wise Logs report");
//			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
//			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
//			InputStream inputStream = new FileInputStream(jasperPath);
//			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
//			jasperDesign = JRXmlLoader.load(inputStream);
//			jasperReport = JasperCompileManager.compileReport(jasperDesign);
//			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
//			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
//			response.setContentType("application/pdf");
//			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//
//			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
//			JRXlsExporter exporterXLS = new JRXlsExporter();
//			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
//			exporterXLS.exportReport();
//			exportPathXLS.write(outputByteArray.toByteArray());
//
//			servletOutputStream.flush();
//			servletOutputStream.close();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JRException e) {
//			e.printStackTrace();
//			StringWriter stringWriter = new StringWriter();
//			PrintWriter printWriter = new PrintWriter(stringWriter);
//			e.printStackTrace(printWriter);
//			response.setContentType("text/plain");
//		}
//
////		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
//	}*/
//

	@RequestMapping(value = "generateTotalWorkingHoursReport.htm", method = RequestMethod.POST)
	public ModelAndView generateTotalWorkingHoursReport(
			@RequestParam(value = "workingHoursType") String workingHoursType,
			@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						0);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReportSingle.xls"));
			} else {
				rs = reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);
				jasperPath = path + "DepartmentWiseReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "DepartmentWiseReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "DepartmentWiseReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Department Wise Logs report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}

	/*
	 * public void generateDepartmentWiseReport(HttpServletRequest
	 * request,HttpServletResponse response, HttpSession session) throws
	 * SQLException { int
	 * departmentId=Integer.parseInt(request.getParameter("department")); String
	 * dateFrom=request.getParameter("datefrom"); String
	 * dateTo=request.getParameter("dateTo");
	 * 
	 * ServletOutputStream servletOutputStream = null; try { servletOutputStream =
	 * response.getOutputStream();
	 * 
	 * String
	 * path=request.getSession().getServletContext().getRealPath("jasperReports")+
	 * "\\"; String
	 * exportPath=request.getSession().getServletContext().getRealPath("reports") +
	 * "\\"; ResultSet rs=reportDAO.getDepartmentWiseEmployeeList(departmentId,
	 * dateFrom, dateTo); String jasperPath=path+"DepartmentWiseReport.jrxml";
	 * Map<String, Object> parameters = new HashMap<String, Object>();
	 * parameters.put("reportTitle","Department Wise Logs report");
	 * rs.beforeFirst(); JRResultSetDataSource resultSetDataSource = new
	 * JRResultSetDataSource(rs); InputStream inputStream = new
	 * FileInputStream(jasperPath); //jasperDesign =
	 * JasperManager.loadXmlDesign(inputStream); jasperDesign =
	 * JRXmlLoader.load(inputStream); jasperReport =
	 * JasperCompileManager.compileReport(jasperDesign); jasperPrint =
	 * JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	 * JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+
	 * "DepartmentWiseReport.pdf"); response.setContentType("application/pdf");
	 * JasperExportManager.exportReportToPdfStream(jasperPrint,
	 * servletOutputStream); servletOutputStream.flush();
	 * servletOutputStream.close();
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (JRException e) { e.printStackTrace();
	 * StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new
	 * PrintWriter(stringWriter); e.printStackTrace(printWriter);
	 * response.setContentType("text/plain"); }
	 * 
	 * //return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm")); }
	 */

	public ModelMap getDailyAttendenceReportModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		modelMap.addAttribute("ErrorMsg", " ");
		return modelMap;
	}

	@RequestMapping(value = "showdailyattendancereport.htm", method = RequestMethod.GET)
	public ModelAndView showDailyAttendanceReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("dailyattendancereport", getDailyAttendenceReportModelMap());
	}

	@RequestMapping(value = "dailyattendancereport.htm", method = RequestMethod.POST)
	public void generateDailyAttendance(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws SQLException {
		ModelMap modelMap = getDailyAttendenceReportModelMap();
		// modelMap.addAttribute("ErrorMsg"," ");
		int employeeId = Integer.parseInt(request.getParameter("dailyattendance"));
		String employeeName = employeeDAO.getEmployeeById(employeeId).getFirstName() + " "
				+ employeeDAO.getEmployeeById(employeeId).getLastName();
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String shiftTime[] = shiftAllocationDAO.getShiftStartEndTime(employeeId);

		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		/*
		 * try { if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) {
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("employeeList", employeeList);
		 * modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid"); return
		 * new ModelAndView("dailyattendancereport",modelMap);
		 * 
		 * } } catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();

			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "DailyAttendanceReport.xls"));
			ResultSet rs = reportDAO.getDailyAttendanceReport(employeeId, dateFrom, dateTo, shiftTime[0], shiftTime[1]);
			String jasperPath = path + "DailyAttendanceReport.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportTitle", "Daily Attendance Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("employeeName", employeeName);
			rs.beforeFirst();

			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "DailyAttendanceReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showdailyattendancereport.htm"));
	}

	@RequestMapping(value = "showemployeedetailsreport.htm", method = RequestMethod.GET)
	public void showEmployeeDetailsReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();

			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "EmployeePersonalDetailsReport.xls"));
			ResultSet rs = reportDAO.getEmployeePersonalDetailsReport();
			String jasperPath = path + "EmployeePersonalDetailsReport.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportTitle", "Employee Personal Details");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			rs.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "EmployeePersonalDetailsReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "showleavereport.htm", method = RequestMethod.GET)
	public ModelAndView showLeaveReport(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		modelMap.addAttribute("ErrorMsg", " ");
		return new ModelAndView("leavereport", modelMap);
	}

	/*
	 * @RequestMapping(value="generatedepartmentwisereport.htm",method=RequestMethod
	 * .POST) public void
	 * generateDepartmentWiseReport(@RequestParam(value="selectedcheckbox") String
	 * selectedcheckbox,@RequestParam(value="empReportType") String empReportType,
	 * HttpServletRequest request,HttpServletResponse response, HttpSession session)
	 * throws SQLException { int
	 * departmentId=Integer.parseInt(request.getParameter("department")); String
	 * dateFrom=request.getParameter("datefrom"); String
	 * dateTo=request.getParameter("dateTo");
	 * 
	 * ServletOutputStream servletOutputStream = null; try { servletOutputStream =
	 * response.getOutputStream();
	 * 
	 * String
	 * path=request.getSession().getServletContext().getRealPath("jasperReports")+
	 * "\\"; String
	 * exportPath=request.getSession().getServletContext().getRealPath("reports") +
	 * "\\"; ResultSet rs=reportDAO.getDepartmentWiseEmployeeList(departmentId,
	 * dateFrom, dateTo, ); String jasperPath=path+"DepartmentWiseReport.jrxml";
	 * Map<String, Object> parameters = new HashMap<String, Object>();
	 * parameters.put("Report_Title","Department Wise Logs report");
	 * rs.beforeFirst(); JRResultSetDataSource resultSetDataSource = new
	 * JRResultSetDataSource(rs); InputStream inputStream = new
	 * FileInputStream(jasperPath); //jasperDesign =
	 * JasperManager.loadXmlDesign(inputStream); jasperDesign =
	 * JRXmlLoader.load(inputStream); jasperReport =
	 * JasperCompileManager.compileReport(jasperDesign); jasperPrint =
	 * JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	 * JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+
	 * "DepartmentWiseReport.pdf"); response.setContentType("application/pdf");
	 * JasperExportManager.exportReportToPdfStream(jasperPrint,
	 * servletOutputStream); servletOutputStream.flush();
	 * servletOutputStream.close();
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (JRException e) { e.printStackTrace();
	 * StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new
	 * PrintWriter(stringWriter); e.printStackTrace(printWriter);
	 * response.setContentType("text/plain"); } //return new ModelAndView(new
	 * RedirectView("showEmployeeWiseReport.htm")); }
	 */

	public ModelMap getshiftWiseReportModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("shiftMasterList", shiftMasterDAO.getShiftMasterList());
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		modelMap.addAttribute("ErrorMsg", " ");
		return modelMap;
	}

	@RequestMapping(value = "shiftWiseDailyReport.htm", method = RequestMethod.GET)
	public ModelAndView shiftWiseDailyReport(@RequestParam(value = "reportType") String reportType,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getshiftWiseReportModelMap();
		modelMap.addAttribute("reportType", reportType);
		return new ModelAndView("shiftwisedailyreport", modelMap);
	}

	@RequestMapping(value = "showShiftWiseEmployeeDiv.htm", method = RequestMethod.POST)
	public ModelAndView showShiftWiseEmployeeDiv(@RequestParam(value = "shiftId") int shiftId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<ShiftAllocation> shiftAllocationList = shiftAllocationDAO.getShiftAllocatedByShiftId(shiftId);
		List<Employee> employeesList = new ArrayList<Employee>();
		if (shiftAllocationList.size() != 0) {
			for (ShiftAllocation shiftAllocationObj : shiftAllocationList) {
				employeesList.add(employeeDAO.getEmployeeById(shiftAllocationObj.getEmployee_id()));
			}
		}
		modelMap.addAttribute("employeesList", employeesList);
		modelMap.addAttribute("shiftId", shiftId);
		return new ModelAndView("showshiftwiseemployeediv", modelMap);
	}

	@RequestMapping(value = "generateShiftWiseReport.htm", method = RequestMethod.POST)
	public void generateShiftWiseReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType,
			@RequestParam(value = "reportType") String reportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getshiftWiseReportModelMap();
		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String allowedTime = "00:00:00";
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		modelMap.addAttribute("reportType", reportType);
		// modelMap.addAttribute("shiftMasterList",
		// shiftMasterDAO.getShiftMasterList());
		/*
		 * try { if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) { //
		 * modelMap.addAttribute("shiftMasterList",
		 * shiftMasterDAO.getShiftMasterList()); modelMap.addAttribute("reportType",
		 * reportType);
		 * modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid"); //
		 * return new ModelAndView("shiftwisedailyreport",modelMap); }
		 * 
		 * } catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		int workId = 0;

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = null;
			OutputStream exportPathXLS = null;
			String jasperPath = null;
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (reportType.equals("InOut")) {
				jasperPath = path + "ShiftWiseReport.jrxml";
				parameters.put("Report_Title", "Shift Wise In/Out Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseInOutReport.xls"));

				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseInOutReport.xls"));
				} else {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseInOutReport.xls"));
				}
			}

			else if (reportType.equals("BreakInOut")) {
				jasperPath = path + "ShiftWiseBreakInOutReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Break In/Out Report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseBreakInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseBreakInOutReport.xls"));

				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseBreakInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseBreakInOutReport.xls"));
				} else {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseBreakInOutReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseBreakInOutReport.xls"));
				}

			}

			else if (reportType.equals("EarlyComing")) {
				jasperPath = path + "ShiftWiseEarlyComingReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Early Coming Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseEarlyComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseEarlyComingReport.xls"));

				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseEarlyComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseEarlyComingReport.xls"));
				} else {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseEarlyComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseEarlyComingReport.xls"));
				}
			} else if (reportType.equals("LateComing")) {
				jasperPath = path + "ShiftWiseLateComingReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Late Coming Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (null != request.getParameter("allowedTime"))
					allowedTime = request.getParameter("allowedTime");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseLateComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseLateComingReport.xls"));
				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseLateComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseLateComingReport.xls"));
				} else {

					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseLateComingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseLateComingReport.xls"));
				}

			} else if (reportType.equals("EarlyGoing")) {
				jasperPath = path + "ShiftWiseEarlyGoingReport.jrxml";
				parameters.put("Report_Title", " Early Going Logs ");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (null != request.getParameter("allowedTime"))
					allowedTime = request.getParameter("allowedTime");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseEarlyGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseEarlyGoingReport.xls"));

				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseEarlyGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseEarlyGoingReport.xls"));
				} else {

					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseEarlyGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseEarlyGoingReport.xls"));
				}

			} else if (reportType.equals("LateGoing")) {
				jasperPath = path + "ShiftWiseLateGoingReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Late Going Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseLateGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseLateGoingReport.xls"));

				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseLateGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseLateGoingReport.xls"));
				} else {

					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseLateGoingReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseLateGoingReport.xls"));
				}

			} else if (reportType.equals("Absent")) {
				jasperPath = path + "ShiftWiseAbsentReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Absent Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWiseAbsentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWiseAbsentReport.xls"));
				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWiseAbsentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWiseAbsentReport"));
				} else {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWiseAbsentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWiseAbsentReport.xls"));
				}

			} else if (reportType.equals("Present")) {
				jasperPath = path + "ShiftWisePresentReport.jrxml";
				parameters.put("Report_Title", "Shift Wise Present Logs report");
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				if (empReportType.equals("multiple")) {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "MultipleShiftWisePresentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "MultipleShiftWisePresentReport.xls"));
				} else if (empReportType.equals("single")) {
					workId = Integer.parseInt(request.getParameter("employeeNo"));
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "SingleShiftWisePresentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "SingleShiftWisePresentReport.xls"));
				} else {
					exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
							+ "ShiftWisePresentReport.pdf";
					exportPathXLS = new FileOutputStream(
							new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
									+ "ShiftWisePresentReport.xls"));
				}
			} else {

			}
			ResultSet rs = reportDAO.getShiftWiseEmployeeList(shiftId, dateFrom, dateTo, empReportType,
					selectedcheckbox, workId, reportType, allowedTime);
			rs.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);

			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();

			// coding For Excel:
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		/* return new ModelAndView("shiftwisedailyreport",modelMap); */
	}

	@RequestMapping(value = "showShiftTypeView.htm", method = RequestMethod.POST)
	public ModelAndView showShiftTypeView(@RequestParam(value = "type") String type,
			@RequestParam(value = "shiftId") int shiftId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		/*
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("EmployeeList",employeeList);
		 */
		ModelMap modelMap = new ModelMap();
		List<ShiftAllocation> shiftAllocationList = shiftAllocationDAO.getShiftAllocatedByShiftId(shiftId);
		List<Employee> employeeList = new ArrayList<Employee>();
		if (shiftAllocationList.size() != 0) {
			for (ShiftAllocation shiftAllocationObj : shiftAllocationList) {
				employeeList.add(employeeDAO.getEmployeeById(shiftAllocationObj.getEmployee_id()));
			}
		}
		modelMap.addAttribute("EmployeeList", employeeList);
		modelMap.addAttribute("ErrorMsg", " ");
		if (type.equals("multiple")) {
			return new ModelAndView("showEmployeeTypeViewMultiple", modelMap);
		} else if (type.equals("single")) {
			return new ModelAndView("showEmployeeTypeViewSingle", modelMap);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "monthlyAttendanceReport.htm", method = RequestMethod.GET)
	public ModelAndView monthlyAttendanceReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("EmployeeList", employeeList);
		return new ModelAndView("monthlyattendancereport", modelMap);
	}

	@RequestMapping(value = "generateMonthlyAttendanceReport.htm", method = RequestMethod.POST)
	public void generateMonthlyAttendanceReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		int employeeNo = 0;
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = null;
			OutputStream exportPathXLS = null;

			if (empReportType.equals("multiple")) {
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MultipleEmployeesMonthlyAttendanceReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MultipleEmployeesMonthlyAttendanceReport.xls"));

			} else if (empReportType.equals("single")) {
				employeeNo = Integer.parseInt(request.getParameter("employeeNo"));
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "SingleEmployeeMonthlyAttendanceReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "SingleEmployeeMonthlyAttendanceReport.xls"));
			} else {
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MonthlyAttendanceReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MonthlyAttendanceReport.xls"));
			}
			ResultSet rs = reportDAO.getMonthlyAttendanceOfEmployee(employeeNo, month, year, empReportType,
					selectedcheckbox);
			String jasperPath = path + "MonthlyAttendanceReport.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Monthly Attendance Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			rs.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}
	}

	@RequestMapping(value = "showleavesperemployee.htm", method = RequestMethod.GET)
	public ModelAndView showLeavesPerEmployee(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		leavesPerEmployee.createDataset(request);
		return new ModelAndView("leavesperemployee", modelMap);
	}

	@RequestMapping(value = "memoreport.htm", method = RequestMethod.GET)
	public ModelAndView memoReport(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Departments> departmentsList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentsList", departmentsList);
		return new ModelAndView("memoreport", modelMap);
	}

	@RequestMapping(value = "generatememoreport.htm", method = RequestMethod.POST)
	public void generateMemoReport(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int employeeId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				jasperPath = path + "MemoReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MemoReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MemoReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				employeeId = Integer.parseInt(request.getParameter("employeeNo"));
				System.out.println("Employee No" + employeeId);
				rs = reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				while (rs.next()) {
					System.out.println("" + rs.getString(2));
				}
				jasperPath = path + "MemoReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MemoReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MemoReportSingle.xls"));
			} else {
				rs = reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);

				jasperPath = path + "MemoReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MemoReportAll.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MemoReportAll.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Memo Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

//		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "showleavesperleavetype.htm", method = RequestMethod.GET)
	public ModelAndView showLeavesPerLeaveType(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// Map<Integer, Integer> leavesPerType=chartDAO.getLeavesPerLeaveType();

		leavesPerLeaveType.createDataset(request);

		return new ModelAndView("leavesperleavetype", modelMap);
	}

	@RequestMapping(value = "shiftWiseMusterReport.htm", method = RequestMethod.GET)
	public ModelAndView shiftWiseMusterReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// modelMap.addAttribute("shiftMasterList",
		// shiftMasterDAO.getShiftMasterList());

		return new ModelAndView("shiftwisemusterreport", getshiftWiseReportModelMap());
	}

	@RequestMapping(value = "basicWorkDurationReport.htm", method = RequestMethod.GET)
	public ModelAndView basicWorkDurationReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// modelMap.addAttribute("shiftMasterList",
		// shiftMasterDAO.getShiftMasterList());
		return new ModelAndView("basicworkdurationreport", getshiftWiseReportModelMap());
	}

	@RequestMapping(value = "showemployeesperdepartment.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeesPerDepartment(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		employeesPerDepartment.createDataset(request);

		return new ModelAndView("employeesperdepartment", modelMap);
	}

	@RequestMapping(value = "showweeklytimecard.htm", method = RequestMethod.GET)
	public ModelAndView showWeeklyTimecard(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		/*
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("employeeList", employeeList);
		 */

		return new ModelAndView("weeklytimecard", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "showweeklytimecardImage.htm", method = RequestMethod.GET)
	public ModelAndView showweeklytimecardImage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		/*
		 * List<Employee> employeeList=employeeDAO.getEmployeeList();
		 * modelMap.addAttribute("employeeList", employeeList);
		 */
		return new ModelAndView("weeklytimecardtimecard", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "weeklytimecard.htm", method = RequestMethod.POST)
	public ModelAndView weeklyTimecard(@RequestParam("empId") int empId, @RequestParam("dateFrom") String dateFrom,
			@RequestParam("dateTo") String dateTo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		modelMap.addAttribute("ErrorMsg", "none");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		try {
			if (dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) {
				/*
				 * List<Employee> employeeList=employeeDAO.getEmployeeList();
				 * modelMap.addAttribute("employeeList", employeeList);
				 */
				modelMap.addAttribute("ErrorMsg", "Start Date and End Date not Valid");
				return new ModelAndView("weeklytimecarddiv", modelMap);

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weeklyTimecard.createDemoPanel(request, empId, dateFrom, dateTo);
		return new ModelAndView("weeklytimecarddiv");
	}

	@RequestMapping(value = "weeklytimecardGet.htm", method = RequestMethod.GET)
	public ModelAndView weeklytimecardGet(@RequestParam("empId") int empId, @RequestParam("dateFrom") String dateFrom,
			@RequestParam("dateTo") String dateTo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		modelMap.addAttribute("ErrorMsg", "none");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		try {
			if (dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo))) {
				/*
				 * List<Employee> employeeList=employeeDAO.getEmployeeList();
				 * modelMap.addAttribute("employeeList", employeeList);
				 */
				modelMap.addAttribute("ErrorMsg", "Start Date and End Date not Valid");
				return new ModelAndView("weeklytimecarddiv", modelMap);

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weeklyTimecard.createDemoPanel(request, empId, dateFrom, dateTo);
		return new ModelAndView("weeklytimecarddiv");
	}

	@RequestMapping(value = "generateShiftWiseMusterReport.htm", method = RequestMethod.POST)
	public void generateShiftWiseMusterReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		String monthName = "invalid";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		monthName = months[Integer.parseInt(request.getParameter("iMonth"))];

		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		if (shiftId != 0) {
			ServletOutputStream servletOutputStream = null;
			try {
				servletOutputStream = response.getOutputStream();
				String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
				String exportPath = null;
				OutputStream exportPathXLS = null;
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShiftWiseMusterReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShiftWiseMusterReport.xls"));

				ResultSet rs = reportDAO.getmusterReportOfEmployees(shiftId, month, year);
				String jasperPath = path + "ShiftWiseMusterReport.jrxml";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("MonthName", monthName);
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				parameters.put("YearName", year);
				parameters.put("Report_Title", "Shift Wise Muster Report");
				rs.beforeFirst();
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				InputStream inputStream = new FileInputStream(jasperPath);
				// jasperDesign = JasperManager.loadXmlDesign(inputStream);
				jasperDesign = JRXmlLoader.load(inputStream);
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

				ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
				exporterXLS.exportReport();
				exportPathXLS.write(outputByteArray.toByteArray());

				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JRException e) {
				e.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "generateBasicWorkDurationReport.htm", method = RequestMethod.POST)
	public void generateBasicWorkDurationReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		String monthName = "invalid";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		monthName = months[Integer.parseInt(request.getParameter("iMonth"))];

		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		if (shiftId != 0) {
			ServletOutputStream servletOutputStream = null;
			try {
				servletOutputStream = response.getOutputStream();
				String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
				String exportPath = null;
				OutputStream exportPathXLS = null;

				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShiftWiseBasicWorkDurReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShiftWiseBasicWorkDurReport.xls"));

				ResultSet rs = reportDAO.getBasicWorkReport(shiftId, month, year);

				String jasperPath = path + "ShiftWiseBasicWorkDurReport.jrxml";

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("MonthName", monthName);
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				parameters.put("YearName", year);
				parameters.put("Report_Title", "Shift Wise Muster Report");
				rs.beforeFirst();
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				InputStream inputStream = new FileInputStream(jasperPath);
				// jasperDesign = JasperManager.loadXmlDesign(inputStream);
				jasperDesign = JRXmlLoader.load(inputStream);
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

				ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
				exporterXLS.exportReport();
				exportPathXLS.write(outputByteArray.toByteArray());

				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JRException e) {
				e.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "monthlyReport2.htm", method = RequestMethod.GET)
	public ModelAndView showMonthlyReport2(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// modelMap.addAttribute("shiftMasterList",
		// shiftMasterDAO.getShiftMasterList());
		return new ModelAndView("monthlyReport2", getshiftWiseReportModelMap());
	}

	@RequestMapping(value = "generateMonthlyReport2.htm", method = RequestMethod.POST)
	public void generateMonthlyReport2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		String monthName = "invalid";
		String allowedTime = null;
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
		if (null != request.getParameter("allowedTime")) {
			allowedTime = request.getParameter("allowedTime");
			System.out.println("Grace Pereoid" + allowedTime);
			System.out.println("Shift name" + request.getParameter("shiftName"));
		}

		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		String sname = reportDAO.getShiftName(shiftId);
		if (shiftId != 0) {
			ResultSet rs = reportDAO.getBasicWorkDetailReport(shiftId, month, year, allowedTime);
			// Generate PDF Report
			ServletOutputStream servletOutputStream = null;
			try {
				servletOutputStream = response.getOutputStream();
				String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
				String exportPath = null;
				OutputStream exportPathXLS = null;

				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShiftWiseMonthlyReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShiftWiseMonthlyReport.xls"));

				String jasperPath = path + "MonthlyAttendanceReport2.jrxml";

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("MonthName", monthName);
				parameters.put("YearName", year);
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				parameters.put("Report_Title", "Shift Wise Muster Report");
				rs.beforeFirst();
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				InputStream inputStream = new FileInputStream(jasperPath);
				// jasperDesign = JasperManager.loadXmlDesign(inputStream);
				jasperDesign = JRXmlLoader.load(inputStream);
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

				ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
				exporterXLS.exportReport();
				exportPathXLS.write(outputByteArray.toByteArray());
				// JasperViewer.viewReport(jasperPrint, false);
				// OutputStream out= new FileOutputStream(new
				// File("D:/Distna_Report/"+"MonthlyAttendanceDetailReport.pdf"));
				// out.write(outputByteArray.toByteArray());

				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JRException e) {
				e.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Generate Excel Report
			try {

				rs.first();

				// Get current Date and Time
				Date date = new Date(System.currentTimeMillis());
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

				XSSFWorkbook workbook = new XSSFWorkbook();

				// Create a blank sheet
				XSSFSheet sheet = workbook.createSheet("Employee Data");
				// sheet.setHorizontallyCenter(true);
				XSSFFont font = workbook.createFont();
				font.setFontHeightInPoints((short) 21);
				font.setFontName("Calibri");
				// font.setBold(true);
				// font.setItalic(true);
				// font.setColor(HSSFColor.BRIGHT_GREEN.index);

				// Set font into style
				XSSFCellStyle style = workbook.createCellStyle();
				style.setFont(font);

				// Create a cell with a value and set style to it.
				sheet.getPrintSetup().setScale((short) 40);
				sheet.getPrintSetup().setLandscape(true);

				// set paper size
				sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);

				// set display grid lines or not
				sheet.setDisplayGridlines(true);

				// set print grid lines or not
				sheet.setPrintGridlines(true);

				// Set Margin
				sheet.setMargin(Sheet.LeftMargin, 0.25);
				sheet.setMargin(Sheet.RightMargin, 0.25);
				sheet.setMargin(Sheet.TopMargin, 0.75);
				sheet.setMargin(Sheet.BottomMargin, 0.75);
				sheet.setMargin(Sheet.HeaderMargin, 0.25);
				sheet.setMargin(Sheet.FooterMargin, 0.25);

				// Set Footer
				Footer footer = sheet.getFooter();
				footer.setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

				// set header
				Header header = sheet.getHeader();
				header.setLeft(HSSFHeader.font("Arial", "Bold") + HSSFHeader.fontSize((short) 22) + "Month :-  "
						+ monthName + "  " + year + "    Shift:- " + sname);
				header.setCenter(HSSFHeader.font("Arial", "Bold") + HSSFHeader.fontSize((short) 32)
						+ "Monthly Attendance Detail Report");
				// header.setRight(HSSFHeader.font("Arial", "Bold") +
				// HSSFHeader.fontSize((short) 20) + df.format(date));
				header.setRight(HSSFHeader.font("Arial", "Bold") + HSSFHeader.fontSize((short) 18)
						+ "A- Absent , P-Present , W- Weekly Off, N- Not allowed");
				// This data needs to be written (Object[])
				Map<String, Object[]> data = new TreeMap<String, Object[]>();

				data.put("1001",
						new Object[] { "Emp No", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
								"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
								"28", "29", "30", "31", "P", "A" });

				int j = 1002;
				int count = 1;
				while (rs.next()) {
					data.put(++j + "", new Object[] { rs.getInt("empNo"),
							rs.getString("first_name") + " " + rs.getString("last_name") });
					data.put(++j + "", new Object[] { "Status", rs.getString("day1"), rs.getString("day2"),
							rs.getString("day3"), rs.getString("day4"), rs.getString("day5"), rs.getString("day6"),
							rs.getString("day7"), rs.getString("day8"), rs.getString("day9"), rs.getString("day10"),
							rs.getString("day11"), rs.getString("day12"), rs.getString("day13"), rs.getString("day14"),
							rs.getString("day15"), rs.getString("day16"), rs.getString("day17"), rs.getString("day18"),
							rs.getString("day19"), rs.getString("day20"), rs.getString("day21"), rs.getString("day22"),
							rs.getString("day23"), rs.getString("day24"), rs.getString("day25"), rs.getString("day26"),
							rs.getString("day27"), rs.getString("day28"), rs.getString("day29"), rs.getString("day30"),
							rs.getString("day31"), rs.getString("presentCount"), rs.getString("absentCount") });
					data.put(++j + "", new Object[] { "In Time", rs.getString("d1In"), rs.getString("d2In"),
							rs.getString("d3In"), rs.getString("d4In"), rs.getString("d5In"), rs.getString("d6In"),
							rs.getString("d7In"), rs.getString("d8In"), rs.getString("d9In"), rs.getString("d10In"),
							rs.getString("d11In"), rs.getString("d12In"), rs.getString("d13In"), rs.getString("d14In"),
							rs.getString("d15In"), rs.getString("d16In"), rs.getString("d17In"), rs.getString("d18In"),
							rs.getString("d19In"), rs.getString("d20In"), rs.getString("d21In"), rs.getString("d22In"),
							rs.getString("d23In"), rs.getString("d24In"), rs.getString("d25In"), rs.getString("d26In"),
							rs.getString("d27In"), rs.getString("d28In"), rs.getString("d29In"), rs.getString("d30In"),
							rs.getString("d31In") });
					data.put(++j + "", new Object[] { "Out Time", rs.getString("d1Out"), rs.getString("d2Out"),
							rs.getString("d3Out"), rs.getString("d4Out"), rs.getString("d5Out"), rs.getString("d6Out"),
							rs.getString("d7Out"), rs.getString("d8Out"), rs.getString("d9Out"), rs.getString("d10Out"),
							rs.getString("d11Out"), rs.getString("d12Out"), rs.getString("d13Out"),
							rs.getString("d14Out"), rs.getString("d15Out"), rs.getString("d16Out"),
							rs.getString("d17Out"), rs.getString("d18Out"), rs.getString("d19Out"),
							rs.getString("d20Out"), rs.getString("d21Out"), rs.getString("d22Out"),
							rs.getString("d23Out"), rs.getString("d24Out"), rs.getString("d25Out"),
							rs.getString("d26Out"), rs.getString("d27Out"), rs.getString("d28Out"),
							rs.getString("d29Out"), rs.getString("d30Out"), rs.getString("d31Out") });
					data.put(++j + "", new Object[] { "Duration", rs.getString("TD1"), rs.getString("TD2"),
							rs.getString("TD3"), rs.getString("TD4"), rs.getString("TD5"), rs.getString("TD6"),
							rs.getString("TD7"), rs.getString("TD8"), rs.getString("TD9"), rs.getString("TD10"),
							rs.getString("TD11"), rs.getString("TD12"), rs.getString("TD13"), rs.getString("TD14"),
							rs.getString("TD15"), rs.getString("TD16"), rs.getString("TD17"), rs.getString("TD18"),
							rs.getString("TD19"), rs.getString("TD20"), rs.getString("TD21"), rs.getString("TD22"),
							rs.getString("TD23"), rs.getString("TD24"), rs.getString("TD25"), rs.getString("TD26"),
							rs.getString("TD27"), rs.getString("TD28"), rs.getString("TD29"), rs.getString("TD30"),
							rs.getString("TD31") });
					data.put(++j + "", new Object[] { "Late By", rs.getString("LT1"), rs.getString("LT2"),
							rs.getString("LT3"), rs.getString("LT4"), rs.getString("LT5"), rs.getString("LT6"),
							rs.getString("LT7"), rs.getString("LT8"), rs.getString("LT9"), rs.getString("LT10"),
							rs.getString("LT11"), rs.getString("LT12"), rs.getString("LT13"), rs.getString("LT14"),
							rs.getString("LT15"), rs.getString("LT16"), rs.getString("LT17"), rs.getString("LT18"),
							rs.getString("LT19"), rs.getString("LT20"), rs.getString("LT21"), rs.getString("LT22"),
							rs.getString("LT23"), rs.getString("LT24"), rs.getString("LT25"), rs.getString("LT26"),
							rs.getString("LT27"), rs.getString("LT28"), rs.getString("LT29"), rs.getString("LT30"),
							rs.getString("LT31") });
					data.put(++j + "", new Object[] { "Over Time", rs.getString("OT1"), rs.getString("OT2"),
							rs.getString("OT3"), rs.getString("OT4"), rs.getString("OT5"), rs.getString("OT6"),
							rs.getString("OT7"), rs.getString("OT8"), rs.getString("OT9"), rs.getString("OT10"),
							rs.getString("OT11"), rs.getString("OT12"), rs.getString("OT13"), rs.getString("OT14"),
							rs.getString("OT15"), rs.getString("OT16"), rs.getString("OT17"), rs.getString("OT18"),
							rs.getString("OT19"), rs.getString("OT20"), rs.getString("OT21"), rs.getString("OT22"),
							rs.getString("OT23"), rs.getString("OT24"), rs.getString("OT25"), rs.getString("OT26"),
							rs.getString("OT27"), rs.getString("OT28"), rs.getString("OT29"), rs.getString("OT30"),
							rs.getString("OT31") });
					data.put(++j + "", new Object[] { "Early By", rs.getString("ET1"), rs.getString("ET2"),
							rs.getString("ET3"), rs.getString("ET4"), rs.getString("ET5"), rs.getString("ET6"),
							rs.getString("ET7"), rs.getString("ET8"), rs.getString("ET9"), rs.getString("ET10"),
							rs.getString("ET11"), rs.getString("ET12"), rs.getString("ET13"), rs.getString("ET14"),
							rs.getString("ET15"), rs.getString("ET16"), rs.getString("ET17"), rs.getString("ET18"),
							rs.getString("ET19"), rs.getString("ET20"), rs.getString("ET21"), rs.getString("ET22"),
							rs.getString("ET23"), rs.getString("ET24"), rs.getString("ET25"), rs.getString("ET26"),
							rs.getString("ET27"), rs.getString("ET28"), rs.getString("ET29"), rs.getString("ET30"),
							rs.getString("ET31") });
					data.put(++j + "", new Object[] { "   " });

					if (count % 3 == 0) {
						data.put(++j + "", new Object[] { "   " });
						data.put(++j + "", new Object[] { "   " });
						data.put(++j + "", new Object[] { "   " });
						data.put(++j + "",
								new Object[] { "Emp No", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
										"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
										"26", "27", "28", "29", "30", "31", "P", "A" });

					}
					count++;
				}

				// sheet.createRow(0).createCell(12).setCellValue("Monthly Attendance Detail
				// Report");
				// sheet.createRow(1).createCell(2).setCellValue(monthName+" Report");
				// sheet.getRow(1).createCell(20).setCellValue("Printed On :"+ new Date());
				// sheet.createRow(2).createCell(10).setCellValue("A- Absent , P-Present , W-
				// Weekly Off, N- Not allowed");
				sheet.setDefaultColumnWidth(9);
				sheet.setDefaultRowHeight((short) 800);
				sheet.setColumnWidth(0, 4900);
				sheet.setColumnWidth(32, 1500);
				sheet.setColumnWidth(33, 1500);
				sheet.setZoom(1, 2);
				// Iterate over data and write to sheet
				Set<String> keyset = new TreeSet<String>(data.keySet());

				int rownum = 1;
				for (String key : keyset) {
					// sheet=workbook.createSheet();
					Row row = sheet.createRow(rownum++);
					System.out.println("Key Value" + key);
					Object[] objArr = data.get(key);
					int cellnum = 0;
					for (Object obj : objArr) {
						Cell cell = row.createCell(cellnum++);

						cell.setCellStyle(style);
						if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Integer)
							cell.setCellValue((Integer) obj);
					}
				}

				try {
					// Write the workbook in file system
					FileOutputStream out = new FileOutputStream(new File(
							"D:/Distna_Report/" + monthName + " AttendanceDetailReport-" + df.format(date) + ".xlsx"));
					workbook.write(out);
					out.close();
					System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+""; String
		 * year=request.getParameter("iYear"); String monthName = "invalid"; String
		 * allowedTime=null; DateFormatSymbols dfs = new DateFormatSymbols(); String[]
		 * months = dfs.getMonths(); monthName =
		 * months[Integer.parseInt(request.getParameter("iMonth"))];
		 * System.out.println("hello"); if(null!=request.getParameter("allowedTime")) {
		 * allowedTime=request.getParameter("allowedTime");
		 * System.out.println("Grace Pereoid"+allowedTime);
		 * System.out.println("Shift name"+request.getParameter("shiftName")); }
		 * 
		 * int shiftId=Integer.parseInt(request.getParameter("shiftName"));
		 * if(shiftId!=0) { ServletOutputStream servletOutputStream = null; try {
		 * servletOutputStream = response.getOutputStream(); String
		 * path=request.getSession().getServletContext().getRealPath("jasperReports")+
		 * "\\"; String exportPath=null; OutputStream exportPathXLS=null;
		 * 
		 * exportPath=request.getSession().getServletContext().getRealPath("reports") +
		 * "\\"+"ShiftWiseMonthlyReport.pdf"; exportPathXLS=new FileOutputStream(new
		 * File(request.getSession().getServletContext().getRealPath("reportsXLS") +
		 * "\\"+"ShiftWiseMonthlyReport.xls"));
		 * 
		 * 
		 * 
		 * 
		 * ResultSet rs=reportDAO.getBasicWorkDetailReport(shiftId, month,
		 * year,allowedTime);
		 * 
		 * 
		 * String jasperPath=path+"MonthlyAttendanceReport2.jrxml";
		 * 
		 * Map<String, Object> parameters = new HashMap<String, Object>();
		 * parameters.put("MonthName",monthName); parameters.put("YearName",year);
		 * parameters.put("Report_Title","Shift Wise Muster Report"); rs.beforeFirst();
		 * JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
		 * InputStream inputStream = new FileInputStream(jasperPath); //jasperDesign =
		 * JasperManager.loadXmlDesign(inputStream); jasperDesign =
		 * JRXmlLoader.load(inputStream); jasperReport =
		 * JasperCompileManager.compileReport(jasperDesign); jasperPrint =
		 * JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
		 * JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
		 * response.setContentType("application/pdf");
		 * JasperExportManager.exportReportToPdfStream(jasperPrint,
		 * servletOutputStream);
		 * 
		 * 
		 * ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		 * JRXlsExporter exporterXLS = new JRXlsExporter();
		 * exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		 * exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
		 * outputByteArray);
		 * exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
		 * Boolean.FALSE);
		 * exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
		 * Boolean.TRUE);
		 * exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
		 * Boolean.FALSE); exporterXLS.setParameter(JRXlsExporterParameter.
		 * IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		 * exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
		 * Boolean.TRUE); exporterXLS.exportReport();
		 * exportPathXLS.write(outputByteArray.toByteArray());
		 * JasperViewer.viewReport(jasperPrint, false); OutputStream out= new
		 * FileOutputStream(new
		 * File("D:/Distna_Report/"+"MonthlyAttendanceDetailReport.xls"));
		 * out.write(outputByteArray.toByteArray());
		 * 
		 * servletOutputStream.flush(); servletOutputStream.close(); } catch
		 * (IOException e) { e.printStackTrace(); } catch (JRException e) {
		 * e.printStackTrace(); StringWriter stringWriter = new StringWriter();
		 * PrintWriter printWriter = new PrintWriter(stringWriter);
		 * e.printStackTrace(printWriter); response.setContentType("text/plain"); }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
	}

	@RequestMapping(value = "showpresenceabsencepercent.htm", method = RequestMethod.GET)
	public ModelAndView presenceAbsencePercentage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		/*
		 * List<Departments> departmentList=departmentDAO.getDepartment();
		 * modelMap.addAttribute("departmentList", departmentList);
		 */
		return new ModelAndView("presenceabsencechart", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "generatepresenceabscencechart.htm", method = RequestMethod.POST)
	public ModelAndView generatePresenceAbscenceChart(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType,
			@RequestParam(value = "departmentId") int departmentId, @RequestParam(value = "dateFrom") String dateFrom,
			@RequestParam(value = "dateTo") String dateTo, @RequestParam(value = "employeeNo") int employeeNo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {

		presenceAbscencePercentage.createDemoPanel(request, employeeNo, dateFrom, dateTo, departmentId,
				selectedcheckbox, empReportType);
		return new ModelAndView("presentabsentratio");
	}

	@RequestMapping(value = "showaddmessage.htm", method = RequestMethod.GET)
	public ModelAndView showAddEmployeeMessages(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, EmployeeMessages employeeMessages) {
		/*
		 * ModelMap modelMap=new ModelMap(); modelMap.addAttribute("employeeList",
		 * employeeDAO.getEmployeeList());
		 */
		return new ModelAndView("addemployeemessages");
	}

	@RequestMapping(value = "addemployeemessages.htm", method = RequestMethod.POST)
	public ModelAndView addEmployeeMessages(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, EmployeeMessages employeeMessages,
			BindingResult bindingResult) {
		employeeMessages.setMessageFrom(Integer.parseInt(request.getParameter("messageFrom")));
		if (request.getParameter("memo") != null) {
			if (request.getParameter("memo").equals("true")) {
				employeeMessages.setMessageSubject("Memo: " + request.getParameter("messageSubject"));
			}
		} else {
			employeeMessages.setMessageSubject(request.getParameter("messageSubject"));
		}
		String messageBody = request.getParameter("messageBody");
		String lineSeparator = System.getProperty("line.separator");
		messageBody = messageBody.replaceAll(lineSeparator, "<br>");
		employeeMessages.setMessageBody(messageBody);
		employeeMessages.setMessageDate(request.getParameter("messageDate"));

		if (empReportType.equals("multiple")) {
			String employeeIds = selectedcheckbox;
			String multipleEmployees[] = employeeIds.split(",");
			// System.out.println(employeeIds);
			for (int i = 0; i < multipleEmployees.length; i++) {
				employeeMessages.setEmployeeId(Integer.parseInt(multipleEmployees[i]));
				employeeMessages.setEmployeeDepartmentId(
						employeeDAO.getDepartmentIdByEmployeeId(employeeMessages.getEmployeeId()));
				employeeMessagesValidator.validate(employeeMessages, bindingResult);
				if (bindingResult.hasErrors()) {
					return new ModelAndView("addemployeemessages");
				} else {
					employeeMessagesDAO.saveEmployeeMessages(employeeMessages);
				}
			}

		}

		else if (empReportType.equals("single")) {
			String employeeId = request.getParameter("employeeNo");
			employeeMessages.setEmployeeId(Integer.parseInt(employeeId));
			employeeMessages
					.setEmployeeDepartmentId(employeeDAO.getDepartmentIdByEmployeeId(employeeMessages.getEmployeeId()));
			employeeMessagesValidator.validate(employeeMessages, bindingResult);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("addemployeemessages");
			} else {
				employeeMessagesDAO.saveEmployeeMessages(employeeMessages);
			}
		}

		else if (empReportType.equals("all")) {
			List<Employee> allEmployees = employeeDAO.getEmployeeList();
			for (Employee employee : allEmployees) {
				employeeMessages.setEmployeeId(employee.getEmployeeId());
				employeeMessages.setEmployeeDepartmentId(
						employeeDAO.getDepartmentIdByEmployeeId(employeeMessages.getEmployeeId()));
				employeeMessagesValidator.validate(employeeMessages, bindingResult);
				if (bindingResult.hasErrors()) {
					return new ModelAndView("addemployeemessages");
				} else {
					employeeMessagesDAO.saveEmployeeMessages(employeeMessages);
				}
			}
		}

		else {
			employeeMessagesValidator.validate(employeeMessages, bindingResult);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("addemployeemessages");
			}

		}

		return new ModelAndView(new RedirectView("showaddmessage.htm"));
	}

	public ModelMap getShowDeptwiseEmployeeModelMap(int deptid) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeListByDepartment(deptid);
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showDeptwiseEmployee.htm", method = RequestMethod.POST)
	public ModelAndView showDeptwiseEmployee(@RequestParam(value = "deptid") int deptid, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		/*
		 * List<Employee> employeeList=employeeDAO.getEmployeeListByDepartment(deptid);
		 * modelMap.addAttribute("employeeList",employeeList);
		 */
		return new ModelAndView("showdeptwiseemployee", getShowDeptwiseEmployeeModelMap(deptid));
	}

//		public ModelMap getattendanceLogsModlMap(){
//			ModelMap modelMap=new ModelMap();
//			List<AttendanceLogs> attendanceLogsList= ((Object) attendanceLogsDAO).getAttendanceLogsList();
//			modelMap.addAttribute("attendanceLogsList",attendanceLogsList);
//			return modelMap;
//		}
//	

//		@RequestMapping(value = "userDashnoardCurrentMonthInOut.htm", method = RequestMethod.POST)
//
//		public ModelAndView userDashnoardCurrentMonthInOut(@RequestParam(value = "employeeNo") int employeeNo,
//				HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//			ModelMap modelMap = new ModelMap();
//			
//			List<Atte ndanceLogsBulkEntry> attendanceLogsBulkEntriesList = attendanceLogsBulkEntryDAO
//					.getAttendanceLogsBulkEntrys(employeeNo);
//			
//			modelMap.addAttribute("attendanceLogsBulkEntriesList", attendanceLogsBulkEntriesList);
//			return new ModelAndView("userdashnoardcurrentmonthInout", modelMap);
//		}

	@RequestMapping(value = "userDashnoardCurrentMonthInOut.htm", method = RequestMethod.POST)
	public ModelAndView userDashnoardCurrentMonthInOut(@RequestParam(value = "employeeNo") int employeeNo,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();

		List<Attendance> attendanceList = attendanceDAO.getAttendanceList(employeeNo);

		modelMap.addAttribute("attendanceList", attendanceList);
		return new ModelAndView("userdashnoardcurrentmonthInout", modelMap);
	}

	@RequestMapping(value = "userDashnoardInbox.htm", method = RequestMethod.POST)
	public ModelAndView userDashnoardInbox(@RequestParam(value = "employeeId") int employeeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getEmployeeWiseReportModelMap();
		List<EmployeeMessages> employeeMessagesList = employeeMessagesDAO.getEmployeeMessagesByEmployeeId(employeeId);
		modelMap.addAttribute("employeeMessagesList", employeeMessagesList);
		return new ModelAndView("userdashnoardinbox", modelMap);
	}

	@RequestMapping(value = "userDashboardShowMsg.htm", method = RequestMethod.POST)
	public ModelAndView userDashboardShowMsg(@RequestParam(value = "messageId") int messageId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		EmployeeMessages employeeMessagesObj = employeeMessagesDAO.getEmployeeMessagesById(messageId);
		modelMap.addAttribute("employeeMessagesObj", employeeMessagesObj);
		employeeMessagesDAO.changeEmployeeMessagesStatus(messageId);
		if (null != session.getAttribute("loginUserId"))
			modelMap.addAttribute("loginUserEmployeeId", (Integer) session.getAttribute("loginUserId"));
		return new ModelAndView("userdashboardshowmsg", modelMap);
	}

	@RequestMapping(value = "showMyUserDashboard.htm", method = RequestMethod.GET)
	public ModelAndView showMyUserDashboard(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		System.out.println("showMyUserDashboard.htm");
		ModelMap modelMap = new ModelMap();
		if (null != session.getAttribute("loginUserId")) {
			Employee employee = employeeDAO.getEmployeeById((Integer) session.getAttribute("loginUserId"));
			List<Employee> monthsBirthdayList = employeeDAO.getEmployeesWithBirthdays();
			Date currentDate = Calendar.getInstance().getTime();
			Calendar calendarStartOfWeek = Calendar.getInstance();
			Calendar calendarEndOfWeek = Calendar.getInstance();
			calendarEndOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
			String currentDateString = dateFormat.format(currentDate);
			int employeeMessagesCount = employeeMessagesDAO.getUnreadEmployeeMessagesCount(employee.getEmployeeId());
			modelMap.addAttribute("employeeMessagesListSize", employeeMessagesCount);
			modelMap.addAttribute("monthsBirthdayList", monthsBirthdayList);
			modelMap.addAttribute("currentDate", currentDateString);
			modelMap.addAttribute("loginUserName", employee.getFirstName());
			modelMap.addAttribute("loginUserEmployeeNo", employee.getEmployeeNo());
			modelMap.addAttribute("loginUserEmployeeId", employee.getEmployeeId());
			int visitorEntry = visitorLogsDAO.getVisitorLogsListAccEmployee(employee.getEmployeeId()).size();
			modelMap.addAttribute("visitorEntry", visitorEntry);
			// return new ModelAndView("adminsuserdashboard",modelMap);
			return new ModelAndView("userdashboard", modelMap);
		} else {
			return new ModelAndView(new RedirectView("loginpage.htm"));

		}

	}

	@RequestMapping(value = "userDashnoardDirectory.htm", method = RequestMethod.POST)
	public ModelAndView userDashnoardDirectory(@RequestParam(value = "employeeId") int employeeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		/*
		 * List<Employee>
		 * employeeList=employeeDAO.getEmployeeListExcludingCurrentId(employeeId);
		 * modelMap.addAttribute("employeeList", employeeList);
		 */
		ModelMap modelMap = getEmployeeWiseReportModelMap();
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		return new ModelAndView("userdashnoarddirectory", modelMap);
	}

	public ModelMap getUserDashboardLeaveApplicationModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("leavetypeList", leaveTypeDAO.getleavetypes());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("priorities", priorityDAO.getPriorityList());
		return modelMap;
	}

	
	 @RequestMapping(value = "userDashboardLeaveApplication.htm", method = RequestMethod.POST) 
	 public ModelAndView userDashboardLeaveApplication(HttpServletRequest request, HttpServletResponse
	  response, HttpSession session, LeaveApplication leaveApplication) {
	  
	  return new ModelAndView("userdashboardleaveapplication",
	  getUserDashboardLeaveApplicationModelMap());
	  
	  }
	 

	 @RequestMapping(value="userDashboardaddLeaveApplication.htm",method=RequestMethod.POST)
		public ModelAndView userDashboardaddLeaveApplication(HttpServletRequest request,HttpServletResponse response, HttpSession session,LeaveApplication leaveApplication,BindingResult bindingResult)
		{
			System.out.println("userDashboardaddLeaveApplication");
			ModelMap modelMap=getUserDashboardLeaveApplicationModelMap();
			int empId= (Integer)session.getAttribute("loginUserId");
			leaveApplication.setEmployee_id(empId);
			leaveApplication.setEmployeeDepartmentId(empId);
			if(null!=request.getParameter("deptEmployee")){
				leaveApplication.setEmployee_no(Integer.parseInt(request.getParameter("deptEmployee")));
				}
			if(request.getParameter("half_day_session")!=null)
			{
				leaveApplication.setHalf_day_session(request.getParameter("half_day_session"));
			
				if(request.getParameter("half_day_session").equals("First Session"))
				{
					leaveApplication.setFirst_half_day("true");
				}
				else if(request.getParameter("half_day_session").equals("Second Session"))
				{
					leaveApplication.setLast_half_day("true");
				}
			}
			else
				leaveApplication.setHalf_day_session("None");
			leaveApplication.setLeaveStatus(1);				
			List<Employee> employeeList=employeeDAO.getEmployeeListById(leaveApplication.getEmployee_id());
			
			
		
			Employee employee=employeeDAO.getEmployeeById(empId);
			leaveApplicationValidator.validate(leaveApplication, bindingResult);
			
			if(bindingResult.hasErrors())
			{
				/*modelMap.addAttribute("employeeList",employeeDAO.getEmployeeList());
				modelMap.addAttribute("leavetypeList",leaveTypeDAO.getleavetypes());
				modelMap.addAttribute("departmentList",departmentDAO.getDepartment());
				modelMap.addAttribute("priorities",priorityDAO.getPriorityList());*/
				return new ModelAndView("userdashboard",modelMap);
				
			}
			else
			{
					
				leaveApplicationDAO.saveLeaveApplication(leaveApplication);
					
					SendMailTLS sendMailTLS=new SendMailTLS();
					
					int senderEmployeeId=leaveApplication.getEmployee_id();
					Employee senderEmployee=employeeDAO.getEmployeeById(senderEmployeeId);
					String senderEmail=senderEmployee.getEmail();
					String decryptedSenderPasssword=encryptPassword.decrypt(senderEmployee.getPassword());
					String smtpHost="";
					String receiverEmail="";
					if(senderEmail.contains("yahoo"))
					{
						smtpHost="smtp.mail.yahoo.com";	
					}
					else
					{
						smtpHost="smtp."+senderEmail.split("@")[1];
					}
					
					if(leaveApplication.getEmployeeType().equalsIgnoreCase("single"))
					{
						receiverEmail=employeeDAO.getEmployeeById(Integer.parseInt(leaveApplication.getSingleEmployeeId())).getEmail();
						sendMailTLS.sendMail(senderEmail,decryptedSenderPasssword,smtpHost, "587","kshitij.pandkar@gmail.com",employee.getFirstName()+" "+employee.getLastName(),leaveApplication.getSubject(), leaveApplication.getDescription());
						
					}
					
					else if(leaveApplication.getEmployeeType().equalsIgnoreCase("multiple"))
					{
						String multipleEmailIds=leaveApplication.getMultipleEmployeeIds();
						String multipleEmailIdsArray[]=multipleEmailIds.split(",");
						for (int i = 0; i < multipleEmailIdsArray.length; i++) {
							receiverEmail=employeeDAO.getEmployeeById(Integer.parseInt(multipleEmailIdsArray[i])).getEmail();
							sendMailTLS.sendMail(senderEmail,decryptedSenderPasssword,smtpHost, "587",receiverEmail,employee.getFirstName()+" "+employee.getLastName(),leaveApplication.getSubject(), leaveApplication.getDescription());
						}
					
					}
					
					else if(leaveApplication.getEmployeeType().equalsIgnoreCase("all"))
					{
						List<Employee> departmentWiseEmployeesList=employeeDAO.getEmployeeListByDepartment(leaveApplication.getDepartment_id());
						for (Employee employeeObj : departmentWiseEmployeesList) {
							receiverEmail=employeeObj.getEmail();
							sendMailTLS.sendMail(senderEmail,decryptedSenderPasssword,smtpHost, "587",receiverEmail,employee.getFirstName()+" "+employee.getLastName(),leaveApplication.getSubject(), leaveApplication.getDescription());
						}
					}
					modelMap=getModelMapForUserDashboard(employee);
					return new ModelAndView("userdashboard",modelMap);
			}	
		}

	@RequestMapping(value = "userDashboardHome.htm", method = RequestMethod.GET)
	public ModelAndView userDashboardHome(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			LeaveApplication leaveApplication, BindingResult bindingResult) {
		ModelMap modelMap = new ModelMap();
		if (null != session.getAttribute("loginUserId")) {
			Employee employee = employeeDAO.getEmployeeById((Integer) session.getAttribute("loginUserId"));
			int userType = employee.getAdminFlag();
			if (userType == 1) {
				modelMap = getModelMapForUserDashboard(employee);
				return new ModelAndView("userdashboard", modelMap);
			} else if (userType == 2) {
				return new ModelAndView("admindashboard");
			} else if (userType == 3) {
				return new ModelAndView("admindashboard");
			} else {
				return new ModelAndView(new RedirectView("loginpage.htm"));
			}
		} else {
			return new ModelAndView(new RedirectView("loginpage.htm"));
		}
	}

	@RequestMapping(value = "updateDeviceInfo.htm", method = RequestMethod.POST)
	public ModelAndView updateDeviceInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			AddDevice addDevice) {
		/* addDevice.setId(Integer.parseInt(request.getParameter("deviceId"))); */
		List<AddDevice> addDevicesList = addDeviceDAO.getDeviceInfoListByIp(addDevice.getIpAddress());
		if (addDevicesList.size() > 0 && addDevice.getId() != addDevicesList.get(0).getId()) {

			ModelMap modelMap = getDeviceModelMap();
			modelMap.addAttribute("ipPresent", "Please select another IP Address");
			return new ModelAndView("updateattendancedevice", modelMap);
		} else {
			addDeviceDAO.saveOrUpdateDevice(addDevice);
			return new ModelAndView(new RedirectView("updateAttendanceDevice.htm"));
		}

	}

	@ModelAttribute("classCommand")
	public ClassCommand getClassCommand() {
		ClassCommand classCommand = new ClassCommand();
		AutoPopulatingList<Student> autoPopulatingList = new AutoPopulatingList<Student>(Student.class);
		classCommand.setStudents(autoPopulatingList);
		return classCommand;
	}

	@RequestMapping(method = RequestMethod.GET, value = "appendStudentView.htm")
	protected ModelAndView showNameForm(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		ModelMap model = new ModelMap();
		return new ModelAndView("appendtudentView", model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "appendStudentViewInner.htm")
	protected ModelAndView appendStudentField(@RequestParam Integer fieldId, HttpServletRequest request,
			HttpServletResponse res, HttpSession session) {
		ModelMap model = new ModelMap();
		model.addAttribute("studentNumber", fieldId);
		return new ModelAndView("appendStudentviewinner", model);
	}

	@RequestMapping(method = RequestMethod.POST, value = "processClassForm.htm")
	protected ModelAndView onSubmit(@ModelAttribute("classCommand") ClassCommand classCommand,
			HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		ModelMap model = new ModelMap();
		model.addAttribute("savedClass", classCommand);
		return new ModelAndView("processclassform", model);
	}

	@RequestMapping(value = "showaddnewcitytextbox.htm", method = RequestMethod.POST)
	public ModelAndView showAddNewCity(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("showCitiesTextBox");
	}

	@RequestMapping(value = "addnewcity.htm", method = RequestMethod.POST)
	public ModelAndView addNewCity(@RequestParam(value = "countryId") int countryId,
			@RequestParam(value = "stateId") int stateId, @RequestParam(value = "addnewcity") String addnewcity,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if (!addnewcity.equals("")) {
			Cities cities = new Cities();
			cities.setCity(addnewcity);
			cities.setCountryId(countryId);
			cities.setState_id(stateId);
			citiesDAO.saveCities(cities);
		}
		return new ModelAndView(new RedirectView("showAvalableCities.htm?Id=" + stateId));
	}

	@RequestMapping(value = "clearAttRecords.htm", method = RequestMethod.POST)
	public void clearAttRecords(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shiftAllocationDAO.clearAttRecords();
		printWriter.write("done");

		List<AttendanceLogsOutdoorEntry> attendanceLogsOutdoorEntries = attendanceLogsOutdoorEntryDAO
				.getAttendanceOutEntryList();
		for (AttendanceLogsOutdoorEntry attendanceLogsOutdoorEntry : attendanceLogsOutdoorEntries) {
			boolean isRecordinAttBulk = attendanceLogsBulkEntryDAO.isRecordAvailable(
					attendanceLogsOutdoorEntry.getRecordDate(), attendanceLogsOutdoorEntry.getWorkID());
			if (isRecordinAttBulk) {
				AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();
				attendanceLogsBulkEntry.setRecordDate(attendanceLogsOutdoorEntry.getRecordDate());
				attendanceLogsBulkEntry.setWorkID(attendanceLogsOutdoorEntry.getWorkID());
				attendanceLogsBulkEntry.setShift(attendanceLogsOutdoorEntry.getShift());
				attendanceLogsBulkEntry.setStatus(99);
				attendanceLogsBulkEntry.setTimeAsPerShftTimings("0:0:0");
				attendanceLogsBulkEntry.setExceptionFlag(false);
				attendanceLogsBulkEntryDAO.saveAttendanceBulkEntryForOutdoor(attendanceLogsBulkEntry);
			}
		}

	}

	@RequestMapping(value = "showAddContractor.htm", method = RequestMethod.GET)
	public ModelAndView showAddContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Contractor contractor) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		return new ModelAndView("showaddcontractor", modelMap);
	}

	@RequestMapping(value = "addContractor.htm", method = RequestMethod.POST)
	public ModelAndView addContractor(@Valid Contractor contractor, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		int departmentId = contractor.getDepartment();
		if (departmentId != 0) {
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			contractor.setDivision(divisionId);
			contractor.setLocation(locationId);
		}
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
			return new ModelAndView("showaddcontractor", modelMap);
		} else {
			contractorDAO.saveContractor(contractor);
			return new ModelAndView(new RedirectView("showAddContractor.htm"));
		}
	}

	@RequestMapping(value = "viewContractor.htm", method = RequestMethod.GET)
	public ModelAndView viewContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("locationList", locationDAO.getLocation());
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("divisionList", divisionDAO.getDivisionList());
		List<Contractor> ContractorList = contractorDAO.getContractorList();
		modelMap.addAttribute("contractorList", ContractorList);
		modelMap.addAttribute("contractorListSize", ContractorList.size());
		return new ModelAndView("viewcontractor", modelMap);
	}

	@RequestMapping(value = "deleteContractor.htm", method = RequestMethod.POST)
	public ModelAndView deleteContractor(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		contractorDAO.deleteContractor(id);
		return new ModelAndView(new RedirectView("viewContractor.htm"));
	}

	@RequestMapping(value = "showUpdateContractor.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateContractor(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("contractor", contractorDAO.getContractorList(id).get(0));
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		return new ModelAndView("showupdatecontractor", modelMap);
	}

	@RequestMapping(value = "updateContractor.htm", method = RequestMethod.POST)
	public ModelAndView updateContractor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@Valid Contractor contractor, BindingResult bindingResult) {
		int departmentId = contractor.getDepartment();
		if (departmentId != 0) {
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			contractor.setDivision(divisionId);
			contractor.setLocation(locationId);
		}
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("contractor", contractorDAO.getContractorList(contractor.getContractorId()).get(0));
			modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
			return new ModelAndView("showupdatecontractor", modelMap);
		} else {
			contractorDAO.saveContractor(contractor);
			return new ModelAndView(new RedirectView("viewContractor.htm"));
		}
	}

	@RequestMapping(value = "changeUserPassword.htm", method = RequestMethod.POST)
	public ModelAndView changeUserPassword(@RequestParam(value = "employeeId") int employeeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, @Valid Contractor contractor,
			BindingResult bindingResult) {

		ModelMap modelMap = new ModelMap();
		Employee employee = employeeDAO.getEmployeeById(employeeId);
		String decryptedPassword = null;
		try {
			encryptPassword = new EncryptPassword();
			decryptedPassword = encryptPassword.decrypt(employee.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (employee != null) {
			modelMap.addAttribute("employee", employee);
			modelMap.addAttribute("employeeId", employeeId);
			modelMap.addAttribute("empPassword", decryptedPassword);
		}
		return new ModelAndView("changeuserpassword", modelMap);
	}

	@RequestMapping(value = "savePasswordInternalDiv.htm", method = RequestMethod.POST)
	public ModelAndView savePasswordInternalDiv(@RequestParam(value = "employeeId") int employeeId,
			@RequestParam(value = "conPass") String password, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @Valid Contractor contractor, BindingResult bindingResult) {
		ModelMap modelMap = new ModelMap();
		Employee employee = employeeDAO.getEmployeeById(employeeId);
		try {
			encryptPassword = new EncryptPassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String password=request.getParameter("confirmPassword");
		String encryptedPassword = encryptPassword.encrypt(password);
		employee.setPassword(encryptedPassword);
		employeeDAO.saveOrUpdateEmployeeAndGetId(employee);
		modelMap = getModelMapForUserDashboard(employee);
		return new ModelAndView("userdashboard", modelMap);
	}

	@RequestMapping(value = "showEmployeeWiseExceptionReport.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeeWiseExceptionReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showEmployeeWiseExceptionReport", getEmployeeWiseReportModelMap());
	}

	@RequestMapping(value = "generateEmployeeWiseExceptionReport.htm", method = RequestMethod.POST)
	public void generateEmployeeWiseExceptionReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("ErrorMsg", " ");
		int workId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseExceptionReportMultiple.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseExceptionReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseExceptionReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseExceptionReportSingle.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseExceptioReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseExceptioReportSingle.xls"));
			} else {
				rs = reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo, empReportType,
						selectedcheckbox);
				jasperPath = path + "EmployeeWiseExceptionReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "EmployeeWiseExceptioReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "EmployeeWiseExceptioReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Employee Wise Exception report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}

	@RequestMapping(value = "showOutForWork.htm", method = RequestMethod.GET)
	public ModelAndView showOutForWork(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			OutForWork outForWork) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		return new ModelAndView("showoutforwork", modelMap);
	}

	/*
	 * @RequestMapping(value="getEmployeeListOutForWork.htm",method=RequestMethod.
	 * GET) public @ResponseBody List<String>
	 * getEmployeeListOutForWork(HttpServletRequest request,HttpServletResponse
	 * response, HttpSession session,OutForWork outForWork) { List<Employee>
	 * employeeList=employeeDAO.getEmployeeList(); List<String>
	 * employeeListString=new ArrayList<String>(); for(Employee
	 * employee:employeeList) { employeeListString.add(employee.getFirstName()); }
	 * return employeeListString; }
	 */

	@RequestMapping(value = "saveOutForWork.htm", method = RequestMethod.POST)
	public ModelAndView saveOutForWork(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			OutForWork outForWork) {
		outForWork.setStatus("Pending");
		outForWorkDAO.saveOutForWorkObject(outForWork);
		return new ModelAndView(new RedirectView("userDashboardHome.htm"));
	}

	@RequestMapping(value = "viewOutForWork.htm", method = RequestMethod.GET)
	public ModelAndView viewOutForWork(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<OutForWork> outForWorkPendingList = outForWorkDAO.getOutForWorkRequestList();
		List<OutForWork> outForWorksPendingForCurrentEmployee = new ArrayList<OutForWork>();
		if (outForWorkPendingList.size() != 0) {
			if (null != session.getAttribute("loginUserId")) {
				boolean checkEmployeePresence = false;
				for (OutForWork outForWork : outForWorkPendingList) {
					checkEmployeePresence = false;
					String approvingEmployees = outForWork.getToEmployees();
					String[] approvingEmployeesArray = approvingEmployees.split(",");
					for (String approvingEmployeesId : approvingEmployeesArray) {
						int id = (Integer) session.getAttribute("loginUserId");
						if (Integer.parseInt(approvingEmployeesId) == id) {
							checkEmployeePresence = true;
						}
					}
					if (checkEmployeePresence) {
						outForWorksPendingForCurrentEmployee.add(outForWork);
					}
				}
				modelMap.addAttribute("outForWorksPendingForCurrentEmployee", outForWorksPendingForCurrentEmployee);
				List<Employee> employeeList = employeeDAO.getEmployeeList();
				modelMap.addAttribute("employeeList", employeeList);
			}
		}

		return new ModelAndView("viewoutforwork", modelMap);
	}

	@RequestMapping(value = "viewOutForWorkApproved", method = RequestMethod.GET)
	public ModelAndView viewOutForWorkApproved(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<OutForWork> outForWorkApprovedList = outForWorkDAO.getOutForWorkApprovedList();
		List<OutForWork> outForWorksApprovedForCurrentEmployee = new ArrayList<OutForWork>();
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy hh:mm:ss");

		Calendar savedTime = Calendar.getInstance();
		Calendar currentTime = Calendar.getInstance();

		if (outForWorkApprovedList.size() != 0) {
			if (null != session.getAttribute("loginUserId")) {
				boolean checkEmployeePresence = false;
				for (OutForWork outForWork : outForWorkApprovedList) {
					try {
						savedTime.setTime(dateFormat.parse(outForWork.getMessageDate()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					long weakDifference = Calendar.getInstance().getTimeInMillis() - savedTime.getTimeInMillis();
					float dayCount = (float) weakDifference / (24 * 60 * 60 * 1000);
					int week = ((int) dayCount / 7);

					if (week < 1) {
						checkEmployeePresence = false;
						String approvingEmployees = outForWork.getToEmployees();
						String[] approvingEmployeesArray = approvingEmployees.split(",");

						for (String approvingEmployeesId : approvingEmployeesArray) {
							int id = (Integer) session.getAttribute("loginUserId");
							if (Integer.parseInt(approvingEmployeesId) == id) {
								checkEmployeePresence = true;
							}
						}
						if (checkEmployeePresence) {
							outForWorksApprovedForCurrentEmployee.add(outForWork);
						}
					}

				}
				modelMap.addAttribute("outForWorksApprovedForCurrentEmployee", outForWorksApprovedForCurrentEmployee);
				List<Employee> employeeList = employeeDAO.getEmployeeList();
				modelMap.addAttribute("employeeList", employeeList);
			}
		}
		return new ModelAndView("viewoutforworkapproved", modelMap);
	}

	@RequestMapping(value = "updatePendingOutForWork.htm", method = RequestMethod.POST)
	public ModelAndView updatePendingOutForWork(@RequestParam(value = "id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		if (null != session.getAttribute("loginUserId")) {
			if (null != request.getParameter("datefrom") && null != request.getParameter("dateTo")) {
				String startDate = request.getParameter("datefrom");
				String endDate = request.getParameter("dateTo");
				List<OutForWork> outForWorkList = outForWorkDAO.getOutForWorkRequestList(id);
				OutForWork outForWork = outForWorkList.get(0);
				outForWork.setApprovedFromDate(request.getParameter("datefrom"));
				outForWork.setApprovedToDate(request.getParameter("dateTo"));
				outForWork.setStatus("Approved");
				outForWorkDAO.saveOutForWorkObject(outForWork);

				EmployeeMessages employeeMessages = new EmployeeMessages();

				employeeMessages.setEmployeeId(outForWork.getEmployeeId());

				employeeMessages.setMessageFrom((Integer) session.getAttribute("loginUserId"));
				employeeMessages.setMessageSubject("Approval For Out Of Work Request");
				employeeMessages.setMessageBody("Your Out For Work Requst Has Been Approved \n \n Approved Start Date:"
						+ outForWork.getApprovedFromDate() + " \n \n Approved End Date:"
						+ outForWork.getApprovedToDate());
				employeeMessages.setMessageDate(request.getParameter("messageDate"));
				employeeMessagesDAO.saveEmployeeMessages(employeeMessages);
				saveAttendanceForOutDoorEntry(outForWork.getEmployeeId(), startDate, endDate);
			}
		}
		return new ModelAndView(new RedirectView("viewOutForWork.htm"));
	}

	@RequestMapping(value = "showLeaveStatusReport.htm", method = RequestMethod.GET)
	public ModelAndView showLeaveStatusReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("leaveTypeList", leaveTypeDAO.getleavetypes());
		return new ModelAndView("showleavestatusreport", modelMap);
	}

	@RequestMapping(value = "generateLeaveStatusReport.htm", method = RequestMethod.POST)
	public void generateLeaveStatusReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		int workId = 0;
		modelMap.addAttribute("ErrorMsg", " ");
		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		int leaveType = 0;
		if (request.getParameter("leaveType") != null) {
			leaveType = Integer.parseInt(request.getParameter("leaveType"));
		}
		int departmentId = Integer.parseInt(request.getParameter("hiddenDepartmentId"));
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		modelMap.addAttribute("ErrorMsg", "Start Date and End Date not Valid");
		/* return new ModelAndView("leavereport",modelMap); */
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
				jasperPath = path + "LeaveStatusReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "LeaveStatusReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "LeaveStatusReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
				jasperPath = path + "LeaveStatusReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "LeaveStatusReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "LeaveStatusReportSingle.xls"));
			} else {
				rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
				jasperPath = path + "LeaveStatusReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "LeaveStatusReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "LeaveStatusReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Leave Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("Dept_Name", departmentDAO.getDepartmentNameById(departmentId));
			parameters.put("Leave_Type", leaveTypeDAO.getLeaveTypeNameById(leaveType));
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();
			System.out.println("Generate Leave status report success");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		/* return new ModelAndView(new RedirectView("showleavereport.htm")); */
	}

	@RequestMapping(value = "viewEmployeeleaveStatus.htm", method = RequestMethod.GET)
	public ModelAndView viewEmployeeleaveStatus(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("departmentList", departmentDAO.getDepartment());
		modelMap.addAttribute("leaveTypeList", leaveTypeDAO.getleavetypes());
		return new ModelAndView("showemployeeleavestatus", modelMap);
	}

	@RequestMapping(value = "showEmployeeLeaveStatusDiv.htm", method = RequestMethod.POST)
	public ModelAndView showEmployeeLeaveStatus(@RequestParam(value = "leaveType") int leaveType,
			@RequestParam(value = "employeeNo") int workId, @RequestParam(value = "department") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("ErrorMsg", " ");
		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		/*
		 * int leaveType=0; if(request.getParameter("leaveType")!=null) {
		 * leaveType=Integer.parseInt(request.getParameter("leaveType")); }
		 */
		/* int departmentId=Integer.parseInt(request.getParameter("department")); */
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		modelMap.addAttribute("ErrorMsg", "Start Date and End Date not Valid");
		/* return new ModelAndView("leavereport",modelMap); */
		if (empReportType.equals("multiple")) {
			rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);

		} else if (empReportType.equals("single")) {
			/* workId=Integer.parseInt(request.getParameter("employeeNo")); */
			rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
		} else {
			rs = reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
		}
		System.out.println("asdfsfsdfsdf");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs.beforeFirst();

		modelMap.addAttribute("resultsetModelMap", rs);

		return new ModelAndView("showemployeeleavestatusDiv", modelMap);
	}

	@RequestMapping(value = "validateIP.htm", method = RequestMethod.POST)
	public void validateIP(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		try {
			String ipAddress = request.getParameter("ipAddress");
			out = response.getWriter();
			int size = addDeviceDAO.getDeviceInfoListByIp(ipAddress).size();
			System.out.println(size);
			out.write(size + "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return addDeviceDAO.getDeviceInfoListByIp(ipAddress).size();
	}

	@RequestMapping(value = "showDepartmentTree.htm", method = RequestMethod.POST)
	public ModelAndView showDepartmentTree(@RequestParam(value = "deptId") int deptId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Departments> departmentsList = departmentDAO.getDepartmentById(deptId);
		if (departmentsList.size() != 0) {
			int departmentId = departmentsList.get(0).getId();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			modelMap.addAttribute("division", division);
			modelMap.addAttribute("location", location);
			return new ModelAndView("showdepartmenttree", modelMap);
		} else {
			return new ModelAndView("");
		}
	}

	@RequestMapping(value = "showWorkspaceTree.htm", method = RequestMethod.POST)
	public ModelAndView showWorkspaceTree(@RequestParam(value = "workspaceId") int workspaceId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		if (workspaceId != 0) {
			Workspaces workspaces = workspacesDAO.getWorkspaceById(workspaceId);
			int departmentId = workspaces.getDepartment();
			Departments department = departmentDAO.getDepartmentById(departmentId).get(0);
			int divisionId = department.getDivision();
			Division division = divisionDAO.getDivision(divisionId);
			int locationId = division.getLocationId();
			Location location = locationDAO.getLocationObject(locationId);
			modelMap.addAttribute("location", location);
			modelMap.addAttribute("department", department);
			modelMap.addAttribute("division", division);
			return new ModelAndView("showworkspacetree", modelMap);
		} else {
			return new ModelAndView("");
		}
	}

	@RequestMapping(value = "showEmployeeFolderPath.htm", method = RequestMethod.GET)
	public ModelAndView showEmployeeFolderPath(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("showEmployeeFolderPath");
	}

	@RequestMapping(value = "showMasterSettings.htm", method = RequestMethod.GET)
	public ModelAndView showMasterSettings(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, MasterSettings masterSettings) {
		ModelMap modelMap = new ModelMap();
		List<CalNumbers> calNumbersList = calTimesDAO.getCalNumbers(31);
		modelMap.addAttribute("calNumbersList", calNumbersList);
		return new ModelAndView("showMasterSettings", modelMap);
	}

	@RequestMapping(value = "showDayDivAccMonth.htm", method = RequestMethod.POST)
	public ModelAndView showDayDivAccMonth(@RequestParam(value = "days") int days, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<CalNumbers> calNumbersList = calTimesDAO.getCalNumbers(days);
		modelMap.addAttribute("calNumbersList", calNumbersList);
		return new ModelAndView("showDayDivAccMonth", modelMap);
	}

	@RequestMapping(value = "addUpdateMasterSettings.htm", method = RequestMethod.POST)
	public ModelAndView addUpdateMasterSettings(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, MasterSettings masterSettings) {
		DbBackup dbBackup = new DbBackup();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		boolean backUpResult = dbBackup.backupDB("DISTNA", "root", "admin",
				"E:\\Distna_" + dateFormat.format(new Date()) + ".sql");
		if (backUpResult) {
			List<MasterSettings> masterSettingsList = masterSettingsDAO.getMasterSettings();
			if (masterSettingsList.size() > 0) {
				for (MasterSettings masterSettings2 : masterSettingsList) {
					int id = masterSettings2.getMasterId();
					masterSettings.setMasterId(id);
					masterSettings.setLastBackupDate(dateFormat.format(new Date()));
				}
			}
			masterSettingsDAO.saveOrUpdateMasterSettings(masterSettings);
		}
		return new ModelAndView(new RedirectView("showMasterSettings.htm"));
	}

	@RequestMapping(value = "showrestoredatabasebackup.htm", method = RequestMethod.GET)
	public ModelAndView showRestoreDatabaseBackup(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("restoredatabasebackup");
	}

	@RequestMapping(value = "restoredatabasebackup.htm", method = RequestMethod.POST)
	public ModelAndView restoreDatabaseBackup(@RequestParam(value = "databasePath") MultipartFile databaseFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		Properties properties = new Properties();
		DbBackup dbBackup = new DbBackup();

		InputStream inputStream = AdminController.class.getClassLoader().getResourceAsStream("database.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String dbusername = properties.getProperty("database.username");
		String encryptdPasswd = properties.getProperty("database.password");
		try {
			encryptPassword = new EncryptPassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dbPasswd = encryptPassword.decrypt(encryptdPasswd);

		ModelMap modelMap = new ModelMap();
		String extension = "";
		if (databaseFile.getSize() > 0) {
			extension = databaseFile.getOriginalFilename()
					.substring(databaseFile.getOriginalFilename().lastIndexOf("."));
			if (!extension.equalsIgnoreCase(".sql")) {
				modelMap.addAttribute("errorMessage", "Please select a valid database file.");
				return new ModelAndView("restoredatabasebackup", modelMap);
			}

			String tempDirectoryPath = request.getSession().getServletContext().getRealPath("\\tempDir");

			File tempDir = new File(tempDirectoryPath);

			if (!tempDir.exists()) {
				tempDir.mkdir();
			}

			String tempFileName = tempDirectoryPath + "\\" + databaseFile.getOriginalFilename();

			File tempFile = new File(tempFileName);

			try {
				databaseFile.transferTo(tempFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String jashdkjashd = tempFile.getAbsolutePath();

			boolean result = dbBackup.restoreDB("DISTNA", dbusername, dbPasswd, tempFile.getAbsolutePath());
			if (result) {
				modelMap.addAttribute("errorMessage", "Database restored successfully.");
				return new ModelAndView("restoredatabasebackup", modelMap);
			} else {
				modelMap.addAttribute("errorMessage", "Cound not restore database. Please try again.");
				return new ModelAndView("restoredatabasebackup", modelMap);
			}

//		 boolean status=dbbackup.restoreDB("distna",dbusername,dbPasswd,databaseFile.);   
		}
		return new ModelAndView("restoredatabasebackup");
	}

	@RequestMapping(value = "showOutdoorEntries.htm", method = RequestMethod.GET)
	public ModelAndView showOutdoorEntries(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<Departments> departmentList = departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		return new ModelAndView("showOutdoorEntries", modelMap);
	}

	@RequestMapping(value = "displaydepartmentwiseemployees.htm", method = RequestMethod.POST)
	public ModelAndView displayDepartmentWiseEmployees(@RequestParam("departmentId") int departmentId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		List<Employee> employeeListDept = employeeDAO.getEmployeeListByDepartment(departmentId);
		modelMap.addAttribute("employeeListDept", employeeListDept);
		modelMap.addAttribute("departmentId", departmentId);
		return new ModelAndView("displayDepartmentWiseEmployees", modelMap);
	}

	@RequestMapping(value = "saveOutdoorEntries.htm", method = RequestMethod.POST)
	public ModelAndView saveOutdoorEntries(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		int id = Integer.parseInt(request.getParameter("department"));
		int employeeId = Integer.parseInt(request.getParameter("employees"));
		String startDate = request.getParameter("datefrom");
		String endDate = request.getParameter("dateTo");
		Date originalStartDate = dateformat.parse(startDate);
		Date originalEndDate = dateformat.parse(endDate);
		startDate = format.format(originalStartDate);
		endDate = format.format(originalEndDate);
		saveAttendanceForOutDoorEntry(employeeId, startDate, endDate);
		/*
		 * List<String> recordDateList=new ArrayList<String>(); List<String>
		 * finalList=new ArrayList<String>(); for(AttendanceLogsOutdoorEntry
		 * attendanceLogsOutdoorEntry2:attendanceLogsOutdoorEntries) {
		 * recordDateList.add(attendanceLogsOutdoorEntry2.getRecordDate()); } for (int i
		 * = 0; i < dateRange.size(); i++){
		 * 
		 * if (!recordDateList.contains(dateRange.get(i))){
		 * 
		 * finalList.add(dateRange.get(i)); } }
		 * 
		 * for (int j = 0; j < recordDateList.size(); j++){
		 * 
		 * if (!dateRange.contains(recordDateList.get(j))) {
		 * finalList.add(recordDateList.get(j)); }
		 * 
		 * }
		 */

		return new ModelAndView(new RedirectView("showOutdoorEntries.htm"));
	}

	public void saveAttendanceForOutDoorEntry(int employeeId, String startDate, String endDate) {
		int shiftId = 0;
		// boolean dateFlag=false;
		List<ShiftAllocation> shiftAllocations = shiftAllocationDAO.getShiftAllocatedEmployeeList(employeeId);
		// List<AttendanceLogsOutdoorEntry>
		// attendanceLogsOutdoorEntries=attendanceLogsOutdoorEntryDAO.getAttendanceOutEntryList(employeeId);
		for (ShiftAllocation shiftAllocation : shiftAllocations) {
			shiftId = shiftAllocation.getShiftid();
		}

		List<String> dateRange = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
		Date startDt;
		Date endDt;
		try {
			startDt = sdf.parse(startDate);
			endDt = sdf.parse(endDate);
			calendar.setTime(startDt);
			while (calendar.getTime().before(endDt)) {
				Date resultado = calendar.getTime();
				String dateString = sdf.format(resultado);
				dateRange.add(dateString);
				calendar.add(Calendar.DATE, 1);
			}
			dateRange.add(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < dateRange.size(); i++) {
			boolean isRecordinOutdoorEntry = attendanceLogsOutdoorEntryDAO.isRecordAvailable(dateRange.get(i),
					employeeId);
			boolean isRecordinBulkEntry = attendanceLogsBulkEntryDAO.isRecordAvailable(dateRange.get(i), employeeId);
			if (isRecordinBulkEntry) {
				if (isRecordinOutdoorEntry) {
					AttendanceLogsOutdoorEntry attendanceLogsOutdoorEntry = new AttendanceLogsOutdoorEntry();
					attendanceLogsOutdoorEntry.setRecordDate(dateRange.get(i));
					attendanceLogsOutdoorEntry.setShift(shiftId);
					attendanceLogsOutdoorEntry.setTimeAsPerShftTimings("0:0:0");
					attendanceLogsOutdoorEntry.setWorkID(employeeId);
					attendanceLogsOutdoorEntry.setStatus(99);
					attendanceLogsOutdoorEntryDAO.saveOutdoorEntry(attendanceLogsOutdoorEntry);

					AttendanceLogsBulkEntry attendanceLogsBulkEntry = new AttendanceLogsBulkEntry();
					attendanceLogsBulkEntry.setRecordDate(dateRange.get(i));
					attendanceLogsBulkEntry.setWorkID(employeeId);
					attendanceLogsBulkEntry.setShift(shiftId);
					attendanceLogsBulkEntry.setStatus(99);
					attendanceLogsBulkEntry.setTimeAsPerShftTimings("0:0:0");
					attendanceLogsBulkEntry.setExceptionFlag(false);
					attendanceLogsBulkEntryDAO.saveAttendanceBulkEntryForOutdoor(attendanceLogsBulkEntry);
				}
			}
		}
	}

	@RequestMapping(value = "exportEmployeedata.htm", method = RequestMethod.GET)
	public ModelAndView exportEmployeedata(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		rs = reportDAO.getEmployeeTableForExport();
		FileWriter fw;
		ModelMap modelMap = new ModelMap();
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		// List <String> tableNameList = new ArrayList<String>();
		try {
			/*
			 * while(rs.next()) { tableNameList.add(rs.getString(1)); }
			 */

			// path to the folder where you will save your csv files

//         String filename = "D:/exportEmployeeFile/";
			String filename = request.getSession().getServletContext().getRealPath("");
			String filenameArray[] = filename.split(":");
			String folderPath = filenameArray[0] + ":\\Distna Exported Files";
			boolean flag = false;
			File file = new File(folderPath);
			if (!file.exists()) {
				flag = file.mkdir();
			}

			modelMap.addAttribute("filename", folderPath);

			// star iterating on each table to fetch its data and save in a .csv file
			/*
			 * for(String tableName:tableNameList) {
			 */
			int k = 0;

			int j = 1;

			// System.out.println(tableName);

			List<String> columnsNameList = new ArrayList<String>();

			// colunm count is necessay as the tables are dynamic and we need to figure out
			// the numbers of columns
			int colunmCount = getColumnCount(rs);

			fw = new FileWriter(folderPath + "\\Employee Records " + dateFormat.format(new Date()) + ".csv");
			for (int i = 1; i <= colunmCount; i++) {
				fw.append(rs.getMetaData().getColumnName(i));
				fw.append(",");

			}

			fw.append(System.getProperty("line.separator"));

			while (rs.next()) {
				for (int i = 1; i <= colunmCount; i++) {

					if (rs.getObject(i) != null) {
						String data = rs.getObject(i).toString();
						fw.append(data);
						fw.append(",");
					} else {
						String data = "null";
						fw.append(data);
						fw.append(",");
					}

				}
				// new line entered after each row
				fw.append(System.getProperty("line.separator"));
			}

			fw.flush();
			fw.close();
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ModelAndView("exportEmployeedata", modelMap);

	}

	public ModelMap exportTable(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String tableName) {
		rs = reportDAO.getTableForExport(tableName);
		FileWriter fw;
		ModelMap modelMap = new ModelMap();
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		// List <String> tableNameList = new ArrayList<String>();
		try {
			/*
			 * while(rs.next()) { tableNameList.add(rs.getString(1)); }
			 */

			// path to the folder where you will save your csv files

//         String filename = "D:/exportEmployeeFile/";
			String filename = request.getSession().getServletContext().getRealPath("");
			String filenameArray[] = filename.split(":");
			String folderPath = filenameArray[0] + ":\\Distna Exported Files";
			boolean flag = false;
			File file = new File(folderPath);
			if (!file.exists()) {
				flag = file.mkdir();
			}

			modelMap.addAttribute("filename", folderPath);

			// star iterating on each table to fetch its data and save in a .csv file
			/*
			 * for(String tableName:tableNameList) {
			 */
			int k = 0;

			int j = 1;

			// System.out.println(tableName);

			List<String> columnsNameList = new ArrayList<String>();

			// colunm count is necessay as the tables are dynamic and we need to figure out
			// the numbers of columns
			int colunmCount = getColumnCount(rs);

			fw = new FileWriter(folderPath + "\\" + tableName + " " + dateFormat.format(new Date()) + ".csv");
			for (int i = 1; i <= colunmCount; i++) {
				fw.append(rs.getMetaData().getColumnName(i));
				fw.append(",");

			}

			fw.append(System.getProperty("line.separator"));

			while (rs.next()) {
				for (int i = 1; i <= colunmCount; i++) {

					if (rs.getObject(i) != null) {
						String data = rs.getObject(i).toString();
						fw.append(data);
						fw.append(",");
					} else {
						String data = "null";
						fw.append(data);
						fw.append(",");
					}

				}
				// new line entered after each row
				fw.append(System.getProperty("line.separator"));
			}

			fw.flush();
			fw.close();
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelMap;
	}

	@RequestMapping(value = "exportVisitors.htm", method = RequestMethod.GET)
	public ModelAndView exportVisitors(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = exportTable(request, response, session, "visitors");
		modelMap = exportTable(request, response, session, "visitor_logs");
		return new ModelAndView("exportEmployeedata", modelMap);
	}

	public static int getRowCount(ResultSet res) throws SQLException {
		res.last();
		int numberOfRows = res.getRow();
		res.beforeFirst();
		return numberOfRows;
	}

	public static int getColumnCount(ResultSet res) throws SQLException {
		return res.getMetaData().getColumnCount();
	}

	@RequestMapping(value = "showImportEmployeedata.htm", method = RequestMethod.GET)
	public ModelAndView showImportEmployeedata(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// reportDAO.importEmployeeTableData();
		return new ModelAndView("showImportEmployeedata");
	}

	@RequestMapping(value = "importEmployeedata.htm", method = RequestMethod.POST)
	public ModelAndView importEmployeedata(@RequestParam(value = "csvFile") MultipartFile csvFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// System.out.println("file.getpath"+csvFile.g);
		String extention = csvFile.getOriginalFilename().substring(csvFile.getOriginalFilename().indexOf("."));
		boolean resultFlag = false;
		String destination = null;
		ModelMap modelMap = new ModelMap();
		if (csvFile.getSize() > 0 && extention.equals(".csv")) {
			destination = request.getSession().getServletContext().getRealPath("") + "\\EmployeeCsvFiles";
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(destination + "\\" + csvFile.getOriginalFilename());
			try {
				if (!file.exists()) {

					file.createNewFile();
				}
				csvFile.transferTo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultFlag = reportDAO.importEmployeeTableData(file.getAbsolutePath());
			if (resultFlag) {
				modelMap.addAttribute("importResultFlag", "File imported successfully!");
			} else {
				modelMap.addAttribute("importResultFlag", "Could not import file.");
			}

		}

		return new ModelAndView("showImportEmployeedata", modelMap);
	}

	@RequestMapping(value = "shiftWiseMonthlyBasicWorkDurReport.htm", method = RequestMethod.GET)
	public ModelAndView shiftWiseMonthlyBasicWorkDurReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("shiftWiseMonthlyBasicWorkDurReport", getshiftWiseReportModelMap());
	}

	@RequestMapping(value = "showImportTableData.htm", method = RequestMethod.GET)
	public ModelAndView showImportTabledata(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// reportDAO.importEmployeeTableData();
		return new ModelAndView("showimporttabledata");
	}

	@RequestMapping(value = "importTableData.htm", method = RequestMethod.POST)
	public ModelAndView importTableData(@RequestParam(value = "csvFile") MultipartFile csvFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// System.out.println("file.getpath"+csvFile.g);
		String extention = csvFile.getOriginalFilename().substring(csvFile.getOriginalFilename().indexOf("."));
		boolean resultFlag = false;
		String destination = null;
		ModelMap modelMap = new ModelMap();
		if (csvFile.getSize() > 0 && extention.equals(".csv")) {
			destination = request.getSession().getServletContext().getRealPath("") + "\\EmployeeCsvFiles";
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(destination + "\\" + csvFile.getOriginalFilename());
			try {
				if (!file.exists()) {

					file.createNewFile();
				}
				csvFile.transferTo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultFlag = reportDAO.importTableData(file.getAbsolutePath());
			if (resultFlag) {
				modelMap.addAttribute("importResultFlag", "File imported successfully!");
			} else {
				modelMap.addAttribute("importResultFlag", "Could not import file.");
			}

		}
		return new ModelAndView("showimporttabledata", modelMap);
	}

	@RequestMapping(value = "generateShiftWiseBasicWorkDurReport.htm", method = RequestMethod.POST)
	public void generateShiftWiseBasicWorkDurReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		String monthName = "invalid";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		monthName = months[Integer.parseInt(request.getParameter("iMonth"))];

		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		if (shiftId != 0) {
			ServletOutputStream servletOutputStream = null;
			try {
				servletOutputStream = response.getOutputStream();
				String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
				String exportPath = null;
				OutputStream exportPathXLS = null;
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShiftWiseBasicWorkDurReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShiftWiseBasicWorkDurReport.xls"));
				ResultSet rs = reportDAO.getmusterReportOfEmployees(shiftId, month, year);
				String jasperPath = path + "ShiftWiseBasicWorkDurReport.jrxml";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("imagePath",
						request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
				parameters.put("MonthName", monthName);
				parameters.put("YearName", year);
				parameters.put("Report_Title", "Shift Wise Basic Work Duration Report");
				rs.beforeFirst();
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				InputStream inputStream = new FileInputStream(jasperPath);
				// jasperDesign = JasperManager.loadXmlDesign(inputStream);
				jasperDesign = JRXmlLoader.load(inputStream);
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

				ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
				exporterXLS.exportReport();
				exportPathXLS.write(outputByteArray.toByteArray());

				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JRException e) {
				e.printStackTrace();
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "showMaharastraMusterRollReport.htm", method = RequestMethod.GET)
	public ModelAndView showMaharastraMusterRollReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getshiftWiseReportModelMap();
		return new ModelAndView("showMaharastraMusterRollReport", modelMap);
	}

	@RequestMapping(value = "generateMaharastraMusterRollReport.htm", method = RequestMethod.POST)
	public void generateMaharastraMusterRollReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType,
			@RequestParam(value = "reportType") String reportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		int workId = 0;
		ModelMap modelMap = getshiftWiseReportModelMap();
		int shiftId = Integer.parseInt(request.getParameter("shiftName"));
		modelMap.addAttribute("reportType", reportType);
		ServletOutputStream servletOutputStream = null;
		String month = (Integer.parseInt(request.getParameter("iMonth")) + 1) + "";
		String year = request.getParameter("iYear");
		String monthName = "invalid";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
				rs = reportDAO.getMaharastraMusterRollReport(shiftId, workId, empReportType, selectedcheckbox, month,
						year);
				jasperPath = path + "MaharastraMusterRollReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MaharastraMusterRollReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MaharastraMusterRollReportMultiple.xls"));

			} else if (empReportType.equals("single")) {
				workId = Integer.parseInt(request.getParameter("employeeNo"));
				rs = reportDAO.getMaharastraMusterRollReport(shiftId, workId, empReportType, selectedcheckbox, month,
						year);
				jasperPath = path + "MaharastraMusterRollReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MaharastraMusterRollReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MaharastraMusterRollReportSingle.xls"));
			} else {
				rs = reportDAO.getMaharastraMusterRollReport(shiftId, workId, empReportType, selectedcheckbox, month,
						year);
				jasperPath = path + "MaharastraMusterRollReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "MaharastraMusterRollReport.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "MaharastraMusterRollReport.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("Report_Title", "Maharastra Muster Roll report");
			parameters.put("MonthName", monthName);
			parameters.put("YearName", year);
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

		// return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}

	@RequestMapping(value = "showaddcanteenitems.htm", method = RequestMethod.GET)
	public ModelAndView showAddCanteenItems(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenItems canteenItems) {
		return new ModelAndView("addcanteenitems");
	}

	@RequestMapping(value = "addcanteenitems.htm", method = RequestMethod.POST)
	public ModelAndView addCanteenItems(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CanteenItems canteenItems, BindingResult bindingResult) {
		canteenItemsValidator.validate(canteenItems, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addcanteenitems");
		}
		canteenItemsDAO.saveCanteenItems(canteenItems);
		return new ModelAndView(new RedirectView("showaddcanteenitems.htm"));
	}

	@RequestMapping(value = "showviewcanteenitems.htm", method = RequestMethod.GET)
	public ModelAndView showViewCanteenItems(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenItems canteenItems) {
		ModelMap modelMap = new ModelMap();
		List<CanteenItems> canteenItemsList = canteenItemsDAO.getCanteenItemsList();
		modelMap.addAttribute("canteenItemsList", canteenItemsList);
		modelMap.addAttribute("canteenItemsListSize", canteenItemsList.size());

		return new ModelAndView("viewcanteenitems", modelMap);
	}

	@RequestMapping(value = "showupdatecanteenitems.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateCanteenItems(@RequestParam("Id") int canteenItemId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("canteenItems", canteenItemsDAO.getCanteenItem(canteenItemId));
		return new ModelAndView("updatecanteenitems", modelMap);
	}

	@RequestMapping(value = "updatecanteenitems.htm", method = RequestMethod.POST)
	public ModelAndView updateCanteenItems(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenItems canteenItems, BindingResult bindingResult) {
		canteenItemsValidator.validate(canteenItems, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addcanteenitems");
		}
		canteenItemsDAO.updateCanteenItems(canteenItems);
		return new ModelAndView(new RedirectView("showviewcanteenitems.htm"));
	}

	@RequestMapping(value = "deletecanteenitems.htm", method = RequestMethod.POST)
	public ModelAndView deleteCanteenItems(@RequestParam("Id") int canteenItemId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		canteenItemsDAO.deleteCanteenItems(canteenItemId);
		return new ModelAndView(new RedirectView("showviewcanteenitems.htm"));
	}

	public ModelMap getCanteenTimingsModelMap() {
		ModelMap modelMap = new ModelMap();
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		List<CanteenItems> canteenItemsList = canteenItemsDAO.getCanteenItemsList();
		modelMap.addAttribute("canteenItemsList", canteenItemsList);

		return modelMap;
	}

	@RequestMapping(value = "showaddcanteentimings.htm", method = RequestMethod.GET)
	public ModelAndView showAddCanteenTimings(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenTimings canteenTimings) {
		ModelMap modelMap = getCanteenTimingsModelMap();
		return new ModelAndView("addcanteentimings", modelMap);
	}

	@RequestMapping(value = "addcanteentimings.htm", method = RequestMethod.POST)
	public ModelAndView addCanteenTimings(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CanteenTimings canteenTimings, BindingResult bindingResult) {
		ModelMap modelMap = getCanteenTimingsModelMap();
		DateFormat currentDateFormat = new SimpleDateFormat("HH:mm:ss");
		canteenTimingsValidator.validate(canteenTimings, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addcanteentimings", modelMap);
		}

		List<CanteenTimings> canteenTimingsList = canteenTimingsDAO.getCanteenTimingsList();

		for (CanteenTimings canteenTimingsIndex : canteenTimingsList) {
			try {
				if (currentDateFormat.parse(canteenTimings.getStartTime())
						.after(currentDateFormat.parse(canteenTimingsIndex.getStartTime()))
						&& currentDateFormat.parse(canteenTimings.getStartTime())
								.before(currentDateFormat.parse(canteenTimingsIndex.getEndTime()))) {
					modelMap.addAttribute("startTimeError",
							"Another canteen-timing is already present in this timespan. Please select a different start time");
					return new ModelAndView("addcanteentimings", modelMap);
				} else {
					modelMap.addAttribute("startTimeError", "");
				}
				if (currentDateFormat.parse(canteenTimings.getEndTime())
						.after(currentDateFormat.parse(canteenTimingsIndex.getStartTime()))
						&& currentDateFormat.parse(canteenTimings.getEndTime())
								.before(currentDateFormat.parse(canteenTimingsIndex.getEndTime()))) {
					modelMap.addAttribute("endTimeError",
							"Another canteen-timing is already present in this timespan. Please select a different end time");
					return new ModelAndView("addcanteentimings", modelMap);
				} else {
					modelMap.addAttribute("endTimeError", "");
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		canteenTimingsDAO.saveCanteenTimings(canteenTimings);
		return new ModelAndView(new RedirectView("showaddcanteentimings.htm"));
	}

	@RequestMapping(value = "showviewcanteentimings.htm", method = RequestMethod.GET)
	public ModelAndView showViewCanteenTimings(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenTimings canteenTimings) {
		ModelMap modelMap = getCanteenTimingsModelMap();
		List<CanteenTimings> canteenTimingsList = canteenTimingsDAO.getCanteenTimingsList();
		modelMap.addAttribute("canteenTimingsList", canteenTimingsList);
		modelMap.addAttribute("canteenTimingsListSize", canteenTimingsList.size());
		return new ModelAndView("viewcanteentimings", modelMap);
	}

	@RequestMapping(value = "showupdatecanteentimings.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateCanteenTimings(@RequestParam("Id") int canteenTimingsId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getCanteenTimingsModelMap();
		modelMap.addAttribute("canteenTimings", canteenTimingsDAO.getCanteenTiming(canteenTimingsId));
		return new ModelAndView("updatecanteentimings", modelMap);
	}

	@RequestMapping(value = "updatecanteentimings.htm", method = RequestMethod.POST)
	public ModelAndView updateCanteenTimings(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, CanteenTimings canteenTimings, BindingResult bindingResult) {

		ModelMap modelMap = getCanteenTimingsModelMap();
		DateFormat currentDateFormat = new SimpleDateFormat("HH:mm:ss");

		canteenTimingsValidator.validate(canteenTimings, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updatecanteentimings", modelMap);
		}

		List<CanteenTimings> canteenTimingsList = canteenTimingsDAO.getCanteenTimingsList();

		for (CanteenTimings canteenTimingsIndex : canteenTimingsList) {
			try {
				if (canteenTimings.getTimingId() != canteenTimingsIndex.getTimingId()
						&& currentDateFormat.parse(canteenTimings.getStartTime())
								.after(currentDateFormat.parse(canteenTimingsIndex.getStartTime()))
						&& currentDateFormat.parse(canteenTimings.getStartTime())
								.before(currentDateFormat.parse(canteenTimingsIndex.getEndTime()))) {
					modelMap.addAttribute("startTimeError",
							"Another canteen-timing is already present in this timespan. Please select a different start time");
					return new ModelAndView("updatecanteentimings", modelMap);
				} else {
					modelMap.addAttribute("startTimeError", "");
				}
				if (canteenTimings.getTimingId() != canteenTimingsIndex.getTimingId()
						&& currentDateFormat.parse(canteenTimings.getEndTime())
								.after(currentDateFormat.parse(canteenTimingsIndex.getStartTime()))
						&& currentDateFormat.parse(canteenTimings.getEndTime())
								.before(currentDateFormat.parse(canteenTimingsIndex.getEndTime()))) {
					modelMap.addAttribute("endTimeError",
							"Another canteen-timing is already present in this timespan. Please select a different end time");
					return new ModelAndView("updatecanteentimings", modelMap);
				} else {
					modelMap.addAttribute("endTimeError", "");
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		canteenTimingsDAO.updateCanteenTimings(canteenTimings);
		return new ModelAndView(new RedirectView("showviewcanteentimings.htm"));
	}

	@RequestMapping(value = "deletecanteentimings.htm", method = RequestMethod.POST)
	public ModelAndView deleteCanteenTimings(@RequestParam("Id") int canteenItemId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		canteenTimingsDAO.deleteCanteenTimings(canteenItemId);
		return new ModelAndView(new RedirectView("showviewcanteentimings.htm"));
	}

	@RequestMapping(value = "canteenitemsreport.htm", method = RequestMethod.GET)
	public void generateCanteenItemsReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();

			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "CanteenItemsReport.xls"));
			ResultSet rs = reportDAO.getCanteenItemsReport();
			String jasperPath = path + "CanteenItemsReport.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("reportTitle", "Canteen Items Report");
			rs.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "CanteenItemsReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "canteentimingsreport.htm", method = RequestMethod.GET)
	public void generateCanteenTimingsReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();

			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
			String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "CanteenTimingsReport.xls"));
			ResultSet rs = reportDAO.getCanteenTimingsReport();
			String jasperPath = path + "CanteenTimingsReport.jrxml";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("reportTitle", "Canteen Timings Report");
			rs.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "CanteenTimingsReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "showaddvisitorlogs.htm", method = RequestMethod.GET)
	public ModelAndView showaddvisitorlogs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VisitorLogs visitorLogs) {
		ModelMap modelMap = new ModelMap();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
		modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
		return new ModelAndView("showaddvisitorlogs", modelMap);
	}

	@RequestMapping(value = "addvisitorlogs.htm", method = RequestMethod.GET)
	public ModelAndView addvisitorlogs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			VisitorLogs visitorLogs, BindingResult bindingResult) {
		VisitorLogsValidator visitorLogsValidator = new VisitorLogsValidator();
		visitorLogsValidator.validate(visitorLogs, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = new ModelMap();
			List<Employee> employeeList = employeeDAO.getEmployeeList();
			modelMap.addAttribute("employeeList", employeeList);
			List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
			modelMap.addAttribute("calTimesList", calTimesList);
			List<Visitors> visitorsList = visitorsDAO.getVisitors();
			modelMap.addAttribute("visitorsList", visitorsList);
			modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
			modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
			return new ModelAndView("showaddvisitorlogs", modelMap);
		} else {
			String dateFrom = request.getParameter("datefrom");
			String dateTo = request.getParameter("dateTo");
			String inTime = visitorLogs.getInTime();
			// String outTime=visitorLogs.getOutTime();
			visitorLogs.setInTime(dateFrom + " " + inTime);
			// visitorLogs.setOutTime(dateTo+" "+outTime);
			visitorLogsDAO.saveVisitorLogs(visitorLogs);
			Visitors visitors = visitorsDAO.getVisitor(visitorLogs.getVisitorId());
			visitors.setVisitCount(visitors.getVisitCount() + 1);
			visitorsDAO.updateVisitor(visitors);
			return new ModelAndView(new RedirectView("showaddvisitorlogs.htm"));
		}
	}

	@RequestMapping(value = "viewvisitorlogs.htm", method = RequestMethod.GET)
	public ModelAndView viewvisitorlogs(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<VisitorLogs> visitorLogsList = visitorLogsDAO.getVisitorLogsList();
		modelMap.addAttribute("visitorLogsList", visitorLogsList);
		modelMap.addAttribute("visitorLogsListSize", visitorLogsList.size());
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		return new ModelAndView("viewvisitorlogs", modelMap);
	}

	@RequestMapping(value = "displayvisitorsrarchresults.htm", method = RequestMethod.POST)
	public ModelAndView displayVisitorSearchResults(@RequestParam(value = "visitorSearchName") String visitorSearchName,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<VisitorLogs> visitorLogsList = visitorLogsDAO.getVisitorLogsByVisitorName(visitorSearchName);
		if (visitorLogsList == null) {
			modelMap.addAttribute("visitorSearchErrorMessage", "No visitor found.");
			return new ModelAndView("visitorlogssearchresults", modelMap);
		}
		modelMap.addAttribute("visitorSearchErrorMessage", "pass");
		modelMap.addAttribute("visitorLogsList", visitorLogsList);
		modelMap.addAttribute("visitorLogsListSize", visitorLogsList.size());
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		return new ModelAndView("visitorlogssearchresults", modelMap);
	}

	@RequestMapping(value = "showupdatevisitorlogs.htm", method = RequestMethod.POST)
	public ModelAndView showupdatevisitorlogs(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<VisitorLogs> visitorLogsList = visitorLogsDAO.getVisitorLogsList(id);
		modelMap.addAttribute("visitorLogs", visitorLogsList.get(0));
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
		modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
		return new ModelAndView("showupdatevisitorlogs", modelMap);
	}

	@RequestMapping(value = "updatevisitorlogs.htm", method = RequestMethod.GET)
	public ModelAndView updatevisitorlogs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			VisitorLogs visitorLogs) {
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String inTime = visitorLogs.getInTime();
		String outTime = visitorLogs.getOutTime();
		visitorLogs.setInTime(dateFrom + " " + inTime);
		visitorLogs.setOutTime(dateTo + " " + outTime);
		visitorLogsDAO.updateVisitorLogs(visitorLogs);
		int oldVisitorId = Integer.parseInt(request.getParameter("oldVisitorId"));
		int newVisitorId = visitorLogs.getVisitorId();
		if (oldVisitorId != newVisitorId) {
			Visitors visitors = visitorsDAO.getVisitor(newVisitorId);
			visitors.setVisitCount(visitors.getVisitCount() + 1);
			visitorsDAO.updateVisitor(visitors);
			Visitors visitors2 = visitorsDAO.getVisitor(oldVisitorId);
			visitors.setVisitCount(visitors.getVisitCount() - 1);
			visitorsDAO.updateVisitor(visitors2);
		}

		return new ModelAndView(new RedirectView("viewvisitorlogs.htm"));
	}

	@RequestMapping(value = "updateOutTimeVisitorLogs.htm", method = RequestMethod.GET)
	public ModelAndView updateOutTimeVisitorLogs(@RequestParam(value = "logId") int logId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date datenow = calendar.getTime();
		String outDate = format.format(datenow);
		VisitorLogs visitorLogs = visitorLogsDAO.getVisitorLogsList(logId).get(0);
		visitorLogs.setOutTime(outDate);
		visitorLogsDAO.updateVisitorLogs(visitorLogs);
		return new ModelAndView(new RedirectView("viewvisitorlogs.htm"));
	}

	@RequestMapping(value = "deletevisitorlogs.htm", method = RequestMethod.POST)
	public ModelAndView deletevisitorlogs(@RequestParam(value = "Id") int id, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		visitorLogsDAO.deleteVisitorLogs(id);
		return new ModelAndView(new RedirectView("viewvisitorlogs.htm"));
	}

	public ModelMap getVisitorsModelMap() {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("visitorTypeList", visitorsDAO.getVisitorTypeList());
		modelMap.addAttribute("locationList", locationDAO.getLocation());
		return modelMap;
	}

	@RequestMapping(value = "showaddvisitors.htm", method = RequestMethod.GET)
	public ModelAndView showAddVisitors(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Visitors visitors) {
		ModelMap modelMap = getVisitorsModelMap();
		return new ModelAndView("addvisitors", modelMap);
	}

	@RequestMapping(value = "addvisitors.htm", method = RequestMethod.POST)
	public ModelAndView addVisitors(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Visitors visitors, BindingResult bindingResult) {
		ModelMap modelMap = getVisitorsModelMap();
		VisitorValidator visitorValidator = new VisitorValidator();
		visitorValidator.validate(visitors, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addvisitors", modelMap);
		} else {
			if (request.getParameter("visitorTypeSelect") != null
					&& !request.getParameter("visitorTypeSelect").equals("0")) {
				visitors.setVisitorType(request.getParameter("visitorTypeSelect"));
			}
			int visitorId = visitorsDAO.saveVisitor(visitors);
			String projectPath = request.getSession().getServletContext().getRealPath("");

			String jarPath = projectPath + "\\PhotoCaptureJar\\";

			String visitorPhotoPath = projectPath + "\\VisitorPhotos";
			File file = new File(visitorPhotoPath);
			if (!file.exists()) {
				file.mkdir();
			}

			String filepath = file.getAbsolutePath();

			try {
				Runtime.getRuntime().exec("java -jar \"" + jarPath + "\\CaptureVisitorPhoto.jar\" " + visitorId + " \""
						+ filepath + "\"");
				visitors.setVisitorPhoto("VisitorPhotos/" + visitorId + ".jpg");
				visitorsDAO.updateVisitor(visitors);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView(new RedirectView("showaddvisitors.htm"), modelMap);
		}
	}

	@RequestMapping(value = "savevisitortype.htm", method = RequestMethod.POST)
	public ModelAndView showVisitorType(@RequestParam(value = "visitorTypeString") String visitorTypeString,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		visitorsDAO.saveVisitorType(visitorTypeString);
		ModelMap modelMap = getVisitorsModelMap();
		return new ModelAndView("visitortypedropdown", modelMap);
	}

	@RequestMapping(value = "viewvisitors.htm", method = RequestMethod.GET)
	public ModelAndView viewVisitors(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Visitors visitors) {
		ModelMap modelMap = getVisitorsModelMap();
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		modelMap.addAttribute("visitorsListSize", visitorsList.size());
		return new ModelAndView("viewvisitors", modelMap);
	}

	@RequestMapping(value = "showupdatevisitors.htm", method = RequestMethod.POST)
	public ModelAndView showUpdateVisitors(@RequestParam("Id") int visitorId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getVisitorsModelMap();
		modelMap.addAttribute("visitors", visitorsDAO.getVisitor(visitorId));
		return new ModelAndView("updatevisitor", modelMap);
	}

	@RequestMapping(value = "updatevisitors.htm", method = RequestMethod.POST)
	public ModelAndView updatevisitors(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Visitors visitors, BindingResult bindingResult) {
		VisitorValidator visitorValidator = new VisitorValidator();
		visitorValidator.validate(visitors, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getVisitorsModelMap();
			modelMap.addAttribute("visitors", visitors);
			return new ModelAndView("updatevisitor", modelMap);
		} else {
			if (request.getParameter("visitorTypeSelect") != null
					&& !request.getParameter("visitorTypeSelect").equals("0")) {
				visitors.setVisitorType(request.getParameter("visitorTypeSelect"));
			}
			visitorsDAO.updateVisitor(visitors);
			return new ModelAndView(new RedirectView("viewvisitors.htm"));
		}
	}

	@RequestMapping(value = "PhotoCapture.htm", method = RequestMethod.GET)
	public ModelAndView PhotoCaputr(@RequestParam(value = "updateVisitorId") int visitorId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		return new ModelAndView("PhotoCapture");

	}

	@RequestMapping(value = "PhotoCapture.htm", method = RequestMethod.POST)
	public ModelAndView PhotoCaputrPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("In Post function");
		try {
			StringBuffer buffer = new StringBuffer();
			Reader reader = request.getReader();
			int current;

			while ((current = reader.read()) >= 0)
				buffer.append((char) current);

			String data = new String(buffer);
			data = data.substring(data.indexOf(",") + 1);

			System.out.println("PNG image data on Base64: " + data);

			FileOutputStream output = new FileOutputStream(
					new File("/C:/repositorio/" + new Random().nextInt(100000) + ".png"));

			output.write(new BASE64Decoder().decodeBuffer(data));
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return new ModelAndView(new RedirectView("viewvisitors.htm"));
		return new ModelAndView("viewvisitors");
	}

	@RequestMapping(value = "updatevisitorphoto.htm", method = RequestMethod.GET)
	public ModelAndView updateVisitorPhoto(@RequestParam(value = "updateVisitorId") int visitorId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ModelMap modelMap = getVisitorsModelMap();
		Visitors visitors = visitorsDAO.getVisitor(visitorId);
		modelMap.addAttribute("visitors", visitors);

		String projectPath = request.getSession().getServletContext().getRealPath("");
		String jarPath = projectPath + "\\PhotoCaptureJar\\";

		String visitorPhotoPath = projectPath + "\\VisitorPhotos";
		File file = new File(visitorPhotoPath);
		if (!file.exists()) {
			file.mkdir();
		}

		String filepath = file.getAbsolutePath();

		try {
			Runtime.getRuntime().exec(
					"java -jar \"" + jarPath + "\\CaptureVisitorPhoto.jar\" " + visitorId + " \"" + filepath + "\"");
			visitors.setVisitorPhoto("VisitorPhotos/" + visitorId + ".jpg");
			visitorsDAO.updateVisitor(visitors);
			modelMap.addAttribute("visitorErrorMessage", "Photo updated successfully");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.addAttribute("visitorErrorMessage", "Error updating photo. Please try again.");
		}
		return new ModelAndView("updatevisitor", modelMap);
		// return new ModelAndView(new
		// RedirectView("showupdatevisitors.htm?Id="+visitorId));
	}

	@RequestMapping(value = "deletevisitors.htm", method = RequestMethod.POST)
	public ModelAndView deletevisitors(@RequestParam("Id") int visitorId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		visitorsDAO.deleteVisitor(visitorId);
		return new ModelAndView(new RedirectView("viewvisitors.htm"));
	}

	@RequestMapping(value = "showaddvehicledetails.htm", method = RequestMethod.GET)
	public ModelAndView showaddvehicledetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleDetails vehicleDetails) {
		return new ModelAndView("showaddvehicledetails");
	}

	@RequestMapping(value = "addvehicledetails.htm", method = RequestMethod.GET)
	public ModelAndView addvehicledetails(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			VehicleDetails vehicleDetails, BindingResult bindingResult) {
		vehicleDetailsValidator.validate(vehicleDetails, bindingResult);
		if (bindingResult.hasErrors()) {
			return new ModelAndView("showaddvehicledetails");
		}
		vehicleDetailsDAO.saveVehicleDetails(vehicleDetails);
		return new ModelAndView(new RedirectView("showaddvehicledetails.htm"));
	}

	@RequestMapping(value = "viewvehicledetails.htm", method = RequestMethod.GET)
	public ModelAndView viewvehicledetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = new ModelMap();
		List<VehicleDetails> vehicledetailsList = vehicleDetailsDAO.getVehicleDetailsList();
		modelMap.addAttribute("vehicledetailsList", vehicledetailsList);
		modelMap.addAttribute("vehicledetailsListSize", vehicledetailsList.size());
		return new ModelAndView("viewvehicledetails", modelMap);
	}

	@RequestMapping(value = "showupdatevehicledetails.htm", method = RequestMethod.POST)
	public ModelAndView showupdatevehicledetails(@RequestParam("Id") int vehicleId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("vehicleDetails", vehicleDetailsDAO.getVehicleDetails(vehicleId));
		return new ModelAndView("showupdatevehicledetails", modelMap);
	}

	@RequestMapping(value = "updatevehicledetails.htm", method = RequestMethod.GET)
	public ModelAndView updatevehicledetails(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleDetails vehicleDetails, BindingResult bindingResult) {
		ModelMap modelMap = new ModelMap();
		vehicleDetailsValidator.validate(vehicleDetails, bindingResult);
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("vehicleDetails", vehicleDetails);
			return new ModelAndView("showupdatevehicledetails", modelMap);
		}

		vehicleDetailsDAO.updateVehicleDetails(vehicleDetails);
		return new ModelAndView(new RedirectView("viewvehicledetails.htm"));
	}

	@RequestMapping(value = "deletevehicledetails.htm", method = RequestMethod.POST)
	public ModelAndView deletevehicledetails(@RequestParam("Id") int vehicleId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		vehicleDetailsDAO.deleteVehicleDetails(vehicleId);
		return new ModelAndView(new RedirectView("viewvehicledetails.htm"));
	}

	public ModelMap getVehicleLogsModelMap() {
		ModelMap modelMap = new ModelMap();
		List<VehicleDetails> vehicledetailsList = vehicleDetailsDAO.getVehicleDetailsList();
		modelMap.addAttribute("vehicledetailsList", vehicledetailsList);
		List<VehicleLogs> vehicleLogsList = vehicleLogsDAO.getVehicleLogsList();
		modelMap.addAttribute("vehicleLogsList", vehicleLogsList);
		modelMap.addAttribute("vehicleLogsListSize", vehicleLogsList.size());
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<CalTimes> calTimesList = calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
		modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
		return modelMap;
	}

	@RequestMapping(value = "showaddvehiclelogs.htm", method = RequestMethod.GET)
	public ModelAndView showaddvehiclelogs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleLogs vehicleLogs) {
		ModelMap modelMap = getVehicleLogsModelMap();
		return new ModelAndView("showaddvehiclelogs", modelMap);
	}

	@RequestMapping(value = "addvehiclelogs.htm", method = RequestMethod.GET)
	public ModelAndView addvehiclelogs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			VehicleLogs vehicleLogs, BindingResult bindingResult) {
		VehicleLogsValidator vehicleLogsValidator = new VehicleLogsValidator();
		vehicleLogsValidator.validate(vehicleLogs, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getVehicleLogsModelMap();
			return new ModelAndView("showaddvehiclelogs", modelMap);
		} else {
			String dateFrom = request.getParameter("datefrom");
			String dateTo = request.getParameter("dateTo");
			String inTime = vehicleLogs.getInTime();
			String outTime = vehicleLogs.getOutTime();
			vehicleLogs.setInTime(dateFrom + " " + inTime);
			vehicleLogs.setOutTime(dateTo + " " + outTime);
			vehicleLogsDAO.saveVehicleLogs(vehicleLogs);
			return new ModelAndView(new RedirectView("showaddvehiclelogs.htm"));
		}
	}

	@RequestMapping(value = "viewvehiclelogs.htm", method = RequestMethod.GET)
	public ModelAndView viewvehiclelogs(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getVehicleLogsModelMap();
		return new ModelAndView("viewvehiclelogs", modelMap);
	}

	@RequestMapping(value = "showupdatevehiclelogs.htm", method = RequestMethod.POST)
	public ModelAndView showupdatevehiclelogs(@RequestParam("Id") int logId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = getVehicleLogsModelMap();
		modelMap.addAttribute("vehicleLogs", vehicleLogsDAO.getVehicleLogs(logId));
		modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
		modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
		return new ModelAndView("showupdatevehiclelogs", modelMap);
	}

	@RequestMapping(value = "updatevehiclelogs.htm", method = RequestMethod.GET)
	public ModelAndView updatevehiclelogs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			VehicleLogs vehicleLogs, BindingResult bindingResult) {
		VehicleLogsValidator vehicleLogsValidator = new VehicleLogsValidator();
		vehicleLogsValidator.validate(vehicleLogs, bindingResult);
		if (bindingResult.hasErrors()) {
			ModelMap modelMap = getVehicleLogsModelMap();
			modelMap.addAttribute("vehicleLogs", vehicleLogs);
			modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
			modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
			return new ModelAndView("showupdatevehiclelogs", modelMap);
		} else {
			String dateFrom = request.getParameter("datefrom");
			String dateTo = request.getParameter("dateTo");
			String inTime = vehicleLogs.getInTime();
			String outTime = vehicleLogs.getOutTime();
			vehicleLogs.setInTime(dateFrom + " " + inTime);
			vehicleLogs.setOutTime(dateTo + " " + outTime);
			vehicleLogsDAO.updateVehicleLogs(vehicleLogs);
			return new ModelAndView(new RedirectView("viewvehiclelogs.htm"));
		}
	}

	@RequestMapping(value = "deletevehiclelogs.htm", method = RequestMethod.POST)
	public ModelAndView deletevehiclelogs(@RequestParam("Id") int logId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		vehicleLogsDAO.deleteVehicleLogs(logId);
		return new ModelAndView(new RedirectView("viewvehiclelogs.htm"));
	}

	public ModelMap getApprovedVisitorMOdelMAp() {
		ModelMap modelMap = new ModelMap();
		List<VisitorLogs> visitorLogsList = visitorLogsDAO.getVisitorLogsApprovedEmployeeList();
		List<Visitors> visitorList = visitorsDAO.getVisitors();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("visitorLogsList", visitorLogsList);
		modelMap.addAttribute("visitorList", visitorList);
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showApprovedVisitors.htm", method = RequestMethod.GET)
	public ModelAndView showapprovedvisitors(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		return new ModelAndView("showapprovedvisitors", getApprovedVisitorMOdelMAp());
	}

	@RequestMapping(value = "changeApprovedVisitorStatus.htm", method = RequestMethod.POST)
	public ModelAndView changeApprovedVisitorStatus(@RequestParam(value = "visitorLogId") int visitorLogId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		visitorLogsDAO.updateVisitorLogs(visitorLogId);
		return new ModelAndView("showapprovedvisitors", getApprovedVisitorMOdelMAp());
	}

	@RequestMapping(value = "userDashboardVisitors.htm", method = RequestMethod.POST)
	public ModelAndView userDashnoardVisitors(@RequestParam(value = "employeeId") int employeeId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("visitorLogsList", visitorLogsDAO.getVisitorLogsListAccEmployee(employeeId));
		modelMap.addAttribute("visitorLogsListSize", visitorLogsDAO.getVisitorLogsListAccEmployee(employeeId).size());
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		modelMap.addAttribute("visitorsList", visitorsList);
		return new ModelAndView("userDashboardVisitors", modelMap);
	}

	@RequestMapping(value = "updateApprovalStatusvisitorlogs.htm", method = RequestMethod.POST)
	public ModelAndView updateApprovalStatusvisitorlogs(@RequestParam("logId") int logId,
			@RequestParam("status") String status, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		VisitorLogs visitorLogs = visitorLogsDAO.getVisitorLogsList(logId).get(0);
		visitorLogs.setApprovalStatus(status);
		visitorLogsDAO.updateVisitorLogs(visitorLogs);
		return new ModelAndView(new RedirectView("showMyUserDashboard.htm"));
	}

	@RequestMapping(value = "showvehiclelogswophoto.htm", method = RequestMethod.GET)
	public ModelAndView showVehicleLogsWOPhoto(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleLogs vehicleLogs) {
		ModelMap modelMap = new ModelMap();
		List<VehicleDetails> vehicleList = vehicleDetailsDAO.getVehicleDetailsList();
		modelMap.addAttribute("vehicleList", vehicleList);
		List<String> uniqueVehicleTypeList = vehicleDetailsDAO.getUniqueTypeVehicleList();
		modelMap.addAttribute("uniqueVehicleTypeList", uniqueVehicleTypeList);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return new ModelAndView("showvehiclelogswophoto", modelMap);
	}

	@RequestMapping(value = "generatevehiclelogswophotoreport.htm", method = RequestMethod.POST)
	public void generateVehicleLogsWoPhotoReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		ModelMap modelMap = new ModelMap();
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String concernPerson = request.getParameter("concernPerson");
		String status = request.getParameter("status");
		String filter = request.getParameter("filter");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String vehicleType = request.getParameter("vehicleType");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			rs = reportDAO.getVehicleLogsWoPhotoReport(dateFrom, dateTo, concernPerson, status, filter, vehicleNumber,
					vehicleType);

			jasperPath = path + "VehicleLogsWoPhotoReport.jrxml";
			exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
					+ "VehicleLogsWoPhotoReport.pdf";
			exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VehicleLogsWoPhotoReport.xls"));

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportTitle", "Vehicle Logs Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "showvehiclefrequencyreport.htm", method = RequestMethod.GET)
	public ModelAndView showVehicleFrequencyReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleLogs vehicleLogs) {
		ModelMap modelMap = new ModelMap();
		List<VehicleDetails> vehicleList = vehicleDetailsDAO.getVehicleDetailsList();
		modelMap.addAttribute("vehicleList", vehicleList);
		List<String> uniqueVehicleTypeList = vehicleDetailsDAO.getUniqueTypeVehicleList();
		modelMap.addAttribute("uniqueVehicleTypeList", uniqueVehicleTypeList);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return new ModelAndView("showvehiclefrequencyreport", modelMap);
	}

	@RequestMapping(value = "generatevehiclefrequencyreport.htm", method = RequestMethod.POST)
	public void generateVehicleFrequencyReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		ModelMap modelMap = new ModelMap();
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String concernPerson = request.getParameter("concernPerson");
		String status = request.getParameter("status");
		String filter = request.getParameter("filter");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String vehicleType = request.getParameter("vehicleType");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			rs = reportDAO.getVehicleFrequencyLogs(dateFrom, dateTo, concernPerson, status, filter, vehicleNumber,
					vehicleType);

			jasperPath = path + "VehicleLogsFrequencyReport.jrxml";
			exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
					+ "VehicleLogsFrequencyReport.pdf";
			exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VehicleLogsFrequencyReport.xls"));

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportTitle", "Vehicle Logs Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "departmentwiseextraworkreport.htm", method = RequestMethod.GET)
	public ModelAndView departmentWiseExtraWorkReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("extraworkreport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "generatedepartmentwiseextraworkreport.htm", method = RequestMethod.POST)
	public void generateDepartmentWiseExtraWorkReport(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int employeeId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
//			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
				rs = reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				jasperPath = path + "ExtraWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ExtraWorkReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ExtraWorkReportMultiple.xls"));
			}

			else if (empReportType.equals("single")) {
				employeeId = Integer.parseInt(request.getParameter("employeeNo"));
//			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
				rs = reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				jasperPath = path + "ExtraWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ExtraWorkReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ExtraWorkReportSingle.xls"));
			} else {
//			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
				rs = reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);
				jasperPath = path + "ExtraWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ExtraWorkReportAll.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ExtraWorkReportAll.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Extra Work report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("Department_Name", departmentDAO.getDepartmentById(departmentId).get(0).getName());
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

//		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}

	@RequestMapping(value = "departmentwiseshortworkreport.htm", method = RequestMethod.GET)
	public ModelAndView departmentWiseShortWorkReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("shortworkreport", getDepartmentWiseReportModelMap());
	}

	@RequestMapping(value = "generatedepartmentwiseshortworkreport.htm", method = RequestMethod.POST)
	public void generateDepartmentWiseShortWorkReport(@RequestParam(value = "departmentId") int departmentId,
			@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "empReportType") String empReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SQLException {
		ModelMap modelMap = getDepartmentWiseReportModelMap();
		int employeeId = 0;
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

		ServletOutputStream servletOutputStream = null;
		String jasperPath = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (empReportType.equals("multiple")) {
//			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				jasperPath = path + "ShortWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShortWorkReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShortWorkReportMultiple.xls"));
			}

			else if (empReportType.equals("single")) {
				employeeId = Integer.parseInt(request.getParameter("employeeNo"));
//			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
				jasperPath = path + "ShortWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShortWorkReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShortWorkReportSingle.xls"));
			} else {
//			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
				rs = reportDAO.getShortWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox,
						departmentId);
				jasperPath = path + "ShortWorkReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "ShortWorkReportAll.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "ShortWorkReportAll.xls"));
			}

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("Report_Title", "Short Work report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			parameters.put("Department_Name", departmentDAO.getDepartmentById(departmentId).get(0).getName());
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

//		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}

	public ModelMap getVisitorReportsModelMap() {
		ModelMap modelMap = new ModelMap();
		List<Visitors> visitorsList = visitorsDAO.getVisitors();
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("visitorsList", visitorsList);
		modelMap.addAttribute("employeeList", employeeList);
		return modelMap;
	}

	@RequestMapping(value = "showvisitorlogswophoto.htm", method = RequestMethod.GET)
	public ModelAndView showvisitorlogswophoto(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getVisitorReportsModelMap();
		return new ModelAndView("showvisitorlogswophoto", modelMap);
	}

	@RequestMapping(value = "showvisitorlogsphoto.htm", method = RequestMethod.GET)
	public ModelAndView showvisitorlogsphoto(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getVisitorReportsModelMap();
		return new ModelAndView("showvisitorlogsphoto", modelMap);
	}

	@RequestMapping(value = "generatevisitordailyreport.htm", method = RequestMethod.POST)
	public void generateVisitorDailyReport(@RequestParam(value = "reportTypeVar") String reportTypeVar,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		int visitorId = 0;
		int employeeId = 0;
		String jasperPath = "";
		String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
		String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
		ResultSet resultSet = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reportTitle", "Visitors Daily Report");
		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream1 = classLoader
				.getResourceAsStream(request.getSession().getServletContext().getRealPath("\\"));
		parameters.put("folderPath", request.getSession().getServletContext().getRealPath("\\"));

		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		if (reportTypeVar.equals("VisitorWise")) {
			if (request.getParameter("visitorsSelectId") != null) {
				visitorId = Integer.parseInt(request.getParameter("visitorsSelectId"));
				parameters.put("visitorName", visitorsDAO.getVisitor(visitorId).getVisitorName());
				jasperPath = path + "VisitorsDailyReportVisitor.jrxml";
				// jasperPath=path+"VisitorsDailyReportVisitorImage.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}

		if (reportTypeVar.equals("EmployeeWise")) {
			if (request.getParameter("employeesSelectId") != null) {
				employeeId = Integer.parseInt(request.getParameter("employeesSelectId"));
				Employee employee = employeeDAO.getEmployeeById(employeeId);
				parameters.put("employeeName", employee.getFirstName() + " " + employee.getLastName());
				jasperPath = path + "VisitorsDailyReportEmployee.jrxml";
				// jasperPath=path+" VisitorsDailyReportVisitorImage.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VisitorsDailyReport.xls"));
			resultSet.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "VisitorsDailyReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "generatevisitorwithPhotoreport.htm", method = RequestMethod.POST)
	public void generatevisitorwithPhotoreport(@RequestParam(value = "reportTypeVar") String reportTypeVar,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		int visitorId = 0;
		int employeeId = 0;
		String jasperPath = "";
		String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
		String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
		ResultSet resultSet = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reportTitle", "Visitors Daily Report");
		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream1 = classLoader
				.getResourceAsStream(request.getSession().getServletContext().getRealPath("\\"));
		parameters.put("folderPath", request.getSession().getServletContext().getRealPath("\\"));
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		if (reportTypeVar.equals("VisitorWise")) {
			if (request.getParameter("visitorsSelectId") != null) {
				visitorId = Integer.parseInt(request.getParameter("visitorsSelectId"));
				parameters.put("visitorName", visitorsDAO.getVisitor(visitorId).getVisitorName());
				jasperPath = path + "VisitorsDailyReportVisitorWithPhoto.jrxml";
				// jasperPath=path+"VisitorsDailyReportVisitorImage.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}

		if (reportTypeVar.equals("EmployeeWise")) {
			if (request.getParameter("employeesSelectId") != null) {
				employeeId = Integer.parseInt(request.getParameter("employeesSelectId"));
				Employee employee = employeeDAO.getEmployeeById(employeeId);
				parameters.put("employeeName", employee.getFirstName() + " " + employee.getLastName());
				// jasperPath=path+"VisitorsDailyReportEmployee.jrxml";
				jasperPath = path + "VisitorsDailyReportEmployeeWithPhoto.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}

		ServletOutputStream servletOutputStream = null;
		try {

			servletOutputStream = response.getOutputStream();

			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VisitorsDailyReport.xls"));

			resultSet.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "VisitorsDailyReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "showvisitorfrequencyreport.htm", method = RequestMethod.GET)
	public ModelAndView showVisitorFrequencyReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getVisitorReportsModelMap();
		return new ModelAndView("showvisitorfrequencyreport", modelMap);
	}

	@RequestMapping(value = "showvisitorlist.htm", method = RequestMethod.POST)
	public ModelAndView showVisitorList(@RequestParam(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		if (type.equals("multiple")) {
			return new ModelAndView("visitorlistmultiple", getVisitorReportsModelMap());
		} else if (type.equals("single")) {
			return new ModelAndView("visitorlistsingle", getVisitorReportsModelMap());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "generatevisitorfrequencyreport.htm", method = RequestMethod.POST)
	public void generateVisitorFrequencyReport(@RequestParam(value = "selectedcheckbox") String selectedcheckbox,
			@RequestParam(value = "visitorReportType") String visitorReportType, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
//		ModelMap modelMap=getVisitorReportsModelMap();
		int visitorId = 0;

		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		ResultSet resultSet = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			if (visitorReportType.equals("multiple")) {
				resultSet = reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
				jasperPath = path + "VisitorFrequencyReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "VisitorFrequencyReportMultiple.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "VisitorFrequencyReportMultiple.xls"));
			} else if (visitorReportType.equals("single")) {
				visitorId = Integer.parseInt(request.getParameter("visitorselect"));
				resultSet = reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
				jasperPath = path + "VisitorFrequencyReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "VisitorFrequencyReportSingle.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "VisitorFrequencyReportSingle.xls"));
			} else {
				resultSet = reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
				jasperPath = path + "VisitorFrequencyReport.jrxml";
				exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
						+ "VisitorFrequencyReportAll.pdf";
				exportPathXLS = new FileOutputStream(
						new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
								+ "VisitorFrequencyReportAll.xls"));
			}

			resultSet.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Visitor Frequency Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "saveGateNumber.htm", method = RequestMethod.POST)
	public ModelAndView saveGateNumber(@RequestParam(value = "textField") String gateNumberString,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		VisitorGates visitorGates = new VisitorGates();
		visitorGates.setVisitorGates(gateNumberString);
		visitorGatesDAO.saveVisitorGatesList(visitorGates);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("visitorGatesList", visitorGatesDAO.getVisitorGatesList());
		/* return new ModelAndView("visitortypedropdown", modelMap); */
		return new ModelAndView("gatesdropdown", modelMap);
	}

	@RequestMapping(value = "savePurpose.htm", method = RequestMethod.POST)
	public ModelAndView savePurpose(@RequestParam(value = "textField") String purposeString, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Purpose purpose = new Purpose();
		purpose.setPurpose(purposeString);
		purposeDAO.savePurposeList(purpose);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("visitorPurposeList", purposeDAO.getPurposeList());
		/* return new ModelAndView("visitortypedropdown", modelMap); */
		return new ModelAndView("purposedropdown", modelMap);
	}

	@RequestMapping(value = "showvisitorMateriallogs.htm", method = RequestMethod.GET)
	public ModelAndView showvisitorMateriallogs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelMap modelMap = getVisitorReportsModelMap();
		return new ModelAndView("showvisitorMateriallogs", modelMap);
	}

	@RequestMapping(value = "generatevisitorMaterialreport.htm", method = RequestMethod.POST)
	public void generatevisitorMaterialreport(@RequestParam(value = "reportTypeVar") String reportTypeVar,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		ModelMap modelMap=getVisitorReportsModelMap();
		int visitorId = 0;
		int employeeId = 0;
		String jasperPath = "";
		String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";
		String exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\";
		ResultSet resultSet = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reportTitle", "Visitors Material's Report");
		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");

		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		if (reportTypeVar.equals("VisitorWise")) {
			if (request.getParameter("visitorsSelectId") != null) {
				visitorId = Integer.parseInt(request.getParameter("visitorsSelectId"));
				parameters.put("visitorName", visitorsDAO.getVisitor(visitorId).getVisitorName());
				jasperPath = path + "VisitorsMaterialCarriedReport.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}

		if (reportTypeVar.equals("EmployeeWise")) {
			if (request.getParameter("employeesSelectId") != null) {
				employeeId = Integer.parseInt(request.getParameter("employeesSelectId"));
				Employee employee = employeeDAO.getEmployeeById(employeeId);
				parameters.put("employeeName", employee.getFirstName() + " " + employee.getLastName());
				jasperPath = path + "VisitorsMaterialCarriedReportEmployee.jrxml";
				resultSet = reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
			}
		}

		ServletOutputStream servletOutputStream = null;
		try {

			servletOutputStream = response.getOutputStream();

			OutputStream exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VisitorsDailyReport.xls"));

			resultSet.beforeFirst();
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
			InputStream inputStream = new FileInputStream(jasperPath);
			// jasperDesign = JasperManager.loadXmlDesign(inputStream);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath + "VisitorsMaterialCarriedReport.pdf");
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		}

	}

	@RequestMapping(value = "showvehicleMateriallogs.htm", method = RequestMethod.GET)
	public ModelAndView showvehicleMateriallogs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, VehicleLogs vehicleLogs) {
		ModelMap modelMap = new ModelMap();
		List<VehicleDetails> vehicleList = vehicleDetailsDAO.getVehicleDetailsList();
		modelMap.addAttribute("vehicleList", vehicleList);
		List<String> uniqueVehicleTypeList = vehicleDetailsDAO.getUniqueTypeVehicleList();
		modelMap.addAttribute("uniqueVehicleTypeList", uniqueVehicleTypeList);
		List<Employee> employeeList = employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		return new ModelAndView("showvehicleMateriallogs", modelMap);
	}

	@RequestMapping(value = "generatevehicleMateriallogsreport.htm", method = RequestMethod.POST)
	public void generatevehicleMateriallogsreport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		ModelMap modelMap = new ModelMap();
		String dateFrom = request.getParameter("datefrom");
		String dateTo = request.getParameter("dateTo");
		String concernPerson = request.getParameter("concernPerson");
		String status = request.getParameter("status");
		String filter = request.getParameter("filter");
		String vehicleNumber = request.getParameter("vehicleNumber");
		String vehicleType = request.getParameter("vehicleType");
		DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath = null;
		OutputStream exportPathXLS = null;
		String jasperPath = null;
		try {
			servletOutputStream = response.getOutputStream();
			String path = request.getSession().getServletContext().getRealPath("jasperReports") + "\\";

			rs = reportDAO.getVehicleLogsWoPhotoReport(dateFrom, dateTo, concernPerson, status, filter, vehicleNumber,
					vehicleType);

			jasperPath = path + "VehicleMaterialLogsReport.jrxml";
			exportPath = request.getSession().getServletContext().getRealPath("reports") + "\\"
					+ "VehicleMaterialLogsReport.pdf";
			exportPathXLS = new FileOutputStream(
					new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"
							+ "VehicleMaterialLogsReport.xls"));

			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("reportTitle", "Vehicle Materials Carried Logs Report");
			parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports") + "\\");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			InputStream inputStream = new FileInputStream(jasperPath);
			jasperDesign = JRXmlLoader.load(inputStream);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, resultSetDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
			exporterXLS.exportReport();
			exportPathXLS.write(outputByteArray.toByteArray());

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "printBatch.htm", method = RequestMethod.POST)
	public ModelAndView printBatch(@RequestParam(value = "Id") int logId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		List<VisitorLogs> visitorLogsList = visitorLogsDAO.getVisitorLogsList(logId);
		if (visitorLogsList.size() != 0) {
			Visitors visitor = visitorsDAO.getVisitor(visitorLogsList.get(0).getVisitorId());
			Employee employee = employeeDAO.getEmployeeById(visitorLogsList.get(0).getEmployeeId());

			String inTimeAndDate = visitorLogsList.get(0).getInTime();
			WriteExcel writeExcel = new WriteExcel();
			VisitorBatchPojo visitorBatchPojo = new VisitorBatchPojo();
			visitorBatchPojo.setBatchId("BT_" + logId);
			visitorBatchPojo.setVisitorId(visitorLogsList.get(0).getVisitorId());
			visitorBatchPojo.setName(visitor.getVisitorName());
			visitorBatchPojo.setIntime(inTimeAndDate.split(" ")[0]);
			visitorBatchPojo.setDate(inTimeAndDate.split(" ")[1]);
			visitorBatchPojo.setVisiting(employee.getFirstName() + " " + employee.getLastName());
			visitorBatchPojo.setPurpose(visitorLogsList.get(0).getPurpose());
			visitorBatchPojo.setCmpyName(visitor.getCompany());
			try {
				File file = new File("D:\\printBatch.xls");
				if (!file.exists()) {
					file.createNewFile();
				}
				writeExcel.setOutputFile("D:\\printBatch.xls");
				writeExcel.write(visitorBatchPojo);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView(new RedirectView("viewvisitorlogs.htm"));
	}

	// USB DOWNLOAD CONTROLLER
	@RequestMapping(value = "usbdownload.htm", method = RequestMethod.GET)
	public ModelAndView usbdownloadget(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// reportDAO.importEmployeeTableData();
		System.out.println("usb get");
		return new ModelAndView("usbdownload");
	}

	@RequestMapping(value = "usbdownload.htm", method = RequestMethod.POST)
	public ModelAndView usbdownloadgetpost(@RequestParam(value = "txtFile") MultipartFile txtFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// reportDAO.importEmployeeTableData();
		System.out.println("usb post");
		String extention = txtFile.getOriginalFilename().substring(txtFile.getOriginalFilename().indexOf("."));
		boolean resultFlag = false;
		String destination = null;
		ModelMap modelMap = new ModelMap();
		if (txtFile.getSize() > 0 && extention.equals(".txt")) {
			destination = request.getSession().getServletContext().getRealPath("UsbLogsFile") + "\\USBLogsFile";
			File dir = new File(destination);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(destination + "\\" + txtFile.getOriginalFilename());
			try {
				if (!file.exists()) {

					file.createNewFile();
				}
				txtFile.transferTo(file);

				BufferedReader br = null;
				ConnectionDao d = new ConnectionDao();
				Connection conn = null;
				Statement stmt = null;
				Statement stmt1 = null;
				conn = d.getConnection();
				stmt = conn.createStatement();
				String sCurrentLine;
				int i = 0;
				br = new BufferedReader(new FileReader(file.getAbsolutePath()));
				while ((sCurrentLine = br.readLine()) != null) {
					i++;
					if (i == 1) {
						// System.out.println(sCurrentLine);
					} else {
						// System.out.println(i+" "+sCurrentLine);
						String s = sCurrentLine;
						s = s.replaceAll("\\s", "");
						// System.out.println(i+" "+s+" String length"+s.length());

						String empno = s.substring(7, 18);
						empno = empno.replaceAll("^0+", "");
						String date = s.substring(18, 28);
						date = date.replaceAll("/", "-");

						String d1, m1, y1;
						d1 = date.substring(8, 10);
						m1 = date.substring(5, 7);
						y1 = date.substring(0, 4);

						date = d1 + "-" + m1 + "-" + y1;

						DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
						Date date1 = format.parse(date);
						date = format.format(date1);

						String time = s.substring(28, 36);
						String mode = "Check In";
						String status = "0";
						String ip = "192.168.0.0";
						String serial = "0123456789123";
						// System.out.println("ENo= "+empno+" Date "+date+" Time "+time);
						// recordCount, empCode, checkInOut, status, date, time,
						// deviceIP, deviceSerialNo

						String sql = "select * from attrecord where date='" + date + "' and time='" + time + "'";
						// System.out.println(sql);
						stmt1 = conn.createStatement();
						ResultSet rs = stmt1.executeQuery(sql);

						if (rs.next()) {
							// System.out.println("match");
						} else {
							String sql1 = "insert into attrecord (empCode, checkInOut, status, date, time, deviceIP, deviceSerialNo) values("
									+ empno + ",'" + mode + "','0','" + date + "','" + time + "','" + ip + "','"
									+ serial + "')";
							stmt = conn.createStatement();
							int f = stmt.executeUpdate(sql1);
						}
						stmt1.close();
						rs.close();
						stmt.close();
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(file.getAbsolutePath());

		}
		saveDownloadedLogsToDatabase();
		modelMap.addAttribute("importResultFlag", "Logs Download successfully!");

		return new ModelAndView("usbdownload", modelMap);
	}

	@RequestMapping(value = "getcard.htm", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getSerialNumber(@RequestParam String cardid, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		System.out.println("CardID" + cardid);

		String Card = cardid;
		System.out.println(Card);

		SimpleDateFormat Date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String timeStamp = Date.format(new Date());

		SimpleDateFormat Date12 = new SimpleDateFormat("d-M-yyyy");
		String date1 = Date12.format(new Date());

		SimpleDateFormat Date1 = new SimpleDateFormat("HH:mm:ss");
		String time = Date1.format(new Date());

		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;

		String eid = cardid;
		request.getSession().setAttribute("EID", eid);
		System.out.println("Employee ID:" + eid);
		int employeeNo = Integer.parseInt(eid);
		Connection conn = null;
		ConnectionDao d = new ConnectionDao();
		conn = d.getConnection();
		PreparedStatement ps = null;

		try {// first try start
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM employees WHERE employee_id='" + eid + "'";// checking employee
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("employee existed");// process to checkin
				try {// second start
					String SQLCHECK = "select * from attrecord where empCode='" + eid + "'";
					Statement stm = conn.createStatement();
					ResultSet rs2 = stm.executeQuery(SQLCHECK);
					if (!rs2.next()) {
						AttendanceRecord attendanceRecord = new AttendanceRecord();
						Attendance attendance = new Attendance();
						attendanceRecord.setEmpCode(employeeNo);
						attendanceRecord.setCheckInOut("CheckIn");
						attendanceRecord.setStatus(0);
						attendanceRecord.setDate(date1);
						attendanceRecord.setTime(time);
						attendanceRecord.setDateTime(timeStamp);
						attendance.setEmpCode(employeeNo);
						attendance.setDate(date1);
						attendance.setIn_Time(time);
						attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
						attendanceDAO.saveAttendance(attendance);

						System.out.println("checkIn SuccessFully");
					} else {
						try {
							String sql1 = "select * from attrecord where empCode='" + eid
									+ "'ORDER BY dateTime DESC LIMIT 1";
							Statement stmt12 = conn.createStatement();
							ResultSet rs12 = stmt12.executeQuery(sql1);

							if (rs12.next()) {// check status
								int status = rs12.getInt(4);
								System.out.println("status:" + status);

								if (status == 1) {
									// checkinpart
									try {// In_Time
										AttendanceRecord attendanceRecord = new AttendanceRecord();
										Attendance attendance = new Attendance();
										attendanceRecord.setEmpCode(employeeNo);
										attendanceRecord.setCheckInOut("CheckIn");
										attendanceRecord.setStatus(0);
										attendanceRecord.setDate(date1);
										attendanceRecord.setTime(time);
										attendanceRecord.setDateTime(timeStamp);
										attendance.setEmpCode(employeeNo);
										attendance.setDate(date1);
										attendance.setIn_Time(time);
										attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
										attendanceDAO.saveAttendance(attendance);
										System.out.println("In SuccessFully");
										conn.close();
									} catch (Exception e) {// in_Time
										e.printStackTrace();
									}

								} else if (status == 0) {
									try {
										AttendanceRecord attendanceRecord = new AttendanceRecord();
										attendanceRecord.setEmpCode(employeeNo);
										attendanceRecord.setCheckInOut("CheckOut");
										attendanceRecord.setStatus(1);
										attendanceRecord.setDate(date1);
										attendanceRecord.setTime(time);
										attendanceRecord.setDateTime(timeStamp);
										attendanceRecordDAO.saveAttendancerecord(attendanceRecord);
										System.out.println("Out SuccessFully");
										String SQL = "UPDATE attendance  SET Out_Time='" + time + "' WHERE empCode='"
												+ eid + "' and Date='" + date1 + "'";
										stmt = conn.createStatement();
										stmt.executeUpdate(SQL);
									} catch (Exception e) {
										e.printStackTrace();
									}

								}

							} // check status end

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					conn.close();
				} catch (Exception e) { // second end....
					e.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "employee id not found ");
			}
			saveDownloadedLogsToDatabase();
		} catch (Exception e) { // first try end...
			System.out.println("Error in getting employee:" + e.getMessage());
		}

		return ResponseHandler.generateResponse("get cardid ", HttpStatus.ALREADY_REPORTED);

	}

}
