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
<title>View Visitors</title>
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
			<td>Company</td>
			<td>Visitor Type</td>
			<td>Location</td>
			<!-- <td>Designation</td> -->
			<td>Mobile Number</td>
			<td>Email id</td>
			<td>Photo</td>
		</tr>
		<c:forEach items="${visitorsList}" var="visitorsListVar">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${visitorsListVar.visitorId}"/>"/></td>
			<td>${visitorsListVar.visitorName}</td>
			<td>${visitorsListVar.company}</td>
			<td>${visitorsListVar.visitorType}</td>
			<c:forEach items="${locationList}" var="locationList">
			<c:if test="${locationList.id==visitorsListVar.location}">
			<td>${locationList.location}</td>
			</c:if>
			</c:forEach>
			<td>${visitorsListVar.mobileNo}</td>
			<td>${visitorsListVar.emailId}</td>
<%-- 			<td><img src="${visitorsListVar.visitorPhoto}" height="48" width="64" alt="${visitorsListVar.visitorPhoto}"/></td>

 --%>
 			<td><img src="data:image/png;base64,${visitorsListVar.profilePhoto}" height="48" width="64" alt="${visitorsListVar.profilePhoto}"/></td>
 		</tr>
		</c:forEach>
	</table>
</div></div>

<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${visitorsListSize},'showupdatevisitors.htm');"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${visitorsListSize},'deletevisitors.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>