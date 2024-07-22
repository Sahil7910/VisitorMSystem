<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
</head>
<%-- <div id=maincontent>
	<form:form encType="multipart/form-data" commandName="priority"
		method="POST" action="savePriorityDiv.htm">

		<table class="content" cellpadding=4 cellspacing=0 border=1id="fields_block">

			<tr>
				<td width=30 style="padding-left: 0px;"><input type="hidden"
					value="${id}" id="updateJobRolesId" name="updateJobRolesId" /></td>
			</tr>

			<tr>
				<td width=30 style="padding-left: 0px;">Name</td>
				<td width=30 style="padding-left: 0px;"><form:input path="name"
						id="name" name="name" /></td>
			</tr>
			<tr>
				<td style="padding-left: 0px;">Rank</td>
				<td style="padding-left: 0px;"><form:input path="rank"
						id="rank" name="rank" /></td>
			</tr>
			<tr>
				<td style="padding-left: 0px;">Description</td>
				<td style="padding-left: 0px;"><form:input path="description"
						id="description" name="description" /></td>
			</tr>
		</table>

		<div id="required_block">
			<img src="images/icon_required.gif">-Required field
		</div>
		<span class=buttonborder><input class=button type="submit"
			value="Save"></span>
		<div class="downedit" id="buttons_block"></div>
	</form:form>
</div>
 --%>
 	
 		<div id=maincontent>
 		<form:form encType="multipart/form-data" commandName="priority"
		method="POST" action="savePriorityDiv.htm">
 		
 		<table class="table table-striped">
 		  <thead>
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Rank</th>
      <th scope="col">Discription</th>
    </tr>
  </thead>
  
  	  <tbody>
  	  
  	  <tr>
				<td width=30 style="padding-left: 0px;"><input type="hidden"
					value="${id}" id="updateJobRolesId" name="updateJobRolesId" /></td>
			</tr>
      
      <th scope="row"><form:input path="name"id="name" name="name" /></td></th>
      <th scope="row"><form:input path="rank"id="rank" name="rank" /></td></th>
      <th scope="row"><form:input path="description"id="description" name="description" /></td></th>
      </tr>
            </tbody>
      </table>
      
      <table>
      <span class=buttonborder>
     <!--  <input class=btn btn-primary type="submit"value="Save" style="margin-left:45%"></span>
	 -->
	 		<span class=buttonborder><input class=button type="submit"
			value="Save"></span>
		<div class="downedit" id="buttons_block"></div></table>
 		</form:form>
 		</div>
 				
 
 
 </body>
</html>
