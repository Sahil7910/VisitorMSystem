<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!-- <script type="text/javascript" src="calendar/js/calendar.js"></script>
<link rel="stylesheet" href="calendar/css/calendar.css" type="text/css">
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Report</title>
</head>
<%
	String dateFormat = "dd-MM-yyyy";
	String dateNow = DateTime.CurrentDate(dateFormat);
	Calendar c_month = Calendar.getInstance();
	Calendar c_week = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	int month = Calendar.MONTH;
	int day = Calendar.DATE;
	c_month.add(Calendar.MONTH, -1);
	c_week.add(Calendar.DATE, -7);
	String lastMonthDate = sdf.format(c_month.getTime());
	String lastWeekDate = sdf.format(c_week.getTime());
	String year = dateNow.substring(7, 10);
	String month1 = dateNow.substring(3, 5);
	int iYear = Integer.parseInt(year);
	String loginUser=(String)session.getAttribute("loginUser");
%>
<body id="public">
<div id="container" class="ltr">
<form:form name="EmployeeWiseReport" class="wufoo leftLabel page"  id="EmployeeWiseReport" target="_blank">
<input type="hidden" id="employeeReportType"/>

<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Select Department
<span id="req_23" class="req">*</span>
</label>
<div>
<select class="field select medium" name="department" id="department" onchange="genericAjaxFunction('displaydepartmentwiseemployeelist.htm?departmentId='+this.value,'POST','employeeListDiv')">
		<option value="0" label="Select">Select</option>
		<c:forEach items="${departmentList}" var="departmentList" varStatus="status">
		<option value="${departmentList.id}" label="${departmentList.name}" >${departmentList.name}</option>
		</c:forEach>
	</select>
</div>
</li>
<div id="employeeListDiv"></div> 


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Select Leave Type<span id="req_23" class="req">*</span></label>
<div>
<select class="field select medium" name="leaveType" id="leaveType">
		<option value="0" label="Select">Select</option>
		<c:forEach items="${leaveTypeList}" var="leaveTypeListVar" varStatus="status">
		<option value="${leaveTypeListVar.id}" label="${leaveTypeListVar.name}" >${leaveTypeListVar.name}</option>
		</c:forEach>
	</select>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
</label>
<div>
<label class="mandatory" id="title110" for="Field110">
${ErrorMsg}
</label>
</div>
</li>




<li class="buttons ">
<div>
<input class="btTxt submit" type="button" value="Show" onclick="return generateReportAjax('showEmployeeLeaveStatusDiv.htm?','POST','showLeaveStatus');"/>
</div>
</li>

</ul>
</form:form>
<div id="showLeaveStatus">

</div>

</div>
</body>
</html>