package dims.web.actions.department;

import dims.web.actionforms.department.DepartmentNewEditForm;
import dims.web.general.DIMSConstants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NewDepartmentB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering NewDepartmentB4Action***");
    
    DepartmentNewEditForm departmentNewEditForm = new DepartmentNewEditForm();
    departmentNewEditForm.setTxtDepartmentNo("New");
    departmentNewEditForm.setTxtDepartmentId("");
    departmentNewEditForm.setTxtDepartmentName("");
    departmentNewEditForm.setTxaDescription("");
    
    logger.info("Mapping : "+mapping.getAttribute());
    request.setAttribute(mapping.getAttribute(),departmentNewEditForm);
    logger.info("Exiting NewDepartmentB4Action***");
    return mapping.findForward("success");
  }


}