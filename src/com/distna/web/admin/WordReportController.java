package com.distna.web.admin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.distna.domain.calendar.CalTimes;
import com.distna.domain.company.Departments;
import com.distna.domain.employee.Employee;
import com.distna.service.calendar.CalTimesDAO;
import com.distna.service.calendar.HolidaysDAO;
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
import com.distna.service.company.ContractorDAO;
import com.distna.service.company.CountriesDAO;
import com.distna.service.company.CurrencyListDAO;
import com.distna.service.company.DepartmentDAO;
import com.distna.service.company.DepartmentValidator;
import com.distna.service.company.DesignationDAO;
import com.distna.service.company.DesignationLevelDAO;
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
import com.distna.service.company.ZonesDAO;
import com.distna.service.devicemanagement.AddDeviceDAO;
import com.distna.service.devicemanagement.AllowedDevicesDAO;
import com.distna.service.devicemanagement.UserInfoDAO;
import com.distna.service.employee.AssesmentDAO;
import com.distna.service.employee.AttendanceLogsBulkEntryDAO;
import com.distna.service.employee.AttendanceLogsDAO;
import com.distna.service.employee.AttendanceLogsOutdoorEntryDAO;
import com.distna.service.employee.AttendanceRecordDAO;
import com.distna.service.employee.EducationDAO;
import com.distna.service.employee.EmployeeAssesmentValidator;
import com.distna.service.employee.EmployeeDAO;
import com.distna.service.employee.EmployeeEducationValidator;
import com.distna.service.employee.EmployeeExperiencesDAO;
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
import com.distna.service.visitor.VisitorGatesDAO;
import com.distna.service.visitor.VisitorLogsDAO;
import com.distna.service.visitor.VisitorLogsValidator;
import com.distna.service.visitor.VisitorValidator;
import com.distna.service.visitor.VisitorsDAO;
import com.distna.utility.EncryptPassword;

@Controller
public class WordReportController {
	private ModelMap modelMap=new ModelMap();
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
	private int countryID=0;
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
	private ResultSet rs=null;
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
	
	DateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy-hh-MM-SS");
	Date date= new Date();
	String today_date= dateFormat.format(date);
	String reportname= null;
	
	@Autowired
	public WordReportController(MasterPrivilegesDAO masterPrivilegesDAO,VehicleLogsValidator vehicleLogsValidator,VisitorLogsValidator visitorLogsValidator,VisitorValidator visitorValidator,PurposeDAO purposeDAO,VisitorGatesDAO visitorGatesDAO,AttendanceLogsOutdoorEntryDAO attendanceLogsOutdoorEntryDAO,MasterSettingsDAO masterSettingsDAO,UserInfoDAO userInfoDAO,AllowedDevicesDAO allowedDevicesDAO,OutForWorkDAO outForWorkDAO,ContractorDAO contractorDAO,PriorityValidator priorityValidator, BreaksValidator breaksValidator, EmployeeMessagesValidator employeeMessagesValidator,EmployeeMessagesDAO employeeMessagesDAO,EmployeeEducationValidator employeeEducationValidator,LeavesPerEmployee leavesPerEmployee,PresenceAbscencePercentage presenceAbscencePercentage,WeeklyTimecard weeklyTimecard,EmployeesPerDepartment employeesPerDepartment,EmployeePersonalValidator employeePersonalValidator,ShiftAllocationValidator shiftAllocationValidator,ShiftDefinitionValidator shiftDefinitionValidator,ShiftMasterValidator shiftMasterValidator,DivisionValidator divisionValidator, JobPositionValidator jobPositionValidator, DesignationValidator designationValidator, DepartmentValidator departmentValidator , LocationValidator locationValidator, CompanyPoliciesValidator companyPoliciesValidator, AttendanceLogsBulkEntryDAO attendanceLogsBulkEntryDAO, AttendanceRecordDAO attendanceRecordDAO, AttendanceLogsDAO attendanceLogsDAO, ShiftAllocationDAO shiftAllocationDAO, EmployeeSkillsDAO employeeSkillsDAO,EmployeeExperiencesDAO employeeExperiencesDAO, HolidaysDAO holidaysDAO, CalTimesDAO calTimesDAO,AddDeviceDAO addDeviceDAO,ListsDAO listsDAO,JobPositionDAO jobPositionDAO,CompanyPoliciesDAO companyPoliciesDAO,LocationDAO locationDAO,DesignationDAO designationDAO,DepartmentDAO departmentDAO,PriorityDAO priorityDAO,JobRolesDAO jobRolesDAO,WorkspacesDAO workspacesDAO,CurrencyListDAO currencyListDAO,CountriesDAO countriesDAO,DesignationLevelDAO designationLevelDAO,CompanyDAO companyDAO,ZonesDAO zonesDAO,StatesDAO statesDAO,CitiesDAO citiesDAO, DivisionDAO divisionDAO, EmployeeDAO employeeDAO,OfficialToursDAO officialToursDAO,ShiftMasterDAO shiftMasterDAO,ShiftDefinitionDAO shiftDefinitionDAO,StatusDAO statusDAO,ProjectsDAO projectsDAO,EducationDAO educationDAO,AssesmentDAO assesmentDAO,BreakDAO breakDAO,LeaveTypeDAO leaveTypeDAO,LeaveAllocationDAO leaveAllocationDAO,LeaveApplicationDAO leaveApplicationDAO, EmployeeSkillsValidator employeeSkillsValidator , JobRolesValidator jobRolesValidator,LeavesValidator leavesValidator,OfficialTourValidator officialTourValidator,LeaveTypeValidator leaveTypeValidator,LeaveAllocationValidator leaveAllocationValidator,LeaveApplicationValidator leaveApplicationValidator,EmployeeAssesmentValidator employeeAssesmentValidator,EmployeeProjectsValidator employeeProjectsValidator,EmployeeValidator employeeValidator,ReportDAO reportDAO,ChartDAO chartDAO, LeavesPerLeaveType leavesPerLeaveType,UserPrivilegeDAO userPrivilegeDAO,EmployeePrivilegeDAO employeePrivilegeDAO, CanteenItemsDAO canteenItemsDAO, CanteenItemsValidator canteenItemsValidator, CanteenTimingsDAO canteenTimingsDAO, CanteenTimingsValidator canteenTimingsValidator,VisitorLogsDAO visitorLogsDAO,VisitorsDAO visitorsDAO,VehicleDetailsDAO vehicleDetailsDAO,VehicleLogsDAO vehicleLogsDAO, VehicleDetailsValidator vehicleDetailsValidator) {
		this.designationLevelDAO=designationLevelDAO;
		this.companyDAO=companyDAO;
		this.zonesDAO=zonesDAO;
		this.countriesDAO=countriesDAO;
		this.currencyListDAO=currencyListDAO;
		this.workspacesDAO=workspacesDAO;
		this.jobRolesDAO=jobRolesDAO;
		this.priorityDAO=priorityDAO;
		this.departmentDAO=departmentDAO;
		this.designationDAO=designationDAO;
		this.locationDAO=locationDAO;
		this.companyPoliciesDAO=companyPoliciesDAO;
		this.statesDAO=statesDAO;
		this.citiesDAO=citiesDAO;
		this.statusDAO=statusDAO;
		this.divisionDAO=divisionDAO;
		this.employeeDAO=employeeDAO;
		this.jobPositionDAO=jobPositionDAO;
		this.listsDAO=listsDAO;
		this.addDeviceDAO=addDeviceDAO;
		this.officialToursDAO=officialToursDAO;
		this.shiftMasterDAO=shiftMasterDAO;
		this.shiftDefinitionDAO=shiftDefinitionDAO;
		this.calTimesDAO=calTimesDAO;
		this.shiftAllocationDAO=shiftAllocationDAO;
		this.holidaysDAO=holidaysDAO;
		this.employeeExperiencesDAO=employeeExperiencesDAO;
		this.employeeSkillsDAO=employeeSkillsDAO;
		this.projectsDAO=projectsDAO;
		this.educationDAO=educationDAO;
		this.breakDAO=breakDAO;
		this.leaveTypeDAO=leaveTypeDAO;
		this.leaveAllocationDAO=leaveAllocationDAO;
		this.leaveApplicationDAO=leaveApplicationDAO;
		this.employeeSkillsValidator=employeeSkillsValidator;
		this.assesmentDAO=assesmentDAO;
		this.jobRolesValidator=jobRolesValidator;
		this.attendanceLogsDAO=attendanceLogsDAO;
		this.attendanceRecordDAO=attendanceRecordDAO;
		this.attendanceLogsBulkEntryDAO=attendanceLogsBulkEntryDAO;
		this.companyPoliciesValidator=companyPoliciesValidator;
		this.locationValidator=locationValidator;
		this.departmentValidator=departmentValidator;
		this.designationValidator=designationValidator;
		this.jobPositionValidator=jobPositionValidator;
		this.divisionValidator=divisionValidator; 
		this.shiftMasterValidator=shiftMasterValidator;
		this.shiftDefinitionValidator=shiftDefinitionValidator;
		this.shiftAllocationValidator=shiftAllocationValidator;
		this.leavesValidator=leavesValidator;
		this.officialTourValidator=officialTourValidator;
		this.leaveTypeValidator=leaveTypeValidator;
		this.leaveAllocationValidator=leaveAllocationValidator;
		this.leaveApplicationValidator=leaveApplicationValidator;
		this.employeeAssesmentValidator=employeeAssesmentValidator;
		this.employeeProjectsValidator=employeeProjectsValidator;
		this.employeeValidator=employeeValidator;
		this.reportDAO=reportDAO;
		this.chartDAO=chartDAO;
		this.contractorDAO=contractorDAO;
		this.leavesPerLeaveType=leavesPerLeaveType;
		this.employeePersonalValidator=employeePersonalValidator;
		this.leavesPerEmployee=leavesPerEmployee;
		this.employeesPerDepartment=employeesPerDepartment;
		this.weeklyTimecard=weeklyTimecard;
		this.presenceAbscencePercentage=presenceAbscencePercentage;
		this.employeeEducationValidator=employeeEducationValidator;
		this.employeeMessagesDAO=employeeMessagesDAO;
		this.userPrivilegeDAO=userPrivilegeDAO;
		this.employeePrivilegeDAO=employeePrivilegeDAO;
		this.employeeMessagesValidator=employeeMessagesValidator;
		this.breaksValidator=breaksValidator;
		this.priorityValidator=priorityValidator;
		this.outForWorkDAO=outForWorkDAO;
		this.allowedDevicesDAO=allowedDevicesDAO;
		this.userInfoDAO=userInfoDAO;
		this.masterSettingsDAO=masterSettingsDAO;
		this.attendanceLogsOutdoorEntryDAO=attendanceLogsOutdoorEntryDAO;
		this.canteenItemsDAO=canteenItemsDAO;
		this.canteenItemsValidator=canteenItemsValidator;
		this.canteenTimingsDAO=canteenTimingsDAO;
		this.canteenTimingsValidator=canteenTimingsValidator;
		this.visitorLogsDAO=visitorLogsDAO;
		this.visitorsDAO=visitorsDAO;
		this.vehicleDetailsDAO=vehicleDetailsDAO;
		this.vehicleLogsDAO=vehicleLogsDAO;
		this.visitorGatesDAO=visitorGatesDAO;
		this.purposeDAO=purposeDAO;
		this.visitorValidator=visitorValidator;
		this.visitorLogsValidator=visitorLogsValidator;
		this.vehicleLogsValidator=vehicleLogsValidator;
		this.vehicleDetailsValidator=vehicleDetailsValidator;
		this.masterPrivilegesDAO=masterPrivilegesDAO;
	}
	public ModelMap getshiftWiseReportModelMap()
	{
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("shiftMasterList", shiftMasterDAO.getShiftMasterList());
		List<CalTimes> calTimesList=calTimesDAO.getCalTimes();
		modelMap.addAttribute("calTimesList", calTimesList);
		modelMap.addAttribute("ErrorMsg"," ");
		return modelMap;
	}
	public ModelMap getDepartmentWiseReportModelMap()
	{
		ModelMap modelMap=new ModelMap();
		List<Employee> employeesList=employeeDAO.getEmployeeList();
		modelMap.addAttribute("EmployeeList", employeesList);
		
		List<Departments> departmentList=departmentDAO.getDepartment();
		modelMap.addAttribute("departmentList", departmentList);
		
		modelMap.addAttribute("ErrorMsg"," ");
		return modelMap;
	}
	public ModelMap getDailyAttendenceReportModelMap()
	{
		ModelMap modelMap=new ModelMap();
		List<Employee> employeeList=employeeDAO.getEmployeeList();
		modelMap.addAttribute("employeeList", employeeList);
		modelMap.addAttribute("ErrorMsg"," ");
		return modelMap;
	} 
	
	@RequestMapping(value="generateEmployeeWiseaAllPunchesReportInDocx.htm",method=RequestMethod.POST)
	public void generateEmployeeWiseaAllPunchesReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("ErrorMsg"," ");
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		ServletOutputStream servletOutputStream = null;
		String exportPath=null;
		
		OutputStream exportPathXLS=null;
		String jasperPath=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseAll.jrxml";
			reportname="EmployeeWiseAllPunchesReportMultiple"+today_date+".docx";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseAllPunchesReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseAllPunchesReportMultiple.docx"));		
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseAll.jrxml";
			reportname="EmployeeWiseAllPunchesReportSingle"+today_date+".docx";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseAllPunchesReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseAllPunchesReportSingle.docx"));
		}
		else
		{
			rs=reportDAO.getEmployeeWiseAllList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseAll.jrxml";
			reportname="EmployeeWiseAllPunchesReport"+today_date+".docx";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseAllPunchesReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseAllPunchesReport.docx"));
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Employee Wise report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
	        response.setContentType("application/pdf");
	        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
	        
	        
	        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
	        JRXlsExporter exporterXLS = new JRXlsExporter();
	        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
	        exporterXLS.exportReport();
	        exportPathXLS.write(outputByteArray.toByteArray());
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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

		//return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}
	
	@RequestMapping(value="generateEmployeeWiseReportInDocx.htm",method=RequestMethod.POST)
	public void generateEmployeeWiseReportInXLS(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("ErrorMsg"," ");
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		/*try {
			if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo)))
			{
				List<Employee> employeeList=employeeDAO.getEmployeeList();
				modelMap.addAttribute("EmployeeList",employeeList);
				modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid");
				return new ModelAndView("showEmployeeWiseReport",modelMap);
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ServletOutputStream servletOutputStream = null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		String jasperPath=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseReportMultiple.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseReportMultiple.docx"));
			reportname="EmployeeWiseReportMultiple"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseReportSingle.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseReportSingle.docx"));
			reportname="EmployeeWiseReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseReport.docx"));
			reportname="EmployeeWiseReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Employee Wise report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        //JasperViewer.viewReport(jasperPrint, false);
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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

		//return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}
	@RequestMapping(value="generateleavereportInDocx.htm",method=RequestMethod.POST)
	public ModelAndView generateLeaveReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		int workId=0;
		modelMap.addAttribute("ErrorMsg"," ");
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		int departmentId=Integer.parseInt(request.getParameter("hiddenDepartmentId"));
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		try {
			if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo)))
			{
				List<Departments> departmentList=departmentDAO.getDepartment();
				modelMap.addAttribute("departmentList", departmentList);
				modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid");
				return new ModelAndView("leavereport",modelMap);
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
			jasperPath=path+"LeaveReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveReportMultiple.docx"));
			reportname="LeaveReportMultiple"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
			jasperPath=path+"LeaveReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveReportSingle.docx"));
			reportname="LeaveReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getLeaveReport(workId, departmentId, dateFrom, dateTo, empReportType, selectedcheckbox);
			jasperPath=path+"LeaveReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveReport.docx"));
			reportname="LeaveReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Leave Report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
	        
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

	
	@RequestMapping(value="generateDepartmentSummaryReportInDocx.htm",method=RequestMethod.GET)
	public void generateDepartmentSummaryReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException
	{
		int department_id=Integer.parseInt(request.getParameter("department"));
		String date=request.getParameter("datefrom");
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			rs=reportDAO.getDepartmentSummaryReport(department_id, date);
			jasperPath=path+"DepartmentSummaryReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentSummaryReport.pdf";
			reportname="DepartmentSummaryReport"+today_date+".docx";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentSummaryReport.docx"));
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Department Summary report");
	        parameters.put("date",date);
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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
		//return new ModelAndView(new RedirectView("showDepartmentSummaryReport.htm"));
	}
	@RequestMapping(value="generatedepartmentwisereportInDocx.htm",method=RequestMethod.POST)
	public ModelAndView generateDepartmentWiseReportInDocx(@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDepartmentWiseReportModelMap();
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReportMultiple.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportMultiple.docx"));
			reportname="EmployeeWiseReportSingle"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReportSingle.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportSingle.docx"));
			reportname="EmployeeWiseReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReport.docx"));
			reportname="DepartmentWiseReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Department Wise Logs report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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
	@RequestMapping(value="generatedepartmentwiseOutForWorkreportInDocx.htm",method=RequestMethod.POST)
	public void generatedepartmentwiseOutForWorkreportInDocx(@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDepartmentWiseReportModelMap();
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"OutForWorkReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"OutForWorkReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"OutForWorkReportMultiple.docx"));
			reportname="OutForWorkReportMultiple"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"OutForWorkReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"OutForWorkReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"OutForWorkReportSingle.docx"));
			reportname="OutForWorkReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getDepartmentWiseOutForWork(workId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
			jasperPath=path+"OutForWorkReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"OutForWorkReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"OutForWorkReport.docx"));
			reportname="OutForWorkReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Department Wise Out For Work report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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

		//return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	}
	@RequestMapping(value="generatedepartmentwiseshortworkreportInDocx.htm",method=RequestMethod.POST)
	public void generateDepartmentWiseShortWorkReportInDocx(@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDepartmentWiseReportModelMap();
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportMultiple.docx"));
			reportname="DepartmentWiseReportMultiple"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportSingle.docx"));
			reportname="DepartmentWiseReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReport.docx"));
			reportname="DepartmentWiseReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Department Wise Logs report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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
	}
	@RequestMapping(value="generateTotalWorkingHoursReportInDocx.htm",method=RequestMethod.POST)
	public ModelAndView generateTotalWorkingHoursReportInDocx(@RequestParam(value="workingHoursType") String workingHoursType,@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDepartmentWiseReportModelMap();
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportMultiple.docx"));
			reportname="DepartmentWiseReportMultiple"+today_date+".docx";
		}
		else if(empReportType.equals("single"))
		{
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReportSingle.docx"));
			reportname="DepartmentWiseReportSingle"+today_date+".docx";
		}
		else
		{
			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
			jasperPath=path+"DepartmentWiseReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"DepartmentWiseReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DepartmentWiseReport.docx"));
			reportname="DepartmentWiseReport"+today_date+".docx";
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Department Wise Logs report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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
	@RequestMapping(value="dailyattendancereportInDocx.htm",method=RequestMethod.POST)
	public void generateDailyAttendanceInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDailyAttendenceReportModelMap();
		//modelMap.addAttribute("ErrorMsg"," ");
		int employeeId=Integer.parseInt(request.getParameter("dailyattendance"));
		String employeeName=employeeDAO.getEmployeeById(employeeId).getFirstName()+" "+employeeDAO.getEmployeeById(employeeId).getLastName();
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		String shiftTime[]=shiftAllocationDAO.getShiftStartEndTime(employeeId);
		
		
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		/*try {
			if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo)))
			{
				List<Employee> employeeList=employeeDAO.getEmployeeList();
				modelMap.addAttribute("employeeList", employeeList);
				modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid");
				return new ModelAndView("dailyattendancereport",modelMap);
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
		
		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
		 OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"DailyAttendanceReport.docx"));
		ResultSet rs=reportDAO.getDailyAttendanceReport(employeeId, dateFrom, dateTo, shiftTime[0], shiftTime[1]);
		String jasperPath=path+"DailyAttendanceReport.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("reportTitle","Daily Attendance Report");
        parameters.put("employeeName", employeeName);
        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
		rs.beforeFirst();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
        InputStream inputStream = new FileInputStream(jasperPath);
        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
        reportname="DailyAttendanceReport"+today_date+".docx";
        jasperDesign = JRXmlLoader.load(inputStream);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "DailyAttendanceReport.pdf");
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
        JRDocxExporter exporter = new JRDocxExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
        exporter.exportReport();
        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
        out.write(outputByteArray.toByteArray());
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

		 //return new ModelAndView(new RedirectView("showdailyattendancereport.htm"));
	}
	@RequestMapping(value="generateShiftWiseReportInDocx.htm",method=RequestMethod.POST)
	public void generateShiftWiseReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,@RequestParam(value="reportType") String reportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getshiftWiseReportModelMap();
		int shiftId=Integer.parseInt(request.getParameter("shiftName"));
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		String allowedTime="00:00:00";
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		modelMap.addAttribute("reportType", reportType);
		//modelMap.addAttribute("shiftMasterList", shiftMasterDAO.getShiftMasterList());
		/*try {
			if(dateFormat.parse(dateFrom).after(dateFormat.parse(dateTo)))
			{
			//	modelMap.addAttribute("shiftMasterList", shiftMasterDAO.getShiftMasterList());
				modelMap.addAttribute("reportType", reportType);
				modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid");
//				return new ModelAndView("shiftwisedailyreport",modelMap);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		int workId=0;
		
		ServletOutputStream servletOutputStream = null;
		try {
		servletOutputStream = response.getOutputStream();
		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
		String exportPath=null;
		OutputStream exportPathXLS=null; 
		String jasperPath=null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
		if(reportType.equals("InOut"))
		{
			jasperPath=path+"ShiftWiseReport.jrxml";
			parameters.put("Report_Title","Shift Wise In/Out Logs report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseInOutReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseInOutReport.docx"));
				
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseInOutReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseInOutReport.docx"));
			}
			else
			{	
				reportname="ShiftWiseInOutReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseInOutReport.docx"));
			}
		}
		
		else if(reportType.equals("BreakInOut"))
		{
			jasperPath=path+"ShiftWiseBreakInOutReport.jrxml";
			parameters.put("Report_Title","Shift Wise Break In/Out Report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseBreakInOutReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseBreakInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseBreakInOutReport.docx"));
				
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseBreakInOutReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseBreakInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseBreakInOutReport.docx"));
			}
			else
			{	
				reportname="ShiftWiseBreakInOutReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseBreakInOutReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseBreakInOutReport.docx"));
			}
			
			
		}
		
		else if(reportType.equals("EarlyComing"))
		{
			jasperPath=path+"ShiftWiseEarlyComingReport.jrxml";
			parameters.put("Report_Title","Shift Wise Early Coming Logs report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseEarlyComingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseEarlyComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseEarlyComingReport.docx"));
				
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseEarlyComingReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseEarlyComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseEarlyComingReport.docx"));
			}
			else
			{
				reportname="ShiftWiseEarlyComingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseEarlyComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseEarlyComingReport.docx"));
			}
		}
		else if(reportType.equals("LateComing"))
		{
			jasperPath=path+"ShiftWiseLateComingReport.jrxml";
			parameters.put("Report_Title","Shift Wise Late Coming Logs report");
			if(null!=request.getParameter("allowedTime"))
			allowedTime=request.getParameter("allowedTime");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseLateComingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseLateComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseLateComingReport.docx"));
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseLateComingReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseLateComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseLateComingReport.docx"));
			}
			else
			{
				reportname="ShiftWiseLateComingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseLateComingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseLateComingReport.docx"));
			}
			
		}
		else if(reportType.equals("EarlyGoing"))
		{
			jasperPath=path+"ShiftWiseEarlyGoingReport.jrxml";
			parameters.put("Report_Title","Shift Wise Early Going Logs report");
			if(null!=request.getParameter("allowedTime"))
				allowedTime=request.getParameter("allowedTime");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseEarlyGoingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseEarlyGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseEarlyGoingReport.docx"));
				
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseEarlyGoingReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseEarlyGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseEarlyGoingReport.docx"));
			}
			else
			{
				reportname="ShiftWiseEarlyGoingReport"+today_date+".docx";
				
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseEarlyGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseEarlyGoingReport.docx"));
			}
			
		}
		else if(reportType.equals("LateGoing"))
		{
			jasperPath=path+"ShiftWiseLateGoingReport.jrxml";
			parameters.put("Report_Title","Shift Wise Late Going Logs report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseLateGoingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseLateGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseLateGoingReport.docx"));
				
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseLateGoingReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseLateGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseLateGoingReport.docx"));
			}
			else
			{
				reportname="ShiftWiseLateGoingReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseLateGoingReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseLateGoingReport.docx"));
			}
			
		}
		else if(reportType.equals("Absent"))
		{
			jasperPath=path+"ShiftWiseAbsentReport.jrxml";
			parameters.put("Report_Title","Shift Wise Absent Logs report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWiseAbsentReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWiseAbsentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWiseAbsentReport.docx"));
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWiseAbsentReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWiseAbsentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWiseAbsentReport"));
			}
			else
			{
				reportname="ShiftWiseAbsentReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseAbsentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseAbsentReport.docx"));
			}
			
		}
		else if(reportType.equals("Present"))
		{
			jasperPath=path+"ShiftWisePresentReport.jrxml";
			parameters.put("Report_Title","Shift Wise Present Logs report");
			if(empReportType.equals("multiple"))
			{
				reportname="MultipleShiftWisePresentReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleShiftWisePresentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleShiftWisePresentReport.docx"));
			}
			else if(empReportType.equals("single"))
			{
				reportname="SingleShiftWisePresentReport"+today_date+".docx";
				workId=Integer.parseInt(request.getParameter("employeeNo"));
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleShiftWisePresentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleShiftWisePresentReport.docx"));
			}
			else
			{
				reportname="ShiftWisePresentReport"+today_date+".docx";
				exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWisePresentReport.pdf";
				exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWisePresentReport.docx"));
			}
		}
		else
		{
			
		}
		ResultSet rs=reportDAO.getShiftWiseEmployeeList(shiftId, dateFrom, dateTo,empReportType,selectedcheckbox,workId,reportType,allowedTime);
		rs.beforeFirst();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
        InputStream inputStream = new FileInputStream(jasperPath);
        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
        jasperDesign = JRXmlLoader.load(inputStream);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
        
        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
         JRDocxExporter exporter = new JRDocxExporter();
         
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
         OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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

		 /*return new ModelAndView("shiftwisedailyreport",modelMap);*/
	}
	@RequestMapping(value="generateMonthlyAttendanceReportInDocx.htm",method=RequestMethod.POST)
	public void generateMonthlyAttendanceReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+"";
		String year=request.getParameter("iYear");
		int employeeNo=0;
		ServletOutputStream servletOutputStream = null;
		try {
		servletOutputStream = response.getOutputStream();
		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
		String exportPath=null;
		OutputStream exportPathXLS=null;
		
		if(empReportType.equals("multiple"))
		{
			reportname="MultipleEmployeesMonthlyAttendanceReport"+today_date+".docx";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MultipleEmployeesMonthlyAttendanceReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MultipleEmployeesMonthlyAttendanceReport.docx"));
			
		}
		else if(empReportType.equals("single"))
		{
			reportname="SingleEmployeeMonthlyAttendanceReport"+today_date+".docx";
			employeeNo=Integer.parseInt(request.getParameter("employeeNo"));
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"SingleEmployeeMonthlyAttendanceReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"SingleEmployeeMonthlyAttendanceReport.docx"));
		}
		else
		{
			reportname="MonthlyAttendanceReport"+today_date+".docx";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MonthlyAttendanceReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MonthlyAttendanceReport.docx"));
		}
		ResultSet rs=reportDAO.getMonthlyAttendanceOfEmployee(employeeNo, month, year,empReportType,selectedcheckbox);
		String jasperPath=path+"MonthlyAttendanceReport.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Report_Title","Monthly Attendance Report");
        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
		rs.beforeFirst();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
        InputStream inputStream = new FileInputStream(jasperPath);
        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
        jasperDesign = JRXmlLoader.load(inputStream);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
        JRDocxExporter exporter = new JRDocxExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
        exporter.exportReport();
        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
        out.write(outputByteArray.toByteArray());
        
        
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
	@RequestMapping(value="generatememoreportInDocx.htm",method=RequestMethod.POST)
	public void generateMemoReportInDocx(@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=getDepartmentWiseReportModelMap();
		int employeeId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			reportname="MemoReportMultiple"+today_date+".docx";
			rs=reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
			jasperPath=path+"MemoReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MemoReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MemoReportMultiple.docx"));
			
		}
		else if(empReportType.equals("single"))
		{
			reportname="MemoReportSingle"+today_date+".docx";
			employeeId=Integer.parseInt(request.getParameter("employeeNo"));
			System.out.println("Employee No"+employeeId);
			rs=reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
			while(rs.next())
			{
				System.out.println(""+rs.getString(2));
			}
			jasperPath=path+"MemoReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MemoReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MemoReportSingle.docx"));
		}
		else
		{
			reportname="MemoReportAll"+today_date+".docx";
			rs=reportDAO.getMemoReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, departmentId);
			
			jasperPath=path+"MemoReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MemoReportAll.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MemoReportAll.docx"));
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Memo Report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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
	}
	@RequestMapping(value="generateShiftWiseMusterReportInDocx.htm",method=RequestMethod.POST)
	public void generateShiftWiseMusterReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	{		
		String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+"";
		String year=request.getParameter("iYear");
		String monthName = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
	    
	   
		int shiftId=Integer.parseInt(request.getParameter("shiftName"));
		if(shiftId!=0)
		{
		ServletOutputStream servletOutputStream = null;
		try {
		servletOutputStream = response.getOutputStream();
		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
		String exportPath=null;
		OutputStream exportPathXLS=null;
		reportname="ShiftWiseMusterReport"+today_date+".docx";
		exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseMusterReport.pdf";
		exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseMusterReport.docx"));
		ResultSet rs=reportDAO.getmusterReportOfEmployees(shiftId, month, year);
		String jasperPath=path+"ShiftWiseMusterReport.jrxml";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("MonthName",monthName);
		parameters.put("YearName",year);
        parameters.put("Report_Title","Shift Wise Muster Report");
        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
		rs.beforeFirst();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
        InputStream inputStream = new FileInputStream(jasperPath);
        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
        jasperDesign = JRXmlLoader.load(inputStream);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
        JRDocxExporter exporter = new JRDocxExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
        exporter.exportReport();
        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
        out.write(outputByteArray.toByteArray());
        
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
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	@RequestMapping(value="generateBasicWorkDurationReportInDocx.htm",method=RequestMethod.POST)
	public void generateBasicWorkDurationReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	{		
		String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+"";
		String year=request.getParameter("iYear");
		String monthName = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
	    
	   
		int shiftId=Integer.parseInt(request.getParameter("shiftName"));
		if(shiftId!=0)
		{
		ServletOutputStream servletOutputStream = null;
		try {
		servletOutputStream = response.getOutputStream();
		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
		String exportPath=null;
		OutputStream exportPathXLS=null;
		reportname="ShiftWiseBasicWorkDurReport"+today_date+".docx";
		exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseBasicWorkDurReport.pdf";
		exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseBasicWorkDurReport.docx"));

		
		
		
		ResultSet rs=reportDAO.getBasicWorkReport(shiftId, month, year);
		
		
		String jasperPath=path+"ShiftWiseBasicWorkDurReport.jrxml";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("MonthName",monthName);
		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
		parameters.put("YearName",year);
        parameters.put("Report_Title","Shift Wise Muster Report");
		rs.beforeFirst();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
        InputStream inputStream = new FileInputStream(jasperPath);
        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
        jasperDesign = JRXmlLoader.load(inputStream);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
        JRDocxExporter exporter = new JRDocxExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
        exporter.exportReport();
        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
        out.write(outputByteArray.toByteArray());
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
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	@RequestMapping(value="generateEmployeeWiseExceptionReportInDocx.htm",method=RequestMethod.POST)
	public void generateEmployeeWiseExceptionReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("ErrorMsg"," ");
		int workId=0;
		String dateFrom=request.getParameter("datefrom");
		String dateTo=request.getParameter("dateTo");
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		ServletOutputStream servletOutputStream = null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		String jasperPath=null;
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			reportname="EmployeeWiseExceptionReportMultiple"+today_date+".docx";
			rs=reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseExceptionReportMultiple.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseExceptionReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseExceptionReportMultiple.docx"));
			
		}
		else if(empReportType.equals("single"))
		{
			reportname="EmployeeWiseExceptioReportSingle"+today_date+".docx";
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseExceptionReportSingle.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseExceptioReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseExceptioReportSingle.docx"));
		}
		else
		{
			reportname="EmployeeWiseExceptioReport"+today_date+".docx";
			rs=reportDAO.getAttendanceLogsBulkEntryExceptionList(workId, dateFrom, dateTo,empReportType,selectedcheckbox);
			jasperPath=path+"EmployeeWiseExceptionReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"EmployeeWiseExceptioReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"EmployeeWiseExceptioReport.docx"));
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Employee Wise Exception report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
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

		//return new ModelAndView(new RedirectView("showEmployeeWiseReport.htm"));
	}
	@RequestMapping(value="generateLeaveStatusReportInDocx.htm",method=RequestMethod.POST)
	public void generateLeaveStatusReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	{
		int workId=0;
		modelMap.addAttribute("ErrorMsg"," ");
		ServletOutputStream servletOutputStream = null;
		String jasperPath=null;
		String exportPath=null;
		OutputStream exportPathXLS=null;
		int leaveType=0;
		if(request.getParameter("leaveType")!=null)
		{
			leaveType=Integer.parseInt(request.getParameter("leaveType"));
		}
		int departmentId=Integer.parseInt(request.getParameter("hiddenDepartmentId"));
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
		
				List<Departments> departmentList=departmentDAO.getDepartment();
				modelMap.addAttribute("departmentList", departmentList);
				modelMap.addAttribute("ErrorMsg","Start Date and End Date not Valid");
				/*return new ModelAndView("leavereport",modelMap);*/
		try 
		{
			servletOutputStream = response.getOutputStream();
			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
			
		if(empReportType.equals("multiple"))
		{
			reportname="LeaveStatusReportMultiple"+today_date+".docx";
			rs=reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
			jasperPath=path+"LeaveStatusReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveStatusReportMultiple.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveStatusReportMultiple.docx"));
			
		}
		else if(empReportType.equals("single"))
		{
			reportname="LeaveStatusReportSingle"+today_date+".docx";
			workId=Integer.parseInt(request.getParameter("employeeNo"));
			rs=reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
			jasperPath=path+"LeaveStatusReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveStatusReportSingle.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveStatusReportSingle.docx"));
		}
		else
		{
			reportname="LeaveStatusReport"+today_date+".docx";
			rs=reportDAO.getLeaveStatusReport(workId, departmentId, empReportType, selectedcheckbox, leaveType);
			jasperPath=path+"LeaveStatusReport.jrxml";
			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"LeaveStatusReport.pdf";
			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"LeaveStatusReport.docx"));
		}
			
			rs.beforeFirst();
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("Report_Title","Leave Report");
	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	        parameters.put("Dept_Name", departmentDAO.getDepartmentNameById(departmentId));
	        parameters.put("Leave_Type", leaveTypeDAO.getLeaveTypeNameById(leaveType));
	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	        InputStream inputStream = new FileInputStream(jasperPath);
	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	        jasperDesign = JRXmlLoader.load(inputStream);
	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	        JRDocxExporter exporter = new JRDocxExporter();
	           
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
	        exporter.exportReport();
	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	        out.write(outputByteArray.toByteArray());
	        
	        
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
	}
		 @RequestMapping(value="generateShiftWiseBasicWorkDurReportInDocx.htm",method=RequestMethod.POST)
	    	public void generateShiftWiseBasicWorkDurReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	    	{		
	    		String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+"";
	    		String year=request.getParameter("iYear");
	    		String monthName = "invalid";
	    	    DateFormatSymbols dfs = new DateFormatSymbols();
	    	    String[] months = dfs.getMonths();
	    	    monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
	    	    
	    	   
	    		int shiftId=Integer.parseInt(request.getParameter("shiftName"));
	    		if(shiftId!=0)
	    		{
	    		ServletOutputStream servletOutputStream = null;
	    		try {
	    		servletOutputStream = response.getOutputStream();
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		reportname="ShiftWiseBasicWorkDurReport"+today_date+".docx";
	    		exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ShiftWiseBasicWorkDurReport.pdf";
	    		exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ShiftWiseBasicWorkDurReport.docx"));
	    		ResultSet rs=reportDAO.getmusterReportOfEmployees(shiftId, month, year);
	    		String jasperPath=path+"ShiftWiseBasicWorkDurReport.jrxml";
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	    		parameters.put("MonthName",monthName);
	    		parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    		parameters.put("YearName",year);
	            parameters.put("Report_Title","Shift Wise Basic Work Duration Report");
	    		rs.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    		catch (SQLException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		}
	    	}
	        
	        
	        @RequestMapping(value="generateMaharastraMusterRollReportInDocx.htm",method=RequestMethod.POST)
	    	public void generateMaharastraMusterRollReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,@RequestParam(value="reportType") String reportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	    	{
	    		int workId=0;
	    		ModelMap modelMap=getshiftWiseReportModelMap();
	    		int shiftId=Integer.parseInt(request.getParameter("shiftName"));
	    		modelMap.addAttribute("reportType", reportType);
	    		ServletOutputStream servletOutputStream = null;
	    		String month=(Integer.parseInt(request.getParameter("iMonth"))+1)+"";
	    		String year=request.getParameter("iYear");
	    		String monthName = "invalid";
	    	    DateFormatSymbols dfs = new DateFormatSymbols();
	    	    String[] months = dfs.getMonths();
	    	    monthName = months[Integer.parseInt(request.getParameter("iMonth"))];
	    		String jasperPath=null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			
	    		if(empReportType.equals("multiple"))
	    		{
	    			reportname="MaharastraMusterRollReportMultiple"+today_date+".docx";
	    			rs=reportDAO.getMaharastraMusterRollReport(shiftId,workId, empReportType, selectedcheckbox, month, year);
	    			jasperPath=path+"MaharastraMusterRollReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MaharastraMusterRollReportMultiple.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MaharastraMusterRollReportMultiple.docx"));
	    			
	    		}
	    		else if(empReportType.equals("single"))
	    		{
	    			reportname="MaharastraMusterRollReportSingle"+today_date+".docx";
	    			workId=Integer.parseInt(request.getParameter("employeeNo"));
	    			rs=reportDAO.getMaharastraMusterRollReport(shiftId,workId,empReportType, selectedcheckbox, month, year);
	    			jasperPath=path+"MaharastraMusterRollReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MaharastraMusterRollReportSingle.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MaharastraMusterRollReportSingle.docx"));
	    		}
	    		else
	    		{
	    			reportname="MaharastraMusterRollReport"+today_date+".docx";
	    			rs=reportDAO.getMaharastraMusterRollReport(shiftId,workId,empReportType, selectedcheckbox, month, year);
	    			jasperPath=path+"MaharastraMusterRollReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"MaharastraMusterRollReport.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"MaharastraMusterRollReport.docx"));
	    		}
	    			
	    			rs.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("Report_Title","Maharastra Muster Roll report");
	    	        parameters.put("MonthName",monthName);
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    			parameters.put("YearName",year);
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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

	    		//return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	    	}
	        @RequestMapping(value="canteenitemsreportInDocx.htm",method=RequestMethod.GET)
	    	public void generateCanteenItemsReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	    	{
	    		ServletOutputStream servletOutputStream = null;
	    		try {
	    			servletOutputStream = response.getOutputStream();
	    		
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		reportname="CanteenItemsReport"+today_date+".docx";
	    		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
	    		OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"CanteenItemsReport.docx"));
	    		ResultSet rs=reportDAO.getCanteenItemsReport();
	    		String jasperPath=path+"CanteenItemsReport.jrxml";
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("reportTitle","Canteen Items Report");
	            parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	          	rs.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "CanteenItemsReport.pdf");
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    	
	    	
	    	
	    	@RequestMapping(value="canteentimingsreportInDocx.htm",method=RequestMethod.GET)
	    	public void generateCanteenTimingsReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session)
	    	{
	    		ServletOutputStream servletOutputStream = null;
	    		try {
	    			servletOutputStream = response.getOutputStream();
	    		
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		reportname="CanteenTimingsReport"+today_date+".docx";
	    		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
	    		OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"CanteenTimingsReport.docx"));
	    		ResultSet rs=reportDAO.getCanteenTimingsReport();
	    		String jasperPath=path+"CanteenTimingsReport.jrxml";
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("reportTitle","Canteen Timings Report");
	            parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	          	rs.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "CanteenTimingsReport.pdf");
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatevehiclelogswophotoreportInDocx.htm",method=RequestMethod.POST)
	    	public void generateVehicleLogsWoPhotoReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{
	    		
	    		ModelMap modelMap=new ModelMap();
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		String concernPerson=request.getParameter("concernPerson");
	    		String status=request.getParameter("status");
	    		String filter=request.getParameter("filter");
	    		String vehicleNumber=request.getParameter("vehicleNumber");
	    		String vehicleType=request.getParameter("vehicleType");
	    		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
	    		ServletOutputStream servletOutputStream = null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		String jasperPath=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			reportname="VehicleLogsWoPhotoReport"+today_date+".docx";
	    			rs=reportDAO.getVehicleLogsWoPhotoReport(dateFrom,dateTo,concernPerson,status,filter,vehicleNumber,vehicleType);
	    			
	    			jasperPath=path+"VehicleLogsWoPhotoReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VehicleLogsWoPhotoReport.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VehicleLogsWoPhotoReport.docx"));
	    			
	    		
	    			
	    			rs.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("reportTitle","Vehicle Logs Report");
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatevehiclefrequencyreportInDocx.htm",method=RequestMethod.POST)
	    	public void generateVehicleFrequencyReportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{
	    		
	    		ModelMap modelMap=new ModelMap();
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		String concernPerson=request.getParameter("concernPerson");
	    		String status=request.getParameter("status");
	    		String filter=request.getParameter("filter");
	    		String vehicleNumber=request.getParameter("vehicleNumber");
	    		String vehicleType=request.getParameter("vehicleType");
	    		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
	    		ServletOutputStream servletOutputStream = null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		String jasperPath=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			
	    			rs=reportDAO.getVehicleFrequencyLogs(dateFrom,dateTo,concernPerson,status,filter,vehicleNumber,vehicleType);
	    			reportname="VehicleLogsFrequencyReport"+today_date+".docx";
	    			jasperPath=path+"VehicleLogsFrequencyReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VehicleLogsFrequencyReport.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VehicleLogsFrequencyReport.docx"));
	    			
	    			rs.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("reportTitle","Vehicle Logs Report");
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatedepartmentwiseextraworkreportInDocx.htm",method=RequestMethod.POST)
	    	public void generateDepartmentWiseExtraWorkReportInDocx(@RequestParam(value="departmentId") int departmentId,@RequestParam(value="selectedcheckbox") String selectedcheckbox,@RequestParam(value="empReportType") String empReportType,HttpServletRequest request,HttpServletResponse response, HttpSession session) throws SQLException 
	    	{
	    		ModelMap modelMap=getDepartmentWiseReportModelMap();
	    		int employeeId=0;
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
	    		
	    		ServletOutputStream servletOutputStream = null;
	    		String jasperPath=null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			
	    		if(empReportType.equals("multiple"))
	    		{
	    			reportname="ExtraWorkReportMultiple"+today_date+".docx";
//	    			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
	    			rs=reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
	    			jasperPath=path+"ExtraWorkReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ExtraWorkReportMultiple.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ExtraWorkReportMultiple.docx"));
	    		}
	    		
	    		else if(empReportType.equals("single"))
	    		{
	    			reportname="ExtraWorkReportSingle"+today_date+".docx";
	    			employeeId=Integer.parseInt(request.getParameter("employeeNo"));
//	    			rs=reportDAO.getDepartmentWiseEmployeeList(workId, dateFrom, dateTo,empReportType,selectedcheckbox,0);
	    			rs=reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, 0);
	    			jasperPath=path+"ExtraWorkReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ExtraWorkReportSingle.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ExtraWorkReportSingle.docx"));
	    		}
	    		else
	    		{
	    			reportname="ExtraWorkReportAll"+today_date+".docx";
//	    			rs=reportDAO.getDepartmentWiseEmployeeList(employeeId, dateFrom, dateTo,empReportType,selectedcheckbox,departmentId);
	    			rs=reportDAO.getExtraWorkReport(employeeId, dateFrom, dateTo, empReportType, selectedcheckbox, departmentId);
	    			jasperPath=path+"ExtraWorkReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"ExtraWorkReportAll.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"ExtraWorkReportAll.docx"));
	    		}
	    			
	    			rs.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("Report_Title","Extra Work report");
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    	        parameters.put("Department_Name", departmentDAO.getDepartmentById(departmentId).get(0).getName());
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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

//	    		return new ModelAndView(new RedirectView("showdepartmentwisereport.htm"));
	    	}
	    	@RequestMapping(value="generatevisitordailyreportInDocx.htm",method=RequestMethod.POST)
	    	public void generateVisitorDailyReportInDocx(@RequestParam(value="reportTypeVar") String reportTypeVar, HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{

	    		int visitorId=0;
	    		int employeeId=0;
	    		String jasperPath="";
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
	    		ResultSet resultSet=null;
	    		
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("reportTitle","Visitors Daily Report"); 
	            parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	            ClassLoader classLoader=getClass().getClassLoader();
	            InputStream inputStream1=classLoader.getResourceAsStream(request.getSession().getServletContext().getRealPath("\\"));
	            parameters.put("folderPath", request.getSession().getServletContext().getRealPath("\\"));
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		if(reportTypeVar.equals("VisitorWise"))
	    		{
	    			if(request.getParameter("visitorsSelectId")!=null)
	    			{
	    				
	    				visitorId=Integer.parseInt(request.getParameter("visitorsSelectId"));
	    				parameters.put("visitorName",visitorsDAO.getVisitor(visitorId).getVisitorName());
	    				jasperPath=path+"VisitorsDailyReportVisitor.jrxml";
	    				//jasperPath=path+"VisitorsDailyReportVisitorImage.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		
	    		if(reportTypeVar.equals("EmployeeWise"))
	    		{
	    			if(request.getParameter("employeesSelectId")!=null)
	    			{
	    				employeeId=Integer.parseInt(request.getParameter("employeesSelectId"));
	    				Employee employee=employeeDAO.getEmployeeById(employeeId);
	    				parameters.put("employeeName", employee.getFirstName()+" "+employee.getLastName());
	    				jasperPath=path+"VisitorsDailyReportEmployee.jrxml";
	    				//jasperPath=path+" VisitorsDailyReportVisitorImage.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		ServletOutputStream servletOutputStream = null;
	    		try 
	    		{
	    		servletOutputStream = response.getOutputStream();
	    		OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorsDailyReport.docx"));
	    		resultSet.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            reportname="VisitorsDailyReport"+today_date+".docx";
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "VisitorsDailyReport.pdf");
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    	
	    	
	    	
	    	@RequestMapping(value="generatevisitorwithPhotoreportInDocx.htm",method=RequestMethod.POST)
	    	public void generatevisitorwithPhotoreportInDocx(@RequestParam(value="reportTypeVar") String reportTypeVar, HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{

	    		int visitorId=0;
	    		int employeeId=0;
	    		String jasperPath="";
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
	    		ResultSet resultSet=null;
	    		
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("reportTitle","Visitors Daily Report"); 
	            ClassLoader classLoader=getClass().getClassLoader();
	            InputStream inputStream1=classLoader.getResourceAsStream(request.getSession().getServletContext().getRealPath("\\"));
	            parameters.put("folderPath", request.getSession().getServletContext().getRealPath("\\"));
	            parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		if(reportTypeVar.equals("VisitorWise"))
	    		{
	    			if(request.getParameter("visitorsSelectId")!=null)
	    			{
	    				visitorId=Integer.parseInt(request.getParameter("visitorsSelectId"));
	    				parameters.put("visitorName",visitorsDAO.getVisitor(visitorId).getVisitorName());
	    				jasperPath=path+"VisitorsDailyReportVisitorWithPhoto.jrxml";
	    				//jasperPath=path+"VisitorsDailyReportVisitorImage.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		
	    		if(reportTypeVar.equals("EmployeeWise"))
	    		{
	    			if(request.getParameter("employeesSelectId")!=null)
	    			{
	    				employeeId=Integer.parseInt(request.getParameter("employeesSelectId"));
	    				Employee employee=employeeDAO.getEmployeeById(employeeId);
	    				parameters.put("employeeName", employee.getFirstName()+" "+employee.getLastName());
	    				//jasperPath=path+"VisitorsDailyReportEmployee.jrxml";
	    				jasperPath=path+"VisitorsDailyReportEmployeeWithPhoto.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		
	    		
	    		ServletOutputStream servletOutputStream = null;
	    		try 
	    		{
	    			
	    		servletOutputStream = response.getOutputStream();
	    		

	    		OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorsDailyReport.docx"));
	    		
	    		
	    		resultSet.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "VisitorsDailyReport.pdf");
	            response.setContentType("application/pdf");
	            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
	            
	            reportname="VisitorsDailyReport"+today_date+".docx";
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatevisitorfrequencyreportInDocx.htm",method=RequestMethod.POST)
	    	public void generateVisitorFrequencyReportInDocx(@RequestParam(value="selectedcheckbox") String selectedcheckbox, @RequestParam(value="visitorReportType") String visitorReportType, HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{
//	    		ModelMap modelMap=getVisitorReportsModelMap();
	    		int visitorId=0;
	    		
	    		ServletOutputStream servletOutputStream = null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		String jasperPath=null;
	    		ResultSet resultSet=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			
	    		if(visitorReportType.equals("multiple"))
	    		{
	    			reportname="VisitorFrequencyReportMultiple"+today_date+".docx";
	    			resultSet=reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
	    			jasperPath=path+"VisitorFrequencyReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VisitorFrequencyReportMultiple.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorFrequencyReportMultiple.docx"));		
	    		}
	    		else if(visitorReportType.equals("single"))
	    		{
	    			reportname="VisitorFrequencyReportSingle"+today_date+".docx";
	    			visitorId=Integer.parseInt(request.getParameter("visitorselect"));
	    			resultSet=reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
	    			jasperPath=path+"VisitorFrequencyReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VisitorFrequencyReportSingle.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorFrequencyReportSingle.docx"));
	    		}
	    		else
	    		{
	    			reportname="VisitorFrequencyReportAll"+today_date+".docx";
	    			resultSet=reportDAO.getVisitorFrequencyReport(visitorId, selectedcheckbox, visitorReportType);
	    			jasperPath=path+"VisitorFrequencyReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VisitorFrequencyReportAll.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorFrequencyReportAll.docx"));
	    		}
	    			
	    			resultSet.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("ReportTitle","Visitor Frequency Report");
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatevisitorMaterialreportInDocx.htm",method=RequestMethod.POST)
	    	public void generatevisitorMaterialreportInDocx(@RequestParam(value="reportTypeVar") String reportTypeVar, HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{
//	    		ModelMap modelMap=getVisitorReportsModelMap();
	    		int visitorId=0;
	    		int employeeId=0;
	    		String jasperPath="";
	    		String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    		String exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\";
	    		ResultSet resultSet=null;
	    		
	    		Map<String, Object> parameters = new HashMap<String, Object>();
	            parameters.put("reportTitle","Visitors Material's Report");
	            parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	            
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		if(reportTypeVar.equals("VisitorWise"))
	    		{
	    			if(request.getParameter("visitorsSelectId")!=null)
	    			{
	    				
	    				visitorId=Integer.parseInt(request.getParameter("visitorsSelectId"));
	    				parameters.put("visitorName",visitorsDAO.getVisitor(visitorId).getVisitorName());
	    				jasperPath=path+"VisitorsMaterialCarriedReport.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		
	    		if(reportTypeVar.equals("EmployeeWise"))
	    		{
	    			if(request.getParameter("employeesSelectId")!=null)
	    			{
	    				employeeId=Integer.parseInt(request.getParameter("employeesSelectId"));
	    				Employee employee=employeeDAO.getEmployeeById(employeeId);
	    				parameters.put("employeeName", employee.getFirstName()+" "+employee.getLastName());
	    				jasperPath=path+"VisitorsMaterialCarriedReportEmployee.jrxml";
	    				resultSet=reportDAO.getVisitorDailyReport(reportTypeVar, visitorId, employeeId, dateFrom, dateTo);
	    			}
	    		}
	    		
	    		
	    		ServletOutputStream servletOutputStream = null;
	    		try 
	    		{
	    			
	    		servletOutputStream = response.getOutputStream();
	    		

	    		OutputStream exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VisitorsDailyReport.docx"));
	    		
	    		
	    		resultSet.beforeFirst();
	            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
	            InputStream inputStream = new FileInputStream(jasperPath);
	            //jasperDesign = JasperManager.loadXmlDesign(inputStream);
	            reportname="VisitorsMaterialCarriedReport"+today_date+".docx";
	            jasperDesign = JRXmlLoader.load(inputStream);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	            JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath+ "VisitorsMaterialCarriedReport.pdf");
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
	            JRDocxExporter exporter = new JRDocxExporter();
		           
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
		        exporter.exportReport();
	            OutputStream out= new FileOutputStream(new File("D:/Distna_Report/"+reportname));
		        out.write(outputByteArray.toByteArray());
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
	    	@RequestMapping(value="generatevehicleMateriallogsreportInDocx.htm",method=RequestMethod.POST)
	    	public void generatevehicleMateriallogsreportInDocx(HttpServletRequest request,HttpServletResponse response, HttpSession session) 
	    	{
	    		
	    		ModelMap modelMap=new ModelMap();
	    		String dateFrom=request.getParameter("datefrom");
	    		String dateTo=request.getParameter("dateTo");
	    		String concernPerson=request.getParameter("concernPerson");
	    		String status=request.getParameter("status");
	    		String filter=request.getParameter("filter");
	    		String vehicleNumber=request.getParameter("vehicleNumber");
	    		String vehicleType=request.getParameter("vehicleType");
	    		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
	    		ServletOutputStream servletOutputStream = null;
	    		String exportPath=null;
	    		OutputStream exportPathXLS=null;
	    		String jasperPath=null;
	    		try 
	    		{
	    			servletOutputStream = response.getOutputStream();
	    			String path=request.getSession().getServletContext().getRealPath("jasperReports")+"\\";
	    			
	    			rs=reportDAO.getVehicleLogsWoPhotoReport(dateFrom,dateTo,concernPerson,status,filter,vehicleNumber,vehicleType);
	    			
	    			jasperPath=path+"VehicleMaterialLogsReport.jrxml";
	    			exportPath=request.getSession().getServletContext().getRealPath("reports") + "\\"+"VehicleMaterialLogsReport.pdf";
	    			exportPathXLS=new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("reportsXLS") + "\\"+"VehicleMaterialLogsReport.docx"));
	    			reportname="VehicleMaterialLogsReport"+today_date+".docx";
	    		
	    			
	    			rs.beforeFirst();
	    			Map<String, Object> parameters = new HashMap<String, Object>();
	    	        parameters.put("reportTitle","Vehicle Materials Carried Logs Report");
	    	        parameters.put("imagePath", request.getSession().getServletContext().getRealPath("jasperReports")+"\\");
	    	        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
	    	        InputStream inputStream = new FileInputStream(jasperPath);
	    	        jasperDesign = JRXmlLoader.load(inputStream);
	    	        jasperReport = JasperCompileManager.compileReport(jasperDesign);
	    	        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,resultSetDataSource);
	    	        JasperExportManager.exportReportToPdfFile(jasperPrint,exportPath);
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
	    	        JRDocxExporter exporter = new JRDocxExporter();
			           
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputByteArray);
			        exporter.exportReport();
	    	        OutputStream out= new FileOutputStream(new File("D://Distna_Report//"+reportname));
	    	        out.write(outputByteArray.toByteArray());
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
}
