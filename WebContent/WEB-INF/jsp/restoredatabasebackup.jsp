<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Restore Database</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form class="wufoo leftLabel page" action="restoredatabasebackup.htm" method="POST" encType="multipart/form-data">
<ul>
<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110"></label>
<div>
	<font class="error">Please select only .sql files</font>
</div>
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110">Select Database</label>
<div>
	<input type="file" id="databasePath" name="databasePath">
</div>
</li>

<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110"></label>
<div>
	<font class="error">${errorMessage}</font>
</div>
</li>


<li id="foli110" class="notranslate      "><label class="desc" id="title110" for="Field110"></label>
<div>
	<input type="submit" value="Restore">
</div>
</li>


</ul>
</form>


</div>
</body>
</html>