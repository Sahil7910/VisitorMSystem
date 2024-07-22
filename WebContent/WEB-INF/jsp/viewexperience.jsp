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
<title>Experience</title>

<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Name</td>
			<td>Designation</td>
			<td>Company</td>
			
		</tr>
		<c:forEach items="${experienceList}" var="experience" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${experience.id}"/>"/></td>
			<td>${index.index+1}</td>
			<td>
			<c:forEach items="${employeeList}" var="employeeVar">
			<c:if test="${experience.employeeId==employeeVar.employeeId}">
				${employeeVar.firstName}&nbsp;${employeeVar.lastName}
			</c:if>
			</c:forEach>
			</td>
			<td>
			
			<c:forEach items="${designationList}" var="designationListVar">
			<c:if test="${experience.designation==designationListVar.id}">
				${designationListVar.designation}
			</c:if>
			</c:forEach>
			</td>
			<td>${experience.company}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${experienceListSize},'deleteExperience.htm');"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${experienceListSize},'showupdateExperience.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>