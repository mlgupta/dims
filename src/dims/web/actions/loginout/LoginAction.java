package dims.web.actions.loginout;

import dims.web.actionforms.loginout.LoginForm;
import dims.web.beans.loginout.Operations;
import dims.web.beans.users.UserProfile;
import dims.web.general.DIMSConstants;
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

public class LoginAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */

  public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
    String target = "success";
    ActionMessages messages = new ActionMessages();
    ActionMessage msg = null;
    HttpSession httpSession = null;
    LoginForm loginForm = (LoginForm)form;
    UserProfile userProfile = new UserProfile();
    DataSource ds = null;
    
    try{
      logger.info("Entering LoginAction now...");
      httpSession = request.getSession(false);
      ds = getDataSource(request,"DIMS");
      userProfile = Operations.authenticateUser(ds,loginForm);
      if( userProfile != null ){
        logger.debug(" Authentication successful!!! ");
        httpSession.setAttribute("userProfile",userProfile);
      }else{
        msg = new ActionMessage("error.login.unsuccessful");
        messages.add("invalidCredentials",msg);
        target = "failure";
      }
      
    }catch( SQLException sqle ){
      logger.error("An SQLError occurred in LoginAction .");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall",sqle.toString());
      messages.add("sqlerror",msg);
      target = "failure";
    }catch( Exception e ){
      logger.error("An Error occurred in LoginAction .");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall",e.toString());
      messages.add("error",msg);
      target = "failure";
    }
    if( !messages.isEmpty() ){
      httpSession.setAttribute("messages",messages);
    }
    logger.info("Exiting LoginAction now...");
    return mapping.findForward(target);  
  }
  
}