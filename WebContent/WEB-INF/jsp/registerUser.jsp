<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form name="registerUser" commandName="employee" method="GET" action="submitRegisterUser.htm">
<table>

<tr>
<td>employeeId</td>
<td><form:input path="employeeId" type="text"/></td>
<td><form:errors path="employeeId"/></td>
</tr>

<tr>
<td>Email</td>
<td><form:input path="email" type="text"/></td>
<td><form:errors path="email"/></td>
</tr>
<tr>
<td>First Name</td>
<td><form:input path="firstName" type="text"/></td>
<td><form:errors path="firstName"/></td>
</tr>
<tr>
<td>Last Name</td>
<td><form:input path="lastName" type="text"/></td>
<td><form:errors path="lastName"/></td>
</tr>
<tr>
<td>Mobile No:</td>
<td><form:input path="mobile" type="text"/></td>
<td><form:errors path="mobile"/></td>
</tr>
<tr>
<td>Password</td>
<td><form:input path="password" type="text" name="password"/></td>
<td><form:errors path="password"/></td>
</tr>
<tr>
<td>User Type</td>
<td><form:select path="adminFlag">
<c:forEach items="${userList}" var="userList">
<form:option value="${userList.id}" label="${userList.userType}">${userList.userType}</form:option>
</c:forEach>
</form:select>
</td>
</tr>
<tr>
<td><input type="submit" value="Submit"/></td>
</tr>
<tr>
<td><a href="loginpage.htm">Go to Login</a></td>
</tr>

</table>
</form:form>
</body>
</html>