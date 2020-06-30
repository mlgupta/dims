<%@ page import="dims.web.beans.users.UserProfile" %> 

<%
  boolean isAdmin = false;
  if(session.getAttribute("userProfile")==null){ %>
  <jsp:forward page="login.jsp" >
       <jsp:param name='sessionExpired' value='true' />
  </jsp:forward>
<%}else{ %>
<%
  UserProfile userProfile = null;
  if( session != null ){
    session.removeAttribute("messages");
    userProfile = (UserProfile)session.getAttribute("userProfile");
    isAdmin = ( userProfile != null && userProfile.getUserStatus().equals("1") )?true:false;
  }
}%>

<link href="main.css" rel="stylesheet" type="text/css" />
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="158" height="60" align="center">
      <a href="http://www.dbsentry.com"> 
        <img src="images/logo_dbsentry.gif" alt="www.dbsentry.com" title="www.dbsentry.com" width="140" height="40" border="0" style="margin-top:5px" />
      </a>
    </td>
    <td width="550" valign="middle"><table border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="35px" class="textHeading20bold bdrLftClr_Lvl_1">&nbsp;&nbsp;Digital Imaging
          Management System </td>
      </tr>
    </table>
    <td width="292" valign="top">
      <table id="topBorder" border="0" align="right" cellpadding="1" cellspacing="2">
        <tr>
          <td width="80" height="13" align="center" class="bgColor_Lvl_1 bdrClr_Lvl_1"><a class="topMenuLink" href="webtop.jsp">Home</a></td>
          <% if( isAdmin ) { %>
            <td width="80" height="13" align="center" class="bgColor_Lvl_1 bdrClr_Lvl_1"><a class="topMenuLink" href="preferenceB4Action.do">Preference</a></td>
            <td width="80" align="center" class="bgColor_Lvl_1 bdrClr_Lvl_1"><a class="topMenuLink" href="logoutAction.do">LogOut</a></td>
          <%}else{%>
            <td width="80" height="13" align="center" class="bgColor_Lvl_1 bdrClr_Lvl_1"><a class="topMenuLink" href="logoutAction.do">LogOut</a></td>
          <%}%>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="19" colspan="3" align="right" background="images/bar_tile19.gif" class="bdrTopClr_Lvl_2 bdrBtmClr_Lvl_2">
     <a class="textLinkBold" href="batchListAction.do">Batch</a> | <a class="textLinkBold" href="batchProcessListAction.do">Process</a> &nbsp;&nbsp;&nbsp;
    </td>
 </tr>
</table>
