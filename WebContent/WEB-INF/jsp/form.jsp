<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
 <script type="text/javascript" src="javascript/jquery-1.3.2.min.js"></script>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function() {
	var studentPosition = 0;
	$("#addStudentButton").click(function() {
		alert(studentPosition);
		studentPosition++;

		$.get("appendStudentViewInner.htm", { fieldId: studentPosition},
			function(data){
			alert(data);
				$("#submitRow").before(data);
		});
	});
});
</script>

<form:form action="processClassForm.htm" method="post" name="classForm" id="classForm" commandName="classCommand">
	<table>
		<tr>
			<td>Class Name:</td>
			<td colspan="2"><form:input path="className" size="40" /></td>
		</tr>
		<tr>
			<td colspan="3"><strong>Students</strong></td>
		</tr>
		
		<%-- <tr>
			<td>Student List</td>
			<td id="studentListTd">
				<select>
				<c:forEach var="student" items="${savedClass.students}">
					<option>${student.studentName}</option>
				</c:forEach>
				</select>
			</td>
		</tr> --%>
		<tr>
			<td>Student name</td>
			<td>
				<spring:bind path="classCommand.students[0].studentName">
					<form:input path="${status.expression}" size="40" />
	  			</spring:bind>
			</td>
			<td><input type="button" id="addStudentButton" value="Add" /></td>
		</tr>
		
		
		<tr id="submitRow">
			<td>&nbsp;</td>
			<td><input type="submit" value="Save Class" id="saveButton"/></td>
		</tr>
	</table>
</form:form>