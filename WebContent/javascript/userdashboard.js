function changeDiv(divName)
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

	 			document.getElementById("profileDiv").innerHTML=xmlhttp.responseText;
	 		}
	 	};
                if(divName=="companyDiv")
                    {
	 	xmlhttp.open("POST","employeeCompany.jsp",true);
                    }
                else if(divName=="employeeDiv")
                    {
                        xmlhttp.open("POST","employeeNames.jsp",true);
                    }
                else if(divName=="leaveDiv")
                    {
                       xmlhttp.open("POST","employeeLeaves.jsp",true);
                    }
                else if(divName=="attendanceDiv")
                    {
                        xmlhttp.open("POST","employeeAttendance.jsp",true);
                    }
	 	xmlhttp.send();
    
    }