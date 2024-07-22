<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<title>Designation Level</title>
<!-- <link href="css/index.css" rel="stylesheet" type="text/css" /> -->

<div id=maincontent>
<form:form action="saveOrUpdateDesignationLevel.htm" method="GET" commandName="designationLevel">

<table border="1">
<tr>
<td width="30%">Id</td>
<td width="30%"><form:input path="id" id="id" readonly="true" value="${designationLevel.id}"/></td>
<td width="30%"></td>
</tr>
<tr>
<td>Level Name</td>
<td><form:input path="levelName" id="levelName" value="${designationLevel.levelName}"/></td>
</tr>
<tr>
<td>Description</td>
<td><form:input path="description" id="description" value="${designationLevel.description}"/></td>
</tr>
<tr>
<td>Rank</td>
<td><form:input path="rank" id="rank" value="${designationLevel.rank}"/></td>
<td><form:errors path="rank" cssClass="error"/> </td>
</tr>
<tr>
<td><input type="submit" value="Save"></td>
<td><input type="reset" value="Clear"></td>
</tr>
</table>

</form:form>
</div>
