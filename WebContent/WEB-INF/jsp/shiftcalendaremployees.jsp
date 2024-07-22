<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<ul>

	<li id="foli01" class="notranslate      "><label class="desc"
		id="title01" for="Field23">Select Employee <span id="req_23" class="req">*</span></label>
		
		<div>
		<select class="field select medium" id="employees" name="employees" onchange="showMonthCalenderDiv('showMonthCalenderDiv.htm','showCalender',this.value,0,0);">
	<option value="0" label="Select">Select</option>
	<c:forEach items="${employeeListDept}" var="employeeListDeptVar">
		<option value="${employeeListDeptVar.employeeId}" label="${employeeListDeptVar.firstName}  ${employeeListDeptVar.lastName}">${employeeListDeptVar.firstName}  ${employeeListDeptVar.lastName}</option>
	</c:forEach>
	</select>
		
		</div>

</li>
</ul>


</html>