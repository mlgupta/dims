package dims.web.actions.users;

import dims.web.actionforms.users.UserListForm;
import dims.web.beans.department.DepartmentListBean;
import dims.web.beans.department.Operations;
import dims.web.beans.users.UserProfile;
import dims.web.general.DIMSConstants;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class UserListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering DepartmentListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    HttpSession session = request.getSession(false);
    UserListForm userListForm = null;
    UserProfile userProfile = null;
    ArrayList users = new ArrayList();
    ArrayList departments = new ArrayList();
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    try {
      DataSource ds = getDataSource(request,"DIMS");
      departments = Operations.listDepartments(ds);
      if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("search") ){
        userListForm = (UserListForm)form;
        logger.info(" Dept ID : " +userListForm.getCboSearchDepartments());
        logger.info("  User ID: " +userListForm.getTxtSearchUserID());
      }else if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("nav") ){
        userListForm = (UserListForm)form;
      }else{
        userListForm = new UserListForm();
        userListForm.setCboSearchDepartments(((DepartmentListBean)departments.get(0)).getDepartmentId());
        userListForm.setTxtSearchUserID("");
      }
      
      
      logger.debug("userListForm.getHdnPageNumber() : "+userListForm.getHdnPageNumber());
      if( userListForm.getHdnPageNumber() == null ){
        userListForm.setHdnPageNumber("1");
      }
      
      users = dims.web.beans.users.Operations.listUsers(ds,userListForm,userProfile.getRecordsPerPage());
      String pageCount = ( users.size() == 1 )?"1":(((Integer)users.get(0)).toString());
      userListForm.setHdnPageCount(pageCount);
      users.remove(0);
      msg = new ActionMessage("msg.items", String.valueOf(users.size()));
      messages.add("items",msg);
    }catch (SQLException sqle) {
      logger.error("An error occured in UserListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in UserListAction");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall", e.toString());
      messages.add("items",msg);
      e.printStackTrace();
    }
    logger.info("Mapping : "+mapping.getAttribute());
    
    if( ((ActionMessages)session.getAttribute("messages")) != null ){
      saveMessages(request,((ActionMessages)session.getAttribute("messages")));
      session.removeAttribute("messages");
    }else{
      saveMessages(request,messages);
    }
    request.setAttribute(mapping.getAttribute(),userListForm);
    request.setAttribute("departments",departments);
    request.setAttribute("users",users);
    
    logger.info("Exiting UserListAction***");
    return mapping.findForward("success");
  }

}