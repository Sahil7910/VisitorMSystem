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
<form:form class="wufoo leftLabel page" commandName="departments" action="addDepartment.htm" method="POST" >
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
<INPUT class="btTxt submit" value=Save type=submit> 
      <INPUT class="btTxt submit" value=Reset type=reset onclick="department.htm"> 
    </div>
</li>
</ul>
</form:form>
 </div>
</body>
</html>

 