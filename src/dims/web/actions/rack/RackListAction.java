package dims.web.actions.rack;

import dims.web.actionforms.rack.RackListForm;
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

public class RackListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering RackListAction***");
    ActionMessages messages = new ActionMessages();
    ActionMessage msg = null;
    RoomListBean room = null; 

    HttpSession session = null;
    RackListForm rackListForm = null;
    UserProfile userProfile = null;
    ArrayList rooms = new ArrayList();
    ArrayList locations = new ArrayList();
    ArrayList racks = new ArrayList();
    
    try {
      session = request.getSession(false);
      userProfile = (UserProfile)session.getAttribute("userProfile");
      DataSource ds = getDataSource(request,"DIMS");
      locations = Operations.listLocations(ds);
      
      if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("search") ){

        rackListForm = (RackListForm)form;
        logger.info(" Location ID : " +rackListForm.getCboSearchLocations());
        logger.info(" Room Number : " +rackListForm.getCboSearchRoomNumber());
        rooms = dims.web.beans.room.Operations.listRooms(ds,Integer.parseInt(rackListForm.getCboSearchLocations()));
        for( int index = 0; index < rooms.size(); index++ ){
          if( ((RoomListBean)rooms.get(index)).getRoomTblPk().equals(rackListForm.getCboSearchRoomNumber()) ){
            RoomListBean loc = ((RoomListBean)rooms.get(index));
            rooms.remove(index);
            rooms.add(0,loc);
          }
        }
        
      }else if( request.getParameter("oper")!= null && 
          request.getParameter("oper").equalsIgnoreCase("nav") ){

        rackListForm = (RackListForm)form;
        rooms = dims.web.beans.room.Operations.listRooms(ds,Integer.parseInt(rackListForm.getCboSearchLocations()));
        for( int index = 0; index < rooms.size(); index++ ){
          if( ((RoomListBean)rooms.get(index)).getRoomTblPk().equals(rackListForm.getCboSearchRoomNumber()) ){
            RoomListBean loc = ((RoomListBean)rooms.get(index));
            rooms.remove(index);
            rooms.add(0,loc);
          }
        }

      }else{

        rackListForm = new RackListForm();
        rackListForm.setCboSearchLocations(((LocationListBean)locations.get(0)).getLocationID());
        room = new RoomListBean();
        room.setRoomTblPk("-1");
        room.setRoomNumber("Select");
        rooms.add(0,room);
        rackListForm.setCboSearchRoomNumber(room.getRoomNumber());
        rackListForm.setTxtSearchRack("");

      }
      
      
      logger.debug("rackListForm.getHdnPageNumber() : "+rackListForm.getHdnPageNumber());
      if( rackListForm.getHdnPageNumber() == null ){
        rackListForm.setHdnPageNumber("1");
      }
      
      racks = dims.web.beans.rack.Operations.listRacks(ds,rackListForm,userProfile.getRecordsPerPage());
      String pageCount = ( racks.size() == 1 )?"1":(((Integer)racks.get(0)).toString());
      rackListForm.setHdnPageCount(pageCount);
      racks.remove(0);

      msg = new ActionMessage("msg.items", String.valueOf(racks.size()));
      messages.add("items",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in RackListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in RackListAction");
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
    request.setAttribute(mapping.getAttribute(),rackListForm);
    request.setAttribute("locations",locations);
    request.setAttribute("rooms",rooms);
    request.setAttribute("racks",racks);
    
    logger.info("Exiting RackListAction***");
    return mapping.findForward("success");
  }

}