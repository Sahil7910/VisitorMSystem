
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 
<script>window.postloadstep = function(){window.current_language='English';
addValid = new validation();
window.TEXT_INLINE_FIELD_REQUIRED='Required field';
window.TEXT_INLINE_FIELD_ZIPCODE='Field should be a valid zipcode';
window.TEXT_INLINE_FIELD_EMAIL='Field should be a valid email address';
window.TEXT_INLINE_FIELD_NUMBER='Field should be a valid number';
window.TEXT_INLINE_FIELD_CURRENCY='Field should be a valid currency';
window.TEXT_INLINE_FIELD_PHONE='Field should be a valid phone number';
window.TEXT_INLINE_FIELD_PASSWORD1='Field can not be \'password\'';
window.TEXT_INLINE_FIELD_PASSWORD2='Field should be at least 4 characters long';
window.TEXT_INLINE_FIELD_STATE='Field should be a valid US state name';
window.TEXT_INLINE_FIELD_SSN='Field should be a valid Social Security Number';
window.TEXT_INLINE_FIELD_DATE='Field should be a valid date';
window.TEXT_INLINE_FIELD_TIME='Field should be a valid time in 24-hour format';
window.TEXT_INLINE_FIELD_CC='Field should be a valid credit card number';
window.TEXT_INLINE_FIELD_SSN='Field should be a valid Social Security Number';
window.locale_dateformat = 0;
window.locale_datedelimiter = "/";
window.bLoading=false;
window.TEXT_PLEASE_SELECT='Please select';
SUGGEST_TABLE='locations_searchsuggest.php';
preloadSelectContent('', 'value_city', '','');
preloadSelectContent('', 'value_state', '','');
SetToFirstControl('editform');
 window.rteIdArr=new Object;
};
 var sl = new ScriptLoader('');
sl.addJS('validate');
sl.addJS('ui');
sl.addJS('ui.core','ui');
sl.addJS('ui.resizable','ui.core');
sl.addJS('onthefly');
sl.addJS('customlabels');
sl.addJS('ajaxsuggest');
sl.load();</script> -->
</head>

<body>
<div style="overflow-y: auto; height: 500px;">
<table cellpadding=0 cellspacing=7 border=0 id="fields_block">


<tr >
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Location</font></td>
    <td  align="left" width=250 class=editshaderight_lb style="padding-left:0px;">
    <input type="text" name="value_location"  maxlength=100 value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Parent Location</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <select size = "1" id="value_sub_locationof" name="value_sub_locationof" ><option value="">Please select</option></select>
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Address1</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_address1"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Address2</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_address2"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black"><font color="red" size="4" style="font-weight: bolder;">*</font>Country</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <select size = "1" id="value_country" name="value_country" onchange="loadSelectContent('locations','country', 'state', '');"><option value="">Please select</option><option value="1">Afghanistan</option><option value="2">Albania</option><option value="3">Algeria</option><option value="4">American Samoa</option><option value="5">Andorra</option><option value="6">Angola</option><option value="7">Anguilla</option><option value="8">Antarctica</option><option value="10">Argentina</option><option value="11">Armenia</option><option value="12">Aruba</option><option value="14">Australia</option><option value="15">Austria</option><option value="16">Azerbaijan</option><option value="18">Bahrain</option><option value="19">Baker Island</option><option value="20">Bangladesh</option><option value="21">Barbados</option><option value="22">Bassas da India</option><option value="23">Belarus</option><option value="24">Belgium</option><option value="25">Belize</option><option value="26">Benin</option><option value="27">Bermuda</option><option value="28">Bhutan</option><option value="29">Bolivia</option><option value="30">Bosnia and Herz</option><option value="31">Botswana</option><option value="32">Bouvet Island</option><option value="33">Brazil</option><option value="34">British Indian </option><option value="35">British Virgin </option><option value="36">Brunei Darussal</option><option value="37">Bulgaria</option><option value="38">Burkina Faso</option><option value="39">Burma</option><option value="40">Burundi</option><option value="41">Cambodia</option><option value="42">Cameroon</option><option value="43">Canada</option><option value="44">Cape Verde</option><option value="45">Cayman Islands</option><option value="46">Central African</option><option value="47">Chad</option><option value="48">Chile</option><option value="49">China</option><option value="50">Christmas Islan</option><option value="51">Clipperton Isla</option><option value="52">Cocos (Keeling)</option><option value="53">Colombia</option><option value="54">Comoros</option><option value="55">Congo, Democrat</option><option value="56">Congo, Republic</option><option value="57">Cook Islands</option><option value="58">Coral Sea Islan</option><option value="59">Costa Rica</option><option value="60">Cote d'Ivoire</option><option value="61">Croatia</option><option value="62">Cuba</option><option value="63">Cyprus</option><option value="64">Czech Republic</option><option value="65">Denmark</option><option value="66">Djibouti</option><option value="67">Dominica</option><option value="68">Dominican Repub</option><option value="69">East Timor</option><option value="70">Ecuador</option><option value="71">Egypt</option><option value="72">El Salvador</option><option value="73">Equatorial Guin</option><option value="74">Eritrea</option><option value="75">Estonia</option><option value="76">Ethiopia</option><option value="77">Europa Island</option><option value="78">Falkland Island</option><option value="79">Faroe Islands</option><option value="80">Fiji</option><option value="81">Finland</option><option value="82">France</option><option value="83">France, Metropo</option><option value="84">French Guiana</option><option value="85">French Polynesi</option><option value="86">French Southern</option><option value="87">Gabon</option><option value="89">Gaza Strip</option><option value="90">Georgia</option><option value="91">Germany</option><option value="92">Ghana</option><option value="93">Gibraltar</option><option value="94">Glorioso Island</option><option value="95">Greece</option><option value="96">Greenland</option><option value="97">Grenada</option><option value="98">Guadeloupe</option><option value="99">Guam</option><option value="100">Guatemala</option><option value="101">Guernsey</option><option value="102">Guinea</option><option value="103">Guinea-Bissau</option><option value="104">Guyana</option><option value="105">Haiti</option><option value="106">Heard Island an</option><option value="107">Holy See (Vatic</option><option value="108">Honduras</option><option value="109">Hong Kong (SAR)</option><option value="110">Howland Island</option><option value="111">Hungary</option><option value="112">Iceland</option><option value="113">India</option><option value="114">Indonesia</option><option value="115">Iran</option><option value="116">Iraq</option><option value="117">Ireland</option><option value="118">Israel</option><option value="119">Italy</option><option value="120">Jamaica</option><option value="121">Jan Mayen</option><option value="122">Japan</option><option value="123">Jarvis Island</option><option value="124">Jersey</option><option value="125">Johnston Atoll</option><option value="126">Jordan</option><option value="127">Juan de Nova Is</option><option value="128">Kazakhstan</option><option value="129">Kenya</option><option value="130">Kingman Reef</option><option value="131">Kiribati</option><option value="132">Korea, North</option><option value="133">Korea, South</option><option value="134">Kuwait</option><option value="135">Kyrgyzstan</option><option value="136">Laos</option><option value="137">Latvia</option><option value="138">Lebanon</option><option value="139">Lesotho</option><option value="140">Liberia</option><option value="141">Libya</option><option value="142">Liechtenstein</option><option value="143">Lithuania</option><option value="144">Luxembourg</option><option value="145">Macao</option><option value="146">Macedonia</option><option value="147">Madagascar</option><option value="148">Malawi</option><option value="149">Malaysia</option><option value="150">Maldives</option><option value="151">Mali</option><option value="152">Malta</option><option value="154">Marshall Island</option><option value="155">Martinique</option><option value="156">Mauritania</option><option value="157">Mauritius</option><option value="158">Mayotte</option><option value="159">Mexico</option><option value="161">Midway Islands</option><option value="163">Moldova</option><option value="164">Monaco</option><option value="165">Mongolia</option><option value="166">Montenegro</option><option value="167">Montserrat</option><option value="168">Morocco</option><option value="169">Mozambique</option><option value="170">Myanmar</option><option value="171">Namibia</option><option value="172">Nauru</option><option value="173">Navassa Island</option><option value="174">Nepal</option><option value="175">Netherlands</option><option value="176">Netherlands Ant</option><option value="177">New Caledonia</option><option value="178">New Zealand</option><option value="179">Nicaragua</option><option value="180">Niger</option><option value="181">Nigeria</option><option value="182">Niue</option><option value="183">Norfolk Island</option><option value="184">Northern Marian</option><option value="185">Norway</option><option value="186">Oman</option><option value="187">Pakistan</option><option value="188">Palau</option><option value="275">Palestinian Ter</option><option value="189">Palmyra Atoll</option><option value="190">Panama</option><option value="191">Papua New Guine</option><option value="192">Paracel Islands</option><option value="193">Paraguay</option><option value="194">Peru</option><option value="195">Philippines</option><option value="196">Pitcairn Island</option><option value="197">Poland</option><option value="198">Portugal</option><option value="199">Puerto Rico</option><option value="200">Qatar</option><option value="202">Romania</option><option value="201">RTunion</option><option value="203">Russia</option><option value="204">Rwanda</option><option value="205">Saint Helena</option><option value="210">Samoa</option><option value="211">San Marino</option><option value="213">Saudi Arabia</option><option value="214">Senegal</option><option value="215">Serbia</option><option value="216">Serbia and Mont</option><option value="217">Seychelles</option><option value="218">Sierra Leone</option><option value="219">Singapore</option><option value="220">Slovakia</option><option value="221">Slovenia</option><option value="222">Solomon Islands</option><option value="223">Somalia</option><option value="224">South Africa</option><option value="225">South Georgia a</option><option value="226">Spain</option><option value="227">Spratly Islands</option><option value="228">Sri Lanka</option><option value="229">Sudan</option><option value="230">Suriname</option><option value="231">Svalbard</option><option value="232">Swaziland</option><option value="233">Sweden</option><option value="234">Switzerland</option><option value="235">Syria</option><option value="236">Taiwan</option><option value="237">Tajikistan</option><option value="238">Tanzania</option><option value="239">Thailand</option><option value="17">The Bahamas</option><option value="88">The Gambia</option><option value="240">Togo</option><option value="241">Tokelau</option><option value="242">Tonga</option><option value="243">Trinidad and To</option><option value="244">Tromelin Island</option><option value="245">Tunisia</option><option value="246">Turkey</option><option value="247">Turkmenistan</option><option value="248">Turks and Caico</option><option value="249">Tuvalu</option><option value="250">Uganda</option><option value="253">UK</option><option value="251">Ukraine</option><option value="252">United Arab Emi</option><option value="256">Uruguay</option><option value="254">USA</option><option value="257">Uzbekistan</option><option value="258">Vanuatu</option><option value="259">Venezuela</option><option value="260">Vietnam</option><option value="263">Virgin Islands </option><option value="262">Virgin Islands </option><option value="261">Virgin Islands</option><option value="264">Wake Island</option><option value="265">Wallis and Futu</option><option value="266">West Bank</option><option value="267">Western Sahara</option><option value="268">Western Samoa</option><option value="269">World</option><option value="270">Yemen</option><option value="271">Yugoslavia</option><option value="272">Zaire</option><option value="273">Zambia</option><option value="274">Zimbabwe</option></select>&nbsp;<a href=# onclick="">Add new</a>
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black"><font color="red" size="4" style="font-weight: bolder;">*</font>State</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <select size = "1" id="value_state" name="value_state" onchange="loadSelectContent('locations','state', 'city', '');"><option value="">Please select</option></select>&nbsp;<a href=# onclick="">Add new</a>
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black"><font color="red" size="4" style="font-weight: bolder;">*</font>City</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <select size = "1" id="value_city" name="value_city" ><option value="">Please select</option></select>&nbsp;<a href=# onclick="">Add new</a>
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Phone</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_phone"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Email</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_email"  value="">
    </td></tr>


<tr>
    <td align="left" width=150 style="padding-left:0px;"><font color="black">Website</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_website"  value="">
    </td></tr>


<tr>
    <td align="left" width=150 style="padding-left:0px;"><font color="black">Details</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <textarea name="value_details" style="width: 200px;height: 100px;"></textarea>
    </td></tr>


<tr>
    <td align="left"  width=150 style="padding-left:0px;"><font color="black">Postal Code</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_postal_code"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Fax</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_fax"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Gps Location</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_gps_location"  value="">
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Allowed IPs</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <textarea name="value_allowed_ips" style="width: 200px;height: 100px;"></textarea>
    </td></tr>


<tr>
    <td  align="left" width=150 style="padding-left:0px;"><font color="black">Attention</font></td>
    <td align="left" width=250  style="padding-left:0px;">
    <input type="text" name="value_attention"  value="">
    </td></tr>


<tr>
    <td align="left" width=150 style="padding-left:0px;"><font color="black">Active</font></td>
    <td align="left" width=250  style="padding-left:0px; border: 1px; border-color: black;">
    <input type="hidden" name="type_active" value="checkbox"><input type="checkbox" name="value_active"  checked>
    </td></tr>

</table>
<br>
<div class="downedit" id="buttons_block">
 <div id="required_block" ><img src="images/icon_required.gif"> - Required field</div>

 <span class=buttonborder><input class=button type=submit value="Save"></span>
 

 <span class=buttonborder><input class=button type=reset value="Reset" onclick="resetEditors();"></span>


 <span class=buttonborder><input class=button type=button value="Back to list" onclick="window.location.href='locations_list.php?a=return'"></span>


</div>
<b class="xbottom"><b class="xb4a"></b><b class="xb3a"></b><b class="xb2a"></b><b class="xb1a"></b></b>
</div>
</div>
<b class="xbottom"><b class="xb4b4"></b><b class="xb3b4"></b><b class="xb2b4"></b><b class="xb1b4"></b></b>

</td></tr></table>
</div>
</body>
</html>