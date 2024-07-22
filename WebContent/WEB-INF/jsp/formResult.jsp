<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Class ${savedClass.className}</h2>

<p>Students</p>

<ul>
	<c:forEach var="student" items="${savedClass.students}">
		<li>${student.studentName}</li>
	</c:forEach>
</ul>

<%-- <select>
				<c:forEach var="student" items="${savedClass.students}">
					<option>${student.studentName}</option>
				</c:forEach>
</select> --%>