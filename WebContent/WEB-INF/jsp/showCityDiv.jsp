<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>

<form:form action="addCity.htm" method="POST" commandName="cities">
<div id=maincontent>
<table>
	<tr>
	<td>State: </td>
	<td align="left"><form:select name ="stateid" id ="stateid" path="state_id" style="width: 150px;">
		<form:option value="0">--Select--</form:option>
	    <c:forEach var="state" items="${States}">
	    <form:option value="${state.stateId}">${state.stateName}</form:option>
	    </c:forEach>
		</form:select>
	</td>
	</tr>
	<tr>
	<td>City: </td>
	<td>
		<form:input path="City" id="city"></form:input>
	</td>
	<td>
		<%-- <form:hidden path="CountryId" value="${countryID}"/> --%>
	</td>
	</tr>
	
	<tr>
	<td><input class=button type=submit value="Save" onclick="return validateCity();"></td>
	<td><input class=button type="button" value="Cancel" onclick="cancleDiv()"></td>
	</tr>
</table>

</div>
</form:form>

</body>
</html>