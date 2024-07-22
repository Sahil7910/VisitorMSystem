<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Approved Visitors</title>
</head>
<body>
<div id=maincontent>
<form:form name="approvedVisitors" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
<table>
<tr>
			<td>Visitor Name</td>
			<td>Employee Name</td>
			<td>Employee Phone</td>
			<td>Vehicle Number</td>
			<td>Date</td>
			<td>Status</td>
</tr>
<c:forEach items="${visitorLogsList}" var="visitorLogsListVar">
<tr>
<td>
<c:forEach items="${visitorList}" var="visitorListVar">
<c:if test="${visitorLogsListVar.visitorId==visitorListVar.visitorId}">
${visitorListVar.visitorName}
</c:if>
</c:forEach>
</td>
<td>
<c:forEach items="${employeeList}" var="employeeListVar">
<c:if test="${visitorLogsListVar.employeeId==employeeListVar.employeeId}">
${employeeListVar.firstName} ${employeeListVar.lastName}
</c:if>
</c:forEach>
</td>
<td>
<c:forEach items="${employeeList}" var="employeeListVar">
<c:if test="${visitorLogsListVar.employeeId==employeeListVar.employeeId}">
${employeeListVar.mobile}
</c:if>
</c:forEach>
</td>
<td>${visitorLogsListVar.vehicleNo}</td>
<td>${fn:split(visitorLogsListVar.inTime,' ')[0]}</td>
<td><c:choose>
<c:when test="${visitorLogsListVar.approvalStatus=='approved'}">
<input type="button" value="Allow" onclick="genericSubmitForRequiredUrl('changeApprovedVisitorStatus.htm?visitorLogId=${visitorLogsListVar.logId}','POST');"/>
</c:when>
<c:otherwise>
${visitorLogsListVar.approvalStatus}
</c:otherwise>
</c:choose>
</td>
</tr>
</c:forEach>
</table>
</div>
</div>
</form:form>
</div>
</body>
</html>