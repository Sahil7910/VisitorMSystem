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
			<td>Workspace Name</td>
			<td>Description</td>
			<td>Department</td>
			<td>Designation</td>
			<td>Division</td>
		</tr>
	<c:forEach items="${workspaceList}" var="workspaceListVar" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${workspaceListVar.id}"/>"/></td>
			<td >${(index.index)+1}</td>
			<td >${workspaceListVar.workspaceName}</td>
			<td >${workspaceListVar.description}</td>
			<td >
			<c:forEach items="${departmentList}" var="departmentListVar">
			<c:if test="${departmentListVar.id==workspaceListVar.department}" >
			${departmentListVar.name}
			</c:if>
			</c:forEach>
			</td>
			<td >
			<c:forEach items="${designationList}" var="designationListVar">
			<c:if test="${designationListVar.id==workspaceListVar.designation}" >
			${designationListVar.designation}
			</c:if>
			</c:forEach></td>
			<td>
			<c:forEach items="${divisionList}" var="divisionListVar">
			<c:if test="${divisionListVar.divisionId==workspaceListVar.division}">
			${divisionListVar.divisionName} 
			</c:if>
			</c:forEach>
			</td>
		 </tr>
	</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return updateDelete(${workspaceListSize},'deleteWorkspace.htm')"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${workspaceListSize},'updateWorkspace.htm');"/></td>
	</tr>
</table>
</form:form>
</div>
</body>
</html>