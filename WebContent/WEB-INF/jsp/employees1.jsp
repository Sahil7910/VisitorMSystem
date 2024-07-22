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

<head>
<title>Add Employee</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>

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
String loginUser = (String) session.getAttribute("loginUser");
%>

<body id="public">
	<div id="container" class="ltr">


		<script language="JavaScript" src="include/jquery.js"></script>
		<script language="JavaScript" src="include/jsfunctions.js"></script>


		<form:form class="wufoo leftLabel page" encType="multipart/form-data"
			method="POST" action="addEmployees.htm" commandName="employee">

			<div style="width: 100%; background-color: black; color: red;"
				align="center">
				<font size="4">${EmployeeAddMsg}</font>
			</div>

			<ul>




				<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> Bank Name<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" path="firstName"
						id="firstName" name="firstName" />
				</div> <form:errors path="firstName" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23">bankBranchName<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" path="lastName" id="lastName"
						name="lastName" />
				</div> <form:errors path="lastName" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> branchFullAddress<span id="req_23"
					class="req">*</span>
			</label>
			
							<div>
					<form:textarea class="field textarea medium" rows="8" cols="40"
						path="introduction" id="introduction" name="introduction" />
				</div></li>
			
				

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> accountNo<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" path="firstName"
						id="firstName" name="firstName" />
				</div> <form:errors path="firstName" cssClass="error" /></li>
				
				

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23">IFSC CODE<span id="req_23"
					class="req">*</span>
			</label>
					<div>
					<form:input class="field text medium" path="firstName"
						id="firstName" name="firstName" />
				</div> <form:errors path="firstName" cssClass="error" /></li>
							
	





				
				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Created By</label>
					<div>
						<label class="desc" id="title23" for="Field23"><%=loginUser%></label>
					</div> <form:hidden path="createdBy" value="<%=loginUser%>" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Created On</label>
					<div>
						<label class="desc" id="title23" for="Field23"><%=dateNow%></label>
					</div> <form:hidden path="createdOn" value="<%=dateNow%>" /></li>

			</ul>



<SPAN class=buttonborder><INPUT class="btTxt submit"	value="Save" type="submit"></SPAN>
		</form:form>

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
					<td align="center" colSpan=2><SELECT id=selectYear
						style="width: 55px;"
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();"
						href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
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
	</div>
</body>
</html>

