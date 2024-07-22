<%@page import="java.util.List" session="true"%>
   <%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link href="css/external.css" rel="stylesheet" type="text/css" />
<link href="css/login-box.css" rel="stylesheet" type="text/css" />
<link href="css/demo.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link href="css/tabs.css" rel="stylesheet" type="text/css" />
<link href="css/buttons.css" rel="stylesheet" type="text/css" />
<link href="css/table.css" rel="stylesheet" type="text/css" />
<link href="css/table_2.css" rel="stylesheet" type="text/css" />
<!-- CSS -->
<link href="css/structure.css" rel="stylesheet">
<link href="css/form.css" rel="stylesheet">
<link href="css/theme.css" rel="stylesheet">
<!-- JavaScript -->
<script src="javascript/wufoo.js"></script>

<link rel="stylesheet" href="css/template1/calendar.css" type="text/css">
 <script type="text/javascript" src="javascript/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="calendar/js/calendar.js"></script>
<script type="text/javascript" src="calendar/js/CalendarPopup.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/devicemanagement.js"></script>
<script type="text/javascript" src="javascript/jscolor.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>

<!-- <script type="text/javascript">
document.onkeydown = checkKey;
function checkKey(e) {
	/* alert (e.keyCode); */
    e = e || window.event;
    alert("keyPressed= "+e.keyCode);
    if (e.keyCode == '38') {
    	//alert (e.keyCode);
    	document.getElementById("text1").value=document.getElementById("selectSimilar").value;
    }
    else if (e.keyCode == '40') {
    	//alert(e.keyCode);
    	document.getElementById("text1").value=document.getElementById("selectSimilar").value;
    }
    else if (e.keyCode == '37') {
    	//alert (e.keyCode);
    	document.getElementById("text1").value=document.getElementById("selectSimilar").value;
    }
    else if (e.keyCode == '39') {
    	//alert (e.keyCode);
    	document.getElementById("text1").value=document.getElementById("selectSimilar").value;
    }
    else if (e.keyCode == '32') {
    	/* alert (e.keyCode); */
    	document.getElementById('print').disabled = true;
    }
    else if (e.keyCode == '13') {
    	/* alert (e.keyCode); */
    	document.getElementById('print').disabled = false;
    }
    
}
</script> -->
</head>
<body style="background-image: url('images/gray.png'); " >
	<div id="wrapper">
	 <table width="100%" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="2"> 
                    <tiles:insertAttribute name="header" />
                </td>
            </tr>
           <tr>
              <%--  <td valign="top">
                    <tiles:insertAttribute name="menu" /> 
               </td> --%>
               <td valign="top" style="width: 100%; hight: 400px;vertical-align: top;">
               		<div id="con">
                   		<tiles:insertAttribute name="body" />
                   </div>
               </td>
           </tr>
            <tr>
               <td colspan="2" >
                   <tiles:insertAttribute name="footer" />
               </td>
            </tr>
        </table>
	</div> 
   </body>
</html>