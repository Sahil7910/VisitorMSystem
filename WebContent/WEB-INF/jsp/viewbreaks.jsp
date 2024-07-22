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
<title>Breaks</title>

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
			<td>Employee No.</td>
			<td>Requested By</td>
			<td>Title</td>
			<td>Date</td>
			<td>Duration</td>
			<td>Status</td>
			<td>Compensation Date</td>
			
		</tr>
		<c:forEach items="${breakList}" var="breaks" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${breaks.id}"/>"/></td>
			<td>${breaks.id}</td>
			<td>${breaks.employee_no}</td>
			<td>${breaks.requested_by}</td>
			<td>${breaks.title}</td>
			<td>${breaks.date}</td>
			<td>${breaks.duration}</td>
			
			
			
			<c:set var="break" value ="true"/>
			<c:forEach items="${statusarr}" var="statusVar" varStatus="statusVarIndex">
				<c:if test="${break eq 'true'}">
					<c:if test="${statusVarIndex.index==index.index}">
						<c:forEach items="${statusList}" var="status" varStatus="statusIndex">
							<c:if test="${statusVar==status.id}">
								<c:set var="flag" value="1"></c:set>
								<c:set var="statusName" value="${status.status_name}"></c:set>
								<c:set var="break" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			
			<%
				String flag=(String)pageContext.getAttribute("flag");
				String statusName=(String)pageContext.getAttribute("statusName");
				if(flag.equals("1"))
				{
			%>
		 	<td><font color="black"><%=statusName%></font></td>	 
			<%  } %>
			
			
			<td>${breaks.compensation_date}</td>
			
		 </tr>
		</c:forEach>
	</table>

</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" class=button name="method" value="DELETE" onclick="return deleteDepartment(${breakListSize},'deleteBreaks.htm','POST');"/>
		<input type="button" class=button name="method" value="UPDATE" onclick="return deleteDepartment(${breakListSize},'showupdateBreaks.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>