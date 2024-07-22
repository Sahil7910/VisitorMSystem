function addlocation()
{
	
	/*alert("in add location");*/
	document.addlocation.method="GET";
	document.addlocation.action="addLocationData.htm";
	document.addlocation.submit();

}

function deleteLocation(size,url)
{
	
	/*alert("in delete: "+size+" url: "+url);*/
	if(size>1){
		var selection = document.check.radionm;
	    var a=0;
		for ( var i = 0; i < selection.length; i++)
		{
			if (selection[i].checked == true)
			{
				var Id=selection[i].value;
	             a=1;
	         	//alert("in delete id: "+Id+" url: "+url);
	             document.check.method="POST";
	             document.check.action=url+"?Id="+Id;
	             document.check.submit();
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
			    document.check.method="POST";
			    document.check.action=url+"?Id="+selection.value;
			    document.check.submit();
			    return true;
			}
			}
			else{
				alert("The List is Empty");
				return false;
			}
}


/*var countryId;
function showState(selectionId)
{
	
	
	countryId=selectionId;
	document.getElementById("statediv").style.display='block';
	//alert("selectionId in showState: "+selectionId);
	var url="showState.htm?Id="+selectionId;
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
	 			document.getElementById("statediv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
	
	
}*/

var countryId;
function showState(selectionId)
{
	/*alert('hi');*/
	countryId=selectionId;
	document.getElementById("statediv").style.display='block';
	//alert("selectionId in showState: "+selectionId);
	var url="showState.htm?Id="+selectionId;
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
	 			document.getElementById("statediv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
	
	
}

function showStateLanguage(selectionId)
{
	
	
	var url="showStateLang.htm?Id="+selectionId;
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
	 			document.getElementById("statedivlang").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
	
	
}


/*function addCity(redirectURL)
{
	
	document.getElementById("citydiv").style.display='block';	
	//alert("redirecturl: "+redirectURL);
	
	//var url="showCity.htm?location="+locationId+"&redirectURL="+redirectURL;
	//alert("countryId in addCity: "+countryId);
	var url="showCity.htm?redirectURL="+redirectURL+"&countryId="+countryId;
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
	 			document.getElementById("citydiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("GET",url,true);
	 	xmlhttp.send();

}*/


function cancleDiv()
{
document.getElementById("citydiv").style.display='none';	
}

function showCities(selectionId)
{
	/*alert("stateid: "+selectionId);*/
	
	var url="showAvalableCities.htm?Id="+selectionId;
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
	 			document.getElementById("showCitiesDiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("GET",url,true);
	 	xmlhttp.send();
	 	document.getElementById("addNewButtonDiv").style.display='block';
}

function showLanguages(selectionId)
{
	var url="showAvalableLanguages.htm?Id="+selectionId;
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
	 			document.getElementById("showLanguageDiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
}

function showCountry()
{
	document.getElementById("showcountry").style.display='block';	
}

function blockOtherLanguage()
{
	document.getElementById("showcountry").style.display='none';
	document.getElementById("statediv").style.display='none';
	document.getElementById("showLanguageDiv").style.display='none';
}

function addNewCityDiv()
{
/*alert('hi');*/
var url="showaddnewcitytextbox.htm";
document.getElementById("addNewButtonDiv").style.display="none";


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
			document.getElementById("showCitiesDiv").innerHTML=xmlhttp.responseText;
		}
	};
	xmlhttp.open("POST",url,true);
	xmlhttp.send();

}


function addNewCity()
{
	/*alert('hi');*/
var stateId=document.getElementById("state").value;
var countryId=document.getElementById("currentCountry").value;
var addnewcity=document.getElementById("addnewcity").value;

/*alert(stateId);
alert(countryId);
alert(addnewcity);*/

var url="addnewcity.htm?countryId="+countryId+"&stateId="+stateId+"&addnewcity="+addnewcity;

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
			document.getElementById("showCitiesDiv").innerHTML=xmlhttp.responseText;
		}
	};
	xmlhttp.open("POST",url,true);
	xmlhttp.send();

}
