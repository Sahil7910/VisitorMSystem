<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.distna.web.login.ConnectionDao" %>  
  
  <%
  
  int i=2;
  
  String SQL = "SELECT * FROM liekeys ;";
  System.out.println("SQL : "+ SQL);
  Connection conn=null;
  Statement stmt=null;
  String temp,status;
  
  try
  {
	  ConnectionDao d=new ConnectionDao();
  	 conn= d.getConnection();
     stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(SQL);
      
      
      if (rs.next())
      {
    	  rs.last();
    	  String statusvalue=rs.getString("status");
    	  if(statusvalue.equals("0"))
    	      response.sendRedirect("liekeycheck.htm");
    	  else
    		  response.sendRedirect("loginpage.htm");  
      }
      else
    	  response.sendRedirect("custform.htm"); 
  }
  catch(Exception e)
  {
	  
	  e.printStackTrace();
  }
  
  %>
  
  