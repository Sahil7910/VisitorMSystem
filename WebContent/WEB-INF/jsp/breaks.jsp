<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
    .error {
    	color: red;
    }
  </style>
</head>
<body id="public">
<%
	String dateFormat = "dd-MM-yyyy";
	String dateNow = DateTime.CurrentDate(dateFormat);
	Calendar c_month = Calendar.getInstance();
	Calendar c_week = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	int month = Calendar.MONTH;
	int day = Calendar.DATE;
	c_month.add(Calendar.MONTH, -1);
	c_week.add(Calendar.DATE, -7);
	String lastMonthDate = sdf.format(c_month.getTime());
	String lastWeekDate = sdf.format(c_week.getTime());
	String year = dateNow.substring(7, 10);
	String month1 = dateNow.substring(3, 5);
	int iYear = Integer.parseInt(year);
	String loginUser=(String)session.getAttribute("loginUser");
	
%>
<div id="container" class="ltr">
<form:form commandName="breaks" action="addbreaks.htm" method="POST" class="wufoo leftLabel page">
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Employee<span id="req_23" class="req">*</span>
</label>
<div>
 <form:select path="employee_no"  style="width: 150px;" class="field select medium"> 
	    <form:option value="0">--Select--</form:option>
	    	<c:forEach items="${employeeList}" var="employee">
	    		<form:option value="${employee.employeeNo}">${employee.firstName} ${employee.lastName}</form:option>
    		</c:forEach>
    </form:select>
    <form:errors path="employee_no" cssClass="error"/> 
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Title<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="title" class="field text medium" />
</div>
<form:errors path="title" cssClass="error"/> 
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Break Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="breakdate" name="breakdate" path="date" value="<%=dateNow%>" readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2030);showCalender(this, 'breakdate',-350,-270);"></img>
	<form:errors path="date" cssClass="error"/>
</div>
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Duration(in Hrs)<span id="req_23" class="req">*</span>
</label>
<div>
 <form:select path="duration"  style="width: 150px;" class="field select medium">  
   		<form:option value="0">--Select--</form:option>
		    <form:option value="1.0">1.0</form:option>
		    <form:option value="1.5">1.5</form:option>
		    <form:option value="2.0">2.0</form:option>
		    <form:option value="2.5">2.5</form:option>
		    <form:option value="3.0">3.0</form:option>
		    <form:option value="3.5">3.5</form:option>
		    <form:option value="4.0">4.0</form:option>
    	</form:select>
    	<form:errors path="duration" cssClass="error"/> 
</div>
</li>   
     
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" class="field textarea medium"></form:textarea>
</div>
</li>  


 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Status<span id="req_23" class="req">*</span>
</label>
<div>
  <form:select path="status"  style="width: 150px;" class="field select medium">   
	    <form:option value="0">--Select--</form:option>
		    <c:forEach items="${statusList}" var="statusListVar">
		    <form:option value="${statusListVar.id}">${statusListVar.status_name}</form:option>
		    </c:forEach>
	    </form:select>
    	<form:errors path="status" cssClass="error"/> 
</div>
</li>      

 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Requested By
</label>
<div>
 <%=loginUser%>
 <form:hidden path="requested_by" value="<%=loginUser%>"/>  
</div>
</li>   
 
  <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Approved By
</label>
<div>
 <form:select path="approved_by"  style="width: 150px;" class="field select medium"> 
    <form:option value="1">--Select--</form:option>
    <c:forEach items="${employeeList}" var="user">
    <form:option value="${user.email}">${user.email}</form:option>
    </c:forEach>
    </form:select>
</div>
</li> 
 
    
 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Compensation Date
</label>
<div>
<form:input type="text" id="compensationDate" name="compensationDate" path="compensation_date" value="<%=dateNow%>" readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'compensationDate',-350,-270);"></img>
</div>
</li>
    
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Created On
</label>
<div>
 <%=dateNow%>
 <form:hidden path="created_on" value="<%=dateNow%>"/>  
</div>
</li>   

<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
<INPUT class="btTxt submit" value=Reset type="reset" onclick="breaks.htm">
</div>
</li>
      
</ul>
<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="center" colSpan="4"><SELECT class="form"
						id="selectMonth"
						onChange="showCalenderBody(createCalender(document.getElementById('selectYear').value,&#13;&#10;&#9;this.selectedIndex, false));"
						name='select' style="width: 90px;">
							<OPTION value=0 selected>Jan</OPTION>
							<OPTION value=1>Feb</OPTION>
							<OPTION value=2>Mar</OPTION>
							<OPTION value=3>Apr</OPTION>
							<OPTION value=4>May</OPTION>
							<OPTION value=5>Jun</OPTION>
							<OPTION value=6>Jul</OPTION>
							<OPTION value=7>Aug</OPTION>
							<OPTION value=8>Sep</OPTION>
							<OPTION value=9>Oct</OPTION>
							<OPTION value=10>Nov</OPTION>
							<OPTION value=11>Dec</OPTION>
					</SELECT></td>
					<td align="center" colSpan=2><SELECT id=selectYear style="width: 55px;"
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();" href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
				</tr>
			</TBODY>
			<tbody id=calenderTableDays>
				<tr>
					<TD>Sun</TD>
					<TD>Mon</TD>
					<TD>Tue</TD>
					<TD>Wed</TD>
					<TD>Thu</TD>
					<TD>Fri</TD>
					<TD>Sat</TD>
				</tr>
			</tbody>
			<tbody id="calender" style="color: black;">
				<tr>
					<td></td>
				</tr>
			</tbody>
		</TABLE>

</form:form>
</div>
</body>
</html>

 