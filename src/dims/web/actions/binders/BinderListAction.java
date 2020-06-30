package dims.web.actions.binders;

import dims.web.actionforms.binders.BinderListForm;
import dims.web.beans.binders.Operations;
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


public class BinderListAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering BinderListAction***");
    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;

    HttpSession session = request.getSession(false);
    BinderListForm binderListForm = null;
    UserProfile userProfile = null;
    
    userProfile = (UserProfile)session.getAttribute("userProfile");
    if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("search") ){
      binderListForm = (BinderListForm)form;
      logger.info(" Binder Prefix : " +binderListForm.getTxtBinderPrefix());
    }else if( request.getParameter("oper")!= null && 
        request.getParameter("oper").equalsIgnoreCase("nav") ){
      binderListForm = (BinderListForm)form;
    }else{
      binderListForm = new BinderListForm();
      binderListForm.setTxtBinderPrefix("");
    }
    
    ArrayList binders = new ArrayList();
    logger.debug("binderListForm.getHdnPageNumber() : "+binderListForm.getHdnPageNumber());
    if( binderListForm.getHdnPageNumber() == null ){
      binderListForm.setHdnPageNumber("1");
    }
    
    try {
      DataSource ds = getDataSource(request,"DIMS");
      binders = Operations.listBinderPrefixes(ds,binderListForm,userProfile.getRecordsPerPage());
      String pageCount = ( binders.size() == 1 )?"1":(((Integer)binders.get(0)).toString());
      binderListForm.setHdnPageCount(pageCount);
      logger.debug("binderListForm.getHdnPageCount() : "+binderListForm.getHdnPageCount());
      binders.remove(0);
      msg = new ActionMessage("msg.items", String.valueOf(binders.size()));
      messages.add("items",msg);
    }catch (SQLException sqle) {
      logger.error("An SQLError occured in BinderListAction");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch (Exception e) {
      logger.error("An error occured in BinderListAction");
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
    request.setAttribute(mapping.getAttribute(),binderListForm);
    request.setAttribute("binders",binders);
    
    logger.info("Exiting BinderListAction***");
    return mapping.findForward("success");
  }

}