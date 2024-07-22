<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Projects List</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Project Name</td>
			<td>Employee Name</td>
			<td>Link</td>
			<td>Client</td>
			<td>Role</td>
			<td>Technology</td>
			<td>From Date</td>
			<td>To Date</td>		
		</tr>
		<c:forEach items="${employeeProjectList}" var="employeeProjectListVar" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${employeeProjectListVar.id}"/>"/></td>
			<td>${(index.index)+1}</td>
			<td>${employeeProjectListVar.projectName}</td>
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeProjectListVar.employeeId==employeeListVar.employeeId}">
			<td>${employeeListVar.firstName}  ${employeeListVar.lastName}</td>
			</c:if>
			</c:forEach>
			<td>${employeeProjectListVar.link}</td>
			<td>${employeeProjectListVar.client}</td>
			<td>${employeeProjectListVar.role}</td>
			<td>${employeeProjectListVar.technology}</td>
			<td>${employeeProjectListVar.fromDate}</td>
			<td>${employeeProjectListVar.toDate}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return updateDelete(${employeeProjectListSize},'deleteEmployeeProjects.htm')"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${employeeProjectListSize},'updateEmployeeProjects.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>