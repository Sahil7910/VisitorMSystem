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
			<td>Id</td>
			<td>Parent Location</td>
			<td>Country</td>
			<td>State</td>
			<td>City</td>
			<td>Phone</td>
			<td>Email</td>
		</tr>
		<c:forEach items="${locationList}" var="locations" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${locations.id}"/>"/></td>
			<td>${locations.location}</td>
			<td>
			<c:forEach items="${parentLocations}" var="parentLocationListVar" varStatus="parentIndex">
			<c:if test="${parentLocationListVar.id==locations.sub_locationof}">
			${parentLocationListVar.location}
			</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${countries}" var="countryVar" varStatus="countryIndex">		
							<c:if test="${countryVar.countryId==locations.country}">
								${countryVar.country}
							</c:if>
			</c:forEach>
			</td>
			
			<td>
			<c:forEach items="${states}" var="statesVar" varStatus="stateIndex">
							<c:if test="${statesVar.stateId==locations.state}">
							${statesVar.stateName}
							</c:if>
							
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${city}" var="citiesVar" varStatus="citiesIndex">
						<c:if test="${citiesVar.cityId==locations.city}">
						${citiesVar.city}
						</c:if>
			</c:forEach>
			</td>
			<td><font color="black">${locations.phone}</font></td>
			<td><font color="black">${locations.email}</font></td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td align="center"><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return deleteLocation(${locationListSize},'deletelocation.htm');"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return deleteLocation(${locationListSize},'showupdatelocation.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>