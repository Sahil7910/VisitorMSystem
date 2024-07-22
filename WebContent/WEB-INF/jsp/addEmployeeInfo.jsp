<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>


<html>
<head>
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form name="check" method="POST" >
<div style="height:400px; width:99%; overflow-y:scroll"  id=maincontent>
	<table width="100%">
		
		
		<tr style="background-color: #333" >
			<th width="50px" style="color: white;">&nbsp;</th>
			<th style="color: white; ">ID</th>
			<th style="color: white; ">Additional Information</th>
		</tr>	
					
<c:forEach step="1" end="2" begin="1"   varStatus="index">
		<tr>
			<td align="center"><input type="radio" name="radionm" id="radioid" value="<c:out value="${index.index}"/>"/></td>
			<td>${index.index}</td>
			<c:if test="${index.index==1}">
			<td>Personal</td>
			</c:if>
			<c:if test="${index.index==2}">
			<td>Education</td>
			</c:if>
			
		</tr>
			</c:forEach>
		
	</table>

</div>

<table align="center" cellspacing="5" style="padding-left: 350px;">
	<tr>
		<td><input type="button" class=button name="method" value="ADD" onclick="return selectAddType(${employeeListSize},${EmpId});"/></td>
	</tr>
</table>
</form:form>
</body>
</html>