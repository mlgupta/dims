package dims.web.actions.room;

import dims.web.actionforms.room.RoomNewEditForm;
import dims.web.beans.room.Operations;
import dims.web.general.DIMSConstants;

import dims.web.general.ErrorConstants;
import java.io.IOException;

import java.sql.SQLException;
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

public class NewRoomAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    RoomNewEditForm roomNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering NewRoomAction***");
    
    try {
      session = request.getSession(false);
      roomNewEditForm = (RoomNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      Operations.addRoom(ds,roomNewEditForm);
      msg = new ActionMessage("msg.new.roomNumber",roomNewEditForm.getTxtRoomNumber());
      messages.add("newRoom",msg);
    }catch (SQLException sqle) {
      logger.error("An error occured in NewRoomAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.RoomNumber",roomNewEditForm.getTxtRoomNumber());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An error occured in NewRoomAction");
      logger.error(e.toString());
      msg=new ActionMessage("errors.catchall",e.toString());
      messages.add("messagecatchall",msg);
      if (!messages.isEmpty()) {
        session.setAttribute("messages",messages);
      }
      return mapping.getInputForward();
    }
    
    if (!messages.isEmpty()) {
      session.setAttribute("messages",messages);
    }
    logger.info("Exiting NewRoomAction***");
    return mapping.findForward("success");
  }

}