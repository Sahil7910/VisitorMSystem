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
<title>Company</title>
</head>
<body id="public">
<div id="container" class="ltr">

<form:form class="wufoo leftLabel page" action="saveOrUpdateWorkspaces.htm" method="POST" commandName="workspaces" enctype="multipart/form-data">
<form:hidden path="id" id="workspaceId"/>


<ul>
<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Workspace Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="workspaceName" id="workspaceName" class="field text medium"/>
</div>
<form:errors path="workspaceName" cssClass="error"/>
</li>


<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Photo<span id="req_23" class="req">*</span>
</label>
<div>
<input type="file" id="photoFile" name="photoFile" onchange="document.getElementById('photo').value=document.getElementById('photoFile').value;">
</div>
<form:hidden path="photo" id="photo" name="photo"/>
</li>

<li id="foli110" 
class="notranslate      "><label class="desc" id="title110" for="Field110">
Description<span id="req_23" class="req">*</span>
</label>
<div>
<form:textarea class="field textarea medium" rows="10" cols="50" path="description" id="description"/>
</div>
</li>


<li id="foli110" 
class="notranslate      "><label class="desc" id="title110" for="Field110">
Department<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="department" class="field select medium" onchange="genericAjaxFunction('showDepartmentTree.htm?deptId='+this.value,'POST','departmentDiv');">
<form:option value="0" label="Select" ></form:option>
<form:options items="${departmentList}" itemLabel="name" itemValue="id"/>
</form:select>
</div>
<form:errors path="department" cssClass="error"/>
</li>

<!--  <div id="deptdiv"></div> -->
 <div id="departmentDiv">
 <li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Location
</label>
<div>
<input type="text" id="locationName" readonly="readonly" value="${location.location}" style="background: transparent;" class="field text medium"/>
</div>
</li>

 <li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Division
</label>
<div>
<input type="text" id="divisionName"  readonly="readonly" value="${division.divisionName}" style="background: transparent;" class="field text medium"/>
</div>
</li>
</div>




<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Designation(if specifically created)
</label>
<div>
<form:select path="designation">
<form:option value="0" label="Select"></form:option>
<form:options items="${designationList}" itemLabel="designation" itemValue="id"/>
</form:select>
<input type="button" class=button value="Add New"  onclick="addDesignationUpdate('${workspaces.id}');">
</div>
</li>
 <div id="desigdiv"></div>

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
    <input class="btTxt submit" type="submit" value="Update"/>
    </div>
</li>


</ul>

</form:form>
</div>
</body>
</html>
