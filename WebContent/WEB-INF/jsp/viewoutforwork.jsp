<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company</title>
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
</head>
<body>
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
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">

	<table>
		<tr>
		<!-- 	<th width="50px" style="color: white;">&nbsp;</th> -->
			<td>Id</td>
			<td>Name</td>
			<td>subject</td>
			<td>Description</td>
			<td>Applied Date</td>
			<td>Approved Start</td>
			<td>Approved End</td>
			<td>Status</td>
			
		</tr>
		<c:forEach items="${outForWorksPendingForCurrentEmployee}" var="outForWorksPendingForCurrentEmployeeVar" varStatus="index">
		<tr>
			<%-- <td align="center" width="50px"><input type="radio" name="radionm" id="radioid" value="<c:out value="${outForWorksPendingForCurrentEmployeeVar.id}"/>"/></td> --%>
			<td>${outForWorksPendingForCurrentEmployeeVar.id} </td>
			<td>
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==outForWorksPendingForCurrentEmployeeVar.toEmployees}" >
			${employeeListVar.firstName} ${employeeListVar.lastName}
			</c:if>
			</c:forEach>
			 </td>
			 	<td>${outForWorksPendingForCurrentEmployeeVar.subject}</td>
			<td>${outForWorksPendingForCurrentEmployeeVar.description}</td>
			<td>${outForWorksPendingForCurrentEmployeeVar.appliedFromDate} to ${outForWorksPendingForCurrentEmployeeVar.appliedToDate}</td>
			<td>
					<input id="datefrom" name="datefrom" 
						value="${outForWorksPendingForCurrentEmployeeVar.appliedFromDate}" readonly="readonly" size="15"/> 
						<div id="DateFromDiv">
						<img
						id="dobImageDateFrom" src="images/cal.gif" alt="cal"
						onClick="setYears(1955, 2015);showCalender(this, 'datefrom',-5,-130);"></img>
						</div>
			 </td>
			<td>
			<input id="dateTo" name="dateTo" 
				value="${outForWorksPendingForCurrentEmployeeVar.appliedToDate}" readonly="readonly" size="15"/> 
				<div id="DateToDiv">
				<img
				id="dobImageDateTo" src="images/cal.gif" alt="cal"
				onClick="setYears(1955, 2015);showCalender(this, 'dateTo',-5,-130);"></img>
				</div>	
			 </td>
			 <td><a href="#" onclick="return updateShiftDefinition('updatePendingOutForWork.htm','${outForWorksPendingForCurrentEmployeeVar.id}');"><font style="color:#808080; font-style: italic;">${outForWorksPendingForCurrentEmployeeVar.status}</font></a></td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<%-- <table align="center" cellspacing="8" style="padding-left: 350px;"  >
	<tr>
		<td ><input type="button" class=button name="method" value="DELETE" onclick="return deleteDepartment(${employeeListSize},'deleteEmployee.htm','POST');"/></td>
		<td><input type="button" class=button name="method" value="UPDATE" onclick="return deleteDepartment(${employeeListSize},'showPersonalDetails.htm','POST');"/></td>
		
	</tr>
</table> --%>

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