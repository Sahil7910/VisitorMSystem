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
<form:form name="check" method="GET">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Name</td>
			<td>Description</td>
			<td>Rank</td>
			
			
		</tr>
		<c:forEach items="${leaveTypeList}" var="leaveType" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${leaveType.id}"/>"/></td>
			<td>${leaveType.id}</td>
			<td>${leaveType.name}</td>
			<td>${leaveType.description}</td>
			<td>${leaveType.rank}</td>
			
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" class=button name="method" value="DELETE" onclick="return deleteDepartment(${leaveTypeListSize},'deleteLeaveType.htm','POST');"/>
		<input type="button" class=button name="method" value="UPDATE" onclick="return deleteDepartment(${leaveTypeListSize},'showupdateLeaveType.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>