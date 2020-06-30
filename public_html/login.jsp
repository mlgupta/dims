<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script name="javascript">
  window.name="dims"
</script>
<html>
<head>
<title>DBSentry - Digital Imaging Management System (Login)</title>
<link href="main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
-->
</style>
<html:javascript formName="loginForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
<script>

  function validateAndSubmit(){
    var thisForm = document.forms[0];
    if(validateLoginForm(thisForm)){
      thisForm.submit();    
    }
    return false;
  }

  function resetFields(){
    var thisForm = document.forms[0];
    thisForm.userID.value = "";
    thisForm.userPassword.value = "";
    thisForm.userID.focus();
    return false;
  }
</script>
</head>
<body>
<html:form action="/loginAction" name="loginForm" type="dims.web.actionforms.loginout.LoginForm" focus="userID" 
  onsubmit="return validateLoginForm(this);">
  <table height="100%" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td valign="middle">
        <table width="700" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrClr_Lvl_2">
          <tr>
            <td height="66px">
              <table width="100%" height="79"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="10px" colspan="2" background="images/bar_tile10_up.gif"></td>
                </tr>
                <tr>
                  <td height="13px" colspan="2"></td>
                </tr>
                <tr>
                  <td width="158" height="53px" align="center">
                    <a href="http://www.dbsentry.com" target="_blank">
                      <img src="images/logo_dbsentry.gif" width="140" height="40" border="0" style="border:0px solid #949494;">
                    </a>
                  </td>
                  <td>
                    <table border="0" cellspacing="1" cellpadding="1">
                      <tr>
                        <td height="35px" class="textHeading20bold bdrLftClr_Lvl_1">&nbsp;&nbsp;Digital Imaging 
                          Management System
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>    
            </td>
          </tr>
          <tr>
            <td background="images/bar_tile19.gif" class="bdrTopClr_Lvl_2 bdrBtmClr_Lvl_2">&nbsp;</td>
          </tr>
          <tr>
            <td height="255">
              <table width="670" height="210"  border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="100px" colspan="2" valign="top">
                    <table width="670px" border="0" cellspacing="0" cellpadding="1">
                      <tr>
                        <td class="text13Bold">
                          <a href="http://www.dbsentry.com/solutions.htm" target="_blank"></a> 
                          Manage Your Digital Imaging   Online!
                        </td>
                      </tr>
                    </table>        
                  </td>
                </tr>
                <tr>
                  <td width="335" height="110px">&nbsp;</td>
                  <td width="335">
                    <table width="335" border="0" cellspacing="3" cellpadding="0">
                      <tr>
                        <td colspan="3" height="5px"></td>
                      </tr>
                      <tr>
                        <td width="161px" align="right" class="text11Normal">UserID:</td>
                        <td width="157px">
                          <html:text property="userID" style="width:145px; border:1px solid #949494" styleClass="bdrClr_Lvl_2" tabindex="1"/>
                        </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right" class="text11Normal">Password: </td>
                        <td>
                          <html:password property="userPassword" style="width:145px; border:1px solid #949494" styleClass="bdrClr_Lvl_2" tabindex="2"/>
                        </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td height="25px">&nbsp;</td>
                        <td>
                          <input type="button" Class="buttons bdrClr_Lvl_2" style="width:70px" tabindex="3" value="Login" onclick="validateAndSubmit();" >
                          <input type="button" Class="buttons bdrClr_Lvl_2" style="width:70px" tabindex="4" value="Reset" onclick="return resetFields();" >
                        </td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td colspan="3" height="5px"></td>
                      </tr>
                      <tr>
                        <td colspan="3" height="15px" align="right" >
                          <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
                        </td>
                      </tr>
                    </table>        
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="10px" background="images/bar_tile10_down.gif"></td>
          </tr>
        </table>
        <table width="700" border="0" align="center" cellpadding="1" cellspacing="1">
          <tr>
            <td align="right" class="textCopyRight">Copyright &copy; 2006, DBSentry Corp. All rights reserved. &nbsp;&nbsp;&nbsp;(Best viewed in 1024 x 768 screen resolution  )</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>