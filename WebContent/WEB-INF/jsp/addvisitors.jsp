<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visitor</title>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/webcamjs/1.0.25/webcam.min.js"></script>
 
  <style type="text/css">
     	#my_camera{
     	margin-left: 200px;
     	}
     	#capture{
     	margin-left: 150px;
     	matgin-top:10px;
     	}
     	
     </style>

</head>
<body id="public">
<div id="container" class="ltr">
<form:form encType="multipart/form-data" class="wufoo leftLabel page" action="addvisitors.htm" method="POST" commandName="visitors">




<ul>
<div id="my_camera"></div>
        <br/>
        
        Capture button
        <input  type="button" id="capture" value="Capture Photo" onclick="take_snapshot()">
        
        Hidden input to store the captured photo data
        <input  type="hidden" name="photo" id="photo" required>
        <br/><br/>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Name<span id="req_23" class="req">*</span></label>
<div>
	<form:input path="visitorName" id="visitorName" name="visitorName" class="field text medium"/>
	<form:errors path="visitorName" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Is Regular Visitor</label>
<div>
	<form:checkbox path="regularVisitor"/>
</div>
	
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Is VIP Visitor</label>
<div>
	<form:checkbox path="vipVisitor"/>
</div>
	
</li>


<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Visitor Type<span id="req_23" class="req">*</span></label>
<div id="visitorTypeDiv">
	<form:select path="visitorType" class="field select medium">
		<form:option value="0">Select</form:option>
		<c:forEach items="${visitorTypeList}" var="visitorTypeListVar">
			<option value="${visitorTypeListVar.visitorTypeVariable}">${visitorTypeListVar.visitorTypeVariable}</option>
	</c:forEach>
	</form:select>
	<input type="button" value="Add" class="btTxt submit" onclick="showTextBoxVisitorType();">
	<form:errors path="visitorType" cssClass="error"></form:errors>
</div>

<div id="visitorTypeTextboxDiv" style="display: none;">
	<input type="text" id="visitorTypeText" name="visitorTypeText" class="field text medium">
<!-- 	<input type="button" value="Add" class="btTxt submit" onclick="saveTextBoxVisitorType();"> -->
	<input type="button" value="Add" class="btTxt submit" onclick="saveTextBoxVisitorType();">

</div>
</li>




<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Company</label>
<div>
	<form:input path="company" id="company" name="company" class="field text medium"/>
	<%-- <form:errors path="company" cssClass="error"></form:errors> --%>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Location<span id="req_23" class="req">*</span></label>
<div>
<form:select path="location" id="location" class="field select medium">
<form:option value="0">Select</form:option>
<c:forEach items="${locationList}" var="locationList">
    <form:option value="${locationList.id}" label="${locationList.location}">${locationList.location}</form:option>
</c:forEach>
</form:select>
<form:errors path="location" cssClass="error"></form:errors>
</div>
</li>


<%-- <li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Location</label>
<div>
	<form:input path="location" id="location" name="location" class="field text medium"/>
	<form:errors path="location" cssClass="error"></form:errors>
</div>
</li> --%>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Designation</label>
<div>
	<form:input path="designation" id="designation" name="designation" class="field text medium"/>
	<%-- <form:errors path="designation" cssClass="error"></form:errors> --%>
</div>
</li>



<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Mobile Number<span id="req_23" class="req">*</span></label>
<div>
	<form:input path="mobileNo" id="mobileNo" name="mobileNo" class="field text medium"/>
	<form:errors path="mobileNo" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Email Id<span id="req_23" class="req">*</span></label>
<div>
	<form:input path="emailId" id="emailId" name="emailId" class="field text medium"/>
	<form:errors path="emailId" cssClass="error"></form:errors>
</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Address<span id="req_23" class="req">*</span></label>
<div>
	<form:textarea path="address" id="address" name="address" class="field textarea medium"/>

</div>
</li>

<li id="foli110" class="notranslate"><label class="desc" id="title110" for="Field110">Remarks</label>
<div>
	<form:textarea path="remarks" id="remarks" name="remarks" class="field textarea medium"/>

</div>
</li>






<li id="foli1" class="notranslate"><label class="desc" id="title1" for="Field1"><span id="req_23" class="req">* Required Fields</span></label>
<div>
</div>
</li>

<li class="buttons ">
<div>
    <input class="btTxt submit" type="submit" name="save" id="save" value="Save"/>
</div>
</li>


</ul>

</form:form>
</div>

<script type="text/javascript">
Webcam.set({
    width: 200,
    height: 220,
    image_format: 'jpeg',
    jpeg_quality: 90
});
Webcam.attach('#my_camera');

// Function to capture the photo
function take_snapshot() {
    Webcam.snap(function(data_uri) {
        // Store the captured photo data in a hidden input field
        document.getElementById('photo').value = data_uri;
        // Optional: display the captured image
        document.getElementById('my_camera').innerHTML = 
            '<img src="'+data_uri+'"/>';
    });
}

function showTextBoxVisitorType()
{
document.getElementById("visitorTypeText").value='';
document.getElementById("visitorTypeDiv").style.display='none';
document.getElementById("visitorTypeTextboxDiv").style.display='block';
}

function saveTextBoxVisitorType()
{
	
	document.getElementById("visitorTypeTextboxDiv").style.display='none';
	var visitorTypeString=document.getElementById("visitorTypeText").value;
	if(visitorTypeString == '')
		{
		alert('Please enter visitor type');
		document.getElementById("visitorTypeTextboxDiv").style.display='block';
		return false;
		}
	document.getElementById("visitorTypeDiv").style.display='block';
	var xmlhttp;
	 if (window.XMLHttpRequest)
	 { 
		 xmlhttp=new XMLHttpRequest();
	 }
	 else
	 { 
		 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	 }
	 	xmlhttp.onreadystatechange=function()
	 	{
	 		if (xmlhttp.readyState==4 && xmlhttp.status==200)
	 		{
	 			document.getElementById("visitorTypeDiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("POST","savevisitortype.htm?visitorTypeString="+visitorTypeString,true);
	 	xmlhttp.send();	
}
</script>

</body>
</html>