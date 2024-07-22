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
<form:form action="saveOrUpdateCompany.htm" method="POST"  class="wufoo leftLabel page" commandName="company" enctype="multipart/form-data">

		<form:hidden path="id" id="id"/>
<ul>
		
				<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23"> Company Name <span id="req_23"
						class="req">*</span>
				</label>
					<div>
							<form:input class="field text medium" path="companyName" id="companyName"/>
					</div> <div>
							<form:errors class="error" path="companyName" id="companyName"/>
					</div> </li>
					
					<li id="foli23" class="notranslate      "><label class="desc"
					id="title23" for="Field23">Logo<span id="req_23"
						class="req">*</span>
				</label>
					<div>
					<input type="file" id="logoFile" name="logoFile">
					<form:hidden path="logo" id="logo" value="${company.logo}"/>
					</div> 
					</li>		
					
					
				<li id="foli133" class="notranslate       "><label class="desc"
					id="title133" for="Field133"> Billing Location <span
						id="req_133" class="req">*</span>
				</label>
					<div>
						<form:select class="field select medium" path="billingLocation" id="billingLocation"  value="${company.billingLocation}">
							<form:option value="0">Select</form:option>
							<form:options items="${locationList}" itemLabel="location" itemValue="id"/>
						</form:select>
					<input class=button type="button" value="AddNew" onclick="callPopupWindowWithPost('addLocationInternally.htm','LocationWindow','GET');"/></div>
					
					
					 </li>
					 
					 
					
				<li id="foli134" class="notranslate       "><label class="desc"
					id="title134" for="Field134"> Shipping Location <span
						id="req_134" class="req">*</span>
				</label>
					<div>
					
					<form:select  class="field select medium" path="shippingLocation" id="shippinglocation"  value="${company.shippingLocation}">
						<form:option value="0">Select</form:option>
						<form:options items="${locationList}" itemLabel="location" itemValue="id"/>
					</form:select>
					
					</div></li>
				<li id="foli135" class="notranslate      "><label class="desc"
					id="title135" for="Field135"> Address <span id="req_135"
						class="req">*</span>
				</label>

					<div>
					
					<form:textarea class="field textarea medium" rows="10" cols="50" path="companyAddress" id="companyAddress"/> 
					

					</div></li>
				<li id="foli136" class="notranslate       "><label class="desc"
					id="title136" for="Field136"> Website<span id="req_136"
						class="req">*</span>
				</label>
					<div>
					<form:input path="website" class="field text medium" id="website" value="${company.website}"/>
					 (<font><strong>www.yourwebsite.domain</strong></font>)
					</div></li>
					
					<li id="foli136" class="notranslate       ">
					<div>
					<form:errors path="website" cssClass="error"/>
					</div></li>
					
					
				<li id="foli237" class="notranslate       "><label class="desc"
					id="title237" for="Field237"> First Fiscal Month </label>
					<div>
					
					
					<form:select path="firstFiscalMonth" class="field select medium">
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
					</div></li>
					
					
				<li id="foli238" class="notranslate       "><label class="desc"
					id="title238" for="Field238"> Default Currency </label>
					<div>
					
					<form:select class="field select medium" id="currency" path="defaultCurrency" onchange="setDropDownValue(this.value,'defaultCurrency');">
						<form:option value="0">Select</form:option>
       					 <c:forEach var="itemsCurrency" items="${currencyList}">
            				<form:option value="${itemsCurrency.id}" >${itemsCurrency.currency}</form:option>
        				</c:forEach>
					</form:select>
						
					</div></li>
				<li id="foli239" class="notranslate       "><label class="desc"
					id="title239" for="Field239"> Default Time Zone </label>
					<div>
					
					<form:select class="field select medium" id="zones" path="defaultTimeZone" onchange="setDropDownValue(this.value,'defaultTimeZone');">
						<form:option value="0">Select</form:option>
         				<c:forEach var="item" items="${zonesList}">
          					 <form:option value="${item.id}">${item.relativeToGmt} ${item.description}</form:option>
        				</c:forEach> 
					</form:select>
					</div></li>
					
				<li id="foli241" class="notranslate      "><label class="desc "
					id="title241" for="Field241"> No. of Leaves Allowed <span
						id="req_241" class="req">*</span>
				</label>
					<div>
					<form:input class="field text nospin medium" path="allowedLeaves"/>
					</div></li>

			<li id="foli241" class="notranslate      ">
					<div>
					<form:errors path="allowedLeaves" cssClass="error"/>
					</div></li>
					
					
				<li class="buttons ">
					<div>
						<input  class="btTxt submit" type="submit" name="save" id="save" value="Update"/>
					</div>
				</li>

			</ul>
			
			
</form:form>
</div>
</body>
</html>