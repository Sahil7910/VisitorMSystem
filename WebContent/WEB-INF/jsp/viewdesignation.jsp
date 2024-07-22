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
<c:set var="flag" value="0"></c:set>
<c:set var="flag1" value="0"></c:set>
<c:set var="flag2" value="0"></c:set>
<div id=maincontent>
<form:form name="check" method="GET">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr >
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Designation</td>
			<td>Department Name</td>
			<td>Rank</td>
			<td>Supervisor Designation</td>
			<!-- <th style="color: white; ">Level</th> -->
			<td>ShortName</td>
		</tr>
		<c:forEach items="${designationList}" var="designation" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${designation.id}"/>"/></td>
			<td>${designation.id}</td>
			<td>${designation.designation}</td>
			<td>
				<c:forEach items="${departmentList}" var="departmentListVar">
					<c:if test="${designation.department_id==departmentListVar.id}">
					${departmentListVar.name}
					</c:if>
				</c:forEach>
			</td>
			<td>${designation.rank}</td>
			<td>
				<c:forEach items="${designationList}" var="designationListVar">
					<c:if test="${designationListVar.id==designation.parent_designation}">
					${designationListVar.designation}
					</c:if>
				</c:forEach>
			</td>
			<%-- <td>
				<c:forEach items="${levelList}" var="levelListVar">
					<c:if test="${levelListVar.id==designation.level}">
					${levelListVar.levelName}
					</c:if>
				</c:forEach>
			</td> --%>
			<td>${designation.shortname}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return deleteDepartment(${designationListSize},'deleteDesignation.htm','POST');"/><input type="button" name="method" style="background-color:lightgray; margin-left: 2%;" value="UPDATE" onclick="return deleteDepartment(${designationListSize},'showupdateDesiganation.htm','POST');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>