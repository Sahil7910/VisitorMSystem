<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.distna.domain.company.Cities" %>


<select name ="language" id ="language" >
<option value="0">--Select--</option>
    <c:forEach items="${Languages}" var="statelanguage">
    <option value="${statelanguage.statewiselanguage}">${statelanguage.statewiselanguage}</option>
    </c:forEach>
    
</select>



