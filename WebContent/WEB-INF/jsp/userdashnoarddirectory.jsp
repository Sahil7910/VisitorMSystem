<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>Employees Directory</title>
<!-- <link href="css/user.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<link href="css/table_3.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="CSSTableGeneratorOutter">
		<div class="CSSTableGenerator">
			<table>
				<tr>
					<td>Name</td>
					<td>Phone No.</td>
					<td>Email</td>
					<td>Department</td>
				</tr>
				<c:forEach items="${EmployeeList}" var="employeeListVar">
					<tr>
						<td>${employeeListVar.firstName} ${employeeListVar.lastName}
						</td>
						<td>${employeeListVar.allPhones}</td>
						<td>${employeeListVar.email}</td>
						<td><c:forEach items="${departmentList}"
								var="departmentListVar">
							
								<c:if test="${departmentListVar.id==employeeListVar.departmentId}">
								${departmentListVar.name}
								</c:if>
							
							</c:forEach></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
