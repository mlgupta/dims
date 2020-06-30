package dims.web.actions.department;

import dims.web.actionforms.department.DepartmentNewEditForm;
import dims.web.beans.department.Operations;
import dims.web.general.DIMSConstants;

import dims.web.general.ErrorConstants;
import java.io.IOException;

import java.sql.SQLException;
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

public class NewDepartmentAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    DepartmentNewEditForm departmentNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering NewDepartmentAction***");
    
    try {
      session = request.getSession(false);
      departmentNewEditForm = (DepartmentNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      Operations.addDepartment(ds,departmentNewEditForm);
      msg = new ActionMessage("msg.new.dept",departmentNewEditForm.getTxtDepartmentId());
      messages.add("newDept",msg);
    }catch (SQLException sqle) {
      logger.error("An error occured in NewDepartmentAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.DepartmentId",departmentNewEditForm.getTxtDepartmentId());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An error occured in NewDepartmentAction");
      logger.error(e.toString());
      msg=new ActionMessage("errors.catchall",e.toString());
      messages.add("messagecatchall",msg);
      if (!messages.isEmpty()) {
        session.setAttribute("messages",messages);
      }
      return mapping.getInputForward();
    }
    
    if (!messages.isEmpty()) {
      session.setAttribute("messages",messages);
    }
    logger.info("Exiting NewDepartmentAction***");
    return mapping.findForward("success");
  }


}