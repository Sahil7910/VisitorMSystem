<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Import Database</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form action="importEmployeedata.htm" method="POST" enctype="multipart/form-data" class="wufoo leftLabel page">
<ul>
<li id="foli23" class="notranslate">
<label class="desc" id="title23" for="Field23">Select File to Import<span id="req_23" class="req">*</span></label>
<div> 
<input type="file" name="csvFile" accept=".csv"/>
</div>
</li>
<li id="foli241" class="notranslate">
<label class="desc " id="title241" for="Field241"></label>
<div><input   class="btTxt submit"  type="submit" name="Import" id="Import" value="Import"/>
</div>
</li>

<li id="foli241" class="notranslate">
<label class="desc " id="title241" for="Field241"></label>
<div><font class="error">${importResultFlag}</font>
</div>
</li>

</ul>
</form>
</div>
</body>
</html>