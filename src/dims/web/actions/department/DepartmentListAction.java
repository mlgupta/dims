package dims.web.actions.department;

import dims.web.actionforms.department.DepartmentListForm;
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


public class DepartmentListAction extends Action  {
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
    DepartmentListForm departmentListForm = null;
    UserProfile userProfile = null;
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("search") ){
      departmentListForm = (DepartmentListForm)form;
      logger.info(" Dept ID : " +departmentListForm.getTxtDepartmentID());
      logger.info("Dept Name: " +departmentListForm.getTxtDepartmentName());
    }else if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("nav") ){
      departmentListForm = (DepartmentListForm)form;
    }else{
      departmentListForm = new DepartmentListForm();
      departmentListForm.setTxtDepartmentID("");
      departmentListForm.setTxtDepartmentName("");
    }
    
    ArrayList departments = new ArrayList();
    logger.debug("departmentListForm.getHdnPageNumber() : "+departmentListForm.getHdnPageNumber());
    if( departmentListForm.getHdnPageNumber() == null ){
      departmentListForm.setHdnPageNumber("1");
    }
    
    try {
      DataSource ds = getDataSource(request,"DIMS");
      departments = Operations.listDepartments(ds,departmentListForm,userProfile.getRecordsPerPage());
      String pageCount = ( departments.size() == 1 )?"1":(((Integer)departments.get(0)).toString());
      departmentListForm.setHdnPageCount(pageCount);
      logger.debug("departmentListForm.getHdnPageCount() : "+departmentListForm.getHdnPageCount());
      departments.remove(0);
      msg = new ActionMessage("msg.items", String.valueOf(departments.size()));
      messages.add("items",msg);
    }catch (SQLException sqle) {
      logger.error("An SQLError occured in DepartmentListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in DepartmentListAction");
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
    request.setAttribute(mapping.getAttribute(),departmentListForm);
    request.setAttribute("departments",departments);
    
    logger.info("Exiting DepartmentListAction***");
    return mapping.findForward("success");
  }

}