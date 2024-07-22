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
String editedOn=Calendar.getInstance().getTime().toString();
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

<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="updateskills.htm" method="POST" commandName="employeeSkills">
<form:hidden path="id" value="${employeeSkills.id}"/>

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
    <input class="btTxt submit" type="submit" name="save" id="save" value="Update"/>
</div>
</li>
</ul>












<<%-- table border="0" width="100%">


<tr>
<td width="25%"><font class="mandatory">*</font>Employee</td>
<td width="25%" align="left">
<form:select path="employeeId">
<form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
<c:choose>
<c:when test="${employeeSkills.employeeId==employee.employeeId}">
<form:option selected="selected" value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:when>

<c:otherwise>
<form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:otherwise>
</c:choose>
</c:forEach>

</form:select>

</td>
<td width="25%" align="left"><form:errors path="employeeId" cssClass="error"></form:errors></td>
<td width="25%"></td>
</tr>


<tr>
<td><font class="mandatory">*</font>Skill Name</td>
<td align="left"><form:input path="skillName" id="skillName" name="skillName" value="${employeeSkills.skillName}"/>
<td>Skill Type</td>
<td align="left">
<form:select path="skillType" id="skillType" name="skillType">
<c:choose>
<c:when test="${employeeSkills.skillType!='0'}">
<form:option value="${employeeSkills.skillType}" label="${employeeSkills.skillType}" selected="selected">${employeeSkills.skillType}</form:option>
</c:when>
<c:otherwise>
<form:option value="0" label="Select" selected="selected">Select</form:option>
</c:otherwise>
</c:choose>
<form:option value="primary" label="Primary">Primary</form:option>
<form:option value="secondary" label="Secondary">Secondary</form:option>
<form:option value="other" label="Other">Other</form:option>

</form:select>

</td>
</tr>

<tr>
<td></td>
<td align="left"><form:errors path="skillName" cssClass="error"></form:errors></td>
<td></td>
<td align="left"></td>
</tr>

<tr>
<td><font class="mandatory">*</font>Skill Level</td>
<td align="left">
<form:select path="skillLevel" id="skillLevel" name="skillLevel">

<c:choose>
<c:when test="${employeeSkills.skillLevel!='0'}">
<form:option value="${employeeSkills.skillLevel}" label="${employeeSkills.skillLevel}" selected="selected">${employeeSkills.skillLevel}</form:option>
</c:when>
<c:otherwise>
<form:option value="0" label="Select" selected="selected">Select</form:option>
</c:otherwise>
</c:choose>


<form:option value="0" label="select">Select</form:option>
<form:option value="novice" label="Novice">Novice</form:option>
<form:option value="intermediate" label="intermediate">Intermediate</form:option>
<form:option value="expert" label="expert">Expert</form:option>

</form:select> </td>
<td>&nbsp;</td>
<td align="left">
    </td>
</tr>

<tr>
<td></td>
<td align="left"><form:errors path="skillLevel" cssClass="error"></form:errors></td>
<td></td>
<td align="left"></td>
</tr>



<tr>
<td><font class="mandatory">*</font>Experience</td>
<td align="left"><form:input path="experienceYear" id="experienceYear" name="experienceYear" value="${employeeSkills.experienceYear}" /> Year(s) </td>
<td align="left"> <form:input path="experienceMonth" id="experienceMonth" name="experienceMonth" value="${employeeSkills.experienceMonth}"/> Month(s)</td>
<td>&nbsp;</td>
</tr>

<tr>

<td>Description</td>
<td align="left" rowspan="3">
<textarea rows="3" id="newDescription" name="newDescription" onblur="setTextarea(this.value,'description');">${employeeSkills.description}</textarea>

<form:hidden path="description" id="description" name="description" value="${employeeSkills.description}"/> </td>
<td>Certification</td>
<td align="left">
<form:select path="certification">
<c:choose>
<c:when test="${employeeSkills.certification!='0'}">
<form:option value="${employeeSkills.certification}" label="${employeeSkills.certification}" selected="selected">${employeeSkills.certification}</form:option>
</c:when>
<c:otherwise>
<form:option value="0" label="Select" selected="selected">Select</form:option>
</c:otherwise>
</c:choose>

<form:option value="yes" label="Yes">Yes</form:option>
<form:option value="no" label="No">No</form:option>
</form:select>

 </td>
</tr>

<tr>

<td>&nbsp;</td>
<td>Attachment</td>
<td align="left"><input type="file" id="attachmentFile" name="attachmentFile"/> 

<form:hidden path="attachment" id="oldfile" name="oldfile" value="${employeeSkills.attachment}"/>

</td>
</tr>

<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>

<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>


<tr>
<td>Edited By</td>
<td align="left"><form:label path="editedBy" value="<%=loginUser%>"/><%=loginUser%></td>
<td>Edited On</td>
<td align="left"><form:label path="editedOn" value="<%=editedOn%>"><%=editedOn%></form:label></td>
</tr>

<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>


<tr>
<td class="mandatory">* Required fields</td>
</tr>


<tr>
<td><input type="submit" name="save" id="save" value="Update"/></td>
</tr>
</table> --%>


		
		
</form:form>
</div>
</body>
</html>