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
<form:form commandName="jobPositions" action="addjobposition.htm" method="POST" >
<div id=maincontent>
 <table cellpadding=7 cellspacing=7 border=0 id="fields_block">


<tr>
    <td width="25%"><font class="mandatory">*</font> Job Code</td>
    <td  width="25%" align="left"><form:input path="job_code"/>
   <td width="25%"> <form:errors path="job_code" cssClass="error"/> </td>
   <td width="25%"></td>
  	</tr>


<tr>
    <td><font class="mandatory">*</font> Position Name</td>
    <td align="left">
    <form:input path="position_name"/>
    </td>
    <td><form:errors path="position_name" cssClass="error"/> </td>
    <td> </td>
    </tr>


<tr>
    <td><font class="mandatory">*</font> Designation</td>
    <td align="left">
    <form:select path="designation"  style="width: 150px;" > 
    <form:option value="0">--Select--</form:option>
    <c:forEach items="${designations}" var="designationVar">
    <form:option value="${designationVar.id}">${designationVar.designation}</form:option>
    </c:forEach>
    </form:select>
    </td>
    
    <td><form:errors path="designation" cssClass="error"/> </td>
    <td> </td>
    </tr>


<tr>
    <td>Description</td>
    <td align="left">
    <form:textarea name="description" style="width: 350px;height: 150px;" path="description"></form:textarea>
    </td>
    <td> </td>
    <td> </td>
    </tr>
    
    <tr>
    <td><font class="mandatory">* Required Fields</font> </td>
    <td> </td>
    </tr>

</table>
     	<SPAN class=buttonborder><INPUT class=button value=Save type=submit></SPAN> 
      <SPAN class=buttonborder><INPUT class=button value=Reset type=reset onclick="addjobposition.htm"></SPAN> 
      
      
</div>
</form:form>

<script type="text/javascript">


</script>
</body>
</html>

 