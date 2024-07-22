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
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Employee</td>
			<td>Position</td>
			<td>Personality</td>
			<td>Communication</td>
			<td>Knowledge </td>
			<td>Strength</td>
			<td>Weakness </td>
			<td>Company Name</td>
			<td>Reason</td>
			<td>Interviwer1</td>
			<td>Date1</td>
			<td>Remark1</td>
			<td>Interviwer2</td>
			<!-- <td>Date2</td>
			<td>Remark2</td> -->
		</tr>
		<c:forEach items="${assesmentList}" var="assesment" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${assesment.id}"/>"/></td>
			<c:forEach items="${employeeList}" var="employeeList">
			<c:if test="${employeeList.employeeId==assesment.empid}">
			<td >${employeeList.firstName} ${employeeList.lastName}</td>
			</c:if>
			</c:forEach>
			<td >
			<c:forEach items="${designationList}" var="designationListVar">
			<c:if test="${designationListVar.id==assesment.position}">
			${designationListVar.designation}
			</c:if>
			</c:forEach>
			</td>
			<td >${assesment.personality}</td>
			<td >${assesment.communication}</td>
			<td >${assesment.knowledge}</td>
			<td >${assesment.strength}</td>
			<td >${assesment.weakness}</td>
			<td >${assesment.company_name}</td>
			<td >${assesment.reason}</td>
			<c:forEach items="${employeeList}" var="employeeList">
			<c:if test="${employeeList.employeeId==assesment.interviewer1}">
			<td >${employeeList.firstName} ${employeeList.lastName}</td>
			</c:if>
			</c:forEach>
			<td >${assesment.date1}</td>
			<td >${assesment.remark1}</td>
			<c:forEach items="${employeeList}" var="employeeList">
			<c:if test="${employeeList.employeeId==assesment.interviewer2}">
			<td >${employeeList.firstName} ${employeeList.lastName}</td>
			</c:if>
			</c:forEach>
			<%-- <td >${assesment.date2}</td>
			<td >${assesment.remark2}</td> --%>
	 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return deleteDepartment(${assesmentListSize},'deleteAssesment.htm','POST');"/><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return deleteDepartment(${assesmentListSize},'showupdateAssesment.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>