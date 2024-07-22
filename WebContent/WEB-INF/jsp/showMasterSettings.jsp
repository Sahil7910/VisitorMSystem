<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<title>Master Settings</title>
</head>

    <%
	DateTime dateTime=new DateTime();
	String isLeap=dateTime.isLeapYear();
	System.out.println("isLeap"+isLeap);
    %>

<body id="public">
<div id="container" class="ltr">
<form:form action="addUpdateMasterSettings.htm" method="POST" commandName="masterSettings" name="masterSettings" class="wufoo leftLabel page">
<form:input type="hidden" path="attendanceDay" id="attendanceDay"/>
<input type="hidden" value="<%=isLeap%>" id="isLeap">
<ul>
<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Auto Database Backup</label>
<div>

</div>
 
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Every</label>
<div>
<form:select name="dbBackupDays" path="dbBackupDays" class="field select medium">
          <form:option value="0">--Select--</form:option>
          <form:options items="${calNumbersList}" itemLabel="n" itemValue="n"/>
</form:select>&nbsp;&nbsp; Days
</div>
 
</li>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Restore Database</label>
<div>
<form:select name="dbBackupDays" path="dbBackupDays" class="field select medium">
          <form:option value="0">--Select--</form:option>
          <form:options items="${calNumbersList}" itemLabel="n" itemValue="n"/>
</form:select>&nbsp;&nbsp; Days
</div>
 
</li>

<%--  <li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Database Backup Path</label>
<div> 
<jsp:plugin code="DatabasePathApplet.class" archive="DatabasePathApplet.jar" height="30px;" width="100px;" codebase="test11" type="applet"></jsp:plugin>
</div>
</li>   --%> 

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Attendance Year Starts on</label>
<div>
 <form:select class="field select medium" path="attendanceMonth" onchange="showDatesAsPerMonth(this.value,'showDayDivAccMonth.htm');">
					<form:option value="99" selected="selected" label="--Select Month--">--Select Month--</form:option>
					<form:option value="0" label="January"></form:option>
					<form:option value="1" label="February"></form:option>
					<form:option value="2" label="March"></form:option>
					<form:option value="3" label="April"></form:option>
					<form:option value="4" label="May"></form:option>
					<form:option value="5" label="June"></form:option>
					<form:option value="6" label="July"></form:option>
					<form:option value="7" label="August"></form:option>
					<form:option value="8" label="September"></form:option>
					<form:option value="9" label="October"></form:option>
					<form:option value="10" label="November"></form:option>
					<form:option value="11" label="December"></form:option>
					</form:select> 
</div>

</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"></label>
 <div id="dayDiv"></div>
 </li>





<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Difference between 2 punches
</label>
<div>
<form:input type="text"  class="field text medium"  path="diffBetPunch" id="diffBetPunch"/>&nbsp;&nbsp; (in minutes)
</div>
</li> 

 


<!--  <li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Employee Photos Path</label>
<div> 

</div>
</li>  
 -->

 <li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
</div>
</li>

<!-- <li id="foli110" 
class="notranslate">
<label class="mandatory" id="title110" for="Field110">
* Required Fields
</label>
<div>

</div>
</li> -->

	</ul>
</form:form>
</div>
</body>
</html>
