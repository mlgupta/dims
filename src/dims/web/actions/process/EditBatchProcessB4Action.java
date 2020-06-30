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

public class EditBatchProcessB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditBatchProcessB4Action***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    ArrayList binders = new ArrayList();
    BinderProcessListForm binderProcessListForm = null;
    HttpSession session = null;
    UserProfile userProfile = null;
    BatchProcessListForm batchProcessListForm = null;
    String batch_process_tbl_pk = null;
    
    try { 
    
      session = request.getSession(false);
      userProfile = (UserProfile)session.getAttribute("userProfile");
      DataSource ds = getDataSource(request,"DIMS");
      
      if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("nav") ){

        binderProcessListForm = (BinderProcessListForm)form;
        batch_process_tbl_pk = binderProcessListForm.getHdnBatchProcessTblFk();
        
        if( (binderProcessListForm.getTxtBatchEndDate() != null) &&  
            (binderProcessListForm.getTxtBatchEndDate().trim().length() != 0 )){
          request.setAttribute("isCompleted",new Boolean(true));
        }else{
          request.setAttribute("isCompleted",new Boolean(false));
        }

      }else if( request.getAttribute("edit")!= null &&
                ((String)request.getAttribute("edit")).equals("edit")  ){

        binderProcessListForm = (BinderProcessListForm)request.getAttribute("binderProcessListForm");
        batch_process_tbl_pk = binderProcessListForm.getHdnBatchProcessTblFk();

      }else{
        batchProcessListForm = (BatchProcessListForm)form; 
        String concatedStr = batchProcessListForm.getRadSelects()[0];
        String splitVals[] = concatedStr.split(":"); 
        batch_process_tbl_pk = splitVals[0];
        
        logger.debug(" batch_process_tbl_pk : "+batch_process_tbl_pk);
        
        
        binderProcessListForm = new BinderProcessListForm();
        if( binderProcessListForm.getHdnPageNumber() == null ){
          binderProcessListForm.setHdnPageNumber("1");
        }
        binderProcessListForm.setTxtBatchNumber(splitVals[1]);
        logger.debug("binderProcessListForm.getTxtBatchNumber() : "+binderProcessListForm.getTxtBatchNumber());
        binderProcessListForm.setTxtDepartmentId(splitVals[4]);
        logger.debug("binderProcessListForm.getTxtDepartmentId() : "+binderProcessListForm.getTxtDepartmentId());
        binderProcessListForm.setTxtBatchStartDate(splitVals[2]);
        logger.debug("binderProcessListForm.getTxtBatchStartDate() : "+binderProcessListForm.getTxtBatchStartDate());
        binderProcessListForm.setTxtBatchEndDate(splitVals[3]);
        logger.debug("binderProcessListForm.getTxtBatchEndDate() : "+binderProcessListForm.getTxtBatchEndDate());
        if( (binderProcessListForm.getTxtBatchEndDate() != null) &&  
            (binderProcessListForm.getTxtBatchEndDate().trim().length() != 0 ) ){
          request.setAttribute("isCompleted",new Boolean(true));
        }else{
          request.setAttribute("isCompleted",new Boolean(false));
        }
      }

      binders = Operations.getBatchProcessDetails(ds,batch_process_tbl_pk,binderProcessListForm.getHdnPageNumber(),userProfile.getRecordsPerPage());
      logger.debug("Binders size : "+binders.size());

      String pageCount = ( binders.size() == 1 )?"1":(((Integer)binders.get(0)).toString());
      binderProcessListForm.setHdnPageCount(pageCount);
      binders.remove(0);
      if( binders.size() == 0 ){
        return mapping.getInputForward();  
      }
      binderProcessListForm.setHdnBatchProcessTblFk(batch_process_tbl_pk);
      
      msg = new ActionMessage("msg.items", String.valueOf(binders.size()));
      messages.add("items",msg);
      
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditBatchProcessB4Action");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
      session.setAttribute("messages",messages);
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditBatchProcessB4Action");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall", e.toString());
      messages.add("items",msg);
      session.setAttribute("messages",messages);
      return mapping.getInputForward();
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute("binderProcessListForm",binderProcessListForm);
    request.setAttribute("binders",binders);
    
    if( ((ActionMessages)session.getAttribute("messages")) != null ){
      saveMessages(request,((ActionMessages)session.getAttribute("messages")));
      session.removeAttribute("messages");
    }else{
      saveMessages(request,messages);
    }

    logger.info("Exiting EditBatchProcessB4Action***");
    return mapping.findForward("success");
  }

}