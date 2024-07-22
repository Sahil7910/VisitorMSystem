<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<select id="deptEmployee" name="deptEmployee"   class="select medium">
	     <option value="0">--Select--</option>
	      <c:forEach items="${employeeList}" var="employee">
	          <option value="${employee.employeeNo}">${employee.firstName} ${employee.lastName}(${employee.employeeNo})</option>
          </c:forEach>
</select>