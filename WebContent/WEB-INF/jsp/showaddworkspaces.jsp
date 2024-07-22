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
<form:form action="addWorkspaces.htm" class="wufoo leftLabel page" method="POST" commandName="workspaces" enctype="multipart/form-data" name="check">

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
</div>




<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Designation(if specifically created)
</label>
<div>
<form:select path="designation" class="field select medium">
<form:option value="0" label="Select"></form:option>
<form:options items="${designationList}" itemLabel="designation" itemValue="id"/>
</form:select>
<input type="button" value="Add New"  onclick="addDesignation(1);">
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
    <input class="btTxt submit" type="submit" value="Save"/>
    </div>
</li>


</ul>

</form:form>
</div>
</body>
</html>
