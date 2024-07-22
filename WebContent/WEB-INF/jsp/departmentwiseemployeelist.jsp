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

<li id="foli346" class="notranslate  threeColumns     ">
<label id="title346" class="desc">
Select a Choice <span id="req_23" class="req">*</span>
</label>
<div>
<input id="radioDefault_346" name="Field346" type="hidden" value="" />
<span>
<input type="radio" name="employeeWise" onclick="showRespectiveDiv('multiple','showDepartmentTypeView.htm?departmentId=${departmentId}&');" class="radio"/>
<label class="choice" for="Field346_0" >
Multiple</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('single','showDepartmentTypeView.htm?departmentId=${departmentId}&');"/>
<label class="choice" for="Field346_1" >
Single</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('all','showDepartmentTypeView.htm?departmentId=${departmentId}&');"/>
<label class="choice" for="Field346_2" >
All Employees</label>
</span>
</div>
</li>
<div id="SelectEmployeeView"><input type="hidden" name="employeeNo" id="employeeNo" value="0"></div>
<input type="hidden" value="${departmentId}" id="hiddenDepartmentId" name="hiddenDepartmentId">

</body>
</html>