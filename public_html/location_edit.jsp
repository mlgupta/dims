<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>DBSentry - Digital Imaging Management System (Edit Location)</title>
    <link href="main.css" rel="stylesheet" type="text/css" />
    <script src="general.js" ></script>
    <html:javascript formName="locationNewEditForm" dynamicJavascript="true" staticJavascript="false"/>
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script>

      function validateAndSubmit(){
        var thisForm = document.forms[0];
        if(validateLocationNewEditForm(thisForm)){
          var varTxtLocationCity = thisForm.txtLocationCity.value;
          thisForm.txtLocationCity.value = varTxtLocationCity.toUpperCase();
          thisForm.submit();
        }
        return false;
      }
    
      function cancelEdit(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="locationListAction.do";
        thisForm.submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        thisForm.txtLocationID.value = ""; 
        thisForm.txtLocationDescription.value = ""; 
        thisForm.txtLocationCity.value = "";
        thisForm.txtLocationState.value = "";
        thisForm.txtLocationCountry.value = "";
        thisForm.txtLocationZipcode.value = "";
        thisForm.txtLocationID.focus();
        return false;
      }
    </script>
  
  </head>
  <body>
    <html:form action="/editLocationAction" name="locationNewEditForm" type="dims.web.actionforms.location.LocationNewEditForm" onsubmit="return validateLocationNewEditForm(this);" focus="txtLocationID" >
      <jsp:include page="/header_masters.jsp" />  
      <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      
        <tr>
          <td align="center">&nbsp;</td>
        </tr>
    
        <tr>
          <td height="150x" align="center">
          <fieldset style="width:770px;">
          <legend>Edit Location:</legend>
            <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="2" align="left" valign="top"></td>
              </tr>
          
              <tr>
                <td align="right">Location Number: </td>
                <td align="left">
                  <html:text name="locationNewEditForm" property="txtLocationNo" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" tabindex="1" />
                </td>
              </tr>
        
              <tr>
                <td width="235" align="right"><span class="textRed">* </span>Location ID:</td>
                <td width="358" align="left">
                  <html:text name="locationNewEditForm" property="txtLocationID" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
                </td>
              </tr>
        
              <tr>
                <td align="right">Address: </td>
                <td align="left">
                  <html:text name="locationNewEditForm" property="txtLocationDescription" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="3" />
                </td>
              </tr>
              
              <tr>
                <td align="right"><span class="textRed">* </span>City: </td>
                <td align="left">
                  <html:text name="locationNewEditForm" property="txtLocationCity" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="4" />
                </td>
              </tr>
        
              <tr>
                <td align="right">Zip: </td>
                <td align="left">
                  <html:text name="locationNewEditForm" property="txtLocationZipcode" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="5" onkeypress="return integerOnly(event);" maxlength="6" />
                </td>
              </tr>
        
              <tr>
                <td align="right"><span class="textRed">* </span>State:</td>
                <td align="left">
                  <html:select name="locationNewEditForm" property="txtLocationState" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="6" >
                    <html:option value="">Select State</html:option>
                    <html:option value="Andaman and Nicobar Islands">Andaman and Nicobar Islands</html:option>
                    <html:option value="Andhra Pradesh">Andhra Pradesh</html:option>
                    <html:option value="Arunachal Pradesh">Arunachal Pradesh</html:option>
                    <html:option value="Assam">Assam</html:option>
                    <html:option value="Bihar">Bihar</html:option>
                    <html:option value="Chandigarh">Chandigarh</html:option>
                    <html:option value="Chhattisgarh">Chhattisgarh</html:option>
                    <html:option value="Dadra and Nagar Haveli">Dadra and Nagar Haveli</html:option>
                    <html:option value="Daman and Diu">Daman and Diu</html:option>
                    <html:option value="Delhi">Delhi</html:option>
                    <html:option value="Goa">Goa</html:option>
                    <html:option value="Gujarat">Gujarat</html:option>
                    <html:option value="Haryana">Haryana</html:option>
                    <html:option value="Himachal Pradesh">Himachal Pradesh</html:option>
                    <html:option value="Jammu and Kashmir">Jammu and Kashmir</html:option>
                    <html:option value="Jharkhand">Jharkhand</html:option>
                    <html:option value="Karnataka">Karnataka</html:option>
                    <html:option value="Kerala">Kerala</html:option>
                    <html:option value="Lakshadweep">Lakshadweep</html:option>
                    <html:option value="Madhya Pradesh">Madhya Pradesh</html:option>
                    <html:option value="Maharashtra">Maharashtra</html:option>
                    <html:option value="Manipur">Manipur</html:option>
                    <html:option value="Meghalaya">Meghalaya</html:option>
                    <html:option value="Mizoram">Mizoram</html:option>
                    <html:option value="Nagaland">Nagaland</html:option>
                    <html:option value="Orissa">Orissa</html:option>
                    <html:option value="Pondicherry">Pondicherry</html:option>
                    <html:option value="Punjab">Punjab</html:option>
                    <html:option value="Rajasthan">Rajasthan</html:option>
                    <html:option value="Sikkim">Sikkim</html:option>
                    <html:option value="Tamil Nadu">Tamil Nadu</html:option>
                    <html:option value="Tripura">Tripura</html:option>
                    <html:option value="Uttaranchal">Uttaranchal</html:option>
                    <html:option value="Uttar Pradesh">Uttar Pradesh</html:option>
                    <html:option value="West Bengal">West Bengal</html:option>
                  </html:select>
                </td>
              </tr>
        
              <tr>
                <td align="right">Country:</td>
                <td align="left">
                  <html:select name="locationNewEditForm" property="txtLocationCountry" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="7">
                    <html:option value="India">India</html:option>
                  </html:select>
                </td>
              </tr>
        
              <tr>
                <td height="50" colspan="2" align="right">
                  <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="8" value="Save" onclick="validateAndSubmit();" >
                  <input name="cancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="9" value="Cancel" onclick="cancelEdit();" >
                  <input name="resetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="10" value="Reset" onclick="return resetFields();" >
                </td>
              </tr>
            </table>
            </fieldset>	
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html>