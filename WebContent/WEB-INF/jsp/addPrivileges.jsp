<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>


<%String loginUser=(String)session.getAttribute("loginUser"); 
String createdOn=Calendar.getInstance().getTime().toString();

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>


<title>Skills</title>
</head>
<body id="public">
<div id="container" class="ltr">

<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="addskills.htm" method="POST" commandName="employeeSkills">




<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Employee
<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="employeeId" class="field select medium">
<form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
<form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:forEach>
</form:select>
</div>
<form:errors path="employeeId" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Skill Name
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="skillName" id="skillName" name="skillName" class="field text medium"/>
</div>
<form:errors path="skillName" cssClass="error"></form:errors>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Skill Type
</label>
<div>
<form:select path="skillType" id="skillType" name="skillType" class="field select medium">
<form:option value="0" label="select">Select</form:option>
<form:option value="primary" label="Primary">Primary</form:option>
<form:option value="secondary" label="Secondary">Secondary</form:option>
<form:option value="other" label="Other">Other</form:option>
</form:select>
</div>

</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Skill Level
<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="skillLevel" id="skillLevel" name="skillLevel" class="field select medium">
<form:option value="0" label="select">Select</form:option>
<form:option value="novice" label="Novice">Novice</form:option>
<form:option value="intermediate" label="intermediate">Intermediate</form:option>
<form:option value="expert" label="expert">Expert</form:option>
</form:select> 
</div>
<form:errors path="skillLevel" cssClass="error"/>
</li>



<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Experience
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="experienceYear" id="experienceYear" name="experienceYear" class="field text medium"/> Year(s)<span id="req_23" class="req">*</span> 
</div>
<form:errors path="experienceYear" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">

</label>
<div>
<form:input path="experienceMonth" id="experienceMonth" name="experienceMonth" class="field text medium"/> Month(s)<span id="req_23" class="req">*</span>
</div>
<form:errors path="experienceMonth" cssClass="error"/>
</li>


<%-- <li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="experienceMonth" id="experienceMonth" name="experienceMonth" class="field text medium"/> Month(s)
</div>
<form:errors path="experienceMonth" cssClass="error"/>
</li> --%>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" id="description" name="description" class="field textarea medium" rows="10" cols="50"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Certification
</label>
<div> 
<form:select path="certification" class="field select medium">
<form:option value="0">Select</form:option>
<form:option value="yes" label="Yes">Yes</form:option>
<form:option value="no" label="No">No</form:option>
</form:select>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Attachment
</label>
<div> 
<input type="file" id="attachmentFile" name="attachmentFile"/>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Created By
</label>
<div> 
<form:label path="createdBy" value="<%=loginUser%>"/><%=loginUser%>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Created On
</label>
<div> 
<form:label path="createdOn" value="${employeeSkills.createdOn}">${employeeSkills.createdOn}</form:label>
</div>
</li>


<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
<span id="req_23" class="req">*-Required Fields</span>
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