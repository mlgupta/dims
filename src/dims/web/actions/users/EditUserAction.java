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

public class EditUserAction extends Action  {
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
    logger.info("Entering EditUserAction***");
    
    try {
      session = request.getSession(false);
      userNewEditForm = (UserNewEditForm)form;
      if( userNewEditForm.getTxtUserPassword().equalsIgnoreCase("PasswordNotChanged12") ){
        userNewEditForm.setTxtUserPassword((String)session.getAttribute("userPwd"));
        userNewEditForm.setTxtConfirmPassword((String)session.getAttribute("userPwd"));
        session.removeAttribute("userPwd");
      }
      DataSource ds = getDataSource(request,"DIMS");
      Operations.editUser(ds,userNewEditForm);
      msg = new ActionMessage("msg.edit.user",userNewEditForm.getTxtUserID());
      messages.add("editUser",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in EditUserAction");
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
      logger.error("An error occured in EditUserAction");
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
    
    logger.info("Exiting EditUserAction***");
    return mapping.findForward("success");
  }

}