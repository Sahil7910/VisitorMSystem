<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%!ResultSet resultSetObj=null; %>

<c:set var="resultsetVar" value="${resultsetModelMap}"></c:set>
<% 
resultSetObj=(ResultSet)pageContext.getAttribute("resultsetVar");
%>

<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
<table>
<tr>
<td>Name</td>
<td>Total Leaves</td>
<td>Leaves Used</td>
<td>Leaves Remaining</td>
<td>Extra Leaves</td>
</tr>
<%while(resultSetObj.next()) 
{
%>
<tr>
<td><%=resultSetObj.getString(1)%>&nbsp;<%=resultSetObj.getString(2)%></td>
<td><%=resultSetObj.getString(3)%></td>
<td><%=resultSetObj.getString(4)%></td>
<td><%=resultSetObj.getString(5)%></td>
<td><%=resultSetObj.getString(6)%></td>
</tr>
<%}%>
</table>
</div></div>


</html>