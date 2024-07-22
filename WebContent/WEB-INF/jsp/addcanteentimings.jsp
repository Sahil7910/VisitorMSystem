<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Canteen Timings</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="addcanteentimings.htm" method="POST" commandName="canteenTimings">

<ul>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Name <span id="req_23" class="req">*</span></label>
<div>
	<form:input path="timingName" id="timingName" name="timingName" class="field text medium"/>
	<form:errors path="timingName" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Short Name</label>
<div>
	<form:input path="shortName" id="shortName" name="shortName" class="field text medium"/>
</div>
	
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Start Time <span id="req_23" class="req">*</span></label>
<div>
	<form:select path="startTime" class="field select medium">
		<form:option value="0">Select</form:option>
		<c:forEach items="${calTimesList}" var="calTimesListVar">
			<form:option value="${calTimesListVar.time}">${calTimesListVar.time}</form:option>
		</c:forEach>
	</form:select>
	<form:errors path="startTime" cssClass="error"></form:errors>
	<br>
	<font class="error"> ${startTimeError}</font>
</div>
	
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">End Time <span id="req_23" class="req">*</span></label>
<div>

	<form:select path="endTime" class="field select medium">
		<form:option value="0">Select</form:option>
		<c:forEach items="${calTimesList}" var="calTimesListVar">
			<form:option value="${calTimesListVar.time}">${calTimesListVar.time}</form:option>
		</c:forEach>
	</form:select>
	
	<form:errors path="endTime" cssClass="error"></form:errors>
	<br>
	<font class="error">${endTimeError}</font>
</div>
	
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Default Item <span id="req_23" class="req">*</span></label>
<div>

	<form:select path="defaultItem" class="field select medium">
		<form:option value="0">Select</form:option>
		<c:forEach items="${canteenItemsList}" var="canteenItemsListVar">
			<form:option value="${canteenItemsListVar.itemsId}">${canteenItemsListVar.itemName}</form:option>
		</c:forEach>
	</form:select>
	
	<form:errors path="defaultItem" cssClass="error"></form:errors>
</div>
	
</li>

<li id="foli1" class="notranslate      "><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
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