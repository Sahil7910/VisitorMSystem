

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

<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="addOtherDetails.htm" method="POST" commandName="employee">


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
<label class="desc" id="stateLable" for="Field110" style="display: none;">
State
<span id="req_23" class="req">*</span>
</label>
<div id="statediv">
</div>
<form:errors path="state" id="state" name="state"/>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" for="Field110" style="display: none;" id="cityLable">
City
<span id="req_23" class="req">*</span>
</label>
<div id="showCitiesDiv"></div>
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
<span id="req_23" class="req">*</span>
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


























<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>


<%
String loginUser = (String) session.getAttribute("loginUser");
String createdOn = Calendar.getInstance().getTime().toString();
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

		<form:form encType="multipart/form-data" class="wufoo leftLabel page"
			action="addOtherDetails.jsp" method="POST" >




			<ul>
				<li id="foli110" class="notranslate"><label class="desc"
					id="title110" for="Field110"> Employee <span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select path="employeeId" class="field select medium">
							<form:option value="0">Select</form:option>
							<c:forEach items="${employeeList}" var="employee">
								<form:option value="${employee.employeeId}"
									label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="employeeId" cssClass="error" /></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110">Bank Name:<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="bankname" id="bankname" name="bankname"
							class="field text medium" />
					</div> <form:errors path="bankname" cssClass="error"></form:errors></li>
					
					
					
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110">bankBranchName:<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="bankBranchName" id="bankBranchName" name="bankBranchName"
							class="field text medium" />
					</div> <form:errors path="bankBranchName" cssClass="error"></form:errors></li>
					
					
					
					
					
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110">branchFullAddress:<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="branchFullAddress" id="branchFullAddress" name="branchFullAddress"
							class="field text medium" />
					</div> <form:errors path="branchFullAddress" cssClass="error"></form:errors></li>
					
					
					
					
					
					
					
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110">accountNo:<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="accountNo" id="accountNo" name="accountNo"
							class="field text medium" />
					</div> <form:errors path="accountNo" cssClass="error"></form:errors></li>
					
					
					
					
					
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110">IFSC No:<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="IFSCNo" id="accountNo" name="IFSCNo"
							class="field text medium" />
					</div> <form:errors path="IFSCNo" cssClass="error"></form:errors></li>
					
					
					
					
					
					
					
					
					
					
					
					
					


				<li id="foli110" class="notranslate"><label class="desc"
					id="title110" for="Field110">bankBranchName: </label>
					<div>
						<form:select path="bankBranchName;" id="bankBranchName;" name="bankBranchName;"
							class="field select medium">
						</form:select>
					</div></li>

				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Skill Level <span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select path="skillLevel" id="skillLevel" name="skillLevel"
							class="field select medium">
							<form:option value="0" label="select">Select</form:option>
							<form:option value="novice" label="Novice">Novice</form:option>
							<form:option value="intermediate" label="intermediate">Intermediate</form:option>
							<form:option value="expert" label="expert">Expert</form:option>
						</form:select>
					</div> <form:errors path="skillLevel" cssClass="error" /></li>



				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Experience <span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="experienceYear" id="experienceYear"
							name="experienceYear" class="field text medium" />
						Year(s)<span id="req_23" class="req">*</span>
					</div> <form:errors path="experienceYear" cssClass="error" /></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> </label>
					<div>
						<form:input path="experienceMonth" id="experienceMonth"
							name="experienceMonth" class="field text medium" />
						Month(s)<span id="req_23" class="req">*</span>
					</div> <form:errors path="experienceMonth" cssClass="error" /></li>


				<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="experienceMonth" id="experienceMonth" name="experienceMonth" class="field text medium"/> Month(s)
</div>
<form:errors path="experienceMonth" cssClass="error"/>
</li>

				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Description </label>
					<div>
						<form:textarea path="description" id="description"
							name="description" class="field textarea medium" rows="10"
							cols="50" />
					</div></li>

				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Certification </label>
					<div>
						<form:select path="certification" class="field select medium">
							<form:option value="0">Select</form:option>
							<form:option value="yes" label="Yes">Yes</form:option>
							<form:option value="no" label="No">No</form:option>
						</form:select>
					</div></li>

				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Attachment </label>
					<div>
						<input type="file" id="attachmentFile" name="attachmentFile" />
					</div></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Created By </label>
					<div>
						<form:label path="createdBy" value="<%=loginUser%>" /><%=loginUser%>
					</div></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Created On </label>
					<div>
						<form:label path="createdOn" value="${employeeSkills.createdOn}">${employeeSkills.createdOn}</form:label>
					</div></li>


				<li id="foli1" class="notranslate      "><label class="desc"
					id="title1" for="Field1"> <span id="req_23" class="req">*-Required
							Fields</span>
				</label>
					<div></div></li>


				<li class="buttons ">
					<div>
						<input class="btTxt submit" type="submit" name="save" id="save"
							value="Save" />
					</div>
				</li>
			</ul>

		</form:form>
	</div>
</body>
</html> --%>