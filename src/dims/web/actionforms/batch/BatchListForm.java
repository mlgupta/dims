package dims.web.actionforms.batch;
import org.apache.struts.action.ActionForm;

public class BatchListForm extends ActionForm  {
  private String cboSearchLocations;
  private String txtSearchBatchNumber;
  private String txtSearchBatchFromDate;
  private String txtSearchBatchToDate;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String[] radSelect;
  private String[] hdnBatchNumber;

  public void setCboSearchLocations(String cboSearchLocations) {
    this.cboSearchLocations = cboSearchLocations;
  }

  public String getCboSearchLocations() {
    return cboSearchLocations;
  }

  public void setTxtSearchBatchNumber(String txtSearchBatchNumber) {
    this.txtSearchBatchNumber = txtSearchBatchNumber;
  }

  public String getTxtSearchBatchNumber() {
    return txtSearchBatchNumber;
  }

  public void setTxtSearchBatchFromDate(String txtSearchBatchFromDate) {
    this.txtSearchBatchFromDate = txtSearchBatchFromDate;
  }

  public String getTxtSearchBatchFromDate() {
    return txtSearchBatchFromDate;
  }

  public void setTxtSearchBatchToDate(String txtSearchBatchToDate) {
    this.txtSearchBatchToDate = txtSearchBatchToDate;
  }

  public String getTxtSearchBatchToDate() {
    return txtSearchBatchToDate;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

  public String getHdnPageCount() {
    return hdnPageCount;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setHdnBatchNumber(String[] hdnBatchNumber) {
    this.hdnBatchNumber = hdnBatchNumber;
  }

  public String[] getHdnBatchNumber() {
    return hdnBatchNumber;
  }
}