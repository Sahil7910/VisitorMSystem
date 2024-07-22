<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.distna.domain.company.Cities" %>

<select class="field select medium" name ="city" id ="city" >
<option value="0">--Select--</option>
    <c:forEach items="${Cities}" var="cities">
    <option value="${cities.cityId}">${cities.city}</option>
    </c:forEach>
</select>



