<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle Log's Report</title>
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
<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="generatevehiclelogswophotoreport.htm" method="POST" commandName="vehicleLogs" target="_blank">
<ul>
<!-- <li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle<span id="req_23" class="req">*</span></label>
<div>
<select id="reportType" class="field select medium">
<option value="0">Select</option>
<option value="EmployeeWise">EmployeeWise</option>
<option value="VisitorWise">VisitorWise</option>
</select>
</div>
</li> -->


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
From Date
<span id="req_23" class="req">*</span>
</label>
<div>
<input type="text" id="datefrom" name="datefrom" 
	value="<%=dateNow%>" readonly="readonly" class="field text medium"/>
	<img
	id="dobImageDateFrom" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'datefrom',-350,-270);"></img>
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
To Date
<span id="req_23" class="req">*</span>
</label>
<div>
<input type="text" id="dateTo" name="dateTo" 
	value="<%=dateNow%>" readonly="readonly" class="field text medium"/>
	<img
	id="dobImageDateTo" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'dateTo',-350,-270);"></img>
</div>
</li>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Concern Person</label>
<div>
<form:select path="concernPerson" id="concernPerson" name="concernPerson" class="field select medium">
<form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
    <form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:forEach>
</form:select>
</div>
</li>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Status</label>
<div>
<form:select path="status" id="status" name="status" class="field select medium">
<form:option value="0">Select</form:option>
<form:option value="Arrived" label="Arrived">Arrived</form:option>
<form:option value="Not Arrived" label="Not Arrived">Not Arrived</form:option>
<form:option value="Arrived & Left" label="Arrived & Left">Arrived & Left</form:option>
</form:select>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Purpose of Entry</label>
<div>
	<form:input path="purpose" id="purpose" name="purpose" class="field text medium"/>
	<form:errors path="purpose" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Filter</label>
<div>
	<input type="checkbox" id="filter" name="filter" class="field checkbox medium" onchange="filterCheckBox(this.checked,'showFilter');"/>
</div>
</li>


<div id="showFilter" style="display: none;">
<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Number</label>
<div>
<select id="vehicleNumber" name="vehicleNumber" class="field select medium">
<option value="0">Select</option>
<c:forEach items="${vehicleList}" var="vehicleListNumberVar">
<option value="${vehicleListNumberVar.vehicleNumber}">${vehicleListNumberVar.vehicleNumber}</option>
</c:forEach>
</select>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Type</label>
<div>
<select id="vehicleType" name="vehicleType" class="field select medium">
<option value="0">Select</option>
<c:forEach items="${uniqueVehicleTypeList}" var="vehicleListTypeVar">
<option value="${vehicleListTypeVar}">${vehicleListTypeVar}</option>
</c:forEach>
</select>
</div>
</li>

</div>


<li id="foli1" class="notranslate"><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
<div>
</div>
</li>


<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="generate" id="generate" value="Generate"/>
</div>
</li>


</ul>
<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="left" align="center" colSpan="4"><SELECT class="form"
						id="selectMonth"
						onChange="showCalenderBody(createCalender(document.getElementById('selectYear').value,&#13;&#10;&#9;this.selectedIndex, false));"
						name='select'>
							<OPTION value=0 selected>Jan</OPTION>
							<OPTION value=1>Feb</OPTION>
							<OPTION value=2>Mar</OPTION>
							<OPTION value=3>Apr</OPTION>
							<OPTION value=4>May</OPTION>
							<OPTION value=5>Jun</OPTION>
							<OPTION value=6>Jul</OPTION>
							<OPTION value=7>Aug</OPTION>
							<OPTION value=8>Sep</OPTION>
							<OPTION value=9>Oct</OPTION>
							<OPTION value=10>Nov</OPTION>
							<OPTION value=11>Dec</OPTION>
					</SELECT></td>
					<td align="left" align="center" colSpan=2><SELECT id=selectYear
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="left" align="center"><a onclick="closeCalender();"href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
				</tr>
			</TBODY>
			<tbody id=calenderTableDays>
				<tr>
					<td align="left">Sun</TD>
					<td align="left">Mon</TD>
					<td align="left">Tue</TD>
					<td align="left">Wed</TD>
					<td align="left">Thu</TD>
					<td align="left">Fri</TD>
					<td align="left">Sat</TD>
				</tr>
			</tbody>
			<tbody id="calender" style="color: black;">
				<tr>
					<td align="left"></td>
				</tr>
			</tbody>
		</TABLE>
</form:form>
</div>
</body>
</html>