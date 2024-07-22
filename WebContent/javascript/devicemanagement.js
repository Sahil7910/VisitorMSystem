 function connectionAjax()
{
	document.testConnection.method="POST";
    document.testConnection.action="testConnection.htm";
    document.testConnection.submit();
}
function showDevice()
{
    document.getElementById('deviceListDiv').style.display='block';
}

function validateAddDevice()
{
	
   var Regx = /^[A-Za-z0-9 ]*$/;
   
    if (Regx.test(document.getElementById("deviceName").value)) {
       if(document.getElementById("deviceName").value=="")
           {
               alert("Plz enter a valid Device Name");
                return false;
           }
    }
    else {
        alert("Name not valid");
        return false;
    }
    var RegxSerialNo = /^[A-Za-z0-9 ]*$/;
   // var RegxSerialNo=/^([0-9A-F]{2}[:-]){5}([0-9A-f]{2})*$/;
    if (RegxSerialNo.test(document.getElementById("serialNumber").value)) {
       if(document.getElementById("serialNumber").value=="")
           {
               alert("Plz enter a valid Serial Number");
                return false;
           }
    }
    else {
       // alert("Serial Number not valid");
        return true;
    }

    var ip=document.getElementById("ipAddress").value;
    if(ip!="")
        {
        var ipArray=ip.split(".");
        if(ipArray.length==4)
            {
             for(var i=0;i<ipArray.length;i++)
                {                 
                    if(ipArray[i]>255)
                        {
                            alert("plz enter a valid ip");
                            return false;
                        }
                }                                                              
            }
             else
            {
                alert("plz enter a valid ip");
                return false;
            }       
        }
    else
        {
            alert("plz enter a valid ip");
            return false;
        }
        if(document.getElementById("connectionType").value==0)
            {
                alert("plz enter a valid Connection Type");
                return false;
            }
        if(document.getElementById("deviceDirection").value==0)
            {
                alert("plz enter a valid Device Direction");
                return false;
            }
      var radios = document.getElementsByName("deviceType");
    var formValid = false;

    var i = 0;
    while (!formValid && i < radios.length) {
        if (radios[i].checked) formValid = true;
        i++;        
    }

    if (!formValid)
        {
        alert("Must check some option!");
        return false;
        }
	return true;
} 



/*function validateIp(url,method)
{
	alert('validateIp');
	alert(url);
	//alert("validateAddDevice");
	   var Regx = /^[A-Za-z0-9 ]*$/;
	   
	    if (Regx.test(document.getElementById("deviceName").value)) {
	       if(document.getElementById("deviceName").value=="")
	           {
	               alert("Plz enter a valid Device Name");
	                return false;
	           }
	    }
	    else {
	        alert("Name not valid");
	        return false;
	    }
	    var RegxSerialNo=/^[0-9]*$/;
	    if (RegxSerialNo.test(document.getElementById("serialNumber").value)) {
	       if(document.getElementById("serialNumber").value=="")
	           {
	               alert("Plz enter a valid Serial Number");
	                return false;
	           }
	    }
	    else {
	        alert("Serial Number not valid");
	        return false;
	    }

	    var ip=document.getElementById("ipAddress").value;
	    if(ip!="")
	        {
	        var ipArray=ip.split(".");
	        if(ipArray.length==4)
	            {
	             for(var i=0;i<ipArray.length;i++)
	                {                 
	                    if(ipArray[i]>255)
	                        {
	                            alert("plz enter a valid ip");
	                            return false;
	                        }
	                }                                                              
	            }
	             else
	            {
	                alert("plz enter a valid ip");
	                return false;
	            }       
	        }
	    else
	        {
	            alert("plz enter a valid ip");
	            return false;
	        }
	        if(document.getElementById("connectionType").value==0)
	            {
	                alert("plz enter a valid Connection Type");
	                return false;
	            }
	        if(document.getElementById("deviceDirection").value==0)
	            {
	                alert("plz enter a valid Device Direction");
	                return false;
	            }
	      var radios = document.getElementsByName("deviceType");
	    var formValid = false;

	    var i = 0;
	    while (!formValid && i < radios.length) {
	        if (radios[i].checked) formValid = true;
	        i++;        
	    }

	    if (!formValid)
	        {
	        alert("Must check some option!");
	        return false;
	        }    
	return true;
}
*/
/*function callAjaxForIp(url,method)
{
	  var ip=document.getElementById("ipAddress").value;
	var finalUrl=url+"?ipAddress="+ip;
	alert(finalUrl);
	  var xmlhttp;
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			
			alert(xmlhttp.readyState);
			alert(xmlhttp.status);
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				//alert(xmlhttp.readyState);
				//alert(xmlhttp.status);
				alert(xmlhttp.responseText);
				if(xmlhttp.responseText=="0")
					{
					return true;
					}
				else
					{
					return false;
					}
				
			}
		};
		xmlhttp.open('POST',"validateIP.htm?ipAddress=192.168.0.201",true);
		 xmlhttp.send();
	    //  return true;
}*/
function initTestConnectionWindow(url,width,height)
{
    var scrleft = (screen.width / 2) - (width /2); //centres horizontal
    var scrtop = ((screen.height / 2) - (height /2))-100; //centres vertical

    var appVer = navigator.appVersion.toLowerCase();
    if(appVer.indexOf('msie'))
    {

        window.open(url,'','width='+width+',height='+height+',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,left='+scrleft+',top='+scrtop);

    } else {
        netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite");
        window.url=url;
        window.menubar.visible = false;
        window.locationbar.visible = false;
        window.scrollbars.visible = true;
        window.personalbar.visible = false;
        window.statusbar.visible = false;
        window.toolbar.visible = false;
        window.top=scrtop+500;
        window.left=scrleft;
        netscape.security.PrivilegeManager.revertPrivilege("UniversalBrowserWrite");
        resizeTo(width,height);   //replace with actual numerical values
    }
}
function callPopupWindowPost(url,name,method)
{
	if(method=='POST')
		{
		alert(document.getElementById("ipAddress").value);
		popupWindowPostWithHeightWidth(url,name,500,200,{ipAddress:document.getElementById("ipAddress").value
		});	
		}
	else
		{
		alert("get");
			window.open(url,"width=260px","scrollbars=no","sizable=yes","toolbar=no","statusbar=no");
		}
}
function popupWindowPostWithHeightWidth(URL,name,width,height,PARAMS) {
	alert(URL);
	 var scrleft = (screen.width / 2) - (width /2); //centres horizontal
	 var scrtop = ((screen.height / 2) - (height /2))-100; //centres vertical
	var windowoption='height='+height+',width='+width+',left='+scrleft+',top='+scrtop+',scrollbars=no,sizable=yes,toolbar=no,statusbar=no';
	
	var temp=document.createElement("form");
	temp.action=URL;
	temp.method="POST";
	temp.style.display="none";
	temp.setAttribute("target", name);
	for(var x in PARAMS) {
		var opt=document.createElement("textarea");
		opt.name=x;
		opt.value=PARAMS[x];
		temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	window.open(URL, name, windowoption);
	temp.submit();
    document.body.removeChild(temp);
	return temp;
}



function checkRadioBox()
{    
    var radios = document.getElementsByName("specificDevice");
    var formValid = false;

    var i = 0;
    while (!formValid && i < radios.length) {
        if (radios[i].checked) formValid = true;
        i++;        
    }

    if (!formValid)
        {
        alert("Must Select a Device!");
        return false;
        }
}

function updateFunctionsAjax(url,ipForAjax)
{               
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
                   // CallJS('Demo()');
                       if (xmlhttp.readyState==4 && xmlhttp.status==200)
                       {
                    	   /*alert(xmlhttp.responseText);*/
                               if(xmlhttp.responseText==ipForAjax)
                                   {
                                       alert("Operation Successfull");
                                   }
                               
                                else
                                    {
                                        alert(xmlhttp.responseText);
                                    }
                       }
               };
               xmlhttp.open("POST",url,true);
               xmlhttp.send();
}
function viewDeviceFunctionsAjax(url)
{
	if(checkRadioBox()==false||validateAddDevice()==false)
	  {
	      return false;
	  }
	   else
	      {
		   /*alert(url);*/
	         var presentIp=document.getElementById("ipAddress").value;
	         alert('click ok and wait for 20 seconds');
	        // viewDeviceFunctionsAjax("viewDeviceStatus.htm?validIp="+presentIp,presentIp);
	         var xmlhttp;
	         if (window.XMLHttpRequest)
	         {
	                 xmlhttp=new XMLHttpRequest();
	         }
	         else
	         {
	                 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	         }
	         var splitByDataKeyValue="";
	                xmlhttp.onreadystatechange=function()
	                {
	                        if (xmlhttp.readyState==4 && xmlhttp.status==200)
	                        {
	                           
	                           /* var splitByData=xmlhttp.responseText.toString().split(",");
	                            alert(xmlhttp.responseText);
	                            for(var i=0;i<splitByData.toString().length;i++)
	                                {
	                                  
	                                    var splitByEqual=splitByData[i].split("=");
	                                     splitByDataKeyValue+=splitByEqual[0]+"="+splitByEqual[1]+"\n\n";
	                                      alert(splitByDataKeyValue);    
	                                 }
	                            alert("final"+splitByDataKeyValue);       */
	                        	//alert(xmlhttp.responseText);
	                        	//document.getElementById("seviceInfoStatus").innerHTML=xmlhttp.responseText;
	                        }
	                };
	                xmlhttp.open("POST",url+"?validIp="+presentIp,true);
	                xmlhttp.send();
	      }
}
function deviceInitializationFunction(url)
{
	
		   /*alert(url);*/
	         var presentIp=document.getElementById("ipAddress").value;
	         alert('click ok and wait for 20 seconds');
	        // viewDeviceFunctionsAjax("viewDeviceStatus.htm?validIp="+presentIp,presentIp);
	         var xmlhttp;
	         if (window.XMLHttpRequest)
	         {
	                 xmlhttp=new XMLHttpRequest();
	         }
	         else
	         {
	                 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	         }
	         var splitByDataKeyValue="";
	                xmlhttp.onreadystatechange=function()
	                {
	                        if (xmlhttp.readyState==4 && xmlhttp.status==200)
	                        {
	                           
	                           /* var splitByData=xmlhttp.responseText.toString().split(",");
	                            alert(xmlhttp.responseText);
	                            for(var i=0;i<splitByData.toString().length;i++)
	                                {
	                                  
	                                    var splitByEqual=splitByData[i].split("=");
	                                     splitByDataKeyValue+=splitByEqual[0]+"="+splitByEqual[1]+"\n\n";
	                                      alert(splitByDataKeyValue);    
	                                 }
	                            alert("final"+splitByDataKeyValue);       */
	                        	//alert(xmlhttp.responseText);
	                        	//document.getElementById("seviceInfoStatus").innerHTML=xmlhttp.responseText;
	                        }
	                };
	                xmlhttp.open("POST",url+"?validIp="+presentIp,true);
	                xmlhttp.send();
	      
}

function checkForValidIpAjax(url,ipForAjax)
{
    
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
                           if(xmlhttp.responseText==ipForAjax)
                               {
                                   
                                   initwindow("enterNewIp.jsp?msgIP="+ipForAjax+"&flag=0",500,200);
                               }
                           
                            else
                                {
                                    alert(xmlhttp.responseText);
                                }
                   }
           };
           xmlhttp.open("POST",url,true);
           xmlhttp.send();
}               

function setDataTimeF()
{
//alert('setDataTimeF');
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
      { 
         var presentIp=document.getElementById("ipAddress").value;
      /*   alert(presentIp);*/
         alert('click ok and wait for 20 seconds');
         updateFunctionsAjax("changeDeviceTimeDate.htm?validIp="+presentIp,presentIp);                       
         
      }
      
}
function changeIp()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }else
      {
         var presentIp2=document.getElementById("ipAddress").value;
         alert('click ok and wait for 20 seconds');
         checkForValidIpAjax("../checkIfValid?validIp="+presentIp2,presentIp2);  
      }

}

function downloadUserF()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
      {
           var presentIp=document.getElementById("ipAddress").value;
         alert('click ok and wait for 20 seconds');
         updateFunctionsAjax("downloadToDatabase.htm?validIp="+presentIp,presentIp);
        
      }
  
}


function downloadLogsToDatebase()
{
	//alert('hi');
	//System.out.println("In download");
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
      {
           var presentIp=document.getElementById("ipAddress").value;
       alert('click ok  and wait for 20 seconds');
       
         updateFunctionsAjax("downloadLogsToDatebase.htm?validIp="+presentIp,presentIp);
        
      }
  
}

function clearLogsF()
{
	/*alert('hi');*/
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
      {
	  var presentIp=document.getElementById("ipAddress").value;
      alert('click ok and wait for 20 seconds');
      updateFunctionsAjax("clearLogsF.htm?validIp="+presentIp,presentIp);
      }
}
function clearAdminPrivilegeF()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
      {
          var presentIp=document.getElementById("ipAddress").value;
         alert('click ok and wait for 20 seconds');
         updateFunctionsAjax("clearAdminPrivilege.htm?validIp="+presentIp,presentIp);
      }
}

function restartDeviceF()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else{
      var presentIp=document.getElementById("ipAddress").value;
         alert('click ok and wait for 20 seconds');
         updateFunctionsAjax("restartDevice.htm?validIp="+presentIp,presentIp);
  }
}
function viewDeviceStatusF()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
   else
      {
          var presentIp=document.getElementById("ipAddress").value;
         alert('click ok and wait for 20 seconds');
         viewDeviceFunctionsAjax("viewDeviceStatus.htm?validIp="+presentIp,presentIp);
      }
}
function deleteUserF()
{
if(checkRadioBox()==false||validateAddDevice()==false)
  {
      return false;
  }
  else
  {
	  var presentIp=document.getElementById("ipAddress").value;
      alert('click ok and wait for 20 seconds');
      genericAjaxFunction("showUsersDiv.htm?validIp="+presentIp,'GET','showUsersDiv');
     /* updateFunctionsAjax("deleteUsers.htm?validIp="+presentIp,presentIp);*/
  }
}

function deleteUserDeviceManagement(size,url,method)
{
	if(size>1){
		var selection = document.check.radionm;
	    var a=0;
		for ( var i = 0; i < selection.length; i++)
		{
			if (selection[i].checked == true)
			{
				var id=selection[i].value;
	             a=1;
	             /*document.check.method=method;
	             document.check.action=url+"id="+id;
	             document.check.submit();*/
	             /*alert(url+"&id="+id);*/
	             alert('Wait For 20 seconds...');
	             genericAjaxFunction(url+"&id="+id,'POST','showUsersDiv');
			} 
			
		}
			if(a==0)
			{
			alert("Please select id");
			return false;
			}
		}
		else if(size==1){
			var selection = document.check.radionm;
			if (selection.checked == true)
			{
			    /*document.check.method=method;
			    document.check.action=url+"id="+selection.value;
			    document.check.submit();*/
				/*alert(url+"&id="+selection.value);*/
				alert('Wait For 20 seconds...');
				genericAjaxFunction(url+"&id="+selection.value,'POST','showUsersDiv');
			    return true;
			}
			}
			else{
				alert("The List is Empty");
				return false;
			}
}


function deleteAttendanceDevice(ipAddress)
{
	window.location="deleteattendancedevice.htm?ipAddress="+ipAddress;
}

