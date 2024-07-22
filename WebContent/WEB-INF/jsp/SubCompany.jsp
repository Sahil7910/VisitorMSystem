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
<form:form commandName="departments" action="addDepartment.htm" method="POST" >
<div id=maincontent>
 
      
     
      <TABLE id="fields_block" cellSpacing=0 cellPadding=4 border=0>
      <TR><TD width=150>Contractor/Sub Company</TD>
          <TD width=250><form:input path="" name="value_division_name" ></form:input></TD></TR>
        <TR><TD  width=150>Code</TD>
          <TD  width=250><form:input path="" name="value_division_code" ></form:input> </TD></TR>
        <TR><TD  width=150>Description</TD>
          <TD  width=250><form:textarea path="" name="value_description" style="width: 250px;height: 150px;"></form:textarea> </TD></TR>
        <TR><TD width=150>Division Head</TD>
          <TD  width=250><form:select path="" id="value_division_head" name="value_division_head" >
		          <form:option value="0">--Select--</form:option>
		          <form:option value="1">1</form:option>
		          <form:option value="2">2</form:option>
		          <form:option value="3">3</form:option>
		          <form:option value="4">4</form:option>
		          <form:option value="5">5</form:option>
          </form:select> 
      </TD></TR></TABLE>
     
      <SPAN class=buttonborder><INPUT class=button type=submit value=Save></SPAN> 
      <SPAN class=buttonborder><INPUT class=button type=reset value=Reset onclick="resetEditors();"></SPAN> 
       
      
     	
      
</div>
</form:form>

<script type="text/javascript">


</script>
</body>
</html>

 