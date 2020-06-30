package dims.web.actions.department;

import dims.web.actionforms.department.DepartmentListForm;
import dims.web.actionforms.department.DepartmentNewEditForm;
import dims.web.beans.department.Operations;
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

public class EditDepartmentB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditDepartmentB4Action***");
    DepartmentNewEditForm departmentNewEditForm = null;
    try {
      //String dept_tbl_pk = request.getParameter("id");
      DepartmentListForm departmentListForm = (DepartmentListForm)form; 
      String dept_tbl_pk = departmentListForm.getRadSelect()[0];
      logger.debug(" dept_tbl_pk : "+dept_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      departmentNewEditForm = Operations.getDepartmentDetails(ds,dept_tbl_pk);
      if( departmentNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditDepartmentB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditDepartmentB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute("departmentNewEditForm",departmentNewEditForm);
    logger.info("Exiting EditDepartmentB4Action***");
    return mapping.findForward("success");
  }


}