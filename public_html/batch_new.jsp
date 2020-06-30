<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="prefixs" name="prefixs" type="java.util.ArrayList"/>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>DBSentry - Digital Imaging Management System (Create New Batch)</title>
    <link href="main.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="general.js"></script> 
    <script> var noOfRows=1; </script>
    <script>
      function createRow(){
        if (validateForm()) {  
          var r = MM_findObj('mytab').getElementsByTagName('tr');
          var len = r.length;
  
          var clone=r[1].cloneNode(true);
          var cloneOfLastRow=r[len-1].cloneNode(true);
          for(var i=0;i<clone.getElementsByTagName('td').length;i++){
            if(i==0){
              //clone.getElementsByTagName('td')[i].innerHTML=len-1;//setting SI.
            }
            var kids=clone.getElementsByTagName('td')[i].childNodes;
            
            for(var j=0;j<kids.length;j++){
              if( typeof(kids[j].id)=="string"){
                var idArr=kids[j].id.split("_");
                kids[j].id=idArr[0]+"_"+(len-1);
                if(typeof(kids[j].name)=="string"){
                  kids[j].name=idArr[0]+"_"+(len-1);
                }
                //alert(kids[j].id);
                if(kids[j].id.indexOf("chkBinder")>=0){
                  kids[j].disabled=false;
                  //kids[j].options.selectedIndex=document.getElementById("chkBinder_0").options.selectedIndex;
                }
                
                if(kids[j].id.indexOf("cboPrefix")>=0){
                  kids[j].disabled=false;
                  kids[j].options.selectedIndex=document.getElementById("cboPrefix_0").options.selectedIndex;
                }
                
                if(kids[j].id.indexOf("txtBinderStartNumber")>=0){
                  kids[j].value="";
                  kids[j].disabled=false;
                  //alert(clone.getElementsByTagName('td')[i].innerHTML);
                }
                
                if(kids[j].id.indexOf("txtBinderEndNumber")>=0){
                  kids[j].value="";
                  kids[j].disabled=false;
                  //alert(clone.getElementsByTagName('td')[i].innerHTML);
                }
                
                if(kids[j].id.indexOf("cboYear")>=0){
                  //alert(kids[j].id);
                  kids[j].disabled=false;
                  //alert(clone.getElementsByTagName('td')[i].innerHTML);
                }
                
              }
            }
          }
          noOfRows=noOfRows+1;
          //index=noOfRows;
          //r[1].parentNode.appendChild(clone);
          r[1].parentNode.appendChild(clone);
          window.scrollTo(0,findPosY(clone));
        }
      }
      
      function findPosX(obj){
        var curleft = 0;
        if (obj.offsetParent){
          while (obj.offsetParent) {
            curleft += obj.offsetLeft
            obj = obj.offsetParent;
          }
        }else if (obj.x)
          curleft += obj.x;
          return curleft;
      }
  
      function findPosY(obj){
        var curtop = 0;
        var printstring = '';
        if (obj.offsetParent){
          while (obj.offsetParent){
            printstring += ' element ' + obj.tagName + ' has ' + obj.offsetTop;
            curtop += obj.offsetTop
            obj = obj.offsetParent;
          }
        }else if (obj.y)
          curtop += obj.y;
        //window.status = printstring;
        return curtop;
      }
    
    
      function validateForm(){
        var thisForm=document.forms[0];
         if((thisForm.cboLocations.value)==-1){
            alert("Select Location.");
            thisForm.cboLocations.focus();
            return false; 
         } 
        for(var i=0;  i<noOfRows; i++){
          
          var txtBinderStartNumberObj=eval(document.getElementById("txtBinderStartNumber_"+i));
          var txtBinderEndNumberObj=eval(document.getElementById("txtBinderEndNumber_"+i));
          
          if(trim(txtBinderStartNumberObj.value).length<=0){
            alert("Start Number Is Required.");
            txtBinderStartNumberObj.focus();
            return false; 
          }else if(trim(txtBinderEndNumberObj.value).length<=0){
            alert("End Number Is Required.");
            txtBinderEndNumberObj.focus();
            return false; 
          }
        }
        return true;
      }

      function validateAndSubmit(){
        var thisForm = document.forms[0];
        if(validateForm()){
          //alert("inside Save");
          thisForm.submit();
        }
        return false;
      }


      function cancelNew(){
        var thisForm = document.forms[0];
        thisForm.target="_self";
        thisForm.action="batchListAction.do";
        thisForm.submit();
      }
    </script>
  </head>
  <body>
    <html:form action="/newBatchAction" name="batchNewEditForm" type="dims.web.actionforms.batch.BatchNewEditForm"  >
      <jsp:include page="/header_transaction.jsp" />
      <table width="1000px;" border="0" align="center" cellpadding="1" cellspacing="1">
        <tr>
          <td align="center">&nbsp;</td>
        </tr>
        <tr>
          <td height="110px;" align="center">
          <fieldset style="width:620px;">
          <legend>Add New Batch:</legend>
            <table width="600px;" border="0" align="center" cellpadding="2" cellspacing="1" class=" " style="margin-top:3px; margin-bottom:3px">
                <tr>
                  <td height="10" colspan="4" align="left" valign="top"></td>
                </tr>
                <tr>
                  <td align="right">Batch Number: </td>
                  <td align="left">
                    <html:text name="batchNewEditForm" property="txtBatchNumber" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="1" />
                  </td>
                  <td align="right">Batch Date:</td>
                  <td>
                    <html:text name="batchNewEditForm" property="dtBatchDate" styleClass="bdrClr_Lvl_2" style="width:166px" tabindex="2"  readonly="true"/>
                  </td>
                </tr>
                <tr>
                  <td height="23px;" align="right">Location:</td>
                  <td align="left">
                    <html:select name="batchNewEditForm" property="cboLocations" styleClass="bdrClr_Lvl_2" style="width:166px" >
                      <html:options collection="locations" property="locationTblPK" labelProperty="locationID" />
                    </html:select>
                  </td>
                  <td align="left">&nbsp;</td>
                  <td align="left">&nbsp;</td>
                </tr>
                <tr>
                  <td width="122px;" align="right">&nbsp;</td>
                  <td width="173px;" align="left">&nbsp;</td>
                  <td width="94px;" align="left">&nbsp;</td>
                  <td width="198px;" align="left">&nbsp;</td>
                </tr>
              </table>
            </fieldset>	
          </td>
        </tr>
        <tr>
          <td align="center">
            <table width="620px" border="0">
              <tr>
                <td align="right">
                  <img src="images/btn_insert.gif" alt="Insert Item" width="47" height="19" border="0" style="margin-right:15px" title="Insert Item" onclick=createRow(); />
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="center">
            <div style="overflow:auto; width:620px; height:400px;" class="bdrClr_Lvl_2">
              <table id="mytab" width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                <tr>
                  <th width="45px;" height="18px;" align="center">Delete </th>
                  <th width="134px;" colspan="5" align="center">Binder Number </th>
                  
                </tr>
                <%int index=0;%>
                
                <tr class="bgColor_Lvl_3">
                  <td id="Sl" align="center">
                    <input type="CHECKBOX" id=<%="chkBinder"+"_"+new String().valueOf(index)%> name=<%="chkBinder"+"_"+new String().valueOf(index)%>> </input> </td>
                  <td>
                     <select id='<%="cboPrefix"+"_"+new String().valueOf(index)%>' name=<%="cboPrefix"+"_"+new String().valueOf(index)%> class="bdrClr_Lvl_2" style="width:130px" >
                      <logic:notEmpty name="prefixs">
                        <logic:iterate id="binderListBean" name="prefixs">
                          <bean:define id="binderTblPk" name="binderListBean" property="binderTblPk" type="java.lang.String" />
                          <bean:define id="binderPrefix" name="binderListBean" property="binderPrefix" type="java.lang.String" />
                          <option value="<%=binderTblPk%>" ><%=binderPrefix%></option> 
                        </logic:iterate>
                      </logic:notEmpty>
                    </select>
                  </td>
                  <td>
                    <input style="width:130px" type="text" onkeypress="return integerOnly(event);" id=<%="txtBinderStartNumber"+"_"+new String().valueOf(index)%> name=<%="txtBinderStartNumber"+"_"+new String().valueOf(index)%> class="bdrClr_Lvl_2" />
                  </td>
                  <td align="center">to</td>
                  <td>
                    <input style="width:130px" type="text" onkeypress="return integerOnly(event);" id=<%="txtBinderEndNumber"+"_"+new String().valueOf(index)%> name=<%="txtBinderEndNumber"+"_"+new String().valueOf(index)%> class="bdrClr_Lvl_2"  />
                  </td>
                  <td>
                    <%
                      java.util.Calendar cal=java.util.Calendar.getInstance();
                      int currYear=cal.get(java.util.Calendar.YEAR);
                    %>
                      <select id=<%="cboYear"+"_"+new String().valueOf(index)%> name=<%="cboYear"+"_"+new String().valueOf(index)%>  class="bdrClr_Lvl_2" style="width:130px">
                    <%
                      int yearLength=100;
                      for(int i=currYear+1;i>(currYear-yearLength);i--){
                      String yearString=String.valueOf(i);
                    %>
                        <option value='<%=(String)yearString%>' <%
                        %>><%=(String)yearString%></option>
                    <%
                      }
                    %>
                    </select>
                  </td>
                </tr>
                <% index++;%>
              </table>
            </div>	
          </td>
        </tr>
        <tr>
          <td align="center">
            <table width="620px;" height="35px;" border="0" cellpadding="1" cellspacing="1">
              <tr>
                <td align="right">
                  <input name="btnSave" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" value="Save" onclick="validateAndSubmit();">
                  <input name="button2" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:8px" value="Cancel" onclick="cancelNew();" >
                  <input name="button3" type="button" Class="buttons bdrClr_Lvl_2" style="width:50px; height:18px; margin-right:0px" value="Reset" >
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html>