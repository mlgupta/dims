package dims.web.actions.loginout;

import dims.web.general.DIMSConstants;
import dims.web.actionforms.loginout.LoginForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;


public class LoginB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering LoginB4Action***");
    HttpSession session = null;
    LoginForm loginForm = null;
    try{
      session = request.getSession(false);
      if( ((ActionMessages)session.getAttribute("messages")) != null ){
        saveMessages(request,((ActionMessages)session.getAttribute("messages")));
        session.removeAttribute("messages");
      }
      loginForm = new LoginForm();
      loginForm.setUserID("");
      loginForm.setUserPassword("");
    }catch( Exception e ){
      logger.error(" An Error occurred in LoginB4Action"); 
      logger.error(e.toString());
    }
    request.setAttribute(mapping.getAttribute(),loginForm);
    logger.info("Exiting LoginB4Action***");
    return mapping.findForward("success");
  }
}
