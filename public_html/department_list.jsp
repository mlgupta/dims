<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="departments" name="departments" scope="request" type="java.util.ArrayList" />
<bean:define id="pageCount" name="departmentListForm" property="hdnPageCount" />
<bean:define id="pageNumber" name="departmentListForm" property="hdnPageNumber" />
<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>DBSentry - Digital Imaging Management System (Department List)</title>
<link href="main.css" rel="stylesheet" type="text/css" />
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
  navBar.onclick="departmentNavigate()";

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
  function departmentNavigate(){
    var thisForm=document.forms[0]
    thisForm.hdnPageNumber.value=navBar.getPageNumber();
    thisForm.target = "_self"
    document.forms[0].action="departmentListAction.do?oper=nav"
    thisForm.submit();
  }

  function newDepartment(){
    document.forms[0].target="_self";
    document.forms[0].action="newDepartmentB4Action.do";
    document.forms[0].submit();
  }
  
  function editDepartment(){
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
    document.forms[0].action="editDepartmentB4Action.do";
    document.forms[0].submit();
  }

  function deleteDepartment(){
    thisForm = document.forms[0];
    var id = null;
    var deptName = null;
    if( typeof thisForm.radSelect != "undefined"  ){
      if( typeof thisForm.radSelect.length != "undefined" ){
        for(index = 0 ; index < thisForm.radSelect.length ;index++){  
          if(thisForm.radSelect[index].checked){   
            id = document.forms[0].radSelect[index].value;
            deptName = document.forms[0].hdnDepartmentName[index].value; 
            break;
          }
        }
      }else{
        if( thisForm.radSelect.checked ){
          id = document.forms[0].radSelect.value;
          deptName = document.forms[0].hdnDepartmentName.value;
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
    if(confirm("Are you sure you want to delete '"+deptName+"' ?")){
      document.forms[0].target="_self";
      document.forms[0].action="deleteDepartmentAction.do?id="+id+"&deptName="+deptName;
      document.forms[0].submit();
    }
  }
  
  function searchList(){
    document.forms[0].target="_self";
    document.forms[0].action="departmentListAction.do?oper=search";
    document.forms[0].submit();
  }

</script>
</head>

<body>
<html:form action="/departmentListAction" name="departmentListForm" type="dims.web.actionforms.department.DepartmentListForm" scope="request">
<html:hidden name="departmentListForm" property="hdnPageCount" />
<html:hidden name="departmentListForm" property="hdnPageNumber" />

<jsp:include page="/header_masters.jsp" />
<table width="1000" border="0" align="center" cellpadding="1" cellspacing="1">
<tr>
  <td align="center">&nbsp;</td>
</tr>
<tr>
    <td height="150x" align="center">
	<fieldset style="width:770px;">
	<legend>Search Department:</legend>
	<table width="400px" border="0" align="center" cellpadding="1" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
      <tr>
        <td height="10px" colspan="2" align="left" valign="top"></td>
        </tr>
      <tr>
        <td width="200" align="right">Department ID:</td>
        <td width="293" align="left">
          <html:text name="departmentListForm" property="txtDepartmentID" styleClass="bdrClr_Lvl_2" style="width:166px" />
        </td>
      </tr>
      <tr>
        <td align="right">Department Name: </td>
        <td align="left">
          <html:text name="departmentListForm" property="txtDepartmentName" styleClass="bdrClr_Lvl_2" style="width:166px" />
        </td>
      </tr>
      <tr>
        <td height="35" colspan="2" align="right">
          <input name="button" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:75px" tabindex="3" value="Search" onclick="searchList();" >
        </td>
      </tr>
    </table>
	</fieldset>	</td>
  </tr>
  <tr>
    <td><table width="770" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="25">
          <input name="newButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="New"  title="Create New Department" onclick="newDepartment();">
          <input name="editButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Edit" title="Edit Department" onclick="editDepartment();" >
          <input name="deleteButton" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Delete" title="Delete Department" onclick="deleteDepartment();" >
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="center">
      <div style="overflow:auto; width:770px; height:250px;" class="bdrClr_Lvl_2">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
          <tr>
            <th width="42" align="center">Select</th>
            <th width="359" align="center">Department ID</th>
            <th width="564" align="center">Department Name</th>
          </tr>
          <logic:notEmpty name="departments" scope="request">
            <logic:iterate name="departments" id="department" indexId="index">
              <bean:define id="departmentTblPk" name="department" property="departmentTblPk" type="java.lang.String" />
              <bean:define id="departmentId" name="department" property="departmentId" type="java.lang.String" />
              <%if (firstTableRow == true){ firstTableRow = false; %>
                <tr class="bgColor_Lvl_3">
              <%}else{ firstTableRow = true; %>
                <tr class="bgColor_Lvl_4">                  
              <%}%>
                <td align="center">
                  <html:radio property="radSelect" value="<%=departmentTblPk%>" />
                  <html:hidden property="hdnDepartmentName" value="<%=departmentId%>" />
                </td>
                <td><bean:write name="department" property="departmentId" /></td>
                <td><bean:write name="department" property="departmentName" /></td>
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
