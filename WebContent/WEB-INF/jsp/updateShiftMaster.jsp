<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<title>Official Tour</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<body id="public">
<div id="container" class="ltr">
<form:form action="saveOrUpdateShiftMaster.htm" method="GET" commandName="shiftMaster" class="wufoo leftLabel page">
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Id
</label>
<div>
<form:input path="shiftid" id="id" class="field text medium" readonly="true"/>
</div>
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift Code<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="shiftcode" id="shiftcode" class="field text medium"/>
</div>
<form:errors path="shiftcode" cssClass="error"/> 
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="shiftname" id="shiftname" class="field text medium"/>
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
<INPUT class="btTxt submit" value="Save" type="submit" >
<INPUT class="btTxt submit" value="Clear" type="reset" >
</div>
</li>
	</ul>

</form:form>
</div>
