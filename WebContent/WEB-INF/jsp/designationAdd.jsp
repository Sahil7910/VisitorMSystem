<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
<title>Insert title here</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" commandName="designation" method="POST" action="addDesignation.htm?view=addWorkspace&addOrUpdateFlag=${addOrUpdateFlag}">
	
	<ul>
	<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Designation<span id="req_23" class="req">*</span>
</label>
<div>
<form:input name="value_designation"  path="designation" class="field text medium" />
</div>
<form:errors  path="designation" cssClass="error"/>
</li>
	
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
ShortName
</label>
<div> 
<form:input name="value_shortname" path="shortname" class="field text medium"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Related Department
<span id="req_23" class="req">*</span>
</label>
<div>
<form:select name="value_department_id" path="department_id" class="field select medium">
          <form:option value="0">--Select--</form:option>
          <c:forEach items="${departments}" var="departments">
          <form:option value="${departments.id}">${departments.name}</form:option>
          </c:forEach>
</form:select>
</div>
<form:errors  path="department_id" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Supervisor designation
</label>
<div>
<form:select  name="value_parent_designation" path="parent_designation" class="field select medium">
          <form:option value="0">--Select--</form:option>
           <c:forEach items="${designations}" var="parentdesignation">
          <form:option value="${parentdesignation.id}">${parentdesignation.designation}</form:option>
          </c:forEach>
</form:select> 
</div>
</li>
	
<li id="foli110" 
class="notranslate      ">
<label class="mandatory" id="title110" for="Field110">
* Required Fields
</label>
<div>

</div>
</li>
	
	
	<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
      <input type="button" value="Cancel" onclick="cancleDesiDiv();" class="btTxt submit" onclick="addLocationData.htm">
    </div>
</li>
	</ul>
</form:form>
      </div>
</body>
</html>