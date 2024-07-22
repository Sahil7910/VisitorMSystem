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

<form:form action="updateContractor.htm" method="POST" commandName="contractor" enctype="multipart/form-data" name="check" class="wufoo leftLabel page">
<form:hidden path="contractorId" id="contractorId"/>
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Contractor Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="contractorName" id="contractorName" class="field text medium" />
</div>
<form:errors path="contractorName" cssClass="error"/> 
</li>
<%-- <table border="0" width="100%">
<tr>
<td width="25%">Contractor Name<form:hidden path="contractorId" id="contractorId"/></td>
<td width="25%" align="left"><form:input path="contractorName" id="contractorName"/><font class="mandatory">*</font></td>
<td width="25%" align="left"><form:errors path="contractorName" cssClass="error"/> </td>
<td width="25%"></td>
</tr>
 --%>
 
 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Code<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="code" id="code" class="field text medium" />
</div>
<form:errors path="code" cssClass="error"/> 
</li>
 
<%-- <tr>
<td>Code</td>
<td  align="left"><form:input path="code" id="code"/> <font class="mandatory">*</font></td>
<td  align="left"><form:errors path="code" cssClass="error"/> </td>
<td></td>
</tr> --%>

 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" id="description" class="field textarea medium"></form:textarea>
</div>
</li>   


<%-- <tr>
<td>Description</td>
<td align="left"><form:textarea path="description" id="description"/></td>
</tr>
 --%>
 
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Department<span id="req_23" class="req">*</span>
</label>
<div>
<form:select  name="department" path="department" class="field select medium">
          <form:option value="0">--Select--</form:option>
          <form:options items="${departmentList}" itemLabel="name" itemValue="id"/>
</form:select>
<form:errors path="department" cssClass="error"/> 
</div>
<div id="deptdiv"></div>
</li>
<%-- <tr>
<td><font class="mandatory">*</font>Department</td>
 <td align="left">
<form:select path="department">
<form:option value="0" label="Select"></form:option>
<form:options items="${departmentList}" itemLabel="name" itemValue="id"/>
</form:select>
<form:errors path="department" cssClass="error"/>
<!-- <input type="button" class=button value="Add New"  onclick="addDepartment(1);"> </td> -->
<td>
 <div id="deptdiv"></div></td>
</tr> --%>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Contact Person Name<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="contactPerson" id="contactPerson" class="field text medium" />
</div>
<form:errors path="contactPerson" cssClass="error"/> 
</li>
<%-- <tr>
<td width="25%">Contact Person Name</td>
<td width="25%" align="left"><form:input path="contactPerson" id="contactPerson"/> <font class="mandatory">*</font></td>
<td width="25%" align="left"><form:errors path="contactPerson" cssClass="error"/> </td>
<td width="25%"></td>
</tr>
 --%>
 
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Contact No<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="contactNo" id="contactNo" class="field text medium" />
</div>
<form:errors path="contactNo" cssClass="error"/> 
</li>
<%-- <tr>
<td width="25%">Contact No</td>
<td width="25%" align="left"><form:input path="contactNo" id="contactNo"/> <font class="mandatory">*</font></td>
<td width="25%" align="left"><form:errors path="contactNo" cssClass="error"/> </td>
<td width="25%"></td>
</tr> --%>

 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Designation Of Contractor<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="designationOfPerson" id="designationOfPerson" class="field text medium" />
</div>
<form:errors path="designationOfPerson" cssClass="error"/> 
</li>
<%-- 
<tr>
<td>Designation Of Contractor</td>
 <td width="25%" align="left"><form:input path="designationOfPerson" id="designationOfPerson"/> <font class="mandatory">*</font></td>
<td width="25%" align="left"><form:errors path="designationOfPerson" cssClass="error"/> </td>
<td width="25%"></td>
</tr> --%>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Email Id<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="emailId" id="emailId" class="field text medium" />
</div>
<form:errors path="emailId" cssClass="error"/> 
</li>

<%-- <tr>
<td width="25%">Email Id</td>
<td width="25%" align="left"><form:input path="emailId" id="emailId"/> <font class="mandatory">*</font></td>
<td width="25%" align="left"><form:errors path="emailId" cssClass="error"/> </td>
<td width="25%"></td>
</tr> --%>
<li id="foli110" 
class="notranslate">
<label class="mandatory" id="title110" for="Field110">
* Required Fields
</label>
<div>

</div>
</li>
<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
</div>
</li>
	</ul>
</form:form>
</div>
</body>
</html>
