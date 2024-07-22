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
<link href="css/table_3.css" rel="stylesheet" type="text/css" />
<title>Company List</title>
</head>
<body>
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
<form:form name="check" method="POST">
<div id="showMessage">
	<table >
		<tr >
			<td>From</td>
			<td>Subject</td>	
		</tr>
		<c:forEach items="${employeeMessagesList}" var="employeeMessagesListVar" varStatus="index">
		<tr>
		<td d>
			<c:forEach items="${EmployeeList}" var="employeeListVar">
			<c:if test="${employeeListVar.employeeId==employeeMessagesListVar.messageFrom}">
			${employeeListVar.firstName} ${employeeListVar.lastName}
			<c:if test="${employeeMessagesListVar.messageStatus eq false}"> <font color="red" size="1">Unread</font></c:if>
			</c:if>
			</c:forEach>
			</td>	
			<td><a href="#" onclick="return genericAjaxFunction('userDashboardShowMsg.htm?messageId=${employeeMessagesListVar.id}','POST','showMessage');"><font style="color:#808080; font-style: italic;">${employeeMessagesListVar.messageSubject}</font></a></td>
		 </tr>
		</c:forEach>
	</table>
</div>
</form:form>
</div>
</div>
</body>
</html>