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


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>


<title>Holiday</title>
</head>
<body id="public">

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

<div id="container" class="ltr">
<form:form action="updateholiday.htm" method="POST" commandName="holidays" class="wufoo leftLabel page">
<form:hidden path="id" id="id" name="id" value="${holidays.id}"/> 
<%-- <table border="0" width="100%">
<tr>
<td></td>
<td align="left"><form:hidden path="id" id="id" name="id" value="${holidays.id}"/> </td>
<td></td>
<td></td>
</tr> --%>

<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Holiday Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="holidayName" id="holidayName" name="holidayName" class="field text medium" value="${holidays.holidayName}"/>
</div>
<form:errors path="holidayName" cssClass="error"/> 
</li>
<%-- <tr>
<td width="25%">Holiday Name</td>
<td width="25%" align="left"><form:input path="holidayName" id="holidayName" name="holidayName" value="${holidays.holidayName}"/> </td>
<td width="25%" align="left"> <form:errors path="holidayName" cssClass="error"/></td>
<td width="25%"></td>
</tr>


<tr>
<td>Holiday Date</td>
<td align="left"><form:input type="text" id="holidayDate" name="holidayDate" path="holidayDate"
	value="${holidays.holidayDate}" readonly="readonly" size="20"/> &nbsp;&nbsp;&nbsp;&nbsp; 
	<img id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'holidayDate',-5,-130);"></img>
    </td>
<td></td>
</tr>


<tr>
<td><input type="submit" name="save" id="save" value="Save"/></td>
</tr>
</table> --%>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Holiday Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="holidayDate" name="holidayDate" path="holidayDate"
	value="${holidays.holidayDate}" readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'holidayDate',-350,-270);"></img>
</div>
</li>
<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
</div>
</li>
	</ul>

<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="center" colSpan="4"><SELECT class="form"
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
					<td align="center" colSpan=2><SELECT id=selectYear
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();"href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
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