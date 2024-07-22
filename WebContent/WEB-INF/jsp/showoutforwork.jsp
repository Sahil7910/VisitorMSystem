<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>




<%
DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy H:m:s");
String createdOn=dateFormat.format(Calendar.getInstance().getTime());
String messageFrom=session.getAttribute("loginUsername").toString();  
String messageFromId=session.getAttribute("loginUserId").toString();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>
<link href="css/table_4.css" rel="stylesheet" type="text/css" />


<title>Skills</title>
</head>
<%
	String dateFormatString = "dd-MM-yyyy";
	String dateNow = DateTime.CurrentDate(dateFormatString);
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



<body>
<form:form action="saveOutForWork.htm" commandName="outForWork" id="saveOutForWork">
<div class="UserTableGeneratorOutter">
<div class="UserTableGenerator">
<!-- <input type="hidden" id="employeeReportType"/> -->

<table align="center">
<tr>
<td><font class="mandatory">* To Employee</font></td>
<td>
<form:select multiple="true" path="toEmployees" style="margin-top:50px;">
<form:options itemValue="employeeId" itemLabel="firstName" items="${employeeList}"/>
<form:errors path="toEmployees" id="toEmployees" cssClass="error" ></form:errors>
</form:select>
</td>
</tr>
<tr>
<td>
<form:label path="employeeId"><%=messageFrom%></form:label>
<form:hidden path="employeeId" value="<%=messageFromId%>"/>
</td>
<td><form:errors path="employeeId" cssClass="error"></form:errors></td>
</tr>

<tr>
<td><font class="mandatory">*</font>Subject</td>
<td><form:input path="subject" name="subject" id="subject"/><form:errors path="subject" cssClass="error"></form:errors></td>


</tr>

<tr>
<td><font class="mandatory">*</font>description</td>
<td><form:textarea path="description" name="descriptionBody"/><form:errors path="description" cssClass="error"></form:errors></td>
</tr>

<tr>
<td >Start Date</td>
<td ><form:input path="appliedFromDate" id="datefrom" name="datefrom" 
	value="<%=dateNow%>" readonly="true" size="15"/> 
	<div id="DateFromDiv">
	<img
	id="dobImageDateFrom" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'datefrom',-5,-130);"></img>
	</div>
	</td>
</tr>

<tr>
<td >End Date</td>
<td ><form:input path="appliedToDate" id="dateTo" name="dateTo" 
	value="<%=dateNow%>" readonly="true" size="15"/>
	<div id="DateToDiv">
	<img
	id="dobImageDateTo" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'dateTo',-5,-130);"></img>
	</div>
	</td>
</tr>
<tr>
<td align="left"><label id="date"><%=createdOn%></label></td>
<td><form:hidden name="messageDate" id="messageDate" path="messageDate" value="<%=createdOn%>"></form:hidden></td>

</tr>


<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td class="mandatory">* Required fields</td>
</tr>
<tr>
<td><input type="submit" value="Send" onclick=" return validateOutOfWorkUserDashBoard();"/></td>
</tr>
</table>
</div>
</div>
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
</body>
</html>