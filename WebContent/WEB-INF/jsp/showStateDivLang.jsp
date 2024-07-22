<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<select name ="state" id ="state" onchange="showLanguages(this.value)">
<option value="0">--Select--</option>
    <c:forEach var="state" items="${States}">
    <option value="${state.stateId}">${state.stateName}</option>
    </c:forEach>
</select>
