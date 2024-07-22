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
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Shift Code</td>
			<td>Shift Name</td>
			<td>Description</td>
			<td>Create Shift Definition</td>
			<td>Allocate Shift</td>		
		</tr>
		<c:forEach items="${shiftMasterList}" var="shiftMasterList" varStatus="index">
		<tr>
			<td><input type="radio" name="radionm" id="radioid" value="<c:out value="${shiftMasterList.shiftid}"/>"/></td>
			<td>${(index.index)+1}</td>
			<td>${shiftMasterList.shiftcode}</td>
			<td>${shiftMasterList.shiftname}</td>
			<td>${shiftMasterList.description}</td>
			<td><a href="#" onclick="return updateShiftDefinition('showShiftDefinitionUpdate.htm','${shiftMasterList.shiftid}','shift');"><font style="color:#808080; font-style: italic;">Shift:${shiftMasterList.shiftname}</font></a></td>
			<td><a href="#" onclick="return updateShiftDefinition('shiftAllocationSingleEmployee.htm','${shiftMasterList.shiftid}','shift');"><font style="color:#808080; font-style: italic;">Allocate Shift:${shiftMasterList.shiftname}</font></a></td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="DELETE" onclick="return updateDelete(${shiftMasterListSize},'deleteShiftMaster.htm')"/><input type="button" name="method" style="background-color:lightgray; margin-right: 2%;" value="UPDATE" onclick="return updateDelete(${shiftMasterListSize},'updateShiftMaster.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>