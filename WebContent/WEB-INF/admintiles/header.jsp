<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>header</title>
<link href="css/drop_list.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/main.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/demo.css" />

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easing.1.3.js"></script>

<script type="text/javascript" src="../js/script.js"></script> -->

</head>

<body>
<!-- <div id="wrapper">
  <div id="header">
  <div  style=" float: right; margin-right: 10px; margin-top: 10px;">
  <a href="loginpage.htm"><font color="white" size="4">Logout</font></a>
  </div>
  </div>

</div> -->
<c:set var="userDashboardFlag" value="false"></c:set>
<c:set var="reportsFlag" value="false"></c:set>
<c:set var="calendarFlag" value="false"></c:set>
<c:set var="leavesFlag" value="false"></c:set>
<c:set var="employeesFlag" value="false"></c:set>
<c:set var="companyFlag" value="false"></c:set>
<c:set var="deviceManagement" value="false"></c:set>
<c:set var="visitorManagement" value="false"></c:set>
<%
String employeePrivileges=null;
String []employeePrivilegesArray=null;
if(null!=session.getAttribute("masterPrivilege"))
{
	if(session.getAttribute("masterPrivilege").equals(true)){
		%>
	<c:set var="masterPrivilege" value="true"></c:set>
		<% 
	}else{
		%>
		<c:set var="masterPrivilege" value="false"></c:set>
		<% 
	}
		
}
if(null!=session.getAttribute("employeePrivilegesString"))
{
employeePrivileges=(String)session.getAttribute("employeePrivilegesString");
employeePrivilegesArray=employeePrivileges.split(",");
for(String employeePrivilegesString:employeePrivilegesArray)
{
	if(Integer.parseInt(employeePrivilegesString)==4)
	{
		%>
		<c:set var="companyFlag" value="true"></c:set>
		<%
		continue;
	}
	if(Integer.parseInt(employeePrivilegesString)==5)
	{
		%>
		<c:set var="employeesFlag" value="true"></c:set>
		<%
		continue;
	}
	
	if(Integer.parseInt(employeePrivilegesString)==2)
	{
		%>
		<c:set var="leavesFlag" value="true"></c:set>
		<%
		continue;
	}
	
	if(Integer.parseInt(employeePrivilegesString)==6)
	{
		%>
		<c:set var="calendarFlag" value="true"></c:set>
		<%
		continue;
	}
	
	if(Integer.parseInt(employeePrivilegesString)==7)
	{
		%>
		<c:set var="reportsFlag" value="true"></c:set>
		<%
		continue;
	}
	if(Integer.parseInt(employeePrivilegesString)==8)
	{
		%>
		<c:set var="deviceManagement" value="true"></c:set>
		<%
		continue;
	}
	if(Integer.parseInt(employeePrivilegesString)==9)
	{
		%>
		<c:set var="visitorManagement" value="true"></c:set>
		<%
		continue;
	}
// 	if(Integer.parseInt(employeePrivilegesString)==1)
// 	{
		%>
		<c:set var="userDashboardFlag" value="true"></c:set>
		<%
		//	continue;
// 	}
}
}
else
{
	if(null!=session.getAttribute("loginUser")&&session.getAttribute("loginUser").equals("admin"))
	{
	%>
	<c:set var="userDashboardFlag" value="false"></c:set>
	<c:set var="reportsFlag" value="false"></c:set>
	<c:set var="calendarFlag" value="false"></c:set>
	<c:set var="leavesFlag" value="false"></c:set>
	<c:set var="employeesFlag" value="true"></c:set>
	<c:set var="companyFlag" value="true"></c:set>
	<c:set var="deviceManagement" value="false"></c:set>
	<c:set var="visitorManagement" value="false"></c:set>
	<%-- <c:set var="userDashboardFlag" value="true"></c:set>
		<c:set var="reportsFlag" value="true"></c:set>
		<c:set var="calendarFlag" value="true"></c:set>
		<c:set var="leavesFlag" value="true"></c:set>
		<c:set var="employeesFlag" value="true"></c:set>
		<c:set var="companyFlag" value="true"></c:set>
		<c:set var="deviceManagement" value="true"></c:set> --%>
	<%
	}
	else
	{
		%>
		<c:set var="userDashboardFlag" value="true"></c:set>
		<c:set var="reportsFlag" value="true"></c:set>
		<c:set var="calendarFlag" value="true"></c:set>
		<c:set var="leavesFlag" value="true"></c:set>
		<c:set var="employeesFlag" value="true"></c:set>
		<c:set var="companyFlag" value="true"></c:set>
		<c:set var="deviceManagement" value="true"></c:set>
		<c:set var="visitorManagement" value="true"></c:set>
		<%
	}
}
%>



<div id="wrapper">
    	<div id="header">
    	 <img src="images/sensedgelogo.png" align="left" style="padding-top: 5px;" /> 	
    	 <div  style=" float: right; margin-right: 10px; margin-top: 10px;">
  <a href="loginpage.htm"><font color="white" size="4">Logout</font></a>
  </div>
    	<div class="nav">
  <br/>
<nav>
	<ul>
	<c:if test="${companyFlag eq true}">
		<li><a href="#">Company</a>
		<ul>

	<li><a href="#">Company</a>
            	<ul>
               		<li><a href="showCompany.htm">Add</a></li>
					<li><a href="viewUpdateCompany.htm">View</a></li>
                </ul>
    </li>
    
    <li><a href="#">Location</a>
            	<ul>
               		<li><a href="addlocation.htm">Add</a></li>
               		<li><a href="viewlocation.htm">View</a></li>
					
                </ul>
    </li>
    
     <li><a href="#">Division</a>
            	<ul>
               		<li><a href="showAddDivision.htm">Add</a></li>
					<li><a href="showViewDivision.htm">View</a></li>
                </ul>
   	 </li>

 	<li><a href="#">Department</a>
            	<ul>
               		<li><a href="department.htm">Add</a></li>
               		<li><a href="viewDepartment.htm">View</a></li>
					
                </ul>
    </li>
    
    
    <li><a href="#">Workspaces</a>
            	<ul>
               		<li><a href="showAddWorkspaces.htm">Add</a></li>
					<li><a href="viewWorkspaces.htm">View</a></li>
                </ul>
    </li>
    
    <li><a href="#">Designation</a>
            	<ul>
               		<li><a href="designation.htm?view=main">Add</a></li>
               		<li><a href="viewDesignation.htm">View</a></li>	
                </ul>
    </li>
    
   <!--  <li><a href="#">Designation Levels</a>
            	<ul>
               		<li><a href="showDesignationLevel.htm">Add</a></li>
					<li><a href="viewDesignationLevel.htm">View</a></li>
                </ul>
    </li> -->
    
      
    
    <!--  <li><a href="#">Company Policies</a>
            	<ul>
               		<li><a href="showAddCompanyPolicies.htm">Add</a></li>
					<li><a href="viewCompanyPolicies.htm">View</a></li>
                </ul>
    </li> -->
    
   
    <!-- <li><a href="#">Job Roles</a>
            	<ul>
               		<li><a href="showAddJobRoles.htm">Add</a></li>
					<li><a href="viewJobRoles.htm">View</a></li>
                </ul>
    </li>
    -->
    
    <!--  <li><a href="#">Job Positions</a>
            	<ul>
               		<li><a href="jobposition.htm">Add</a></li>
               		<li><a href="viewJobPosition.htm">View</a></li>
					
                </ul>
    </li> -->
	
    <!-- / -->
    
     <!-- <li><a href="#">Contractor/Sub Company</a>
            	<ul>
               		<li><a href="showAddContractor.htm">Add</a></li>
               		<li><a href="viewContractor.htm">View</a></li>
					
                </ul>
    </li>
	 <li><a href="#">Canteen</a>
    	<ul>
	      <li><a href="#">Canteen Items</a>
					<ul>
	               		<li><a href="showaddcanteenitems.htm">Add</a></li>
	               		<li><a href="showviewcanteenitems.htm">View</a></li>
	                </ul>
			</li>
			 <li><a href="#">Canteen Timings</a>
					<ul>
	               		<li><a href="showaddcanteentimings.htm">Add</a></li>
	               		<li><a href="showviewcanteentimings.htm">View</a></li>
	                </ul>
			</li>
    	</ul>
    </li> -->
   <!--  <li><a href="#">Master Settings</a>
            	<ul>
               		<li><a href="showEmployeeFolderPath.htm">Employee Photo Folder Path</a></li>
					<li><a href="showMasterSettings.htm">Master settings</a></li>
					<li><a href="showrestoredatabasebackup.htm">Restore Database Backup</a></li>
					<li><a href="exportEmployeedata.htm">Export Employee Data</a></li>
					<li><a href="showImportEmployeedata.htm">Import Employee Data</a></li>
                </ul>
    </li> -->
	</ul>
</li>
</c:if>
<c:if test="${employeesFlag eq true}">
		<!-- <li><a href="#">Employees</a>
			<ul>
 -->
 <li><a href="#">Employees</a>
            	<ul>
               		<li><a href="showEmployees.htm">Add</a></li>
               		<li><a href="viewEmployees.htm">View</a></li>
					
                </ul>
    </li> 
	

<!-- <li><a href="#">Device Management</a>
            	<ul>
               		<li><a href="addAttendanceDevice.htm">Add Device</a></li>
					<li><a href="updateAttendanceDevice.htm">Update Device</a></li>
                </ul>
</li> -->
	
    <!-- <li><a href="#">Employee Privileges</a>
            	<ul>
               		<li><a href="showAddPrivileges.htm">Add</a></li>
               		<li><a href="viewPrivilegesDetails.htm">View</a></li>
                </ul>
    </li>  -->
	
 <!--    <li><a href="#">Personal</a>
            	<ul>
               		<li><a href="showAddPersonalDetails.htm">Add</a></li>
               		<li><a href="viewPersonalDetails.htm">View</a></li>
                </ul>
    </li>
    
    <li><a href="#">Education</a>
            	<ul>
               		 <li><a href="showAddEducationalDetails.htm">Add</a></li>
               		<li><a href="viewEducation.htm">View</a></li>
                </ul>
    </li>
    
     <li><a href="#">Assesment</a>
            	<ul>
               		<li><a href="showAssesment.htm">Add</a></li>
               		<li><a href="viewAssesment.htm">View</a></li>
                </ul>
    </li>
    
  
     <li><a href="#">Projects</a>
            	<ul>
               		<li><a href="showAddEmployeeProject.htm">Add</a></li>
               		<li><a href="viewEmployeeProject.htm">View</a></li>
                </ul>
    </li>
    
    <li><a href="#">Experiences</a>
            	<ul>
               		<li><a href="showaddexperience.htm">Add</a></li>
					<li><a href="showviewexperience.htm">View</a></li>
                </ul>
    </li>
    
	<li><a href="#">Skills</a>
            	<ul>
               		<li><a href="showaddskills.htm">Add</a></li>
					<li><a href="showviewskills.htm">View</a></li>
                </ul>
    </li>
    
    
    <li><a href="#">Bank Details</a>
            	<ul>
               		<li><a href="showaddbank.htm">Add</a></li>
					<li><a href="showviewbank.htm">View</a></li>
                </ul>
    </li> -->
    
 <!--  <li><a href="#">Messages / Memo</a>
            	<ul>
               		<li><a href="showaddmessage.htm">Add</a></li>
					<li><a href="showviewmessage.htm">View</a></li>
                </ul>
    </li> -->
   	            		
	 	</c:if>
		<c:if test="${leavesFlag eq true}">
		<!-- <li><a href="#">Leaves</a>
			<ul>
			
			
	<li><a href="#">Leave Type</a>
            	<ul>
               		<li><a href="leaveType.htm">Add</a></li>
					<li><a href="viewleaveType.htm">View</a></li>
                </ul>
     </li>
     
     <li><a href="#">Leave Allocation</a>
            	<ul>
               		<li><a href="leaveAllocation.htm">Add</a></li>
               		<li><a href="viewleaveApplication.htm">View Pending Leaves</a></li>
               		<li><a href="AllocateLeave.htm">Allocate Pending Leaves</a></li>
					<li><a href="viewleaveAllocation.htm">View Allocated Leaves</a></li>
					<li><a href="viewEmployeeleaveStatus.htm">View Leaves Status</a></li>
                </ul>
    </li>
    
    
	<li><a href="#">Breaks</a>
            	<ul>
               		<li><a href="breaks.htm">Add</a></li>
					<li><a href="viewBreaks.htm">View</a></li>
                </ul>
    </li>
    
    
   	<li><a href="#">Tour</a>
			<ul>
				<li><a href="showOfficialTour.htm">Add</a></li>
				<li><a href="viewUpdateOfficialTour.htm">View</a></li>
			</ul>
	</li>
	
	 <li><a href="#">Out For Work</a>
			<ul>
				<li><a href="viewOutForWork.htm">View Pending</a></li>
				<li><a href="viewOutForWorkApproved.htm">View Approved</a></li>
			</ul>
	</li>
	
	
	 
    
  	 <li><a href="#">Outdoor Entries</a>
            	<ul>
               		<li><a href="showOutdoorEntries.htm">Add Entries</a></li>
					<li><a href="viewleaveApplication.htm">View</a></li>
                </ul>
    </li> 
    
    <li><a href="#">Leave Application</a>
            	<ul>
               		<li><a href="leaveApplication.htm">Add</a></li>
					<li><a href="viewleaveApplication.htm">View</a></li>
                </ul>
    </li>
</ul>
		</li> -->
		</c:if>
		
		
		<c:if test="${calendarFlag eq true}" >
		<!-- <li><a href="#">Calendar</a>
			<ul>
<li><a href="#">Shift Master</a>
				<ul>
				<li><a href="showShiftMaster.htm">Add</a></li>
				<li><a href="viewUpdateShiftMaster.htm">View</a></li>
				</ul>
			</li>
			<li><a href="#">Shift Allocation</a>
				<ul>
				<li><a href="allocateShiftSingleEmployee.htm">Shift Allocation: Single Employee</a></li>
				<li><a href="viewUpdateShiftMaster.htm">Shift Allocation: Multiple Employee</a></li>
				</ul>
			</li>
			<li><a href="viewEmployeeCalendar.htm">View Employee Calendar</a>
			</li>
			<li><a href="#">Shift Calender</a>
				<ul>
				<li><a href="createShiftDefinition.htm">Create Shift Definition</a></li>
				<li><a href="showMonthlyCalendar.htm">Monthly Calendar</a></li>				
				</ul>
			</li>
			
			<li><a href="#">Holiday</a>
				<ul>
				<li><a href="showaddholiday.htm">Add</a></li>
				<li><a href="showviewholiday.htm">View</a></li>
				</ul>
			</li>
   	 </ul>
			
		</li> -->
		</c:if>
		
				
		<c:if test="${masterPrivilege eq true}">
		<c:if test="${visitorManagement eq true}">
		<li><a href="#">Visitor Management</a>
			<ul>
               		<li><a href="#">Visitors</a>
               		<ul>
	               		<li><a href="showaddvisitors.htm">Add</a></li>
	               		<li><a href="viewvisitors.htm">View</a></li>
               		</ul>
               		
               		</li>
               		
               		<li><a href="#">Visitor Logs</a>
               		<ul>
	               		<li><a href="showaddvisitorlogs.htm">Add</a></li>
	               		<li><a href="viewvisitorlogs.htm">View</a></li>
               		</ul>
               		
               		</li>
               		
               		<li><a href="#">Vehicles</a>
               		<ul>
	               		<li><a href="showaddvehicledetails.htm">Add</a></li>
	               		<li><a href="viewvehicledetails.htm">View</a></li>
               		</ul>
               		
               		</li>
               		
               		<li><a href="#">Vehicle Logs</a>
               		<ul>
	               		<li><a href="showaddvehiclelogs.htm">Add</a></li>
	               		<li><a href="viewvehiclelogs.htm">View</a></li>
               		</ul>
               		</li>
               		
					<li><a href="showApprovedVisitors.htm">Visitor DashBoard</a></li>	
					 <li><a href="exportVisitors.htm">Export Visitors</a></li>	
					<!-- <li><a href="showImportTableData.htm">Import Visitors</a></li> -->
                </ul>
		</li>
		</c:if>
		</c:if>
		
		
		
		<c:if test="${reportsFlag eq true}">
		<li><a href="#">Reports</a>
			<ul>
			<!-- <li><a href="#">Reports</a> -->
				<ul>
				<!-- <li><a href="#">Employee Wise Report</a>
					<ul>
					<li><a href="showEmployeeWiseReport.htm">Employee Wise Attendance Report</a></li>
               		<li><a href="showEmployeeWiseExceptionReport.htm">Exception Report</a></li>
               		<li><a href="showEmployeeWiseAllPunchesReport.htm">All Punches Report</a></li>
               		</ul>
				</li>
				<li><a href="#">Department Wise Report</a>
					<ul>
					<li><a href="showdepartmentwisereport.htm"> Department Wise Report</a></li>
					<li><a href="showDepartmentSummaryReport.htm">Department Summary Report</a></li>
					<li><a href="departmentwiseextraworkreport.htm">Extra Work Report</a></li>
					<li><a href="departmentwiseshortworkreport.htm">Short Work Report</a></li>
					<li><a href="showDepartmentOutForWorkReport.htm">Department Wise OutForWork Report</a></li>
					</ul>
				
				</li>
			    <li><a href="totalWorkingHoursReport.htm">Total Working Hours</a></li>
				<li><a href="#">Shiftwise Daily Report</a>
				<ul>
               		<li><a href="shiftWiseDailyReport.htm?reportType=InOut">Check In/Out</a></li>
               		<li><a href="shiftWiseDailyReport.htm?reportType=BreakInOut">Break In/Out</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=EarlyComing">Early Coming</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=LateComing">Late Coming</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=EarlyGoing">Early Going</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=LateGoing">Late Going</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=Absent">Absent</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=Present">Present</a></li>
					
                </ul>
				</li> -->
				<!-- <li><a href="showdailyattendancereport.htm">Daily Attendance Report</a></li>
				<li><a href="#" onclick="window.open('showemployeedetailsreport.htm')">Employee Personal Details Report</a></li>
				<li><a href="monthlyAttendanceReport.htm">Monthly Attendance Report</a></li>
				<li><a href="memoreport.htm">Memo Report</a></li> -->
					<!-- <li><a href="#">Canteen Report</a>
					<ul>
						<li><a href="#" onclick="window.open('canteenitemsreport.htm')">Canteen Items</a></li>
						<li><a href="#" onclick="window.open('canteentimingsreport.htm')">Canteen Timings</a></li>
					</ul>
				</li> -->
				</ul>
			</li>
			
			<!--  <li><a href="shiftWiseMusterReport.htm">Muster Reports</a>
				<ul><li><a href="shiftWiseMusterReport.htm">Muster Report</a></li>
					<li><a href="basicWorkDurationReport.htm">Monthly Basic Work Duration Report</a></li>
					<li><a href="showMaharastraMusterRollReport.htm">Maharashtra Muster Roll Report</a></li>
					<li><a href="monthlyReport2.htm">Monthly Detail Work Duration Report</a></li>
				</ul>
				</li>
			 -->
			<!-- <li><a href="#">Leave Reports</a>
				<ul>
				<li><a href="showleavereport.htm">Leave Report</a></li>
				<li><a href="showLeaveStatusReport.htm">Leaves Status</a></li>
				</ul>
			</li>
			<li><a href="#">Charts</a>
				<ul>
				<li><a href="showleavesperemployee.htm">Leaves Per Employee</a></li>
				<li><a href="showleavesperleavetype.htm">Leaves Per Leave Type</a></li>
				<li><a href="showemployeesperdepartment.htm">Employees per Department</a></li>
				<li><a href="showweeklytimecard.htm">Weekly Timecard</a></li>
				<li><a href="showpresenceabsencepercent.htm">Presence Absence Percentage</a></li>
				</ul>
			</li> -->
			<c:if test="${masterPrivilege eq true}">
			<li><a href="#">Visitor Reports</a>
				<ul>
				<li><a href="visitorCount.htm">VisitorCount</a></li>
				<li><a href="#">Daily Report</a>
					<ul>
						<li><a href="showvisitorlogswophoto.htm">Visitor Report</a></li>
						<!-- <li><a href="showvisitorlogsphoto.htm">Visitor Report With Photo</a></li> -->
						<li><a href="showvehiclelogswophoto.htm">Vehicle Report</a></li>
						<!-- <li><a href="showvehiclelogsphoto.htm">Vehicle Report With Photo</a></li> -->
					</ul>
				</li>
				<li><a href="#">Material Carried</a>
					<ul>
						<li><a href="showvisitorMateriallogs.htm">Visitor Report</a></li>
						<li><a href="showvehicleMateriallogs.htm">Vehicle Report</a></li>
					</ul>
				</li>
				<li><a href="#">Frequency of Visit Report</a>
					<ul>
						<li><a href="showvisitorfrequencyreport.htm">Visitor Frequency</a></li>
						<li><a href="showvehiclefrequencyreport.htm">Vehicle Frequency</a></li>
					</ul>
				</li>
				</ul>
			</li>
			</c:if>
			
   	 </ul>
		</li>
		</c:if>
		<%-- <c:if test="${deviceManagement eq true}">
		<!-- <li><a href="#">Device Management</a>
			<ul>
               		<li><a href="addAttendanceDevice.htm">Add Device</a></li>
					<li><a href="updateAttendanceDevice.htm">Update Device</a></li>
					<li><a href="usbdownload.htm">USB Download</a></li>
					<li><a href="showRestoreLogs.htm">Restore</a></li>
                </ul>
		</li> -->
		</c:if> --%>
		
	<%-- 	<c:if test="${userDashboardFlag eq true}" >
		<li><a href="showMyUserDashboard.htm">UserDashBoard</a>
		</li>
		</c:if>  --%>
		<!-- <li><a href="help.htm">Help</a>      	
    </li> -->
	</ul>
	
</nav>
        </div>
       </div> 
    </div>
</body>
</html>


	