<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="batchs" name="batchs" scope="request" type="java.util.ArrayList" />
<bean:define id="pageCount" name="batchListForm" property="hdnPageCount" />
<bean:define id="pageNumber" name="batchListForm" property="hdnPageNumber" />

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>

<html>
  <head>
    <title>DBSentry - Digital Imaging Management System (Batch List)</title>
    <link href="main.css" rel="stylesheet" type="text/css" />
    <script src="navigationbar.js" ></script>
    <script src="general.js" ></script>
    <script src="datePicker.js"></script>
    <script src="timezone.js"></script>
    <script>
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNumber%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="batchNavigate()";
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


    <!-- Date Related Scripts Starts -->
    <script>
      var createDateFrom = new Calendar("createDateFrom",0,0);
      createDateFrom.noTimezone=true;
      createDateFrom.noTime=true;
      createDateFrom.onclick="selectedValues(createDateFrom,'txtSearchBatchFromDate')";
      createDateFrom.onclear="clearValues('txtSearchBatchFromDate')";
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
      createDateTo.onclick="selectedValues(createDateTo,'txtSearchBatchToDate')";
      createDateTo.onclear="clearValues('txtSearchBatchToDate')";
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
        //alert(txtDateTimeName.value);
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
  </script>
  
  <script>
    
    function window_onload(){
      var currentDate=null;
      if(document.forms[0].txtSearchBatchFromDate.value!=""){
        currentDate=new Date(document.forms[0].txtSearchBatchFromDate.value);
      }else{
        currentDate=new Date();
      }
      createDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                              currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
      //createDateFrom.setTimezone(document.forms[0].timezone.value);
      if(document.forms[0].txtSearchBatchFromDate.value!=""){
        createDateFrom.click();
      }
      
      if(document.forms[0].txtSearchBatchToDate.value!=""){
        currentDate=new Date(document.forms[0].txtSearchBatchToDate.value);
      }else{
        currentDate=new Date();
      }
      createDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                              currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
      //createDateTo.setTimezone(document.forms[0].timezone.value);
      if(document.forms[0].txtSearchBatchToDate.value!=""){
        createDateTo.click();
      }
    }
  </script>

  <!-- Date Related Scripts Ends-->  

    <script>
      //Called When Navigation bar buttons Clicked
      function batchNavigate(){
        var thisForm=document.forms[0]
        thisForm.hdnPageNumber.value=navBar.getPageNumber();
        thisForm.target = "_self"
        document.forms[0].action="batchListAction.do?oper=nav"
        thisForm.submit();
      }
    
      function newBatch(){
        document.forms[0].target="_self";
        document.forms[0].action="newBatchB4Action.do";
        document.forms[0].submit();
      }
    
      function editBatch(){
        thisForm = document.forms[0];
        var id = null;
        if( typeof thisForm.radSelect != "undefined"  ){
          if( typeof thisForm.radSelect.length != "undefined" ){
            for(index = 0 ; index < thisForm.radSelect.length ;index++){  
              if(thisForm.radSelect[index].checked){   
                id = document.forms[0].radSelect[index].value;
                break;
              }
            }
          }else{
            if( thisForm.radSelect.checked ){
              id = document.forms[0].radSelect.value;
            }
          }
        }else{
          alert("There is no item to select");
          return false;
        }
        if( id == null ){
          alert("Please select an item");
          return false;
        }
        document.forms[0].target="_self";
        document.forms[0].action="editBatchB4Action.do";
        document.forms[0].submit();
      }
    
    
    
    function submitToProcess(){
        thisForm = document.forms[0];
        var id = null;
        var department=null;
        if( typeof thisForm.radSelect != "undefined" ){
          if( typeof thisForm.radSelect.length != "undefined" ){
            for(index = 0 ; index < thisForm.radSelect.length ;index++){  
              if(thisForm.radSelect[index].checked){   
                id = document.forms[0].radSelect[index].value;
                break;
              }
            }
          }else{
            if( thisForm.radSelect.checked ){
              id = document.forms[0].radSelect.value;
            }
          }
        }else{
          alert("There is no item to select");
          return false;
        }
        if( id == null ){
          alert("Please select an item");
          return false;
        }
        document.forms[0].target="_self";
        document.forms[0].action="submitToBatchProcessAction.do?id="+id;
        document.forms[0].submit();
      }
    
    
      function deleteBatch(){
        thisForm = document.forms[0];
        var id = null;
        var batchName = null;
        if( typeof thisForm.radSelect != "undefined"  ){
          if( typeof thisForm.radSelect.length != "undefined" ){
            for(index = 0 ; index < thisForm.radSelect.length ;index++){  
              if(thisForm.radSelect[index].checked){   
                id = document.forms[0].radSelect[index].value;
                batchName = document.forms[0].hdnBatchName[index].value; 
                break;
              }
            }
          }else{
            if( thisForm.radSelect.checked ){
              id = document.forms[0].radSelect.value;
              batchName = document.forms[0].hdnBatchName.value;
            }
          }
        }else{
          alert("There is no item to select");
          return false;
        }
        if( id == null ){
          alert("Please select an item");
          return false;
        }
        if(confirm("Are you sure you want to delete '"+batchName+"' ?")){
          document.forms[0].target="_self";
          document.forms[0].action="deleteBatchAction.do?id="+id+"&batchName="+batchName;
          document.forms[0].submit();
        }
      }
    
      function searchList(){
        document.forms[0].target="_self";
        document.forms[0].action="batchListAction.do?oper=search";
        document.forms[0].submit();
      }
    
    </script>
  </head>
  <body onload="window_onload();">
    <html:form action="/batchListAction" name="batchListForm" type="dims.web.actionforms.batch.BatchListForm" scope="request">
      <html:hidden name="batchListForm" property="hdnPageCount" />
      <html:hidden name="batchListForm" property="hdnPageNumber" />
      
      <jsp:include page="/header_transaction.jsp" />
      <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td height="150x" align="center">
          <fieldset style="width:770px;">
          <legend>Search Batch:</legend>
          <table width="650" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
              <tr>
                <td height="10px" colspan="6" align="left" valign="top"></td>
              </tr>
              <tr>
                <td align="right">Batch Number: </td>
                <td align="left">
                  <html:text name="batchListForm" property="txtSearchBatchNumber" styleClass="bdrClr_Lvl_2" style="width:166px" />
                </td>
                <td align="left">&nbsp;</td>
                <td align="right">Location: </td>
                <td align="left">
                  <html:select name="batchListForm" property="cboSearchLocations" styleClass="bdrClr_Lvl_2" style="width:166px" >
                    <html:options collection="locations" property="locationTblPK" labelProperty="locationID" />
                  </html:select>
                </td>
                <td align="left">&nbsp;</td>
              </tr>
              <tr>
                <td width="125px" align="right">Batch From Date:</td>
                <td width="169px" align="left">
                  <html:hidden name="batchListForm" property="txtSearchBatchFromDate" styleClass="borderClrLvl_2 componentStyle" style="width:166px" />
                  <script>createDateFrom.render();</script>
                </td>
                <td align="left">
                  &nbsp;
                </td>
                <td width="125px" align="right">Batch To Date:</td>
                <td width="168px" align="left">
                  <html:hidden name="batchListForm" property="txtSearchBatchToDate" styleClass="borderClrLvl_2 componentStyle" style="width:166px" />
                  <script>createDateTo.render();</script>
                </td>
                <td width="10" align="left">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td height="35" colspan="6" align="right">
                  <input name="btnSearch" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:75px" value="Search" onclick="searchList();" >
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
                  <input name="btnSubmitToProcess" type="button"  align="right" Class="buttons bdrClr_Lvl_2" style="width:120px; height:18px; margin-right:0px" value="Submit To Process" onclick="submitToProcess();" >
                  <input name="newButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="New"  title="Create New Department" onclick="newBatch();">
                  <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Edit" title="Edit Department" onclick="editBatch();" >
                  <input name="deleteButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Delete" title="Delete Department" onclick="deleteBatch();" >
                  
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
                  <th width="50" align="center">Select</th>
                  <th width="304" align="center">Batch Number </th>
                  <th width="200" align="center">Batch Date </th>
                  <th width="200" align="center">Department </th>
                  <th width="205" align="center">Location </th>
                  
                </tr>

                <logic:notEmpty name="batchs" scope="request">
                  <logic:iterate name="batchs" id="batch" indexId="index">
                    <bean:define id="batchTblPk" name="batch" property="batchTblPk" type="java.lang.String" />
                    <bean:define id="batchNumber" name="batch" property="batchNumber" type="java.lang.String" />
                    <%if (firstTableRow == true){ firstTableRow = false; %>
                      <tr class="bgColor_Lvl_3">
                    <%}else{ firstTableRow = true; %>
                      <tr class="bgColor_Lvl_4">                  
                    <%}%>
                        <td align="center">
                          <html:radio property="radSelect" value="<%=batchTblPk%>" />
                          <html:hidden property="hdnBatchNumber" value="<%=batchNumber%>" />
                        </td>
                          <td><bean:write name="batch" property="batchNumber" /></td>
                          <td><bean:write name="batch" property="batchDate" /></td>
                          <td><bean:write name="batch" property="department" /></td>
                          <td><bean:write name="batch" property="location" /></td>
                          
                      </tr>
                    </logic:iterate>
                  </logic:notEmpty>
                  <logic:empty name="batchs" scope="request">
                    <tr class="bgColor_Lvl_3">
                      <td colspan="5" height="230px" align="center" valign="middle">
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