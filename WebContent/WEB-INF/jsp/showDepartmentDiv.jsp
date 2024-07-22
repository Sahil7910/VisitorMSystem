<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<form:form commandName="departments" action="addNewDepartment.htm" method="POST" >
<div id=maincontent>
 <TABLE id="departmentTable" border=0 cellSpacing=7 cellPadding=4 class="maincontent">
        
        <TR>
          <TD width=150 >Name</TD>
          <TD width=250>
          <form:input path="name" name="deptName"/>
           </TD></TR>
        <TR>
          <TD   width=150>Head</TD>
          <TD   width=250>
          <form:select path="head" style="width: 150px;">
          <form:option value="0">--Select--</form:option>
          <%-- <form:option value="1">1</form:option>
          <form:option value="2">2</form:option>
          <form:option value="3">3</form:option> --%>
            <c:forEach items="${departmentListForHead}" var="departmentListForHeadVar">
           <form:option value="${departmentListForHeadVar.id}">${departmentListForHeadVar.name}</form:option>
          </c:forEach>
          </form:select>
          </TD></TR>
        <TR>
          <TD   width=150>Email</TD>
          <TD   width=250><!-- <input type="text" name="value_email"  maxlength=100 value=""> -->
           <form:input path="email" name="email"/>
           </TD></TR>
 </TABLE>
     	<SPAN class=buttonborder><INPUT class=button value=Save type=submit></SPAN> 
      <SPAN class=buttonborder><input class=button type="button" value="Cancel" onclick="cancleDeptDiv();"></SPAN> 
    
      
</div>
</form:form>

<script type="text/javascript">


</script>
</body>
</html>

 