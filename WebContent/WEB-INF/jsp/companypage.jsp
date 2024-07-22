<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/displayPages.js"></script>
<title>Company</title>
</head>


<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" action="saveCompany.htm" method="POST" commandName="company" enctype="multipart/form-data">
<c:choose>
<c:when test="${errorMessageFlag!=0}">


<ul>
<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Company Name <span id="req_23" class="req">*</span></label>
		<div><form:input class="field text medium" path="companyName" id="companyName"/> 
		</div>
	<form:errors path="companyName" cssClass="error"/>
</li>

<li id="foli23" class="notranslate      "><label class="desc"	id="title23" for="Field23"> Logo <span id="req_23" class="req">*</span>	</label>
					<div><input type="file" id="logoFile" name="logoFile" />
					</div>
</li>

<li id="foli133" class="notranslate       "><label class="desc"
					id="title133" for="Field133"> Billing Location <span
						id="req_133" class="req">*</span>
				</label>
					<div>
					<form:select class="field select medium" path="billingLocation" id="billingLocation">
					<form:option value="0" label="Select"></form:option>
					<form:options items="${locationList}" itemLabel="location" itemValue="id"/>
					</form:select>
					<input class="btTxt submit" type="button" value="AddNew" onclick="callPopupWindowWithPost('addLocationInternally.htm','LocationWindow','GET');"/></div>
					
</li>
					
<%-- <td>Set Shipping Location same as Billing Location<input type="checkbox" id="setShippLocation" onchange="setShippingLocation();"/></td>
<td><input class=button type="button" value="AddNew" onclick="callPopupWindowWithPost('addLocationInternally.htm','LocationWindow','GET');"/></td>
    <td>
    <div id="addLocationDiv">
	</div>

<td><form:errors path="currency" cssClass="errors"/></td>
</tr> --%>

<li id="foli134" class="notranslate       "><label class="desc"	id="title134" for="Field134"> Shipping Location <span id="req_134" class="req">*</span>	</label>
					<div>
						<form:select class="field select medium" path="shippingLocation" id="shippinglocation">
						<form:option value="0" label="Select"></form:option>
						<form:options items="${locationList}" itemLabel="location" itemValue="id"/>
						</form:select> 
					</div>
</li>




<li id="foli135" class="notranslate      "><label class="desc"
					id="title135" for="Field135"> Address <span id="req_135"
						class="req">*</span>
				</label>

					<div>
					<form:textarea class="field textarea medium" path="companyAddress" id="companyAddress"/>
					</div>
					<form:errors path="companyAddress" cssClass="error"/>
		</li>





<li id="foli136" class="notranslate       "><label class="desc"	id="title136" for="Field136"> Website<span id="req_136"	class="req">*</span>	</label>
					<div><form:input  class="field text medium" path="website" id="website"/> 
					(<font><strong>www.yourwebsite.domain</strong></font>)
					</div>
					<form:errors path="website" cssClass="error"/>
			</li>



<li id="foli136" class="notranslate       "><label class="desc"	id="title136" for="Field136">Display Logo </label>
		<div>
			<form:checkbox path="displayLogo" id="displayLogo"/>
		</div>
</li>





	<li id="foli237" class="notranslate       "><label class="desc"
					id="title237" for="Field237"> First Fiscal Month </label>
					<div>
					<form:select class="field select medium" path="firstFiscalMonth" >
					<form:option value="99" label="Select"></form:option>
					<form:option value="0" label="January"></form:option>
					<form:option value="1" label="February"></form:option>
					<form:option value="2" label="March"></form:option>
					<form:option value="3" label="April"></form:option>
					<form:option value="4" label="May"></form:option>
					<form:option value="5" label="June"></form:option>
					<form:option value="6" label="July"></form:option>
					<form:option value="7" label="August"></form:option>
					<form:option value="8" label="September"></form:option>
					<form:option value="9" label="October"></form:option>
					<form:option value="10" label="November"></form:option>
					<form:option value="11" label="December"></form:option>
					</form:select> 
				</div>
			</li>



	<li id="foli238" class="notranslate       "><label class="desc"
					id="title238" for="Field238"> Default Currency </label>
					<div>
				<select class="field select medium" id="currency" onchange="setDropDownValue(this.value,'defaultCurrency');">
			        <option value="0" selected="selected" >select</option>
			        <c:forEach var="itemsCurrency" items="${currencyList}">
			            <option value="${itemsCurrency.id}">${itemsCurrency.currency}</option>
			        </c:forEach>
				</select>
				</div>
				<form:hidden path="defaultCurrency" id="defaultCurrency" name="defaultCurrency"></form:hidden>
		</li>
		
		
		
	<li id="foli239" class="notranslate       "><label class="desc"
					id="title239" for="Field239"> Default Time Zone </label>
					<div>
			<select class="field select medium" id="zones" onchange="setDropDownValue(this.value,'defaultTimeZone');">
			        <option value="0" selected="selected">select</option>
			        <c:forEach var="item" items="${zonesList}">
			            <option value="${item.id}">${item.relativeToGmt} ${item.description}</option>
			        </c:forEach>
			</select>
		</div>
			<form:hidden path="defaultTimeZone" id="defaultTimeZone" name="defaultTimeZone" ></form:hidden>
	</li>
	
	
	
	

<li id="foli241" class="notranslate      "><label class="desc "
					id="title241" for="Field241"> No. of Leaves Allowed <span
						id="req_241" class="req">*</span>
				</label>
					<div><form:input path="allowedLeaves"/>
				</div>
					<form:errors path="allowedLeaves" cssClass="error"/>
		</li>			
				

<li id="foli241" class="notranslate      "><label class="desc "
					id="title241" for="Field241"></label>
					<div><input   class="btTxt submit"  type="submit" name="save" id="save" value="save"/>
				</div>
		</li>
	


</ul>


</c:when>
<c:otherwise>
<c:if test="${errorMessageFlag==0}">
<div style="width: 100%;color: red;" align="center">
${errorMessage}
</div>
</c:if>
</c:otherwise>
</c:choose>
</form:form>
</div>
</body>
</html>