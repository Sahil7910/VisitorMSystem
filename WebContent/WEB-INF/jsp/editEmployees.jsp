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

<head>
<title>Edit Employees</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>

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

<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>
<body id="public">
	<div id="container" class="ltr">
		<form:form class="wufoo leftLabel page" encType="multipart/form-data"
			method="POST" action="saveOrUpdateEmployee.htm?Id=${Id}"
			commandName="employeeObj">
			<div style="width: 100%; color: red;" align="center">
				<font size="4">${EmployeeAddMsg}</font>
			</div>
			<form:hidden path="photo" />
			<form:hidden path="employeeNo" />
			<form:hidden path="dateOfBirth" />
			<form:hidden path="fatherhusbandName" />
			<form:hidden path="homePhone" />
			<form:hidden path="homeEmail" />
			<form:hidden path="personalEmails" />
			<form:hidden path="childrenCount" />
			<form:hidden path="emergencyContact" />
			<form:hidden path="relativesFriends" />
			<form:hidden path="languages" />
			<form:hidden path="skills" />
			<form:hidden path="education" />
			<form:hidden path="passingYear" />
			<form:hidden path="collegeName" />
			<form:hidden path="universityName" />
			<%-- <form:hidden path="currentAddress"/> --%>
			<form:hidden path="permanentAddress" />
			<form:hidden path="permanentAddress2" />
			<form:hidden path="permanentCountry" />
			<form:hidden path="permanentState" />
			<form:hidden path="permanentCity" />
			<form:hidden path="permanentPostalCode" />
			<form:hidden path="homeDistance" />
			<form:hidden path="onewayTime" />
			<form:hidden path="travelMode" />
			<form:hidden path="homeGpsLocation" />
			<form:hidden path="companyFriends" />
			<form:hidden path="allPhones" />
			<form:hidden path="rediffChatId" />
			<form:hidden path="gtalkChatId" />
			<form:hidden path="msnChatId" />
			<form:hidden path="yahooChatId" />
			<form:hidden path="skypeChatId" />
			<form:hidden path="gender" />
			<form:hidden path="employeeNo" />


			<form:hidden value="${employeeObj.employeeId}" path="employeeId"
				id="employeeid" name="employeeid" />

			<ul>
				<li id="foli01" class="notranslate      "><label class="desc"
					id="title01" for="Field23"> Photo </label>
					<div>
						<img alt="photo" src="${employeeObj.photo}" width="60px" />
					</div></li>

				<li id="foli01" class="notranslate      "><label class="desc"
					id="title01" for="Field23"> Photo </label>
					<div>
						<input type="file" id="photoFile" name="photoFile" />
					</div></li>


				<%-- <li id="foli02" class="notranslate      "><label class="desc"
		id="title23" for="Field23"> Device Id <span id="req_23"
						class="req">*</span></label>
		<div>
   			<form:input class="field text medium" path="deviceEmployeeNo" id="deviceEmployeeNo" name="deviceEmployeeNo"  readonly="true"/>
    </div>
		<form:errors path="deviceEmployeeNo" cssClass="error"/>
	</li> --%>

				<li id="foli02" class="notranslate"><label class="desc"
					id="title23" for="Field23"> Employee Id <span id="req_23"
						class="req">*</span></label>
					<div>

						<form:input class="field text medium" path="employeeId"
							id="employeeId" name="employeeId" />
					</div> <form:errors path="employeeId" cssClass="error" /></li>



				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> First Name<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input class="field text medium" path="firstName"
							id="firstName" name="firstName" />
					</div> <form:errors path="firstName" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Last Name<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input class="field text medium" path="lastName"
							id="lastName" name="lastName" />
					</div> <form:errors path="lastName" cssClass="error" /></li>


				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Email<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input class="field text medium" path="email" id="email"
							name="email" />
					</div> <form:errors path="email" cssClass="error" /></li>

				<%-- <tr>
<td align="left"width=250  style="padding-left:0px;" align="left"><input type="button" value="Change Password"  onclick="showPasswordWindow();"></td>
<td align="left"align="left" width=250 ></td>
 <td><div id="passwordDiv" style="display: none;">
 <table>
 <tr><td>Old Password</td><td><input type="password" id="oldPassword" name="oldPassword" /></td></tr>
 <tr><td>New Password</td><td><input type="password" id="newPassword" name="newPassword"/></td></tr>
 <tr><td>Confirm Password</td><td><input type="password" id="confirmPassword" name="confirmPassword"/></td></tr>
 <tr>
	<td><input class=button type=button value="Save" onclick="savePasswordEmp('${empPassword}','saveEmpPassword.htm?Id=${Id}');"></td>
	<td><input class=button type="button" value="Cancel" onclick="canclePass();"></td>
	</tr>
 </table>
 </div>
 <form:hidden path="password"/>
 </td>
</tr> --%>
				<form:hidden path="password" />
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> </label>
					<div>
						<input type="button" value="Change Password"
							onclick="showPasswordWindow();">
					</div></li>



				<div id="passwordDiv" style="display: none;">
					<ul>
						<li id="foli110" class="notranslate      "><label
							class="desc" id="title110" for="Field110"> Old Password <span
								id="req_23" class="req">*</span>
						</label>
							<div>
								<input type="password" id="oldPassword" name="oldPassword"
									class="field text medium" />
							</div></li>

						<li id="foli110" class="notranslate      "><label
							class="desc" id="title110" for="Field110"> New Password <span
								id="req_23" class="req">*</span>
						</label>
							<div>
								<input type="password" id="newPassword" name="newPassword"
									class="field text medium" />
							</div></li>

						<li id="foli110" class="notranslate      "><label
							class="desc" id="title110" for="Field110"> Confirm
								Password <span id="req_23" class="req">*</span>
						</label>
							<div>
								<input type="password" id="confirmPassword"
									name="confirmPassword" class="field text medium" />
							</div></li>

						<li id="foli110" class="notranslate      "><label
							class="desc" id="title110" for="Field110"> </label>
							<div>
								<input type=button value="Save"
									onclick="savePasswordEmp('${empPassword}','saveEmpPassword.htm?Id=${Id}');"><input
									type="button" value="Cancel" onclick="canclePass();">
							</div></li>
					</ul>
				</div>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> User Type<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select class="field select medium" path="adminFlag">
							<c:forEach items="${userList}" var="userList">
								<form:option value="${userList.id}" label="${userList.userType}">${userList.userType}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="adminFlag" cssClass="error" /></li>


				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Designation<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select class="field select medium" path="designation"
							id="Designation" name="Designation">
							<form:option value="0">--Select--</form:option>
							<c:forEach items="${designationList}" var="designationListVar">

								<form:option value="${designationListVar.id}"
									label="${designationListVar.designation}">${designationListVar.designation}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="designation" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Supervisor <%
				if (session.getAttribute("loginUser") != null && !session.getAttribute("loginUser").equals("admin")) {
				%> <span id="req_23" class="req">*</span> <%
 }
 %>
				</label>
					<div>
						<form:select class="field select medium" path="supervisor"
							id="supervisor" name="supervisor">
							<form:option value="0">--Select--</form:option>
							<c:forEach items="${employeeList}" var="employeeListVar">
								<form:option value="${employeeListVar.employeeId}"
									label="${employeeListVar.firstName} ${employeeListVar.lastName}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="supervisor" cssClass="error" /></li>



				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Workspace<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select class="field select medium" path="workspace"
							id="workspace" name="workspace"
							onchange="genericAjaxFunction('showWorkspaceTree.htm?workspaceId='+this.value,'POST','workspaceDiv');">
							<form:option value="0">--Select--</form:option>
							<c:forEach items="${workspacesList}" var="workspacesListVar">
								<form:option value="${workspacesListVar.id}"
									label="${workspacesListVar.workspaceName}">${workspacesListVar.workspaceName}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="workspace" cssClass="error" /></li>




				<div id="workspaceDiv">
					<ul>
						<li id="foli02" class="notranslate      "><label class="desc"
							id="title23" for="Field23">Location</label>
							<div>
								<input type="text" class="field text medium" id="locationName"
									readonly="readonly" value="${location.location}"
									style="background: transparent;" />
							</div></li>
						<li id="foli02" class="notranslate      "><label class="desc"
							id="title23" for="Field23"> Division: </label>
							<div>
								<input type="text" class="field text medium" id="divisionName"
									readonly="readonly" value="${division.divisionName}"
									style="background: transparent;" />
							</div></li>
						<li id="foli02" class="notranslate      "><label class="desc"
							id="title23" for="Field23"> Department: </label>

							<div>
								<input type="text" class="field text medium" id="departmentName"
									readonly="readonly" value="${department.name}"
									style="background: transparent;" />
							</div></li>
				</div>




				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Joining Date<span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input class="field text medium" type="text" id="joiningDate"
							name="joiningDate" path="joiningDate" readonly="true" />
						&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1950, 2050);showCalender(this, 'joiningDate',20,-100);"></img>
					</div> <form:errors path="workspace" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Confirmation Date<span
						id="req_23" class="req">*</span>
				</label>
					<div>
						<form:input class="field text medium" type="text"
							id="confirmationDate" name="confirmationDate"
							path="confirmationDate" readonly="true" />
						&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal"
							onClick="setYears(1950, 2050);showCalender(this, 'confirmationDate',20,-100);"></img>
					</div> <form:errors path="workspace" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Group Id</label>
					<div>
						<form:input class="field text medium" path="groupId" id="groupId"
							name="groupId" />
					</div></li>


				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Introduction </label>
					<div>
						<form:textarea class="field textarea medium" path="introduction"
							id="introduction" name="introduction" />
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Work Phone</label>
					<div>
						<form:input class="field text medium" path="workPhone"
							id="workPhone" name="workPhone" />
					</div> <form:errors path="workPhone" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Work Phone Ext</label>
					<div>
						<form:input class="field text medium" path="workPhoneExt"
							id="workPhoneExt" name="workPhoneExt" />
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Fax Phone</label>
					<div>
						<form:input class="field text medium" path="faxPhone"
							id="faxPhone" name="faxPhone" />
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Address Line 1</label>
					<div>
						<form:input class="field text medium" path="currentAddress"
							id="currentAddress" name="currentAddress" />
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Address Line 2</label>
					<div>
						<form:input class="field text medium" path="currentAddress2"
							id="currentAddress2" name="currentAddress2" />
					</div></li>


				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Current Country<span
						id="req_23" class="req">*</span>
				</label>
					<div>
						<form:select class="field select medium" path="currentCountry"
							onchange="showState(this.value);">
							<c:forEach var="countryVar" items="${countriesList}">
								<form:option value="${countryVar.countryId}">${countryVar.country}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="currentCountry" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label
					style="display: block;" class="desc" id="stateLable" for="Field23">Current
						State<span id="req_23" class="req">*</span>
				</label>
					<div id="statediv">
						<select class="field select medium" name="state" id="state"
							onchange="showCities(this.value)">
							<c:forEach var="stateVar" items="${States}">
								<c:choose>
									<c:when test="${stateVar.stateId==employeeObj.currentState}">
										<option value="${stateVar.stateId}" selected="selected">${stateVar.stateName}</option>
									</c:when>
									<c:otherwise>
										<option value="${stateVar.stateId}">${stateVar.stateName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div> <form:errors path="currentState" cssStyle="error" /></li>



				<li id="foli02" class="notranslate      "><label
					style="display: block;" class="desc" id="cityLable" for="Field23">Current
						City<span id="req_23" class="req">*</span>
				</label>
					<div id="showCitiesDiv">
						<select class="field select medium" name="city" id="city">
							<c:forEach items="${Cities}" var="cities">
								<c:choose>
									<c:when test="${cities.cityId==employeeObj.currentCity}">
										<option value="${cities.cityId}" selected="selected">${cities.city}</option>
									</c:when>
									<c:otherwise>
										<option value="${cities.cityId}">${cities.city}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div id="addNewButtonDiv" style="display: block;">
						<input type="button" value="Add new" onclick="addNewCityDiv();">
					</div> <form:errors path="currentCity" cssStyle="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Postal Code<span id="req_23"
						class="req">*</span></label>
					<div>
						<form:input class="field text medium" path="currentPostalCode"
							id="currentPostalCode" name="currentPostalCode" />
					</div> <form:errors path="currentPostalCode" cssClass="error" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Mobile</label>
					<div>
						<form:input class="field text medium" path="mobile" id="mobile"
							name="mobile" />
					</div> <form:errors path="mobile" cssClass="error" /></li>


				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Job Code </label>
					<div>
						<form:select class="field select medium" path="jobCode"
							id="jobCode" name="jobCode">
							<form:option value="0">--Select--</form:option>
							<c:forEach items="${jobPositionsList}" var="jobPositionsListVar">
								<form:option value="${jobPositionsListVar.job_code}"
									label="${jobPositionsListVar.job_code}">${priorityListVar.job_code}</form:option>
							</c:forEach>
						</form:select>
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Employment Status </label>
					<div>
						<form:select class="field select medium" path="employment_status"
							id="employment_status" name="employment_status">
							<form:option value="0">--Select--</form:option>
							<c:forEach items="${statusList}" var="listsListVar">
								<form:option value="${listsListVar.id}">${listsListVar.status_name}</form:option>
							</c:forEach>
						</form:select>
					</div></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Created By</label>
					<div>
						<label class="desc" id="title23" for="Field23"><%=loginUser%></label>
					</div> <form:hidden path="createdBy" value="<%=loginUser%>" /></li>

				<li id="foli02" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Created On</label>
					<div>
						<label class="desc" id="title23" for="Field23"><%=dateNow%></label>
					</div> <form:hidden path="createdOn" value="<%=dateNow%>" /></li>


				<li class="buttons ">
					<div>
						<input class="btTxt submit" value=Update type=submit>
					</div>
				</li>

			</ul>


		</form:form>


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
	</div>
</body>
</html>

