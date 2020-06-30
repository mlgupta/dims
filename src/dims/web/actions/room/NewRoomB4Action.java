package dims.web.actions.room;

import dims.web.actionforms.room.RoomNewEditForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.location.Operations;
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

public class NewRoomB4Action extends Action  {
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
    HttpSession session = request.getSession(false);
    
    logger.info("Entering NewRoomB4Action***");
    RoomNewEditForm roomNewEditForm = new RoomNewEditForm();
    
    roomNewEditForm.setTxtRoomNumber("");
    roomNewEditForm.setHdnRoomTblPk("");
    
    try{
      DataSource ds = getDataSource(request,"DIMS");
      locations = Operations.listLocations(ds);
      roomNewEditForm.setCboLocations(((LocationListBean)locations.get(0)).getLocationTblPK());
    }catch (SQLException sqle) {
      logger.error("An error occured in NewRoomB4Action");
      logger.error(sqle.toString());
      sqle.printStackTrace();
    }catch (Exception e) {
      logger.error("An error occured in NewRoomB4Action");
      logger.error(e.toString());
      e.printStackTrace();
    }
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute(mapping.getAttribute(),roomNewEditForm);
    request.setAttribute("locations",locations);
    
    logger.info("Exiting NewRoomB4Action***");
    return mapping.findForward("success");
  }

}