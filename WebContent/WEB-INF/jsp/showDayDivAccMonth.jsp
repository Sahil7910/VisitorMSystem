<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<select name="attendance" class="field select medium" onchange="document.getElementById('attendanceDay').value=this.value">
          <option value="0">--Select Date--</option>
          <c:forEach items="${calNumbersList}" var="calNumbersList">
          	<option value="${calNumbersList.n}">${calNumbersList.n}</option>	
          </c:forEach>
</select>
</body>
</html>