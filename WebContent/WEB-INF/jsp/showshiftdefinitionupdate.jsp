<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!-- <script type="text/javascript" src="calendar/js/calendar.js"></script>
<link rel="stylesheet" href="calendar/css/calendar.css" type="text/css">
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
<form:form name="ShiftDefinition" commandName="shiftDefinition" method="POST" action="saveOrUpdateShiftDefinition.htm" class="wufoo leftLabel page">
<form:hidden path="id" id="shiftDefinitionId" name="shiftDefinitionId" readonly="true" value="${shiftDefinition.id}"/>
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift<span id="req_23" class="req">*</span>
</label>
<div>
<input type="text" id="shiftIdName" name="shiftIdName" readonly="readonly" value="${shiftMasterObj.shiftname}" class="field text medium" />
<form:hidden path="shiftid" id="shiftId" name="shiftId" readonly="true" value="${shiftMasterObj.shiftid}"/>
<form:errors path="shiftid" cssClass="error"/>
</div>
</li>


<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Location<span id="req_23" class="req">*</span>
</label>
<div>
<form:select path="location_id" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${locationList}" var="locationList" varStatus="status">
		<form:option value="${locationList.id}" label="${locationList.location}" />
		</c:forEach>
	</form:select>
<form:errors path="location_id" cssClass="error"/> 
</div>
</li>



<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Description
</label>
<div> 
<form:textarea path="description" id="description" class="field textarea medium"></form:textarea>
</div>
</li> 



<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Start Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="datefrom" name="datefrom" path="dateField" readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2030);showCalender(this, 'datefrom',-350,-270);"></img>
	<form:errors path="dateField" cssClass="error"/>
</div>
</li>



<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
End Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="dateTo" name="dateTo" path="endDate" readonly="true" size="15" class="field text medium"/> 
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1955, 2030);showCalender(this, 'dateTo',-350,-270);"></img>
	<form:errors path="endDate" cssClass="error"/>
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Start Time<span id="req_23" class="req">*</span>
</label>
<div>
	<form:select path="timeField" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
<form:errors path="timeField" cssClass="error"/> 
</div>
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
End Time<span id="req_23" class="req">*</span>
</label>
<div>
	<form:select path="endTime" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
<form:errors path="endTime" cssClass="error"/> 
</div>
</li>





<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Break1
</label>
<div>
<c:if test="${shiftDefinition.break1==true}">
<c:url value="checked" var="break1Check">
</c:url>
</c:if>
<form:checkbox path="break1" id="break1" checked="${break1Check}" onclick="enableDisableBreaks(this,'break1StartTime','break1EndTime');"/>
</div>
</li>



<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Start Time
</label>
<div>
<c:choose>
<c:when test="${shiftDefinition.break1==true}">
<c:set var="break1Disablecheck" value="false"></c:set>
</c:when>
<c:otherwise>
<c:set var="break1Disablecheck" value="true"></c:set>
</c:otherwise>
</c:choose>
<form:select path="break1StartTime" id="break1StartTime" disabled="${break1Disablecheck}" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
	</div>
	</li>
	
<li id="foli110" 
class="notranslate">	
<label class="desc" id="title110" for="Field110">
End Time
</label>	
<div>
<form:select path="break1EndTime" id="break1EndTime" disabled="${break1Disablecheck}" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
</div>
</li>





 
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Break2
</label>
<div>
<c:if test="${shiftDefinition.break2==true}">
<c:url value="checked" var="break2Check">
</c:url>
</c:if>
<form:checkbox path="break2" id="break2" checked="${break2Check}" onclick="enableDisableBreaks(this,'break2StartTime','break2EndTime');"/>
</div>
</li>
 


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Start Time
</label>
<div>
<c:choose>
<c:when test="${shiftDefinition.break2==true}">
<c:set var="break2Disablecheck" value="false"></c:set>
</c:when>
<c:otherwise>
<c:set var="break2Disablecheck" value="true"></c:set>
</c:otherwise>
</c:choose>
<form:select path="break2StartTime" id="break2StartTime" disabled="${break2Disablecheck}" class="field select medium">
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
</div>
	</li>
	
<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
End Time
</label>
<div>	
<form:select path="break2EndTime" id="break2EndTime" disabled="${break2Disablecheck}" class="field select medium"> 
		<form:option value="0" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
</div>
</li>





<li id="foli110" 
class="notranslate">
<c:if test="${shiftDefinition.weeklyOff1==true}">
<c:url value="checked" var="weeklyOff1Check">
</c:url>
</c:if>
<form:checkbox path="weeklyOff1" id="weeklyOff1" checked="${weeklyOff1Check}" onclick="enableDisableWeeklyOff(this,'weeklyOff1Day');"/>
<label class="desc" id="title110" for="Field110">
weekly Off 1
</label>
<div>
<c:choose>
<c:when test="${shiftDefinition.weeklyOff1==true}">
<c:set var="weeklyOff1Disablecheck" value="false"></c:set>
</c:when>
<c:otherwise>
<c:set var="weeklyOff1Disablecheck" value="true"></c:set>
</c:otherwise>
</c:choose>
<form:select path="weeklyOff1Day" id="weeklyOff1Day" disabled="${weeklyOff1Disablecheck}" class="field select medium">
		<form:option value="0" label="Select" />
		<form:option value="1" label="Sun" />
		<form:option value="2" label="Mon" />
		<form:option value="3" label="Tue" />
		<form:option value="4" label="Wed" />
		<form:option value="5" label="Thu" />
		<form:option value="6" label="Fri" />
		<form:option value="7" label="Sat" />
</form:select>

</div>
</li>

<li id="foli110" 
class="notranslate">
<c:if test="${shiftDefinition.weeklyOff2==true}">
<c:url value="checked" var="weeklyOff2Check">
</c:url>
</c:if>
<form:checkbox path="weeklyOff2" id="weeklyOff2" checked="${weeklyOff2Check}" onclick="enableDisableWeeklyOff(this,'weeklyOff2Day');"/>
<label class="desc" id="title110" for="Field110">
weekly Off 2
</label>
<div>
<c:choose>
<c:when test="${shiftDefinition.weeklyOff2==true}">
<c:set var="weeklyOff2Disablecheck" value="false"></c:set>
</c:when>
<c:otherwise>
<c:set var="weeklyOff2Disablecheck" value="true"></c:set>
</c:otherwise>
</c:choose>
<form:select path="weeklyOff2Day" id="weeklyOff2Day" disabled="${weeklyOff2Disablecheck}" class="field select medium">
		<form:option value="0" label="Select" />
		<form:option value="1" label="Sun" />
		<form:option value="2" label="Mon" />
		<form:option value="3" label="Tue" />
		<form:option value="4" label="Wed" />
		<form:option value="5" label="Thu" />
		<form:option value="6" label="Fri" />
		<form:option value="7" label="Sat" />
</form:select>
</div>
</li>

<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Punch Begin Before
</label>
<div>	
<form:select path="punchBeginBefore" id="punchBeginBefore" class="field select medium"> 
			<form:option value="00:00:00" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<c:if test="${fn:split(calTimesListVar.time,':')[0]=='00' || fn:split(calTimesListVar.time,':')[0]=='01' || fn:split(calTimesListVar.time,':')[0]=='02'  || fn:split(calTimesListVar.time,':')[0]=='03' || fn:split(calTimesListVar.time,':')[0]=='04' && fn:split(calTimesListVar.time,':')[1]=='00'}">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:if>
		</c:forEach>
	</form:select>
</div>
</li>


<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Punch End After
</label>
<div>	
<form:select path="punchEndAfter" id="punchEndAfter" class="field select medium"> 
		<form:option value="00:00:00" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<c:if test="${fn:split(calTimesListVar.time,':')[0]=='00' || fn:split(calTimesListVar.time,':')[0]=='01' || fn:split(calTimesListVar.time,':')[0]=='02'  || fn:split(calTimesListVar.time,':')[0]=='03' || fn:split(calTimesListVar.time,':')[0]=='04' && fn:split(calTimesListVar.time,':')[1]=='00'}">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:if>
		</c:forEach>
	</form:select>
</div>
</li>


<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Punch Grace Time
</label>
<div>	
<form:select path="graceTime" id="graceTime" class="field select medium"> 
		<form:option value="00:00:00" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<c:if test="${fn:split(calTimesListVar.time,':')[0]=='00' || fn:split(calTimesListVar.time,':')[0]=='01' || fn:split(calTimesListVar.time,':')[0]=='02'  || fn:split(calTimesListVar.time,':')[0]=='03' || fn:split(calTimesListVar.time,':')[0]=='04' && fn:split(calTimesListVar.time,':')[1]=='00'}">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:if>
		</c:forEach>
	</form:select>
</div>
</li>


<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Partial Day
</label>
<div>	
<form:select path="partialDay" id="partialDay" class="field select medium">
		<form:option value="0" label="Select" />
		<form:option value="1" label="Sun" />
		<form:option value="2" label="Mon" />
		<form:option value="3" label="Tue" />
		<form:option value="4" label="Wed" />
		<form:option value="5" label="Thu" />
		<form:option value="6" label="Fri" />
		<form:option value="7" label="Sat" />
</form:select>
</div>
</li>

<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Partial Day Start Time
</label>
<div>	
<form:select path="partialDayStartTime" id="partialDayStartTime" class="field select medium"> 
		<form:option value="00:00:00" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
</div>
</li>

<li id="foli110" 
class="notranslate">		
<label class="desc" id="title110" for="Field110">
Partial Day End Time
</label>
<div>	
<form:select path="partialDayEndTime" id="partialDayEndTime" class="field select medium"> 
		<form:option value="00:00:00" label="Select" />
		<c:forEach items="${calTimesList}" var="calTimesListVar" varStatus="status">
		<form:option value="${calTimesListVar.time}" label="${calTimesListVar.time}" />
		</c:forEach>
	</form:select>
</div>
</li>

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