<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
</head>


<c:if test="${checkOnload==1}">
<c:choose>
<c:when test="${checkConnectionStatus=='successfull'}">
<c:url var="onloadFunctionName" value="window.opener.document.getElementById('ipAddress').value='${ipAddress}';alert('Connection Successfull');window.close();"></c:url>
</c:when>
<c:otherwise>
<c:url var="onloadFunctionName" value="alert('Connection failed');window.close();"></c:url>
</c:otherwise>
</c:choose>
</c:if>
<body onload="${onloadFunctionName}">
<div id="maincontent">
<form:form method="POST" name="testConnection" action="testConnection.htm">
<table cellpadding=0 cellspacing=7 border=0 id="fields_block">
<tr>
    <td  align="left" width=150 style="padding-left:0px;">Enter New IP</td>
    <td align="left" width=250 ><input type="text" id="ipAddress" name="ipAddress"/></td>
</tr>
<tr><td><input type="submit" name="save" id="save" value="TestConnection"/></td><td></td></tr>
 </table>
</form:form>
</div>
</body>
</html>

