package dims.web.actions.rack;

import dims.web.actionforms.rack.RackNewEditForm;
import dims.web.beans.rack.Operations;
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

public class EditRackAction extends Action  {
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
    RackNewEditForm rackNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering EditRackAction***");
    
    try {
      session = request.getSession(false);
      rackNewEditForm = (RackNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      Operations.editRack(ds,rackNewEditForm);
      msg = new ActionMessage("msg.edit.rackNumber",rackNewEditForm.getTxtRackNumber(),rackNewEditForm.getTxtNumberOfShelves());
      messages.add("editRack",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in EditRackAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.RackNumber",rackNewEditForm.getTxtRackNumber());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An error occured in EditRackAction");
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
    
    logger.info("Exiting EditRackAction***");
    return mapping.findForward("success");
  }

}