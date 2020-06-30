package dims.web.actions.users;

import dims.web.actionforms.users.UserListForm;
import dims.web.actionforms.users.UserNewEditForm;
import dims.web.beans.department.DepartmentListBean;
import dims.web.beans.users.Operations;
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

public class EditUserB4Action extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering EditUserB4Action***");
    UserNewEditForm userNewEditForm = null;
    ArrayList departments = new ArrayList();
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      //String dept_tbl_pk = request.getParameter("id");
      UserListForm userListForm = (UserListForm)form; 
      String user_tbl_pk = userListForm.getRadSelect()[0];
      logger.debug(" user_tbl_pk : "+user_tbl_pk);
      DataSource ds = getDataSource(request,"DIMS");
      userNewEditForm = Operations.getUserDetails(ds,user_tbl_pk);
      String dept_Id = userNewEditForm.getCboDepartments();
      departments = dims.web.beans.department.Operations.listDepartments(ds);
      departments.remove(0);
      for( int index = 0; index < departments.size(); index++ ){
        if( ((DepartmentListBean)departments.get(index)).getDepartmentId().equalsIgnoreCase(dept_Id) ){
          userNewEditForm.setCboDepartments(((DepartmentListBean)departments.get(index)).getDepartmentTblPk());
          DepartmentListBean dept = ((DepartmentListBean)departments.get(index));
          departments.remove(index);
          departments.add(0,dept);
        }
      }
      if( userNewEditForm == null ){
        return mapping.getInputForward();  
      }
    }catch (SQLException sqle) {
      logger.error("SQLError occurred in EditUserB4Action");
      logger.error(sqle.toString());
      return mapping.getInputForward();
    }catch (Exception e) {
      logger.error("Error occurred in EditUserB4Action");
      logger.error(e.toString());
      return mapping.getInputForward();
    }
    
    logger.info("Mapping : "+mapping.getAttribute());
    session.setAttribute("userPwd",userNewEditForm.getTxtUserPassword());
    request.setAttribute("userNewEditForm",userNewEditForm);
    request.setAttribute("departments",departments);
    logger.info("Exiting EditUserB4Action***");
    return mapping.findForward("success");
  }


}