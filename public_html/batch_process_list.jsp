<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="departments" name="departments" scope="request" type="java.util.ArrayList" />
<bean:define id="batches" name="batches" scope="request" type="java.util.ArrayList" />
<bean:define id="pageCount" name="batchProcessListForm" property="hdnPageCount" />
<bean:define id="pageNumber" name="batchProcessListForm" property="hdnPageNumber" />

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
int totalBatches = 0;
totalBatches = batches.size();
%>

<html>
  <head>
    <title>DBSentry - Digital Imaging Management System (Batch Process List)</title>
    <link href="main.css" rel="stylesheet" type="text/css" />
    <script src="datePicker.js"></script>
    <script src="timezones.js"></script>
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
      navBar.onclick="batchProcessNavigate()";
    
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
    
      //Called When Navigation bar buttons Clicked
      function batchProcessNavigate(){
        var thisForm=document.forms[0]
        thisForm.hdnPageNumber.value=navBar.getPageNumber();
        thisForm.target = "_self"
        document.forms[0].action="batchProcessListAction.do?oper=nav"
        thisForm.submit();
      }
    
      function searchList(){
        document.forms[0].target="_self";
        document.forms[0].action="batchProcessListAction.do?oper=search";
        document.forms[0].submit();
      }
    
    
      function unCheckRem( index ){
        var radSelectObj = null;
        
        for( var radIndex = 0; radIndex < <%=totalBatches%>; radIndex++ ){  
          if( radIndex != index ){
            radSelectObj = document.getElementById('radSelect['+radIndex+']');
            radSelectObj.checked = false;
          }
        }
        return;
      }
      
      
      function editBatchProcess(){
        var thisForm = document.forms[0];
        var chkIsCompleted = null;
        var radSelectObj = null;
        var id = null;
        var arrayIndex = null;
        
        for(index = 0 ; index < <%=totalBatches%> ;index++){
          radSelectObj = document.getElementById('radSelect['+index+']'); 
          if(radSelectObj.checked){   
            id = radSelectObj.value;
            /*chkIsCompleted = document.getElementById('chkIsCompleted['+index+']');
            if(chkIsCompleted.checked){
              alert("Complete Batch Process cannot be edited.");
              return false; 
            }*/
            break;
          }
        }
        
        if( id == null ){
          alert("Please select an item");
          return false;
        }
        
        document.forms[0].target="_self";
        document.forms[0].action="editBatchProcessB4Action.do";
        document.forms[0].submit();
      
      }
    </script>
    <script>
    
      var createDateFrom = new Calendar("createDateFrom",0,0);
      createDateFrom.noTimezone=true;
      createDateFrom.noTime=true;
      createDateFrom.onclick="selectedValues(createDateFrom,'hdnBatchFromDate')";
      createDateFrom.onclear="clearValues('hdnBatchFromDate')";
      createDateFrom.tooltipCalendar='Click to Popup the Calendar';
      createDateFrom.tooltipCancel='Cancel Changes';
      createDateFrom.tooltipClear='Click to Clear';
      createDateFrom.tooltipDay='Choose Day';
      createDateFrom.tooltipHour='Choose Hours (0-23)';
      createDateFrom.tooltipMinute='Choose Minutes (0-59)';
      createDateFrom.tooltipNextMonth='Move to Next Month';
      createDateFrom.tooltipNextYear='Move to Next Year';
      createDateFrom.tooltipNow='Move to Now';
      createDateFrom.tooltipOk='Submit Changes';
      createDateFrom.tooltipPrevMonth='Move to Previous Month';
      createDateFrom.tooltipPrevYear='Move to Previous Year';
      createDateFrom.tooltipSecond='Choose Seconds (0-59)';
      createDateFrom.tooltipTimezone='Choose Timezone';
    
      var createDateTo = new Calendar("createDateTo",0,0);
      createDateTo.noTimezone=true;
      createDateTo.noTime=true;
      createDateTo.onclick="selectedValues(createDateTo,'hdnBatchToDate')";
      createDateTo.onclear="clearValues('hdnBatchToDate')";
      createDateTo.timezoneDisabled=true;
      createDateTo.tooltipCalendar='Click to Popup the Calendar';
      createDateTo.tooltipCancel='Cancel Changes';
      createDateTo.tooltipClear='Click to Clear';
      createDateTo.tooltipDay='Choose Day';
      createDateTo.tooltipHour='Choose Hours (0-23)';
      createDateTo.tooltipMinute='Choose Minutes (0-59)';
      createDateTo.tooltipNextMonth='Move to Next Month';
      createDateTo.tooltipNextYear='Move to Next Year';
      createDateTo.tooltipNow='Move to Now';
      createDateTo.tooltipOk='Submit Changes';
      createDateTo.tooltipPrevMonth='Move to Previous Month';
      createDateTo.tooltipPrevYear='Move to Previous Year';
      createDateTo.tooltipSecond='Choose Seconds (0-59)';
      createDateTo.tooltipTimezone='Choose Timezone';
    
      function  selectedValues(dtPickerObj, txtDateTimeName){
        var dateString=dtPickerObj.getMonth();
        dateString+="/" +dtPickerObj.getDay();
        dateString+="/" +dtPickerObj.getYear();
        dateString+=" " +dtPickerObj.getHours();
        dateString+=":" +dtPickerObj.getMinutes();
        dateString+=":" +dtPickerObj.getSeconds();
        eval("document.forms[0]."+txtDateTimeName).value=dateString;
      }  
    
      function  clearValues(txtDateTimeName){
        eval("document.forms[0]."+txtDateTimeName).value="";
      }
      
      function window_onload(){
        var currentDate=null;
        if(document.forms[0].hdnBatchFromDate.value!=""){
          currentDate=new Date(document.forms[0].hdnBatchFromDate.value);
        }else{
          currentDate=new Date();
        }
        createDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
        //createDateFrom.setTimezone(document.forms[0].timezone.value);
        if(document.forms[0].hdnBatchFromDate.value!=""){
          createDateFrom.click();
        }
        
        if(document.forms[0].hdnBatchToDate.value!=""){
          currentDate=new Date(document.forms[0].hdnBatchToDate.value);
        }else{
          currentDate=new Date();
        }
        createDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
        //createDateTo.setTimezone(document.forms[0].timezone.value);
        if(document.forms[0].hdnBatchToDate.value!=""){
          createDateTo.click();
        }
      }
    </script>
  </head>
  <body onload="window_onload();">
    <html:form action="/batchProcessListAction" name="batchProcessListForm" type="dims.web.actionforms.process.BatchProcessListForm" scope="request">
      <html:hidden name="batchProcessListForm" property="hdnPageCount" />
      <html:hidden name="batchProcessListForm" property="hdnPageNumber" />
      <jsp:include page="/header_transaction.jsp" />
      <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
          <legend>Search Batch Process:</legend>
          <table width="650" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="6" align="left" valign="top"></td>
              </tr>
              <tr>
                <td width="125px" align="right">Batch Number: </td>
                <td width="169px" align="left">
                  <html:text name="batchProcessListForm" property="txtBatchNumber" styleClass="bdrClr_Lvl_2" style="width:166px" onkeypress=" return integerOnly(event);" />
                </td>
                <td align="left">&nbsp;</td>
                <td width="125px" align="right">Department ID:</td>
                <td width="168px" align="left">
                  <html:select name="batchProcessListForm" property="cboDepartmentId" styleClass="bdrClr_Lvl_2" style="width:166px" >
                    <html:options collection="departments" property="departmentId" labelProperty="departmentId" />
                  </html:select>
                </td>
                <td width="10" align="left">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td width="125px" align="right">Batch From Date:</td>
                <td width="169px" align="left">
                  <html:hidden name="batchProcessListForm" property="hdnBatchFromDate" styleClass="bdrClr_Lvl_2 componentStyle" style="width:166px"  />
                  <script>createDateFrom.render();</script>
                </td>
                <td align="left">
                  &nbsp;
                </td>
                <td width="125px" align="right">Batch To Date:</td>
                <td width="168px" align="left">
                  <html:hidden name="batchProcessListForm" property="hdnBatchToDate" styleClass="bdrClr_Lvl_2 componentStyle" style="width:166px"  />
                  <script>createDateTo.render();</script>
                </td>
                <td width="10" align="left">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td height="35" colspan="6" align="right">
                  <input name="searchButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:20px" value="Search" onclick="return searchList();" />
                </td>
              </tr>
            </table>
            </fieldset>
          </td>
        </tr>
        <tr>
          <td>
            <table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="25">
                  <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Edit" title="Edit Batch Process" onclick=" editBatchProcess();" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="center">
            <div style="overflow:auto; width:770px; height:250px;" class="bdrClr_Lvl_2">
              <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                <tr>
                  <th width="50px" align="center">Select</th>
                  <th width="254px" align="center">Batch Number </th>
                  <th width="150px" align="center">Batch Start Date </th>
                  <th width="150px" align="center">Batch End Date </th>
                  <th width="150px" align="center">Department ID</th>
                  <th width="150px" align="center">Percentage </th>
                  <th width="50px" align="center">Complete</th>
                </tr>
                <logic:notEmpty name="batches" scope="request">
                  <logic:iterate name="batches" id="batch" indexId="index">
                    
                    <bean:define id="batchProcessTblPk" name="batch" property="batchProcessTblPk" type="java.lang.String" />
                    <bean:define id="batchNumber" name="batch" property="batchNumber" type="java.lang.String" />
                    <bean:define id="isCompleted" name="batch" property="isCompleted" type="java.lang.Boolean" />
                    <bean:define id="batchStartDate" name="batch" property="batchStartDate" type="java.lang.String" />
                    <bean:define id="batchEndDate" name="batch" property="batchEndDate" type="java.lang.String" />
                    <bean:define id="departmentId" name="batch" property="departmentId" type="java.lang.String" />
                    
                    <%if (firstTableRow == true){ firstTableRow = false; %>
                      <tr class="bgColor_Lvl_3">
                    <%}else{ firstTableRow = true; %>
                      <tr class="bgColor_Lvl_4">                  
                    <%}%>
                      <td align="center">
                      <% String radValue = batchProcessTblPk+":"+batchNumber+":"+batchStartDate+":"+batchEndDate+":"+departmentId; %>
                        <input type="RADIO" name="radSelect[<%=index%>]" id="radSelect[<%=index%>]" value="<%=radValue%>" onclick=" return unCheckRem(<%=index%>);" >
                      </td>
                      <td>
                        <%=batchNumber%>
                      </td>
                      <td>
                        <%=batchStartDate%>
                      </td>
                      <td>
                        <%=batchEndDate%>
                      </td>
                      <td>
                        <%=departmentId%>
                      </td>
                      <td><bean:write name="batch" property="percentageCompleted" /></td>
                      <td>
                        <% boolean isChecked = ((Boolean)isCompleted).booleanValue(); 
                           if( isChecked ){
                        %>
                          <input type="CHECKBOX" name="chkIsCompleted[<%=index%>]" id="chkIsCompleted[<%=index%>]" checked="checked" value="<%=isChecked%>" disabled="disabled" />
                        <%}else{%>
                          <input type="CHECKBOX" name="chkIsCompleted[<%=index%>]" id="chkIsCompleted[<%=index%>]" value="<%=isChecked%>" disabled="disabled" />
                        <%}%>
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="batches" scope="request">
                <tr class="bgColor_Lvl_3">
                  <td colspan="7" align="center" valign="middle">
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
            <table class="bdrClr_Lvl_2 bgClrLvl_4 " width="770px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
              <tr>
                <td height="20px" width="30px"><div class="imgStatusMsg"></div></td>
                <td width="557px" style="color:black">
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
      </table>
    </html:form>
  </body>
</html>