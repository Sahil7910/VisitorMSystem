<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Privileges</title>
<SCRIPT src="include/ibox.js" type=text/javascript></SCRIPT>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>

</head>


<body>
	<div id="container" class="ltr">
		<form:form class="wufoo leftLabel page" action="showAddPrivileges.htm"
			method="POST">
			<ul>
				<li id="foli01" class="notranslate" id="foli01" class="notranslate">
					<label class="desc" id="title01" for="Field23"> Select
						Employee <span id="req_23" class="req">*</span>
				</label>
				
					<div>
					
						<select class="field select medium" id="employeeId"
							name="employeeId"
							onchange="genericAjaxFunction('showAddPrivilegesInternalDiv.htm?id='+this.value,'POST','privilege');">
							<option value="0">--Select--</option>
							<c:forEach items="${employeeList}" var="employeeListVar">
								<option value="${employeeListVar.employeeId}"
								label="${employeeListVar.firstName} ${employeeListVar.lastName}">${employeeListVar.firstName}
									${employeeListVar.lastName}</option>
							</c:forEach>
						</select>
						</div>
					
					</li>
						
					</ul>
					
					
					
				</div>
			
			<div id="privilege" style="margin-left:550px">
			</div>
			
 <INPUT class="btTxt submit" value="Submit" type="button" style="margin-top:50px" onclick="addPrivileges('addPrivileges.htm');" /> 
          
</form:form>
	
</body>
</html>
