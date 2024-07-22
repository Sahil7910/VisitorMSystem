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
			<td>Employee</td>
			<td>FromDate</td>
			<td>ToDate</td>
			<td>Description</td>
			<td>Created On</td>
			<td>Created By</td>
		</tr>
		<c:forEach items="${officialToursList}" var="officialToursList" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${officialToursList.id}"/>"/></td>
			<td >${(index.index)+1}</td>
			<c:forEach items="${employeesList}" var="employeesList">
			<c:if test="${officialToursList.workID == employeesList.employeeNo}">
			<td >${employeesList.firstName} ${employeesList.lastName}</td>
			</c:if>
			</c:forEach>
			<td >${officialToursList.from_date}</td>
			<td >${officialToursList.to_date}</td>
			<td >${officialToursList.description}</td>
			<td >${officialToursList.created_on}</td>
			<td >${officialToursList.created_by}</td>
		 </tr>
		</c:forEach>
	</table>

</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" value="DELETE" onclick="return updateDelete(${officialToursListSize},'deleteOfficialTour.htm')"/><input type="button" name="method" value="UPDATE" onclick="return updateDelete(${officialToursListSize},'updateOfficialTour.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>