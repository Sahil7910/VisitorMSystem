<table>
 <tr><td>Old Password</td><td><input type="password" id="oldPassword" name="oldPassword" /></td></tr>
 <tr><td>New Password</td><td><input type="password" id="newPassword" name="newPassword"/></td></tr>
 <tr><td>Confirm Password</td><td><input type="password" id="confirmPassword" name="confirmPassword"/></td></tr>
 <tr>
	<td><input class=button type=button value="Save" onclick="savePasswordEmp('${empPassword}','savePasswordInternalDiv.htm?employeeId=${employeeId}');"></td>
	<td><input class=button type="button" value="Cancel"></td>
	</tr>
 </table>