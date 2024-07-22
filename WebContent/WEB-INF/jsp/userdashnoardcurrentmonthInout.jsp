<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>Employee Profile</title>
<!-- <link href="css/user.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<link href="css/table_3.css" rel="stylesheet" type="text/css" />
</head>
<%!String punchInOutString = "";%>
<body>
	<div class="CSSTableGeneratorOutter">
		<div class="CSSTableGenerator">
			<table>
				<tr>
					<td>Date</td>
					<td>CheckIn</td>
					<td>CheckOut</td>
				</tr>
				<%-- <c:forEach items="${attendanceLogsBulkEntriesList}" var="attendanceLogsBulkEntriesListVar">
<tr>
<td>
${attendanceLogsBulkEntriesListVar.recordDate}
</td>
<c:set value="${attendanceLogsBulkEntriesListVar.timeAsPerShftTimings}" var="punchInOut"></c:set>
<%
punchInOutString=(String)pageContext.getAttribute("punchInOut");
String[] punchInOutStringArray=punchInOutString.split("~");
%>
<td >
<%=punchInOutStringArray[0]%>
</td>
<td >
<%
if(punchInOutStringArray[0].equals(punchInOutStringArray[punchInOutStringArray.length-1]))
{
%>No Punch<% 
}
else
{
	%><%=punchInOutStringArray[punchInOutStringArray.length-1]%><%
}
%>
</td>
</tr>
</c:forEach>
 --%>
 			<c:forEach items="${attendanceList}" var="attendanceListVar">
 				<tr>
			<td>
				${attendanceListVar.date}
			</td>
			<td>
				${attendanceListVar.in_Time}
				
			</td>
			<td>
				${attendanceListVar.out_Time}
				
			</td>
		</tr>
		</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
