package dims.web.actions.preference;

import dims.web.actionforms.preference.PreferenceForm;
import dims.web.beans.preference.Operations;
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

public class PreferenceAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    logger.info("Entering PreferenceAction***");

    ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    HttpSession session = request.getSession(false);
    PreferenceForm preferenceForm = null;
    DataSource ds = null;
    String[] cboControlFlow = null;
    String[] chkEnableDept = null;
    String[] hdnDeptTblPk = null;
    String[] txtDeptID = null;
    int totalDepts = 0;
    int enabledDepts = 0;
    
    try{
      ds = getDataSource(request,"DIMS");
      preferenceForm = (PreferenceForm)form;
      
      if( preferenceForm != null ){
        totalDepts = preferenceForm.deptTblPkMapSize();
        enabledDepts = preferenceForm.deptIDMapSize();
      
        cboControlFlow = new String[enabledDepts];
        chkEnableDept = new String[enabledDepts];
        txtDeptID = new String[enabledDepts];
        hdnDeptTblPk = new String[totalDepts];
        int counter = 0;
  
        for( int index = 0; index < totalDepts; index++ ){
          if( preferenceForm.getChkEnableDept(index) != null ){
            cboControlFlow[counter] = preferenceForm.getCboControlFlow(index);
            chkEnableDept[counter] = preferenceForm.getChkEnableDept(index);
            txtDeptID[counter] = preferenceForm.getTxtDeptID(index);
            logger.debug(" chkEnableDept["+counter+"]  : "+chkEnableDept[counter] );
            logger.debug(" cboControlFlow["+counter+"] : "+cboControlFlow[counter] );
            logger.debug(" txtDeptID["+counter+"]      : "+txtDeptID[counter] );
            logger.debug(" \n " );
            counter++;
          }
          hdnDeptTblPk[index] = preferenceForm.getHdnDeptTblPk(index);
          logger.debug(" hdnDeptTblPk["+index+"]      : "+hdnDeptTblPk[index] );
          logger.debug(" \n " );
        }
        
        Operations.setDeptSequences(ds,chkEnableDept,cboControlFlow,hdnDeptTblPk,txtDeptID);
        msg = new ActionMessage("msg.department.control.sequence");
        messages.add("controlSequence",msg);
      }
    }catch( SQLException sqle ){
      logger.error("SQLException in PreferenceAction...");
      logger.error(sqle.toString());
      msg = new ActionMessage("errors.catchall",sqle.toString());
      messages.add("controlSequenceErr",msg);
    }catch( Exception e ){
      logger.error("Exception in PreferenceAction...");
      logger.error(e.toString());
      msg = new ActionMessage("errors.catchall",e.toString());
      messages.add("controlSequenceErr",msg);
      e.printStackTrace();
    }
    
    session.setAttribute("messages",messages);
    logger.info("Exiting PreferenceAction***");
    return mapping.findForward("success");
  }

}