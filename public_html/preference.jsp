<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="dims.web.beans.department.DepartmentListBean" %>

<bean:define id="departments" name="departments" type="java.util.ArrayList" />
<bean:define id="sequences" name="sequences" type="java.util.ArrayList" />

<%
  boolean  firstTableRow = true;
  int totalDepts = departments.size();
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Record Management System (Preference)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script src="general.js"></script>
    <script>
      /* function implemented to sort numbers in array */
      function sortNumber(a,b){
        return a - b;
      }    
      
      /* function called when submit button is clicked */
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        var arr = new Array(<%=totalDepts%>);
        
        for( var index = 0; index < arr.length; index++ ){
          var cboControlFlowObj = document.getElementById('cboControlFlow['+index+']');
          var chkObk = document.getElementById('chkEnableDept['+index+']');
          var txtDeptIdObj = document.getElementById('txtDeptID['+index+']');
          
          if( (cboControlFlowObj.value == "-1") && (chkObk.checked) ){
            alert(" Please set control sequence for "+txtDeptIdObj.value);
            cboControlFlowObj.focus();
            return false;
          }
          arr[index] = parseInt(cboControlFlowObj.value);
        }
        arr = arr.sort(sortNumber);
        
        for( var i = 0; i < arr.length; i++ ){
          for( var j = i+1; j < arr.length; j++){
            if( arr[i] == -1 ){
              break;
            }
            if( arr[i] == arr[j] ){
              alert("Duplicate Control Sequence. Please assign unique sequence.");
              return false;
            }
          }
        }
        thisForm.submit();
      }
    
      /* function called when cancel button is clicked */
      function cancelPreference(){
        var thisForm = document.forms[0];
        thisForm.action = "webtop.jsp";
        thisForm.submit();
      }
      
      /* function called when reset button is clicked */
      function resetFields(){
        for( var index = 0; index < <%=totalDepts%>; index++ ){
          document.getElementById('cboControlFlow['+index+']').value = "-1";
        }
        return;      
      }
      
      /* function called when a new control sequence option is selected */
      function setDepartmentPk(index){
        var chkObj = document.getElementById('chkEnableDept['+index+']');
        var txtDeptIdObj = document.getElementById('txtDeptID['+index+']');
        var cboControlFlowObj = document.getElementById('cboControlFlow['+index+']');
        var chkAllObj = document.forms[0].chkAll;
        
        if(chkObj.checked){
          txtDeptIdObj.disabled=false;
          cboControlFlowObj.disabled=false;
          chkObj.value = document.getElementById('hdnDeptTblPk['+index+']').value;
          for( var indexVal = 0; indexVal < <%=totalDepts%>; indexVal++ ){
            if( !document.getElementById('chkEnableDept['+indexVal+']').checked ){
              chkAllObj.checked=false;
              break;
            }
            chkAllObj.checked=true;
          }
        }else{
          chkAllObj.checked=false;
          txtDeptIdObj.disabled=true;
          cboControlFlowObj.disabled=true;
          chkObj.value = "-1"; 
          
        }
        document.getElementById('chkEnableDept['+index+']').value=chkObj.value;
        return;
      }
      
      /* function called to check / uncheck all the check boxes */
      function checkAll(thisForm){
        var checkValue = (thisForm.chkAll.checked)?true:false;
        var chkEnableDeptObj = null;
        for( var indexVals = 0; indexVals < <%=totalDepts%>; indexVals++ ){
          chkEnableDeptObj = document.getElementById('chkEnableDept['+indexVals+']');
          chkEnableDeptObj.checked = checkValue;
          setDepartmentPk(indexVals);
        }
        return;
      }
    
      /* function called when a department entry is enabled or disabled */
      function enableDisable(index){
        var cboControlFlowObj = document.getElementById('cboControlFlow['+index+']');
        var chkObj = document.getElementById('chkEnableDept['+index+']');
        var chkAllObj = document.forms[0].chkAll;
        if( cboControlFlowObj.value == "Select Control Sequence" ||  cboControlFlowObj.value == "-1"){
          var txtDeptIdObj = document.getElementById('txtDeptID['+index+']');
          txtDeptIdObj.disabled=true;
          cboControlFlowObj.disabled=true;
          chkObj.checked=false;
          chkObj.value = "-1";
          chkAllObj.checked=false;
        }
        return;
      
      }
      
      /* function called when the page has been loaded */
      function window_onload(){
        var chkAllObj = document.forms[0].chkAll;
        for( var index = 0; index < <%=totalDepts%>; index++ ){
          if( !document.getElementById('chkEnableDept['+index+']').checked ){
            chkAllObj.checked=false;
            break;
          }
          chkAllObj.checked=true;
        }
        return;      
      }
      
    </script>
  </head>
  <body onload="window_onload();">
    <html:form action="/preferenceAction" name="preferenceForm" type="dims.web.actionforms.preference.PreferenceForm" >
    <jsp:include page="/header_masters.jsp" />
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center">
          <fieldset style="width:550px;">
            <legend>Preference:</legend>
            <table width="500" border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>
                  <div style="overflow:auto; width:500px; height:250px;" class="bdrClr_Lvl_2">
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                      <tr>
                        <th width="82px" align="center">
                          <input type="CHECKBOX" name="chkAll" id="chkAll" onclick="return checkAll(this.form);" title="Select to enable all departments" />
                        </th>
                        <th width="407px" align="center">Department ID</th>
                        <th width="476px" align="center">Control Flow</th>
                      </tr>
                      <logic:notEmpty name="departments" scope="request">
                        <logic:iterate name="departments" id="department" indexId="index">
                          <bean:define id="departmentTblPk" name="department" property="departmentTblPk" />
                          <bean:define id="departmentId" name="department" property="departmentId" />
                          <bean:define id="deptCntrlSeq" name="department" property="departmentCntrlSeq" />
                          <bean:define id="deptEnabled" name="department" property="deptEnabled" />
                          
                          <%if (firstTableRow == true){ firstTableRow = false; %>
                            <tr class="bgColor_Lvl_3">
                          <%}else{ firstTableRow = true; %>
                            <tr class="bgColor_Lvl_4">                  
                          <%}%>
                            <td align="center">
                              <input type="HIDDEN" id="hdnDeptTblPk[<%=index%>]" name="hdnDeptTblPk[<%=index%>]" value="<%=departmentTblPk%>" />
                              <% if ( ((Boolean)deptEnabled).booleanValue() ) {%>
                                <input type="CHECKBOX" checked="checked" id="chkEnableDept[<%=index%>]" name="chkEnableDept[<%=index%>]" onclick="return setDepartmentPk(<%=index%>);" title="Select to enable <%=departmentId%>" value="<%=departmentTblPk%>" />
                              </td>
                              <td align="center">
                                <input type="TEXT" id="txtDeptID[<%=index%>]" name="txtDeptID[<%=index%>]" class="bdrClr_Lvl_2" style="width:200px" readonly="readonly" value="<%=departmentId%>" />
                              </td>
                              <td align="center">
                                <select id="cboControlFlow[<%=index%>]" name="cboControlFlow[<%=index%>]" class="bdrClr_Lvl_2" style="width:166px" onchange=" return enableDisable(<%=index%>);" value="<%=deptCntrlSeq%>">
                              <%}else{%>
                                <input type="CHECKBOX" id="chkEnableDept[<%=index%>]" name="chkEnableDept[<%=index%>]" onclick="return setDepartmentPk(<%=index%>);" title="Select to enable <%=departmentId%>" value="-1" />
                              </td>
                              <td align="center">
                                <input type="TEXT" id="txtDeptID[<%=index%>]" name="txtDeptID[<%=index%>]" disabled="disabled" class="bdrClr_Lvl_2" style="width:200px" readonly="readonly" value="<%=departmentId%>" />
                              </td>
                              <td align="center">
                                <select id="cboControlFlow[<%=index%>]" name="cboControlFlow[<%=index%>]" disabled="disabled" class="bdrClr_Lvl_2" style="width:166px" value="<%=deptCntrlSeq%>">
                              <%}%>
                                  <logic:iterate id="dept" name="sequences">
                                    <bean:define id="departmentCntrlSeq" name="dept" property="departmentCntrlSeq" />
                                    <% if( ((String)departmentCntrlSeq).equals("Select Control Sequence") ){ %>
                                      <% if( ((String)departmentCntrlSeq).equals((String)deptCntrlSeq))  {%>
                                        <option value="-1" selected="selected"><%=departmentCntrlSeq%></option>  
                                      <%}else{%>
                                        <option value="-1"><%=departmentCntrlSeq%></option>
                                      <%}%>
                                    <%}else{%>
                                      <% if( ((String)departmentCntrlSeq).equals((String)deptCntrlSeq))  {%>
                                        <option value="<%=departmentCntrlSeq%>" selected="selected"><%=departmentCntrlSeq%></option>
                                      <%}else{%>
                                        <option value="<%=departmentCntrlSeq%>"><%=departmentCntrlSeq%></option>
                                      <%}%>
                                      
                                    <%}%>
                                  </logic:iterate>
                                </select>
                            </td>
                          </tr>
                        </logic:iterate>
                      </logic:notEmpty>
                      <logic:empty name="departments" scope="request">
                        <tr class="bgColor_Lvl_3">
                          <td colspan="3" align="center" valign="middle">
                            There is no item to display
                          </td>
                        </tr>
                      </logic:empty>
                    </table>
                  </div>
                </td>
              </tr>
              <tr>
                <% if( totalDepts > 0  ){ %>
                  <td height="50" align="right">
                    <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Save" title="Save Control Sequence" onclick="return validateAndSubmit();" />
                    <input name="cancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Cancel" title="Cancel" onclick="cancelPreference();" />
                    <input name="resetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Reset" title="Reset Control Sequence" onclick="return resetFields();" />
                  </td>
                <%}%>
              </tr>
            </table>
          </fieldset>
        </td>
      </tr>
      <tr><td height="2px"></td></tr>
      <tr>
        <td align="center">
          <table class="bdrClr_Lvl_2 bgClrLvl_4 " width="550px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
            <tr>
              <td height="20px" width="30px"><div class="imgStatusMsg"></div></td>
              <td width="530px" style="color:black">
                &nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    </html:form>
  </body>
</html>
