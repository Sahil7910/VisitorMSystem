<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>

<!-- <script type="text/javascript" src="calendar/js/calendar.js"></script>
<link rel="stylesheet" href="calendar/css/calendar.css" type="text/css">
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script> -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<ul>
<li id="foli346" class="notranslate  threeColumns">
<label id="title346" class="desc">
Select a Choice
</label>
<div>
<input id="radioDefault_346" name="Field346" type="hidden" value="" />
<span>
<input type="radio" name="employeeWise" onclick="showRespectiveDiv('multiple','showShiftTypeView.htm?shiftId=${shiftId}&');" class="radio"/>
<label class="choice" for="Field346_0" >
Multiple</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('single','showShiftTypeView.htm?shiftId=${shiftId}&');"/>
<label class="choice" for="Field346_1" >
Single</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('all','showShiftTypeView.htm?shiftId=${shiftId}&');"/>
<label class="choice" for="Field346_2" >
All Employees</label>
</span>
</div>
</li>
<div id="SelectEmployeeView">
<input type="hidden" name="employeeNo" id="employeeNo" value="0">
</div>
</ul>
</body>
</html>