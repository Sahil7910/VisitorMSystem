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
<title>Company List</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr  >
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Role Name</td>
			<td>Description</td>
			<!-- <th style="color: white; ">Parent Role</th> -->
			<td>Created On</td>
			<td>Level</td>
		</tr>
		<c:forEach items="${jobRolesList}" var="jobRolesListVar" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${jobRolesListVar.id}"/>"/></td>
			<td >${(index.index)+1}</td>
			<td >${jobRolesListVar.roleName}</td>
			<td >${jobRolesListVar.description}</td>
			<td >${jobRolesListVar.createdOn}</td>
			<td >${jobRolesListVar.level}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return updateDelete(${jobRolesListSize},'deleteJobRoles.htm')"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${jobRolesListSize},'updateJobRoles.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>