<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="saveMasterPrivileges.htm" method="POST">
<c:forEach items="${masterPrivilegesList}" var="masterPrivilegesListVar">
<c:choose>
<c:when test="${encryptedStatus eq masterPrivilegesListVar.privilegeStatus or masterPrivilegesListVar.privilegeStatus=='1'}">
<input type="checkbox" id="${masterPrivilegesListVar.privilegeName}" checked="checked" name="${masterPrivilegesListVar.privilegeName}" onchange="if(this.checked){this.value='1';}else{this.value='0';}">${masterPrivilegesListVar.privilegeName}
</c:when>
<c:otherwise>
<input type="checkbox" id="${masterPrivilegesListVar.privilegeName}" name="${masterPrivilegesListVar.privilegeName}" onchange="if(this.checked){this.value='1';}else{this.value='0';}">${masterPrivilegesListVar.privilegeName}
</c:otherwise>
</c:choose>

<br/>
</c:forEach>
<input type="submit" name="save" value="Save"/>
</form>
</body>
</html>