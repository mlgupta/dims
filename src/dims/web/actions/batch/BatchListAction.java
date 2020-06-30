package dims.web.actions.batch;

import dims.web.actionforms.batch.BatchListForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.location.Operations;
import dims.web.beans.batch.BatchListBean;
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


public class BatchListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering BatchListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    HttpSession session = request.getSession(false);
    BatchListForm batchListForm = null;
    UserProfile userProfile = null;
    ArrayList batchs = new ArrayList();
    ArrayList locations = new ArrayList();
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    try {
      DataSource ds = getDataSource(request,"DIMS");
      locations = Operations.listLocations(ds);
      if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("search") ){
        batchListForm = (BatchListForm)form;
        logger.info(" Location ID : " +batchListForm.getCboSearchLocations());
        logger.info(" Batch Number : " +batchListForm.getTxtSearchBatchNumber());
      }else if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("nav") ){
        batchListForm = (BatchListForm)form;
      }else{
        batchListForm = new BatchListForm();
        batchListForm.setCboSearchLocations(((LocationListBean)locations.get(0)).getLocationID());
        batchListForm.setTxtSearchBatchNumber("");
      }
      
      logger.debug("batchListForm.getHdnPageNumber() : "+batchListForm.getHdnPageNumber());
      if( batchListForm.getHdnPageNumber() == null ){
        batchListForm.setHdnPageNumber("1");
      }
      
      batchs = dims.web.beans.batch.Operations.listBatchs(ds,batchListForm,userProfile.getRecordsPerPage());
      String pageCount = ( batchs.size() == 1 )?"1":(((Integer)batchs.get(0)).toString());
      batchListForm.setHdnPageCount(pageCount);
      batchs.remove(0);
      
      msg = new ActionMessage("msg.items", String.valueOf(batchs.size()));
      messages.add("items",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in BatchListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in BatchListAction");
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
    request.setAttribute(mapping.getAttribute(),batchListForm);
    request.setAttribute("locations",locations);
    request.setAttribute("batchs",batchs);
    
    logger.info("Exiting BatchListAction***");
    return mapping.findForward("success");
  }

}