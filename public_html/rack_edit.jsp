<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="locations" name="locations" scope="request" type="java.util.ArrayList" />
<bean:define id="rooms" name="rooms" scope="request" type="java.util.ArrayList" />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Edit Rack)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script src="general.js" ></script>
    <html:javascript formName="rackNewEditForm" dynamicJavascript="true" staticJavascript="false" />
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script>
      
      function showRoomsListData(httpRequest){
        var cboRoomObj = document.getElementById('cboRoomNumber');
        cboRoomObj.options.length = 0;
        
        if (httpRequest.readyState == 4) {
          if (httpRequest.status == 200) {
            // response can be set now
            var responseArr = httpRequest.responseText.split("|");
            for( var index = 0; index < responseArr.length; index++ ){
              var optionObj = document.createElement("OPTION");
              var textValueArr = responseArr[index].split(",");
              optionObj.text = textValueArr[0];
              optionObj.value = textValueArr[1];
              cboRoomObj.options[index]=optionObj;
            }
          } else {
              // there was a problem with the request,
              // for example the response may be a 404 (Not Found)
              // or 500 (Internal Server Error) response codes
          }
        } else {
            // still not ready
              var optionObj = document.createElement("OPTION");
              optionObj.text = "Loading";
              optionObj.value = "-1";
              cboRoomObj.options[0]=optionObj;
        }
      }
      
      function getRoomsListData(){
        var thisForm = document.forms[0];
        var locationTblPK = thisForm.cboLocations.value;
        var requestParam = 'locationTblPK='+locationTblPK;
        var cboRoomObj = document.getElementById('cboRoomNumber');
        if( locationTblPK == "-1" ){
          cboRoomObj.options.length=0;
          var optionObj = document.createElement("OPTION");
          optionObj.text = "Select";
          optionObj.value = "-1";
          cboRoomObj.options[0]=optionObj;
        }else{
          ajaxRequest('POST','roomList4LocationAction.do',true,requestParam,showRoomsListData,true);
        }

      }
      
    </script>
    <script>
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        var varNumberOfShelves = thisForm.txtNumberOfShelves.value;
        
        if( varNumberOfShelves.length > 1 ){
          varNumberOfShelves=((varNumberOfShelves.substring(0,1)=='0')?varNumberOfShelves.substring(1,varNumberOfShelves.length):varNumberOfShelves);
        }else{
          varNumberOfShelves = parseInt(varNumberOfShelves);
        }
        
        if(validateRackNewEditForm(thisForm)){
          if(thisForm.cboLocations.value=="-1"){
            alert("<bean:message key="errors.select.location"/>");  
            thisForm.cboLocations.focus();
            return false;
          }
          if(thisForm.cboRoomNumber.value=="-1"){
            alert("<bean:message key="errors.select.room"/>");  
            thisForm.cboRoomNumber.focus();
            return false;
          }
          if(parseInt(varNumberOfShelves) == 0){
            alert("<bean:message key="error.invalid.numberOfShelves"/>");  
            thisForm.txtNumberOfShelves.focus();
            return false;
          }
          thisForm.txtNumberOfShelves.value=parseInt(varNumberOfShelves);
          thisForm.submit();
        }
        return false;
      }
    
      function cancelEdit(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="rackListAction.do";
        thisForm.submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        thisForm.txtRackNumber.value = "";
        thisForm.txtNumberOfShelves.value = "";
        thisForm.txtRackNumber.focus();
        return false;
      }
    </script>
  </head>
  <body>
    <html:form action="/editRackAction" name="rackNewEditForm" type="dims.web.actionforms.rack.RackNewEditForm" focus="txtRackNumber">
    <html:hidden name="rackNewEditForm" property="hdnRackTblPk" />
    
    <jsp:include page="/header_masters.jsp" />
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
            <legend>Edit Rack:</legend>
            <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="2" align="left" valign="top"/>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Rack Number:</td>
                <td align="left">
                  <html:text name="rackNewEditForm" property="txtRackNumber" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1" />
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Number Of Shelves:</td>
                <td align="left">
                  <html:text name="rackNewEditForm" property="txtNumberOfShelves" onkeypress="return integerOnly(event);" maxlength="5" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Location ID:</td>
                <td align="left">
                  <html:select name="rackNewEditForm" property="cboLocations" styleClass="bdrClr_Lvl_2" style="width:166px" onchange="return getRoomsListData();" tabindex="3" >
                    <html:options collection="locations" property="locationTblPK" labelProperty="locationID" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td width="235" align="right"><span class="textRed">* </span>Room Number:</td>
                <td width="358" align="left">
                  <html:select name="rackNewEditForm" property="cboRoomNumber" styleId="cboRoomNumber" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="4">
                    <html:options collection="rooms" property="roomTblPk" labelProperty="roomNumber" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td height="50" colspan="2" align="right">
                  <input name="SaveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="5" value="Save" onclick="validateAndSubmit();" />
                  <input name="CancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="6" value="Cancel" onclick="cancelEdit();" />
                  <input name="ResetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="7" value="Reset" onclick="return resetFields();" />
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
