package dims.web.actions.rack;

import dims.web.actionforms.rack.RackListForm;
import dims.web.actionforms.rack.RackNewEditForm;
import dims.web.beans.rack.Operations;
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

public class EditRackB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditRackB4Action***");
    RackNewEditForm rackNewEditForm = null;
    ArrayList locations = new ArrayList();
    ArrayList rooms = new ArrayList();
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      //String dept_tbl_pk = request.getParameter("id");
      RackListForm rackListForm = (RackListForm)form; 
      String rack_tbl_pk = rackListForm.getRadSelect()[0];
      logger.debug(" rack_tbl_pk : "+rack_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      rackNewEditForm = Operations.getRackDetails(ds,rack_tbl_pk);
      if( rackNewEditForm == null ){
        return mapping.getInputForward();  
      }
      locations = dims.web.beans.location.Operations.listLocations(ds);
      locations.remove(0);
      rooms = dims.web.beans.room.Operations.listRooms(ds, Integer.parseInt(rackNewEditForm.getCboLocations()));
      rooms.remove(0);
      
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditRackB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditRackB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    request.setAttribute("rackNewEditForm",rackNewEditForm);
    request.setAttribute("locations",locations);
    request.setAttribute("rooms",rooms);

    logger.info("Exiting EditRackB4Action***");
    return mapping.findForward("success");
  }

}