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
			<td>Emp Id</td>
			<td>Name</td>
			<td>subject</td>
			<td>Description</td>
			<td>Approved Start</td>
			<td>Approved End</td>
			<td>Status</td>
			
		</tr>
		<c:forEach items="${outForWorksApprovedForCurrentEmployee}" var="outForWorksApprovedForCurrentEmployeeVar" varStatus="index">
		<tr>
			<%-- <td align="center" width="50px"><input type="radio" name="radionm" id="radioid" value="<c:out value="${outForWorksPendingForCurrentEmployeeVar.id}"/>"/></td> --%>
			<td>${outForWorksApprovedForCurrentEmployeeVar.id} </td>
			<td>
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==outForWorksApprovedForCurrentEmployeeVar.employeeId}" >
			${employeeListVar.firstName} ${employeeListVar.lastName}
			</c:if>
			</c:forEach>
			 </td>
			<td>${outForWorksApprovedForCurrentEmployeeVar.subject}</td>
			<td>${outForWorksApprovedForCurrentEmployeeVar.description}</td>
			<td>${outForWorksApprovedForCurrentEmployeeVar.approvedFromDate}</td>
			<td>${outForWorksApprovedForCurrentEmployeeVar.approvedToDate}</td>
			 <td>${outForWorksApprovedForCurrentEmployeeVar.status}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
</form:form>
</div>
</body>
</html>