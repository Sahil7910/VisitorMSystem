<%@page import="java.text.DateFormat"%>
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


<%
// DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy H:m:s");
DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
String createdOn=dateFormat.format(Calendar.getInstance().getTime());
String messageFromId;
String messageFrom;
if(null!=session.getAttribute("loginUsername")&&null!=session.getAttribute("loginUserId"))
{
	messageFrom=session.getAttribute("loginUsername").toString();  
	messageFromId=session.getAttribute("loginUserId").toString();
}
else
{
	messageFrom="admin";  
	messageFromId="0";
}
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

<form:form class="wufoo leftLabel page" name="EmployeeWiseReport" id="EmployeeWiseReport" commandName="employeeMessages">
<input type="hidden" id="employeeReportType"/>


<ul>



<li id="foli346" class="notranslate  threeColumns     ">

<label id="title346" class="desc">
Select a Choice
</label>
<div>
<input id="radioDefault_346" name="Field346" type="hidden" value="" />
<span>
<input type="radio" name="employeeWise" onclick="showRespectiveDiv('multiple','showEmployeeTypeView.htm?');" class="radio"/>
<label class="choice" for="Field346_0" >
Multiple</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('single','showEmployeeTypeView.htm?');"/>
<label class="choice" for="Field346_1" >
Single</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('all','showEmployeeTypeView.htm?');"/>
<label class="choice" for="Field346_2" >
All Employees</label>
</span>
</div>
<form:errors path="employeeId" cssClass="error"/>
</li>
<div id="SelectEmployeeView"> <input type="hidden" name="employeeNo" id="employeeNo" value="0"></div>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">From<span id="req_23" class="req">*</span></label>
<div>
<form:label class="desc" path="messageFrom"><%=messageFrom%></form:label>
<form:hidden path="messageFrom" value="<%=messageFromId%>"/>
</div>
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Subject<span id="req_23" class="req">*</span></label>
<div>
<form:input path="messageSubject" name="messageSubject" class="field text medium"/> 
</div>
<form:errors path="messageSubject" cssClass="error"/>
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Message<span id="req_23" class="req">*</span></label>
<div>
<form:textarea path="messageBody" name="messageBody" class="field textarea medium" rows="10" cols="50"/>
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Send as Memo <span id="req_23" class="req">*</span></label>
<div>
<form:checkbox path="memo" name="memo" />
</div>
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Created On</label>
<div>
<label class="desc" id="title110" for="Field110">
<%=createdOn%>
<span id="req_23" class="req">*</span>
</label>
<form:hidden name="messageDate" id="messageDate" path="messageDate" value="<%=createdOn%>"/>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="button" value="Send" onclick="return generateReportForMsg('addemployeemessages.htm?','<%=messageFrom%>');"/>
    </div>
</li>


</ul>







<%-- <table border="0" width="100%">








<tr>
<td  width="25%"><font class="mandatory">*</font>From</td>
<td  width="25%" align="left">
<form:label path="messageFrom"><%=messageFrom%></form:label>
<form:hidden path="messageFrom" value="<%=messageFromId%>"/>
</td>
<td  width="25%" align="left"><form:errors path="messageFrom" cssClass="error"></form:errors> </td>
<td width="25%"></td>
</tr>

<tr>
<td><font class="mandatory">*</font>Subject</td>
<td align="left"><form:input path="messageSubject" name="messageSubject"/> </td>
<td align="left"><form:errors path="messageSubject" cssClass="error"></form:errors> </td>

</tr>

<tr>
<td><font class="mandatory">*</font>Message</td>
<td align="left"><form:textarea path="messageBody" name="messageBody"/></td>
<td align="left"><form:errors path="messageBody" cssClass="error"></form:errors> </td>
</tr>

<tr>
<td>&nbsp;</td>
<td align="left"><label id="date"><%=createdOn%></label></td>
<td><form:hidden name="messageDate" id="messageDate" path="messageDate" value="<%=createdOn%>"></form:hidden></td>
<td></td>
</tr>


<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>









<tr>
<td class="mandatory">* Required fields</td>
</tr>


<tr>
<td><input type="button" value="Send" onclick="return generateReportForMsg('addemployeemessages.htm?','<%=messageFrom%>');"/></td>
</tr>
</table> --%>
</form:form>
</div>
</body>
</html>