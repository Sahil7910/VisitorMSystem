<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visitor types</title>
</head>
<body>
<select id="purposeTemp" name="purposeTemp" class="field select medium" onchange="document.getElementById('purpose').value=this.value">
	<option value="0" label="Select">Select</option>
		<c:forEach items="${visitorPurposeList}" var="visitorPurposeListVar">
		<option value="${visitorPurposeListVar.purpose}">${visitorPurposeListVar.purpose}</option>
		</c:forEach>
	</select>
	<input type="button" value="Add" class="btTxt submit" onclick="showTextBoxGeneric('visitorPurposeText','purposeDropdownDiv','visitorPurposeAddDiv');">
</body>
</html>