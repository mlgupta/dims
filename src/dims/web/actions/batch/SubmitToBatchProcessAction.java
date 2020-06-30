package dims.web.actions.batch;

import dims.web.actionforms.batch.BatchListForm;
import dims.web.actionforms.batch.BatchNewEditForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.batch.Operations;
import dims.web.beans.users.UserProfile;
import dims.web.general.DIMSConstants;

import dims.web.general.ErrorConstants;
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

public class SubmitToBatchProcessAction extends Action  {
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
    HttpSession session = null;
    logger.info("Entering SubmitToBatchProcessAction***");
    
    try {
      session = request.getSession(false);
      DataSource ds = getDataSource(request,"DIMS");
      UserProfile userProfile=(UserProfile)session.getAttribute("userProfile");
      String batchTblPK =request.getParameter("id");
      Operations.submitToBatchProcess(ds,batchTblPK,userProfile.getUserTblPk());
      msg = new ActionMessage("msg.submit.submittoprocess",batchTblPK);
      messages.add("submitToBatchProcess",msg);
    }catch (SQLException sqle) {
      logger.error("An SQLerror occured in SubmitToBatchProcessAction");
      logger.error(sqle.getMessage());
      if( sqle.getMessage().indexOf(ErrorConstants.REFERED.getErrorCode()) > -1 ){
        msg=new ActionMessage("msg.Referred.Batch", request.getParameter("id"));
        messages.add("messageReferred",msg);
      }
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Referred.Batch",request.getParameter("id"));
        messages.add("messageUnique",msg);
      }
    }catch (Exception e) {
      logger.error("An error occured in SubmitToBatchProcessAction");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall",e.toString());
      messages.add("submitToBatchProcess",msg);
    }
    
    if (!messages.isEmpty()) {
      session.setAttribute("messages",messages);
    }
    logger.info("Exiting SubmitToBatchProcessAction***");
    return mapping.findForward("success");
  }
}