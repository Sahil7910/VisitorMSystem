<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login Page</title>
<!-- <link href="css/external.css" rel="stylesheet" type="text/css" /> -->
<!-- <link href="css/login-box.css" rel="stylesheet" type="text/css" /> -->
<link href="css/login_pg.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/jquery-1.3.2.min.js"></script>


<script type="text/javascript">
$(document).ready(function(){ 
    $(document).keyup(function(event){
        if (event.keyCode == 13){
        $('#loginSubmit').trigger('click'); 
        }
    });
});
</script>
</head>
<body >
<div id="sessionDiv">${sessionExpired}</div>
<%-- <div id="sessionDiv" style="margin-top: 7%;height: 20px;">${sessionExpired}</div>
<form:form name="login" id="login" method="POST" action="checkvaliduser.htm">
<table align="center" width="100%" style="margin-top:60px;">
<tr>
</tr>
<tr>
<td align="center" >
<div style="">
<div id="login-box">
<br />
<div id="login-box-name" style="margin-top:20px; margin-left:5px;">Username:</div><div id="login-box-field" style="margin-top:20px;"><input type="text" id="username" name="username" class="form-login" title="Username" value="" size="30" maxlength="2048" /></div>
<div id="login-box-name" style="margin-left:5px;" >Password:</div><div id="login-box-field"><input type="password" name="password" id="password" class="form-login" title="Password" value="adminPassword" size="30" maxlength="2048" /></div>
 <br />
<br />
<br />
<a href="#" onclick="document.getElementById('login').submit();" id="linkSubmit"><img src="images/login-btn-green.png" width="85" height="30" style="margin-left:200px; margin-top:10px;" /></a>
</div>
</div>
</td>
</tr>
<!-- <tr><td><a href="registerUser.htm"><font color="White">Register</font></a></td></tr> -->
<tr>
<td align="center"><font color="white"></font></td>
</tr>
</table>
</form:form>
</form> --%>

<form:form name="login" id="login">
<div id="content">
        	<div class="text"><!-- <br /><span style="font-size:18px; color:#FFFFFF; float:left;margin-top:20px;"><strong>Login With Your Username And Password</strong></span>
            <br /><br /> -->
            
       
<div class="form">
<table>
<tr>
<td><label for='username' style="font-size: 20px;font-family: sans-serif;"><strong>Username</strong></label></td>
</tr>
<tr>
<td><input style="background-color:#CCC; width: 180px" type='text' name='username' id='username' /></td>
</tr>
<tr>
<td><label for='password' style="font-size: 20px;font-family: sans-serif;"><strong>Password</strong></label></td>
</tr>
<tr>
<td><input style="background-color:#CCC;width: 180px"type='password' name='password' id='password'  /></td>
</tr>
<tr  style="float: left; margin-top:10%; margin-left: 0px;">
<td><button type="button" class="btn" name="loginSubmit" id="loginSubmit" style="background-color:lightgrey" onclick="checkProjectViewPassword();">Login</button></td>

<td><button class="btn" type="reset" style="background-color:lightgrey">Reset</button></td>
</tr>
</table>






<div style="clear: both; margin-left: 90px; margin-top:70px;">${message}</div>
</div>

              </div><!--/*txt div*/-->
        </div>

	<%-- <div id="content">
		<div class="text">
			DISTNA <br />
			<span
				style="font-size: 16px; color: #FFFFFF; float: left; margin-top: 10px;">Login
				With Your Username And Password</span> <br />
				<div id="sessionDiv" style="font-size: 13px; color: #FFFFFF; float: left; margin-top: 1px;">${sessionExpired}</div>
			<br />
			<div class="form">

				<label for='username'>UserName:</label> <input type='text'
					name='username' id='username' maxlength="30" /> <br />
				<br />
				<br /> <label for='password'>Password:</label> <span
					style="margin-left: 10px;"><input type='password'
					name='password' id='password' maxlength="30" /></span> <br />
				<br />
				<br />
				<div class="login">
					<button type="submit" style="background-color: lightgray">Login</button>
				</div>


			</div>
		</div>
	</div> --%>
</form:form>
</body>
</div>
</html>














<!-- <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login Page</title>
<link href="login_pg.css" rel="stylesheet" type="text/css" />
</head>

<body>	
	
    	<div id="content">
        	<div class="text"><br /><span style="font-size:18px; color:#FFFFFF; float:left;margin-top:20px;"><strong>Login With Your Username And Password</strong></span>
            
            <br /><br />
            
       
<div class="form">
<label for='username' ><strong>Username:&nbsp;</strong></label>&nbsp;&nbsp;
<input style="background-color:#CCC" type='text' name='username' id='username' maxlength="10" />
  <br /><br /><br />
<label for='password' ><strong>Password:</strong></label>&nbsp;&nbsp;
<span style="margin-left:10px;"><input style="background-color:#CCC"type='password' name='password' id='password' maxlength="10" /></span>
<br /><br />
<div class="login"><button style="background-color:lightgrey">Login</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button style="background-color:lightgrey">Reset</button></div></div>


              </div>/*txt div*/
        </div>
 
</body>
</html> -->


