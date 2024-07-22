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
<title>Designation</title>

<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="GET" action="updateAttendanceDevice.htm">
<c:choose>
<c:when test="${connectionStatus==0 and deviceSerial==0}">
<div style="height:400px; width:99%; overflow-y:scroll">
	<table width="100%">
		<tr style="background-color: #333" >
			<th width="50px" style="color: white;">&nbsp;</th>
			<th style="color: white; ">Enrollment No</th>
			<th style="color: white; ">Employee Name</th>
		</tr>
		<c:forEach items="${userInfoList}" var="userInfoListVar" varStatus="index">
		<tr>
			<td align="center"><input type="radio" name="radionm" id="radioid" value="<c:out value="${userInfoListVar.enrollmentNo}"/>"/></td>
			<td align="center">${userInfoListVar.enrollmentNo}</td>
			<td align="center">${userInfoListVar.emp_name}</td>
			
		 </tr>
		</c:forEach>
	</table>
</div>
</c:when>
<c:otherwise>
<c:if test="${connectionStatus==0}">
<div style="width: 20%;" align="center">
${deviceSerial}
</div>
</c:if>
<c:if test="${deviceSerial==0}">
<div style="width: 20%;" align="center">
${connectionStatus}
</div>
</c:if>
</c:otherwise>
</c:choose>
<table align="center">
	<tr>
	<c:if test="${connectionStatus==0 and deviceSerial==0}">
		<td><input type="button" class=button name="method" value="DELETE" onclick="return deleteUserDeviceManagement(${userInfoListSize},'deleteUserFromDiv.htm?enterIP=${enterIP}','POST');"/></td>
	</c:if>
		<td><input type="submit" class=button name="method" value="CLOSE"/></td>
	</tr>
</table>
</form:form>
</div>
</body>
</html>