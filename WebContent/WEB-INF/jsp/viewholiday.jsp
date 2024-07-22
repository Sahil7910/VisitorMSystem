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
			<td>Holiday Name</td>
			<td>Holiday Date</td>
		</tr>
		<c:forEach items="${holidayList}" var="holiday" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${holiday.id}"/>"/></td>
			<td>${(index.index)+1}</td>
			<td>${holiday.holidayName}</td>
			<td>${holiday.holidayDate}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${holidayListSize},'deleteholiday.htm')"/><input type="button" name="method" style="background-color:lightgray; margin-left: 2%;" value="UPDATE" onclick="return updateDelete(${holidayListSize},'showupdateholiday.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>