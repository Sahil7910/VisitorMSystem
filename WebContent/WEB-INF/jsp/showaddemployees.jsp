<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Job Roles</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>
</head>
<body>
	<script language="JavaScript" src="include/jquery.js"></script>
	<script language="JavaScript" src="include/jsfunctions.js"></script>
	<div id=maincontent>
		<form:form encType="multipart/form-data" method="POST"
			action="addEmployee.htm" commandName="employee">
			<table cellpadding=4 cellspacing=0 border=0 id="fields_block">
				<tr>
					<td width=150 style="padding-left: 0px;">Photo</td>
					<td width=250 style="padding-left: 0px;" align="left"><input
						type="file" id="photoFile" name="photoFile" /></td>
				</tr>

				<tr>
					<td>employeeId</td>
					<td><form:input path="employeeId" type="text" /></td>
					<td><form:errors path="employeeId" /></td>
				</tr>


				<%-- <tr>
					<td width=150 style="padding-left: 0px;">First Name</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="firstName" id="firstName" name="firstName" /></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Last Name</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="lastName" id="lastName" name="lastName" /></td>
				</tr>

 --%>
				<tr>
					<td width=150 style="padding-left: 0px;">Email</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="lastName" id="lastName" name="lastName" /></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Department</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="departmentId" id="departmentId" name="departmentId">
							<form:option value="0">Select</form:option>
							<c:forEach items="${departmentsList}" var="departmentsListVar">
								<form:option value="${departmentsListVar.id}"
									label="${departmentsListVar.name}">${departmentsListVar.name}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>


				<tr>
					<td width=150 style="padding-left: 0px;">Division</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="division" id="division" name="division">
							<form:option value="0">Select</form:option>
							<c:forEach items="${divisionList}" var="divisionListVar">
								<form:option value="${divisionListVar.divisionId}"
									label="${divisionListVar.divisionName}">${divisionListVar.divisionName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>




				<tr>
					<td width=150 style="padding-left: 0px;">Designation</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="Designation" id="Designation" name="Designation">
							<form:option value="0">Select</form:option>
							<c:forEach items="${designationList}" var="designationListVar">
								<form:option value="${designationListVar.id}"
									label="${designationListVar.designation}">${designationListVar.designation}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Supervisor</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="supervisor" id="supervisor" name="supervisor">
							<form:option value="0">Select</form:option>
							<c:forEach items="${employeeList}" var="employeeListVar">
								<form:option value="${employeeListVar.employeeId}"
									label="${employeeListVar.firstName} ${employeeListVar.lastName}">${employeeListVar.firstName} ${employeeListVar.lastName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Location</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="location" id="location" name="location">
							<form:option value="0">Select</form:option>
							<c:forEach items="${locationsList}" var="locationsListVar">
								<form:option value="${locationsListVar.id}"
									label="${locationsListVar.location}">${locationsListVar.location}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>



				<tr>
					<td width=150 style="padding-left: 0px;">Workspace</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="workspace" id="workspace" name="workspace">
							<form:option value="0">Select</form:option>
							<c:forEach items="${workspacesList}" var="workspacesListVar">
								<form:option value="${workspacesListVar.id}"
									label="${workspacesListVar.workspaceName}">${workspacesListVar.workspaceName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>

				<%-- <tr>
					<td width=150 style="padding-left: 0px;">Group Id</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="groupId" id="groupId" name="groupId" /></td>
				</tr> --%>


				<tr>
					<td width=150 style="padding-left: 0px;">Introduction</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:textarea
							path="introduction" id="introduction" name="introduction" /></td>
				</tr>

				<%-- <tr>
					<td width=150 style="padding-left: 0px;">Work Phone</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="workPhone" id="workPhone" name="workPhone" /></td>
				</tr> --%>

				<%-- <tr>
					<td width=150 style="padding-left: 0px;">Work Phone Ext</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="workPhoneExt" id="workPhoneExt" name="workPhoneExt" /></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Fax Phone</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="faxPhone" id="faxPhone" name="faxPhone" /></td>
				</tr> --%>

				<tr>
					<td width=150 style="padding-left: 0px;">Current Address</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="currentAddress" id="currentAddress" name="currentAddress" />
					</td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Current Address2</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="currentAddress2" id="currentAddress2"
							name="currentAddress2" /></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Current Country</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="currentCountry" id="currentCountry" name="currentCountry">
							<form:option value="0">Select</form:option>
							<c:forEach items="${countriesList}" var="countriesListVar">
								<form:option value="${countriesListVar.countryId}"
									label="${countriesListVar.country}">${countriesListVar.country}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>


				<%-- <tr>
    <td  width=150 style="padding-left:0px;">Current City</td>
    <td width=250  style="padding-left:0px;" align="left">
    <form:select path="currentCity" id="currentCity" name="currentCity">
    <form:option value="0">Select</form:option>
    <c:forEach items="${citiesList}" var="citiesListVar">
    <form:option value="${citiesListVar.cityId}" label="${citiesListVar.city}">${citiesListVar.city}</form:option>
    </c:forEach>
    </form:select>
    </td>
</tr>


<tr>
    <td  width=150 style="padding-left:0px;">Current State</td>
    <td width=250  style="padding-left:0px;" align="left">
    <form:select path="currentState" id="currentState" name="currentState">
    <form:option value="0">Select</form:option>
    <c:forEach items="${statesList}" var="statesListVar">
    <form:option value="${statesList.id}" label="${statesList.name}">${statesList.name}</form:option>
    </c:forEach>
    </form:select>
    </td>
</tr> --%>

				<tr>
					<td><div style="display: none;" id="stateLable">
							<font class="mandatory">*</font>State
						</div></td>
					<td align="left">
						<div id="statediv"></div>
					</td>
					<td></td>
				</tr>


				<tr>
					<td><div style="display: none;" id="cityLable">
							<font class="mandatory">*</font>City
						</div></td>
					<td align="left">
						<%-- <form:input path="studentCity"/> --%>
						<div id="showCitiesDiv"></div>
					</td>
					<td><div id="addNewButtonDiv" style="display: none;">
							<input type="button" value="Add new" onclick="addNewCityDiv();">
						</div></td>
					<td></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Current Postal Code</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="currentPostalCode" id="currentPostalCode"
							name="currentPostalCode" /></td>
				</tr>

				<tr>
					<td width=150 style="padding-left: 0px;">Mobile</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:input
							path="mobile" id="mobile" name="mobile" /></td>
				</tr>


				<%-- <tr>
					<td width=150 style="padding-left: 0px;">Job Code</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="jobCode" id="jobCode" name="jobCode">
							<form:option value="0">Select</form:option>
							<c:forEach items="${jobPositionsList}" var="jobPositionsListVar">
								<form:option value="${jobPositionsListVar.job_code}"
									label="${jobPositionsListVar.job_code}">${priorityListVar.job_code}</form:option>
							</c:forEach>
						</form:select></td>
				</tr> --%>

				<tr>
					<td width=150 style="padding-left: 0px;">Employment Status</td>
					<td width=250 style="padding-left: 0px;" align="left"><form:select
							path="employment_status" id="employment_status"
							name="employment_status">
							<form:option value="0">Select</form:option>
							<c:forEach items="${listsList}" var="listsListVar">
								<form:option value="${listsListVar.id}"
									label="${listsListVar.name}">${listsListVar.name}</form:option>
							</c:forEach>
						</form:select></td>	
				</tr>
				<tr>
					<td width=150 style="padding-left: 0px;">Created on</td>
					<td width=250 style="padding-left: 0px;" align="left"></td>
				</tr>


				<tr>
					<td width=150 style="padding-left: 0px;">Created By</td>
					<td width=250 style="padding-left: 0px;" align="left"></td>
				</tr>

				<tr>
					<td><img src="images/icon_required.gif"> - Required
						field</td>
					<td><input class=button type=submit value="Save"></td>
				</tr>
			</table>
			<!-- <div class="downedit" id="buttons_block">
 <div id="required_block" ><img src="images/icon_required.gif"> - Required field</div>
 <div class=buttonborder><input class=button type=submit value="Save"></>
</div> -->
		</form:form>
	</div>
</body>
</html>

