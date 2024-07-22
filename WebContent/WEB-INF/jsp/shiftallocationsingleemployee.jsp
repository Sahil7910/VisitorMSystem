<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head><title>Shift Allocation</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>
</head>
<body id="public">

<div id="container" class="ltr">
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
<form:form encType="multipart/form-data" method="POST" action="saveShiftAllocation.htm?idType=${idType}" commandName="shiftAllocation" class="wufoo leftLabel page">
<ul>
<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Department<span id="req_23" class="req">*</span>
</label>
<div>
<c:choose>
		<c:when test="${idType=='employee'}">
			<c:set var="onFocusDeptVal" value="this.blur();"></c:set>
				</c:when>
					<c:otherwise>
							<c:url var="departmentVal" value="0"></c:url>
					</c:otherwise>
	</c:choose> 
    <form:select path="department" id="department" name="department" value="${departmentVal}" onfocus="${onFocusDeptVal}" onchange="genericAjaxFunction('showDeptwiseEmployee.htm?deptid='+this.value,'POST','changeEmployeeByDepartment');"  class="select medium">
    						<c:if test="${departmentVal==0}">
								<form:option value="0">Select</form:option>
							</c:if>
							<c:forEach items="${departmentsList}" var="departmentsListVar">
								<c:choose>
									<c:when test="${selectedEmployeesDepartment.id==departmentsListVar.id}">
										<form:option value="${selectedEmployeesDepartment.id}"
											label="${departmentsListVar.name}"
											selected="selected">${departmentsListVar.name}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${departmentsListVar.id}"
											label="${departmentsListVar.name}">${departmentsListVar.name}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
    </form:select>
<form:errors path="department" cssClass="error"/>
</div>
</li>


<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Employee<span id="req_23" class="req">*</span>
</label>
<div id="changeEmployeeByDepartment">
<c:choose>
	<c:when test="${idType=='employee'}">
		<c:set var="onFocusEmpVal" value="this.blur();"></c:set>
	</c:when>
	<c:otherwise>
		<c:url var="employeeVal" value="0"></c:url>
	</c:otherwise>
</c:choose> 
<form:select path="employee_id" id="employee_id"
	name="employee_id" value="${employeeVal}" onfocus="${onFocusEmpVal}" class="select medium">
	<c:if test="${employeeVal==0}">
		<form:option value="0">select</form:option>
	</c:if>
	<c:forEach items="${employeeList}" var="employeeListVar">
		<c:choose>
			<c:when test="${employeeObj.employeeId==employeeListVar.employeeId}">
				<form:option value="${employeeListVar.employeeId}"
					label="${employeeListVar.firstName} ${employeeListVar.lastName}"
					selected="selected">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
			</c:when>
			<c:otherwise>
				<form:option value="${employeeListVar.employeeId}"
					label="${employeeListVar.firstName} ${employeeListVar.lastName}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</form:select>
<form:errors path="employee_id" cssClass="error"/>
</div>
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Shift Id<span id="req_23" class="req">*</span>
</label>
<div>	
<c:choose>
		<c:when test="${idType=='shift'}">
								<c:set var="onFocusVal" value="this.blur();"></c:set>
							</c:when>
							<c:otherwise>
								<c:url var="shiftVal" value="0"></c:url>
							</c:otherwise>
						</c:choose>
	<form:select path="shiftid" id="shiftid" name="shiftid" onfocus="${onFocusVal}" class="select medium">
	<c:if test="${shiftVal==0}">
	<form:option value="0">select</form:option>
	</c:if>
    <c:forEach items="${shiftMasterList}" var="shiftMasterListVar">
    <c:choose>
	<c:when test="${shiftMasterListVar.shiftid==shiftMasterObj.shiftid}">
 	<form:option value="${shiftMasterListVar.shiftid}" selected="selected">${shiftMasterListVar.shiftname}</form:option>
	</c:when>
	<c:otherwise>
	<form:option value="${shiftMasterListVar.shiftid}">${shiftMasterListVar.shiftname}</form:option>
	</c:otherwise>
	</c:choose>
    </c:forEach>
    </form:select>
     <form:errors path="shiftid" cssClass="error"/>
</div>
</li>	
				

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
Start Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="datefrom" name="datefrom" path="startdate"
	value="<%=dateNow%>" readonly="true" size="15" class="field text medium"/>
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'datefrom',-350,-270);"></img>
</div>
</li>

<li id="foli110" 
class="notranslate">
<label class="desc" id="title110" for="Field110">
End Date<span id="req_23" class="req">*</span>
</label>
<div>
<form:input type="text" id="dateTo" name="dateTo" path="enddate"
	value="<%=dateNow%>" readonly="true" size="15" class="field text medium"/> 
	<img
	id="dobImage1" src="images/cal.gif" alt="cal"
	onClick="setYears(1950, 2050);showCalender(this, 'dateTo',-350,-270);"></img>
	<form:errors path="enddate" cssClass="error"/>
</div>
</li>


   <li id="foli110" 
class="notranslate      ">
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

