<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" commandName="departments" action="updatedepartment.htm?Id=${id}" method="POST" >

<ul>

<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Name<span id="req_23" class="req">*</span>
</label>
<div>
  <form:input path="name" name="deptName" class="field text medium"/>
</div>
<form:errors path="name" cssClass="error"/>
</li>


<li id="foli103" class="notranslate       ">
<label class="desc" id="title103" for="Field103">
Head
</label>
<div>
<form:select path="head" class="field select medium">
          <form:option value="0">--Select--</form:option>
           <c:forEach items="${employeeList}" var="employeeListVar">
          <form:option value="${employeeListVar.employeeId}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
            </c:forEach>
          </form:select>
</div>
</li>


<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Email
</label>
<div>
<form:input path="email" name="email" class="field text medium"/>
</div>
<form:errors path="email" cssClass="error"/>
</li>
<li id="foli103" class="notranslate       ">
<label class="desc" id="title103" for="Field103">
Division<span id="req_23" class="req">*</span>
</label>
<div>
			<form:select path="division">
			<form:option value="0" label="Select"></form:option>
			<form:options items="${divisionList}" itemLabel="divisionName" itemValue="divisionId"/>
			</form:select>
			<form:errors path="division" cssClass="error"/>
			<!-- <input type="button" style="background-color:lightgray" value="Add New"  onclick="genericAjaxFunction('showAddDivisionDiv.htm','POST','divisiondiv');"> -->
</div>
</li>
<div id="divisiondiv">
</div>
 <li class="buttons ">
<div>
<INPUT class="btTxt submit" value="Update" type="submit"> 
      <INPUT class="btTxt submit" value=Reset type=reset onclick="department.htm"> 
    </div>
</li>
</ul>








<%--  <TABLE id="departmentTable" border=1 cellSpacing=7 cellPadding=4 class="maincontent">
        
        <TR>
          <TD width="33%"><font class="mandatory">*</font> Name</TD>
          <TD width="33%" align="left"><!-- <input type="text" name="value_name"  maxlength=100 value=""> -->
          <form:input path="name" name="deptName" />
           </TD>
            <td width="33%"><form:errors path="name" cssClass="error"/> <form:hidden path="id" /></td>
           </TR>
        <TR>
           <TD ><!-- <font class="mandatory">*</font> -->Head</TD>
          <TD align="left"><!-- <select size = "1" id="value_head" name="value_head" ></select> -->
          <form:select path="head" style="width: 150px;">
          <form:option value="0">--Select--</form:option>
           <c:forEach items="${employeeList}" var="employeeListVar">
          <form:option value="${employeeListVar.employeeId}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
            </c:forEach>
          </form:select>
          </TD>
          <td> <form:errors path="head" cssClass="error"/></td>
          </TR>
        <TR>
          <TD>Email</TD>
          <TD align="left"><!-- <input type="text" name="value_email"  maxlength=100 value=""> -->
           <form:input path="email" name="email"/>
           </TD></TR>
           
        <tr>
			<td>Division</td>
			 <td align="left">
			<form:select path="division">
			<form:option value="0" label="Select"></form:option>
			<form:options items="${divisionList}" itemLabel="divisionName" itemValue="divisionId"/>
			</form:select>
			<input type="button" class=button value="Add New"  onclick="genericAjaxFunction('showAddDivisionDiv.htm','POST','divisiondiv');"> </td>
			<td>
			 <div id="divisiondiv"></div></td>
		</tr>   
           
            <TR>
          <TD> <font class="mandatory">* Required Fields</font> </TD>
          <td><td><form:errors path="division" cssClass="error"/></td>
         </TR>
 </TABLE>
     	<SPAN class=buttonborder><INPUT class="button" value="Update" type="submit"></SPAN> 
      <SPAN class=buttonborder><INPUT class="button" value="Reset" type="reset" onclick="showupdateDepartment.htm"></SPAN> 
     
       --%>
</form:form>
</div>

</body>
</html>

 