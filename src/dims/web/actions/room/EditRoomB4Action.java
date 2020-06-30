package dims.web.actions.room;

import dims.web.actionforms.room.RoomListForm;
import dims.web.actionforms.room.RoomNewEditForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.room.Operations;
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

public class EditRoomB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditRoomB4Action***");
    RoomNewEditForm roomNewEditForm = null;
    ArrayList locations = new ArrayList();
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      //String dept_tbl_pk = request.getParameter("id");
      RoomListForm roomListForm = (RoomListForm)form; 
      String room_tbl_pk = roomListForm.getRadSelect()[0];
      logger.debug(" room_tbl_pk : "+room_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      roomNewEditForm = Operations.getRoomDetails(ds,room_tbl_pk);
      String location_tbl_Fk = roomNewEditForm.getCboLocations();
      locations = dims.web.beans.location.Operations.listLocations(ds);
      locations.remove(0);
      /*for( int index = 0; index < locations.size(); index++ ){
        if( ((LocationListBean)locations.get(index)).getLocationTblPK().equalsIgnoreCase(location_tbl_Fk) ){
          LocationListBean loc = ((LocationListBean)locations.get(index));
          locations.remove(index);
          locations.add(0,loc);
        }
      }*/
      if( roomNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditRoomB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditRoomB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    request.setAttribute("roomNewEditForm",roomNewEditForm);
    request.setAttribute("locations",locations);

    logger.info("Exiting EditRoomB4Action***");
    return mapping.findForward("success");
  }

}