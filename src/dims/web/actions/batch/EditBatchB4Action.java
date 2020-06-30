package dims.web.actions.batch;

import dims.web.actionforms.batch.BatchListForm;
import dims.web.actionforms.batch.BatchNewEditForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.batch.Operations;
import dims.web.general.DIMSConstants;

import java.io.IOException;

import java.sql.Date;
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

public class EditBatchB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ArrayList locations = new ArrayList();
    ArrayList prefixs = new ArrayList();
    ArrayList binders = new ArrayList();
    HttpSession session = request.getSession(false);
    String batchTblPK= null;
    logger.info("Entering EditBatchB4Action***");
    BatchNewEditForm batchNewEditForm = new BatchNewEditForm();
    BatchListForm batchListForm = (BatchListForm)form;
    try{
      DataSource ds = getDataSource(request,"DIMS");
      locations = dims.web.beans.location.Operations.listLocations(ds);
      batchNewEditForm.setCboLocations(((LocationListBean)locations.get(0)).getLocationTblPK());
      prefixs = dims.web.beans.binders.Operations.listBinderPrefixs(ds);
      batchTblPK = batchListForm.getRadSelect()[0];
      batchNewEditForm = Operations.getBatchDetails(ds,batchTblPK);
      //if (batchNewEditForm!=null){
        binders= Operations.getBinders(ds,batchTblPK);
        System.out.println("Binder Size : " + binders.size());
      //}
      if( batchNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("An error occured in EditBatchB4Action");
      logger.error(sqle.toString());
      sqle.printStackTrace();
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("An error occured in EditBatchB4Action");
      logger.error(e.toString());
      e.printStackTrace();
      return mapping.getInputForward();
    }
    logger.info("Mapping : "+mapping.getAttribute());
    //request.setAttribute(mapping.getAttribute(),batchNewEditForm);
    request.setAttribute("batchNewEditForm",batchNewEditForm);
    request.setAttribute("locations",locations);
    request.setAttribute("prefixs",prefixs);
    request.setAttribute("binders",binders);
    
    logger.info("Exiting EditBatchB4Action***");
    return mapping.findForward("success");
  }
}