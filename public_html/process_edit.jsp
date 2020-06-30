<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="binders" name="binders" scope="request" type="java.util.ArrayList" />
<bean:define id="pageCount" name="binderProcessListForm" property="hdnPageCount" />
<bean:define id="pageNumber" name="binderProcessListForm" property="hdnPageNumber" />
<bean:define id="txtBatchNumber" name="binderProcessListForm" property="txtBatchNumber" />

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
int totalBinders = 0;
totalBinders = binders.size();
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Binder Listing)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script src="navigationbar.js" ></script>
    <script src="general.js" ></script>
    
    <script>
      function MM_findObj(n, d) { //v4.01
        var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
          d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
        if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
        for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
        if(!x && d.getElementById) x=d.getElementById(n); return x;
      }
    </script>
    <script>
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNumber%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="binderProcessNavigate()";
    
      navBar.msgPageNotExist="<bean:message key="page.pageNumberNotExists" />";
      navBar.msgNumberOnly="<bean:message key="page.enterNumbersOnly"/>";    
      navBar.msgEnterPageNo="<bean:message key="page.enterPageNo"/>";
      navBar.msgOnFirst="<bean:message key="page.onFirst"/>";
      navBar.msgOnLast="<bean:message key="page.onLast"/>";
    
      navBar.lblPage="<bean:message key="lbl.Page"/>";
      navBar.lblOf="<bean:message key="lbl.Of"/>";
    
      navBar.tooltipPageNo="<bean:message key="tooltips.PageNo"/>";
      navBar.tooltipFirst="<bean:message key="tooltips.First"/>";
      navBar.tooltipNext="<bean:message key="tooltips.Next"/>";
      navBar.tooltipPrev="<bean:message key="tooltips.Previous"/>";
      navBar.tooltipLast="<bean:message key="tooltips.Last"/>";
      navBar.tooltipGo="<bean:message key="btn.Go"/>";
    </script>
    <script>
    
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        checkAllProcessed();
        thisForm.submit();
      }
    
      function checkAllProcessed(){
      
        var chkIsCompletedObj = null;
        var isChecked = true;
        
        for( var index = 0; index < <%=totalBinders%>; index++  ){
          chkIsCompletedObj = document.getElementById('chkIsCompleted['+index+']');
          if( chkIsCompletedObj.checked ){
            //isChecked = true;
          }else{
            isChecked = false;
          }
        }
        document.forms[0].blnIsBatchProcessed.value=isChecked;
        return;
      }
      
      function setRowVals( index ){
      
        var hdnRowValueObj = null;
        var hdnBinderProcessTblPkObj = null;
        var txtAssignedToObj = null;
        var chkIsCompletedObj = null;
        var txtCommentsObj = null;
        
        hdnRowValueObj = document.getElementById('hdnRowValue['+index+']');
        hdnBinderProcessTblPkObj = document.getElementById('hdnBinderProcessTblPk['+index+']');
        txtAssignedToObj = document.getElementById('txtAssignedTo['+index+']');
        chkIsCompletedObj = document.getElementById('chkIsCompleted['+index+']'); 
        txtCommentsObj = document.getElementById('txtComments['+index+']');

        if( (txtAssignedToObj.value == "") || (trim(txtAssignedToObj.value).length == 0) ){
          txtAssignedToObj.value = "Not Assigned"
        }
        
        if( (txtCommentsObj.value == "") || (trim(txtCommentsObj.value).length == 0)  ){
          txtCommentsObj.value = "No Comments"
        }
        
        if( chkIsCompletedObj.checked ){
          hdnRowValueObj.value = hdnBinderProcessTblPkObj.value+"|"+txtAssignedToObj.value+"|"+"true"+"|"+txtCommentsObj.value; 
        }else{
          hdnRowValueObj.value = hdnBinderProcessTblPkObj.value+"|"+txtAssignedToObj.value+"|"+"false"+"|"+txtCommentsObj.value;
        }
        return true;
      }
      
      //Called When Navigation bar buttons Clicked
      function binderProcessNavigate(){
        var thisForm=document.forms[0]
        thisForm.hdnPageNumber.value=navBar.getPageNumber();
        thisForm.target = "_self"
        document.forms[0].action="editBatchProcessB4Action.do?oper=nav"
        document.forms[0].submit();
      }
    
      function cancelEdit(){
        document.forms[0].target="_self";
        document.forms[0].action="batchProcessListAction.do";
        document.forms[0].submit();
      }
    
      function resetFields(){
        var thisForm = document.forms[0];
        var hdnRowValueObj = null;
        var hdnBinderProcessTblPkObj = null;
        var txtAssignedToObj = null;
        var chkIsCompletedObj = null;
        var txtCommentsObj = null;

        for( var index = 0; index < <%=totalBinders%>; index++  ){
          hdnRowValueObj = document.getElementById('hdnRowValue['+index+']');
          txtAssignedToObj = document.getElementById('txtAssignedTo['+index+']');
          chkIsCompletedObj = document.getElementById('chkIsCompleted['+index+']'); 
          txtCommentsObj = document.getElementById('txtComments['+index+']');

          hdnRowValueObj.value = "";
          txtAssignedToObj.value = "";
          txtCommentsObj.value = "";
          chkIsCompletedObj.checked = false;

        }
        return true;
      }

    </script>
  </head>
  <body>
    <html:form action="/editBatchProcessAction" name="binderProcessListForm" type="dims.web.actionforms.process.BinderProcessListForm">
      <html:hidden name="binderProcessListForm" property="hdnBatchProcessTblFk" />
      <html:hidden name="binderProcessListForm" property="hdnPageCount" />
      <html:hidden name="binderProcessListForm" property="hdnPageNumber" />
      <html:hidden name="binderProcessListForm" property="blnIsBatchProcessed" value="false" />
      
      <jsp:include page="/header_transaction.jsp"/>
      <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
          <td align="center">&nbsp;</td>
        </tr>
        <tr>
          <td height="150x" align="center">
            <fieldset style="width:650px;">
              <legend>Binder Listing :</legend>
              <table width="650" border="0" align="center" cellpadding="2" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
                <tr>
                  <td height="10" colspan="4" align="left" valign="top"/>
                </tr>
                <tr>
                  <td align="right">Batch Number:</td>
                  <td align="left">
                    <html:text name="binderProcessListForm" property="txtBatchNumber" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" />
                  </td>
                  <td align="right">Batch Start Date:</td>
                  <td>
                    <html:text name="binderProcessListForm" property="txtBatchStartDate" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" />
                  </td>
                </tr>
                <tr>
                  <td height="23" align="right">Department ID:</td>
                  <td align="left">
                    <html:text name="binderProcessListForm" property="txtDepartmentId" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" />
                  </td>
                  <td align="right">Batch End Date:</td>
                  <td align="left">
                    <html:text name="binderProcessListForm" property="txtBatchEndDate" styleClass="bdrClr_Lvl_2" style="width:166px" readonly="true" />
                  </td>
                </tr>
                <tr>
                  <td width="101px" align="right">&nbsp;</td>
                  <td width="195px" align="left">&nbsp;</td>
                  <td width="144px" align="left">&nbsp;</td>
                  <td width="189px" align="left">&nbsp;</td>
                </tr>
              </table>
            </fieldset>
          </td>
        </tr>
        <tr>
          <td align="center">
            <div style="overflow:auto; width:650px; height:250px;" class="bdrClr_Lvl_2">
              <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                <tr>
                  <th width="42px" height="18px" align="center">Sl. No.</th>
                  <th width="202px" align="center">Binder Number</th>
                  <th width="132px" align="center">Assigned To</th>
                  <th width="96px" align="center">Is Completed?</th>
                  <th width="160px" align="center">Comment</th>
                </tr>
                <logic:notEmpty name="binders" scope="request">
                  <logic:iterate name="binders" id="binder" indexId="index">

                    <bean:define id="binderProcessTblPk" name="binder" property="binderProcessTblPk" type="java.lang.String" />
                    <bean:define id="batchProcessTblFk" name="binder" property="batchProcessTblFk" type="java.lang.String" />
                    <bean:define id="binderTblFk" name="binder" property="binderTblFk" type="java.lang.String" />
                    <bean:define id="binderName" name="binder" property="binderName" type="java.lang.String" />
                    <bean:define id="assignedTo" name="binder" property="assignedTo" type="java.lang.String"  />
                    <bean:define id="isProcessed" name="binder" property="isProcessed" type="java.lang.Boolean" />
                    <bean:define id="binderProcessComments" name="binder" property="binderProcessComments" type="java.lang.String" />
                    
                    <input type="HIDDEN" id="hdnBinderProcessTblPk[<%=index%>]" name="hdnBinderProcessTblPk[<%=index%>]" value="<%=binderProcessTblPk%>" />
                    <input type="HIDDEN" id="hdnRowValue[<%=index%>]" name="hdnRowValue[<%=index%>]" value=" " />
                    
                    <%if (firstTableRow == true){ firstTableRow = false; %>
                      <tr class="bgColor_Lvl_3">
                    <%}else{ firstTableRow = true; %>
                      <tr class="bgColor_Lvl_4">                  
                    <%}%>
                      <td align="center">
                        <%=index.intValue()+1%>
                      </td>
                      <td align="center">
                        <a target="_self" href="recordListAction.do?binderProcessTblPk=<%=binderProcessTblPk%>&batchNumber=<%=txtBatchNumber%>&binderNumber=<%=binderName%>">
                          <%=binderName%>
                        </a>
                      </td>
                      <td align="center">
                        <input type="TEXT" id="txtAssignedTo[<%=index%>]" name="txtAssignedTo[<%=index%>]" class="bdrClr_Lvl_2 componentStyle" style="width:99%" onchange="return setRowVals(<%=index%>);" value="<%=assignedTo%>" />
                      </td>
                      <td align="center">
                        <% boolean isChecked = ((Boolean)isProcessed).booleanValue(); 
                           if( isChecked ){
                        %>
                          <input type="CHECKBOX" checked="checked" name="chkIsCompleted[<%=index%>]" id="chkIsCompleted[<%=index%>]" value="<%=binderName%> " onclick="return setRowVals(<%=index%>);" />
                        <%}else{%>
                          <input type="CHECKBOX" name="chkIsCompleted[<%=index%>]" id="chkIsCompleted[<%=index%>]" value="<%=binderName%>" onclick="return setRowVals(<%=index%>);" />
                        <%}%>
                      </td>
                      <td align="center">
                        <input type="TEXT" id="txtComments[<%=index%>]" name="txtComments[<%=index%>]" class="bdrClr_Lvl_2 componentStyle" style="width:99%" onchange="return setRowVals(<%=index%>);" value="<%=binderProcessComments%>" />
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="binders" scope="request">
                  <tr class="bgColor_Lvl_3">
                    <td colspan="5" align="center" valign="middle">
                      There is no item to display
                    </td>
                  </tr>
                </logic:empty>
              </table>
            </div>
          </td>
        </tr>
        <tr><td height="2px"></td></tr>
        <tr>
          <td align="center">
            <table class="bdrClr_Lvl_2 bgClrLvl_4 " width="650px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
              <tr>
                <td height="20px" width="30px"><div class="imgStatusMsg"></div></td>
                <td width="437px" style="color:black">
                  &nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
                </td>
                <td width="3px">
                  <div style="float:left; width:1px;height:100%;" class="bgClrLvl_2"></div>
                  <div style="float:left; width:1px;height:100%;" class="bgClrLvl_F"></div>
                </td>
                <td width="200px" align="right">
                  <script>navBar.render();</script>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr><td height="2px"></td></tr>
        <tr>
          <td align="center">
            <table width="650" height="35" border="0" cellpadding="1" cellspacing="1">
              <tr>
                <td align="right">
                  <% if( !(((Boolean)request.getAttribute("isCompleted")).booleanValue()) ) {%>
                    <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" title="Save Changes" value="Save" onclick="validateAndSubmit();" />
                  <%}%>
                  <input name="cancelButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" title="Cancel Changes" value="Cancel" onclick="return cancelEdit();" />
                  <input name="resetButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" title="Reset Entries" value="Reset" onclick="return resetFields();" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html>
