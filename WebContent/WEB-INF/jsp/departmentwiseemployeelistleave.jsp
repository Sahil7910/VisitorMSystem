<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="calendar/js/calendar.js"></script>
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/devicemanagement.js"></script>
<script type="text/javascript" src="javascript/jscolor.js"></script>
<title></title>
</head>
<body>

<table>

<tr><td><input type="radio" name="employeeWise" onclick="showRespectiveDiv('multiple','showDepartmentTypeView.htm?departmentId=${departmentId}&');"/>Multiple Employees</td><td><input type="radio" name="employeeWise" onclick="showRespectiveDiv('single','showDepartmentTypeView.htm?departmentId=${departmentId}&');"/>Single Employee</td><td><input type="radio" name="employeeWise" onclick="showRespectiveDiv('all','showDepartmentTypeView.htm?departmentId=${departmentId}&');"/>All Employees</td></tr>

<tr>
<td>

<div id="SelectEmployeeView"><input type="hidden" name="employeeNo" id="employeeNo" value="0"></div>

</td>
</tr>
</table>
<input type="hidden" value="${departmentId}" id="hiddenDepartmentId" name="hiddenDepartmentId">

</body>
</html>