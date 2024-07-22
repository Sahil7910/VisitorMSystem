<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Canteen Items</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="updatecanteenitems.htm" method="POST" commandName="canteenItems">

<form:hidden path="itemsId"/>
<ul>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Item Name<span id="req_23" class="req">*</span></label>
<div>
	<form:input path="itemName" id="itemName" name="itemName" class="field text medium"/>
	<form:errors path="itemName" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Short Name</label>
<div>
	<form:input path="itemShortName" id="itemShortName" name="itemShortName" class="field text medium"/>
</div>
	
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Employee Contribution</label>
<div>
	<form:input path="employeeContribution" id="employeeContribution" name="employeeContribution" class="field text medium"/>
	<form:errors path="employeeContribution" cssClass="error"></form:errors>
</div>
	
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Employer Contribution</label>
<div>
	<form:input path="employerContribution" id="employerContribution" name="employerContribution" class="field text medium"/>
	<form:errors path="employerContribution" cssClass="error"></form:errors>
</div>
	
</li>

<li id="foli1" class="notranslate      "><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
<div>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="save" id="save" value="Update"/>
</div>
</li>


</ul>

</form:form>
</div>
</body>
</html>