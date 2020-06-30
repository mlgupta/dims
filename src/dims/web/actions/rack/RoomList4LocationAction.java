package dims.web.actions.rack;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class RoomList4LocationAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering RoomList4LocationAction***");

    HttpSession session = request.getSession(false);
    String roomListDropDown = null;
    int locationTblPK = -1;

    try {
      DataSource ds = getDataSource(request,"DIMS");
      if( request.getParameter("locationTblPK") != null ){
        locationTblPK = Integer.parseInt(request.getParameter("locationTblPK"));
      }
      logger.debug("locationTblPK : "+locationTblPK);
      roomListDropDown = Operations.listRoomsForAjax(ds,locationTblPK);
      logger.debug("roomListDropDown: ");
      logger.debug(roomListDropDown);
    }catch (Exception e) {
      logger.error("An error occured in RoomList4LocationAction");
      logger.error(e.toString());
      e.printStackTrace();
    }

    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write(roomListDropDown);
    
    logger.info("Exiting RoomList4LocationAction***");
    return null;
  }

}