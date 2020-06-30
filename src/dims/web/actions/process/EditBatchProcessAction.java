package dims.web.actions.process;

import dims.web.actionforms.process.BatchProcessListForm;
import dims.web.actionforms.process.BinderProcessListForm;
import dims.web.beans.process.Operations;
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

public class EditBatchProcessAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditBatchProcessAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    ArrayList binders = new ArrayList();
    BinderProcessListForm binderProcessListForm = null;
    HttpSession session = null;
    UserProfile userProfile = null;
    
    try { 
    
      session = request.getSession(false);
      userProfile = (UserProfile)session.getAttribute("userProfile");
      DataSource ds = getDataSource(request,"DIMS");
      
      binderProcessListForm = (BinderProcessListForm)form; 
      String [] rowValues = binderProcessListForm.getHdnRowValues();
      int totalRows = (rowValues == null)?0:rowValues.length;
      
      for( int index = 0; index < totalRows; index++ ){
        logger.debug(" rowValues["+index+"]: "+rowValues[index]);
      }
      
      String hdnBatchProcessTblFk = binderProcessListForm.getHdnBatchProcessTblFk();
      logger.debug(" hdnBatchProcessTblFk : "+hdnBatchProcessTblFk);
      boolean isBatchProcessed = binderProcessListForm.getBlnIsBatchProcessed();
      logger.debug(" isBatchProcessed : "+isBatchProcessed);
      
      Operations.editBatchBinders(ds,isBatchProcessed,rowValues,Integer.parseInt(userProfile.getUserTblPk()),hdnBatchProcessTblFk);
      msg = new ActionMessage("msg.batch.edited");
      messages.add("items",msg);

    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditBatchProcessAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("Error occurred in EditBatchProcessAction");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall", e.toString());
      messages.add("items",msg);
    }
    
    session.setAttribute("messages",messages);
    BatchProcessListForm batchProcessListForm = new BatchProcessListForm();
    
    /*batchProcessListForm.setChkIsCompleted(new boolean[0]);
    batchProcessListForm.setRadSelect(new String[0]);*/
    
    request.setAttribute("batchProcessListForm",batchProcessListForm);
    request.setAttribute("edit","edit");

    logger.info("Exiting EditBatchProcessAction***");
    return mapping.findForward("success");
  }

}