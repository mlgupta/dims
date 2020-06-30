package dims.web.actions.location;

import dims.web.actionforms.location.LocationListForm;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class LocationListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering LocationListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    HttpSession session = request.getSession(false);
    LocationListForm locationListForm = null;
    UserProfile userProfile = null;
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    if( request.getParameter("oper")!= null && request.getParameter("oper").equalsIgnoreCase("search") ){
      locationListForm = (LocationListForm)form;
      logger.info("Location ID: " +locationListForm.getLocationID());
      logger.info("Location City: " +locationListForm.getLocationCity());
    }else if( request.getParameter("oper")!= null && request.getParameter("oper").equalsIgnoreCase("nav") ){
      locationListForm = (LocationListForm)form;
    }else{
      locationListForm = new LocationListForm();
      locationListForm.setLocationID("");
      locationListForm.setLocationCity("");
    }
    
    ArrayList locations = new ArrayList();
    logger.debug("locationListForm.getHdnPageNumber() : "+locationListForm.getHdnPageNumber());
    if( locationListForm.getHdnPageNumber() == null ){
      locationListForm.setHdnPageNumber("1");
    }
    
    try {
      DataSource ds = getDataSource(request,"DIMS");
      locations = Operations.listLocations(ds,locationListForm,userProfile.getRecordsPerPage());
      String pageCount = ( locations.size() == 1 )?"1":(((Integer)locations.get(0)).toString());
      locationListForm.setHdnPageCount(pageCount);
      logger.debug("locationListForm.getHdnPageCount() : "+locationListForm.getHdnPageCount());
      locations.remove(0);
      msg = new ActionMessage("msg.items", String.valueOf(locations.size()));
      messages.add("items",msg);
    }catch (SQLException sqle) {
      logger.error("An error occured in LocationListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in LocationListAction");
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
    request.setAttribute(mapping.getAttribute(),locationListForm);
    request.setAttribute("locations",locations);
    
    logger.info("Exiting LocationListAction***");
    return mapping.findForward("success");
  }

}