<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<script type="text/javascript" src="javascript/displayPages.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company List</title>
</head>
<body>
<div id=maincontent>
<form:form name="check" method="POST">
<div class="CSSTableGeneratorOutter">
<div class="CSSTableGenerator">
	<table>
		<tr>
			<td >&nbsp;</td>
			<td >Id</td>
			<td >Name</td>
			<td >Logo</td>
			<td >First Fiscal Month</td>
			<td >Currency</td>
			<td >Default Time Zone</td>
			<td >Company Address</td>
		</tr>
		<c:forEach items="${companyList}" var="companyListVar" varStatus="index">
		<tr>
			<td ><input type="radio" name="radionm" id="radioid" value="<c:out value="${companyListVar.id}"/>"/></td>
			<td>${(index.index)+1}</td>
			<td >${companyListVar.companyName}</td>
			<td ><img alt="image" src="${companyListVar.logo}" width="40px"/></td>
			<td >
			<c:forEach items="${monthsList}" var="monthsListVar" varStatus="monthsIndex">
			<c:if test="${monthsIndex.index==companyListVar.firstFiscalMonth}">
			${monthsListVar}
			</c:if>
			</c:forEach>
			</td>
			<c:forEach var="itemsCurrency" items="${currencyList}">
         	<c:if test="${companyListVar.defaultCurrency==itemsCurrency.id}">
         	<td>${itemsCurrency.currency}</td>
        	 </c:if>
         	</c:forEach>
        	
        	<c:forEach var="item" items="${zonesList}">
         <c:if test="${companyListVar.defaultTimeZone==item.id}">
         <td >${item.relativeToGmt} ${item.description}</td>
         </c:if>
        </c:forEach>
			<td >${companyListVar.companyAddress}</td>
		 </tr>
		</c:forEach>
	</table>

</div>
</div>
<table width="100%"  style=" margin-top: 2%;">
	<tr>
		<td align="center"><input style="background-color:lightgray; margin-right: 2%;" type="button" name="method" value="UPDATE" onclick="return updateDelete(${companyListSize},'updateCompany.htm');"/> <input style="background-color:lightgray; margin-left: 2%;" type="button" name="method" value="DELETE" onclick="return updateDelete(${companyListSize},'deleteCompany.htm')"/></td>
	</tr>
</table>
</form:form>
</div>
</body>
</html>