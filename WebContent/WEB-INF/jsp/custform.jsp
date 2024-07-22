<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

body{

     background: url(images/Login_Background.png);  
    background-size: 100% 600px;
    background-repeat: no-repeat;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="POST">
</br></br></br></br></br></br>
<h2 style="color: white;" align="center">Registration Form</h2>
<table style="color: white; padding: 20px;"  cellspacing="14px" align="center">
  <tr>
  <td>Customer Name :</td>
  <td><input name="name" id="name"/></td>
  </tr>
  <tr>
  <td>Mobile No :</td>
  <td><input name="mobile" id="mobile"/></td>
  </tr>
  <tr>
  <td>Email :</td>
  <td><input name="email" id="email"/></td>
  </tr>
  <tr>
  <td>Company :</td>
  <td><input name="company" id="key"/></td>
  </tr>

</table>
<div align="center"><button  class ="btn" type="submit"  name="infosubmit" id="info">Submit</button></div>
</form>
</body>
</html>