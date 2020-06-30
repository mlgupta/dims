package dims.web.actions.batch;

import dims.web.actionforms.batch.BatchNewEditForm;
import dims.web.beans.batch.Operations;
import dims.web.beans.users.UserProfile;
import dims.web.general.DIMSConstants;

import dims.web.general.ErrorConstants;
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

public class EditBatchAction extends Action  {
  Logger logger=Logger.getLogger(DIMSConstants.LOGGER.toString());
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ActionMessages messages=new ActionMessages();
    ActionMessage msg=null;
    String lineNumber=null;
    BatchNewEditForm batchNewEditForm = null;
    HttpSession session = null;
    logger.info("Entering EditBatchAction***");
    String cboPrefix="cboPrefix_"; 
    String txtBinderStartNumber="txtBinderStartNumber_";
    String txtBinderEndNumber="txtBinderEndNumber_";
    String cboYear="cboYear_";
    String hdnRowValue="hdnRowValue_";
    String chkBinder="chkBinder_";
    String hdnBinderTblPK="hdnBinderTblPK_";
    int totalRows=0;
    try {
      session = request.getSession(false);
      UserProfile userProfile=(UserProfile)session.getAttribute("userProfile");
      
      batchNewEditForm = (BatchNewEditForm)form;
      DataSource ds = getDataSource(request,"DIMS");
      totalRows= Integer.parseInt(request.getParameter("hdnRowValues"));
      System.out.println("totalRows :" + totalRows);
      //System.out.println("RowValues : " + batchNewEditForm.getHdnRowValues());
      StringBuffer binderString=null;
      binderString = new StringBuffer();
      for(int i=0 ; i<totalRows ;i++){
        System.out.println("hdnRowvalue : " +request.getParameter(hdnRowValue+new String().valueOf(i)));
        if ((request.getParameter(hdnRowValue+new String().valueOf(i)))!=null && !(request.getParameter(hdnRowValue+new String().valueOf(i))).equalsIgnoreCase("") ){
      
          lineNumber=Integer.toString((i+1));
          StringBuffer binderDetailString=null;
          String strYear = request.getParameter(cboYear+new String().valueOf(i));
          String intBinderPrefixTblFk= request.getParameter(cboPrefix+new String().valueOf(i));
          String strBinderStartNumber= request.getParameter(txtBinderStartNumber+new String().valueOf(i));
          String strBinderEndNumber= request.getParameter(txtBinderEndNumber+new String().valueOf(i));
          String strBinderNumber = (strBinderStartNumber +"to"+ strBinderEndNumber);
          String strStatus= request.getParameter(hdnRowValue+new String().valueOf(i));
          String strBinderTblPK = request.getParameter(hdnBinderTblPK+new String().valueOf(i));
          if (strBinderTblPK==null){  
            strBinderTblPK="-1";
          }
          System.out.println("Binder PK :" + strBinderTblPK);
          binderDetailString = new StringBuffer( "\"{" + strYear +"," + 
                                     intBinderPrefixTblFk + "," + 
                                     strBinderNumber + "," + 
                                     strBinderStartNumber + "," +
                                     strBinderEndNumber + "," +
                                     strStatus + "," +
                                     strBinderTblPK + "}\"");
          if ((binderString!=null) && (binderString.length()!=0)) {
            binderString.append(","); 
            binderString.append(binderDetailString); 
          }else{
            binderString.append("{"); 
            binderString.append(binderDetailString);
          }
        }
      }
      binderString.append("}");
      logger.debug("binderString : " + binderString);
      //System.out.println("binderString : " + binderString);
      
      Operations.saveEditBatch(ds,batchNewEditForm,binderString,userProfile.getUserTblPk());
      //Operations.saveEditBatch(ds,batchNewEditForm,binderString,"100");
      msg = new ActionMessage("msg.new.batchNumber",batchNewEditForm.getTxtBatchNumber());
      messages.add("newBatch",msg);
      
    }catch (SQLException sqle) {
      logger.error("An error occured in EditBatchAction");
      logger.error(sqle.toString());
      if( sqle.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode()) ){
        msg=new ActionMessage("msg.Unique.BatchNumber",batchNewEditForm.getTxtBatchNumber());
        messages.add("messageUnique",msg);
        if (!messages.isEmpty()) {
          session.setAttribute("messages",messages);
        }
        return mapping.getInputForward();
      }
    }catch (Exception e) {
      logger.error("An error occured in EditBatchAction");
      logger.error(e.toString());
      msg=new ActionMessage("errors.catchall",e.toString());
      messages.add("messagecatchall",msg);
      if (!messages.isEmpty()) {
        session.setAttribute("messages",messages);
      }
      return mapping.getInputForward();
    }
    
    if (!messages.isEmpty()) {
      session.setAttribute("messages",messages);
    }
    
    logger.info("Exiting EditBatchAction***");
    return mapping.findForward("success");
  }

}