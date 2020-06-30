<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="locations" name="locations" scope="request" type="java.util.ArrayList" />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Create New Room)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <html:javascript formName="roomNewEditForm" dynamicJavascript="true" staticJavascript="false" />
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script>
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        if(validateRoomNewEditForm(thisForm)){
          if(thisForm.cboLocations.value=="-1"){
            alert("<bean:message key="errors.select.location"/>");  
            thisForm.cboLocations.focus();
            return false;
          }
          thisForm.submit();
        }
        return false;
      }
    
      function cancelNew(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="roomListAction.do";
        thisForm.submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        thisForm.txtRoomNumber.value = "";
        thisForm.cboLocations.focus();
        return false;
      }
    </script>
  </head>
  <body>
    <html:form action="/newRoomAction" name="roomNewEditForm" type="dims.web.actionforms.room.RoomNewEditForm" focus="cboLocations" >
    <html:hidden name="roomNewEditForm" property="hdnRoomTblPk" value="" />
    <jsp:include page="/header_masters.jsp" />
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
            <legend>Add New Room:</legend>
            <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="2" align="left" valign="top"/>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Location ID:</td>
                <td align="left">
                  <html:select name="roomNewEditForm" property="cboLocations" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1">
                    <html:options collection="locations" property="locationTblPK" labelProperty="locationID" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Room Number:</td>
                <td align="left">
                  <html:text name="roomNewEditForm" property="txtRoomNumber" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
                </td>
              </tr>
              <tr>
                <td height="50" colspan="2" align="right">
                  <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="4" value="Save" onclick="validateAndSubmit();" />
                  <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="5" value="Cancel" onclick="cancelNew();" />
                  <input name="deleteButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="6" value="Reset" onclick="return resetFields();" />
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
