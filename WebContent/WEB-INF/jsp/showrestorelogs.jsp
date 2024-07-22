<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form encType="multipart/form-data" class="wufoo leftLabel page" name="restoreLogs" id="restoreLogs" onsubmit="if(document.getElementById('selectFile').value==''){alert('PLease Select Valid Dat File');return false;};genericSubmitForRequiredUrl('restoreLogs.htm?restoreType='+document.getElementById('restoreType'),'POST')" method="POST">
<ul>
<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110" >

</label>
<div style="font-size: 15px; color: red;">
${status}
</div>
</li>

<li id="foli346" class="notranslate  threeColumns     ">
<label id="title346" class="desc">
Update
</label>
<div>
<span>
<input type="radio" name="updateLogs"  id="updateUsers" class="radio" value="users" onchange="genericAjaxFunction('showInnerRestore.htm?restoreType='+this.value,'POST','selectFileDiv');"/>
<label class="choice" for="Field346_0" >
Users Logs</label>
</span>
<span>
<input class="radio" type="radio" name="updateLogs" id="updateAttLogs" value="attLogs" onchange="genericAjaxFunction('showInnerRestore.htm?restoreType='+this.value,'POST','selectFileDiv');"/>
<label class="choice" for="Field346_1" >
Attendance Logs</label>
</span>
</div>
</li>
<div id="selectFileDiv">
<input type="hidden" id="restoreType" name="restoreType" value=""/>
</div>
</ul>
</form:form>
</div>
</body>
</html>