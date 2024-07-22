                  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
                <form:form name="updateForm" Method="post" id="updateForm" commandName="addDevice" action="updateDeviceInfo.htm">
                    <div style="width:100%; height:200px;" id="deviceInfo">
                    <fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Update Device Info</b> </legend>
                      <form:hidden path="id" name="deviceId" id="deviceId"/>
                              <table border="0" width="100%" cellspacing="5">
	
                   <tr>
                        <td width="25%" class="txt_black">Device Name</td>
                        <td width="25%"><form:input path="deviceName" name="deviceName" id="deviceName"/></td>
                        <td width="25%" class="txt_black">Short Name</td>
                        <td width="25%"><form:input path="shortName" name="shortName" id="shortName"/></td>
                    </tr>
                   <tr>
                        <td class="txt_black">Serial Number</td>
                        <td><form:input path="serialNo" name="serialNumber" id="serialNumber"/></td>
                        <td class="txt_black">Connection Type</td>
                        <td><form:select path="connectionType" name="connectionType" style="width:150px;" id="connectionType" >
                                <form:option value="TCP/IP">TCP/IP</form:option>
                                <form:option value="USB">USB</form:option>
                                <form:option value="SERIAL">Serial Port</form:option>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="txt_black">Device Type</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" name="deviceType" value="attendanceAccess" id="deviceType"/>Attendance + Access</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" name="deviceType" value="canteenReader" id="deviceType"/>Canteen Reader</td>
                        <td class="txt_black"><form:radiobutton path="deviceType" name="deviceType" value="access" id="deviceType"/>Access</td>
                    </tr>
                    <tr>
                        <td class="txt_black">IP Address</td>
                        <td><form:input path="ipAddress" name="ipAddress" id="ipAddress"/></td>
                        <td class="txt_black">Device Direction</td>
                        <td><form:select path="deviceDirection" name="deviceDirection" style="width:150px;" id="deviceDirection">
                                <form:option value="IN">In Device</form:option>
                                <form:option value="OUT">Out Device</form:option>
                                <form:option value="ALT">Alternate In/Out Device</form:option>
                                <form:option value="SYSTEM">System Direction(In/Out)</form:option> 
                            </form:select></td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
             </table>
             <table>
                          <tr>
                        <td><input class="submit_class" style="width:165px;" type="submit" value="Update Info" name="saveDevice" onclick="return validateAddDevice();"/> <input class="submit_class" style="width:165px;" type="button" value="Delete" name="deleteDevice" onclick="deleteAttendanceDevice('${addDevice.ipAddress}');"/></td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                      </table>
                     </fieldset>
</div>
                </form:form>