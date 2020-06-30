<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="departments" name="departments" scope="request" type="java.util.ArrayList" />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Create New User)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <html:javascript formName="userNewEditForm" dynamicJavascript="true" staticJavascript="false" />
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script>
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        if(validateUserNewEditForm(thisForm)){
          if(thisForm.txtUserPassword.value!=thisForm.txtConfirmPassword.value){
            alert("<bean:message key="errors.password.mismatch"/>");
            thisForm.txtUserPassword.value = "";
            thisForm.txtConfirmPassword.value = "";
            thisForm.txtUserPassword.focus();
            return false;
          }
          if(thisForm.cboDepartments.value=="-1"){
            alert("<bean:message key="errors.select.user"/>");  
            thisForm.cboDepartments.focus();
            return false;
          }
          thisForm.submit();
        }
        return false;
      }
    
      function cancelNew(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="userListAction.do";
        thisForm.submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        thisForm.txtUserID.value = "";
        thisForm.txtUserPassword.value = "";
        thisForm.txtConfirmPassword.value = "";
        thisForm.txtUserName.value = "";
        thisForm.cboDepartments.focus();
        return false;
      }
    
    </script>
  </head>
  <body>
    <html:form action="/newUserAction" name="userNewEditForm" type="dims.web.actionforms.users.UserNewEditForm" onsubmit="return validateUserNewEditForm(this);" focus="cboDepartments" >
    <html:hidden name="userNewEditForm" property="hdnUserTblPk" value="" />
    <jsp:include page="/header_masters.jsp" />
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
            <legend>Add New User:</legend>
            <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="2" align="left" valign="top"/>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Department ID:</td>
                <td align="left">
                  <html:select name="userNewEditForm" property="cboDepartments" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1">
                    <html:options collection="departments" property="departmentTblPk" labelProperty="departmentId" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>User ID:</td>
                <td align="left">
                  <html:text name="userNewEditForm" property="txtUserID" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>User Password:</td>
                <td align="left">
                  <html:password name="userNewEditForm" property="txtUserPassword" styleClass="bdrClr_Lvl_2" style="width:166px" redisplay="false" maxlength="20" tabindex="3" />
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Confirm Password:</td>
                <td align="left">
                  <html:password name="userNewEditForm" property="txtConfirmPassword" styleClass="bdrClr_Lvl_2" style="width:166px" redisplay="false" maxlength="20" tabindex="4" />
                </td>
              </tr>
              <tr>
                <td width="235" align="right"><span class="textRed">* </span>User Name:</td>
                <td width="358" align="left">
                  <html:text name="userNewEditForm" property="txtUserName" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="5" />
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td align="left">
                  <html:radio name="userNewEditForm" property="radStatus" value="1" tabindex="6" />Admin
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td align="left">
                  <html:radio name="userNewEditForm" property="radStatus" value="2" tabindex="7" />Supervisor
                </td>
              </tr>
              <tr>
                <td height="50" colspan="2" align="right">
                  <input name="button" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="8" value="Save" onclick="validateAndSubmit();" />
                  <input name="button" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="9" value="Cancel" onclick="cancelNew();" />
                  <input name="button" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="10" value="Reset" onclick="return resetFields();" />
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
