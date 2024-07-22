<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Allocation</title>

<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="GET">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">

	<table>
		<c:set var="flag1" value="0"></c:set>
		<c:set var="leaveTypeName" value=""></c:set>
		<c:set var="flag2" value="0"></c:set>
		<c:set var="statusName" value=""></c:set>
		
		
		
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Employee Name</td>
			<td>Status</td>
			<td>Leave Type</td>
			<td>No. of Leaves Allocated </td>
			<td>Approved FromDate</td>
			<td>Approved ToDate</td>
			<td>Expiration Date</td>						
		</tr>
		<c:forEach items="${leaveAllocationList}" var="leaveAllocations" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${leaveAllocations.id}"/>"/></td>
			<td>${leaveAllocations.id}</td>
	
			
			<c:forEach items="${EmployeeList}" var="employeeListVar">
			<c:if test="${leaveAllocations.employee_id==employeeListVar.employeeId}">
			<td>${employeeListVar.firstName}  ${employeeListVar.lastName} (${employeeListVar.employeeNo})</td>
			</c:if>
			</c:forEach>
			
			<c:set var="break2" value ="true"/>
			<c:forEach items="${statusarr}" var="statusVar" varStatus="statusVarIndex">
				<c:if test="${break2 eq 'true'}">
					<c:if test="${statusVarIndex.index==index.index}">
						<c:forEach items="${statusList}" var="statusList" varStatus="statusIndex">
							<c:if test="${statusVar==statusList.id}">
								<c:set var="flag2" value="1"></c:set>
								<c:set var="statusName" value="${statusList.status_name}"></c:set>
								<c:set var="break2" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag2=(String)pageContext.getAttribute("flag2");
				String statusName=(String)pageContext.getAttribute("statusName");
				if(flag2.equals("1"))
				{
			%>
		 	<td><font color="black"><%=statusName%></font></td>	 
			<%  } %>
			
			
			<c:set var="break1" value ="true"/>
			<c:forEach items="${leavearr}" var="leaveVar" varStatus="leaveVarIndex">
				<c:if test="${break1 eq 'true'}">
					<c:if test="${leaveVarIndex.index==index.index}">
						<c:forEach items="${leaveTypeList}" var="leaveType" varStatus="leaveTypeIndex">
							<c:if test="${leaveVar==leaveType.id}">
								<c:set var="flag1" value="1"></c:set>
								<c:set var="leaveTypeName" value="${leaveType.name}"></c:set>
								<c:set var="break1" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag1=(String)pageContext.getAttribute("flag1");
				String leaveTypeName=(String)pageContext.getAttribute("leaveTypeName");
				if(flag1.equals("1"))
				{
			%>
		 	<td><font color="black"><%=leaveTypeName%></font></td>	 
			<%  } %>
			
			<td>${leaveAllocations.value}</td>
			<td>${leaveAllocations.approved_fromdate}</td>
			<td>${leaveAllocations.approved_todate}</td>
			<td>${leaveAllocations.expiration_date}</td>
			
			
		 </tr>
		</c:forEach>
	</table>
</div>
</div>

<table style="width: 100%;  margin-top: 2%;" >
	<tr>
		<td ><input type="button" name="method" value="DELETE" onclick="return deleteDepartment(${leaveAllocationListSize},'deleteleaveAllocation.htm','POST');"/>
		<input type="button" name="method" value="UPDATE" onclick="return deleteDepartment(${leaveAllocationListSize},'showupdateleaveAllocation.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>