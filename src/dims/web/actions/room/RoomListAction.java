package dims.web.actions.room;

import dims.web.actionforms.room.RoomListForm;
import dims.web.beans.location.LocationListBean;
import dims.web.beans.location.Operations;
import dims.web.beans.room.RoomListBean;
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


public class RoomListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering RoomListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    HttpSession session = request.getSession(false);
    RoomListForm roomListForm = null;
    UserProfile userProfile = null;
    ArrayList rooms = new ArrayList();
    ArrayList locations = new ArrayList();
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    try {
      DataSource ds = getDataSource(request,"DIMS");
      locations = Operations.listLocations(ds);
      if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("search") ){
        roomListForm = (RoomListForm)form;
        logger.info(" Location ID : " +roomListForm.getCboSearchLocations());
        logger.info(" Room Number : " +roomListForm.getTxtSearchRoomNumber());
      }else if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("nav") ){
        roomListForm = (RoomListForm)form;
      }else{
        roomListForm = new RoomListForm();
        roomListForm.setCboSearchLocations(((LocationListBean)locations.get(0)).getLocationID());
        roomListForm.setTxtSearchRoomNumber("");
      }
      
      
      logger.debug("roomListForm.getHdnPageNumber() : "+roomListForm.getHdnPageNumber());
      if( roomListForm.getHdnPageNumber() == null ){
        roomListForm.setHdnPageNumber("1");
      }
      
      rooms = dims.web.beans.room.Operations.listRooms(ds,roomListForm,userProfile.getRecordsPerPage());
      String pageCount = ( rooms.size() == 1 )?"1":(((Integer)rooms.get(0)).toString());
      roomListForm.setHdnPageCount(pageCount);
      rooms.remove(0);
      
      msg = new ActionMessage("msg.items", String.valueOf(rooms.size()));
      messages.add("items",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in RoomListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in RoomListAction");
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
    request.setAttribute(mapping.getAttribute(),roomListForm);
    request.setAttribute("locations",locations);
    request.setAttribute("rooms",rooms);
    
    logger.info("Exiting RoomListAction***");
    return mapping.findForward("success");
  }

}