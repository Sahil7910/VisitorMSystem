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
<title>Visitor Log's Report</title>
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
<form:form class="wufoo leftLabel page" name="check" target="_blank">
<ul>
<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Select Report Type<span id="req_23" class="req">*</span></label>
<div>
<select id="reportType" name="reportType" class="field select medium" onchange="changeVisitorReportType(this.value);">
	<option value="0">Select</option>
	<option value="VisitorWise">Visitor Wise</option>
	<option value="EmployeeWise">Employee Wise</option>
</select>
</div>
</li>

<li id="visitorsLabelId" class="notranslate" style="display: none;"><label class="desc" id="title110" for="Field110">Select Visitor <span id="req_23" class="req">*</span></label>
<div>
	<select id="visitorsSelectId" name="visitorsSelectId" class="field select medium" style="display: none;">
		<option value="0">Select</option>
		<c:forEach items="${visitorsList}" var="visitorsListVar">
			<option value="${visitorsListVar.visitorId}">${visitorsListVar.visitorName}</option>		
		</c:forEach>
	</select>
</div>
</li>

<li id="employeesLabelId" class="notranslate" style="display: none;"><label class="desc" id="title110" for="Field110">Select Employee <span id="req_23" class="req">*</span></label>
<div>
	<select id="employeesSelectId" name="employeesSelectId"  class="field select medium" style="display: none;">
		<option value="0">Select</option>
		<c:forEach items="${employeeList}" var="employeeListVar">
			<option value="${employeeListVar.employeeId}">${employeeListVar.firstName} ${employeeListVar.lastName}</option>		
		</c:forEach>
	</select>
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">From Date<span id="req_23" class="req">*</span></label>
<div>
<input type="text" id="datefrom" name="datefrom" 
	value="<%=dateNow%>" readonly="readonly" class="field text medium"/>
	<img
	id="dobImageDateFrom" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'datefrom',-350,-270);"></img>
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">To Date<span id="req_23" class="req">*</span></label>
<div>
<input type="text" id="dateTo" name="dateTo" 
	value="<%=dateNow%>" readonly="readonly" class="field text medium"/>
	<img
	id="dobImageDateTo" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'dateTo',-350,-270);"></img>
</div>
</li>



<li id="foli1" class="notranslate"><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
<div>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="button" name="save" id="save" value="Generate" onclick="return generateVisitorReport('generatevisitorwithPhotoreport.htm');"/>
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