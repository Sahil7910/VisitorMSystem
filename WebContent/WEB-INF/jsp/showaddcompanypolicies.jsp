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
<title>Company Policies</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" action="addCompanyPolicies.htm" method="POST" commandName="companyPolicies" enctype="multipart/form-data">

<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Section
<span id="req_23" class="req">*</span>
</label>
<div> 
<form:input path="section" id="section" name="section" class="field text medium"/>
</div>
<form:errors path="section" cssClass="error"/> 
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Title
<span id="req_23" class="req">*</span>
</label>
<div> 
<form:input path="title" id="title" name="title" class="field text medium"/>
</div>
<form:errors path="section" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Statement
</label>
<div> 
<form:textarea path="statement" id="statement" name="statement" class="field textarea medium" rows="10" cols="50"/> 
</div>
</li>


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
Document
</label>
<div> 
<input type="file" id="documentFile" name="documentFile"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Department
<span id="req_23" class="req">*</span>
</label>
<div> 
 <form:select path="departmentId" id="departmentId" name="departmentId" class="field select medium" onchange="genericAjaxFunction('showDepartmentTree.htm?deptId='+this.value,'POST','departmentDiv');">
    <form:option value="0">Select</form:option>
    <c:forEach items="${departmentsList}" var="departmentsListVar">
    <form:option value="${departmentsListVar.id}" label="${departmentsListVar.name}">${departmentsListVar.name}</form:option>
    </c:forEach>
    </form:select>
</div>
<form:errors path="section" cssClass="error"/>
</li>

 <div id="departmentDiv">
</div>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Priority
<span id="req_23" class="req">*</span>
</label>
<div> 
 <form:select path="priorityId" id="priorityId" name="priorityId" class="field select medium">
    <form:option value="0">Select</form:option>
    <c:forEach items="${priorityList}" var="priorityListVar">
    <form:option value="${priorityListVar.id}" label="${priorityListVar.name}">${priorityListVar.name}</form:option>
    </c:forEach>
    </form:select>
    <input type="button" value="AddNew" onclick="callPopupWindowWithPost('addPriorityDiv.htm','PriorityWindow','POST');"/>
</div>
<form:errors path="section" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Location
<span id="req_23" class="req">*</span>
</label>
<div> 
 
  <form:select path="locationId" id="locationId" name="locationId" class="field select medium">
    <form:option value="0">Select</form:option>
    <c:forEach items="${locationsList}" var="locationsListVar">
    <form:option value="${locationsListVar.id}" label="${locationsListVar.location}">${locationsListVar.location}</form:option>
    </c:forEach>
    </form:select>
    <!-- <input class=button type="button" value="AddNew" onclick="callPopupWindowWithPost('addLocationInternally.htm','LocationWindow','GET');"/> -->
    
</div>
<form:errors path="locationId" cssClass="error"/>
</li>
<div id="addLocationDiv">
</div>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Required fields
<span id="req_23" class="req">*</span>
</label>
<div>

</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" value="Save"/>
    </div>
</li>

</ul>

</form:form>
</div>
</body>
</html>