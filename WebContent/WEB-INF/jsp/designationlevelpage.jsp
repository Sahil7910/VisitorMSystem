<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<title>Designation Level</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />

<div id=maincontent>
<form:form action="addDesignationLevel.htm" method="GET" commandName="designationLevel">

<table border="1">
<tr>
<td width="33%">Id</td>
<td width="33%"><input type="text" id="id" readonly="readonly" value="${nextId}"/></td>
<td width="33%"> </td>
</tr>
<tr>
<td>Level Name</td>
<td><form:input path="levelName" id="levelName" /></td>
<td><form:errors path="levelName" cssClass="error"/> </td>
</tr>
<tr>
<td>Description</td>
<td><form:input path="description" id="description" /></td>
<td> </td>
</tr>
<tr>
<td>Rank</td>
<td><form:input path="rank" id="rank"/></td>
<td><form:errors path="rank" cssClass="error"/> </td>
</tr>
<tr>
<td><input type="submit" value="Save"></td>
<td><input type="reset" value="Clear"></td>
</tr>
</table>
</form:form>
</div>