<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page  language="java" import="java.util.*,java.text.*"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="iYearVar" value="${iYear}"></c:set>
<c:set var="iMonthVar" value="${iMonth}"></c:set>
<%
 /* int iYear=nullIntconv(request.getParameter("iYear"));
 int iMonth=nullIntconv(request.getParameter("iMonth")); */
  int iYear=(Integer)pageContext.getAttribute("iYearVar");
 int iMonth=(Integer)pageContext.getAttribute("iMonthVar");

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
%>
<c:set var="weeklyOff1Day" value="${allocatedShiftsDefinition.weeklyOff1Day}"></c:set>
<c:set var="weeklyOff2Day" value="${allocatedShiftsDefinition.weeklyOff2Day}"></c:set>
<c:set var="break1" value="${allocatedShiftsDefinition.break1StartTime}"></c:set>
<table align="center" border="1" cellpadding="3" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <th>Sun</th>
          <th>Mon</th>
          <th>Tue</th>
          <th>Wed</th>
          <th>Thu</th>
          <th>Fri</th>
          <th>Sat</th>
        </tr>
        <%
        int cnt =1;
        for(int i=1;i<=iTotalweeks;i++)
        {
		%>
        <tr>
          <% 
	        for(int j=1;j<=7;j++)
	        {
	        	
	        	 String weeklyOff1=(String)pageContext.getAttribute("weeklyOff1Day");
	        	String weeklyOff2=(String)pageContext.getAttribute("weeklyOff2Day");
	        	if(weeklyOff1.equals(j+""))
	        	{
	        		%>
	        		<c:set value="#B3AFB0" var="bgColor"></c:set>
	        		<c:set value="" var="workingStatus"></c:set>
	        		<%
	        	}
	        	else if(weeklyOff2.equals(j+""))
	        	{
	        		%>
	        		<c:set value="#B3AFB0" var="bgColor"></c:set>
	        		<c:set value="" var="workingStatus"></c:set>
	        		<%
	        	}
	        	else
	        	{
	        		%>
	        		<c:set value="" var="bgColor"></c:set>
	        		
	        		<c:set value="" var="workingStatus"></c:set>
	        		<%
	        		System.out.println();
	        	}
	        	/* System.out.println("weekStartDay="+weekStartDay);//the weaks starting day's position  
	        	System.out.println("iTotalweeks="+iTotalweeks);
	        	System.out.println("cnt="+cnt); */
		        if(cnt<weekStartDay || (cnt-weekStartDay+1)>days)
		        {
		        %>
                <td align="center" height="35" bgcolor="${bgColor}">&nbsp;</td>
               <% 
		        }
		        else
		        {
		         %>
		        
		        <c:forEach items="${monthlyHolidayList}" var="holidayvar" >
		        	<c:set var="holidayDate" value="${holidayvar.holidayDate}"></c:set>
		        	<% 
		        	
		        	String holidayArray[]=new String[3];
		        	String holidayDate=(String)pageContext.getAttribute("holidayDate");
		        	holidayArray=holidayDate.split("-");
		        	/* System.out.println(" ===========================");
	        		System.out.println(iYear+" year  "+Integer.parseInt(holidayArray[2]));
	        		System.out.println(iMonth+"  month "+(Integer.parseInt(holidayArray[1])-1));
	        		System.out.println(cnt-weekStartDay+1+" day  "+Integer.parseInt(holidayArray[0])); */
	        		int currentDate=cnt-weekStartDay+1;
		        	if(iYear==Integer.parseInt(holidayArray[2]) && (iMonth==Integer.parseInt(holidayArray[1])-1) && currentDate==Integer.parseInt(holidayArray[0]))
		
		        	{	
		        	%>
		        	<c:set var="holidayflag" value="true"></c:set>
		        	<%
		        	break;
		        	}
		        	else
		        	{
		        	%>
		        	<c:set var="holidayflag" value="false"></c:set>
		        	<%}
		        	
		        	%>
		        	
		        </c:forEach>
		    
		    	<c:choose>
		    	
		    	<c:when test="${holidayflag==true}">
		    	
		    	<td align="center" height="35" id="day_<%=(cnt-weekStartDay+1)%>" bgcolor="${bgColor}"> <font color="Red"> <span><%=(cnt-weekStartDay+1)%></span> </font></td>
		    	</c:when>
		    	<c:otherwise>
               		 <td align="center" height="35" id="day_<%=(cnt-weekStartDay+1)%>" bgcolor="${bgColor}"><span><%=(cnt-weekStartDay+1)%></span></td>
                </c:otherwise>
                </c:choose>
               <% 
		        }
		        cnt++;
		      }
	        %>
        </tr>
        <% 
	    }
	    %>
      </tbody>
    </table>