<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="UTF-8">

<title>Insert title here</title>
<style>
#container {
   /*  margin: 0px auto; */
   margin-top: 45px;
    width: 350px;
    height: 255px;
    border: 10px #333 solid;
}
#videoID {
    width: 350px;
    height: 255px;
    background-color: #666;
}
#canvasID {
    width: 350px;
    height: 275px;
    background-color: #CCC;
}

</style>
</head>
<body>
<form action="PhotoCaptureNew.htm" method="post" accept-charset="UTF-8">
<table style="margin-top: 50px;">
		<tr>
			<td>
                  <div id="container"><video id="videoID" autoplay style="border: 1px solid black;"></video></div>
            </td> 
             <td>
				<table style=" margin-left:2px;margin-top :20px; " >
					<tr> 
						<td >
                           <div><canvas id="canvasID" style="border: 1px solid black;"></canvas></div>
                         </td>
                      </tr>
                   </table>
                </td>
              </tr>
              
        </table>                 
 <div>
 <input type="button" value="Take photo" onclick="capture()" 
 style="width: 200px; height: 30px;"/>
     <input type="button" value="Send" onclick="send()" 
          style="width: 200px; height: 30px;"/>
 </div>



<script type="text/javascript">
 
 var video = document.getElementById('videoID');
 var canvas = document.getElementById('canvasID');
 var context = canvas.getContext('2d');
 
 window.URL = window.URL || window.webkitURL;
 navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || 
                          navigator.mozGetUserMedia || navigator.msGetUserMedia;
  navigator.getUserMedia({
	 video : true
	 },
	 function(stream) {
	 video.src = window.URL.createObjectURL(stream);
	 }, 
	 function(e) { console.log('An error happened:', e);})
	 
 function capture() 
 {
 context.drawImage(video, 0, 0, 320, 240);
 };
 
 function send()
 {
 var imageData =  canvas.toDataURL();
 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("POST", "/DISTNA/PhotoCapture.htm", true);
 xmlhttp.send(imageData);
 };
 </script>
</body>
</html> -->


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Capture Photo</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/webcamjs/1.0.26/webcam.min.js"></script>
    <script language="JavaScript">
        // Configure a few settings and attach camera
        function configure() {
            Webcam.set({
                width: 320,
                height: 240,
                image_format: 'jpeg',
                jpeg_quality: 90
            });
            Webcam.attach('#my_camera');
        }

        // A button for taking snaps
        function take_snapshot() {
            Webcam.snap(function (data_uri) {
                document.getElementById('results').innerHTML =
                    '<img src="' + data_uri + '"/>';
                document.getElementById('photoData').value = data_uri;
            });
        }
        
        
     
    </script>
</head>
<body onload="configure();">

 

    <h1>Capture Visitor Photo</h1>

    <!-- Camera -->
    <div id="my_camera"></div>
    <br/>
    <input type="button" value="Take Snapshot" onClick="take_snapshot()">
    <br/><br/>

	<h1></h1>
    <!-- Display the captured image -->
    <div id="results"></div>

    <!-- Hidden form to send image data -->
    <form action="updatevphoto.htm" method="post" enctype="multipart/form-data">
     	<input type="hidden" name="Id" value="${visitorId}"/>
        <input type="hidden" name="photo" id="photoData"/>
        <button type="submit">Save Photo</button>
    </form>
</body>
</html>
