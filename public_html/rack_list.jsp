<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="locations" name="locations" type="java.util.ArrayList" />
<bean:define id="rooms" name="rooms" type="java.util.ArrayList" />
<bean:define id="racks" name="racks" type="java.util.ArrayList" />
<bean:define id="pageCount" name="rackListForm" property="hdnPageCount" />
<bean:define id="pageNumber" name="rackListForm" property="hdnPageNumber" />

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>DBSentry - Digital Imaging Management System (Rack List)</title>
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
      
      function showRoomsListData(httpRequest){
        var cboSearchRoomObj = document.getElementById('cboSearchRoomNumber');
        cboSearchRoomObj.options.length = 0;
        
        if (httpRequest.readyState == 4) {
          if (httpRequest.status == 200) {
            // response can be set now
            var responseArr = httpRequest.responseText.split("|");
            for( var index = 0; index < responseArr.length; index++ ){
              var optionObj = document.createElement("OPTION");
              var textValueArr = responseArr[index].split(",");
              optionObj.text = textValueArr[0];
              optionObj.value = textValueArr[1];
              cboSearchRoomObj.options[index]=optionObj;
            }
          } else {
              // there was a problem with the request,
              // for example the response may be a 404 (Not Found)
              // or 500 (Internal Server Error) response codes
          }
        } else {
            // still not ready
              var optionObj = document.createElement("OPTION");
              optionObj.text = "Loading";
              optionObj.value = "-1";
              cboSearchRoomObj.options[0]=optionObj;
        }
      }
      
      function getRoomsListData(){
        var thisForm = document.forms[0];
        var locationTblPK = thisForm.cboSearchLocations.value;
        var requestParam = 'locationTblPK='+locationTblPK;
        var cboSearchRoomObj = document.getElementById('cboSearchRoomNumber');
        if( locationTblPK == "-1" ){
          cboSearchRoomObj.options.length=0;
          var optionObj = document.createElement("OPTION");
          optionObj.text = "Select";
          optionObj.value = "-1";
          cboSearchRoomObj.options[0]=optionObj;
        }else{
          ajaxRequest('POST','roomList4LocationAction.do',true,requestParam,showRoomsListData,true);
        }

      }
      
    </script>
    <script>
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNumber%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="rackNavigate()";
    
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
      function rackNavigate(){
        var thisForm=document.forms[0]
        thisForm.hdnPageNumber.value=navBar.getPageNumber();
        thisForm.target = "_self"
        document.forms[0].action="rackListAction.do?oper=nav"
        thisForm.submit();
      }
    
      function newRack(){
        document.forms[0].target="_self";
        document.forms[0].action="newRackB4Action.do";
        document.forms[0].submit();
      }
      
      function editRack(){
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
        document.forms[0].action="editRackB4Action.do";
        document.forms[0].submit();
      }
    
      function deleteRack(){
        thisForm = document.forms[0];
        var id = null;
        var rackNumber = null;
        if( typeof thisForm.radSelect != "undefined"  ){
          if( typeof thisForm.radSelect.length != "undefined" ){
            for(index = 0 ; index < thisForm.radSelect.length ;index++){  
              if(thisForm.radSelect[index].checked){   
                id = document.forms[0].radSelect[index].value;
                rackNumber = document.forms[0].hdnRackNumber[index].value; 
                break;
              }
            }
          }else{
            if( thisForm.radSelect.checked ){
              id = document.forms[0].radSelect.value;
              rackNumber = document.forms[0].hdnRackNumber.value;
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
    
        if(confirm("Are you sure you want to delete '"+rackNumber+"' ?")){
          document.forms[0].target="_self";
          document.forms[0].action="deleteRackAction.do?id="+id+"&rackNumber="+rackNumber;
          document.forms[0].submit();
        }
      }
      
      function searchList(){
        document.forms[0].target="_self";
        document.forms[0].action="rackListAction.do?oper=search";
        document.forms[0].submit();
      }
    
    </script>
  </head>
  <body>
    <html:form action="/rackListAction" name="rackListForm" type="dims.web.actionforms.rack.RackListForm" > 
    <html:hidden name="rackListForm" property="hdnPageCount" />
    <html:hidden name="rackListForm" property="hdnPageNumber" />
    
      <jsp:include page="/header_masters.jsp" />
      <table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
          <td align="center">&nbsp;</td>
        </tr>
        <tr>
          <td height="150x" align="center">
            <fieldset style="width:770px;">
              <legend>Search Rack:</legend>
              <table width="600px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
                <tr>
                  <td height="10px" colspan="4" align="left" valign="top"/>
                </tr>
                <tr>
                  <td align="right">Location ID:</td>
                  <td align="left">
                    <html:select name="rackListForm" property="cboSearchLocations" styleClass="bdrClr_Lvl_2" style="width:166px" onchange="return getRoomsListData();" >
                      <html:options collection="locations" property="locationTblPK" labelProperty="locationID" />
                    </html:select>
                  </td>
                  <td align="right">Rack Number:</td>
                  <td align="left">
                    <html:text name="rackListForm" property="txtSearchRack" styleClass="bdrClr_Lvl_2" style="width:166px" />
                  </td>
                </tr>
                <tr>
                  <td width="99" align="right">Room Number:</td>
                  <td width="181" align="left">
                    <html:select name="rackListForm" property="cboSearchRoomNumber" styleId="cboSearchRoomNumber" styleClass="bdrClr_Lvl_2" style="width:166px">
                      <html:options collection="rooms" property="roomTblPk" labelProperty="roomNumber" />
                    </html:select>
                  </td>
                  <td colspan="2"></td>
                </tr>
                <tr>
                  <td height="35" colspan="4" align="right">
                    <input name="searchButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:30px" tabindex="3" value="Search" onclick="searchList();" />
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
                  <input name="newButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="New" title="Create New Rack" onclick="newRack();" />
                  <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Edit" title="Edit Rack" onclick="editRack();" />
                  <input name="deleteButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Delete" title="Delete Rack" onclick="deleteRack();" />
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
                  <th width="303" align="center">Rack Number</th>
                  <th width="303" align="center">Room Number</th>
                  <th width="303" align="center">Location ID</th>
                </tr>
                <logic:notEmpty name="racks" scope="request">
                  <logic:iterate name="racks" id="rack" indexId="index">
                    <bean:define id="rackTblPk" name="rack" property="rackTblPk" type="java.lang.String" />
                    <bean:define id="rackNumber" name="rack" property="rackNumber" type="java.lang.String" />
                    <%if (firstTableRow == true){ firstTableRow = false; %>
                      <tr class="bgColor_Lvl_3">
                    <%}else{ firstTableRow = true; %>
                      <tr class="bgColor_Lvl_4">                  
                    <%}%>
                      <td align="center">
                        <html:radio name="rackListForm" property="radSelect" value="<%=rackTblPk%>" />
                        <html:hidden property="hdnRackNumber" value="<%=rackNumber%>" />
                      </td>
                      <td><bean:write name="rack" property="rackNumber" /></td>
                      <td><bean:write name="rack" property="roomNumber" /></td>
                      <td><bean:write name="rack" property="locationID" /></td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="racks" scope="request">
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
