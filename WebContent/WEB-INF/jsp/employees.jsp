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
<title>Add Employee</title>

<!-- <style>
a {
  text-decoration: none;
  display: inline-block;
  padding: 8px 16px;
}

a:hover {
  background-color: #ddd;
  color: black;
}

.previous {
  background-color: #f1f1f1;
  color: black;
}

.next {
  background-color: #04AA6D;
  color: white;
}

.round {
  border-radius: 50%;
}
</style> -->

<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>
<script>
    function pageRedirect() {
    	
      window.location.href ="showEmployees1.htm";
    }      
</script>

</head>

<style>
.addbutton{
			width:70px;
			height:10px;
			font-size:16px;
			color:#fff;
			background:#980002;
			box-shadow:0px 0px 3px #000;
			font-family:calibri;
			}

</style>


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



<div id="container" class="ltr">


	<script language="JavaScript" src="include/jquery.js"></script>
	<script language="JavaScript" src="include/jsfunctions.js"></script>


	<form:form class="wufoo leftLabel page" encType="multipart/form-data"
		method="POST" action="addEmployees.htm" commandName="employee">

		<div style="width: 100%; background-color: black; color: red;"
			align="center">
			<font size="4">${EmployeeAddMsg}</font>
		</div>

		<ul>

			<li id="foli01" class="notranslate"><label class="desc"
				id="title01" for="Field23"> Photo </label>
				<div>
					<input type="file" id="photoFile" name="photoFile" />
				</div></li>

			<li id="foli02" class="notranslate"><label class="desc"
				id="title23" for="Field23"> Employee Id <span id="req_23"
					class="req">*</span></label>
				<div>

					<form:input class="field text medium" path="employeeId"
						id="employeeId" name="employeeId" maxlength="9"/>
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
					<form:input class="field text medium" path="lastName" id="lastName"
						name="lastName" />
				</div> <form:errors path="lastName" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> Email<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" path="email" id="email"
						name="email" />
				</div> <form:errors path="email" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> Password<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:password class="field text medium" path="password"
						id="password" name="password" />
				</div> <form:errors path="password" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> User Type<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:select path="adminFlag" class="field select medium">
						<form:option value="0">Select</form:option>
						<c:forEach items="${userList}" var="userList">
							<form:option value="${userList.id}" label="${userList.userType}">${userList.userType}</form:option>
						</c:forEach>
					</form:select>
				</div> <form:errors path="adminFlag" cssClass="error" /></li>

			<li id="foli02" class="notranslate "><label class="desc"
				id="title23" for="Field23"> Designation<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:select class="field select medium" path="designation"
						id="Designation" name="Designation">
						<form:option value="0">Select</form:option>
						<c:forEach items="${designationList}" var="designationListVar">
							<form:option value="${designationListVar.id}"
								label="${designationListVar.designation}">${designationListVar.designation}</form:option>
						</c:forEach>
					</form:select>
				</div> <form:errors path="designation" cssClass="error" /></li>

			<li id="foli02" class="notranslate"><label class="desc"
				id="title23" for="Field23"> Supervisor <%
				if (session.getAttribute("loginUser") != null && !session.getAttribute("loginUser").equals("admin")) {
				%> <span id="req_23" class="req">*</span> <%
 }
 %>
			</label>
				<div>
					<form:select class="field select medium" path="supervisor"
						id="supervisor" name="supervisor">
						<form:option value="0">Select</form:option>
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
						<form:option value="0">Select</form:option>
						<c:forEach items="${workspacesList}" var="workspacesListVar">
							<form:option value="${workspacesListVar.id}"
								label="${workspacesListVar.workspaceName}">${workspacesListVar.workspaceName}</form:option>
						</c:forEach>
					</form:select>
				</div> <form:errors path="workspace" cssClass="error" /></li>


			<div id="workspaceDiv"></div>


			<li id="foli02" class="notranslate"><label class="desc"
				id="title23" for="Field23"> Joining Date<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" type="text" id="joiningDate"
						value="<%=dateNow%>" name="joiningDate" path="joiningDate"
						readonly="true" />
					&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal"
						onClick="setYears(1950, 2050);showCalender(this, 'joiningDate',20,-100);"></img>
				</div> <form:errors path="workspace" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23"> Confirmation Date<span
					id="req_23" class="req">*</span>
			</label>
				<div>
					<form:input class="field text medium" type="text"
						id="confirmationDate" value="<%=dateNow%>" name="confirmationDate"
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
					<form:textarea class="field textarea medium" rows="10" cols="50"
						path="introduction" id="introduction" name="introduction" />
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
					<form:input class="field text medium" path="faxPhone" id="faxPhone"
						name="faxPhone" />
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
				id="title23" for="Field23"> Current Country<span id="req_23"
					class="req">*</span>
			</label>
				<div>
					<form:select class="field select medium" path="currentCountry"
						id="currentCountry"
						onchange="showState(this.value);document.getElementById('stateLable').style.display= 'block';">
						<form:option value="0">Select</form:option>
						<c:forEach var="country" items="${countries}">
							<form:option value="${country.countryId}">${country.country}</form:option>
						</c:forEach>
					</form:select>
				</div> <form:errors path="currentCountry" cssClass="error" /></li>

			<li id="foli02" class="notranslate      "><label
				style="display: none;" class="desc" id="stateLable" for="Field23">Current
					State<span id="req_23" class="req">*</span>
			</label>
				<div id="statediv"></div> <form:errors path="currentState"
					cssStyle="error" /></li>

			<li id="foli02" class="notranslate      "><label
				style="display: none;" class="desc" id="cityLable" for="Field23">Current
					City<span id="req_23" class="req">*</span>
			</label>
				<div id="showCitiesDiv"></div>
				<div id="addNewButtonDiv" style="display: none;">
					<input type="button" value="Add new" onclick="addNewCityDiv();">
				</div> <form:errors path="currentCity" cssStyle="error" /></li>



			<li id="foli02" class="notranslate"><label class="desc"
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

			<li id="foli02" class="notranslate"><label class="desc"
				id="title23" for="Field23"> Job Code </label>
				<div>
					<form:select class="field select medium" path="jobCode"
						id="jobCode" name="jobCode">
						<form:option value="0">Select</form:option>
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
						<form:option value="0">Select</form:option>
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

		</ul>



		<SPAN class=buttonborder><INPUT class="btTxt submit"	value="Save" type="submit"></SPAN>
 
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

