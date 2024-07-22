<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>


<html>
<head>
<style type="text/css">
body {
	background: url(images/Login_Background.png);
	background-size: 100% 600px;
	background-repeat: no-repeat;
}
.container td{
	font-color:red;
	}
	
	
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

			<form action="successful.htm" method="post">
				<%-- <%String card_no = (String) request.getSession().getAttribute("CID"); %> --%>
				
		
		<%-- <table align="center" class="content" style="font-family: Arial;">
			<tr>
				<td>&nbsp;&nbsp;Card Number :</td>
				<td>&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</td>
				<td><form:label path="cardNo" style="font-color:Red"><%=card_no%></form:label></td>
			</tr>
		
		 --%>	
		 
		 	<%String card_no=(String) request.getSession().getAttribute("CID"); %>
		 		
		 		<table class="container">
		 		<tr>
		 				<td bgcolor="green">Card Number:</td>
		 				<td bgcolor="red"><form:label path="cardNo" style="font-color:Red"><%=card_no%></form:label></td>
		 		</tr>
		 			
		 		</table>
		 		
		 
			</form>
	
</body>
</html>