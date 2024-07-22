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
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Name</td>
			<td>Privileges</td>
			
		</tr>
	<c:forEach items="${employeePrivilegesList}" var="employeePrivilegesListVar" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${employeePrivilegesListVar.id}"/>"/></td>
			<td >${(index.index)+1}</td>
			
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeePrivilegesListVar.employeeId==employeeListVar.employeeId}">
			<td >${employeeListVar.firstName} ${employeeListVar.lastName}</td>
			</c:if>
			</c:forEach>			
			<td>
			<c:forEach var="userPrivilegeListVar" items="${userPrivilegeList}">
			<c:set var="employeePrivilegesArray" value="${fn:split(employeePrivilegesListVar.privilege,',')}"></c:set>
			<c:forEach var="item" items="${employeePrivilegesArray}" varStatus="privilegeIndex">
			<c:if test="${userPrivilegeListVar.id==item}">
			${userPrivilegeListVar.privilege}
				<c:if test="${privilegeIndex.index+1<fn:length(employeePrivilegesArray)}">,</c:if>
			</c:if>
			</c:forEach>
			</c:forEach>
			</td>
		 </tr>
	</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${employeePrivilegesListSize},'deletePrivilegesDetails.htm')"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${employeePrivilegesListSize},'showUpdatePrivilegesDetails.htm');"/></td>
	</tr>
</table>
</form:form>
</div>
</body>
</html>