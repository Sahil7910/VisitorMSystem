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
<title>Employee</title>
<script type="text/javascript" src="javascript/department.js"></script>
<script type="text/javascript" src="javascript/location.js"></script>
</head>
<body>
<div id=maincontent>

<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table >
		<tr >
			<td>&nbsp;</td>
			<!-- <th style="color: white; ">Id</th> -->
			<!-- <th style="color: white; ">Emp No.</th> -->
			<td>Emp ID</td>
			<td>Name</td>
			<td>Location</td>
			<td>Division</td>
			<td>Department</td>
			<td>Workspace</td>
			<td>Designation</td>
			<!-- <th style="color: white; ">Address</th> -->
			<!-- <th style="color: white; ">Country</th>
			<th style="color: white; ">State</th>
			<th style="color: white; ">City</th> -->
			<td>Email</td>
			<td>Mobile</td>
			<!-- <th style="color: white; ">Workspace</th> -->
		<!-- 	<th style="color: white; ">Joining Date</th> -->
			<!-- <th style="color: white; ">Division</th> -->
			<!-- <th style="color: white; ">Employment Status</th> -->
		</tr>
		<c:forEach items="${employeeList}" var="employees" varStatus="index">
		<%-- <c:if test="${employees.employeeNo!=0}"> --%>
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${employees.employeeId}"/>"/></td>
		
			<td> ${employees.employeeId} </td>
			<td>${employees.firstName} ${employees.lastName} </td>
			
			
			
			
			
			<td><font color="black">
			<c:forEach items="${locationList}" var="locationListVar">
			<c:if test="${locationListVar.id==employees.location}">
			${locationListVar.location}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			
			
			<td><font color="black">
			<c:forEach items="${divisionList}" var="divisionListVar">
			<c:if test="${divisionListVar.divisionId==employees.division}">
			${divisionListVar.divisionName}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			
			<td><font color="black">
			<c:forEach items="${departmentsList}" var="departmentsListVar">
			<c:if test="${departmentsListVar.id==employees.departmentId}">
			${departmentsListVar.name}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			
			
			<td><font color="black">
			<c:forEach items="${workspaceList}" var="workspaceListVar">
			<c:if test="${workspaceListVar.id==employees.workspace}">
			${workspaceListVar.workspaceName}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			<td><font color="black">
			<c:forEach items="${designationList}" var="designationListVar">
			<c:if test="${designationListVar.id==employees.designation}">
			${designationListVar.designation}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			
			
			<%-- <td><font color="black">
			<c:forEach items="${countriesList}" var="countriesListVar">
			<c:if test="${countriesListVar.countryId ==employees.currentCountry}">
			${countriesListVar.country}
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			<td><font color="black">
			<c:forEach items="${statesList}" var="statesListVar">
			<c:if test="${statesListVar.stateId ==employees.currentState}">
			${statesListVar.stateName }
			</c:if>
			</c:forEach>
			 </font>
			</td>
			
			<td><font color="black">
			<c:forEach items="${cityList}" var="cityListVar">
			<c:if test="${cityListVar.cityId==employees.currentCity }">
			${cityListVar.city}
			</c:if>
			</c:forEach>
			 </font>
			</td> --%>
			
			
			<%-- <c:set var="break" value ="true"/>
			<c:forEach items="${deptarr}" var="dept" varStatus="deptIndex">
				<c:if test="${break eq 'true'}">
					<c:if test="${deptIndex.index==index.index}">
						<c:forEach items="${departments}" var="department" varStatus="dept1Index">
							<c:if test="${dept==department.id}">
								<c:set var="flag" value="1"></c:set>
								<c:set var="deptName" value="${department.name}"></c:set>
								<c:set var="break" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			
			<%
			String flag=null;
			String deptName=null;
			if(null!=pageContext.getAttribute("flag4"))
			{
				flag=(String)pageContext.getAttribute("flag");
				deptName=(String)pageContext.getAttribute("deptName");
			}
			else
			{
				flag="0";
				deptName="0";
			}	
				if(flag.equals("1"))
				{
			%>
		 	<td><font color="black"><%=deptName%></font></td>	 
			<%  } %>
			
			<td>${employees.firstName} ${employees.lastName} </td>
			
			<c:set var="break4" value ="true"/>
			<c:forEach items="${designationarr}" var="designationarr" varStatus="designationIndex">
				<c:if test="${break4 eq 'true'}">
					<c:if test="${designationIndex.index==index.index}">
						<c:forEach items="${designationList}" var="designation" varStatus="designationListIndex">
							<c:if test="${designationarr==designation.id}">
								<c:set var="flag4" value="1"></c:set>
								<c:set var="designationName" value="${designation.designation}"></c:set>
								<c:set var="break4" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
			String flag4=null;
			String designationName=null;
			if(null!=pageContext.getAttribute("flag4"))
			{
				flag4=(String)pageContext.getAttribute("flag4");
				designationName=(String)pageContext.getAttribute("designationName");
			}
			else
			{
				flag4="0";
				designationName="0";
			}	
				if(flag4.equals("1"))
				{
			%>
		 	<td><font color="black"><%=designationName%></font></td>	 
			<%  } %>
			
			
			<td>${employees.permanentAddress}</td>
			<c:set var="break1" value ="true"/>
			<c:forEach items="${countryarr}" var="country" varStatus="countryIndex">
				<c:if test="${break1 eq 'true'}">
					<c:if test="${countryIndex.index==index.index}">
						<c:forEach items="${countries}" var="countries" varStatus="countriesIndex">
							<c:if test="${country==countries.countryId}">
								<c:set var="flag1" value="1"></c:set>
								<c:set var="countrynname" value="${countries.country}"></c:set>
								<c:set var="break" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
			String flag1="0";
			String countrynname="0";
			if(null!=pageContext.getAttribute("flag4"))
			{
				flag1=(String)pageContext.getAttribute("flag1");
				countrynname=(String)pageContext.getAttribute("countrynname");
			}
				if(flag1.equals("1"))
				{
			%>
		 	<td><font color="black"><%=countrynname%></font></td>	 
			<%  } %>
			
			<c:set var="break2" value ="true"/>
			<c:forEach items="${statearr}" var="state" varStatus="stateIndex">
				<c:if test="${break2 eq 'true'}">
					<c:if test="${stateIndex.index==index.index}">
						<c:forEach items="${states}" var="states" varStatus="statesIndex">
							<c:if test="${state==states.stateId}">
								<c:set var="flag2" value="1"></c:set>
								<c:set var="stateName" value="${states.stateName}"></c:set>
								<c:set var="break2" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag2="0";
				String stateName="0";
				if(null!=pageContext.getAttribute("flag4"))
				{
				flag2=(String)pageContext.getAttribute("flag2");
				stateName=(String)pageContext.getAttribute("stateName");
				}
				if(flag2.equals("1"))
				{
			%>
		 	<td><font color="black"><%=stateName%></font></td>	 
			<%  } %>
			<c:set var="break3" value ="true"/>
			<c:forEach items="${cityarr}" var="cityarr" varStatus="cityIndex">
				<c:if test="${break3 eq 'true'}">
					<c:if test="${cityIndex.index==index.index}">
						<c:forEach items="${city}" var="cities" varStatus="citiesIndex">
							<c:if test="${cityarr==cities.cityId}">
								<c:set var="flag3" value="1"></c:set>
								<c:set var="cityName" value="${cities.city}"></c:set>
								<c:set var="break3" value="false"></c:set>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
			<%
				String flag3=(String)pageContext.getAttribute("flag3");
				String cityName=(String)pageContext.getAttribute("cityName");
				if(flag2.equals("1"))
				{
			%>
		 	<td><font color="black"><%=cityName%></font></td>	 
			<%  } %> --%>
			
			<td><font color="black">${employees.email}</font></td>
			<td><font color="black">${employees.mobile}</font></td>
			
			<%-- <td><font color="black">${employees.employment_status}</font></td> --%>
			
			
		 </tr>
		<%-- </c:if> --%>
		</c:forEach>
	</table>
</div>
</div>
<table  width="100%"  style=" margin-top: 2%;">
	<tr>
		<td align="center"><input type="button"  style="background-color:lightgray; margin-right: 2%;" name="method" value="DELETE" onclick="return deleteDepartment(${employeeListSize},'deleteEmployee.htm','POST');"/><input type="button"  style="background-color:lightgray; margin-left: 2%;" name="method" value="UPDATE" onclick="return deleteDepartment(${employeeListSize},'showupdateEmployee.htm','POST');"/></td>
		<%-- <td><input type="button" class=button name="method" value="ADD" onclick="return deleteDepartment(${employeeListSize},'additionalEmployeeInfo.htm','POST');"/></td> --%>
	</tr>
</table>

</form:form>
</div>
</body>
</html>