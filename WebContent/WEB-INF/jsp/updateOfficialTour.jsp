<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<title>Official Tour</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
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
<body id="public">
<div id="container" class="ltr">
<form:form action="saveOrUpdateOfficialTour.htm" method="GET" commandName="officialTours" class="wufoo leftLabel page">

<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Employee<span id="req_23" class="req">*</span>
</label>
<div>
    
    <form:select path="workID" style="width: 150px;" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${employeesList}" var="employeesList" varStatus="status">
		<form:option value="${employeesList.employeeNo}" label="${employeesList.firstName} ${employeesList.lastName}" />
		</c:forEach>
	</form:select>
	<form:errors path="workID" cssClass="error"/> 
</div>
</li>



<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
From Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="datefrom" name="datefrom" path="from_date"
						 readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'datefrom',-350,-270);"></img>
	<form:errors path="from_date" cssClass="error"/>
</div>
</li>
						
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
To Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="dateto" name="dateto" path="to_date"
						 readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'dateto',-350,-270);"></img>
	<form:errors path="to_date" cssClass="error"/>
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" class="field textarea medium" id="description"></form:textarea>
</div>
</li> 

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Created On
</label>
<div>
${officialTours.created_on}
</div>
</li>  


 <li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Created By
</label>
<div>
${officialTours.created_by}
</div>
</li> 

<li class="buttons ">
<div>
<INPUT class="btTxt submit" value=Save type="submit" >
<INPUT class="btTxt submit" value=Clear type="reset" >
</div>
</li>
</ul>
<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="center" colSpan="4"><SELECT class="form"
						id="selectMonth"
						onChange="showCalenderBody(createCalender(document.getElementById('selectYear').value,&#13;&#10;&#9;this.selectedIndex, false));"
						name='select'>
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
					<td align="center" colSpan=2><SELECT id=selectYear
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();"href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
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
