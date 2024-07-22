<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<form:form commandName="employeePrivilege" name="employeePrivileges"
		id="employeePrivileges">
		<ul>
			<c:forEach items="${userPrivilegeList}" var="userPrivilegeList"
				varStatus="index">
				<li id="foli01" class="notranslate"><label class="desc"
					id="title01" for="Field23"></label>
					<div>
						<c:set var="checkedFlag" value=""></c:set>
						<c:forEach items="${employeePrivilegeSplitList}"
							var="employeePrivilegeSplitListVar">
							<c:if
								test="${employeePrivilegeSplitListVar eq userPrivilegeList.id}">
								<c:set var="checkedFlag" value="true"></c:set>
							</c:if>
						</c:forEach>
						<form:checkbox path="privilege" value="${userPrivilegeList.id}"
							id="${index.index}" name="userPrivilege" checked="${checkedFlag}" />
						&nbsp;&nbsp;${userPrivilegeList.privilege}
					</div></li>
			</c:forEach>

			<li id="foli01" class="notranslate"><label class="desc"
				id="title01" for="Field23"></label>
				<div>
					<input type="checkbox" value="selectAll" id="selectAll"
						onclick="selectOrDeselectAll(${userPrivilegeListSize});" />&nbsp;&nbsp;Select
					All
				</div></li>
		</ul>
	</form:form>
</body>
</html>