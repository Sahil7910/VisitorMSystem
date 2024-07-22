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
<title>Designation</title>

<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="GET">
<div style="height:400px; width:99%; overflow-y:scroll">
	<table width="100%">
		<tr style="background-color: #333" >
			<th width="50px" style="color: white;">&nbsp;</th>
			<th style="color: white; ">Id</th>
			<th style="color: white; ">Job Code</th>
			<th style="color: white; ">Position Name</th>
			<th style="color: white; ">Designation</th>
			<th style="color: white; ">Description</th>
			
			
			
			
	 </tr>
		<c:forEach items="${jobPositionList}" var="jobPosition" varStatus="index">
		<tr>
			<td align="center"><input type="radio" name="radionm" id="radioid" value="<c:out value="${jobPosition.id}"/>"/></td>
			<td>${jobPosition.id}</td>
			<td>${jobPosition.job_code}</td>
			<td>${jobPosition.position_name}</td>
			
			<td align="center">
			<c:forEach items="${designations}" var="designationsVar">
			<c:if test="${designationsVar.id==jobPosition.designation}" >
				${designationsVar.designation}
			</c:if>
			</c:forEach>
			</td>
			
				<%--<c:set var="break" value ="true"/>
			<c:forEach items="${designationarr}" var="designationVar" varStatus="designationIndex">
				<c:if test="${break eq 'true'}">
					<c:if test="${designationIndex.index==index.index}">
						<c:forEach items="${designations}" var="designations" varStatus="desigIndex">
							<c:if test="${designationVar==designations.id}">
								<c:set var="flag" value="1"></c:set>
								<c:set var="designationName" value="${designations.designation}"></c:set>
								<c:set var="break" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			
			<%
				String flag=(String)pageContext.getAttribute("flag");
				String designationName=(String)pageContext.getAttribute("designationName");
				if(flag.equals("1"))
				{
			%>
		 	<td><font color="black"><%=designationName%></font></td>	 
			<%  } %>
			
			
			--%>
			<td>${jobPosition.description}</td>
			
		 </tr> 
		</c:forEach>
	</table>

</div>
<table align="center" cellspacing="8" style="padding-left: 350px;"  >
	<tr>
		<td ><input type="button" class=button name="method" value="DELETE" onclick="return deleteDepartment(${jobPositionListSize},'deleteJobPosition.htm','POST');"/></td>
		<td><input type="button" class=button name="method" value="UPDATE" onclick="return deleteDepartment(${jobPositionListSize},'showupdateJobPosition.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>