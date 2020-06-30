package dims.web.actions.process;

import dims.web.actionforms.process.BatchProcessListForm;
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


public class BatchProcessListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    logger.info("Entering BatchProcessListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    ArrayList batches = new ArrayList();
    ArrayList departments = new ArrayList();
    HttpSession session = request.getSession(false);
    BatchProcessListForm batchProcessListForm = null;
    UserProfile userProfile = null;
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("search") ){
      batchProcessListForm = (BatchProcessListForm)form;
      logger.info("     Dept ID : " +batchProcessListForm.getCboDepartmentId());
      logger.info(" Batch Number: " +batchProcessListForm.getTxtBatchNumber());
      logger.info("    From Date: " +batchProcessListForm.getHdnBatchFromDate());
      logger.info("      To Date: " +batchProcessListForm.getHdnBatchToDate());
      
    }else if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("nav") ){
      batchProcessListForm = (BatchProcessListForm)form;
    }else{
      batchProcessListForm = new BatchProcessListForm();
      batchProcessListForm.setCboDepartmentId(userProfile.getDeptID());
      batchProcessListForm.setTxtBatchNumber("");
      batchProcessListForm.setHdnBatchFromDate("");
      batchProcessListForm.setHdnBatchToDate("");
    }
    
    logger.debug("batchProcessListForm.getHdnPageNumber() : "+batchProcessListForm.getHdnPageNumber());
    if( batchProcessListForm.getHdnPageNumber() == null ){
      batchProcessListForm.setHdnPageNumber("1");
    }
    
    try {
      DataSource ds = getDataSource(request,"DIMS");
      batches = Operations.listBatchProcesses(ds,batchProcessListForm,userProfile.getRecordsPerPage());
      departments = dims.web.beans.department.Operations.listDepartments(ds,userProfile);
      String pageCount = ( batches.size() == 1 )?"1":(((Integer)batches.get(0)).toString());
      batchProcessListForm.setHdnPageCount(pageCount);
      logger.debug("batchProcessListForm.getHdnPageCount() : "+batchProcessListForm.getHdnPageCount());
      batches.remove(0);
      msg = new ActionMessage("msg.items", String.valueOf(batches.size()));
      messages.add("items",msg);
    }catch (SQLException sqle) {
      logger.error("An SQLError occured in BatchProcessListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in BatchProcessListAction");
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
    request.setAttribute(mapping.getAttribute(),batchProcessListForm);
    request.setAttribute("batches",batches);
    request.setAttribute("departments",departments);
    
    logger.info("Exiting BatchProcessListAction***");
    return mapping.findForward("success");
  }

}