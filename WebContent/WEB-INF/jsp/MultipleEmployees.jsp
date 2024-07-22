<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body id="public">
<c:forEach items="${EmployeeList}" var="EmployeeList">
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
</label>
<div>
<input type="checkbox" value="${EmployeeList.employeeNo}"/>&nbsp;&nbsp;&nbsp;${EmployeeList.firstName}  ${EmployeeList.lastName}
</div>
</li>

</c:forEach>
<%-- <table border="0" cellspacing="5" width="100%">
<c:forEach items="${EmployeeList}" var="EmployeeList">
<tr>
<td align="left"></td>
</tr>
</c:forEach>
</table> --%>
</body>
</html>