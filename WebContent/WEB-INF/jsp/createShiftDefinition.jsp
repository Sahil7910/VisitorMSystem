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
<title>Insert title here</title>
</head>
<body>
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
<div id="maincontent">
<form:form name="ShiftDefinition" commandName="shiftDefinition" method="POST" action="saveShiftDefinition.htm">
<table>
<tr>
<td align="left">Shift</td>
<td align="left"><form:select path="shiftid">
		<form:option value="0" label="Select" />
		<c:forEach items="${shiftMasterList}" var="shiftMasterList" varStatus="status">
		<form:option value="${shiftMasterList.shiftid}" label="${shiftMasterList.shiftname}" />
		</c:forEach>
	</form:select></td>
</tr>
<tr>
<td align="left">Location</td>
<td align="left"><form:select path="location_id">
		<form:option value="0" label="Select" />
		<c:forEach items="${locationList}" var="locationList" varStatus="status">
		<form:option value="${locationList.id}" label="${locationList.location}" />
		</c:forEach>
	</form:select></td>
</tr>
<tr>
<td align="left">Category</td>
<td align="left"><form:select path="category">
		<form:option value="0" label="Select" />
		<c:forEach items="${categoryList}" var="categoryList" varStatus="status">
		<form:option value="${categoryList.id}" label="${categoryList.category}" />
		</c:forEach>
	</form:select></td>
</tr>
<tr>
<td align="left">Description</td>
<td align="left"><form:textarea path="description" rows="4"/></td>
</tr>
<tr>
<td align="left">Start Date</td>
<td align="left"><form:input type="text" id="datefrom" name="datefrom" path="dateField"
	value="<%=dateNow%>" readonly="true" size="15"/> </td>
	<td align="left">
	<div id="DateFromDiv">
	<img
	id="dobImageDateFrom" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'datefrom',-5,-130);"></img>
	</div>
	</td>
</tr>

<tr>
<td align="left">End Date</td>
<td align="left"><form:input type="text" id="dateTo" name="dateTo" path="endDate"
	value="<%=dateNow%>" readonly="true" size="15"/> </td>
	<td align="left"><div id="DateToDiv">
	<img
	id="dobImageDateTo" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'dateTo',-5,-130);"></img>
	</div>
	</td>
</tr>


<tr>
<td align="left">Start Time</td>
<td align="left"><form:select path="timeField">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.id}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select></td>
</tr>
<tr>

<td align="left">End Time</td>
<td align="left"><form:select path="endTime">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.id}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select></td>
</tr>

<%-- <tr>
<td align="left">Full Day Event</td>
<td align="left"><form:checkbox path="dayEvent" onclick="disableStartAndEndTime(this.checked,'datefrom','dateTo');"/></td>
</tr>

<tr>
<td align="left">Recurrence</td>
<td align="left"><form:checkbox path="recurrence" onclick="showRecurrenceDiv(this.checked);"/>	
</td>
</tr>


<tr>
<td align="left">
<div id="recurrenceDivName" style="display: none">
Recurrence Period(From Start Date)
</div></td>
<td align="left">
<div id="recurrenceDiv" style="display: none">
<form:select path="period">
		<form:option value="0" label="Select" />
		<form:option value="Day" label="Day" />
		<form:option value="Weak" label="Weak" />
		<form:option value="Month" label="Month" />
		<form:option value="Year" label="Year" />
	</form:select>
</div>
	</td>
</tr>


<tr>
<td align="left">Overnight</td>
<td align="left"><form:checkbox path="overnight" />	</td>
</tr> --%>
<tr><td align="left"><input type="submit" value="Save"/></td></tr>
</table>
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