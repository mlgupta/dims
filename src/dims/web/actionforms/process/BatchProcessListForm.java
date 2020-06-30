package dims.web.actionforms.process;

import java.util.HashMap;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class BatchProcessListForm extends ActionForm  {
  private String txtBatchNumber;
  private String cboDepartmentId;
  private String hdnBatchFromDate;
  private String hdnBatchToDate;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String[] radSelect;
  //private boolean[] chkIsCompleted;
  private String[] chkIsCompleted;

  private Map radSelectMap = new HashMap();
  private Map chkIsCompletedMap = new HashMap();

  public String getTxtBatchNumber() {
    return txtBatchNumber;
  }

  public void setTxtBatchNumber(String txtBatchNumber) {
    this.txtBatchNumber = txtBatchNumber;
  }

  public String getCboDepartmentId() {
    return cboDepartmentId;
  }

  public void setCboDepartmentId(String cboDepartmentId) {
    this.cboDepartmentId = cboDepartmentId;
  }

  public String getHdnBatchFromDate() {
    return hdnBatchFromDate;
  }

  public void setHdnBatchFromDate(String hdnBatchFromDate) {
    this.hdnBatchFromDate = hdnBatchFromDate;
  }

  public String getHdnBatchToDate() {
    return hdnBatchToDate;
  }

  public void setHdnBatchToDate(String hdnBatchToDate) {
    this.hdnBatchToDate = hdnBatchToDate;
  }

/*  public String[] getRadSelect() {
    return radSelect;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public boolean[] getChkIsCompleted() {
    return chkIsCompleted;
  }

  public void setChkIsCompleted(boolean[] chkIsCompleted) {
    this.chkIsCompleted = chkIsCompleted;
  }
*/
  public String getHdnPageCount() {
    return hdnPageCount;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

////////////
  public String getRadSelect(int index) {
    return (String) radSelectMap.get(new Integer(index));
  }

  public void setRadSelect(int index ,String radSelect) {
    radSelectMap.put( new Integer( index ), radSelect );
  }
  
  public String[] getRadSelects() {
    return (String[]) radSelectMap.values().toArray( new String[radSelectMap.size()] );
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




}