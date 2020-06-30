<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="records" name="records" scope="request" type="java.util.ArrayList" />
<bean:define id="batchNumber" name="batchNumber" scope="request" type="java.lang.String" />
<bean:define id="binderNumber" name="binderNumber" scope="request" type="java.lang.String" />

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
int totalRecords = 0;
totalRecords = records.size();
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Record Listing)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
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
    
      function validateAndSubmit(){
        var thisForm = document.forms[0];
        thisForm.submit();
      }
    
      function setRowVals( index ){
      
        var hdnRowValueObj = null;
        var hdnRecordTblPkObj = null;
        var chkIsRecordAvailableObj = null;
        var txtCommentsObj = null;
        
        hdnRowValueObj = document.getElementById('hdnRowValue['+index+']');
        hdnRecordTblPkObj = document.getElementById('hdnRecordTblPk['+index+']');
        chkIsRecordAvailableObj = document.getElementById('chkIsRecordAvailable['+index+']'); 
        txtCommentsObj = document.getElementById('txtComments['+index+']');

        if( (txtCommentsObj.value == "") || (trim(txtCommentsObj.value).length == 0)  ){
          txtCommentsObj.value = "No Comments"
        }
        
        if( chkIsRecordAvailableObj.checked ){
          hdnRowValueObj.value = hdnRecordTblPkObj.value+"|"+"true"+"|"+txtCommentsObj.value; 
        }else{
          hdnRowValueObj.value = hdnRecordTblPkObj.value+"|"+"false"+"|"+txtCommentsObj.value;
        }
        
        return true;
      }
      
      function cancelEdit(){
        document.forms[0].target="_self";
        document.forms[0].action="batchProcessListAction.do";
        document.forms[0].submit();
      }
    
      function window_onload(){
        var chkIsRecordAvailableObj = null;
        for( var index = 0; index < <%=totalRecords%>; index++ ){
          chkIsRecordAvailableObj = document.getElementById('chkIsRecordAvailable['+index+']');
          if( chkIsRecordAvailableObj.value == "true" ){
            chkIsRecordAvailableObj.checked=true;
          }else{
            chkIsRecordAvailableObj.checked=false;
          }
        }
        return true;
      }
    
    </script>
  </head>
  <body onload="window_onload();">
    <html:form action="editRecordAction" name="recordListForm" type="dims.web.actionforms.process.RecordListForm">
    <jsp:include page="/header_transaction.jsp"/>
    <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:650px;">
            <legend>Record Listing:</legend>
            <table width="650px" border="0" align="center" cellpadding="2" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="4" align="left" valign="top"/>
              </tr>
              <tr>
                <td align="right">Batch Number:</td>
                <td align="left">
                  <html:text name="recordListForm" property="txtBatchNumber" styleClass="bdrClr_Lvl_2 componentStyle" style="width:166px" readonly="true" value="<%=batchNumber%>" />
                </td>
                <td align="right">Binder Number:</td>
                <td>
                  <html:text name="recordListForm" property="txtBinderNumber" styleClass="bdrClr_Lvl_2 componentStyle" style="width:166px" readonly="true" value="<%=binderNumber%>" />
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
                <th width="43" height="18" align="center">Sl. No.</th>
                <th width="81" align="center">Is Available?</th>
                <th width="220" align="center">Record Number</th>
                <th width="291" align="center">Comment</th>
              </tr>
              <logic:notEmpty name="records" scope="request">
                <logic:iterate id="record" name="records" indexId="index">
                  <bean:define id="recordTblPK" name="record" property="recordTblPK" type="java.lang.String" />
                  <bean:define id="recordNumber" name="record" property="recordNumber" type="java.lang.String" />
                  <bean:define id="recordComments" name="record" property="recordComments" type="java.lang.String" />
                  <bean:define id="isAvailable" name="record" property="isAvailable" type="java.lang.Boolean" />
                  <bean:define id="binderTblFk" name="record" property="binderTblFk" type="java.lang.String" />
                
                  <%if (firstTableRow == true){ firstTableRow = false; %>
                    <tr class="bgColor_Lvl_3">
                  <%}else{ firstTableRow = true; %>
                    <tr class="bgColor_Lvl_4">                  
                  <%}%>
                      <td align="center"><%=index%></td>
                      <td align="center"> 
                        <input type="HIDDEN" name="hdnRecordTblPk[<%=index%>]" id="hdnRecordTblPk[<%=index%>]" value="<%=recordTblPK%>" />
                        <input type="HIDDEN" name="hdnRowValue[<%=index%>]" id="hdnRowValue[<%=index%>]" value=" " />
                        <% if( ((Boolean)isAvailable).booleanValue()) {%>
                          <input type="CHECKBOX" name="chkIsRecordAvailable[<%=index%>]" id="chkIsRecordAvailable[<%=index%>]" checked="checked" onclick="return setRowVals(<%=index%>);" value="true" >
                        <%}else{%>
                          <input type="CHECKBOX" name="chkIsRecordAvailable[<%=index%>]" id="chkIsRecordAvailable[<%=index%>]" onclick="return setRowVals(<%=index%>);" value="false" >
                        <%}%>
                      </td>
                      <td align="center">
                        <%=recordNumber%>
                      </td>
                      <td align="center">
                        <input type="TEXT" name="txtComments[<%=index%>]" id="txtComments[<%=index%>]" class="bdrClr_Lvl_2 componentStyle" style="width:99%" value="<%=recordComments%>" onchange="return setRowVals(<%=index%>);" />
                      </td>
                    </tr>                
                </logic:iterate>
              </logic:notEmpty>
              <logic:empty name="records" scope="request">
                <tr class="bgColor_Lvl_3">
                  <td colspan="4" align="center" valign="middle">
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
              <td width="630px" style="color:black">
                &nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr><td height="2px"></td></tr>
      <tr>
        <td align="center">
          <table width="650px" height="35" border="0" cellpadding="1" cellspacing="1">
            <tr>
              <td align="right">
                <input name="saveButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" value="Save" onclick="validateAndSubmit();" />
                <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" value="Cancel" onclick="cancelEdit();" />
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    </html:form>
  </body>
</html>
