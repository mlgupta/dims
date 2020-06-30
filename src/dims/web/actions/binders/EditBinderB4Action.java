package dims.web.actions.binders;

import dims.web.actionforms.binders.BinderListForm;
import dims.web.actionforms.binders.BinderNewEditForm;
import dims.web.beans.binders.Operations;
import dims.web.general.DIMSConstants;

import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditBinderB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditBinderB4Action***");
    BinderNewEditForm binderNewEditForm = null;
    try {
      //String dept_tbl_pk = request.getParameter("id");
      BinderListForm binderListForm = (BinderListForm)form; 
      String binder_prefix_tbl_pk = binderListForm.getRadSelect()[0];
      logger.debug(" binder_prefix_tbl_pk : "+binder_prefix_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      binderNewEditForm = Operations.getBinderPrefixDetails(ds,binder_prefix_tbl_pk);
      if( binderNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditBinderB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditBinderB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute("binderNewEditForm",binderNewEditForm);
    logger.info("Exiting EditBinderB4Action***");
    return mapping.findForward("success");
  }

}