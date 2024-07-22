<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bank Details</title>

<script type="text/javascript" src="javascript/department.js"></script>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr>
			<td>&nbsp;</td>
			<td>Id</td>
			<td>Employee Name</td>
			<td>Bank Name</td>
			<td>Branch Name</td>
			<td>Branch Add</td>
			<td>Account No</td>
			<td>IFSC Code</td>
			<td>Created By</td>
			<td>Created On</td>
			
		</tr>
		<c:forEach items="${BankList}" var="bank" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${bank.id}"/>"/></td>
			<td>${index.index+1}</td>
			
			<c:forEach items="${employeeList}" var="employeeVar">
			<c:if test="${bank.employeeId==employeeVar.employeeId}">
				<td>${employeeVar.firstName}&nbsp;${employeeVar.lastName}</td>
			</c:if>
			
			</c:forEach>
			
			<td>${bank.bankName}</td>
			<td>${bank.bankBranchName}</td>
			<td>${bank.branchFullAddress}</td>
			<td>${bank.accountNo}</td>
			<td>${bank.IFSC_CODE}</td>
			<td>${bank.createdBy}</td>
			<td>${bank.createdOn}</td>
		 </tr>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td ><input type="button" style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return updateDelete(${BankListSize},'deleteemployeeBank.htm');"/><input type="button" style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return updateDelete(${BankListSize},'showupdateemployeesbank.htm');"/></td>
	</tr>
</table>

</form:form>
</div>
</body>
</html>