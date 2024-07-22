
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
</head>

<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" name="updatelocation" id="updatelocation" action="updatelocation.htm?Id=${Id}" commandName="locationObj" method="POST" enctype="multipart/form-data">

<ul>

<li id="foli1" class="notranslate      ">
<label class="desc" id="title1" for="Field1">
Location<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="location" name="location" class="field text medium"/>
</div>
<form:errors path="location" cssClass="error"/>
</li>

<li id="foli103" class="notranslate       ">
<label class="desc" id="title103" for="Field103">
Parent Location
</label>
<div>   
    <form:select path="sub_locationof" class="field select medium">
     <form:option value="0">select</form:option>
    <c:forEach var="locationListVAr" items="${allLocationList}">
    <form:option value="${locationListVAr.id}">${locationListVAr.location}</form:option>
    </c:forEach>
    </form:select>
</div>
</li>


<li id="foli110" 
class="notranslate      "><label class="desc" id="title110" for="Field110">
Address1<span id="req_23" class="req">*</span>
</label>
<div>
<form:textarea class="field textarea medium" rows="10" cols="50" path="address1" id="address1"/>

</div>
<form:errors path="address1" cssClass="error"/>
</li>


<li id="foli111" 
class="notranslate      "><label class="desc" id="title111" for="Field111">
Address2
</label>

<div>
<form:textarea class="field textarea medium" rows="10" cols="50" path="address2" id="address2"/>

</div>
<form:errors path="address2" cssClass="error"/>
</li>


<li id="foli112" class="notranslate       ">
<label class="desc" id="title112" for="Field112">
Country<span id="req_23" class="req">*</span>
</label>
<div>


 <form:select path="country" class="field select medium" onchange="showState(this.value);addCity('${redirectUrlupdate}')" id="currentCountry">
    <form:option value="0" label="Select">Select</form:option>
    <c:forEach var="country" items="${countries}">
    <form:option value="${country.countryId}">${country.country}</form:option>
    </c:forEach>
    </form:select>
</div>
     <form:errors path="country" cssClass="error"/>
</li>


<li id="foli112" class="notranslate       ">
<label class="desc" id="title112" for="Field112">
State<span id="req_23" class="req">*</span>
</label>
<div id="statediv">
 <select name ="state" id ="state" class="field select medium" onchange="showCities(this.value)">
    <c:forEach var="stateVar" items="${States}">
    <c:choose>
    <c:when test="${stateVar.stateId==locationObj.state}">
    <%-- <option value="${stateVar.stateId}" selected="selected">${stateVar.stateName}</option> --%>
    <option value="${stateVar.stateId}" selected="selected">${stateVar.stateName}</option>
    </c:when>
    <c:otherwise>
    <option value="${stateVar.stateId}">${stateVar.stateName}</option>
    </c:otherwise>
    </c:choose>
    </c:forEach>
	</select>
</div>
<form:errors path="state" cssClass="error"/>
</li>


<li id="foli112" class="notranslate       ">
<label class="desc" id="title112" for="Field112">
City<span id="req_23" class="req">*</span>
</label>
<div id="showCitiesDiv">
 <select name ="city" id ="city" class="field select medium">
    <c:forEach items="${Cities}" var="cities">
    <c:choose>
    <c:when test="${cities.cityId==locationObj.city}">
      <option value="${cities.cityId}" selected="selected">${cities.city}</option>
    </c:when>
    <c:otherwise>
     <option value="${cities.cityId}">${cities.city}</option>
    </c:otherwise>
    </c:choose>
    </c:forEach>
	</select>
</div>
<div id="addNewButtonDiv" style="display: none;" > <input type="button" value="Add new" onclick="addNewCityDiv();"/></div>
<form:errors path="city" cssClass="error"/>
</li>







<li id="foli113" class="notranslate      ">
<label class="desc" id="title113" for="Field113">
Phone
</label>
<div>
<form:input path="phone" name="phone" class="field text medium"/>
</div>
 <form:errors path="phone" cssClass="error"/>
</li>
<!-- <li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li> -->


<li id="foli114" class="notranslate      ">
<label class="desc" id="title114" for="Field114">
Email
</label>
<div>
<form:input path="email" name="email" class="field text medium"/>
</div>
 <form:errors path="email" cssClass="error"/>
</li>

<!-- <li id="foli136" class="notranslate       ">
					<div>
					 
					</div></li> -->

<li id="foli115" class="notranslate       ">
<label class="desc" id="title115" for="Field115">
Website
</label>
<div>
<form:input path="website" name="website" class="field text medium"/>
</div>
 <form:errors path="website" cssClass="error"/>
</li>

<!-- <li id="foli136" class="notranslate       ">
					<div>
					 
					</div>
</li> -->

<li id="foli116" 
class="notranslate      "><label class="desc" id="title116" for="Field116">
Details
</label>
<div>
<form:textarea path="details" name="details" class="field textarea medium"/>
</div>
</li>
<!-- <li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li> -->

<li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
 Postal Code<span id="req_23" class="req">*</span>
</label>
<div>
<form:input path="postal_code" name="postal_code"  class="field text medium"/>
</div>
 <form:errors path="postal_code" cssClass="error"/>
</li>
<!-- <li id="foli136" class="notranslate       ">
					<div>
					
					</div>
</li> -->


<li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
Fax
</label>
<div>
<form:input path="fax" name="fax" class="field text medium"/>
</div>
</li>
<!-- <li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li> -->


<%-- <li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
Gps Location
</label>
<div>
<form:input path="gps_location" name="gps_location" class="field text medium"/>
</div>
</li>
<li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li>



<li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
Allowed IPs
</label>
<div>
<form:textarea path="allowed_ips" name="allowed_ips" class="field textarea medium"/>
</div>
</li>
<li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li>



<li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
Attention
</label>
<div>
<form:input path="attention" name="attention" class="field text medium"/>
</div>
</li>

<li id="foli136" class="notranslate       ">
					<div>
					   
					</div></li> --%>


<li id="foli117" class="notranslate      ">
<label class="desc" id="title117" for="Field117">
Active
</label>
<div>
<form:checkbox path="active" name="active" value="active" class="field checkbox"/>
</div>
</li>

<li class="buttons ">
<div>
    <input  type="submit"  value="Update"  class="btTxt submit"/>
    <input  type="button"  value="Reset" onclick="showupdatelocation.htm" />
    </div>
</li>
</ul>
</form:form>
</div>
</body>
</html>