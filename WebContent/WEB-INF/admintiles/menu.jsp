<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<head>
<title>Filament Group Lab</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    
    <script type="text/javascript" src="javascript/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="javascript/fg.menu.js"></script>
    <script type="text/javascript" src="javascript/displayPages.js"></script>
    <link type="text/css" href="css/main.css" media="screen" rel="stylesheet" />
    <link type="text/css" href="css/fg.menu.css" media="screen" rel="stylesheet" />
    <link type="text/css" href="css/theme/ui.all.css" media="screen" rel="stylesheet" />
    
    <!-- styles for this example page only -->
	<style type="text/css">
	body { font-size:62.5%; margin:0; padding:0; }
	#menuLog { font-size:1.4em; margin:20px; }
	.hidden { position:absolute; top:0; left:-9999px; width:1px; height:1px; overflow:hidden; }
	
	.fg-button { clear:left; margin:0 4px 10px 0px; width:155px; padding: .4em 1em; text-decoration:none !important; cursor:pointer; position: relative; text-align: center; zoom: 1; }
	.fg-button .ui-icon { position: absolute; top: 50%; margin-top: -8px; left: 50%; margin-left: -8px; }
	a.fg-button { float:left;  }
	button.fg-button { width:auto; overflow:visible; } /* removes extra button width in IE */
	
	.fg-button-icon-left { padding-left: 2.1em; }
	.fg-button-icon-right { padding-right: 2.1em; }
	.fg-button-icon-left .ui-icon { right: auto; left: .2em; margin-left: 0; }
	.fg-button-icon-right .ui-icon { left: auto; right: .2em; margin-left: 0; }
	.fg-button-icon-solo { display:block; width:8px; text-indent: -9999px; }	 /* solo icon buttons must have block properties for the text-indent to work */	
	
	.fg-button.ui-state-loading .ui-icon { background: url(spinner_bar.gif) no-repeat 0 0; }
	</style>
	
	<!-- style exceptions for IE 6 -->
	<!--[if IE 6]>
	<style type="text/css">
		.fg-menu-ipod .fg-menu li { width: 95%; }
		.fg-menu-ipod .ui-widget-content { border:0; }
	</style>
	<![endif]-->	
    
    <script type="text/javascript">    
    $(function(){
    	// BUTTONS
    	$('.fg-button').hover(
    		function(){ $(this).removeClass('ui-state-default').addClass('ui-state-focus'); },
    		function(){ $(this).removeClass('ui-state-focus').addClass('ui-state-default'); }
    	);
    	
    	// MENUS    	
		$('#flat').menu({ 
			content: $('#flat').next().html(), // grab content from this page
			showSpeed: 400 
		});
		
		$('#company').menu({
			content: $('#company').next().html(),
			crumbDefaultText: ' '
		});
		
		$('#employee').menu({
			content: $('#employee').next().html(),
			crumbDefaultText: ' '
		});
		$('#leaves').menu({
			content: $('#leaves').next().html(),
			crumbDefaultText: ' '
		});
		$('#shifts').menu({
			content: $('#shifts').next().html(),
			crumbDefaultText: ' '
		});
		$('#reports').menu({
			content: $('#reports').next().html(),
			crumbDefaultText: ' '
		});
		$('#userDashboard').menu({
			content: $('#userDashboard').next().html(),
			crumbDefaultText: ' '
		});
		$('#deviceManagement').menu({
			content: $('#deviceManagement').next().html(),
			crumbDefaultText: ' '
		});
		/* $('#clearAttRecords').menu({
			content: $('#clearAttRecords').next().html(),
			crumbDefaultText: ' '
		}); */
		$('#hierarchybreadcrumb').menu({
			content: $('#hierarchybreadcrumb').next().html(),
			backLink: false
		});
		
		
		// or from an external source
		$.get('menuContent.html', function(data){ // grab content from another page
			$('#flyout').menu({ content: data, flyOut: true });
		});
    });
    </script>
    
    <!-- theme switcher button -->
   <!--  <script type="text/javascript" src="http://ui.jquery.com/applications/themeroller/themeswitchertool/"></script> -->
	<script type="text/javascript"> $(function(){ $('<div style="position: absolute; top: 20px; right: 300px;" />').appendTo('body').themeswitcher(); }); </script>
</head>
<body>
<c:set var="userDashboardFlag" value="false"></c:set>
<c:set var="reportsFlag" value="false"></c:set>
<c:set var="calendarFlag" value="false"></c:set>
<c:set var="leavesFlag" value="false"></c:set>
<c:set var="employeesFlag" value="false"></c:set>
<c:set var="companyFlag" value="false"></c:set>
<c:set var="deviceManagement" value="false"></c:set>
<%
String employeePrivileges=null;
String []employeePrivilegesArray=null;
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
		<%
	}
}
%>
<div id="nav1">
<c:if test="${companyFlag eq true}">
<a tabindex="0" href="#news-items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="company"><span class="ui-icon ui-icon-triangle-1-s"></span>Company</a>
<div id="news-items" class="hidden">
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
    
      
    
     <li><a href="#">Company Policies</a>
            	<ul>
               		<li><a href="showAddCompanyPolicies.htm">Add</a></li>
					<li><a href="viewCompanyPolicies.htm">View</a></li>
                </ul>
    </li>
    
   
    <li><a href="#">Job Roles</a>
            	<ul>
               		<li><a href="showAddJobRoles.htm">Add</a></li>
					<li><a href="viewJobRoles.htm">View</a></li>
                </ul>
    </li>
   
    
    <!--  <li><a href="#">Job Positions</a>
            	<ul>
               		<li><a href="jobposition.htm">Add</a></li>
               		<li><a href="viewJobPosition.htm">View</a></li>
					
                </ul>
    </li> -->
	
    <!-- / -->
    
    <li><a href="#">Contractor/Sub Company</a>
            	<ul>
               		<li><a href="showAddContractor.htm">Add</a></li>
               		<li><a href="viewContractor.htm">View</a></li>
					
                </ul>
    </li>
    
   	 
   	 
	</ul>		
</div>
</c:if>
<c:if test="${employeesFlag eq true}">
<a tabindex="0" href="#news-items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="employee"><span class="ui-icon ui-icon-triangle-1-s"></span>Employees</a>
<div id="news-items" class="hidden">
<ul>

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
	
    <li><a href="#">Employee Privileges</a>
            	<ul>
               		<li><a href="showAddPrivileges.htm">Add</a></li>
               		<li><a href="viewPrivilegesDetails.htm">View</a></li>
                </ul>
    </li> 
	
    <li><a href="#">Personal</a>
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
    
  <li><a href="#">Messages</a>
            	<ul>
               		<li><a href="showaddmessage.htm">Add</a></li>
					<!-- <li><a href="showviewmessage.htm">View</a></li> -->
                </ul>
    </li>
    <li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
   	 </ul>
			
</div>
</c:if>

<c:if test="${deviceManagement eq true}">
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="deviceManagement"><span class="ui-icon ui-icon-triangle-1-s"></span>Device Management</a>
<div id="items" class="hidden">
<ul>
	<li><a href="#">Device Management</a>
            	<ul>
               		<li><a href="addAttendanceDevice.htm">Add Device</a></li>
					<li><a href="updateAttendanceDevice.htm">Update Device</a></li>
                </ul>
</li>

 <li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			 <li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			 <li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
</ul>
</div>
</c:if>

<c:if test="${leavesFlag eq true}">
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="leaves"><span class="ui-icon ui-icon-triangle-1-s"></span>Leaves</a>
<div id="items" class="hidden">
<ul>
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
	
	
	 <li><a href="#">Leave Type</a>
            	<ul>
               		<li><a href="leaveType.htm">Add</a></li>
					<li><a href="viewleaveType.htm">View</a></li>
                </ul>
     </li>
     
     <li><a href="#">Leave Allocation</a>
            	<ul>
               		<!-- <li><a href="leaveAllocation.htm">Add</a></li> -->
               		<!-- <li><a href="viewleaveApplication.htm">View Pending Leaves</a></li> -->
               		<li><a href="AllocateLeave.htm">Allocate Pending Leaves</a></li>
					<li><a href="viewleaveAllocation.htm">View Alocated Leaves</a></li>
                </ul>
    </li>
    
    <!-- <li><a href="#">Leave Application</a>
            	<ul>
               		<li><a href="leaveApplication.htm">Add</a></li>
					<li><a href="viewleaveApplication.htm">View</a></li>
                </ul>
    </li> -->
</ul>
</div>
</c:if>
<c:if test="${calendarFlag eq true}" >
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="shifts"><span class="ui-icon ui-icon-triangle-1-s"></span>Calendar</a>
<div id="news-items" class="hidden">
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
				<!-- <li><a href="createShiftDefinition.htm">Create Shift Definition</a></li> -->
				<li><a href="showMonthlyCalendar.htm">Monthly Calendar</a></li>				
				</ul>
			</li>
			
			<li><a href="#">Holiday</a>
				<ul>
				<li><a href="showaddholiday.htm">Add</a></li>
				<li><a href="showviewholiday.htm">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
     <li><a href="#">------------------</a>
            	<ul>
               		<li><a href="showAddDivision.htm">Add</a></li>
					<li><a href="showViewDivision.htm">View</a></li>
                </ul>
   	 </li>
   	 </ul>
			
</div>
</c:if>
<c:if test="${reportsFlag eq true}">
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="reports"><span class="ui-icon ui-icon-triangle-1-s"></span>Reports</a>
<div id="news-items" class="hidden">
<ul>
<li><a href="#">Reports</a>
				<ul>
				<li><a href="">Employee Wise Report</a>
					<ul>
					<li><a href="showEmployeeWiseReport.htm">Employee Wise Attendance Report</a></li>
               		<li><a href="showEmployeeWiseExceptionReport.htm">Exception Report</a></li>
               		</ul>
				</li>
				<li><a href="showdepartmentwisereport.htm">Department Wise Report</a></li>
<li><a href="">Shiftwise Daily Report</a>
				<ul>
               		<li><a href="shiftWiseDailyReport.htm?reportType=InOut">Check In/Out</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=EarlyComing">Early Coming</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=LateComing">Late Coming</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=EarlyGoing">Early Going</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=LateGoing">Late Going</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=Absent">Absent</a></li>
					<li><a href="shiftWiseDailyReport.htm?reportType=Present">Present</a></li>
					<li><a href="shiftWiseMusterReport.htm">Muster Report</a></li>
                </ul>
				</li>
				<li><a href="showdailyattendancereport.htm">Daily Attendance Report</a></li>
				<li><a href="#" onclick="window.open('showemployeedetailsreport.htm')">Employee Personal Details Report</a></li>
				<li><a href="monthlyAttendanceReport.htm">Monthly Attendance Report</a></li>
				</ul>
			</li>
			
			<li><a href="#">Leave Reports</a>
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
			</li>
			
			<!-- <li><a href="#">Dynammic List</a>
				<ul>
				<li><a href="appendStudentView.htm">Dynammic List</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li> -->
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			<li><a href="#">------------------</a>
				<ul>
				<li><a href="#">Add</a></li>
				<li><a href="#">View</a></li>
				</ul>
			</li>
			
			
			
   
   	 </ul>
</div>
</c:if>
<c:if test="${userDashboardFlag eq true}" >
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="userDashboard"><span class="ui-icon ui-icon-triangle-1-s"></span>UserDashboard</a>
<div id="news-items" class="hidden">
			<ul>
			<li><a href="showMyUserDashboard.htm">My UserDashBoard</a>
			</li>
	</ul>
</div>
</c:if>
<%-- <c:if test="${userDashboardFlag eq true}" >
<a tabindex="0" href="#items" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="clearAttRecords"><span class="ui-icon ui-icon-triangle-1-s"></span>Clear Att Records</a>
<div id="news-items" class="hidden">
			<ul>
			<li><a href="#" onclick="">Clear Att Records</a>
			</li>
	</ul>
</div>
</c:if> --%>
<div id="items" class="hidden">
<ul>	
	</ul>
</div>
</div>
</body>
</html>
