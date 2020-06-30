package dims.web.actions.preference;

import dims.web.actionforms.preference.PreferenceForm;
import dims.web.beans.preference.Operations;
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

public class PreferenceB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering PreferenceB4Action***");

    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    HttpSession session = request.getSession(false);
    ArrayList departments = new ArrayList();
    ArrayList sequences = new ArrayList();
    PreferenceForm preferenceForm = null;
    DataSource ds = null;
    
    try{
      ds = getDataSource(request,"DIMS");
      preferenceForm = new PreferenceForm();
      departments = Operations.listDepartments(ds);
      /*for( int index = 0; index < departments.size(); index++ ){
        DepartmentListBean bean = (DepartmentListBean)departments.get(index);
        logger.debug(" DepartmentTblPk    : "+bean.getDepartmentTblPk());
        logger.debug(" DepartmentId        : "+bean.getDepartmentId());
        logger.debug(" DepartmentCntrlSeq : "+bean.getDepartmentCntrlSeq());
        logger.debug("\n");
      }*/
      sequences = Operations.listDepartmentSequences(ds);
      msg = new ActionMessage("msg.items", String.valueOf(departments.size()));
      messages.add("items",msg);
    }catch( SQLException sqle ){
      logger.error("SQLException in PreferenceB4Action...");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall", sqle.toString());
      messages.add("items",msg);
    }catch( Exception e ){
      logger.error("Exception in PreferenceB4Action...");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall", e.toString());
      messages.add("items",msg);
    }
    
    if( ((ActionMessages)session.getAttribute("messages")) != null ){
      saveMessages(request,((ActionMessages)session.getAttribute("messages")));
      session.removeAttribute("messages");
    }else{
      saveMessages(request,messages);
    }
    request.setAttribute("preferenceForm",preferenceForm);
    request.setAttribute("departments",departments);
    request.setAttribute("sequences",sequences);

    logger.info("Exiting PreferenceB4Action***");
    return mapping.findForward("success");
  }

}