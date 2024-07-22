<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page  language="java" import="java.util.*,java.text.*"%>
<%!
public int nullIntconv(String inv)
{   
	int conv=0;
		
	try{
		conv=Integer.parseInt(inv);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return conv;
}
%>
<c:set var="iYearVar" value="${iYear}"></c:set>
<c:set var="iMonthVar" value="${iMonth}"></c:set>
<%
 /* int iYear=nullIntconv(request.getParameter("iYear"));
 int iMonth=nullIntconv(request.getParameter("iMonth")); */
  int iYear=(Integer)pageContext.getAttribute("iYearVar");
 int iMonth=(Integer)pageContext.getAttribute("iMonthVar");; 

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

<c:set var="weeklyOff1Day" value="${allocatedShiftsDefinition.weeklyOff1Day}"></c:set>
<c:set var="weeklyOff2Day" value="${allocatedShiftsDefinition.weeklyOff2Day}"></c:set>

<html>
<head>
<!-- <script>
  function goTo(employeeId)
{ 
   /*  document.frm.submit(); 
   alert('hi'); 
   showMonthCalenderDiv(employeeId); */
  				document.frm.method="POST";
	            document.frm.action="showMonthCalenderDiv.htm?employeeId="+employeeId;
	            document.frm.submit(); 
 }  
</script> -->
</head>
<body>
<div id="maincontent">
<form:form name="frm" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%">Year</td>
        <td width="10%">
		<%-- <select name="iYear" onchange="goTo('${employeeId}')  "> --%>
		<select name="iYear" id="iYear" style="width: 100px;" onchange="showMonthCalenderDiv('showMonthCalenderInternalDiv.htm','actualCalenderDiv','${employeeId}',1,1);">
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
        <td width="60%" align="center"><h3>${shiftName}</h3></td>
        <td width="10%">Month</td>
        <td width="10%">
		<%-- <select name="iMonth" onchange="goTo('${employeeId}')"> --%>
		<select name="iMonth" id="iMonth" style="width: 100px;" onchange="showMonthCalenderDiv('showMonthCalenderInternalDiv.htm','actualCalenderDiv','${employeeId}',1,1);">
		
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
        </select></td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
  <td> &nbsp;</td>
  </tr>
  
  <tr>
    <td>
    <div id="actualCalenderDiv">
    
    
    <div class="CSSTableGeneratorOutter">
	<div class="CSSTableGenerator">
    
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td width="14%">Sun</td>
          <td width="14%">Mon</td>
          <td width="14%">Tue</td>
          <td width="14%">Wed</td>
          <td width="14%">Thu</td>
          <td width="14%">Fri</td>
          <td width="14%">Sat</td>
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
	        		<%
	        	}
	        	else if(weeklyOff2.equals(j+""))
	        	{
	        		%>
	        		<c:set value="#B3AFB0" var="bgColor"></c:set>
	        		<%
	        	}
	        	else
	        	{
	        		%>
	        		<c:set value="" var="bgColor"></c:set>
	        		<%
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
		        {%>
		        
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
    </div></div>
    
    
    </div>
    </td>
  </tr>
</table>
</form:form>
</div>
</body>
</html>