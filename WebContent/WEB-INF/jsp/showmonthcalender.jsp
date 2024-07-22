<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<%
 int iYear=nullIntconv(request.getParameter("iYear"));
 int iMonth=nullIntconv(request.getParameter("iMonth"));

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
 System.out.println("days="+days);//total days in the selected month
 System.out.println("weekStartDay="+weekStartDay);//the weaks starting day's position */
%>
<html>
<head>
<title>How to create Calendar in JSP</title>
<script>
function goTo()
{
  document.frm.submit();
}
</script>
</head>
<body>
<div id="maincontent">
<form name="frm" method="GET">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="6%">Year</td>
        <td width="7%">
		<select name="iYear" onchange="goTo()">
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
        <td width="73%" align="center"><h3><%=new SimpleDateFormat("MMMM").format(new Date(2008,iMonth,01))%> <%=iYear%></h3></td>
        <td width="6%">Month</td>
        <td width="8%">
		<select name="iMonth" onchange="goTo()">
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
    <td><table align="center" border="1" cellpadding="3" cellspacing="0" width="100%">
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
		        if(cnt<weekStartDay || (cnt-weekStartDay+1)>days)
		        {
		         %>
                <td align="center" height="35">&nbsp;</td>
               <% 
		        }
		        else
		        {
		         %>
                <td align="center" height="35" id="day_<%=(cnt-weekStartDay+1)%>"><span><%=(cnt-weekStartDay+1)%></span></td>
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
    </table></td>
  </tr>
</table>
</form>
</div>
</body>
</html>
