<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <link href="css/table.css" rel="stylesheet" type="text/css" />
<link href="css/table_2.css" rel="stylesheet" type="text/css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visitors</title>
</head>
<body>

<c:if test="${visitorSearchErrorMessage!='pass'}">
<font class="error"> ${visitorSearchErrorMessage}</font>
</c:if>


<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter" style="height: 400px;">
<div class="CSSTableGenerator">
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>Visitor Name</td>
			<td>To meet</td>
			<td>Status</td>
			<td>In Time</td>
			<td>Out Time</td>
			<td>Vehicle No.</td>
		</tr>
		<c:forEach items="${visitorLogsList}" var="visitorLogsList">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${visitorLogsList.logId}"/>"/></td>
			<c:forEach items="${visitorsList}" var="visitorsList">
			<c:if test="${visitorsList.visitorId==visitorLogsList.visitorId}">
			<td>${visitorsList.visitorName}</td>
			</c:if>
			</c:forEach>
			<c:forEach items="${employeeList}" var="employeeList">
			<c:if test="${employeeList.employeeId==visitorLogsList.employeeId}">
			<td>${employeeList.firstName} ${employeeList.lastName}</td>
			</c:if>
			</c:forEach>
			<td>${visitorLogsList.status}</td>
			<td>${visitorLogsList.inTime}</td>
			<td><c:choose>
			<c:when test="${visitorLogsList.outTime=='SignOut'}">
			<a href="updateOutTimeVisitorLogs.htm?logId=${visitorLogsList.logId}"><font color="blue">${visitorLogsList.outTime}</font></a>
			</c:when>
			<c:otherwise>
			${visitorLogsList.outTime}
			</c:otherwise>
			</c:choose></td>
			<td>${visitorLogsList.vehicleNo}</td>
		</tr>
		</c:forEach>
	</table>
</div></div>

<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${visitorLogsListSize},'showupdatevisitorlogs.htm');"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${visitorLogsListSize},'deletevisitorlogs.htm');"/></td>
	</tr>
</table>
</form:form>
</body>
</html>