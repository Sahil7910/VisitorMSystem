<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>




<body id="public">
<div id="container" class="ltr">
<form action="" class="wufoo leftLabel page">

<!-- <body>
<div id="maincontent"> -->
<%!int scripletEmployeeListIndex=0; %>
<div style="overflow: auto; height: 150px; back">

<ul>

	<li id="foli01" class="notranslate      "><label class="desc"
		id="title01" for="Field23">Select Department <span id="req_23" class="req">*</span></label>
		
	<div>
	<select class="field select medium" id="department" name="department" onchange="genericAjaxFunction('displaydepartmentwiseemployeelistshift.htm?departmentId='+this.value,'POST','employeeListDiv')">
		<option value="0" label="Select">Select</option>
		<c:forEach items="${departmentList}" var="departmentListVar">
			<option value="${departmentListVar.id}" label="${departmentListVar.name}">${departmentListVar.name}</option>
		</c:forEach>
	</select>
	
	</div>
		

</li>
</ul>

<div id="employeeListDiv"></div>

<%-- <table width="100%">
<c:forEach items="${listOfEmployeeList}" var="listOfEmployeeListVar" varStatus="status">
				<c:set var="employeeListIndex" value="${status.index}" ></c:set>
				<%
				scripletEmployeeListIndex=(Integer)pageContext.getAttribute("employeeListIndex");
				if(scripletEmployeeListIndex%4==0){ %>
				<tr>
				<%} %>
						<td><a href="#" onclick="javascript:showMonthCalenderDiv('showMonthCalenderDiv.htm','showCalender','${listOfEmployeeListVar.employeeId}',0,0);"><font color="black">Employee :- ${listOfEmployeeListVar.firstName} ${listOfEmployeeListVar.lastName}</font></a>
						</td>
				<%
				if(scripletEmployeeListIndex%4==3){ %>
					</tr>
				<%} %>		
				</c:forEach>
				</table> --%>
</div>
			
<div id="showCalender">
</div>
</form>
</div>

</body>
</html>