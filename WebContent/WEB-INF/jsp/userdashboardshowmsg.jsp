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

	<table>
		<tr>
			<!-- 			<td >&nbsp;</td> -->
			<!-- 			<td >&nbsp;</td>			 -->
		</tr>
		<tr>
			<td>Subject-</td>
			<td>${employeeMessagesObj.messageSubject}</td>
		</tr>
		<tr>
			<td>Message-</td>
			<td>${employeeMessagesObj.messageBody}</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="button" name="back" value="Back" id="back"
				onclick="genericAjaxFunction('userDashnoardInbox.htm?employeeId=${loginUserEmployeeId}','POST','profileDiv');" /></td>
		</tr>
	</table>
</body>
</html>