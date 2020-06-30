package dims.web.actions.process;

import dims.web.actionforms.process.RecordListForm;
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

public class RecordListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering RecordListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    ArrayList records = new ArrayList();
    RecordListForm recordListForm = null;
    HttpSession session = null;
    UserProfile userProfile = null;
    String binder_process_tbl_pk = null;
    String batchNumber = null;
    String binderNumber = null;
    
    try { 
    
      session = request.getSession(false);
      userProfile = (UserProfile)session.getAttribute("userProfile");
      DataSource ds = getDataSource(request,"DIMS");
      
      if( request.getAttribute("edit") == null ){
        binder_process_tbl_pk = request.getParameter("binderProcessTblPk");
        batchNumber = request.getParameter("batchNumber");
        binderNumber = request.getParameter("binderNumber");
        session.setAttribute("binder_process_tbl_pk",binder_process_tbl_pk);
        session.setAttribute("batchNumber",batchNumber);
        session.setAttribute("binderNumber",binderNumber);
      }else{
        binder_process_tbl_pk = (String)session.getAttribute("binder_process_tbl_pk"); 
        batchNumber = (String)session.getAttribute("batchNumber");
        binderNumber = (String)session.getAttribute("binderNumber");
      }
      
      
      records = Operations.listRecords(ds,binder_process_tbl_pk);
      
      msg = new ActionMessage("msg.items", String.valueOf(records.size()));
      messages.add("items",msg);
      
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in RecordListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("Error occurred in RecordListAction");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall", e.toString());
      messages.add("items",msg);
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute("records",records);
    request.setAttribute("batchNumber",batchNumber);
    request.setAttribute("binderNumber",binderNumber);
    
    if( ((ActionMessages)session.getAttribute("messages")) != null ){
      saveMessages(request,((ActionMessages)session.getAttribute("messages")));
      session.removeAttribute("messages");
    }else{
      saveMessages(request,messages);
    }

    logger.info("Exiting RecordListAction***");
    return mapping.findForward("success");
  }

}