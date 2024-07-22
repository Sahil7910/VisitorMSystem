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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
<title>Leave Application</title>
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
	String loginUser = (String) session.getAttribute("loginUser");
	%>

	<%-- <form:form commandName="leaveApplication" action="addLeaveApplication.htm" method="POST" > --%>
	<form:form id="leaveForm" commandName="leaveApplication"
		action="addLeaveApplication.htm" method="POST">

		<div id=maincontent>
			<font style="color: red;">Note: An email will be sent to the
				following employees for the approval of leave.</font> <br> <br>
			<table border="0">

				<TBODY>
					<TR>
						<TD width="33%" align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Department</TD>
						<TD width="33%" align="left"><form:select
								path="department_id" id="value_department_id"
								style="width: 145px;"
								onchange="genericAjaxFunction('getdepartmentwisesingleemployeelist.htm?departmentId='+this.value,'POST','employeeListDiv')">
								<form:option value="0">--Select--</form:option>
								<c:forEach items="${departmentList}" var="department">
									<form:option value="${department.id}">${department.name}</form:option>
								</c:forEach>
							</form:select></TD>
						<td width="33%"><form:errors path="department_id"
								cssStyle="color : red;" /></td>
					</TR>


					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Employee</TD>
						<td align="left" colspan="2">
							<div id="employeeListDiv"></div>
						</td>
						<td><form:errors path="employee_id" cssStyle="color : red;" /></td>
					</TR>
					<%
					int employeeNo = (Integer) session.getAttribute("loginUserNo");
					System.out.println(employeeNo);
					%>

					<TR>
						<td><input type="hidden" id="deptEmployee"
							name="deptEmployee" value="<%=employeeNo%>"></td>
						<%-- <td><form:errors path="employee_id" cssStyle="color : red;"/></td> --%>
					</TR>

					<tr>
						<td><input type="hidden" id="employeeReportType" />
						<form:hidden path="employeeType" width="150px;" id="employeeType" />
						</td>
						<td><form:hidden path="multipleEmployeeIds" width="150px;"
								id="multipleSelectedEmployees" /></td>
						<td><form:hidden path="singleEmployeeId"
								id="singleSelectedEmployee" /></td>
					</tr>

					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>From Date</TD>
						<td align="left"><form:input type="text" id="fromdate"
								name="fromdate" path="from_date" value="<%=dateNow%>"
								readonly="true" size="15" /> &nbsp;&nbsp;&nbsp;&nbsp;<img
							id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1990, 2050);showCalender(this, 'fromdate',-5,-130);"></img></td>
						<td colspan="1" width=150 align="left">
							<%-- <form:checkbox path="half_day_session" label="Half Day" value="1"></form:checkbox> --%>
							<input type="checkbox" id="halfDay"
							onchange="javascript:displaySessionDiv();" />&nbsp;Half Day
						</td>
						<%-- <td><form:errors path="from_date" cssStyle="color : red;"/></td> --%>
					</TR>

					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>To Date</TD>
						<td align="left"><form:input type="text" id="todate"
								name="todate" path="to_date" value="<%=dateNow%>"
								readonly="true" size="15" /> &nbsp;&nbsp;&nbsp;&nbsp;<img
							id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1990, 2050);showCalender(this, 'todate',-5,-130);"></img></td>
						<%-- <td><form:errors path="to_date" cssStyle="color : red;"/></td> --%>
					</TR>


					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Duration(in Days excl.
							Holidays, 0.5=Half Day)</TD>
						<TD align="left"><form:input name="value_duration"
								path="duration" id="value_duration"></form:input></TD>
						<%-- <td><form:errors path="duration" cssStyle="color : red;"/></td> --%>
					</TR>


					<%-- <TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Priority</TD>
						<TD align="left"><form:select path="priority"
								id="value_priority" style="width: 145px;">
								<form:option value="0">--Select--</form:option>
								<c:forEach items="${priorities}" var="priority">
									<form:option value="${priority.id}">${priority.name}</form:option>
								</c:forEach>
							</form:select></TD>
						<td><form:errors path="priority" cssStyle="color : red;"/></td>
					</TR> --%>


					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Leave Type</TD>
						<TD align="left"><form:select path="leavetype" id="leavetype"
								style="width: 150px;">
								<form:option value="0">--Select--</form:option>
								<c:forEach items="${leavetypeList}" var="leavetypevar">
									<form:option value="${leavetypevar.id}">${leavetypevar.name}</form:option>
								</c:forEach>
							</form:select></TD>
						<%-- <td><form:errors path="leavetype" cssStyle="color : red;"/></td> --%>
					</TR>

					<TR>
						<td colspan="3" align="left"><div id="half_day_sessionDiv"></div>
						</td>
					</TR>

					<TR>
						<TD align="left"><font color="red" size="4"
							style="font-weight: bolder;">*</font>Subject</TD>
						<TD align="left"><form:input name="value_subject"
								id="value_subject" path="subject"></form:input></TD>
						<%--   <td><form:errors path="subject" cssStyle="color : red;"/></td> --%>
					</TR>

					<TR>
						<TD align="left">Message</TD>
						<TD align="left"><form:textarea path="description"
								name="value_description" style="width: 200px;height: 150px;"></form:textarea>
						</TD>
					</TR>
					<TR>
					<tr>
						<td align="left">Created On</td>
						<td align="left"><%=dateNow%></td>
						<td><form:hidden path="created_on" value="<%=dateNow%>" /></td>
					</tr>

					<tr>
						<td align="left">Requested By</td>
						<td align="left"><%=loginUser%></td>
						<td><form:hidden path="requested_by" value="<%=loginUser%>" />
						</td>
					</tr>
			</table>

			<SPAN class="buttonborder"><INPUT class="button" value="Apply"
				type="submit" onclick="return validateLeaveApplication();"></SPAN>
			<!-- <SPAN class=buttonborder><INPUT class=button value=Reset type=reset ></SPAN> 
       -->
		</div>


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

	</form:form>
</body>
</html>
