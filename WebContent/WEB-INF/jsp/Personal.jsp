<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.distna.utility.DateTime"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD align="left" width=150 HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
<title>Insert title here</title>

<script type="text/javascript" src="javascript/location.js"></script>
<script type="text/javascript" src="javascript/countries2.js"></script>
</head>
<%
	String dateFormat = "dd-MM-yyyy";
	String dateNow = DateTime.CurrentDate(dateFormat);
	Calendar c_month = Calendar.getInstance();
	Calendar c_week = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	int month = Calendar.MONTH;
	int day = Calendar.DATE;
	c_month.add(Calendar.MONTH, -1);
	c_week.add(Calendar.DATE, -7);
	String lastMonthDate = sdf.format(c_month.getTime());
	String lastWeekDate = sdf.format(c_week.getTime());
	String year = dateNow.substring(7, 10);
	String month1 = dateNow.substring(3, 5);
	int iYear = Integer.parseInt(year);
	String loginUser=(String)session.getAttribute("loginUser");
	
%>
<c:choose>
<c:when test="${dynamicActionFlag==0}">
<c:url var="actionAddPersonal" value="addPersonalDetails.htm?Id=${employeeObj.employeeId}"></c:url>
</c:when>
<c:otherwise>
<c:url var="actionAddPersonal" value="addPersonalDetailsFromPersonalAdd.htm?Id=${employeeObj.employeeId}"></c:url>
</c:otherwise>
</c:choose>


<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" commandName="employeeObj" action="${actionAddPersonal}" method="POST" >
	<form:hidden path="photo"/>
	<%-- <form:hidden path="deviceEmployeeNo"/> --%>
	<%-- <form:hidden path="firstName"/>
	<form:hidden path="lastName"/> --%>
	<form:hidden path="email"/>
	<form:hidden path="password"/>
	<form:hidden path="adminFlag"/>
	<form:hidden path="departmentId"/>
	<form:hidden path="departmentId"/>
	<form:hidden path="division"/>
	<form:hidden path="designation"/>
	<form:hidden path="supervisor"/>
	<form:hidden path="location"/>
	<form:hidden path="workspace"/>
	<form:hidden path="groupId"/>
	<form:hidden path="introduction"/>
	<form:hidden path="workPhone"/>
	<form:hidden path="workPhoneExt"/>
	<form:hidden path="faxPhone"/>
	<%-- <form:hidden path="currentAddress"/> --%>
	<form:hidden path="currentAddress2"/>
	<form:hidden path="currentCountry"/>
	<form:hidden path="currentState"/>
	<form:hidden path="currentCity"/>
	<form:hidden path="currentPostalCode"/>
	<form:hidden path="mobile"/>
	<form:hidden path="jobCode"/>
	<form:hidden path="employment_status"/>
	<form:hidden path="createdOn"/>
	<form:hidden path="createdBy"/>
    <form:hidden value="${employeeObj.employeeId}" path="employeeId" id="employeeid" name="employeeid" />
	<form:hidden path="employeeNo" value="${employeeObj.employeeNo}"/>
	  
	<ul>
			
			  
			
				
	<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> First Name <span id="req_23" class="req">*</span>	</label>
		<div>
			<form:input class="field text medium" path="firstName" readonly="true"/>
		</div>
	</li>


		<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Last Name <span id="req_23"	class="req">*</span></label>
			<div>
				<form:input class="field text medium" path="lastName" readonly="true" />
			</div>
		</li>

		<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Marital Status</label>
			<div>
				  <form:select  class="field select medium" id="value_marital_status" name="value_marital_status" path="maritalStatus">
			          <form:option value="0" >Select</form:option>
			          <form:option value="Single">Single</form:option>
			          <form:option value="Married">Married</form:option>
		          </form:select> 
			</div>
		</li>
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Gender</label>
			<div>
			<form:select class="field select medium"  id="value_gender" name="value_gender" path="gender">
	          <form:option value="0" >Select</form:option>
	          <form:option value="Male">Male</form:option>
	          <form:option value="Female">Female</form:option>
          	</form:select> 
		</div>
		</li>
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Date Of Birth</label>
			<div>
	          <c:choose>
	          <c:when test="${dateOfBirth==''}">
	           <form:input class="field text medium" type="text" id="todate" name="todate" path="dateOfBirth" value="<%=dateNow%>" readonly="true" />
	          </c:when>
	         <c:otherwise>         
	         <form:input class="field text medium" type="text" id="todate" name="todate" path="dateOfBirth" readonly="true"/> 
	         </c:otherwise>
	          </c:choose>
			&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal" onClick="setYears(1950, 2050);showCalender(this, 'dateOfBirth',-350,-270);"></img>
			</div>
		</li>
	
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Father/Husband Name</label>
			<div>
				<form:input class="field text medium" type="text" name="value_father_husband_name" path="fatherhusbandName" /> 
         	</div>
         </li>
          
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Home Phone</label>
			<div>
				<form:input class="field text medium" type="text" name="value_home_phone" path="homePhone"/> 
			</div>
				<form:errors path="homePhone" cssClass="error"/>
	  </li>
	  
	  
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Home Email</label>
			<div>
				<form:input class="field text medium"  type="text" name="value_home_email" path="homeEmail"/> 
			</div>
			<form:errors path="homeEmail" cssClass="error"/>
	  </li>
	  
	  
	  
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Personal Email</label>
			<div>
				<form:input class="field text medium"  type="text" name="value_personal_emails" path="personalEmails" /> 
			</div>
			<form:errors path="personalEmails" cssClass="error"/>
		</li>
		
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Children Count</label>
			<div>
				<form:select  class="field select medium" id="value_children_count" name="value_children_count" path="childrenCount" >
			          <form:option value="0" >Select</form:option>
			          <form:option value="0">0</form:option>
			          <form:option value="1">1</form:option>
			          <form:option value="2">2</form:option>
			          <form:option value="3">3</form:option>
			          <form:option value="4">4</form:option>
			          <form:option value="5">5</form:option>
			          <form:option value="6">6</form:option>
			          <form:option value="7">7</form:option>
			          <form:option value="8">8</form:option>
			          <form:option value="9">9</form:option>
			          <form:option value="10">10</form:option>
			     </form:select> 
			 </div>
		</li>
		
		
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Emergency Contact</label>
			<div>
				<form:textarea class="field textarea medium" name="value_emergency_contact" path="emergencyContact"></form:textarea>
			</div>
		</li>
			
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Relatives/Friends</label>
			<div>
				<form:textarea class="field textarea medium"  name="value_relatives_friends" path="relativesFriends"></form:textarea> 
			</div>
	   </li>
				
      <%--  <li id="fo1li244" class="notranslate  threeColumns     "><fieldset><![if !IE | (gte IE 8)]><legend id="title244" class="desc">Languages</legend><![endif]>
		       <!--[if lt IE 8]><label id="title244" class="desc">
				Language
				</label>
				<![endif]-->
			<div>
			<span><form:checkbox  class="field checkbox" path="languages" name="type_language_English" value="English" label="English" /></span>
			<span><form:checkbox class="field checkbox" path="languages" name="type_language_Hindi" value="Hindi" label="Hindi" /></span>
			<span><input class="field checkbox" type="checkbox" name="type_language_Other"  value="Other" onchange="javascript:showCountry();"/>Other</span>
			</div>
		</fieldset>
	</li>
			
			
			
          <tr>
          <td width=100>
          <select onclick="showStateLanguage(this.value);" style="width: 150px; display: none;" id="showcountry">
          <option value="105">India</option>
          </select>
          </td>
          <td align="left" width=150>
    		<div id="statedivlang"></div>
   		  </td>
   		    <td align="left" width=250 ><div id="showLanguageDiv"></div></td>
   		  
          </tr> --%>
          
          
          
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Skills</label>
			<div>
				<form:input class="field text medium" type="text" name="value_skills" path="skills" ></form:input>
			</div>
		</li>
				
				
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Years of Experience</label>
			<div>
				<form:input class="field text medium" type="text" id="value_yrs_experience" path="yearsExperience" ></form:input>
			</div>
		</li>
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Education</label>
			<div>
				<form:input class="field text medium" type="text" name="value_education" path="education" ></form:input>
			</div>
		</li>
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Passing Year</label>
			<div>
						<c:choose>
							<c:when test="${passingYear==''}">
								<form:input class="field text medium" type="text" id="type_passing_year"
									name="type_passing_year" path="passingYear"
									value="<%=dateNow%>" readonly="true" size="15" />
							</c:when>
							<c:otherwise>
								<form:input class="field text medium" type="text" id="type_passing_year"
									name="type_passing_year" path="passingYear" readonly="true"
									size="15" />
							</c:otherwise>
						</c:choose>
						&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal" onClick="setYears(1960, 2020);showCalender(this, 'passingYear',-350,-270);"></img>
			</div>
		</li>
		
		
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> College Name</label>
			<div>
				<form:input class="field text medium" type="text" name="value_college_name" path="collegeName" ></form:input>
			</div>
		</li>
				
				
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> University Name</label>
			<div>
				<form:input class="field text medium" type="text" name="value_university_name"  path="universityName" ></form:input>
			</div>
		</li>
		
		
        <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Current Address</label>
			<div>
				<form:input class="field text medium" type="text" name="value_current_address" path="currentAddress" ></form:input>
			</div>
		</li>
		
		
		
		
       <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Permanent Address 1</label>
			<div>
				<form:input class="field text medium" type="text" name="value_permanent_address"  path="permanentAddress" ></form:input>
			</div>
		</li>		
				
				
         <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Permanent Address2</label>
			<div>
				<form:input class="field text medium" type="text" name="value_permanent_address2" path="permanentAddress2" ></form:input> 
			</div>
		</li>	
				
				
         <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Permanent Country <span id="req_136"	class="req">*</span></label>
			<div>
		    <form:select class="field select medium" path="permanentCountry" onchange="showState(this.value);document.getElementById('stateLable').style.display= 'block';addCity('showPersonalDetailsAddNew.htm?Id=${employeeObj.employeeId}');" id="currentCountry">
					<form:option value="0">Select</form:option>
						<c:forEach var="country" items="${countries}">
							<form:option value="${country.countryId}">${country.country}</form:option>
						</c:forEach>
			</form:select>
			</div>
		    	<form:errors path="permanentCountry" cssClass="error"/>
		   </li>
		    
      
        <li id="foli02" class="notranslate      "><label  style="display: block; " class="desc"	id="stateLable" for="Field23"> Permanent State <span id="req_23" class="req">*</span></label>
		    <div id="statediv">
			    <select class="field select medium" name ="state" id ="state" onchange="showCities(this.value)">
				    <c:forEach var="stateVar" items="${States}">
					    <c:choose>
						    <c:when test="${stateVar.stateId==employeeObj.currentState}">
						    	<option value="${stateVar.stateId}" selected="selected">${stateVar.stateName}</option>
						    </c:when>
						    <c:otherwise>
						    	<option value="${stateVar.stateId}">${stateVar.stateName}</option>
						    </c:otherwise>
					    </c:choose>
				    </c:forEach>
				</select>
		    </div>
		    <form:errors path="permanentState" cssStyle="color : red;"/>
		</li>
		
		
	<li id="foli02" class="notranslate      "><label  style="display: block; " class="desc" id="cityLable" for="Field23"> Permanent City<span id="req_23" class="req">*</span></label>
	   <div id="showCitiesDiv">
		   <select class="field select medium" name ="city" id ="city" >
			    <c:forEach items="${Cities}" var="cities">
				    <c:choose>
					    <c:when test="${cities.cityId==employeeObj.currentCity}">
					      	<option value="${cities.cityId}" selected="selected">${cities.city}</option>
					    </c:when>
					    <c:otherwise>
					     	<option value="${cities.cityId}">${cities.city}</option>
					    </c:otherwise>
				    </c:choose>
			    </c:forEach>
			</select>
	   </div>
   		<div id="addNewButtonDiv" style="display: block;" > <input type="button" value="Add new" onclick="addNewCityDiv();"></div>
   		<form:errors path="permanentCity" cssStyle="color : red;"/>
   	</li>
   		
          
      <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Permanent Postal Code<span id="req_23" class="req">*</span></label>
		<div>
			<form:input class="field text medium" type="text" name="value_permanent_postal_code" path="permanentPostalCode" ></form:input>
        </div>
      </li>  
          
          
          
          
       <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Home Distance</label>
			<div>
	          	<form:input class="field text medium" type="text" name="value_home_distance" path="homeDistance" ></form:input>
	        </div>
        </li>  
          
          
          
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Oneway Time</label>
			<div>
				<form:input class="field text medium" type="text" name="value_oneway_time" path="onewayTime" ></form:input>
			</div>
		</li>
		
		
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Travel Mode</label>
			<div>
				<form:input class="field text medium" type="text" name="value_travel_mode" path="travelMode" ></form:input>
			</div>
		</li>
		
		
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Home Gpslocation</label>
			<div>
				<form:input class="field text medium" type="text" name="value_home_gpslocation"  path="homeGpsLocation" ></form:input>
			</div>
		</li>
		
		
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Company Friends</label>
			<div>
				<form:textarea class="field textarea medium" name="value_company_friends" path="companyFriends"></form:textarea>
			</div>
		</li>
		
		
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> All Phones</label>
			<div>
				<form:input class="field text medium" type="text" name="value_all_phones"  path="allPhones"></form:input>
			</div>
		</li>
		
	     
        <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23">Chat Ids:</label>
			<div></div>
		</li>
		
	  
       <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> >Gtalk</label>
			<div>
				<form:input class="field text medium" type="text" name="gtalkChatId"  path="gtalkChatId" ></form:input>
			</div>
		</li>
		
		
      <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Rediff</label>
			<div>
				<form:input class="field text medium" type="text" name="rediffChatId"  path="rediffChatId" ></form:input> 
			</div>
	  </li>
	  
	  
		
       <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> MSN</label>
			<div>
				<form:input class="field text medium" type="text" name="msnChatId"  path="msnChatId" ></form:input>
			</div>
		</li>
		
		
		
       <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Yahoo</label>
			<div>
				<form:input class="field text medium" type="text" name="yahooChatId"  path="yahooChatId" ></form:input>
			</div>
		</li>
		
		
		
       <li id="foli02" class="notranslate      "><label class="desc"		id="title23" for="Field23"> Skype</label>
			<div>
				<form:input class="field text medium" type="text" name="skypeChatId"  path="skypeChatId" ></form:input>
			</div>
		</li>
		
</ul>      
      <INPUT class="btTxt submit" value=Save type=submit>    <INPUT class="btTxt submit" value=Reset type=reset >



     
<TABLE id="calenderTable">
			<TBODY id="calenderTableHead">
				<tr>
					<td align="center" colSpan="4"><SELECT class="form"
						id="selectMonth"
						onChange="showCalenderBody(createCalender(document.getElementById('selectYear').value,&#13;&#10;&#9;this.selectedIndex, false));"
						name='select' style="width: 90px;">
							<OPTION value=0 selected>Jan</OPTION>
							<OPTION value=1>Feb</OPTION>
							<OPTION value=2>Mar</OPTION>
							<OPTION value=3>Apr</OPTION>
							<OPTION value=4>May</OPTION>
							<OPTION value=5>Jun</OPTION>
							<OPTION value=6>Jul</OPTION>
							<OPTION value=7>Aug</OPTION>
							<OPTION value=8>Sep</OPTION>
							<OPTION value=9>Oct</OPTION>
							<OPTION value=10>Nov</OPTION>
							<OPTION value=11>Dec</OPTION>
					</SELECT></td>
					<td align="center" colSpan=2><SELECT id=selectYear style="width: 55px;"
						onChange="showCalenderBody(createCalender(this.value, &#13;&#10;&#9;&#9;&#9;&#9;document.getElementById('selectMonth').selectedIndex, false));"
						name=select></SELECT></td>
					<td align="center"><a onclick="closeCalender();" href="javascript://"> <FONT color=#003333 size=+1>X</FONT></a></td>
				</tr>
			</TBODY>
			<tbody id=calenderTableDays>
				<tr>
					<TD >Sun</TD>
					<TD>Mon</TD >
					<TD>Tue</TD >
					<TD >Wed</TD >
					<TD >Thu</TD >
					<TD >Fri</TD >
					<TD >Sat</TD >
				</tr>
			</tbody>
			<tbody id="calender" style="color: black;">
				<tr>
					<td></td>
				</tr>
			</tbody>
		</TABLE> 
     
</form:form>
</div>
</body>
</html>