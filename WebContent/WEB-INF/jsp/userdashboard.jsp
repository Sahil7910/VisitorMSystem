<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>Employee Profile</title>
<!-- <link href="css/user.css" rel="stylesheet" type="text/css" /> -->
<link href="css/navigation.css" rel="stylesheet" type="text/css" />
<link href="css/drop_list.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/template1/calendar.css" type="text/css" />
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="calendar/js/calendar.js"></script>
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script>
<script type="text/javascript" src="javascript/jscolor.js"></script>
<script type="text/javascript" src="javascript/jquery-1.3.2.min.js"></script>




<%
Calendar calendar = Calendar.getInstance();
Date dateTo = calendar.getTime();
Calendar calendarFrom = Calendar.getInstance();
calendarFrom.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
Date dateFrom = calendarFrom.getTime();

int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
String weekDays[] = new DateFormatSymbols().getWeekdays();
weekDays[calendar.getFirstDayOfWeek()].toUpperCase();
DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
String dataFromString = dateFormat.format(dateFrom);
String dataToString = dateFormat.format(dateTo);
/*  calendar.clear();
 calendar.setTimeInMillis(timestamp); */
/*  while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
     calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
 }
 long firstDayOfWeekTimestamp = calendar.getTimeInMillis(); */
System.out.println(weekDays[calendar.getFirstDayOfWeek()].toUpperCase());
%>

</head>
<body onload="genericAjaxFunction('userDashboardVisitors.htm?employeeId=${loginUserEmployeeId}','POST','profileDiv');">

	<div id="wrapper">
		<!-- <div id="header"><font color="#FFFFFF" size="+1"><span style="margin-left:30px; ">HEADER</span></font></div> -->
		<img src="images/sensedgelogo.png" align="left"
			style="padding-top: 30px;" />
		<div style="float: right; margin-right: 10px; margin-top: 40px;">

			<a href="loginpage.htm" style="text-decoration: none;"><font
				color="white" size="4">Logout</font></a>
		</div>
		<div id="box">
			<!--  <div id="nav"> -->
			<div class="nav">
				<table class="navTable" border="0" width="100%">
					<tr>
						<td align="center"><nav>
							<ul>

								<li><a href="#"
									onclick="home('userDashboardHome.htm','GET');">Home</a></li>
								
								<li><a href="#"
									onclick="genericAjaxFunction('userDashboardallowed.htm?employeeId=${loginUserEmployeeId}','POST','profileDiv');">Meetings
										
								</a></li>
							</ul>
							</nav></td>
					</tr>
				</table>

			</div>
			<!--    </div> -->
			<!-- nav div ends-->
		</div>

		<!-----------     box div ends------------>


		<div class="content" style="margin-left: 150px">
			<div class="cont1" id="profileDiv"
				style="overflow: auto; font-family: Arial;"></div>
			<%-- <div class="cont2" style="font-family: Arial; font-size: 13px;">

				<div class="bday">
					<table style="width: 100%;">
						<tr>
							<td colspan="2" bgcolor="#666666" style="color: white;">Birthdays</td>
						</tr>
						<tr>
							<td width="50%" height="21">Name</td>
							<td width="50%">Birthday</td>
						</tr>
						<c:forEach items="${monthsBirthdayList}"
							var="monthsBirthdayListVar">
							<tr>
								<td width="50%" height="21">${monthsBirthdayListVar.firstName}
									${monthsBirthdayListVar.lastName}</td>
								<c:set value="${monthsBirthdayListVar.dateOfBirth}"
									var="listDate"></c:set>
								<c:set value="${currentDate}" var="todaysDate"></c:set>
								<td width="50%">
									<%
									String listDateString = (String) pageContext.getAttribute("listDate");
									String todaysDateString = (String) pageContext.getAttribute("todaysDate");
									if (listDateString.substring(0, 6).equals(todaysDateString.substring(0, 6))) {
									%> <blink>Today</blink> <%
 } else {
 %> ${monthsBirthdayListVar.dateOfBirth} <%
 }
 %>
								</td>
								<c:if test="${monthsBirthdayListVar.dateOfBirth eq currentDate}">
            Today
            </c:if>
							</tr>
						</c:forEach>
					</table>

				</div>
				<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />


				<div class="msg">
					<table style="width: 100%;">
						<tr>
							<td bgcolor="#666666" style="color: white;">Canteen</td>
						</tr>
						<tr>
							<%
							if (session.getAttribute("canTimingName") != null) {
							%>
							<td><%=session.getAttribute("canTimingName")%></td>
							<%
							}
							%>
						</tr>
						<tr>
							<%
							if (session.getAttribute("canteenItemName") != null) {
							%>
							<td><%=session.getAttribute("canteenItemName")%></td>
							<%
							}
							%>
						</tr>
					</table>
				</div>
				<div class="videoDiv"></div>
				<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
				<div class="tcard">
					<table style="width: 100%;">
						<tr>
							<td bgcolor="#666666" style="color: white;">My Weekly
								TimeCard</td>
						</tr>
						<tr>
							<td>
								 <a href="#" onclick="callPopupWindowWithPost('weeklytimecard.htm?empId=${loginUserEmployeeId}&dateFrom=<%=dataFromString%>&dateTo=<%=dataToString%>','My Weekly Time Card','POST')">
								<!-- 	   popupWindowWithPostSize('','My Weekly Time Card','height=550px,width=860px,top=100px,left=250px,scrollbars=no,sizable=yes,toolbar=no,statusbar=no',{}); -->
								<a href="#"
								onclick="window.open('weeklytimecardGet.htm?empId=${loginUserEmployeeId}&dateFrom=<%=dataFromString%>&dateTo=<%=dataToString%>','height=550px','width=1060px','top=100px','left=250px','scrollbars=no','sizable=yes','toolbar=no','statusbar=no');">
									<img src="Charts/WeeklyTimecard_${loginUserName}.png"
									height="120px" width="300px"
									alt="Charts/WeeklyTimecard_${loginUserName}.png" />
							</a>
							</td>
						</tr>
					</table>
				</div>
			</div> --%>
			<!-- <div id="footer"></div> -->
		</div>
	</div>

</body>
</html>