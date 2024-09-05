<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Vehicle Logs</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Vehicle No.</td>
			<td>Concerned Person</td>
			<td>Status</td>
			<td>In Time</td>
			<td>Out Time</td>
			<td>Purpose</td>
			
		</tr>
		<c:forEach items="${vehicleLogsList}" var="vehicleLogsList">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${vehicleLogsList.logId}"/>"/></td>
			<c:forEach items="${vehicledetailsList}" var="vehicledetailsList">
			<c:if test="${vehicledetailsList.vehicleId==vehicleLogsList.vehicleId}">
			<td>${vehicledetailsList.vehicleNumber}</td>
			</c:if>
			</c:forEach>
			<c:forEach items="${employeeList}" var="employeeList">
			<c:if test="${employeeList.employeeId==vehicleLogsList.concernPerson}">
			<td>${employeeList.firstName} ${employeeList.lastName}</td>
			</c:if>
			</c:forEach>
			<td>${vehicleLogsList.status}</td>
			<td>${vehicleLogsList.inTime}</td>
			<td>${vehicleLogsList.outTime}</td>
			<td>${vehicleLogsList.purpose}</td>
		
		</tr>
		</c:forEach>
	</table>
</div></div>

<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${vehicleLogsListSize},'showupdatevehiclelogs.htm');"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${vehicleLogsListSize},'deletevehiclelogs.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>