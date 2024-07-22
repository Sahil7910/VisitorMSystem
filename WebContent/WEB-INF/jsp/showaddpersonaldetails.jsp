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

<head><title>Employee Personal Details</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>

</head>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>

<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" method="POST" action="" name="employeeForm" onsubmit="getSelectedEmployeeDetails('showPersonalDetailsFromAddTab.htm','employeeId');">
<ul>
<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"></label>
					<div>Please Select Employee before clicking ADD button</div>
					</li>
	<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Employee <span id="req_23"
						class="req">*</span>
				</label>
				<div>
    <select class="field select medium" id="employeeId" name="employeeId">
    <option value="0">--Select--</option>
    <c:forEach items="${employeeList}" var="employeeListVar">
    <option value="${employeeListVar.employeeId}" label="${employeeListVar.firstName} ${employeeListVar.lastName} ">${employeeListVar.firstName} ${employeeListVar.lastName}</option>
    </c:forEach>
    </select>
				</div>
   </li>
</ul>

<li class="buttons ">
<div>
<INPUT class="btTxt submit" value="Add" type="submit" onclick="if(document.getElementById('employeeId').value==0){alert('Employee Not Selected');return false;};"/>
</div>
</li>
           
          
</form:form>
</div>
</body>
</html>

