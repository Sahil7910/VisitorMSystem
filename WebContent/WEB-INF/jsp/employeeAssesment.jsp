<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.distna.utility.DateTime"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="javascript/department.js"></script>
<title>Employee Assessment</title>

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


<body id="public">
<div id="container" class="ltr">
<form:form class="wufoo leftLabel page" encType="multipart/form-data" commandName="assesment" action="addAssesment.htm" method="POST" >
	<ul>
		 <li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Employee <span id="req_23" class="req">*</span>	</label>
		<div>
          <form:select class="field select medium"  path="empid" id="value_employee_id" >
	          <form:option value="0">Select</form:option>
	          	<c:forEach items="${employeeList}" var="employee">
	          <form:option value="${employee.employeeId}">${employee.firstName} ${employee.lastName}(${employee.employeeNo})</form:option>
          </c:forEach>
          </form:select>
          </div>
          <form:errors path="empid" cssStyle="color : red;"/>
         </li>

 		<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Position <span id="req_23" class="req">*</span>	</label>
		<div>
	     <form:select class="field select medium" path="position" id="value_position">
		          <form:option value="0">Select</form:option>
		          	<c:forEach items="${designationList}" var="designation">
		          <form:option value="${designation.id}">${designation.designation}</form:option>
	          	</c:forEach>
	        </form:select>
    
     </div>
     <form:errors path="position" cssStyle="color : red;"/>
     </li>

 	<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Personality <span id="req_23" class="req">*</span>	</label>
		<div>
	    <form:select class="field select medium" size="1" name="value_personality" path="personality">
		    <form:option selected="0" value="0">Select</form:option>
		    <form:option value="Poor">Poor</form:option>
		    <form:option value="Fair">Fair</form:option>
		    <form:option value="Good">Good</form:option>
		    <form:option value="V. Good">V. Good</form:option>
		    <form:option value="Excellent">Excellent</form:option>
	    </form:select>
  		</div> 
  <form:errors path="personality" cssStyle="color : red;"/>
  </li>


	<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Communication <span id="req_23" class="req">*</span>	</label>
		<div>
	    <form:select class="field select medium" size="1" name="value_communication" path="communication">
		    <form:option selected="0" value="0">Select</form:option>
		    <form:option value="Poor">Poor</form:option>
		    <form:option value="Fair">Fair</form:option>
		    <form:option value="Good">Good</form:option>
		    <form:option value="V.Good">V.Good</form:option>
		    <form:option value="Excellent">Excellent</form:option>
	    </form:select>
     <!--  &nbsp;<img src="Employee%20Assessment_files/icon_required.gif"> -->
 	 </div><form:errors path="communication" cssStyle="color : red;"/>
  </li>


	
	<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Knowledge <span id="req_23" class="req">*</span>	</label>
		<div>
	    <form:select size="1" class="field select medium" name="value_education" path="knowledge">
		    <form:option selected="0" value="0">Select</form:option>
		    <form:option value="Poor">Poor</form:option>
		    <form:option value="Fair">Fair</form:option>
		    <form:option value="Good">Good</form:option>
		    <form:option value="V.Good">V.Good</form:option>
		    <form:option value="Excellent">Excellent</form:option>
	    </form:select>
 		</div>
 			<form:errors path="knowledge" cssStyle="color : red;"/>
 	</li>


	<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Strength</label>
		<div>
    		<form:textarea class="field textarea medium" name="value_strength" path="strength"></form:textarea>
		</div>
	</li>

		<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Weakness</label>
			<div>
	   		 <form:textarea  class="field textarea medium" name="value_weekness" path="weakness"></form:textarea>
	   		</div>
   		</li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Previous Company Name </label>
			<div>
    			<form:input class="field text medium" name="value_company_name" maxlength="50" size="40" type="text" path="company_name"></form:input>
  			</div>
  		</li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Reason for leaving </label>
			<div>
    			<form:textarea class="field textarea medium" name="value_reason" style="width: 300px;height: 150px;" path="reason"></form:textarea>
   			</div>
   </li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Interviewer 1 <span id="req_23" class="req">*</span>	</label>
		<div>
		     <form:select class="field select medium" path="interviewer1" id="value_employee_id">
			      <form:option  selected="0" value="0">Select</form:option>
			       	<c:forEach items="${employeeList}" var="employee">
			        <form:option value="${employee.employeeId}">${employee.firstName} ${employee.lastName}</form:option>
		          </c:forEach>
		     </form:select>
      
 		</div>
 		<form:errors path="interviewer1" cssStyle="color : red;"/>
 </li>



<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Date 1 <span id="req_23" class="req">*</span>	</label>
		<div>
        <form:input class="field text medium" type="text" id="date1" name="date1" path="date1" value="<%=dateNow%>" readonly="true" size="15" /> 
		&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal" onClick="setYears(1990, 2050);showCalender(this, 'date1',-350,-270);"></img>
     </div>
     <form:errors path="date1" cssStyle="color : red;"/>
     </li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Remark1 <span id="req_23" class="req">*</span>	</label>
		<div>
    		<form:textarea class="field textarea medium" name="value_remark1"  path="remark1"></form:textarea>
      
 		</div><form:errors path="remark1" cssStyle="color : red;"/>
 </li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Interviewer 2	</label>
		<div>
	     <form:select class="field select medium" path="interviewer2" id="value_employee_id">
		      <form:option value="0">Select</form:option>
		       	<c:forEach items="${employeeList}" var="employee">
		        <form:option value="${employee.employeeId}">${employee.firstName} ${employee.lastName}</form:option>
	          </c:forEach>
	     </form:select>
   </div>
   </li>


<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23"> Date 2</label>
		<div>
        	<form:input  class="field text medium" type="text" id="date2" name="date2" path="date2" value="<%=dateNow%>" readonly="true" size="15" /> 
			&nbsp;&nbsp;<img id="dobImage1" src="images/cal.gif" alt="cal" onClick="setYears(1990, 2050);showCalender(this, 'date2',-350,-270);"></img>
     	</div>
     </li>



<li id="foli23" class="notranslate      "><label class="desc" id="title23" for="Field23">Remark2</label>
	<div>
    	<form:textarea class="field textarea medium" name="value_remark2" path="remark2"></form:textarea>
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
					<TD>Sun</TD>
					<TD>Mon</TD>
					<TD>Tue</TD>
					<TD>Wed</TD>
					<TD>Thu</TD>
					<TD>Fri</TD>
					<TD>Sat</TD>
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