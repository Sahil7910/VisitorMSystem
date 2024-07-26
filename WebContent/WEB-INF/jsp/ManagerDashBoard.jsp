<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script>
    function pageRedirect() {
    	
    	let x = document.forms["myForm"]["EmployeeID"].value;
    	  if (x == "") {
    	    alert("Employee ID  must be filled out");
    	    return false;
    	  }

    
    }      
    
    
    function FocusOnInput()
    {
    document.getElementById("EmployeeID").focus();
    }
    
    
    

</script>

<style type="text/css">
.btn {
	background: #d93434;
	background-image: -webkit-linear-gradient(top, #d93434, #b82b2b);
	background-image: -moz-linear-gradient(top, #d93434, #b82b2b);
	background-image: -ms-linear-gradient(top, #d93434, #b82b2b);
	background-image: -o-linear-gradient(top, #d93434, #b82b2b);
	background-image: linear-gradient(to bottom, #d93434, #b82b2b);
	-webkit-border-radius: 28;
	-moz-border-radius: 28;
	border-radius: 28px;
	font-family: Arial;
	color: #ffffff;
	font-size: 14px;
	height: 30px;
	width: 90px;
	padding: 3px 5px 5px 5px;
	text-decoration: none;
}

.btn:hover {
	background: #fc3c63;
	background-image: -webkit-linear-gradient(top, #fc3c63, #d93434);
	background-image: -moz-linear-gradient(top, #fc3c63, #d93434);
	background-image: -ms-linear-gradient(top, #fc3c63, #d93434);
	background-image: -o-linear-gradient(top, #fc3c63, #d93434);
	background-image: linear-gradient(to bottom, #fc3c63, #d93434);
	text-decoration: none;
}

body {
	background: url(images/Login_Background.png);
	background-size: 100% 600px;
	background-repeat: no-repeat;
}

input[type=text] {
	width: 100%;
	padding: 18px 20px;
	margin: 8px 0;
	box-sizing: border-box;
}-
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="FocusOnInput()" >
	<form name="myForm" method="POST" action="ManagerDashBoard.htm" onsubmit="return pageRedirct()">
		</br>
		</br> 
		</br>
		</br>
		</br>
		</br>
		<!-- <h2 style="color: white;" align="center">Registration Form</h2> -->
		<table style="color: white; padding: 20px;" cellspacing="14px"
			align="center">
			<tr>
				<td style="font-size: 50px">Employee ID:</td>
				<td><input name="EmployeeID" id="EmployeeID" maxlength="9" /></td>
			</tr>
		</table>
		<div align="center">
			<button class="btn" type="submit" onclick="pageRedirect()">Submit</button>
		</div>
	</form>
</body>
</html>