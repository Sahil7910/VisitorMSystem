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
<title>Company</title>

<script type="text/javascript" src="javascript/department.js"></script>
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
			<td>Name</td>
			<td>Division</td>
			<td>Head</td>
			<td>Email</td>
		</tr>
		<c:forEach items="${departmentList}" var="department" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${department.id}"/>"/></td>
			<td>${department.id}</td>
			<td>${department.name}</td>
			<td>
			<c:forEach items="${divisionList}" var="divisionListVar">
			<c:if test="${divisionListVar.divisionId==department.division}">
			${divisionListVar.divisionName} 
			</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==department.head}">
			${employeeListVar.firstName} ${employeeListVar.lastName}
			</c:if>
			</c:forEach>
			</td>
			<td><font color="black">${department.email}</font></td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td align="center"><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return deleteDepartment(${departmentListSize},'deleteDepartment.htm','POST');"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return deleteDepartment(${departmentListSize},'showupdateDepartment.htm','POST');"/></td>
	</tr>
</table>
</form:form>
</div>
</body>
</html>