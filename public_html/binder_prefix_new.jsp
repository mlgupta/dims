<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Create New Binder Prefix)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <html:javascript formName="binderNewEditForm" dynamicJavascript="true" staticJavascript="false"/>
    <script language="Javascript1.1" src="staticJavascript.jsp"></script>
    <script>
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        if(validateBinderNewEditForm(thisForm)){
          thisForm.submit();
        }
        return false;
      }
    
      function cancelNew(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="binderListAction.do";
        thisForm.submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        thisForm.txtBinderPrefixName.value = "";
        thisForm.txtBinderPrefixDescription.value = "";
        thisForm.txtBinderPrefixName.focus();
        return false;
      }
    </script>
  </head>
  <body>
    <html:form action="/newBinderAction" name="binderNewEditForm" type="dims.web.actionforms.binders.BinderNewEditForm" onsubmit="return validateBinderNewEditForm(this);" focus="txtBinderPrefixName" >
    <jsp:include page="/header_masters.jsp" />
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
            <legend>Add Binder Prefix:</legend>
            <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="2" align="left" valign="top"/>
              </tr>
              <tr>
                <td align="right">Binder Prefix No.:</td>
                <td align="left">
                  <html:text name="binderNewEditForm" property="txtBinderPrefixNumber" readonly="true" styleClass="bdrClr_Lvl_2" style="width:166px" />
                </td>
              </tr>
              <tr>
                <td align="right"><span class="textRed">* </span>Binder Prefix Name:</td>
                <td align="left">
                  <html:text name="binderNewEditForm" property="txtBinderPrefixName" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1" />
                </td>
              </tr>
              <tr>
                <td width="235" align="right">Binder Prefix Desc.:</td>
                <td width="358" align="left">
                  <html:text name="binderNewEditForm" property="txtBinderPrefixDescription" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
                </td>
              </tr>
              <tr>
                <td height="50" colspan="2" align="right">
                  <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="3" value="Save" onclick="validateAndSubmit();" />
                  <input name="cancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="4" value="Cancel" onclick="cancelNew();" />
                  <input name="resetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="5" value="Reset" onclick="return resetFields();" />
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
