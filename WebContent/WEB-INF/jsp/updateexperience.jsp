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


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>


<title>Experience</title>
</head>

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

<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="updateexperience.htm" method="POST" commandName="employeeExperiences">

<form:hidden path="id" id="id" name="id"/>
<ul>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Employee
<span id="req_23" class="req">*</span><br/>
<form:errors path="employeeId" cssClass="error"/>
</label>

<div>
<form:select path="employeeId" class="field select medium">
<form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
<form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
</c:forEach>
</form:select>
</div>

</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Designation
<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="designation" class="field select medium">
<form:option value="0" label="Select"></form:option>
<form:options items="${designationList}" itemLabel="designation" itemValue="id"/>
</form:select>
</div>
<form:errors path="designation" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Company
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="company" id="company" name="company" class="field text medium"/>
</div>
<form:errors path="company" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
From Date
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="fromDate" name="fromDate" path="fromDate"
	value="<%=dateNow%>" readonly="true" class="field text medium"/> &nbsp;&nbsp; 
	<img id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'fromDate',-350,-270);" ></img>
</div>
<form:errors path="fromDate" cssClass="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
To Date
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" class="field text medium" id="toDate" name="toDate" path="toDate"
	value="<%=dateNow%>" readonly="true"/> &nbsp;&nbsp;&nbsp;&nbsp; 
	<img id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'toDate',-350,-270);"></img>
</div>
<form:errors path="toDate" cssClass="error"/>
</li>




<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Address
</label>
<div>
<form:textarea path="address" id="address" name="address" class="field textarea medium" rows="10" cols="50"/>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Country
<span id="req_23" class="req">*</span>
</label>
<div>
<form:select class="field select medium" path="country" id="currentCountry" onchange="showState(this.value);document.getElementById('stateLable').style.display= 'block';" >
    <form:option value="0">--Select--</form:option>
    <c:forEach var="country" items="${countries}">
    <form:option value="${country.countryId}">${country.country}</form:option>
    </c:forEach>
</form:select>
</div>
<form:errors path="country" cssStyle="error"/>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="stateLable1" for="Field110">
State
<span id="req_23" class="req">*</span>
</label>
<div id="statediv">
<form:select path="state" class="field select medium" onchange="showCities(this.value)">
 <form:option value="0">--Select--</form:option>
<c:forEach items="${employeeState}" var="employeeState">
<form:option value="${employeeState.stateId}">${employeeState.stateName}</form:option>
<%-- <c:choose>
<c:when test="${employeeExperiences.state==employeeState.stateId}">
<form:option selected="selected" value="${employeeState.stateId}">${employeeState.stateName}</form:option>
</c:when>
<c:otherwise>
<form:option value="${employeeState.stateId}">${employeeState.stateName}</form:option>
</c:otherwise>
</c:choose> --%>
</c:forEach>
</form:select>
</div>
<form:errors path="state" id="state" name="state"/>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" for="Field110" id="cityLable">
City
<span id="req_23" class="req">*</span>
</label>
<div id="showCitiesDiv">
<form:select path="city" class="field select medium">
<form:option value="0">--Select--</form:option>
<c:forEach items="${employeeCity }" var="employeeCity">
<form:option value="${employeeCity.cityId}">${employeeCity.city}</form:option>
<%-- <c:choose>
<c:when test="${employeeExperiences.city==employeeCity.cityId}">
<form:option selected="selected" value="${employeeCity.cityId}">${employeeCity.city}</form:option>

</c:when>
<c:otherwise>
<form:option value="${employeeCity.cityId}">${employeeCity.city}</form:option>

</c:otherwise>

</c:choose> --%>

</c:forEach>
</form:select>

</div>
<div id="addNewButtonDiv" style="display: none;" > <input type="button" value="Add new" onclick="addNewCityDiv();"></div>
<form:errors path="city" id="city" name="city"/>
</li>



<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Website
</label>
<div>
<form:input path="website" id="website" name="website" class="field text medium"/>
</div>
<form:errors path="website" cssClass="error"/> 
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Salary
</label>
<div>
<form:input path="salary" id="salary" name="salary" class="field text medium"/>
</div>
<form:errors path="salary" cssClass="error"/>
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

<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
<span id="req_23" class="req">*-Required Fields</span>
</label>
<div>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="save" id="save" value="Save"/>
</div>
</li>
</ul>



<%-- <table border="0" width="100%">

<form:hidden path="id" id="id" name="id"/>

<tr>
<td width="25%">Employee Id</td>
<td width="25%" align="left">
<form:select path="employeeId">
<form:option value="0">Select</form:option>
<c:forEach items="${employeeList}" var="employee">
	<c:choose>
	<c:when test="${employeeExperiences.employeeId==employee.employeeId}">
	<form:option selected="selected" value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
	</c:when>
	
	<c:otherwise>
	<form:option value="${employee.employeeId}" label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
	</c:otherwise>
	
	</c:choose>

</c:forEach>

</form:select>
<font class="mandatory">*</font>
</td>
<td width="25%"><form:errors path="employeeId" cssClass="error"></form:errors></td>
<td width="25%"></td>
</tr>


<tr>
<td>Designation</td>
<td align="left"><form:input path="designation" id="designation" name="designation" value="${employeeExperiences.designation}"/> <font class="mandatory">*</font> </td>
<td>Company</td>
<td align="left"><form:input path="company" id="company" name="company" value="${employeeExperiences.company}"/><font class="mandatory">*</font> </td>
</tr>

<tr>
<td></td>
<td align="left"><form:errors path="designation" cssClass="error"/>
<td></td>
<td align="left"><form:errors path="company" cssClass="error"/> </td>
</tr>


<tr>
<td>From Date</td>
<td align="left"><form:input  type="text" id="fromDate" name="fromDate" path="fromDate"
	value="${employeeExperiences.fromDate}" readonly="true" size="15"/> &nbsp;&nbsp;&nbsp;&nbsp; 
	<img id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'fromDate',-5,-130);"></img>
    </td>
<td>To Date</td>
<td align="left"><form:input type="text"  id="toDate" name="toDate" path="toDate"
	value="${employeeExperiences.toDate}" readonly="true" size="15"/> &nbsp;&nbsp;&nbsp;&nbsp; 
	<img id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2015);showCalender(this, 'toDate',-5,-130);"></img>
    </td>
</tr>



<tr>
<td>Address</td>
<td align="left" rowspan="3"><textarea rows="3" id="address" name="address">${employeeExperiences.address}</textarea>
<td>Country</td>
<td align="left"> <form:select path="country" onclick="showState(this.value);" style="width: 150px;"> 
    <c:forEach var="countryVar" items="${countries}">
    <c:choose>
	<c:when test="${employeeExperiences.country==countryVar.countryId}">
	 <form:option selected="selected" value="${countryVar.countryId}">${countryVar.country}</form:option>
	</c:when>
	
	<c:otherwise>
	<form:option value="${countryVar.countryId}">${countryVar.country}</form:option>
	</c:otherwise>    
    </c:choose>
   
    </c:forEach>
    </form:select>
    </td>
</tr>
<tr>
<td>&nbsp;<form:hidden path="state" id="oldState" name="oldState" value="${employeeExperiences.state}"/> </td>
<td>State</td>
<td align="left"><div id="statediv">
<form:select path="state" cssStyle=" width:150px;" onchange="showCities(this.value)">
<c:forEach items="${employeeState }" var="employeeState">
<c:choose>
<c:when test="${employeeExperiences.state==employeeState.stateId}">
<form:option selected="selected" value="${employeeState.stateId}">${employeeState.stateName}</form:option>

</c:when>
<c:otherwise>
<form:option value="${employeeState.stateId}">${employeeState.stateName}</form:option>

</c:otherwise>

</c:choose>

</c:forEach>
</form:select>

<form:hidden path="state" id="oldState" name="oldState" value="${employeeExperiences.state}"/>
    </div>
</td>
</tr>

<tr>
<td>&nbsp;</td>
<td>City</td>
<td><div id="showCitiesDiv" style="width: 150px;">

<form:select path="city" cssStyle=" width:150px;">
<c:forEach items="${employeeCity }" var="employeeCity">
<c:choose>
<c:when test="${employeeExperiences.city==employeeCity.cityId}">
<form:option selected="selected" value="${employeeCity.cityId}">${employeeCity.city}</form:option>

</c:when>
<c:otherwise>
<form:option value="${employeeCity.cityId}">${employeeCity.city}</form:option>

</c:otherwise>

</c:choose>

</c:forEach>
</form:select>

</div></td>
<td align="left"><form:hidden path="city" id="city" name="city" value="${employeeExperiences.city}"/> </td>
</tr>

<tr>
<td>Website</td>
<td align="left"><form:input path="website" id="website" name="website" value="${employeeExperiences.website}"/> </td>
<td align="left"><form:errors path="website" cssClass="error"/></td>
<td>&nbsp;</td>
</tr>

<tr>
<td>Salary</td>
<td align="left"><form:input path="salary" id="salary" name="salary" value="${employeeExperiences.salary}"/> </td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>

<tr>
<td>Attachment</td>
<td align="left"><input type="file" id="attachmentFile" name="attachmentFile"/> </td>
<td>&nbsp;<form:hidden path="attachment" id="oldfile" name="oldfile" value="${employeeExperiences.attachment}"/> </td>
<td>&nbsp;</td>
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
<td><input type="submit" name="save" id="save" value="Save"/></td>
</tr>
</table> --%>

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
</body>
</html>