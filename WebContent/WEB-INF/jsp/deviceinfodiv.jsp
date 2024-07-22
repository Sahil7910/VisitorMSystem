<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<fieldset class="txt_black">
                      <legend class="txt_bold_grey"><b>Device Status</b> </legend>
                      <div style="overflow:auto">
                      <div class="CSSTableGeneratorOutter" style="height: 125px; overflow: auto;">
					<div class="CSSTableGenerator" >      
                          <table width="80%" align="center">
                    <tr>
                        <td width="20%" class="txt_black">User Count</td>
                        <td width="20%" class="txt_black">Admin Count</td>
                        <td width="20%" class="txt_black">Att Count</td>
                        <td width="20%" class="txt_black">Finger Print Count</td>
                        <td width="20%" class="txt_black">No Of Pwd</td>
                    </tr>  
                     	<tr>                                            
                      <c:forEach items="${statusNativeStringValues}" var="statusNativeStringValuesVar">
                        <td  class="txt_black">${statusNativeStringValuesVar}</td>
                      </c:forEach>
                        </tr>                                           
                      </table>
                      </div></div>
                       </div>
</fieldset>