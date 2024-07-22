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
<title>Division</title>
</head>
<body>

<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr style="background-color: #333" >
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Division</td>
			<td>Code</td>
			<td>Description</td>
			<td>Division Head</td>
			<td>Location</td>
		</tr>
		<c:forEach items="${divisionList}" var="division" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${division.divisionId}"/>"/></td>
			<td >${(index.index)+1}</td>
			<td >${division.divisionName}</td>
			<td >${division.divisionCode}</td>
			<td >${division.divisionDescription}</td>
			<td >
			<c:forEach items="${employeeList}" var="employee">
			<c:if test="${division.divisionHead==employee.employeeId}">
				${employee.firstName}&nbsp;${employee.lastName}
			</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${locationsList}" var="locationsListVar">
			<c:if test="${division.locationId==locationsListVar.id}">
				${locationsListVar.location}
			</c:if>
			</c:forEach>
			</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td align="center"><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${divisionListSize},'deleteDivision.htm')"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${divisionListSize},'showUpdateDivision.htm');"/></td>
	</tr>
</table>

</form:form>
</div>

</body>
</html>