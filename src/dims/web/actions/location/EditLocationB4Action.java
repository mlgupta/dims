package dims.web.actions.location;

import dims.web.actionforms.location.LocationListForm;
import dims.web.actionforms.location.LocationNewEditForm;
import dims.web.beans.location.Operations;
import dims.web.general.DIMSConstants;

import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditLocationB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditLocationB4Action***");
    LocationNewEditForm locationNewEditForm = null;
    try {
      //String dept_tbl_pk = request.getParameter("id");
      LocationListForm locationListForm = (LocationListForm)form; 
      String location_tbl_pk = locationListForm.getRadSelect()[0];
      logger.debug(" location_tbl_pk : " + location_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      locationNewEditForm = Operations.getLocationDetails(ds,location_tbl_pk);
      if( locationNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditLocationB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditLocationB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute("locationNewEditForm",locationNewEditForm);
    logger.info("Exiting EditLocationB4Action***");
    return mapping.findForward("success");
  }


}