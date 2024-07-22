<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
@keyframes anim {
  0% { background-position: 0 0; }
  100% { background-position: 50px 50px; }
}    

.bar {
 display: block;
  margin: auto;
  top: 0; bottom: 0; left: 0; right: 0;
  width: 450px;
  height: 30px;
  overflow: hidden;
  background-size:100px 100px;
  background-image: linear-gradient(-45deg,
     #33c9ff 25%, #00b2f2 25%, 
     #00b2f2 50%, #33c9ff 50%,
     #33c9ff 75%, #00b2f2 75%);
  animation: anim 1s linear infinite;
}

.bar p {
  width: 300px;
  margin: 1px;
  font-size: 14px;
  text-align: center;
  color: white;
}
</style>

<script type="text/javascript" src="javascript/displayPages.js"></script>
<script type="text/javascript" src="javascript/devicemanagement.js"></script>
    </head>
    <body id="bodycolor">
<p id="prog"></p>
       <div id="maincontent">
        <div style="margin-left:5%;margin-top:2%;width:90%;" align="center">
            <form:form name="updateForm" Method="post" id="updateForm" commandName="addDevice" action="updateDeviceInfo.htm">
            <div class="left_div"></div>
            <div class="head" style="width:15%;"><!-- Update Device --></div><div class="right_div"></div>

            <div class="main_div" id="deviceUpdateDiv" style="display:block" >

                    <div style="width:100%; height:80px;">
                    <fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Update Device</b> </legend>
                      <table border="0" cellspacing="5" width="70%">
                        <tr>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Set System Date/Time" name="setDateTime" onclick="setDataTimeF();"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Initialize Device" name="viewDeviceStatus" onclick="viewDeviceFunctionsAjax('InitilizationDevice.htm');"/></td>
                            <!-- <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Change I/P Address" name="changeIpAdd" onclick="callPopupWindowPost('changeDeviceIp.htm','changeIP','POST');"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Download User to DB" name="downloadUser" onclick="downloadUserF();"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Clear Logs" name="clearLogs" onclick="clearLogsF();"/></td> -->
                        </tr>
                       <!--  <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr> -->
                        
                       <!--  <tr>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Clear Admin Privilege" name="clearAdminPrivilege" onclick="clearAdminPrivilegeF()"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Restart device" name="restartDevice" onclick="restartDeviceF()"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="View Device Status" name="viewDeviceStatus" onclick="viewDeviceFunctionsAjax('viewDeviceStatus.htm');"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Delete User" name="deleteUser" onclick="deleteUserF();"/></td>
                            <td align="center"><input class="submit_class" style="width:165px;" type="button" value="Delete User" name="deleteUser" onclick="genericAjaxFunction('showUsersDiv.htm','POST','showUsersDiv');"/></td>
                        </tr> -->
                </table>
                     </fieldset>
                          </div>
                       
                          
           <div id="showUsersDiv" style="width:100%;">
           <div style="width:100%;">
            <fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Device List</b> </legend>
                      <div style="overflow:auto"> 

				<div class="CSSTableGeneratorOutter" style="height: 200px; overflow: auto;">
					<div class="CSSTableGenerator" >                
                          <table  width="80%" align="center">
                    <tr>
                        <td width="12%" class="txt_black">Select Device</td>
                        <td width="12%" class="txt_black">IP Address</td>
                        <td width="12%" class="txt_black">Device Name</td>
                        <td width="12%" class="txt_black">Short Name</td>
                        <td width="12%" class="txt_black">Serial Number</td>
                        <td width="12%" class="txt_black">Connection Type</td>
                        <td width="12%" class="txt_black">Device Direction</td>
                        <td width="12%" class="txt_black">Device Type</td>
                    </tr>    
                      
                        <c:forEach items="${addDeviceList}" var="addDeviceListVar">
                     	<tr>                         
                        <td  class="txt_black"><input type="radio" value="${addDeviceListVar.ipAddress}" name="specificDevice" id="specificDevice" onchange="genericAjaxFunction('showDeviceInfodiv.htm?ipAddress='+this.value,'POST','deviceInfo');"/></td>                        
                        <td  class="txt_black">${addDeviceListVar.ipAddress}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceName}</td>
                        <td  class="txt_black">${addDeviceListVar.shortName}</td>
                        <td  class="txt_black">${addDeviceListVar.serialNo}</td>
                        <td  class="txt_black">${addDeviceListVar.connectionType}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceDirection}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceType}</td>
                        </tr>  
                    </c:forEach>                     
                     
                      </table>
                      </div></div>
                       </div>
            </fieldset>
                    </div>
                       <div style="width:100%; height:200px;" id="deviceInfo">
                   <fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Update Device Info</b> </legend>
                <table border="0" width="100%" cellspacing="5">
                   <tr>
                        <td width="25%" class="txt_black">Device Name</td>
                        <td width="25%"><form:input path="deviceName" name="deviceName" value="" id="deviceName"/></td>
                        <td width="25%" class="txt_black">Short Name</td>
                        <td width="25%"><form:input path="shortName" name="shortName" value="" id="shortName"/></td>
                    </tr>
                     
                    
                   <tr>
                        <td class="txt_black">Serial Number</td>
                        <td><form:input path="serialNo" name="serialNumber" value="" id="serialNumber"/></td>
                        <td class="txt_black">Connection Type</td>
                        <td><form:select path="connectionType" name="connectionType" style="width:150px;" id="connectionType">
                                <form:option value="0">Select</form:option>
                                <form:option value="TCP/IP">TCP/IP</form:option>
                                <form:option value="USB">USB</form:option>
                                <form:option value="SERIAL">Serial Port</form:option>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="txt_black">Device Type</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" name="deviceType" value="attendanceAccess" id="deviceType"/>Attendance + Access</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" type="radio" name="deviceType" value="canteenReader" id="deviceType"/>Canteen Reader</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" name="deviceType" value="access" id="deviceType"/>Access</td>
                    </tr>
                    <tr>
                        <td class="txt_black">IP Address</td>
                        <td><form:input path="ipAddress" name="ipAddress" value="" id="ipAddress"/></td>
                        <td class="txt_black">Device Direction</td>
                        <td><form:select path="deviceDirection" name="deviceDirection" style="width:150px;" id="deviceDirection">
                                <form:option value="0">Select</form:option>
                                <form:option value="IN">In Device</form:option>
                                <form:option value="OUT">Out Device</form:option>
                                <form:option value="ALT">Alternate In/Out Device</form:option>
                                <form:option value="SYSTEM">System Direction(In/Out)</form:option>
                            </form:select></td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                     <tr>
                       <td>&nbsp;</td>
                    </tr>
                    <tr>
                       <td>${ipPresent}</td>
                    </tr>
             </table>
                     </fieldset>
                          </div>
                
				 <div style="width:100%;" id="seviceInfoStatus">
                    </div>
                    <div id="progress" class="bar">
  				<p>downloading logs Under progress</p>
				</div>       
                      <table>
                      
                          <tr>
                          
                    <!--       
                    genericAjaxFunction('clearAttRecords.htm','POST','');
                    -->
                        <td colspan="4" align="center"><input class="submit_class" style="width:165px;" type="button" value="Test Connection" name="testConnection" onclick="initTestConnectionWindow('showTestConnection.htm',500,200);"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="submit_class" style="width:165px;" type="button" value="Clear Records" name="clearRecords" id="clearRecords" onclick="updateFunctionsAjax('clearAttRecords.htm','done');"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="submit_class" style="width:165px;" type="button" value="Download Logs" name="saveLogs" onclick="secondFunction();"/>
                       
                      <!--   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="submit_class" style="width:165px;" type="button" value="Transfer User" name="transferUser" onclick="transferUserJS();"/> -->
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
              </table>
            </div> 
        </div> 
       
             
            </form:form>
        </div>
       </div>
       <script type="text/javascript">
		//alert('hi');
		var adiv=document.getElementById('progress');
		adiv.style.display="none";
		function progDiv()
		{
			adiv.style.display="none";
		}
		function firstFunction(_callback) {
			// do some asynchronous work
			// and when the asynchronous stuff is complete
		//	alert('first');
			downloadLogsToDatebase();
		//	adiv.style.display="none";
			_callback();
		}
		function secondFunction() {
			// call first function and pass in a callback function which
			// first function runs when it has completed
			firstFunction(function() {
				adiv.style.display="block";
				alert('Do not close it till Downloading');
				adiv.style.display="none";
			});
			
		}
		</script>
    </body>
</html>
