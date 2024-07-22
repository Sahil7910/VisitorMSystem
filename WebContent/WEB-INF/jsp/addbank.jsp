<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>


<%
String loginUser = (String) session.getAttribute("loginUser");
String createdOn = Calendar.getInstance().getTime().toString();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
<script language="JavaScript" src="include/jquery.js"></script>
<script language="JavaScript" src="include/jsfunctions.js"></script>



<title>Bank Details</title>
</head>
<body id="public">
	<div id="container" class="ltr">

		<form:form encType="multipart/form-data" class="wufoo leftLabel page"
			action="addbank.htm" method="POST" commandName="employeeBank">


			<ul>
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Employee <span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:select path="employeeId" class="field select medium">
							<form:option value="0">Select</form:option>
							<c:forEach items="${employeeList}" var="employee">
								<form:option value="${employee.employeeId}"
									label="${employee.firstName} ${employee.lastName}">${employee.firstName}&nbsp;${employee.lastName}</form:option>
							</c:forEach>
						</form:select>
					</div> <form:errors path="employeeId" cssClass="error" /></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Bank Name <span id="req_23"
						class="req">*</span>
				</label>
					<div>
						<form:input path="BankName" id="BankName" name="BankName"
							class="field text medium" />
					</div> <form:errors path="BankName" cssClass="error"></form:errors></li>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Bank Branch Name <span id="req_23"
						class="req">*</span></label>
					<div> 
					<form:input path="BankBranchName" id="BankBranchName" name="BankBranchName"
						class="field text medium" />
					</div>
						<form:errors path="BankBranchName" cssClass="error"></form:errors>
					</li>



			<li id="foli110" class="notranslate"><label class="desc"
			id="title110" for="Field110"> branchFullAddress <span id="req_23" class="req">*</span>
			</label>
	<div>
	<form:textarea class="field textarea medium" rows="10" cols="50"
		path="branchFullAddress" id="branchFullAddress" name="branchFullAddress" />
				</div>
				</li>
	
	
	
	
				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Account Number <span id="req_23"
						class="req">*</span></label>
					<div>
					 <form:input path="accountNo" id="accountNo" name="accountNo" class="field text medium" />
							</div>
						<form:errors path="accountNo" cssClass="error"></form:errors>
					</li>
	
		
					<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> IFSC CODE <span id="req_23"
						class="req">*</span></label>
					<div>
					 <form:input path="IFSC_CODE" id="IFSC_CODE" name="IFSC_CODE"
						class="field text medium" />
							</div>
						<form:errors path="IFSC_CODE" cssClass="error"></form:errors>
					</li>
					
					
<%-- 					<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> PAN Number <span id="req_23"
						class="req">*</span></label>
					<div>
					 <form:input path="PANNo" id="PANNo" name="PANNo" class="field text medium" />
							</div>
						<form:errors path="PANNo" cssClass="error"></form:errors>
					</li>
 --%>


				<li id="foli110" class="notranslate      "><label class="desc"
					id="title110" for="Field110"> Attachment </label>
					<div>
						<input type="file" id="attachmentFile" name="attachmentFile" />
					</div></li>


				<li id="foli02" class="notranslate      "><label class="desc"
				id="title23" for="Field23">Created By</label>
				<div>
					<label class="desc" id="title23" for="Field23"><%=loginUser%></label>
				</div> <form:hidden path="createdBy" value="<%=loginUser%>" /></li>
 
 				<%-- <tr>
				<td>&nbsp;&nbsp;Created By</td>
				<td>&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</td>
				<td><form:label path="createdBy"><%=loginUser%></form:label></td>
			</tr> --%>
 	

	<li id="foli110" class="notranslate      "><label class="desc"
		id="title110" for="Field110"> Created On </label>
		<div>
			<form:label path="createdOn" value="${employeeBank.createdOn}">${employeeBank.createdOn}</form:label>
		</div>
		</li>


	<li id="foli1" class="notranslate      "><label class="desc"
		id="title1" for="Field1"> <span id="req_23" class="req">*-Required
				Fields</span>
	</label>
	</li>


	<li class="buttons ">
		<div>
			<input class="btTxt submit" type="submit" name="save" id="save"
				value="Save" />
		</div>
	</li>
	</ul> 

	</form:form>
	</div>
</body>
</html>