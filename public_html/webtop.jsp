<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="dims.web.beans.users.UserProfile" %> 
<%
  UserProfile userProfile = null;
  boolean isAdmin = false;
  if( session != null ){
    session.removeAttribute("messages");
    userProfile = (UserProfile)session.getAttribute("userProfile");
    isAdmin = ( userProfile != null && userProfile.getUserStatus().equals("1") )?true:false;
  }
  
   
%>

<script name="javascript">
    window.name="dims"
</script>
<html>
  <head>
    <title>DBSentry -Digital Imaging Management System (WebTop)</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
<!--

-->
</style>
    <script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
  </head>
  <body onLoad="MM_preloadImages('images/webtop_masters_over.gif','images/webtop_transaction_over.gif','images/webtop_report_over.gif')">
    <table height="100%" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td valign="middle">
          <table width="700" border="0" align="center" cellpadding="0" cellspacing="0" class="bdrClr_Lvl_2">
            <tr>
              <td>
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="10px" colspan="3" background="images/bar_tile10_up.gif"/>
                  </tr>
                  <tr>
                    <td height="0px" colspan="3"/>
                  </tr>
                  <tr>
                    <td colspan="3" align="right">
                      <table border="0" align="right" cellpadding="0" cellspacing="0">
                        <tr>
                          <% if( isAdmin ) { %>
                            <td width="70" height="13" align="center">
                              <a class="textLinkBold" href="preferenceB4Action.do">Preference</a>
                            </td>
                          <%}else{%>
                            <td width="70" height="13" align="center">
                            </td>
                          <%}%>
                          <td width="53" align="center" class="bdrLftClr_Lvl_1">
                            <a class="textLinkBold" href="logoutAction.do">LogOut</a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td width="158" height="53px" align="center">
                      <a href="http://www.dbsentry.com" target="_blank">
                        <img src="images/logo_dbsentry.gif" width="140" height="40" border="0" style="border:0px solid #949494;"/>
                      </a>
                    </td>
                    <td width="397">
                      <table border="0" cellspacing="1" cellpadding="1">
                        <tr>
                          <td width="385" height="35px" class="textHeading20bold bdrLftClr_Lvl_1">&nbsp;&nbsp;Digital Imaging Management System</td>
                        </tr>
                      </table>
                    </td>
                    <td width="143" align="right" valign="top">&nbsp;</td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td height="17px" background="images/bar_tile19.gif" class="bgColor_Lvl_2 bdrTopClr_Lvl_2 bdrBtmClr_Lvl_2">&nbsp;</td>
            </tr>
            <tr>
              <td height="255">
                <table width="670" height="210" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="100px" align="center">
                      <% if( isAdmin ) { %>
                        
                        <a href="header_masters.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('masters','','images/webtop_masters_over.gif',1)">
                          <img src="images/webtop_masters.gif" alt="Masters" title="Masters" name="masters" width="75" height="60" border="0" style="margin-right:20px"/>
                        </a>
                        
                        <a href="header_transaction.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('transaction','','images/webtop_transaction_over.gif',1)">
                          <img src="images/webtop_transaction.gif" alt="Transaction" title="Transaction" name="transaction" width="75" height="60" border="0" style="margin-right:20px"/>
                        </a>

                        <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('report','','images/webtop_report_over.gif',1)">
                          <img src="images/webtop_report.gif" name="report" alt="Report" title="Report" width="75" height="60" border="0"/>
                        </a>
                        
                      <%} else { %>
                        <a href="header_transaction.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('transaction','','images/webtop_transaction_over.gif',1)">
                          <img src="images/webtop_transaction.gif" alt="Transaction" title="Transaction" name="transaction" width="75" height="60" border="0" style="margin-right:20px"/>
                        </a>
                      <% } %>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td height="10px" background="images/bar_tile10_down.gif"/>
            </tr>
          </table>
          <table width="700" border="0" align="center" cellpadding="1" cellspacing="1">
            <tr>
              <td align="right" class="textCopyRight">Copyright &copy; 2006, DBSentry Corp. All rights reserved. &nbsp;&nbsp;&nbsp;(Best viewed in 1024 x 768 screen resolution )</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
