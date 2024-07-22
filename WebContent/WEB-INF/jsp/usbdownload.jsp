<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>USB Logs Download</title>
<style type="text/css">

@keyframes anim {
  0% { background-position: 0 0; }
  100% { background-position: 50px 50px; }
}    

.bar {
 display: block;
  margin: auto;
  top: 0; bottom: 0; left: 0; right: 0;
  width: 450px;
  height: 30px;
  overflow: hidden;
  background-size:100px 100px;
  background-image: linear-gradient(-45deg,
     #33c9ff 25%, #00b2f2 25%, 
     #00b2f2 50%, #33c9ff 50%,
     #33c9ff 75%, #00b2f2 75%);
  animation: anim 1s linear infinite;
}

.bar p {
  width: 300px;
  margin: 1px;
  font-size: 14px;
  text-align: center;
  color: white;
}
</style>
</head>
<body id="public">

<div id="container" class="ltr">
<form action="usbdownload.htm" method="POST" enctype="multipart/form-data" class="wufoo leftLabel page">
<ul>
<li id="foli23" class="notranslate">
<label class="desc" id="title23" for="Field23">Select File to Download<span id="req_23" class="req">*</span></label>
<div> 
<input type="file" name="txtFile" accept=".txt"/>
</div>
</li>
<li id="foli241" class="notranslate">
<label class="desc " id="title241" for="Field241"></label>
<div><input   class="btTxt submit"  type="submit" name="Import" id="Import" value="Download" onclick="startbar()"/>
</div>
</li>

<li id="foli241" class="notranslate">
<label class="desc " id="title241" for="Field241"></label>
<div><font class="error">${importResultFlag}</font>
</div>
</li>

</ul>


                     
</form>
 <div id="progress" class="bar">
  				<p>downloading logs Under progress</p>
				</div>   
</div>
  
                    
<script type="text/javascript">
var adiv=document.getElementById('progress');
adiv.style.display="none";

function startbar()
{   adiv.style.display="block";
	//alert('ok');
	//adiv.style.display="none";
}


</script>

</body>
</html>