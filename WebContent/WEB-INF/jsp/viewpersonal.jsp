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
<script type="text/javascript" src="javascript/location.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>Emp No.</td>
			<td>Name</td>
			<td>Marital Status</td>
			<td>Gender</td>
			<td>Date of Birth</td>
			<td>Home Phone</td>
			<td>Personal Email</td>
			<td>Emergency Contact</td>
			<td>Skills</td>
			<td>Years Experience</td>
			<td>Passing Year</td>
			<td>Country</td>
			<td>State</td>
			<td>City</td>
			
		</tr>
		<c:forEach items="${employeeList}" var="employees" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${employees.employeeId}"/>"/></td>
			<td >${employees.employeeNo} </td>
			<td >${employees.firstName} ${employees.fatherhusbandName} ${employees.lastName} </td>
			<td >${employees.maritalStatus}</td>
			<td >${employees.gender}</td>
			<td >${employees.dateOfBirth}</td>
			<td >${employees.homePhone}</td>
			<td>${employees.personalEmails}</td>
			<td>${employees.emergencyContact}</td>
			<td >${employees.skills}</td>
			<td>${employees.yearsExperience}</td>
			<td>${employees.passingYear}</td>
			<td>${countryMap[employees.permanentCountry]}</td>
			<td>${stateMap[employees.permanentState]}</td>
			<td>${cityMap[employees.permanentCity]}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<%-- <td ><input type="button" class=button name="method" value="DELETE" onclick="return deleteDepartment(${employeeListSize},'deleteEmployee.htm','POST');"/></td> --%>
		<td><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return deleteDepartment(${employeeListSize},'showPersonalDetails.htm','POST');"/></td>
		
	</tr>
</table>
</form:form>
</div>
</body>
</html>