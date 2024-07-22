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
<div style="height:400px; width:99%; overflow-y:scroll">
	<table width="100%">
		<tr style="background-color: #333" >
			<th width="50px" style="color: white;">&nbsp;</th>
			<th style="color: white; ">Id</th>
			<th style="color: white; ">Nevel Name</th>
			<th style="color: white; ">Description</th>
			<th style="color: white; ">Rank</th>
		</tr>
		<c:forEach items="${designationLevelList}" var="designationLevelListVar" varStatus="index">
		<tr>
			<td align="center"><input type="radio" name="radionm" id="radioid" value="<c:out value="${designationLevelListVar.id}"/>"/></td>
			<td align="center">${(index.index)+1}</td>
			<td align="center">${designationLevelListVar.levelName}</td>
			<td align="center">${designationLevelListVar.description}</td>
			<td align="center">${designationLevelListVar.rank}</td>
		 </tr>
		</c:forEach>
	</table>

</div>
<table>
	<tr>
		<td><input type="button" name="method" value="DELETE" onclick="return updateDelete(${designationLevelListSize},'deleteDesignationLevel.htm')"/></td><td><input type="button" name="method" value="UPDATE" onclick="return updateDelete(${designationLevelListSize},'updateDesignationLevel.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>