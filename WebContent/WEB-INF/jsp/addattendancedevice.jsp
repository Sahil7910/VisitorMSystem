<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
       <!--  <title>Add Device</title> -->
        <script>
       </script>
    </head>
    <body>
<%--     <div id="maincontent">
        <div style="margin-left:5%;margin-top:2%;width:90%;" align="center">
            <form:form name="formAddDevice" Method="Post" id="formAddDevice" action="addDevice.htm" commandName="addDevice">
            <div class="left_div"></div>
            <div class="head" style="width:15%;"><!-- Add Device --></div><div class="right_div"></div>
            <div class="main_div" id="divPersonal" style="display:block">
            <div style="float:left; width:75%; height:200px;">
                    <fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Add Device</b> </legend>
                      <br>
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
                       <td>&nbsp;</td>
                    </tr>
                    <tr>
                       <td>${ipPresent}</td>
                    </tr>
            </table>
                     </fieldset>
                          </div>
                                               

      <div style="float:right; width:25%; height:200px;">
                <table border="0" width="100%">
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    
                    <tr>
                        <td align="center"><input class="submit_class" style="width:145px;" type="button" value="Test Connection" name="testConnection" id="testConnection" onclick="initTestConnectionWindow('showTestConnection.htm',500,200);"/></td>
                    </tr>

                    <tr>
                        <td></td>
                    </tr>
                    <tr>
                        <td align="center"><input class="submit_class" style="width:145px;" type="submit" value="Save" name="save" id="save" onclick="return validateAddDevice();"/></td>
                    </tr>
                    <tr>
                        <td></td>
                    </tr>
                    <tr>
                        <td align="center"><input class="submit_class" style="width:145px;" type="button" value="Device List" name="deviceList" id="deviceList" onclick="showDevice();"/></td>
                    </tr>
                    <tr>
                        <td></td>
                    </tr>
                    <tr>
                    	<td align="center"><input class="submit_class" style="width:145px;" type="button" value="Initialize Device" name="viewDeviceStatus" onclick="deviceInitializationFunction('InitilizationDevice.htm');"/></td>
                    </tr>
                    <tr>
                    	<td></td>
                    </tr>
 					<tr>
                        <td align="center"><input class="submit_class" style="width:145px;" type="submit" value="Get Serial Number" name="save" id="getserialnoid" /></td>
                    </tr> 
            </table>
         </div>
                        <div style="clear:both;" >
                    </div>
                    <div style="width:100%; float:left; height:200px; display: none" id="deviceListDiv" >
            			<fieldset class="txt_black">
                      <legend class="txt_bold_grey" ><b>Device List</b> </legend>
                      <br>
                      <div style="overflow:auto;">
                      <div class="CSSTableGeneratorOutter">
					<div class="CSSTableGenerator">
                       <table width="80%" align="center">
                          <c:if test="${addDeviceListSize>0}">
                    <tr>
                        <td width="14%" class="txt_black">IP Address</td>
                        <td width="14%" class="txt_black">Device Name</td>
                        <td width="14%" class="txt_black">Short Name</td>
                        <td width="14%" class="txt_black">Serial Number</td>
                        <td width="14%" class="txt_black">Connection Type</td>
                        <td width="14%" class="txt_black">Device Direction</td>
                        <td width="14%" class="txt_black">Device Type</td>
                    </tr>     
                    <c:forEach items="${addDeviceList}" var="addDeviceListVar">
                     <tr>
                        <td  class="txt_black">${addDeviceListVar.ipAddress}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceName}</td>
                        <td  class="txt_black">${addDeviceListVar.shortName}</td>
                        <td  class="txt_black">${addDeviceListVar.serialNo}</td>
                        <td  class="txt_black">${addDeviceListVar.connectionType}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceDirection}</td>
                        <td  class="txt_black">${addDeviceListVar.deviceType}</td>
                      </tr>                       
                    </c:forEach>
                     </c:if>
                      </table>
                      </div></div>
                       </div>

            </fieldset>
                    </div>
                                         
            </div>
          
              <p><font color="red" >**NOTE : To get serial number, first enter IP address and then click on Get Serial Number Button.</font> </p>                                     
            </form:form>
        </div>     
    </div> --%>
  			
  				<table>
  				<tr>
  				<td>
  						Card Number:<input type="text" name="cname" id="cid">
  						</td>
  					
                        <td align="center"><input class="submit_class" style="width:145px;" type="submit" value="Save" name="save" id="save" onclick="return validateAddDevice();"/></td>
                    </tr>
                    
  						
  				</table>  
      </body>
</html>
