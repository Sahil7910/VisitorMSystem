<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<select class="field select medium" name ="state" id ="state" onchange="showCities(this.value);document.getElementById('cityLable').style.display= 'block';document.getElementById('cityButton').style.display= 'block';">
<option value="0">--Select--</option>
    <c:forEach var="state" items="${States}">
    <option value="${state.stateId}">${state.stateName}</option>
    </c:forEach>
</select>
