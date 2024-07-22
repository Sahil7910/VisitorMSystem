<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<title>Division</title>
</head>
<body id="public">
<div id="container" class="ltr">

<form:form action="addDivision.htm" class="wufoo leftLabel page" method="POST" commandName="division">


<ul>

<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
 Division Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="divisionName" id="divisionName" name="divisionName" class="field text medium"/>
</div>
<form:errors path="divisionName" cssClass="error" /> 
</li>

<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Code<span id="req_23" class="req">*</span>
</label>
<div> 
<form:input path="divisionCode" id="divisionCode" name="divisionCode" class="field text medium"/>
</div>
<form:errors path="divisionCode" cssClass="error" />
</li>

<li id="foli110" 
class="notranslate      "><label class="desc" id="title110" for="Field110">
Description
</label>
<div>
<form:textarea path="divisionDescription" class="field textarea medium" rows="10" cols="50" id="divisionDescription" name="divisionDescription"/>
</div>
</li>

<li id="foli103" class="notranslate       ">
<label class="desc" id="title103" for="Field103">
Division Head
</label>
<div>

<form:select path="divisionHead" id="divisionHead" class="field select medium">
 <form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
    <form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:forEach>
</form:select>
</div>
</li>

<li id="foli103" class="notranslate       ">
<label class="desc" id="title103" for="Field103">
Location<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="locationId" id="location_Id" name="location_Id" class="field select medium">
<form:option value="0" label="Select"></form:option>
<form:options items="${locationsList}" itemLabel="location" itemValue="id"/>
</form:select>
<input type="button" value="AddNew" onclick="callPopupWindowWithPost('addLocationInternally.htm','LocationWindow','GET');"/>
</div>
<form:errors path="locationId" cssClass="error"/>
</li>


<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
<span id="req_23" class="req">*</span>Required Fields
</label>
<div>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="save" id="save" value="Save"/>
</div>
</li>
</ul>

</form:form>
</div>
</body>
</html>