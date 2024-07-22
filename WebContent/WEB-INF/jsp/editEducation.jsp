<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD align="left" width=150 HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
<title>Insert title here</title>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
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
String loginUser = (String) session.getAttribute("loginUser");
%>
<body>
<body id="public">
	<div id="container" class="ltr">
		<form:form class="wufoo leftLabel page" encType="multipart/form-data"
			commandName="education"
			action="addEducationDetails.htm?fromFlag=update" method="POST">

			<form:hidden path="emp_id" />
			<form:hidden path="id" />
			<ul>

				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Employee Id.</label>
					<div>
						<input class="field text medium" type="text" readonly="readonly"
							value="${employeeObj.employeeId}" id="employeeid"
							name="employeeid" />
					</div></li>


				<li id="foli23" class="notranslate"><label class="desc"
					id="title23" for="Field23"> Employee No.</label>
					<div>
						<input class="field text medium" type="text" readonly="readonly"
							value="${employeeObj.employeeNo}" />
					</div></li>

				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> First Name <span id="req_23"
						class="req">*</span></label>
					<div>
						<input class="field text medium" type="text" readonly="readonly"
							id="firstName" name="firstName" value="${employeeObj.firstName}" />
					</div></li>

				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Last Name <span id="req_23"
						class="req">*</span></label>
					<div>
						<input class="field text medium" type="text" id="lastName"
							name="lastName" readonly="readonly"
							value="${employeeObj.lastName}" />
					</div></li>

				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Qualification <span id="req_23"
						class="req">*</span></label>
					<div>
						<form:select class="field select medium" path="emp_education"
							name="value_qualification">
							<form:option value="">Select</form:option>
							<form:option value="10th">10th</form:option>
							<form:option value="12th">12th</form:option>
							<form:option value="Diploma">Diploma</form:option>
							<form:option value="Graduate">Graduate</form:option>
							<form:option value="UG">UG</form:option>
							<form:option value="PG">PG</form:option>
							<form:option value="PGDiploma">PGDiploma</form:option>
							<form:option value="Computer Course">Computer Course</form:option>
							<form:option value="Other">Other</form:option>
						</form:select>
					</div></li>




				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Course Name <span id="req_23"
						class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="course_name"
							name="value_course_name"></form:input>
					</div></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Specialization <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="specialisation"
							name="value_specialisation"></form:input>
					</div></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Board/University <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="board_university"
							name="value_board_university"></form:input>
					</div></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> College/Institute <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="college"
							name="value_college"></form:input>
					</div></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Address</label>
					<div>
						<form:textarea class="field textarea medium" path="address"
							name="value_address"></form:textarea>
					</div></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> College Country <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:select class="field select medium" path="edu_country"
							onchange="showState(this.value); document.getElementById('stateLable').style.display= 'block';"
							id="currentCountry">
							<c:forEach var="country" items="${countries}">
								<form:option value="${country.countryId}">${country.country}</form:option>
							</c:forEach>
						</form:select>
					</div></li>



				<li id="foli02" class="notranslate      "><label
					style="display: block;" class="desc" id="stateLable" for="Field23">
						College State <span id="req_23" class="req">*</span>
				</label>
					<div id="statediv">
						<select class="field select medium" name="state" id="state"
							onchange="showCities(this.value)">
							<c:forEach var="stateVar" items="${States}">
								<c:choose>
									<c:when test="${stateVar.stateId==education.edu_state}">
										<option value="${stateVar.stateId}" selected="selected">${stateVar.stateName}</option>
									</c:when>
									<c:otherwise>
										<option value="${stateVar.stateId}">${stateVar.stateName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div></li>

				<li id="foli02" class="notranslate      "><label
					style="display: block;" class="desc" id="cityLable" for="Field23">
						College City <span id="req_23" class="req">*</span>
				</label>
					<div id="showCitiesDiv">
						<select class="field select medium" name="city" id="city">
							<c:forEach items="${Cities}" var="cities">
								<c:choose>
									<c:when test="${cities.cityId==education.edu_city}">
										<option value="${cities.cityId}" selected="selected">${cities.city}</option>
									</c:when>
									<c:otherwise>
										<option value="${cities.cityId}">${cities.city}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div id="addNewButtonDiv" style="display: none;">
						<input type="button" value="Add new" onclick="addNewCityDiv();">
					</div></li>



				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Website </label>
					<div>
						<form:input class="field text medium" path="website"
							name="value_website"></form:input>
					</div></li>





				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Duration (in years) <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="duration"
							name="value_duration"></form:input>
					</div> <form:errors path="duration" cssStyle="color : red;" /></li>

				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> From <span id="req_23"
						class="req">*</span></label>
					<div>
						<form:input class="field text medium" type="text"
							id="education_duration_From" name="education_duration_From"
							path="from_date" readonly="true" size="15" />
						&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1950, 2050);showCalender(this, 'education_duration_From',20,-130);"></img>
					</div> <form:errors path="from_date" cssStyle="color : red;" /></li>





				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> To <span id="req_23"
						class="req">*</span></label>
					<div>
						<form:input class="field text medium" type="text"
							id="education_duration_To" name="education_duration_To"
							path="to_date" readonly="true" size="15" />
						&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1990, 2020);showCalender(this, 'education_duration_To',-5,-130);"></img>
					</div> <form:errors path="to_date" cssStyle="color : red;" /></li>



				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Aggregate Percentage <span
						id="req_23" class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="percentage"
							name="value_percentage"></form:input>
					</div> <form:errors path="percentage" cssStyle="color : red;" /></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Remarks</label>
					<div>
						<form:textarea class="field textarea medium" path="remarks"
							name="value_remarks"></form:textarea>
					</div></li>



				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Attachment</label>
					<div>
						<input type="file" id="attachmentFile" name="attachmentFile" />
					</div></li>

				<form:hidden path="attachment" name="value_attachment" />


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Created By</label>
					<div><%=loginUser%></div> <form:hidden path="created_by"
						value="<%=loginUser%>" /></li>


				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Created On </label>
					<div><%=dateNow%></div> <form:hidden path="created_on"
						value="<%=dateNow%>" /></li>


			</ul>


			<INPUT class="btTxt submit" value="Update" type="submit">
			<INPUT class="btTxt submit" value="Reset" type="reset">


		</form:form>
	</div>



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
				<td align="center" colSpan=2><SELECT id=selectYear
					style="width: 55px;"
					onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
					name=select></SELECT></td>
				<td align="center"><a onclick="closeCalender();"
					href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
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

</body>
</html>