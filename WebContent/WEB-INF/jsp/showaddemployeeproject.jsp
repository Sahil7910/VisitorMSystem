<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head><title></title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>
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
<form:form encType="multipart/form-data" class="wufoo leftLabel page" method="POST" action="saveOrUpdateEmployeeProject.htm" commandName="projects">
<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Employee
<span id="req_23" class="req">*</span><br><form:errors path="employeeId" cssClass="error"/>
</label>
<div>
<form:select path="employeeId" id="employeeId" name="employeeId" class="field select medium">
    <form:option value="0">Select</form:option>
    <c:forEach items="${employeeList}" var="employeeListVar">
    <form:option value="${employeeListVar.employeeId}" label="${employeeListVar.firstName} ${employeeListVar.lastName}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
    </c:forEach>
</form:select>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Project Name
<span id="req_23" class="req">*</span>
</label>
<div>
 <form:input path="projectName" id="projectName" name="projectName" class="field text medium"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
From Date
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="datefrom" name="datefrom" path="fromDate"
	value="<%=dateNow%>" readonly="true" class="field text medium"/>
	<img
	id="dobImageDateFrom" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'datefrom',-350,-270);"></img>
</div>
<form:errors path="fromDate" cssClass="error"/>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
To Date
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="dateTo" name="dateTo" path="toDate"
	value="<%=dateNow%>" readonly="true" class="field text medium"/>
	<img
	id="dobImageDateTo" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'dateTo',-350,-270);"></img>
</div>
<form:errors path="toDate" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Link
<span id="req_23" class="req">*</span>
</label>
<div>
 <form:input path="link" id="link" name="link" class="field text medium"/>
</div>
<form:errors path="link" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Client
<span id="req_23" class="req">*</span>
</label>
<div>
  <form:input path="client" id="client" name="client" class="field text medium"/>
</div>
<form:errors path="client" cssClass="error"/>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Team Size
</label>
<div>
 <form:input path="teamSize" id="teamSize" name="teamSize" class="field text medium"/>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Role
<span id="req_23" class="req">*</span>
</label>
<div>
    <form:input path="role" id="role" name="role" class="field text medium"/>
</div>
<form:errors path="role" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Technology
<span id="req_23" class="req">*</span>
</label>
<div>
    <form:textarea path="technology" id="technology" name="technology" class="field textarea medium" rows="10" cols="50"/>
</div>
<form:errors path="technology" cssClass="error"/>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Project Details
</label>
<div>
 <form:textarea path="projectDetails" id="projectDetails" name="projectDetails" class="field textarea medium" rows="10" cols="50"/>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Created By
</label>
<div>
<%=loginUser%>
 <form:hidden path="createdBy" value="<%=loginUser%>"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Created On
<span id="req_23" class="req">*</span>
</label>
<div>
<%=dateNow%>
<form:hidden path="createdOn" value="<%=dateNow%>"/>
</div>
</li>


<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
<span id="req_23" class="req">*-Required Fields</span>
</label>
<div>
</div>
</li>


<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="save" id="save" value="Save"/>
</div>
</li>
</ul>
   
<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="center" colSpan="4"><SELECT class="form"
						id="selectMonth"
						onChange="showCalenderBody(createCalender(document.getElementById('selectYear').value,&#13;&#10;&#9;this.selectedIndex, false));"
						name='select' style="width: 90px;">
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
					<td align="center" colSpan=2><SELECT id=selectYear style="width: 55px;"
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();" href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
				</tr>
			</TBODY>
			<tbody id=calenderTableDays>
				<tr>
					<TD>Sun</TD>
					<TD>Mon</TD>
					<TD>Tue</TD>
					<TD>Wed</TD>
					<TD>Thu</TD>
					<TD>Fri</TD>
					<TD>Sat</TD>
				</tr>
			</tbody>
			<tbody id="calender" style="color: black;">
				<tr>
					<td></td>
				</tr>
			</tbody>
		</TABLE>
     
</form:form>
</div>
</body>
</html>

