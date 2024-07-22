<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<%@ page  language="java" import="java.util.*,java.text.*"%>
<!-- <script type="text/javascript" src="calendar/js/calendar.js"></script>
<link rel="stylesheet" href="calendar/css/calendar.css" type="text/css">
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%
 /* int iYear=nullIntconv(request.getParameter("iYear"));
 int iMonth=nullIntconv(request.getParameter("iMonth")); */
 int iYear=0;
int iMonth=0;
 /*  int iYear=(Integer)pageContext.getAttribute("iYearVar");
 int iMonth=(Integer)pageContext.getAttribute("iMonthVar");;  */

 Calendar ca = new GregorianCalendar();
 int iTDay=ca.get(Calendar.DATE);
 int iTYear=ca.get(Calendar.YEAR);
 int iTMonth=ca.get(Calendar.MONTH);

 if(iYear==0)
 {
	  iYear=iTYear;
	  iMonth=iTMonth;
 }
 GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 
 int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
 int weekStartDay=cal.get(Calendar.DAY_OF_WEEK);
 cal = new GregorianCalendar (iYear, iMonth, days); 
 int iTotalweeks=cal.get(Calendar.WEEK_OF_MONTH);
 
 /* System.out.println("iTDay="+iTDay);//today's Date
 System.out.println("iTYear="+iTYear);// current year
 System.out.println("iTMonth="+iTMonth);//current month which starts from 0   i.e 0-Jan,1-Feb... 
 System.out.println("iTotalweeks="+iTotalweeks);//tolal no of weeks in the current selected month
 System.out.println("iYear="+iYear);//selected year
 System.out.println("iMonth="+iMonth);//selected month
 System.out.println("days="+days);//total days in the selected month*/
/*  System.out.println("weekStartDay="+weekStartDay);//the weaks starting day's position  
 System.out.println("iTotalweeks="+iTotalweeks); */
%>
<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" name="EmployeeWiseReport"  id="EmployeeWiseReport"method="POST" action="generateMonthlyAttendanceReport.htm" target="_blank">
<%-- <form:form name="MonthlyAttendanceReport"  method="POST" action="generateMonthlyAttendanceReport.htm"> --%>
<input type="hidden" id="employeeReportType"/>
<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Year
<span id="req_23" class="req">*</span>
</label>
<div>
<select name="iYear" id="iYear" class="field select medium">
        <%
		// start year and end year in combo box to change year in calendar
	    for(int iy=iTYear-70;iy<=iTYear+70;iy++)
		{
		  if(iy==iYear)
		  {
		    %>
          <option value="<%=iy%>" selected="selected"><%=iy%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=iy%>"><%=iy%></option>
          <%
		  }
		}
	   %>
      </select>
</div>
</li>

<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
Month
<span id="req_23" class="req">*</span>
</label>
<div>
<select name="iMonth" id="iMonth" class="field select medium">	
        <%
		// print month in combo box to change month in calendar
	    for(int im=0;im<=11;im++)
		{
		  if(im==iMonth)
		  {
	     %>
          <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
          <%
		  }
		}
	   %>
        </select>
</div>
</li>

<li id="foli346" class="notranslate  threeColumns     ">
<label id="title346" class="desc">
Select a Choice
</label>
<div>
<input id="radioDefault_346" name="Field346" type="hidden" value="" />
<span>
<input type="radio" name="employeeWise" onclick="showRespectiveDiv('multiple','showEmployeeTypeView.htm?');" class="radio"/>
<label class="choice" for="Field346_0" >
Multiple</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('single','showEmployeeTypeView.htm?');"/>
<label class="choice" for="Field346_1" >
Single</label>
</span>
<span>
<input class="radio" type="radio" name="employeeWise" onclick="showRespectiveDiv('all','showEmployeeTypeView.htm?');"/>
<label class="choice" for="Field346_2" >
All Employees</label>
</span>
</div>
</li>



<div id="SelectEmployeeView"><input type="hidden" name="employeeNo" id="employeeNo" value="0"></div>


<li class="buttons ">
<div>
<!-- <input  class="btTxt submit" type="button" value="Generate" onclick="return generateReport('generateMonthlyAttendanceReport.htm?');"/>
 -->
  <img  src="images/pdf.png" height="35" width="35" onclick="return generateReport('generateMonthlyAttendanceReport.htm?');">
<img  src="images/logo-excel.png" height="35" width="35" onclick="return generateReport('generateMonthlyAttendanceReportInXSL.htm?');">
<img  src="images/word.jpg" height="35" width="35" onclick="return generateReport('generateMonthlyAttendanceReportInDocx.htm?');">
</div>
</li>
</ul>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%-- <tr>
        <td width="25%">Year</td>
        <td width="25%">

		<select name="iYear" onchange="goTo('${employeeId}')  ">
		<select name="iYear" id="iYear" >
        <%
		// start year and end year in combo box to change year in calendar
	    for(int iy=iTYear-70;iy<=iTYear+70;iy++)
		{
		  if(iy==iYear)
		  {
		    %>
          <option value="<%=iy%>" selected="selected"><%=iy%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=iy%>"><%=iy%></option>
          <%
		  }
		}
	   %>
        </select></td>
        <td width="25%">Month</td>
        <td width="25%">
		<select name="iMonth" onchange="goTo('${employeeId}')">
		<select name="iMonth" id="iMonth" >
		
        <%
		// print month in combo box to change month in calendar
	    for(int im=0;im<=11;im++)
		{
		  if(im==iMonth)
		  {
	     %>
          <option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
          <%
		  }
		  else
		  {
		    %>
          <option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008,im,01))%></option>
          <%
		  }
		}
	   %>
        </select>
        </td>
      </tr>
<tr> --%>

<tr><td>Multiple Employees</td><td>Single Employee</td><td>All Employees</td></tr>
<tr>
<tr><td>

</td></tr>

</table>
<table width="100%">
<tr><td>&nbsp;</td></tr>
<tr><td align="center"></td></tr></table>
</form:form>
</div>
</body>
</html>

