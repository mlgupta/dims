<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>DBSentry - Digital Imaging Management System (Edit Department)</title>
<link href="main.css" rel="stylesheet" type="text/css" />
<html:javascript formName="departmentNewEditForm" dynamicJavascript="true" staticJavascript="false"/>
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
<script>
  function validateAndSubmit(){
    var thisForm = document.forms[0];
    if(validateDepartmentNewEditForm(thisForm)){
      thisForm.submit();
    }
    return false;
  }

  function cancelEdit(){
    var thisForm = document.forms[0];
    thisForm.target="_self";
    thisForm.action="departmentListAction.do";
    thisForm.submit();
  }

  function resetFields(){
    var thisForm = document.forms[0];
    thisForm.txtDepartmentId.value = "";
    thisForm.txtDepartmentName.value = "";
    thisForm.txaDescription.value = "";
    thisForm.txtDepartmentId.focus();
    return false;
  }

</script>
</head>

<body>
<html:form action="/editDepartmentAction" name="departmentNewEditForm" type="dims.web.actionforms.department.DepartmentNewEditForm" onsubmit="return validateDepartmentNewEditForm(this);" focus="txtDepartmentId" >
<html:hidden name="departmentNewEditForm" property="hdnDeptControlSeq" />
<html:hidden name="departmentNewEditForm" property="hdnBoolDeptEnable" />

<jsp:include page="/header_masters.jsp" />
<table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td height="150x" align="center">
      <fieldset style="width:770px;">
        <legend>Edit Department:</legend>
        <table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
          <tr>
            <td height="10px" colspan="2" align="left" valign="top"></td>
          </tr>
          <tr>
            <td align="right">Department Number: </td>
            <td align="left">
              <html:text name="departmentNewEditForm" property="txtDepartmentNo" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" />
            </td>
          </tr>
          <tr>
            <td align="right"><span class="textRed">* </span>Department ID:</td>
            <td align="left">
              <html:text name="departmentNewEditForm" property="txtDepartmentId" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1" />
            </td>
          </tr>
          <tr>
            <td width="235" align="right"><span class="textRed">* </span>Department Name:</td>
            <td width="358" align="left">
              <html:text name="departmentNewEditForm" property="txtDepartmentName" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2" />
            </td>
          </tr>
          <tr>
            <td align="right" valign="top">Department Description: </td>
            <td align="left">
              <html:textarea name="departmentNewEditForm" property="txaDescription" styleClass="bdrClr_Lvl_2" style="width:166px; height:70px" tabindex="3" />
            </td>
          </tr>
          <tr>
            <td height="50" colspan="2" align="right">
              <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="4" value="Save" onclick="validateAndSubmit();" >
              <input name="cancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:3px" tabindex="5" value="Cancel" onclick="cancelEdit();" >
              <input name="resetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:83px" tabindex="6" value="Reset" onclick="return resetFields();" >
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
