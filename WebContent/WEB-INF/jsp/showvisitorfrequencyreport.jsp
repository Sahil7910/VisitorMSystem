<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visitors</title>
</head>
<body id="public">
<div id="container" class="ltr">
<form:form name="check" class="wufoo leftLabel page" id="check" target="_blank">

<ul>
<li id="foli346" class="notranslate  threeColumns     ">
<label id="title346" class="desc">
Select a Choice
<input type="hidden" id="employeeReportType" name="employeeReportType"/>
</label>
<div>
<input id="radioDefault_346" name="Field346" type="hidden" value="" />
<span>
<input type="radio" name="visitorWise" onclick="showRespectiveDiv('multiple','showvisitorlist.htm?');" class="radio"/>
<label class="choice" for="Field346_0" >
Multiple</label>
</span>
<span>
<input class="radio" type="radio" name="visitorWise" onclick="showRespectiveDiv('single','showvisitorlist.htm?');"/>
<label class="choice" for="Field346_1" >
Single</label>
</span>
<span>
<input class="radio" type="radio" name="visitorWise" onclick="showRespectiveDiv('all','showvisitorlist.htm?');"/>
<label class="choice" for="Field346_2" >
All Visitors</label>
</span>
</div>
</li>
<div id="SelectEmployeeView">
<input type="hidden" name="employeeNo" id="employeeNo" value="0">
</div>



<li id="foli110" 
class="notranslate      ">
<label class="desc" id="title110" for="Field110">
</label>
<div>
<label class="mandatory" id="title110" for="Field110">
${ErrorMsg}
</label>
</div>
</li>

<!-- <li class="buttons ">
<div>

<input  type="button" id="capture" value="Capture Photo" onclick="take_snapshot()">
<img  src="images/pdf.png" height="30" width="30" onclick="return generateVisitorFrequencyReport('generatevisitorfrequencyreport.htm?');">
<img  src="images/logo-excel.png" height="30" width="30" onclick="return generateVisitorFrequencyReport('generatevisitorfrequencyreportInXSL.htm?');">
<img  src="images/word.jpg" height="30" width="30" onclick="return generateVisitorFrequencyReport('generatevisitorfrequencyreportInDocx.htm?');">
</div>
</li>
</ul> -->

<input  type="button" id="" value="Generate Report" style="margin-left: 50px;" onclick="return generateVisitorFrequencyReport('generatevisitorfrequencyreport.htm?');">

</form:form>
</div>
</body>
</html>