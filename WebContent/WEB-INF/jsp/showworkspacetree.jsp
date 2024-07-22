<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<ul>
<li id="foli02" class="notranslate      "><label class="desc"
		id="title23" for="Field23">Location</label> 
<div>
	<input type="text"  class="field text medium" id="locationName" readonly="readonly" value="${location.location}" style="background: transparent;"/>
</div>
</li>


<li id="foli02" class="notranslate      "><label class="desc"
		id="title23" for="Field23"> Division: </label>
 <div>
 <input type="text" class="field text medium" id="divisionName"  readonly="readonly" value="${division.divisionName}" style="background: transparent;"/>
 </div>
 </li>
 
 
<li id="foli02" class="notranslate      "> <label class="desc"id="title23" for="Field23"> Department: </label>

<div>
<input type="text" class="field text medium" id="departmentName"  readonly="readonly" value="${department.name}" style="background: transparent;"/>
</div>
</li>
</ul>