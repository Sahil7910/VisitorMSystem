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
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Employee</td>
			<td>Department Id</td>
			<td>From Date</td>
			<td>To Date</td>
			<td>Duration</td>
			<td>Status</td>
			<td>Priority</td>
			<td>Leave Type</td>
			<td>Half Day Session</td>
			<td>Requested By</td>
			<td>Created On</td>
			
		</tr>
		<c:set var="break" value="true"/>
		<c:set var="flag" value="0"></c:set>
		<c:set var="FirstName" value=""></c:set>
		<c:set var="LastName" value=""></c:set>
		<c:set var="EmployeeNo" value="${zeroFlag}"></c:set>
		<c:forEach items="${leaveApplicationList}" var="leaveApplications" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${leaveApplications.id}"/>"/></td>
			<td>${leaveApplications.id}</td>
			<td>
			
          <%-- <c:set var="count" value="true"></c:set> --%>
			
			<c:forEach items="${employeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==leaveApplications.singleEmployeeId}">
			<%-- <c:if test="${count eq true}"> --%>
				${employeeListVar.firstName} ${employeeListVar.lastName}
				 <%-- <c:set var="count" value="false"></c:set> --%>
			</c:if>
				
			<%-- </c:if> --%>
			</c:forEach>
			 
			</td>
			
			<td>
			<c:forEach items="${deptList}" var="deptListVar">
			<c:if test="${deptListVar.id==leaveApplications.department_id}">
			${deptListVar.name}
			</c:if>
			</c:forEach>
			 
			</td>
			
			<td>${leaveApplications.from_date}</td>
			<td>${leaveApplications.to_date}</td>
			<td>${leaveApplications.duration}</td>
			
			<td>
			<c:forEach items="${statusList}" var="statusListVar">
			<c:if test="${statusListVar.id==leaveApplications.leaveStatus}">
			${statusListVar.status_name}
			</c:if>
			</c:forEach>
			 
			</td>
			
			<td>
			<c:forEach items="${prioritiesList}" var="prioritiesListVar">
			<c:if test="${prioritiesListVar.id==leaveApplications.priority}">
			${prioritiesListVar.name}
			</c:if>
			</c:forEach>
			 
			</td>
			
			
			<td>
			<c:forEach items="${leaveTypeList}" var="leaveTypeListVar">
			<c:if test="${leaveTypeListVar.id==leaveApplications.leavetype}">
			${leaveTypeListVar.name}
			</c:if>
			</c:forEach>
			 
			</td>
			
			<td>${leaveApplications.half_day_session}</td>
			<td>${leaveApplications.requested_by}</td>
			<td>${leaveApplications.created_on}</td>
			
			
			<%-- c:set var="break" value ="true"/>
			<c:forEach items="${employeearr}" var="employeeVar" varStatus="employeeVarIndex">
				<c:if test="${break eq 'true'}">
					<c:if test="${employeeVarIndex.index==index.index}">
						<c:forEach items="${EmployeeList}" var="employeeList" varStatus="EmployeeIndex">
							<c:if test="${employeeVar==employeeList.employeeNo}">
								<c:set var="flag" value="1"></c:set>
								<c:set var="FirstName" value="${employeeList.firstName}"></c:set>
								<c:set var="LastName" value="${employeeList.lastName}"></c:set>
								<c:set var="EmployeeNo" value="${employeeList.employeeNo}"></c:set>
								<c:set var="break" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			
			<%
				String flag=(String)pageContext.getAttribute("flag");
				String FirstName=(String)pageContext.getAttribute("FirstName");
				String LastName=(String)pageContext.getAttribute("LastName");
				 int EmployeeNo=(Integer)pageContext.getAttribute("EmployeeNo"); 
				/* String Employee=FirstName+" "+LastName+" ("+EmployeeNo+")"; */
				
				if(flag.equals("1"))
				{
			%>
		 	<td><font color="black"><%=FirstName%> <%=LastName%> (<%=EmployeeNo%>)</font></td>	 
			<%  } %>
			
			
		<c:set var="break2" value ="true"/>
			<c:forEach items="${deptarr}" var="deptVar" varStatus="deptVarIndex">
				<c:if test="${break2 eq 'true'}">
					<c:if test="${deptVarIndex.index==index.index}">
						<c:forEach items="${deptList}" var="deptList" varStatus="deptListIndex">
							<c:if test="${deptVar==deptList.id}">
								<c:set var="flag2" value="1"></c:set>
								<c:set var="deptName" value="${deptList.name}"></c:set>
								<c:set var="break2" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag2=(String)pageContext.getAttribute("flag2");
				String departmentName=(String)pageContext.getAttribute("deptName");
				if(flag2.equals("1"))
				{
			%>
		 	<td><font color="black"><%=departmentName%></font></td>	 
			<%  } %>
			
			<td>${leaveApplications.from_date}</td>
			<td>${leaveApplications.to_date}</td>
			<td>${leaveApplications.duration}</td>
			
				<c:set var="break1" value ="true"/>
			<c:forEach items="${statusarr}" var="statusVar" varStatus="statusVarIndex">
				<c:if test="${break1 eq 'true'}">
					<c:if test="${statusVarIndex.index==index.index}">
						<c:forEach items="${statusList}" var="statusList" varStatus="statusIndex">
							<c:if test="${statusVar==statusList.id}">
								<c:set var="flag1" value="1"></c:set>
								<c:set var="statusName" value="${statusList.status_name}"></c:set>
								<c:set var="break1" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag1=(String)pageContext.getAttribute("flag1");
				String statusName=(String)pageContext.getAttribute("statusName");
				if(flag1.equals("1"))
				{
			%>
		 	<td><font color="black"><%=statusName%></font></td>	 
			<%  } %>
			
			
			<c:set var="break3" value ="true"/>
			<c:forEach items="${priorityarr}" var="priorityVar" varStatus="priorityVarIndex">
				<c:if test="${break3 eq 'true'}">
					<c:if test="${priorityVarIndex.index==index.index}">
						<c:forEach items="${Priorities}" var="Priorities" varStatus="PriorityIndex">
							<c:if test="${priorityVar==Priorities.id}">
								<c:set var="flag3" value="1"></c:set>
								<c:set var="PriorityName" value="${Priorities.name}"></c:set>
								<c:set var="break3" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag3=(String)pageContext.getAttribute("flag3");
				String PriorityName=(String)pageContext.getAttribute("PriorityName");
				if(flag3.equals("1"))
				{
			%>
		 	<td><font color="black"><%=PriorityName%></font></td>	 
			<%  } %>
			
			<c:set var="break4" value ="true"/>
			<c:forEach items="${leavearr}" var="leaveVar" varStatus="leaveVarIndex">
				<c:if test="${break4 eq 'true'}">
					<c:if test="${leaveVarIndex.index==index.index}">
						<c:forEach items="${leaveTypeList}" var="leaveType" varStatus="leaveTypeIndex">
							<c:if test="${leaveVar==leaveType.id}">
								<c:set var="flag4" value="1"></c:set>
								<c:set var="leaveTypeName" value="${leaveType.name}"></c:set>
								<c:set var="break4" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag4=(String)pageContext.getAttribute("flag4");
				String leaveTypeName=(String)pageContext.getAttribute("leaveTypeName");
				if(flag4.equals("1"))
				{
			%>
		 	<td><font color="black"><%=leaveTypeName%></font></td>	 
			<%  } %> --%>
			
			
			
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
<tr>
	<td><input type="button" name="method" value="Allocate" onclick="return deleteDepartment(${leaveApplicationListSize},'AllocateLeaveApplication.htm','POST');"/></td>
</tr>
</table>

</form:form>
</div>
</body>
</html>