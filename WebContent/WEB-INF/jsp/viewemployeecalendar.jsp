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
<title>Employee List</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table>
		<tr>
			<!-- <th width="50px" style="color: white;">&nbsp;</th> -->
			<td>Id</td>
			<td>Employee No</td>
			<td>Employee</td>
			<td>Department</td>
			<td>Allocate Shift</td>
			<td>Allocated Shift</td>
		</tr>
		<c:forEach items="${employeeList}" var="employeeListVar" varStatus="index">
		<tr>
			<%-- <td align="center"><input type="radio" name="radionm" id="radioid" value="<c:out value="${employeeListVar.employeeId}"/>"/></td> --%>
			<td>${(index.index)+1}</td>
			<td>${employeeListVar.employeeNo}</td>
			<td>${employeeListVar.firstName} ${employeeListVar.lastName}</td>
			<td>
			<c:forEach items="${departmentsList}" var="departmentsListVar">
			<c:if test="${employeeListVar.departmentId==departmentsListVar.id}">${departmentsListVar.name}</c:if>
			</c:forEach>
			</td>
			<td><a href="#" onclick="return updateShiftDefinition('shiftAllocationSingleEmployee.htm','${employeeListVar.employeeId}','employee');"><font style="color:#808080; font-style: italic;">Allocate Shift:${employeeListVar.firstName} ${employeeListVar.lastName}</font></a></td>

			<td>
			<c:set value="true" var="blankVar"/>
			<c:forEach items="${allocatedShiftList}" var="allocatedShiftListVar">
										<c:if test="${employeeListVar.employeeId==allocatedShiftListVar.employee_id}">
											<c:forEach items="${shiftMasterList}" var="shiftMasterListVar">
												<c:if test="${shiftMasterListVar.shiftid==allocatedShiftListVar.shiftid}">
													${shiftMasterListVar.shiftname}
													<c:set value="false" var="blankVar"/>
												</c:if>
											</c:forEach>
										</c:if>
					
			</c:forEach>
			<c:if test="${blankVar!='false'}">
				Not Allocated
				</c:if>
			</td>
						
			
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<%-- <table>
	<tr>
		<td><input type="button" name="method" value="DELETE" onclick="return updateDelete(${shiftMasterListSize},'deleteShiftMaster.htm')"/></td><td><input type="button" name="method" value="UPDATE" onclick="return updateDelete(${shiftMasterListSize},'updateShiftMaster.htm');"/></td>
	</tr>
</table> --%>

</form:form>
</div>
</body>
</html>