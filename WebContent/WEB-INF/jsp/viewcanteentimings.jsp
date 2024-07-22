<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Canteen Timings</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Name</td>
			<td>Short Name</td>
			<td>Start Time</td>
			<td>End Time</td>
			<td>Default Item</td>
		</tr>
		<c:forEach items="${canteenTimingsList}" var="canteenTimingsListVar">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${canteenTimingsListVar.timingId}"/>"/></td>
			<td>${canteenTimingsListVar.timingName}</td>
			<td>${canteenTimingsListVar.shortName}</td>
			<td>${canteenTimingsListVar.startTime}</td>
			<td>${canteenTimingsListVar.endTime}</td>
			<c:forEach items="${canteenItemsList}" var="canteenItemsListVar">
				<c:if test="${canteenTimingsListVar.defaultItem==canteenItemsListVar.itemsId}">
			    	<td>${canteenItemsListVar.itemName}</td>
				</c:if>
			</c:forEach>
		</tr>
		</c:forEach>
	</table>
</div></div>

<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${canteenTimingsListSize},'showupdatecanteentimings.htm');"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${canteenTimingsListSize},'deletecanteentimings.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>