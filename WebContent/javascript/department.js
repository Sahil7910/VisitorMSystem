function deleteDepartment(size,url,method)
{
	
	//alert("in delete department");
	
	if(size>1){
		var selection = document.check.radionm;
	    var a=0;
		for ( var i = 0; i < selection.length; i++)
		{
			if (selection[i].checked == true)
			{
				var Id=selection[i].value;
	             a=1;
	         	
	             document.check.method=method;
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
			    document.check.method=method;
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

function addDepartment(deptid)
{
	
document.getElementById("deptdiv").style.display='block';	
	
	
	var url="showDepartment.htm?deptid="+deptid;
	
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
	 			document.getElementById("deptdiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("GET",url,true);
	 	xmlhttp.send();
}


function addDesignation(desgnid)
{
	
	document.getElementById("desigdiv").style.display='block';	
	
	
	var url="designation.htm?view=addnew";
	
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
	 			document.getElementById("desigdiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("GET",url,true);
	 	xmlhttp.send();
}

function addDesignationUpdate(desgnid)
{
	/*alert(desgnid);*/
	document.getElementById("desigdiv").style.display='block';	
	var url="designation.htm?view="+desgnid;
	
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
	 			document.getElementById("desigdiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
	 	xmlhttp.open("GET",url,true);
	 	xmlhttp.send();
}

function addAndHideDivision()
{
	var url="addDivisionInternally.htm?divisionName="+document.getElementById("divisionName").value+"&divisionCode="+document.getElementById("divisionCode").value+"&divisionDescription="+document.getElementById("divisionDescription").value+"&divisionHead="+document.getElementById("divisionHead").value+"&locationId="+document.getElementById("locationId").value;
	
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
	 			if(xmlhttp.responseText=="true")
	 				{
	 				/*document.getElementById("divisiondiv").style.display="none";*/
	 				location.reload();
	 				}
	 			else
	 				{
	 				alert("Please Enter All Fields");
	 				}
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
}

/*function addDesignationByAjax(url)
{
	alert(url);
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
	 			if(xmlhttp.responseText=="true")
	 				{
	 				document.getElementById("divisiondiv").style.display="none";
	 				location.reload();
	 				}
	 			else
	 				{
	 				alert("Please Enter All Fields");
	 				}
	 		}
	 	};
	 	xmlhttp.open("POST",url,true);
	 	xmlhttp.send();
}*/


function cancleDesiDiv()
{
document.getElementById("desigdiv").style.display='none';	
}

function cancleDeptDiv()
{
document.getElementById("deptdiv").style.display='none';	
}

function validateAddNewDepartment()
{
	//alert("AddNewDept");
	var nameValue=document.getElementById("deptName").value;
	var selectedHead=document.getElementById("deptHead").value;
	var email = document.getElementById('deptEmail');
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	
	if(selectedHead=="0")
	{
		alert("Please Select Head");
		return false;
	}
	
	if(nameValue=="")
	{
		alert("Please Enter Department Name");
		return false;
	}
    
	if (!filter.test(email.value)) {
	    alert('Please provide a valid email address');
	    email.focus;
	    return false;
	}
	
}


function displaySessionDiv()
{
	if(document.getElementById("halfDay").checked==true)
	{
		document.getElementById("half_day_sessionDiv").style.display='block';	
		document.getElementById("value_duration").value="0.5";
			var url="showHalfDaySession.htm";
			
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
			 			document.getElementById("half_day_sessionDiv").innerHTML=xmlhttp.responseText;
			 		}
			 	};
			 	xmlhttp.open("GET",url,true);
			 	xmlhttp.send();
	}
	else
		document.getElementById("half_day_sessionDiv").style.display='none';

}

function selectAddType(size,empId)
{
	
	if(size>=1){
		var selection = document.check.radionm;
	    var a=0;
		for ( var i = 0; i < selection.length; i++)
		{
			if (selection[i].checked == true)
			{
				var selectionType=selection[i].value;
	             a=1;
	          
	            
	             if(selectionType==1)
	             {
	             document.check.method="POST";
	             document.check.action="showPersonalDetails.htm?Id="+empId;
	             document.check.submit();
	             }
	             else if(selectionType==2)
	             {
	             document.check.method="POST";
	             document.check.action="showEducationDetails.htm?Id="+empId;
	             document.check.submit();
	             }
	           
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
			    document.check.method=method;
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

function showEmployeeByDept(deptid)
{

	//alert("showdeptemployee: "+deptid);
	//document.getElementById("deptwiseEmployeediv").style.display='block';	
		
		
		var url="showDeptwiseEmployee.htm?deptid="+deptid;
		
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
		 			document.getElementById("deptwiseEmployeediv").innerHTML=xmlhttp.responseText;
		 		}
		 	};
		 	xmlhttp.open("POST",url,true);
		 	xmlhttp.send();
}


function validateAllocation()
{
	var leaveStatus=document.getElementById("leaveStatus").value;
	
 
	if(leaveStatus=="1")
	{
		alert("Please Select Status");
		return false;
	}
	
}

function validateCity()
{
	alert("in validatecity");
	var selectedState=document.getElementById("stateid").value;
	var city=document.getElementById("city").value;
	/*alert("city: "+city);*/
	if(selectedState=="0")
	{
		alert("Please Select State");
		return false;
	}
	
	if(city=="")
	{
		alert("Please Enter City");
		return false;
	}
	
}

