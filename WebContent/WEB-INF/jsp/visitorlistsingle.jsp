<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Select Visitor<span id="req_23" class="req">*</span>
</label>
<div>
<select name="visitorselect" id="visitorselect" class="field select medium">
		<option value="0" label="Select">Select</option>
		<c:forEach items="${visitorsList}" var="visitorsListVar" varStatus="status">
		<option  value="${visitorsListVar.visitorId}"  >${visitorsListVar.visitorName}</option>
		</c:forEach>
	</select>
</div>
</li>


<%-- <table>
<tr>
<td align="left">Select Employee</td>
<td align="left"><select name="employeeNo" id="employeeNo">
		<option value="0" label="Select">Select</option>
		<c:forEach items="${EmployeeList}" var="EmployeeList" varStatus="status">
		<option value="${EmployeeList.employeeNo}" label="${EmployeeList.firstName}  ${EmployeeList.lastName}" >${EmployeeList.firstName}  ${EmployeeList.lastName}</option>
		</c:forEach>
	</select></td>
</tr>
</table> --%>
</body>
</html>