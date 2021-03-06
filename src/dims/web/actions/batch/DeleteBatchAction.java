package dims.web.actions.batch;

import dims.web.actionforms.batch.BatchListForm;
import dims.web.beans.batch.Operations;
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

public class DeleteBatchAction extends Action  {
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
    logger.info("Entering DeleteBatchAction***");
    
    try {
      session = request.getSession(false);
      DataSource ds = getDataSource(request,"DIMS");
      Operations.deleteBatch(ds,request.getParameter("id"));
      msg = new ActionMessage("msg.delete.batchNumber",request.getParameter("batchNumber"));
      messages.add("deleteBatch",msg);
    }catch (SQLException sqle) {
      logger.error("An SQLerror occured in DeleteBatchAction");
      logger.error(sqle.getMessage());
      if( sqle.getMessage().indexOf(ErrorConstants.REFERED.getErrorCode()) > -1 ){
        msg=new ActionMessage("msg.Referred.BatchNumber",request.getParameter("BatchNumber"));
        messages.add("messageReferred",msg);
      }
    }catch (Exception e) {
      logger.error("An error occured in DeleteBatchAction");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall",e.toString());
      messages.add("deleteBatch",msg);
    }
    
    if (!messages.isEmpty()) {
      session.setAttribute("messages",messages);
    }
    logger.info("Exiting DeleteBatchAction***");
    return mapping.findForward("success");
  }

}