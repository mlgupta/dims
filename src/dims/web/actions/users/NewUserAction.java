package dims.web.actions.users;

import dims.web.actionforms.users.UserNewEditForm;
import dims.web.beans.users.Operations;
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

public class NewUserAction extends Action  {
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
    UserNewEditForm userNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering NewUserAction***");
    
    try {
      session = request.getSession(false);
      userNewEditForm = (UserNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      Operations.addUser(ds,userNewEditForm);
      msg = new ActionMessage("msg.new.user",userNewEditForm.getTxtUserID());
      messages.add("newDept",msg);
    }catch (SQLException sqle) {
      logger.error("An error occured in NewUserAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.UserId",userNewEditForm.getTxtUserID());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An error occured in NewUserAction");
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
    logger.info("Exiting NewUserAction***");
    return mapping.findForward("success");
  }

}