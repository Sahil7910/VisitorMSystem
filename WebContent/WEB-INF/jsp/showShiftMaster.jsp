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
<%
	String dateFormat = "dd-MM-yyyy";
	String dateNow = DateTime.CurrentDate(dateFormat);
	Calendar c_month = Calendar.getInstance();
	Calendar c_week = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	int month = Calendar.MONTH;
	int day = Calendar.DATE;
	c_month.add(Calendar.MONTH, -1);
	c_week.add(Calendar.DATE, -7);
	String lastMonthDate = sdf.format(c_month.getTime());
	String lastWeekDate = sdf.format(c_week.getTime());
	String year = dateNow.substring(7, 10);
	String month1 = dateNow.substring(3, 5);
	int iYear = Integer.parseInt(year);
	String loginUser=(String)session.getAttribute("loginUser");
%>
<!-- <script type="text/javascript" src="calendar/js/calendar.js"></script>
<link rel="stylesheet" href="calendar/css/calendar.css" type="text/css">
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form name="shiftMaster" commandName="shiftMaster" method="GET" action="saveShiftMaster.htm" class="wufoo leftLabel page">
<form:hidden path="record_Creation_Date" id="record_Creation_Date" value="<%=dateNow%>"/>
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift Code<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="shiftcode" id="shiftcode" class="field text medium" />
</div>
<form:errors path="shiftcode" cssClass="error"/> 
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="shiftname" id="shiftname" class="field text medium" />
</div>
<form:errors path="shiftname" cssClass="error"/> 
</li>

 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" class="field textarea medium"></form:textarea>
</div>
</li>  

 
<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
</div>
</li>
	</ul>
</form:form>
</div>
</body>
</html>