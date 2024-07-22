<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle Details</title>
</head>

<body id="public">
<div id="container" class="ltr">
<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="updatevehicledetails.htm" method="GET" commandName="vehicleDetails">
<form:hidden path="vehicleId"/>
<ul>
<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Number<span id="req_23" class="req">*</span></label>
<div>
<form:input path="vehicleNumber" id="vehicleNumber" name="vehicleNumber" class="field text medium"/> <font class="error"> (MH 01 AA 1111)</font>
<form:errors path="vehicleNumber" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Brand<span id="req_23" class="req">*</span></label>
<div>
<form:input path="vehicleBrand" id="vehicleBrand" name="vehicleBrand" class="field text medium"/>
<form:errors path="vehicleBrand" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Model<span id="req_23" class="req">*</span></label>
<div>
<form:input path="vehicleModel" id="vehicleModel" name="vehicleModel" class="field text medium"/>
<form:errors path="vehicleModel" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Edition<span id="req_23" class="req">*</span></label>
<div>
<form:input path="vehicleEdition" id="vehicleEdition" name="vehicleEdition" class="field text medium"/>
<form:errors path="vehicleEdition" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Type <span id="req_23" class="req">*</span></label>
<div>
<form:select path="vehicleType" id="vehicleType" class="field select medium">
<form:option value="0">Select</form:option>
<form:option value="Two Wheeler">Two Wheeler</form:option>
<form:option value="Three Wheeler">Three Wheeler</form:option>
<form:option value="Four Wheeler">Four Wheeler</form:option>
</form:select>
<form:errors path="vehicleType" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Owner Name<span id="req_23" class="req">*</span></label>
<div>
<form:input path="ownerName" id="ownerName" name="ownerName" class="field text medium"/>
<form:errors path="ownerName" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Vehicle Owner Ph no.<span id="req_23" class="req">*</span></label>
<div>
<form:input path="ownerPhoneNo" id="ownerPhoneNo" name="ownerPhoneNo" class="field text medium"/>
<form:errors path="ownerPhoneNo" cssClass="error"></form:errors>
</div>
</li>


<li id="foli1" class="notranslate"><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
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