<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form commandName="leaveType" action="addLeaveType.htm" method="POST" class="wufoo leftLabel page">
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Name<span id="req_23" class="req">*</span>
</label>
<div>
  <form:input path="name" class="field text medium" />
	<form:errors path="name" cssClass="error"/> 
</div>
</li>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> Short Name </label>
<div>
  <form:input path="shortname" class="field text medium" />
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> Yearly Limit <span id="req_23" class="req">*</span> </label>
<div>
  <form:input path="yearlyLimit" class="field text medium" />
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> Carry Forward Limit <span id="req_23" class="req">*</span> </label>
<div>
  <form:input path="carryForwardLimit" class="field text medium" />
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> Applicable To <span id="req_23" class="req">*</span> </label>
<div>
  <form:radiobutton path="applicableTo" value="all" /> All &nbsp;&nbsp;&nbsp;
  <form:radiobutton path="applicableTo" value="male" /> Male &nbsp;&nbsp;&nbsp;
  <form:radiobutton path="applicableTo" value="female" /> Female
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> Consider As <span id="req_23" class="req">*</span> </label>
<div>
  <form:select path="paymentApplicable">
  	<form:option value="1" label="Leave With Pay" >Leave With Pay</form:option>
  	<form:option value="0" label="Leave Without Pay">Leave Without Pay</form:option>
  </form:select>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110"> </label>
<div>
	<form:checkbox path="allowNegativePayBalance" value="1"/> Allow Negative Leave Balance
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" class="field textarea medium" name="value_description"></form:textarea>
</div>
</li> 

<%-- <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Rank
</label>
<div>
  <form:input path="rank" class="field text medium" />
	<form:errors path="rank" cssClass="error"/> 
</div>
</li> --%>


<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
<INPUT class="btTxt submit" value=Reset type="reset" >
</div>
</li>
</ul>

</form:form>
</div>
</body>
</html>
  