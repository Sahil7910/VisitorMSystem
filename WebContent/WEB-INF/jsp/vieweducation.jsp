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
		<tr >
			<td>&nbsp;</td>
			
		
			
			<td>Emp.Id</td>
			<td>Name</td>
			<td>Qualification</td>
			<td>Course Name</td>
			<td>Specialization</td>
			<td>Board University</td>
			<td>College/Institute</td>
			<td>Website </td>
			<td>From</td>
			<td>To</td>
			<td>Duration</td>
			<td>Aggregate Percentage</td>
			<td>Remarks</td>
			
		</tr>
		<c:forEach items="${educationList}" var="education" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${education.emp_id}"/>"/></td>
			
			<td>${education.emp_id} </td>
			<td>
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==education.emp_id}">
			${employeeListVar.firstName} ${employeeListVar.lastName}
			</c:if>
			</c:forEach>
			</td>
			<td>${education.emp_education} </td>
			<td>${education.course_name}</td>
			<td>${education.specialisation}</td>
			<td>${education.board_university}</td>
			<td>${education.college}</td>
			<td >${education.website}</td>
			<td >${education.from_date}</td>
			<td >${education.to_date}</td>
			<td >${education.duration}</td>
			<td>${education.percentage}</td>
			<td >${education.remarks}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><%-- <input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return deleteDepartment(${educationListSize},'deleteEducation.htm','POST');"/> --%><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="UPDATE" onclick="return deleteDepartment(${educationListSize},'showupdateEducation.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>