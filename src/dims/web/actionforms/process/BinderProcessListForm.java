package dims.web.actionforms.process;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class BinderProcessListForm extends ActionForm  {
  private String txtBatchNumber;
  private String txtDepartmentId;
  private String txtBatchStartDate;
  private String txtBatchEndDate;
  private String[] txtAssignedTo;
  private String[] chkIsCompleted;
  private String[] txtComments;
  private String hdnBatchProcessTblFk;
  private String[] hdnBinderProcessTblPk;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String[] hdnRowValue;
  private boolean blnIsBatchProcessed;
  
  private Map hdnRowValueMap = new HashMap();
  private Map hdnBinderProcessTblPkMap = new HashMap();
  private Map txtCommentsMap = new HashMap();
  private Map chkIsCompletedMap = new HashMap();
  private Map txtAssignedToMap = new HashMap();

  public String getTxtBatchNumber() {
    return txtBatchNumber;
  }

  public void setTxtBatchNumber(String txtBatchNumber) {
    this.txtBatchNumber = txtBatchNumber;
  }

  public String getTxtDepartmentId() {
    return txtDepartmentId;
  }

  public void setTxtDepartmentId(String txtDepartmentId) {
    this.txtDepartmentId = txtDepartmentId;
  }

  public String getTxtBatchStartDate() {
    return txtBatchStartDate;
  }

  public void setTxtBatchStartDate(String txtBatchStartDate) {
    this.txtBatchStartDate = txtBatchStartDate;
  }

  public String getTxtBatchEndDate() {
    return txtBatchEndDate;
  }

  public void setTxtBatchEndDate(String txtBatchEndDate) {
    this.txtBatchEndDate = txtBatchEndDate;
  }

  public String getHdnBatchProcessTblFk() {
    return hdnBatchProcessTblFk;
  }

  public void setHdnBatchProcessTblFk(String hdnBatchProcessTblFk) {
    this.hdnBatchProcessTblFk = hdnBatchProcessTblFk;
  }

////////////
  public String getHdnBinderProcessTblPk(int index) {
    return (String) hdnBinderProcessTblPkMap.get(new Integer(index));
  }

  public void setHdnBinderProcessTblPk(int index ,String hdnBinderProcessTblPk) {
    hdnBinderProcessTblPkMap.put( new Integer( index ), hdnBinderProcessTblPk );
  }
  
  public String[] getHdnBinderProcessTblPks() {
    return (String[]) hdnBinderProcessTblPkMap.values().toArray( new String[hdnBinderProcessTblPkMap.size()] );
  }
////////////

////////////
  public String getTxtAssignedTo(int index) {
    return (String) txtAssignedToMap.get(new Integer(index));
  }

  public void setTxtAssignedTo(int index ,String txtAssignedTo) {
    txtAssignedToMap.put( new Integer( index ), txtAssignedTo );
  }
  
  public String[] getTxtAssignedTos() {
    return (String[]) txtAssignedToMap.values().toArray( new String[txtAssignedToMap.size()] );
  }
////////////

////////////
  public String getChkIsCompleted(int index) {
    return (String) chkIsCompletedMap.get(new Integer(index));
  }

  public void setChkIsCompleted(int index ,String chkIsCompleted) {
    chkIsCompletedMap.put( new Integer( index ), chkIsCompleted );
  }
  
  public String[] getChkIsCompleteds() {
    return (String[]) chkIsCompletedMap.values().toArray( new String[chkIsCompletedMap.size()] );
  }
////////////

////////////
  public String getTxtComments(int index) {
    return (String) txtCommentsMap.get(new Integer(index));
  }

  public void setTxtComments(int index ,String txtComments) {
    txtCommentsMap.put( new Integer( index ), txtComments );
  }
  
  public String[] getTxtCommentss() {
    return (String[]) txtCommentsMap.values().toArray( new String[txtCommentsMap.size()] );
  }
////////////

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

  public String getHdnPageCount() {
    return hdnPageCount;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

  public boolean getBlnIsBatchProcessed() {
    return blnIsBatchProcessed;
  }

  public void setBlnIsBatchProcessed(boolean blnIsBatchProcessed) {
    this.blnIsBatchProcessed = blnIsBatchProcessed;
  }

////////////
  public String getHdnRowValue(int index) {
    return (String) hdnRowValueMap.get(new Integer(index));
  }

  public void setHdnRowValue(int index ,String hdnRowValue) {
    hdnRowValueMap.put( new Integer( index ), hdnRowValue );
  }
  
  public String[] getHdnRowValues() {
    return (String[]) hdnRowValueMap.values().toArray( new String[hdnRowValueMap.size()] );
  }
////////////
}