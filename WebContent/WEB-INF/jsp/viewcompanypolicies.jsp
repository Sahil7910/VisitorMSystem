<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company List</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Section</td>
			<td>Title</td>
			<td>Department</td>
			<td>Priority</td>
			<td>Location</td>
		</tr>
		<c:forEach items="${companyPoliciesList}" var="companyPoliciesListVar" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${companyPoliciesListVar.policyId}"/>"/></td>
			<td >${(index.index)+1}</td>
			<td >${companyPoliciesListVar.section}</td>
			<td>${companyPoliciesListVar.title}</td>
			<td >
			<c:forEach var="departmentsListVar" items="${departmentsList}">
         	<c:if test="${companyPoliciesListVar.departmentId==departmentsListVar.id}">
         	${departmentsListVar.name}
        	 </c:if>
         	</c:forEach>
         	</td>
        	
        	<td>
        	<c:forEach var="prioritiesListVar" items="${prioritiesList}">
        	 <c:if test="${companyPoliciesListVar.priorityId==prioritiesListVar.id}">
         	${prioritiesListVar.name}
        	 </c:if>
        	</c:forEach>
        	</td>
        
        <td >
        <c:forEach var="locationsListVar" items="${locationsList}">
         	<c:if test="${companyPoliciesListVar.locationId==locationsListVar.id}">
         	${locationsListVar.location}
        	 </c:if>
         </c:forEach>
         </td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return updateDelete(${companyPoliciesListSize},'deleteCompanyPolicies.htm')"/><input type="button" name="method" value="UPDATE" style="background-color:lightgray; margin-left: 2%;" onclick="return updateDelete(${companyPoliciesListSize},'updateCompanyPolicies.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>