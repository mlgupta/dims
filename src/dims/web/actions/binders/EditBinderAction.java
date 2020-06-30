package dims.web.actions.binders;

import dims.web.actionforms.binders.BinderNewEditForm;
import dims.web.beans.binders.Operations;
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

public class EditBinderAction extends Action  {
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
    BinderNewEditForm binderNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering EditBinderAction***");
    
    try {
      session = request.getSession(false);
      binderNewEditForm = (BinderNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      Operations.editBinderPrefix(ds,binderNewEditForm);
      msg = new ActionMessage("msg.edit.binderPrefix",binderNewEditForm.getTxtBinderPrefixName());
      messages.add("newDept",msg);
      
    }catch (SQLException sqle) {
      logger.error("An SQLException occured in EditBinderAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.BinderPrefix",binderNewEditForm.getTxtBinderPrefixName());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An Exception occured in EditBinderAction");
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
    
    logger.info("Exiting EditBinderAction***");
    return mapping.findForward("success");
  }

}